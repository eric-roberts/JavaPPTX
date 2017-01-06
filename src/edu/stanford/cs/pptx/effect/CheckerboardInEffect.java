/*
 * File: CheckerboardInEffect.java
 * -------------------------------
 * This class implements the checkerboard-in effect.
 */

package edu.stanford.cs.pptx.effect;

import edu.stanford.cs.pptx.util.PPOutputStream;

/**
 * This entrance effect causes the shape to appear in a checkerboard pattern.
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
 *     <code>/across</code></td>
 *     <td>The checkerboard moves from left to right</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/down</code></td>
 *     <td>The checkerboard moves from top to bottom</td></tr>
 * </table>
 */

public class CheckerboardInEffect extends AnimationEffect {

   public CheckerboardInEffect() {
      super("CheckerboardIn");
   }

   @Override
   public String getPresetTag() {
      return "presetID='5' presetClass='entr' presetSubtype='10'";
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
      os.print("<p:animEffect transition='in' filter='checkerboard(" +
               getDirection() + ")'>");
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
      setDirection("across");
   }

   public void acrossKey(String value) {
      setDirection("across");
   }

   public void downKey(String value) {
      setDirection("down");
   }

}
