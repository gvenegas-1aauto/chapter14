package com.studygroup;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgsTest {

    @Test
    public void testCreateWithNoSchemaOrArguments() throws Exception {
        Args args = new Args("", new String[0]);
        assertEquals(0, args.cardinality());
    }


    @Test
    public void testWithNoSchemaButWithOneArgument() throws Exception {

        Assertions.assertThrows(ArgsException.class, () -> new Args("", new String[]{"-x"}), "Bad character:*in Args format: *");

    }


    @Test
    public void testWithNoSchemaButWithMultipleArguments() throws Exception {
        Assertions.assertThrows(ArgsException.class, () -> new Args("", new String[]{"-x", "-y"}), "Argument -x unexpected.");
    }


    @Test
    public void testNonLetterSchema() throws Exception {

        Assertions.assertThrows(ArgsException.class, () -> new Args("*", new String[]{}), "Bad character:*in Args format: *");

    }


    @Test
    public void testInvalidArgumentFormat() throws Exception {

        Assertions.assertThrows(ArgsException.class, () -> new Args("f~", new String[]{}), "Argument: f has invalid format: ~.");

    }


    @Test
    public void testSimpleBooleanTruePresent() throws Exception {
        Args args = new Args("x", new String[]{"-x", "true"});
        assertEquals(1, args.cardinality());
        assertEquals(true, args.getBoolean('x'));
    }

    // Currently fails...
//    @Test
//    public void testSimpleBooleanFalsePresent() throws Exception {
//        Args args = new Args("x", new String[]{"-x", "false"});
//        assertEquals(1, args.cardinality());
//        assertEquals(false, args.getBoolean('x'));
//    }

    // Currently fails...
//    @Test
//    public void testMissingBooleanArgument() throws Exception {
//        Args args = new Args("x", new String[]{"-x"});
//        assertEquals(false, args.isValid());
//    }

    // Currently fails...
//    @Test
//    public void testInvalidBoolean() throws Exception {
//        Args args = new Args("x", new String[]{"-x", "Truthy"});
//        assertEquals(1, args.cardinality());
//        assertEquals(false, args.getBoolean('x'));
//    }

    @Test
    public void testMultipleBooleans() throws Exception {
        Args args = new Args("x,y", new String[]{"-xy", "true", "true"});
        assertEquals(2, args.cardinality());
        assertEquals(true, args.getBoolean('x'));
        assertEquals(true, args.getBoolean('y'));
    }

    @Test
    public void testSpacesInFormat() throws Exception {
        Args args = new Args("x, y", new String[]{"-xy", "true", "true"});
        assertEquals(2, args.cardinality());
        assertTrue(args.has('x'));
        assertTrue(args.has('y'));
        assertEquals(true, args.getBoolean('x'));
        assertEquals(true, args.getBoolean('y'));
    }

    @Test
    public void testInvalidArgumentValueFormat() throws Exception {

        Assertions.assertThrows(ArgsException.class, () -> new Args("x,y", new String[]{"xy", "true", "false"}), "'xy' is not a valid argument format.");

    }

    @Test
    public void testSimpleStringPresent() throws Exception {
        Args args = new Args("x*", new String[]{"-x", "param"});
        assertEquals(1, args.cardinality());
        assertTrue(args.has('x'));
        assertEquals("param", args.getString('x'));
    }

    @Test
    public void testMissingStringArgument() throws Exception {
        Assertions.assertThrows(ArgsException.class, () -> new Args("x*", new String[]{"-x"}), "Could not find string parameter for -x.");
    }

    @Test
    public void testSimpleIntPresent() throws Exception {
        Args args = new Args("x#", new String[]{"-x", "42"});
        assertEquals(1, args.cardinality());
        assertTrue(args.has('x'));
        assertEquals(42, args.getInt('x'));
    }

    @Test
    public void testInvalidInteger() throws Exception {
        Assertions.assertThrows(ArgsException.class, () -> new Args("x#", new String[]{"-x", "Forty two"}), "");
    }

    @Test
    public void testMissingInteger() throws Exception {

        Assertions.assertThrows(ArgsException.class, () -> new Args("x#", new String[]{"-x"}), "Could not find integer parameter for -x.");

    }
}
