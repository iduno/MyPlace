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
        byte[] inputData = "<U>Ping</U=db> ".getBytes();
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
}