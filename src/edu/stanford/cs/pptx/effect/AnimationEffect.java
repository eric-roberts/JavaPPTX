/*
 * File: AnimationEffect.java
 * --------------------------
 * This file contains the root of the AnimationEffect hierarchy.
 */

package edu.stanford.cs.pptx.effect;

import edu.stanford.cs.options.OptionParser;
import edu.stanford.cs.pptx.PPShape;
import edu.stanford.cs.pptx.PPSlide;
import edu.stanford.cs.pptx.util.PPOutputStream;
import java.util.ArrayList;

public class AnimationEffect extends OptionParser {

   protected AnimationEffect(String name) {
      delay = 0;
      duration = 0;
   }

   public void resetState() {
      /* Empty */
   }

   public PPShape getShape() {
      return shape;
   }

   public void setAnimationType(String type) {
      effectType = type;
   }

   public String getAnimationType() {
      return effectType;
   }

   public void setDirection(String dir) {
      direction = dir;
   }

   public String getDirection() {
      return direction;
   }

   public void setTrigger(String trigger) {
      this.trigger = trigger;
   }

   public String getTrigger() {
      return trigger;
   }

   public void setDelay(double delay) {
      this.delay = delay;
   }

   public double getDelay() {
      return delay;
   }

   public void setDuration(String str) {
      setDuration(parseDuration(str));
   }

   public void setDuration(double duration) {
      this.duration = duration;
   }

   public double getDuration() {
      return duration;
   }

   public void undefinedKey(String key, String value) {
      double duration = parseDuration(key);
      if (duration > 0) {
         setDuration(duration);
      } else {
         throw new RuntimeException("Unrecognized option: " + key);
      }
   }

   public void onClickKey(String value) {
      trigger = "onClick";
   }

   public void withPreviousKey(String value) {
      trigger = "withPrev";
   }

   public void withPrevKey(String value) {
      trigger = "withPrev";
   }

   public void afterPreviousKey(String value) {
      trigger = "afterPrev";
   }

   public void afterPrevKey(String value) {
      trigger = "afterPrev";
   }

   public void delayKey(String value) {
      delay = Double.parseDouble(value);
   }

   public void durationKey(String value) {
      duration = parseDuration(value);
   }

   public void durKey(String value) {
      duration = parseDuration(value);
   }

   private double parseDuration(String str) {
      if (str.equalsIgnoreCase("veryFast")) return 0.5;
      if (str.equalsIgnoreCase("fast")) return 1.0;
      if (str.equalsIgnoreCase("medium")) return 2.0;
      if (str.equalsIgnoreCase("slow")) return 3.0;
      if (str.equalsIgnoreCase("verySlow")) return 5.0;
      return Double.parseDouble(str);
   }

   public String getTriggerTag() {
      if (trigger == null) return "clickEffect";
      if (trigger.equalsIgnoreCase("withPrev")) return "withEffect";
      if (trigger.equalsIgnoreCase("afterPrev")) return "afterEffect";
      return "clickEffect";
   }

   public String getPresetTag() {
      return "";
   }

   public void dumpBehavior(PPOutputStream os, String delayTag) {
      /* Empty */
   }

   public void dumpAnimation(PPOutputStream os, double startT) {
      os.print("<p:par>");
      String delayTag = "delay='" + (int) Math.round(1000 * delay) +"'";
      os.print("<p:cTn id='" + os.getNextSequenceId() + "' " +
               getPresetTag() + " fill='hold' " +
               "grpId='" + os.getNextGroupId() + "' " +
               "nodeType='" + getTriggerTag() + "'>");
      os.print("<p:stCondLst>");
      if (trigger != null && trigger.equals("afterPrev")) {
         os.print("<p:cond " + delayTag + "/>");
         delayTag = "delay='0'";
      } else {
         os.print("<p:cond delay='0'/>");
      }
      os.print("</p:stCondLst>");
      os.print("<p:childTnLst>");
      dumpBehavior(os, delayTag);
      os.print("</p:childTnLst>");
      os.print("</p:cTn>");
      os.print("</p:par>");
   }

/* Protected methods */

   protected String getDurationTag() {
      return "dur='" + (int) Math.round(1000 * getDuration()) +"'";
   }

/* Static methods */

