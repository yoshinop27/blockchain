package edu.grinnell.csc207.blockchain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Tests {
    // TODO: fill me in with tests that you write for this project!
    // cite https://stackoverflow.com/questions/11208479/how-do-i-initialize-a-byte-array-in-java

    @Test
    public void testToString() {
        byte[] data = { 0x00, 0x0A, 0x7F, 0x01 };
        Hash h = new Hash(data);
        assertEquals("000a7f01", h.toString());
    }

    @Test
    public void testIsValidTrue() {
        byte[] data = { 0x00, 0x00, 0x00, 0x01 };
        Hash h = new Hash(data);
        assertEquals(true, h.isValid());
    }

    @Test
    public void testIsValidFalse() {
        byte[] data = { 0x01, 0x00, 0x00, 0x01 };
        Hash h = new Hash(data);
        assertEquals(false, h.isValid());
    }

    @Test
    public void testEqualsSame() {
        byte[] data1 = { 0x01, 0x02, 0x03 };
        byte[] data2 = { 0x01, 0x02, 0x03 };
        Hash h1 = new Hash(data1);
        Hash h2 = new Hash(data2);
        assertEquals(true, h1.equals(h2));
    }

    @Test
    public void testEqualsDifferent() {
        byte[] data1 = { 0x01, 0x02, 0x03 };
        byte[] data2 = { 0x01, 0x02, 0x04 };
        Hash h1 = new Hash(data1);
        Hash h2 = new Hash(data2);
        assertEquals(false, h1.equals(h2));
    }
}
