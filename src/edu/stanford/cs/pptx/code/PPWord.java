/*
 * File: PPWord.java
 * -----------------
 * This class represents a word in a memory viewer.
 */

package edu.stanford.cs.pptx.code;

import edu.stanford.cs.pptx.PPGroup;
import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPTextBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;

/**
 * This class represents a word in a memory viewer.
 */

public class PPWord extends PPGroup {

/**
 * Returns a new <code>PPWord</code> object with the default dimensions.
 */

   public PPWord() {
      this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
   }

/**
 * Returns a new <code>PPWord</code> object with the specified dimensions.
 *
 * @param width The width of the word in pixels
 * @param height The height of the word in pixels
 */

   public PPWord(double width, double height) {
      this.width = width;
      this.height = height;
      label = null;
      addr = -1;
      background = new PPRect(width, height);
      background.setFillColor(Color.WHITE);
      add(background);
      border = new PPRect(width, height);
      border.setFillColor("none");
      border.setLineColor(Color.BLACK);
      add(border);
      orientation = "";
      display = null;
      setFont(VALUE_FONT);
      setAddressFont(ADDRESS_FONT);
      setLabelFont(LABEL_FONT);
   }

/**
 * Assigns a label to the word.  The position of the label is controlled by
 * the <code>setLabelOrientation</code> method.
 *
 * @param label The label assigned to the word
 */
   
   public void setLabel(String label) {
      this.label = label;
      PPTextBox box = new PPTextBox(label);
      box.setFont(labelFont);
      box.setVerticalAlignment("Middle");
      if (orientation.startsWith("label-")) {
         box.setHorizontalAlignment("Right");
         add(box, LEFT_DX - box.getWidth(), 0);
      } else if (orientation.endsWith("label")) {
         box.setHorizontalAlignment("Left");
         add(box, getWidth() + RIGHT_DX, 0);
      }
   }

/**
 * Returns the label assigned to this word.
 *
 * @return The label assigned to this word
 */
   
   public String getLabel() {
      return label;
   }

/**
 * Sets the address of the word.
 *
 * @param addr The address of the word
 */
   
   public void setAddress(int addr) {
      this.addr = addr;
      String str = Integer.toHexString(0x10000 + addr).substring(1);
      str = str.toUpperCase();
      PPMonospacedLabel label = new PPMonospacedLabel(str, addressFont, "D");
      if (orientation.startsWith("address-")) {
         add(label, LEFT_DX - 4 * label.getCharWidth(), ADDRESS_DY);
      } else if (orientation.endsWith("address")) {
         add(label, getWidth() + RIGHT_DX + ADDRESS_DX, ADDRESS_DY);
      }
   }

/**
 * Returns the address of the word.  If no address has been assigned,
 * <code>getAddress</code> returns -1.
 *
 * @return The address of the word
 */
   
   public int getAddress() {
      return addr;
   }

/**
 * Sets the value assigned to this word.
 *
 * @param value The value assigned to this word
 */

   public void set(Object value) {
      set(value, "");
   }

/**
 * Sets the value assigned to this word.  This version allows the client
 * to change the animation options.
 *
 * @param value The value assigned to this word
 * @param options The animation options
 */

   public void set(Object value, String options) {
      this.value = value.toString();
      updateDisplay("");
   }

/**
 * Erases the value of this variable from the display.
 */

   public void clear() {
      if (display != null) display.disappear("/afterPrev");
   }

/**
 * Sets the fill color of the word.
 *
 * @param color The fill color
 */

   public void setFillColor(Color color) {
      background.setFillColor(color);
   }

/**
 * Sets the font for the value assigned to this word.
 *
 * @param font The font for the value
 */

   public void setFont(Font font) {
      valueFont = font;
   }

/**
 * Sets the font for the value.  In this version of <code>setFont</code>,
 * the font is expressed as a string in the form
 *
 *<pre>
 *    "<i>family</i>-<i>style</i>-<i>size</i>"
 *</pre>
 *
 * @param str A string representing the font
 */

   public void setFont(String str) {
      setFont(Font.decode(str));
   }

/**
 * Gets the value font.
 *
 * @return The font used to display the value
 */

