package edu.grinnell.csc207;

import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.NullKeyException;
import edu.grinnell.csc207.util.KeyNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.security.KeyPair;
import org.junit.jupiter.api.Test;

/**
 * A place for you to put your own tests (beyond the shared repo).
 *
 * @author Sara Jaljaa
 */
public class TestsFromStudent {
  /**
   * A simple test.
   */
  @Test
  public void alwaysPass() throws Exception {
    AssociativeArray<String, Integer> test = new AssociativeArray<String, Integer>();
    AssociativeArray<String, Integer> clonedtest = test.clone();
  } // alwaysPass()

  /**
   * Checks that a key can be removed anywhere from the array.
   *
   * @throws KeyNotFoundException
   *     When searching for a key that does not exist in the array.
   *
   * @throws NullKeyException
   *     When searching for a key whose value is null.
   */
  @Test
  public void jaljaaSaraTest1() throws KeyNotFoundException, NullKeyException {
    AssociativeArray<String, String> stringArr = new AssociativeArray<String, String>();

    // Set keys
    stringArr.set("a", "apple");
    stringArr.set("b", "bones");
    stringArr.set("c", "corn");
    stringArr.set("d", "digeridoo");
    stringArr.set("e", "encephalitis");

    // Remove keys from center, beginning, and end
    stringArr.remove("c");
    stringArr.remove("e");
    stringArr.remove("a");

    assertFalse(stringArr.hasKey("c"), "\"c\" is not an existing key.");
    assertFalse(stringArr.hasKey("e"), "\"e\" is not an existing key.");
    assertFalse(stringArr.hasKey("a"), "\"a\" is not an existing key.");
    assertEquals(2, stringArr.size(), "Correct size of array after removed is 2.");
  } // jaljaaSaraTest1()

  /**
   * Checks that the pair value of a key has been correctly set.
   *
   * @throws NullKeyException
   *     When searching for a key whose value is null.
   */
  @Test
  public void jaljaaSaraTest2() throws NullKeyException, KeyNotFoundException {
    AssociativeArray<Character, String> charStringArr = new AssociativeArray<Character, String>();
    AssociativeArray<String, String> strStringArr = new AssociativeArray<String, String>();
    AssociativeArray<Integer, String> integerStringArr = new AssociativeArray<Integer, String>();

    // Set single non-alphabetical character keys
    charStringArr.set('\0', "c terminate");
    charStringArr.set('\r', "line return");
    charStringArr.set('\b', "");

    // Set string keys that contain substrings of each other
    strStringArr.set("substringtest", "whole string");
    strStringArr.set("sub", "small substring");
    strStringArr.set("string", "medium substring");
    strStringArr.set("substringtes", "large substring");

    // Set integer keys
    integerStringArr.set(0, "zero");
    integerStringArr.set(2, "two");
    integerStringArr.set(500, "five hundred");
    integerStringArr.set(52, "fifty-two");
    integerStringArr.set(18, "eighteen");

    assertEquals("c terminate", charStringArr.get('\0'));
    assertEquals("", charStringArr.get('\b'));
    assertEquals("whole string", strStringArr.get("substringtest"));
    assertEquals("large substring", strStringArr.get("substringtes"));
    assertEquals("zero", integerStringArr.get(0));
    assertEquals("eighteen", integerStringArr.get(18));
  } // jaljaaSaraTest2()

  /**
   * Tests for integer wraparound in key values that are greater than
   * the byte representation range of integers.
   *
   * @throws KeyNotFoundException
   *     When searching for a key that does not exist in the array.
   *
   * @throws NullKeyException
   *     When searching for a key whose value is null.
   */
  @Test
  public void jaljaaSaraEdge1() throws KeyNotFoundException, NullKeyException {
  AssociativeArray<Integer, String> keyFlow = new AssociativeArray<Integer, String>();

    // Set key with different values that represent the same overflow.
    keyFlow.set(Integer.MIN_VALUE, "OVERFLOW");
    keyFlow.set(Integer.MAX_VALUE + 1, "overflow");

    // Set key with different values that represent the same underflow.
    keyFlow.set(Integer.MAX_VALUE, "UNDERFLOW");
    keyFlow.set(Integer.MIN_VALUE - 1, "underflow");

    assertEquals(
        "overflow",
        keyFlow.get(Integer.MAX_VALUE + 1),
        "Key overflow, wrapped around");
    assertEquals(
        "underflow",
        keyFlow.get(Integer.MIN_VALUE - 1),
        "Key underflow, wrapped around");
  } // jaljaaSaraEdge1()
} // class TestsFromStudent
