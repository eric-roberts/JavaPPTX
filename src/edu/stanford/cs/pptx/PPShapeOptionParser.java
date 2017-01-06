/*
 * File: PPShapeOptionParser.java
 * ------------------------------
 * This class implements the common option parser for all shapes.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.options.OptionParser;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class PPShapeOptionParser extends OptionParser {

   public PPShapeOptionParser(PPShape obj) {
      target = obj;
   }

   public void invokeKey(String key, String value) {
      Class<?>[] types = { String.class };
      Object[] args = { value };
      try {
         Method fn = getClass().getMethod(key + "Key", types);
         fn.invoke(this, args);
      } catch (NoSuchMethodException ex) {
         undefinedKey(key, value);
      } catch (InvocationTargetException ex) {
         throw new RuntimeException(ex);
      } catch (IllegalAccessException ex) {
         throw new RuntimeException(ex);
      }
   }

   public void postOptionHook() {
      super.postOptionHook();
      Rectangle2D bounds = target.getBounds();
      if (bounds == null) return; // Fix
      if (x == null) x = bounds.getX();
      if (y == null) y = bounds.getY();
      if (width == null) width = bounds.getWidth();
      if (height == null) height = bounds.getHeight();
      target.setBounds(x, y, width, height);
   }

   public void xKey(String str) {
      x = Double.valueOf(str);
   }

   public void yKey(String str) {
      y = Double.valueOf(str);
   }

   public void widthKey(String str) {
      width = Double.valueOf(str);
   }

   public void heightKey(String str) {
      height = Double.valueOf(str);
   }

/* Private instance variables */

   private PPShape target;
   private Double x;
   private Double y;
   private Double width;
   private Double height;

}
