/*
 * File: PPTextShapeOptionParser.java
 * ----------------------------------
 * This class implements the common option parser for text shapes.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.util.PPUtil;

class PPTextShapeOptionParser extends PPSimpleShapeOptionParser {

   public PPTextShapeOptionParser(PPTextShape obj) {
      super(obj);
      target = obj;
   }

   public void fontKey(String str) {
      target.setFont(str);
   }

   public void fontColorKey(String str) {
      target.setFontColor(PPUtil.decodeColor(str));
   }

   public void leftKey(String str) {
      target.setHorizontalAlignment("Left");
   }

   public void rightKey(String str) {
      target.setHorizontalAlignment("Right");
   }

   public void centerKey(String str) {
      target.setHorizontalAlignment("Center");
   }

   public void justifyKey(String str) {
      target.setHorizontalAlignment("Justify");
   }

   public void alignKey(String str) {
      target.setHorizontalAlignment(str);
   }

   public void topKey(String str) {
      target.setVerticalAlignment("Top");
   }

   public void middleKey(String str) {
      target.setVerticalAlignment("Middle");
   }

   public void bottomKey(String str) {
      target.setVerticalAlignment("Bottom");
   }

   public void valignKey(String str) {
      target.setVerticalAlignment(str);
   }

   public void leftMarginKey(String str) {
      target.setLeftMargin(Float.parseFloat(str));
   }

   public void rightMarginKey(String str) {
      target.setRightMargin(Float.parseFloat(str));
   }

   public void topMarginKey(String str) {
      target.setTopMargin(Float.parseFloat(str));
   }

   public void bottomMarginKey(String str) {
      target.setBottomMargin(Float.parseFloat(str));
   }

   public void wrapKey(String str) {
      target.setWordWrapFlag(true);
   }

   public void nowrapKey(String str) {
      target.setWordWrapFlag(false);
   }

/* Private instance variables */

   private PPTextShape target;

}
