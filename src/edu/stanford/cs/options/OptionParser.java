/*
 * File: OptionParser.java
 * -----------------------
 * This class creates a simple options parser used throughout the
 * Stanford packages.
 */

package edu.stanford.cs.options;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class implements a simple tool for parsing key/value pairs.
 * The options are specified as a string consisting of a sequence of
 * option specifications separated by whitespace.  Each option
 * specification takes one of the following forms:
 *
 * <ol>
 * <li>/key
 * <li>/key:value
 * </ol>
 *
 * The first form sets the value associated with the key to
 * the empty string; the second supplies the value explicitly.
 * In the second form, the value string may be enclosed in either
 * single or double quotation marks if it contains a slash, a
 * colon, or whitespace.  The slash in front of the first key
 * may be omitted.  All keys are case-sensitive.
 *
 * Clients who wish to use the <code>OptionParser</code> class
 * must create a subclass that defines public methods for each
 * of the possible keys, where the name of the key method is the
 * name of the key followed by the suffix <code>"Key"</code>.
 * When executing the <code>parseOptions</code> method, the
 * class uses reflection to invoke those functions in the subclass
 * object, passing in the value string as a parameter.  If no method
 * is found for a particular key, the <code>parseOptions</code>
 * method invokes
 *
 *<pre>
 *    undefinedKey(key, value)
 *</pre>
 *
 * <p>The base class implementation of <code>undefinedKey</code>
 * simply reports an error.  Subclasses can override this to
 * perform some other semantic checks for valid keys.
 */

public abstract class OptionParser {

/**
 * Parses the option string, invoking the callbacks on this object.
 *
 * @param str The option string to be parsed
 */

   public void parseOptions(String str) {
      preOptionHook();
      try {
         StreamTokenizer tokenizer = createTokenizer(str);
         int ttype = tokenizer.nextToken();
         while (ttype != StreamTokenizer.TT_EOF) {
            if (ttype == '/') {
               ttype = tokenizer.nextToken();
            }
            if (ttype != StreamTokenizer.TT_WORD) {
               String msg = "Illegal key in option string: " + str;
               throw new RuntimeException(msg);
            }
            String key = tokenizer.sval;
            String value = "";
            ttype = tokenizer.nextToken();
            if (ttype == ':') {
               ttype = tokenizer.nextToken();
               if (ttype != StreamTokenizer.TT_WORD &&
                   ttype != '"' && ttype != '\'') {
                  String msg = "Illegal key in option string: " + str;
                  throw new RuntimeException(msg);
               }
               value = tokenizer.sval;
               ttype = tokenizer.nextToken();
            }
            invokeKey(key, value);
         }
      } catch (IOException ex) {
         throw new RuntimeException("Illegal option string: " + str);
      }
      postOptionHook();
   }

/**
 * Allows subclasses to specify processing that is done before
 * the options are parsed.  Subclasses should call this method
 * in their superclass to ensure that all processing is done.
 */

   public void preOptionHook() {
      /* Empty */
   }

/**
 * Allows subclasses to specify processing that is done after
 * the options are parsed.  Subclasses should call this method
 * in their superclass to ensure that all processing is done.
 */

   public void postOptionHook() {
      /* Empty */
   }

/**
 * Invokes a key in the target class using reflection.  Any
 * package-private classes that extend OptionParser must copy
 * this method to ensure that the reflection does not get
 * blocked by the package-private check.
 */

   public void invokeKey(String key, String value) {
      Class<?>[] types = { String.class };
      Object[] args = { value };
      try {
         Method fn = getClass().getMethod(key + "Key", types);
         fn.invoke(this, args);
      } catch (NoSuchMethodException ex) {
         undefinedKey(key, value);
      } catch (InvocationTargetException ex) {
         throw new RuntimeException(ex);
      } catch (IllegalAccessException ex) {
         throw new RuntimeException(ex);
      }
   }

/**
 * Handles a key that is undefined in the subclass.  Subclasses
 * can override this method to handle errors differently or to
 * use other approaches for determining a valid key.
 *
 * @param key The key in the key/value pair
 * @param value The corresponding value string
 */

   public void undefinedKey(String key, String value) {
      throw new RuntimeException("Undefined key: " + key);
   }

/**
 * Creates a tokenizer for the specified string.
 */

   private StreamTokenizer createTokenizer(String str) {
      StreamTokenizer t = new StreamTokenizer(new StringReader(str));
      t.resetSyntax();
      t.wordChars((char) 33, (char) 126);
      t.ordinaryChar(':');
      t.ordinaryChar('/');
      t.quoteChar('"');
      t.quoteChar('\'');
      t.whitespaceChars(' ', ' ');
      t.whitespaceChars('\t', '\t');
      return t;
   }

}
