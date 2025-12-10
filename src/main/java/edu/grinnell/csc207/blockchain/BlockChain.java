package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hashed blocks representing a ledger of transactions
 */
public class BlockChain {
    Node first;
    Node last;
    private int size;
    
    // internal node class
    private class Node {
        Block cur;
        Node next;

        /**
         * @param cur block for this node
         */
        public Node(Block cur) {
            this.cur = cur;
            this.next = null;
        }
    }

    /**
     * Creates our blockchain and first node + block
     * @param initial intial amount
     * @throws NoSuchAlgorithmException
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block genesis = new Block(0, initial, null);
        Node n = new Node(genesis);
        first = n;
        last = n;
        size = 1;
    }

    /**
     * @return size of blockchain
     */
    public int getSize() {
        return size;
    }

    /**
     * adds block to blockchain
     * @param blk block to add
     * @throws IllegalArgumentException
     */
    public void append(Block blk) throws IllegalArgumentException {
        Node n = new Node(blk);
        last.next = n;
        last = n;
        size++;
    }

    /**
     * @param amount transaction amount
     * @return new block
     * @throws NoSuchAlgorithmException
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block b = new Block(size, amount, last.cur.getHash());
        return b;
    }

    /**
     * remove last block
     * @return false if blockchain has one element, true if a removal occurs
     */
    public boolean removeLast() {
        if (size == 1) {
            return false;
        }
        Node cur = first;
        Node prev = null;
        while (cur != last) {
            prev = cur;
            cur = cur.next;
        }
        prev.next = null;
        last = prev;
        size--;
        return true;
    }

    /**
     * @return last hash
     */
    public Hash getHash() {
        return last.cur.getHash();
    }
    
    /**
     * @return true if blockchain is valid, false otherwise
     */
    public boolean isValidBlockChain() {
        int balance = first.cur.getAmount(); // initial balance
        Node cur = first.next;
        while (cur != null) {
            balance += cur.cur.getAmount(); // check transaction amount
            if (balance < 0) {
                return false;
            }
            cur = cur.next;
        }
        return true;
    }

    /**
     * print balances
     */
    public void printBalance() {
        int alice = first.cur.getAmount(); 
        int bob = 0;
        Node cur = first.next;
        while (cur != null) {
            int transaction = cur.cur.getAmount();
            if (transaction > 0) {
                alice += transaction;
                bob -= transaction;
            } else {
                alice += transaction;
                bob -= transaction;
            }
            cur = cur.next;
        }
        System.out.printf("Alice: %d, Bob: %d%n", alice, bob);
    }

    /**
     * @return string representation of block chain
     */
    public String toString() {
        Node cur = first;
        String ret = "";
        while (cur != null) {
            ret += cur.cur.toString();
            ret += "\n";
            cur = cur.next;
        }
        return ret;
    }
}
