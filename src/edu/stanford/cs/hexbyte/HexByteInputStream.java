/*
 * File: HexByteInputStream.java
 * -----------------------------
 * This file exports a class for reading binary data from a source
 * of ASCII hex bytes.
 */

package edu.stanford.cs.hexbyte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

public class HexByteInputStream extends InputStream {

   public HexByteInputStream(String str) {
      this(new StringReader(str));
   }

   public HexByteInputStream(InputStream in) {
      this(new InputStreamReader(in));
   }

   public HexByteInputStream(Reader reader) {
      rd = reader;
   }

   public int read() throws IOException {
      int d1 = readHexDigit();
      if (d1 == -1) return -1;
      int d2 = readHexDigit();
      if (d2 == -1) {
         throw new UnsupportedEncodingException("Odd number of hex digits");
      }
      return d1 << 4 | d2;
   }

   public static byte[] toByteArray(String str) {
      HexByteInputStream in = new HexByteInputStream(str);
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try {
         while (true) {
            int ch = in.read();
            if (ch == -1) break;
            out.write(ch);
         }
         in.close();
      } catch (IOException ex) {
         throw new RuntimeException(ex.getMessage());
      }
      return out.toByteArray();
   }

   private int readHexDigit() throws IOException {
      while (true) {
         int ch = rd.read();
         if (ch == -1) return -1;
         int index = "0123456789ABCDEF".indexOf(Character.toUpperCase(ch));
         if (index >= 0) return index;
         if (!Character.isWhitespace(ch)) {
            throw new UnsupportedEncodingException("Illegal character " + ch);
         }
      }
   }
/* Private instance variables */

   private Reader rd;

}
