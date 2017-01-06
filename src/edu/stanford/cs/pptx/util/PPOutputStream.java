/*
 * File: PPOutputStream.java
 * -------------------------
 * This class extends ZipOutputStream to support writing .pptx files.
 */

package edu.stanford.cs.pptx.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

public class PPOutputStream extends ZipOutputStream {

   public PPOutputStream(File file) {
      super(createStream(file));
      offsetX = 0;
      offsetY = 0;
   }

   public void print(String str) {
      try {
         write(str.getBytes("UTF-8"));
      } catch (IOException ex) {
         throw new RuntimeException(ex.toString());
      }
   }

   public void println() {
      try {
         write('\n');
      } catch (IOException ex) {
         throw new RuntimeException(ex.toString());
      }
   }

   public void println(String str) {
      print(str);
      println();
   }

   public double getXOffset() {
      return offsetX;
   }

   public double getYOffset() {
      return offsetY;
   }

   public void adjustOffset(double dx, double dy) {
      offsetX += dx;
      offsetY += dy;
   }

   public String getOffsetTag(double x, double y) {
      return "x='" + PPUtil.pointsToUnits(x + offsetX) +
             "' y='" + PPUtil.pointsToUnits(y + offsetY) + "'";
   }

   public void resetIdSequence() {
      grpId = 0;
      seqId = 1;
   }

   public int getNextSequenceId() {
      return ++seqId;
   }

   public int getNextGroupId() {
      return ++grpId;
   }

/* Private methods */

   private static OutputStream createStream(File file) {
      try {
         return new BufferedOutputStream(new FileOutputStream(file));
      } catch (IOException ex) {
         throw new RuntimeException(ex.toString());
      }
   }

/* Private instance variables */

   private double offsetX;
   private double offsetY;
   private int grpId;
   private int seqId;

}
