package edu.grinnell.csc207.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.ByteBuffer;

/**
 * A single block of a blockchain
 */
public class Block {
    // have numeric fields as longs so its easier to hash
    private long number;
    private long data;
    private Hash prev;
    private long nonce;
    private Hash cur;

    /**
     * Creates a new block
     * 
     * @param num an integer representing the block number
     * @param amount is the integer amount of the transaction
     * @param prevHash the hash of the previous block
     * @throws NoSuchAlgorithmException
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.number = (long) num;
        this.data = (long) amount;
        this.prev = prevHash;

        // Create message digest
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        for (long i = 0;; i++) {
            md.update(longToBytes(number));
            md.update(longToBytes(data));
            if (this.prev != null) {
                md.update(prev.getData()); // only update if prev != null
            }
            // testing the nonce
            md.update(longToBytes(i));
            byte[] bytesHash = md.digest(); // Get hash in bytes
            Hash h = new Hash(bytesHash); // Create hash object
            if (h.isValid()) { // Check if valid
                this.nonce = i; // assign params
                this.cur = h;
                break;
            }
        }
    }

    /**
     * Creates a new block
     * 
     * @param num an integer representing the block number
     * @param amount is the integer amount of the transaction
     * @param prevHash the hash of the previous block
     * @param nonce a number used in hashing
     * @throws NoSuchAlgorithmException
     */
    public Block(int num, int amount, Hash prevHash, long nonce)
            throws NoSuchAlgorithmException, IllegalArgumentException {
        this.number = (long) num;
        this.data = (long) amount;
        this.prev = prevHash;
        this.nonce = nonce;

        // Create message digest
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(longToBytes(number));
        md.update(longToBytes(data));
        if (this.prev != null) {
            md.update(prev.getData());
        }
        md.update(longToBytes(nonce));
        byte[] bytesHash = md.digest();
        Hash h = new Hash(bytesHash);
        if (!h.isValid()) {
            throw new IllegalArgumentException("Provided nonce does not produce a valid hash");
        }
        this.cur = h;
    }

    /**
     * @return block number
     */
    public int getNum() {
        return (int) this.number;
    }

    /**
     * @return block transaction amount
     */
    public int getAmount() {
        return (int) this.data;
    }

    /**
     * @return nonce of block
     */
    public long getNonce() {
        return this.nonce;
    }

    /**
     * @return current hash
     */
    public Hash getHash() {
        return this.cur;
    }

    /**
     * @return previous blocks hash
     */
    public Hash getPrevHash() {
        return this.prev;
    }

    /**
     * @return a string representation of our block
     */
    public String toString() {
        String prevStr = (prev == null) ? "null" : prev.toString();
        String curStr = (cur == null) ? "null" : cur.toString();
        return String.format("Block %d (Amount: %d, Nonce: %d, prevHash: %s, hash: %s)",
                number, data, nonce, prevStr, curStr);
    }

    /**
     * Convert a long to an array of bytes
     * Cite: stackoverflow.com/questions/4485128
     *
     * @param num a long representing a block field value
     * @return num converted to a byte array
     */
    public static byte[] longToBytes(long num) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(num);
        return buffer.array();
    }
}
