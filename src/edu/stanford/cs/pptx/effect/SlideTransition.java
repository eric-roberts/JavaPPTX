/*
 * File: SlideTransition.java
 * --------------------------
 * This file contains the root of the SlideTransition hierarchy.
 */

package edu.stanford.cs.pptx.effect;

import edu.stanford.cs.options.OptionParser;
import edu.stanford.cs.pptx.util.PPOutputStream;

public class SlideTransition extends OptionParser {

   protected SlideTransition() {
      delay = -1;
      speed = null;
   }

   public void resetState() {
      /* Empty */
   }

   public void setDelay(double delay) {
      this.delay = delay;
   }

   public double getDelay() {
      return delay;
   }

   public void setSpeed(String str) {
      setSpeed(parseSpeed(str));
   }

   public String getSpeed() {
      return speed;
   }

   public void undefinedKey(String key, String value) {
      throw new RuntimeException("Unrecognized option: " + key);
   }

   public void delayKey(String value) {
      delay = Double.parseDouble(value);
   }

   public void speedKey(String value) {
      speed = parseSpeed(value);
   }

   private String parseSpeed(String str) {
      if (str.equalsIgnoreCase("fast")) return "fast";
      if (str.equalsIgnoreCase("med")) return "med";
      if (str.equalsIgnoreCase("medium")) return "med";
      if (str.equalsIgnoreCase("slow")) return "slow";
      throw new RuntimeException("Illegal speed designation");
   }

   public void dumpTransition(PPOutputStream os) {
      os.print("<p:transition");
      if (delay != -1) {
         os.print(" advTm='" + (int) Math.round(1000 * delay) +"'");
      }
      if (speed != null) {
         os.print(" spd='" + speed +"'");
      }
      os.print(">");
      dumpEffect(os);
      os.print("</p:transition>");
   }

   public void dumpEffect(PPOutputStream os) {
      /* Empty */
   }

/* Protected methods */

   protected String getSpeedTag() {
      return "spd='" + speed +"'";
   }

/* Static methods */

   public static SlideTransition createTransition(String options) {
      int slash = options.indexOf('/');
      if (slash == -1) slash = options.length();
      String effectName = options.substring(0, slash);
      if (slash == 0) effectName = "No";
      String className = SlideTransition.class.getName();
      className = className.substring(0, className.lastIndexOf('.') + 1) +
                  effectName + "Transition";
      try {
         Class<?> effectClass = Class.forName(className);
         SlideTransition effect = (SlideTransition) effectClass.newInstance();
         effect.parseOptions(options.substring(slash));
         return effect;
      } catch (ClassNotFoundException ex) {
         throw new RuntimeException("Transition " + effectName +
                                    " is not yet implemented");
      } catch (InstantiationException ex) {
         throw new RuntimeException(ex);
      } catch (IllegalAccessException ex) {
         throw new RuntimeException(ex);
      }
   }

/* Private instance variables */

   private String speed;
   private double delay;

}
