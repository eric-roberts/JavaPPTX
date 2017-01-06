/*
 * File: GrowEffect.java
 * ---------------------
 * This class implements the Grow effect.
 */

package edu.stanford.cs.pptx.effect;

import edu.stanford.cs.pptx.util.PPOutputStream;

/**
 * This emphasis effect scales the shape relative to its center.  The legal
 * options are:
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
 *     <code>/sf:</code><i>value</i>
 *     <td>Specifies the scale factor in both dimensions</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/sx:</code><i>value</i>
 *     <td>Specifies the scale factor in the <i>x</i> dimension only</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/sy:</code><i>value</i>
 *     <td>Specifies the scale factor in the <i>y</i> dimension only</td></tr>
 * </table>
 */

public class GrowEffect extends AnimationEffect {

   public GrowEffect() {
      super("Grow");
   }

   public GrowEffect(String options) {
      this();
      parseOptions(options);
   }

   public void preOptionHook() {
      sx = sy = 1.0;
      setDuration("medium");
   }

   @Override
   public String getPresetTag() {
      String tag = "presetID='6' presetClass='emph' presetSubtype='0'";
      tag += " accel='" + (accFlag ? 50000 : 0) + "'";
      tag += " decel='" + (decFlag ? 50000 : 0) + "'";
      return tag;
   }

   @Override
   public void dumpBehavior(PPOutputStream os, String delayTag) {
      os.print("<p:animScale>");
      os.print("<p:cBhvr>");
      os.print("<p:cTn id='" + os.getNextSequenceId() + "' " +
               getDurationTag() + " fill='hold'/>");
      os.print("<p:tgtEl>");
      os.print("<p:spTgt spid='" + getShape().getShapeId() + "'/>");
      os.print("</p:tgtEl>");
      os.print("</p:cBhvr>");
      os.print("<p:by x='" + (int) Math.round(100000 * sx) +
               "' y='" + (int) Math.round(100000 * sy) + "'/>");
      os.print("</p:animScale>");
   }

   public void sfKey(String value) {
      sx = sy = Double.parseDouble(value);
   }

   public void sxKey(String value) {
      sx = Double.parseDouble(value);
   }

   public void syKey(String value) {
      sy = Double.parseDouble(value);
   }

   public void accKey(String value) {
      accFlag = true;
   }

   public void decKey(String value) {
      decFlag = true;
   }

/* Private instance variables */

   private double sx;
   private double sy;
   private boolean accFlag;
   private boolean decFlag;

}
