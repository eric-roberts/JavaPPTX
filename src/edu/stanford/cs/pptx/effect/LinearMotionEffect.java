/*
 * File: LinearMotionEffect.java
 * -----------------------------
 * Represents a linear motion effect.
 */

package edu.stanford.cs.pptx.effect;

import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.util.PPOutputStream;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

public class LinearMotionEffect extends AnimationEffect {

   public LinearMotionEffect() {
      super("MotionPath");
      format = new DecimalFormat("0.000");
      sx = 1.0 / PPShow.WIDTH;
      sy = 1.0 / PPShow.HEIGHT;
   }

   public void setState(MotionPathState state) {
      mpState = state;
   }

   public void preOptionHook() {
      speed = -1.0;
      setDuration(-1.0);
      accFlag = false;
      decFlag = false;
   }

   public void resetState() {
      if (mpState != null) mpState.resetEffectLocation();
   }

   public double getDuration() {
      double duration = super.getDuration();
      if (duration > 0) return duration;
      if (speed < 0) speed = mpState.getSpeed();
      double distance = Math.sqrt(dx * dx + dy * dy);
      return distance / speed;
   }

   @Override
   public String getPresetTag() {
      String tag = "presetID='0' presetClass='path' presetSubtype='0'";
      tag += " accel='" + (accFlag ? 50000 : 0) + "'";
      tag += " decel='" + (decFlag ? 50000 : 0) + "'";
      return tag;
   }

   @Override
   public void dumpBehavior(PPOutputStream os, String delayTag) {
      Point2D start = mpState.getInitialLocation();
      Point2D current = mpState.getEffectLocation();
      double x0 = current.getX() - start.getX();
      double y0 = current.getY() - start.getY();
      os.print("<p:animMotion origin='layout' " +
               "path='" + getMotionString(x0, y0) + "' " +
               "pathEditMode='relative' ptsTypes='AA'>");
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
      os.print("<p:attrName>ppt_x</p:attrName>");
      os.print("<p:attrName>ppt_y</p:attrName>");
      os.print("</p:attrNameLst>");
      os.print("</p:cBhvr>");
      os.print("</p:animMotion>");
      mpState.adjustEffectLocation(dx, dy);
   }

   private String getMotionString(double x0, double y0) {
      String str = "M";
      str += " " + format.format(sx * x0);
      str += " " + format.format(sy * y0);
      str += " L " + format.format(sx * (x0 + dx));
      str += " " + format.format(sy * (y0 + dy)) + " ";
      return str;
   }

/* Option keys */

   public void dxKey(String value) {
      dx = Double.parseDouble(value);
   }

   public void dyKey(String value) {
      dy = Double.parseDouble(value);
   }

   public void speedKey(String value) {
      speed = Double.parseDouble(value);
   }

   public void accKey(String value) {
      accFlag = true;
   }

   public void decKey(String value) {
      decFlag = true;
   }

/* Private instance variables */

   private MotionPathState mpState;
   private DecimalFormat format;
   private double dx;
   private double dy;
   private double sx;
   private double sy;
   private double speed;
   private boolean accFlag;
   private boolean decFlag;

}
