/*
 * File: WipeInEffect.java
 * -----------------------
 * This class implements the wipe entrance effect.
 */

package edu.stanford.cs.pptx.effect;

import edu.stanford.cs.pptx.util.PPOutputStream;

/**
 * This entrance effect causes the shape to appear gradually from one side.
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
 *     <td>The shape appears from left to right</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromRight</code></td>
 *     <td>The shape appears from right to left</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromTop</code></td>
 *     <td>The shape appears from top to bottom</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/fromBottom</code></td>
 *     <td>The shape appears from bottom to top</td></tr>
 * </table>
 */

public class WipeInEffect extends AnimationEffect {

   public WipeInEffect() {
      super("WipeIn");
   }

   @Override
   public String getPresetTag() {
      return "presetID='22' presetClass='entr' presetSubtype='" +
             subtype + "'";
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
      os.print("<p:animEffect transition='in' filter='" + filter + "'>");
      os.print("<p:cBhvr>");
      os.print("<p:cTn id='" + os.getNextSequenceId() + "' " +
               getDurationTag() + "/>");
      os.print("<p:tgtEl>");
      os.print("<p:spTgt spid='" + getShape().getShapeId() + "'/>");
      os.print("</p:tgtEl>");
      os.print("</p:cBhvr>");
      os.print("</p:animEffect>");
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
