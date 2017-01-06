/*
 * File: WipeOutEffect.java
 * ------------------------
 * This class implements the wipe exit effect.
 */

package edu.stanford.cs.pptx.effect;

import edu.stanford.cs.pptx.util.PPOutputStream;

/**
 * This exit effect causes the shape to disappear gradually from one side.
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
 *     <code>/fromLeft</code></td>
 *     <td>The shape disappears from left to right</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromRight</code></td>
 *     <td>The shape disappears from right to left</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromTop</code></td>
 *     <td>The shape disappears from top to bottom</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromBottom</code></td>
 *     <td>The shape disappears from bottom to top</td></tr>
 * </table>
 */

public class WipeOutEffect extends AnimationEffect {

   public WipeOutEffect() {
      super("WipeOut");
   }

   @Override
   public String getPresetTag() {
      return "presetID='22' presetClass='exit' presetSubtype='" +
             subtype + "'";
   }

   @Override
   public void dumpBehavior(PPOutputStream os, String delayTag) {
      os.print("<p:animEffect transition='out' filter='" + filter + "'>");
      os.print("<p:cBhvr>");
      os.print("<p:cTn id='" + os.getNextSequenceId() + "' " +
               getDurationTag() + ">");
      os.print("<p:stCondLst>");
      os.print("<p:cond " + delayTag + "/>");
      os.print("</p:stCondLst>");
      os.print("</p:cTn>");
      os.print("<p:tgtEl>");
      os.print("<p:spTgt spid='" + getShape().getShapeId() + "'/>");
      os.print("</p:tgtEl>");
      os.print("</p:cBhvr>");
      os.print("</p:animEffect>");
      os.print("<p:set>");
      os.print("<p:cBhvr>");
      os.print("<p:cTn id='" + os.getNextSequenceId() + "' " +
               "dur='1' fill='hold'>");
      os.print("<p:stCondLst>");
      os.print("<p:cond delay='" + Math.round(1000 * getDuration() - 1) +
               "'/>");
      os.print("</p:stCondLst>");
      os.print("</p:cTn>");
      os.print("<p:tgtEl>");
      os.print("<p:spTgt spid='" + getShape().getShapeId() + "'/>");
      os.print("</p:tgtEl>");
      os.print("<p:attrNameLst>");
      os.print("<p:attrName>style.visibility</p:attrName>");
      os.print("</p:attrNameLst>");
      os.print("</p:cBhvr>");
      os.print("<p:to><p:strVal val='hidden'/></p:to>");
      os.print("</p:set>");
   }

   public void preOptionHook() {
      setDuration("medium");
      fromBottomKey(null);
   }

   public void fromRightKey(String value) {
      filter = "wipe(right)";
      subtype = 8;
   }

   public void fromLeftKey(String value) {
      filter = "wipe(left)";
      subtype = 2;
   }

   public void fromBottomKey(String value) {
      filter = "wipe(down)";
      subtype = 4;
   }

   public void fromTopKey(String value) {
      filter = "wipe(up)";
      subtype = 1;
   }

/* Private instance variables */

   private String filter;
   private int subtype;

}
