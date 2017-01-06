/*
 * File: PPUtil.java
 * -----------------
 * This class exports several static methods that are useful in writing
 * .pptx files.
 */

package edu.stanford.cs.pptx.util;

import java.awt.Color;
import java.awt.Container;
import java.awt.FontMetrics;

public class PPUtil {

   private PPUtil() {
      /* Don't allow instantiation */
   }

/**
 * Converts points into the units used in the underlying PowerPoint
 * representation.
 *
 * @param points The measurement in points
 * @return The corresponding number of units
 */

   public static int pointsToUnits(double points) {
      return (int) Math.round(UNITS_PER_POINT * points);
   }

/**
 * Returns a width value that is padded to ensure that text does not wrap
 * if the font metrics differ from platform to platform.  The constants are
 * determined experimentally.
 *
 * @param str The string
 * @param fm The <code>FontMetrics</code> object for the current font
 * @return The padded width as an integer
 */

   public static int getPaddedWidth(String str, FontMetrics fm) {
      return (int) Math.round(WIDTH_SCALE * fm.stringWidth(str) + WIDTH_PAD);
   }

/**
 * Returns an empty container that can be used as to derive font metrics
 * or serve as an image observer.  This method returns the same empty
 * container on all calls.
 *
 * @return An empty container
 */

   public static Container getEmptyContainer() {
      if (empty == null) empty = new EmptyContainer();
      return empty;
   }

/**
 * Decodes a color value.  The parameter <code>str</code> is either the
 * name of a Java color or a string in the form <code>"#rrggbb"</code>
 * or <code>"#aarrggbb"</code>.  As a special case, the string
 * <code>"none"</code> returns <code>null</code>.
 *
 * @param str The string representation of the color
 * @return The color value
 */

   public static Color decodeColor(String str) {
      if (str.equalsIgnoreCase("none")) return null;
      if (str.equalsIgnoreCase("bg")) return BACKGROUND_FILL;
      if (str.equalsIgnoreCase("black")) return Color.BLACK;
      if (str.equalsIgnoreCase("blue")) return Color.BLUE;
      if (str.equalsIgnoreCase("cyan")) return Color.CYAN;
      if (str.equalsIgnoreCase("darkGray")) return Color.DARK_GRAY;
      if (str.equalsIgnoreCase("DARK_GRAY")) return Color.DARK_GRAY;
      if (str.equalsIgnoreCase("gray")) return Color.GRAY;
      if (str.equalsIgnoreCase("green")) return Color.GREEN;
      if (str.equalsIgnoreCase("lightGray")) return Color.LIGHT_GRAY;
      if (str.equalsIgnoreCase("LIGHT_GRAY")) return Color.LIGHT_GRAY;
      if (str.equalsIgnoreCase("magenta")) return Color.MAGENTA;
      if (str.equalsIgnoreCase("orange")) return Color.ORANGE;
      if (str.equalsIgnoreCase("pink")) return Color.PINK;
      if (str.equalsIgnoreCase("red")) return Color.RED;
      if (str.equalsIgnoreCase("white")) return Color.WHITE;
      if (str.equalsIgnoreCase("yellow")) return Color.YELLOW;
      try {
         if (str.startsWith("0x")) {
            str = str.substring(2);
         } else if (str.startsWith("#")) {
            str = str.substring(1);
         }
         int nDigits = str.length();
         int aa = 0xFF;
         if (nDigits == 8) {
            aa = Integer.parseInt(str.substring(0, 2), 16);
            str = str.substring(2);
         } else if (nDigits != 6) {
            throw new RuntimeException("decodeColor: Colors must have " +
                                       "6 or 8 hexadecimal digits");
         }
         return new Color(Integer.parseInt(str, 16) | (aa << 24), true);
      } catch (NumberFormatException ex) {
         throw new RuntimeException("decodeColor: Illegal color " + str);
      }
   }

/**
 * Returns the XML color tag corresponding to the specified color.
 *
 * @param str The string representation of the color
 * @return The XML string that represents that color
 */

   public static String getColorTag(String str) {
      return getColorTag(decodeColor(str));
   }

/**
 * Returns the XML color tag corresponding to the Java color.
 *
 * @param color The Java color
 * @return The XML string that represents that color
 */

   public static String getColorTag(Color color) {
      if (color == BACKGROUND_FILL) return "<a:schemeClr val='accent1'/>";
      String rgb = Integer.toHexString(color.getRGB()).toUpperCase();
      return "<a:srgbClr val='" + rgb.substring(2) + "'/>";
   }

/**
 * Returns <code>true</code> if the color is identical to the special
 * background fill color.
 *
 * @param color The color being tested
 * @return The value <code>true</code> if the color is the background fill
 */

   public static boolean isBackgroundFill(Color color) {
      return color == BACKGROUND_FILL;
   }

/* Constants */

   private static final Color BACKGROUND_FILL = new Color(0, 1, 1, 0);
   private static final double WIDTH_SCALE = 1.02;
   private static final double WIDTH_PAD = 2.0;
   private static final int UNITS_PER_POINT = 12800;

/* Static variables */

   private static Container empty;

}

/**
 * This class represents a simple lightweight container.  The only
 * between EmptyContainer and Container is that EmptyContainer is
 * concrete and can therefore be instantiated.
 */

class EmptyContainer extends Container {
   /* Empty */
}
