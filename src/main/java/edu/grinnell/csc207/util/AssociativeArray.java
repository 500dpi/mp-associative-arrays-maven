package edu.grinnell.csc207.util;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Sara Jaljaa
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V>[] pairs;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   *
   * @return a new copy of the array
   */
  public AssociativeArray<K, V> clone() {

    // Set the pair and size fields of the cloned array to
    // match the new array

    AssociativeArray<K, V> cloned = new AssociativeArray<K, V>();
    cloned.pairs = new KVPair[this.pairs.length];
    cloned.size = this.size;

    // Set each individual pair of the cloned array to
    // match the original array
    for (int i = 0; i < cloned.size; i++) {
      if (cloned.pairs[i] != null) {
        cloned.pairs[i] = this.pairs[i];
      } // for
    } // for
    return cloned;
  } // clone()

  /**
   * Convert the array to a string.
   *
   * @return a string of the form "{Key0:Value0, Key1:Value1, ... KeyN:ValueN}"
   */
  public String toString() {
    String concat = "{";

    // If the array is not empty, create a string of all the
    // key/pair values
    if (size > 0) {
      for (int i = 0; i < this.pairs.length - 1; i++) {
        concat += this.pairs[i].toString() + ", ";
      } // for
      concat += this.pairs[this.pairs.length - 1].toString();
    } // if
    return concat + "}";
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   *
   * @param key
   *   The key whose value we are setting.
   * @param value
   *   The value of that key.
   *
   * @throws NullKeyException
   *   If the client provides a null key.
   */
  public void set(K key, V value) throws NullKeyException, KeyNotFoundException {
    int keyIndex;

    // If the key is null, return a null key exception
    if (key == null) {
      throw new NullKeyException();
    } // if

    // Try to find the key in the array; if it exists, update the value of the key
    // with the new value
    try {
      keyIndex = this.find(key);
      this.pairs[keyIndex].val = value;

    // If the key does not exist in the array, add it to the end of the array
    // & increase the array's size
    } catch (KeyNotFoundException e) {
      if (this.size <= this.pairs.length) {
        this.expand();
      } // if
      this.pairs[size] = new KVPair<K, V>(key, value);
      this.size++;
    } // try/catch
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @param key
   *   A key
   *
   * @return
   *   The corresponding value
   *
   * @throws KeyNotFoundException
   *   when the key is null or does not appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    int index = this.find(key);
    return this.pairs[index].val;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key, since it cannot appear.
   *
   * @param key
   *   The key we're looking for.
   *
   * @return true if the key appears and false otherwise.
   */
  public boolean hasKey(K key) {
    // If the key is found in the array, the array has
    // the key; otherwise, the key is not in the array
    try {
      if (key != null) {
        this.find(key);
        return true;
      } else {
        return false;
      } // if/else
    } catch (KeyNotFoundException e) {
      return false;
    } // try/catch
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   *
   * @param key
   *   The key to remove.
   */
  public void remove(K key) {
    // Check if the key is in the array
    if (hasKey(key)) {
      try {
        int index = this.find(key);
        this.size--;

        // If the key is in the array, set all the pair indices back by one
        // starting at the next value from the value to be removed
        for (int i = index; i < this.size; i++) {
          this.pairs[i] = this.pairs[i + 1];
        } // for
        this.pairs[size] = null;

      // Do nothing if the key is not in the array
      } catch (KeyNotFoundException e) {
        return;
      } // try/catch
    } // if
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   *
   * @return The number of key/value pairs in the array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   *
   * @param key
   *   The key of the entry.
   *
   * @return
   *   The index of the key, if found.
   *
   * @throws KeyNotFoundException
   *   If the key does not appear in the associative array.
   */
  int find(K key) throws KeyNotFoundException {
    // Iterate through the array to find the key and return
    // its index of occurence; if not in the array, throw an
    // exception
    for (int i = 0; i < this.size; i++) {
      if (this.pairs[i].key.equals(key)) {
        return i;
      } // if
    } // for
    throw new KeyNotFoundException();
  } // find(K)
} // class AssociativeArray
