/*
 * File: SpinEffect.java
 * ---------------------
 * This class implements the Spin effect.
 */

package edu.stanford.cs.pptx.effect;

import edu.stanford.cs.pptx.util.PPOutputStream;

/**
 * This emphasis effect spins the shape around its center.  The legal
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
 *     <code>/angle:</code><i>degrees
 *     <td>Specifies the number of degrees in the rotation</td></tr>
 * </table>
 */

public class SpinEffect extends AnimationEffect {

   public SpinEffect() {
      super("Spin");
   }

   public SpinEffect(String options) {
      this();
      parseOptions(options);
   }

   public void preOptionHook() {
      angle = 360;
      setDuration("medium");
   }

   @Override
   public String getPresetTag() {
      String tag = "presetID='8' presetClass='emph' presetSubtype='0'";
      tag += " accel='" + (accFlag ? 50000 : 0) + "'";
      tag += " decel='" + (decFlag ? 50000 : 0) + "'";
      return tag;
   }

   @Override
   public void dumpBehavior(PPOutputStream os, String delayTag) {
      os.print("<p:animRot by='" + (int) Math.round(60000 * angle) + "'>");
      os.print("<p:cBhvr>");
      os.print("<p:cTn id='" + os.getNextSequenceId() + "' " +
               getDurationTag() + " fill='hold'>");
      os.print("<p:stCondLst>");
      os.print("<p:cond " + delayTag + "/>");
      os.print("</p:stCondLst>");
      os.print("</p:cTn>");
      os.print("<p:tgtEl>");
      os.print("<p:spTgt spid='" + getShape().getShapeId() + "'/>");
      os.print("</p:tgtEl>");
      os.print("<p:attrNameLst>");
      os.print("<p:attrName>r</p:attrName>");
      os.print("</p:attrNameLst>");
      os.print("</p:cBhvr>");
      os.print("</p:animRot>");
   }

   public void angleKey(String value) {
      angle = Double.parseDouble(value);
   }

   public void accKey(String value) {
      accFlag = true;
   }

   public void decKey(String value) {
      decFlag = true;
   }

/* Private instance variables */

   private double angle;
   private boolean accFlag;
   private boolean decFlag;

}