   public static AnimationEffect createEffect(String options, PPShape shape) {
      int slash = options.indexOf('/');
      if (slash == -1) slash = options.length();
      String effectName = options.substring(0, slash);
      String className = AnimationEffect.class.getName();
      className = className.substring(0, className.lastIndexOf('.') + 1) +
                  effectName + "Effect";
      try {
         Class<?> effectClass = Class.forName(className);
         AnimationEffect effect = (AnimationEffect) effectClass.newInstance();
         effect.parseOptions(options.substring(slash));
         effect.shape = shape;
         return effect;
      } catch (ClassNotFoundException ex) {
         throw new RuntimeException("Effect " + effectName +
                                    " is not yet implemented");
      } catch (InstantiationException ex) {
         throw new RuntimeException(ex);
      } catch (IllegalAccessException ex) {
         throw new RuntimeException(ex);
      }
   }

   public static void updateAnimations(PPSlide slide,
                                       ArrayList<AnimationEffect> list) {
      // Fill in if needed
   }

   public static void dumpAnimationList(PPOutputStream os,
                                        ArrayList<AnimationEffect> list) {
      double t = 0;
      double nextT = 0;
      double startT = 0;
      int n = list.size();
      int currentDepth = 0;
      for (int i = 0; i < n; i++) {
         AnimationEffect effect = list.get(i);
         String trigger = effect.getTrigger();
         if (trigger == null) trigger = "onClick";
         int desiredDepth = 0;
         if (trigger.equals("withPrev")) {
            desiredDepth = 2;
         } else if (trigger.equals("afterPrev")) {
            desiredDepth = 1;
         }
         while (currentDepth > desiredDepth) {
            os.print("</p:childTnLst>");
            os.print("</p:cTn>");
            os.print("</p:par>");
            currentDepth--;
         }
         if (trigger.equals("withPrev")) {
            startT = t + effect.getDelay();
            nextT = startT + effect.getDuration();
         } else if (trigger.equals("afterPrev")) {
            t = startT = nextT + effect.getDelay();
            nextT = startT + effect.getDuration();
            os.print("<p:par>");
            os.print("<p:cTn id='" + os.getNextSequenceId() +
                     "' fill='hold'>");
            os.print("<p:stCondLst>");
            if (i == 0) {
               os.print("<p:cond delay='indefinite'/>");
               os.print("<p:cond evt='onBegin' delay='0'>");
               os.print("<p:tn val='3'/>");
               os.print("</p:cond>");
            } else {
               os.print("<p:cond delay='" + Math.round(1000 * startT) + "'/>");
            }
            os.print("</p:stCondLst>");
            os.print("<p:childTnLst>");
            currentDepth++;
            if (i == 0) {
               os.print("<p:par>");
               os.print("<p:cTn id='" + os.getNextSequenceId() +
                        "' fill='hold'>");
               os.print("<p:stCondLst>");
               os.print("<p:cond delay='0'/>");
               os.print("</p:stCondLst>");
               os.print("<p:childTnLst>");
               currentDepth++;
            }
         } else {
            t = startT = 0;
            nextT = startT + effect.getDuration();
            os.print("<p:par>");
            os.print("<p:cTn id='" + os.getNextSequenceId() +
                     "' fill='hold'>");
            os.print("<p:stCondLst>");
            os.print("<p:cond delay='indefinite'/>");
            os.print("</p:stCondLst>");
            os.print("<p:childTnLst>");
            os.print("<p:par>");
            os.print("<p:cTn id='" + os.getNextSequenceId() +
                     "' fill='hold'>");
            os.print("<p:stCondLst>");
            os.print("<p:cond delay='0'/>");
            os.print("</p:stCondLst>");
            os.print("<p:childTnLst>");
            currentDepth += 2;
         }
         effect.dumpAnimation(os, startT);
      }
      while (currentDepth > 0) {
         os.print("</p:childTnLst>");
         os.print("</p:cTn>");
         os.print("</p:par>");
         currentDepth--;
      }
   }

   public static void dumpBuildList(PPOutputStream os,
                                    ArrayList<AnimationEffect> list) {
      if (!list.isEmpty()) {
         os.print("<p:bldLst>");
         for (AnimationEffect effect : list) {
            os.print("<p:bldP spid='" + effect.getShape().getShapeId() +
                     "' grpId='" + os.getNextGroupId() + "' animBg='1'/>");
         }
         os.print("</p:bldLst>");
      }
   }

/* Private instance variables */

   private PPShape shape;
   private String effectType;
   private String direction;
   private String trigger;
   private double duration;
   private double delay;

}
