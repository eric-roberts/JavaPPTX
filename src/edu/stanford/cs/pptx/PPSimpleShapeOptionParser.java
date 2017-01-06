/*
 * File: PPSimpleShapeOptionParser.java
 * ------------------------------------
 * This class implements the common option parser for simple shapes.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.util.PPUtil;
import java.awt.Color;

class PPSimpleShapeOptionParser extends PPShapeOptionParser {

   public PPSimpleShapeOptionParser(PPSimpleShape obj) {
      super(obj);
      target = obj;
   }

   public void colorKey(String str) {
      fillColorKey(str);
      lineColorKey(str);
   }

   public void fillKey(String str) {
      fillColorKey(str);
   }

   public void fillColorKey(String str) {
      Color color = null;
      if (!str.equals("none")) color = PPUtil.decodeColor(str);
      target.setFillColor(color);
   }

   public void lineKey(String str) {
      lineColorKey(str);
   }

   public void lineColorKey(String str) {
      Color color = null;
      if (!str.equals("none")) color = PPUtil.decodeColor(str);
      target.setLineColor(color);
   }

   public void weightKey(String str) {
      lineWeightKey(str);
   }

   public void lineWeightKey(String str) {
      target.setLineWeight(Double.parseDouble(str));
   }

   public void arrowKey(String str) {
      target.setEndArrow(str);
   }

   public void endArrowKey(String str) {
      target.setEndArrow(str);
   }

   public void startArrowKey(String str) {
      target.setStartArrow(str);
   }

/* Private instance variables */

   private PPSimpleShape target;

}