   public Font getFont() {
      return valueFont;
   }

/**
 * Sets the font for the label.
 *
 * @param font The font for the label
 */

   public void setLabelFont(Font font) {
      labelFont = font;
   }

/**
 * Sets the font for the label.  In this version of <code>setLabelFont</code>,
 * the font is expressed as a string in the form
 *
 *<pre>
 *    "<i>family</i>-<i>style</i>-<i>size</i>"
 *</pre>
 *
 * @param str A string representing the font
 */

   public void setLabelFont(String str) {
      setLabelFont(Font.decode(str));
   }

/**
 * Gets the label font.
 *
 * @return The font used to display the label
 */

   public Font getLabelFont() {
      return labelFont;
   }

/**
 * Sets the font for the address.
 *
 * @param font The font for the address
 */

   public void setAddressFont(Font font) {
      addressFont = font;
   }

/**
 * Sets the font for the address.  In this version of
 * <code>setAddressFont</code>, the font is expressed as a string
 * in the form
 *
 *<pre>
 *    "<i>family</i>-<i>style</i>-<i>size</i>"
 *</pre>
 *
 * @param str A string representing the font
 */

   public void setAddressFont(String str) {
      setAddressFont(Font.decode(str));
   }

/**
 * Gets the address font.
 *
 * @return The font used to display the address
 */

   public Font getAddressFont() {
      return addressFont;
   }

/**
 * Sets the format for the labels attached to the word.  The parameter
 * is a string consisting of the literal words <code>"address"</code>
 * and <code>"label"</code> separated by a hyphen, if both appear.
 * The field to the left of the hyphen appears on the left side of the
 * variable box and the field to the right of the hyphen (or a field
 * alone on the line) appears to right of the variable box.
 *
 * @param str The orientation string
 */

   public void setLabelOrientation(String str) {
      orientation = str;
   }

/**
 * Gets the format for the labels attached to the word.
 *
 * @return The orientation string
 */

   public String getLabelOrientation() {
      return orientation;
   }

/**
 * Returns the width of the word box.  Note that <code>getWidth</code>
 * returns the width of the group, which may be different.
 *
 * @return The width of the word box
 */

   public double getWordWidth() {
      return width;
   }

/**
 * Returns the height of the word box.  Note that <code>getHeight</code>
 * returns the height of the group, which may be different.
 *
 * @return The height of the word box
 */

   public double getWordHeight() {
      return height;
   }

/**
 * Returns the center of the word box.
 *
 * @return The center of the word box
 */

   @Override
   public Point2D getCenter() {
      return new Point2D.Double(getX() + width / 2, getY() + height / 2);
   }

/* Protected methods */

   protected void updateDisplay(String options) {
      PPRect oldDisplay = display;
      display = new PPRect(getX() + VALUE_DX, getY() + VALUE_DY,
                           width + VALUE_DW, height + VALUE_DH);
      display.setColor(Color.WHITE);
      display.setHorizontalAlignment("Center");
      display.setVerticalAlignment("Middle");
      display.setFont(valueFont);
      display.setText(value.toString());
      getSlide().add(display);
      display.appear("/afterPrev" + options);
      if (oldDisplay != null) oldDisplay.disappear("/withPrev");
   }

/* Public constants */

   public static final double DEFAULT_WIDTH = 80;
   public static final double DEFAULT_HEIGHT = 24;

/* Private constants */

   private static final String ADDRESS_FONT = "Helvetica Neue-Bold-14";
   private static final String LABEL_FONT = "Courier New-Bold-18";
   private static final String VALUE_FONT = "Courier New-Bold-18";
   private static final double ADDRESS_DX = -14;
   private static final double ADDRESS_DY = 1;
   private static final double LEFT_DX = -3;
   private static final double RIGHT_DX = 2;
   private static final double VALUE_DX = 2;
   private static final double VALUE_DY = 2;
   private static final double VALUE_DW = -4;
   private static final double VALUE_DH = -6;

/* Private instance variables */

   private Font addressFont;
   private Font labelFont;
   private Font valueFont;
   private PPRect background;
   private PPRect border;
   private PPRect display;
   private String label;
   private String orientation;
   private String value;
   private double height;
   private double width;
   private int addr;

}
