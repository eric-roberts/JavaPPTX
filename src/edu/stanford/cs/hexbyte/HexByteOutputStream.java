/*
 * File: HexByteOutputStream.java
 * ------------------------------
 * This file exports a class for writing binary data as ASCII hex bytes.
 */

package edu.stanford.cs.hexbyte;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * This class creates an <code>OutputStream</code> subclass that
 * converts data to hex bytes.
 */

public class HexByteOutputStream extends OutputStream {

/**
 * Creates an <code>OutputStream</code> subclass that converts data to
 * hex bytes.
 *
 * @param printStream The underlying <code><b>PrintStream<b></code> to
 *                    which data is written
 */

   public HexByteOutputStream(PrintStream printStream) {
      out = printStream;
      out.flush();
      column = 0;
      lineWidth = DEFAULT_LINE_WIDTH;
      stopPattern = null;
      patternCount = 0;
   }

/**
 * Sets a pattern of bytes at which the stream should stop
 * by throwing an EOFException.  This method is used as a
 * debugging aide.
 *
 * @param pattern An array of bytes that serves as the stop pattern
 */

   public void setLineWidth(int nChars) {
      lineWidth = nChars;
   }

/**
 * Sets a pattern of bytes at which the stream should stop
 * by throwing an EOFException.  This method is used as a
 * debugging aide.
 *
 * @param pattern An array of bytes that serves as the stop pattern
 */

   public void setStopPattern(byte[] pattern) {
      stopPattern = pattern;
   }

/** Writes a single byte to the output stream */

   public void write(int b) throws IOException {
      if (stopPattern != null) {
         if (b == stopPattern[patternCount]) {
            patternCount++;
            if (patternCount == stopPattern.length) {
               out.flush();
               out.println();
               throw new EOFException("Pattern seen");
            }
         } else {
            patternCount = 0;
         }
      }
      String hex = Integer.toHexString(0x100 + (b & 0xFF)).toUpperCase();
      out.write(hex.charAt(1));
      out.write(hex.charAt(2));
      column += 2;
      if (lineWidth > 0 && column >= lineWidth) {
         column = 0;
         out.println();
      }
   }

/** Flushes data in the output stream */

   public void flush() {
      column = 0;
      out.flush();
   }

/** Closes the output stream */

   public void close() {
      out.close();
   }

/* Private constants */

   private static final int DEFAULT_LINE_WIDTH = 76;

/* Private instance variables */

   private int column;
   private int lineWidth;
   private PrintStream out;
   private byte[] stopPattern;
   private int patternCount;

}
