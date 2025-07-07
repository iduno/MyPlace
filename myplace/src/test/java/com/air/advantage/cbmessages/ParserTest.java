package com.air.advantage.cbmessages;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ParserTest {

    @Test
    public void testParseValidMessage() {
        // Simulate a valid input stream with a message
        byte[] inputData = "<U>CAN2 in use</U=95> ".getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);

        Parser parser = new Parser(inputStream);
        parser.parse();

        Message message = parser.pollMessage();
        assertNotNull(message);
        assertTrue(message.getCrcValid());
    }

    @Test
    public void testParsePingMessage() {
        // Simulate a ping message in the input stream
        byte[] inputData = "<U>Ping</U=db> ".getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);

        Parser parser = new Parser(inputStream);
        parser.parse();

        Message message = parser.pollMessage();
        assertNotNull(message);
        assertTrue(message instanceof MessagePing);
    }

    @Test
    public void testParseInvalidMessage() {
        // Simulate an invalid input stream
        byte[] inputData = "<U>Ping</U=db> <U>InvalidMessage</U=ff> <U>setCAN 1 0701000000600000000000000 0701000000600000000000000 </U=5a> ".getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);

        Parser parser = new Parser(inputStream);
        parser.parse();

        Message message = parser.pollMessage();
        assertNotNull(message);
        message = parser.pollMessage();
        assertNotNull(message);
        assertFalse(message.getCrcValid());
        message = parser.pollMessage();
        assertNotNull(message);
    }

    @Test
    public void testSetParseInitMessage() {
        // Simulate an invalid input stream
        byte[] inputData = "<U>setCAN 0201000000000360000000000 0201000000236000000000000 0801000000600000000000000 0801000000236000000000000 0801123450700000000000000 0701123450700000000000000 </U=ef> ".getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);

        Parser parser = new Parser(inputStream);
        parser.parse();
        parser.parse();
        parser.parse();

        Message message = parser.pollMessage();
        assertNotNull(message);
    }


    @Test
    public void testParseInitMessage() {
        // Simulate an invalid input stream
        byte[] inputData = "<U>getCAN 1 0703111110a00000000000000 0703111110211000000000000 070311111030464012c120800 0703111110301e4012c130300 070311111030264012c120400 070311111030364012c110800 070311111030564012c130200 070311111030664012e120100 070311111030764012c120900 0703111110404005000010029 0703111110800000000000000 0703111110405005000010027 0703111110308640030000000 0703111110309640030000000 070311111030a640030000000 070311111040200640001002c 0703111110401006400010028 070311111010e070101000001 070311111040600500001002f 0703111110403006400010028 070311111040700500001002c </U=16> ".getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);

        Parser parser = new Parser(inputStream);
        parser.parse();
        parser.parse();
        parser.parse();

        Message message = parser.pollMessage();
        assertNotNull(message);
    }
}