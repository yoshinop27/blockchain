package edu.grinnell.csc207.blockchain;

import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    private byte[] value;

    /**
     * Construct a Hash object from a byte array
     * @param data a byte array
     */
    public Hash(byte[] data) {
        this.value = data;
    }

    /**
     * Get byte array representing hash value
     * @return byte array
     */
    public byte[] getData() {
        return this.value;
    }

    /**
     * Check if we have a valid hash value
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        for (int i = 0; i < 3; i++) {
            if (this.value[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cite: https://stackoverflow.com/questions/8689526
     * Returns a string representation of the hash value in hexadecimal format
     * @return hexadecimal string representation
     */
    public String toString() {
        String ret = "";
        for (byte b: this.value) {
            int temp = Byte.toUnsignedInt(b);
            ret += String.format("%02x", temp);
        }
        return ret;
    }

    /**
     * Check if two Hash objects are equal
     * @param other another object
     * @return true if equal, false otherwise
     */
    public boolean equals(Object other) {
        if (!(other instanceof Hash)) {
            return false;
        }
        Hash o = (Hash) other;
        return Arrays.equals(this.value, o.value);
    }
}
