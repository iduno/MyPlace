package com.air.advantage.cbmessages;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ByteArrayTest {

    @Test
    public void testFindString() {
        byte[] buffer = "<ack>1</ack>".getBytes(StandardCharsets.UTF_8);
        byte[] findText = "<ack>1</ack>".getBytes(StandardCharsets.UTF_8);
        int index = ByteArray.findString(buffer,0,buffer.length, findText);
        assertEquals(0, index);
    }

    @Test
    public void testFindNak() {
        byte[] buffer = "<ack>0</ack>".getBytes(StandardCharsets.UTF_8);
        int index = ByteArray.findNak(buffer);
        assertEquals(0, index);
    }

    @Test
    public void testFindAck() {
        byte[] buffer = "<ack>1</ack>".getBytes(StandardCharsets.UTF_8);
        int index = ByteArray.findAck(buffer);
        assertEquals(0, index);
    }

    @Test
    public void testParseHexValue() {
        byte[] buffer = {'a', 'f', '>', ' '};
        int value = ByteArray.parseCrcValue(3, buffer);
        assertEquals(175, value);
    }

    @Test
    public void testShiftArrayLeft() {
        byte[] buffer = {1, 2, 3, 4, 5};
        ByteArray.shiftArrayLeft(2, buffer);
        assertArrayEquals(new byte[]{3, 4, 5, 0, 0}, buffer);
    }

    @Test
    public void testGetSubBytes() {
        byte[] buffer = {1, 2, 3, 4, 5};
        byte[] subBytes = ByteArray.getSubBytes(buffer, 1, 4);
        assertArrayEquals(new byte[]{2, 3, 4}, subBytes);
    }

    @Test
    public void testGetTagContent() {
        byte[] buffer = "<tag>content</tag>".getBytes(StandardCharsets.UTF_8);
        byte[] tag = "tag".getBytes(StandardCharsets.UTF_8);
        byte[] content = ByteArray.getTagContent(buffer, tag);
        assertArrayEquals("content".getBytes(StandardCharsets.UTF_8), content);
    }

    @Test
    public void testAreArraysEqual() {
        byte[] array1 = {1, 2, 3};
        byte[] array2 = {1, 2, 3};
        assertTrue(ByteArray.areArraysEqual(array1, array2));
    }

    @Test
    public void testReplaceTagContent() {
        byte[] buffer = "<tag>oldContent</tag>".getBytes(StandardCharsets.UTF_8);
        byte[] fromTag = "tag".getBytes(StandardCharsets.UTF_8);
        byte[] toTag = "newContent".getBytes(StandardCharsets.UTF_8);
        // byte[] result = ByteArray.replaceTagContent(buffer, fromTag, toTag);
        // assertArrayEquals("<tag>newContent</tag>".getBytes(StandardCharsets.UTF_8), result);
    }

    @Test
    public void testExtractTagContentAsString() {
        byte[] buffer = "<tag>content</tag>".getBytes(StandardCharsets.UTF_8);
        byte[] tag = "tag".getBytes(StandardCharsets.UTF_8);
        String content = ByteArray.extractTagContentAsString(buffer, tag);
        assertEquals("content", content);
    }

    @Test
    public void testCopySubArray() {
        byte[] buffer = {1, 2, 3, 4, 5};
        byte[] subArray = ByteArray.copySubArray(buffer, 1, 4);
        assertArrayEquals(new byte[]{2, 3, 4}, subArray);
    }

    @Test
    public void testFindParseBlockTagStart() {
        byte[] buffer = "<U>Ping</U=db>".getBytes(StandardCharsets.UTF_8);
        int index = ByteArray.findParseBlockTagStart(buffer,0,buffer.length);

        assertEquals(0, index);

    }

    @Test
    public void testFindParseBlockTagPing() {
        byte[] buffer = "<U>Ping</U=db>".getBytes(StandardCharsets.UTF_8);
        int index = ByteArray.findParseBlockTagPing(buffer,0,buffer.length);

        assertEquals(14, index);

    }

    @Test
    public void testFindParseBlockTagEnd() {
        byte[] buffer = "<U>1234</U=10>".getBytes(StandardCharsets.UTF_8);
        int endIndex = ByteArray.findParseBlockTagEnd(buffer,0 ,buffer.length);

        assertEquals(14, endIndex);

    }
}