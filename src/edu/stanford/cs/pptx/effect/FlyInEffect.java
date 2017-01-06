/*
 * File: FlyInEffect.java
 * ----------------------
 * This class implements the FlyIn effect.
 */

package edu.stanford.cs.pptx.effect;

import edu.stanford.cs.pptx.util.PPOutputStream;

/**
 * This entrance effect moves the object in from one of the compass points.
 * The legal options are:
 *
 * <table border=0>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/onClick</code></td>
 *     <td>Effect takes place after a mouse click</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/afterPrev</code></td>
 *     <td>Effect takes place after the previous one</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/withPrev</code></td>
 *     <td>Effect takes place with the previous one</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/delay:</code><i>time</i></td>
 *     <td>Effect is delayed by <i>time</i> seconds</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/duration:</code><i>time</i></td>
 *     <td>Effect lasts <i>time</i> seconds</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/acc</code></td>
 *     <td>The effect accelerates at the beginning</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/dec</code></td>
 *     <td>The effect decelerates at the end</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromTop</code>
 *     <td>Shape flies in from the top</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromRight</code>
 *     <td>Shape flies in from the right</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromTopRight</code>
 *     <td>Shape flies in from the topright</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromBottom</code>
 *     <td>Shape flies in from the bottom</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromBottomRight</code>
 *     <td>Shape flies in from the bottomright</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromLeft</code>
 *     <td>Shape flies in from the left</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromTopLeft</code>
 *     <td>Shape flies in from the topleft</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromBottomLeft</code>
 *     <td>Shape flies in from the bottomleft</td></tr>
 * </table>
 */

public class FlyInEffect extends AnimationEffect {

   public FlyInEffect() {
      super("FlyIn");
   }

   @Override
   public String getPresetTag() {
      String tag = "presetID='2' presetClass='entr'";
      tag += " presetSubtype='" + subtype + "'";
      tag += " accel='" + (accFlag ? 50000 : 0) + "'";
      tag += " decel='" + (decFlag ? 50000 : 0) + "'";
      return tag;
   }

   @Override
   public void dumpBehavior(PPOutputStream os, String delayTag) {
      os.print("<p:set>");
      os.print("<p:cBhvr>");
      os.print("<p:cTn id='" + os.getNextSequenceId() + "' " +
               "dur='1' fill='hold'>");
      os.print("<p:stCondLst>");
      os.print("<p:cond " + delayTag + "/>");
      os.print("</p:stCondLst>");
      os.print("</p:cTn>");
      os.print("<p:tgtEl>");
      os.print("<p:spTgt spid='" + getShape().getShapeId() + "'/>");
      os.print("</p:tgtEl>");
      os.print("<p:attrNameLst>");
      os.print("<p:attrName>style.visibility</p:attrName>");
      os.print("</p:attrNameLst>");
      os.print("</p:cBhvr>");
      os.print("<p:to><p:strVal val='visible'/></p:to>");
      os.print("</p:set>");
      os.print("<p:anim calcmode='lin' valueType='num'>");
      os.print("<p:cBhvr additive='base'>");
      os.print("<p:cTn id='" + os.getNextSequenceId() + "' " +
               getDurationTag() + " fill='hold'/>");
      os.print("<p:tgtEl>");
      os.print("<p:spTgt spid='" + getShape().getShapeId() + "'/>");
      os.print("</p:tgtEl>");
      os.print("<p:attrNameLst>");
      os.print("<p:attrName>ppt_x</p:attrName>");
      os.print("</p:attrNameLst>");
      os.print("</p:cBhvr>");
      os.print("<p:tavLst>");
      os.print("<p:tav tm='0'>");
      os.print("<p:val>");
      os.print("<p:strVal val='" + startX + "'/>");
      os.print("</p:val>");
      os.print("</p:tav>");
      os.print("<p:tav tm='100000'>");
      os.print("<p:val>");
      os.print("<p:strVal val='#ppt_x'/>");
      os.print("</p:val>");
      os.print("</p:tav>");
      os.print("</p:tavLst>");
      os.print("</p:anim>");
      os.print("<p:anim calcmode='lin' valueType='num'>");
      os.print("<p:cBhvr additive='base'>");
      os.print("<p:cTn id='" + os.getNextSequenceId() + "' " +
               getDurationTag() + " fill='hold'/>");
      os.print("<p:tgtEl>");
      os.print("<p:spTgt spid='" + getShape().getShapeId() + "'/>");
      os.print("</p:tgtEl>");
      os.print("<p:attrNameLst>");
      os.print("<p:attrName>ppt_y</p:attrName>");
      os.print("</p:attrNameLst>");
      os.print("</p:cBhvr>");
      os.print("<p:tavLst>");
      os.print("<p:tav tm='0'>");
      os.print("<p:val>");
      os.print("<p:strVal val='" + startY + "'/>");
      os.print("</p:val>");
      os.print("</p:tav>");
      os.print("<p:tav tm='100000'>");
      os.print("<p:val>");
      os.print("<p:strVal val='#ppt_y'/>");
      os.print("</p:val>");
      os.print("</p:tav>");
      os.print("</p:tavLst>");
      os.print("</p:anim>");
   }

   public void preOptionHook() {
      setDuration("veryFast");
      fromBottomKey(null);
   }

   public void fromTopKey(String value) {
      startX = "#ppt_x";
      startY = "0-#ppt_h/2";
      subtype = 1;
   }

   public void fromRightKey(String value) {
      startX = "1+#ppt_w/2";
      startY = "#ppt_y";
      subtype = 2;
   }

   public void fromTopRightKey(String value) {
      startX = "1+#ppt_w/2";
      startY = "0-#ppt_h/2";
      subtype = 3;
   }

   public void fromBottomKey(String value) {
      startX = "#ppt_x";
      startY = "1+#ppt_h/2";
      subtype = 4;
   }

   public void fromBottomRightKey(String value) {
      startX = "1+#ppt_w/2";
      startY = "1+#ppt_h/2";
      subtype = 6;
   }

   public void fromLeftKey(String value) {
      startX = "0-#ppt_w/2";
      startY = "#ppt_y";
      subtype = 8;
   }

   public void fromTopLeftKey(String value) {
      startX = "0-#ppt_w/2";
      startY = "0-#ppt_h/2";
      subtype = 9;
   }

   public void fromBottomLeftKey(String value) {
      startX = "0-#ppt_w/2";
      startY = "1+#ppt_h/2";
      subtype = 12;
   }

   public void accKey(String value) {
      accFlag = true;
   }

   public void decKey(String value) {
      decFlag = true;
   }

/* Private instance variables */

   private String startX;
   private String startY;
   private boolean accFlag;
   private boolean decFlag;
   private int subtype;

}
