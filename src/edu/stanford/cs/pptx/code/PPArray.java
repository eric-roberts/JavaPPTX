/*
 * File: PPArray.java
 * ------------------
 * This class represents an array in a code trace.
 */

package edu.stanford.cs.pptx.code;

import edu.stanford.cs.pptx.PPGroup;
import edu.stanford.cs.pptx.PPLine;
import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPTextBox;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class represents an array in a code trace.  The type parameter
 * is the value type.
 */

public class PPArray<T> extends PPGroup {

/**
 * Returns a new array object with the default cell dimensions.
 *
 * @param name The name of the array
 * @param n The size of the array
 */

   public PPArray(String name, int n) {
      this(name, n, DEFAULT_ELEMENT_WIDTH, DEFAULT_ELEMENT_HEIGHT);
   }

/**
 * Returns a new array object with the specified name, size, and element
 * dimensions.
 *
 * @param name The name of the array
 * @param n The size of the array
 * @param width The width of each element in pixels
 * @param height The height of each element in pixels
 */

   public PPArray(String name, int n, double width, double height) {
      this.name = name;
      elementWidth = width;
      elementHeight = height;
      listeners = new ArrayList<ChangeListener>();
      background = new PPRect(n * width, height);
      background.setFillColor(Color.WHITE);
      add(background);
      border = new PPRect(n * width, height);
      border.setFillColor("none");
      border.setLineColor(Color.BLACK);
      add(border);
      PPTextBox label = new PPTextBox(name);
      label.setFont(LABEL_FONT);
      add(label, LABEL_DX, LABEL_DY);
      array = new ArrayList<T>();
      displayValues = new ArrayList<PPRect>();
      for (int i = 0; i < n; i++) {
         double x = i * width;
         if (i > 0) add(new PPLine(x, 0, x, height));
         PPTextBox box = new PPTextBox("" + i);
         box.setFont(INDEX_FONT);
         box.setHorizontalAlignment("Center");
         box.setBounds(x, height + INDEX_DY, width, box.getHeight());
         add(box);
         array.add(null);
         displayValues.add(null);
      }
      setFont(VALUE_FONT);
   }

/**
 * Returns the name of the array.
 *
 * @return The name of the array
 */

   public String getName() {
      return name;
   }

/**
 * Sets the value of the array element.
 *
 * @param index The index in the array
 * @param value The new value
 */

   public void set(int index, T value) {
      set(index, value, "");
   }

/**
 * Sets the value of the array element.  This version allows the client
 * to change the animation options.
 *
 * @param index The index in the array
 * @param value The new value
 * @param options The animation options
 */

   public void set(int index, T value, String options) {
      array.set(index, value);
      updateElement(index, "/afterPrev" + options);
   }

/**
 * Gets the value of the specified array element.
 *
 * @param index The index in the array
 * @return The value of this variable
 */

   public T get(int index) {
      return array.get(index);
   }

/**
 * Returns the width of an array element.
 *
 * @return The width of an array element
 */

   public double getElementWidth() {
      return elementWidth;
   }

/**
 * Returns the height of an array element.
 *
 * @return The height of an array element
 */

   public double getElementHeight() {
      return elementHeight;
   }

/**
 * Sets the font for the values in the array.
 *
 * @param font The font for the values in the array
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
 * Adds a change listener to this array.
 *
 * @param listener The change listener
 */

   public void addChangeListener(ChangeListener listener) {
      listeners.add(listener);
   }

/**
 * Removes a change listener from this array.
 *
 * @param listener The change listener
 */

   public void removeChangeListener(ChangeListener listener) {
      listeners.remove(listener);
   }

/* Protected methods */

   protected void updateElement(int index, String options) {
      PPRect oldDisplay = displayValues.get(index);
      double x = getX() + index * elementWidth + VALUE_DX;
      double y = getY() + VALUE_DY;
      double w = elementWidth + VALUE_DW;
      double h = elementHeight + VALUE_DH;
      PPRect display = new PPRect(x, y, w, h);
      display.setColor(Color.WHITE);
      display.setHorizontalAlignment("Center");
      display.setVerticalAlignment("Middle");
      display.setFont(valueFont);
      display.setText(array.get(index).toString());
      getSlide().add(display);
      display.appear("/afterPrev" + options);
      if (oldDisplay != null) oldDisplay.disappear("/withPrev");
      displayValues.set(index, display);      
      fireChangeListeners();
   }

   protected void clear() {
      for (PPRect display : displayValues) {
         if (display != null) display.disappear("/afterPrev");
      }
   }

   protected void fireChangeListeners() {
      ChangeEvent e = new ChangeEvent(this);
      for (ChangeListener listener : listeners) {
         listener.stateChanged(e);
      }
   }

/* Private constants */

   private static final String INDEX_FONT = "Times New Roman-12";
   private static final String LABEL_FONT = "Courier New-Bold-18";
   private static final String VALUE_FONT = "Courier New-Bold-18";
   private static final double DEFAULT_ELEMENT_WIDTH = 60;
   private static final double DEFAULT_ELEMENT_HEIGHT = 30;
   private static final double INDEX_DY = -2;
   private static final double LABEL_DX = 1;
   private static final double LABEL_DY = -21;
   private static final double VALUE_DX = 2;
   private static final double VALUE_DY = 2;
   private static final double VALUE_DW = -3;
   private static final double VALUE_DH = -5;

/* Private instance variables */

   private ArrayList<ChangeListener> listeners;
   private ArrayList<PPRect> displayValues;
   private ArrayList<T> array;
   private Font valueFont;
   private PPRect background;
   private PPRect border;
   private String name;
   private double elementHeight;
   private double elementWidth;

}
