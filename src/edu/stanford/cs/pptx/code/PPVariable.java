/*
 * File: PPVariable.java
 * ---------------------
 * This class represents variable in a stack frame.  This class is the
 * superclass of the parameterized PPVar class.
 */

package edu.stanford.cs.pptx.code;

import edu.stanford.cs.pptx.PPGroup;
import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPTextBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class represents a variable in a stack frame.  This class is the
 * superclass of the parameterized <code>PPVar</code> class.
 */

public class PPVariable extends PPGroup {

/**
 * Returns a new anonymous variable with the default dimensions.
 */

   public PPVariable() {
      this("");
   }

/**
 * Returns a new variable object with the default dimensions.
 *
 * @param name The name of the variable
 */

   public PPVariable(String name) {
      this(name, DEFAULT_WIDTH, DEFAULT_HEIGHT);
   }

/**
 * Returns a new variable object with the specified width and the default
 * height.
 *
 * @param name The name of the variable
 * @param width The width of the variable in pixels
 */

   public PPVariable(String name, double width) {
      this(name, width, DEFAULT_HEIGHT);
   }

/**
 * Returns a new variable object with the specified name and dimensions.
 *
 * @param name The name of the variable
 * @param width The width of the variable in pixels
 * @param height The height of the variable in pixels
 */

   public PPVariable(String name, double width, double height) {
      this.name = name;
      this.width = width;
      this.height = height;
      listeners = new ArrayList<ChangeListener>();
      background = new PPRect(width, height);
      background.setFillColor(Color.WHITE);
      add(background);
      border = new PPRect(width, height);
      border.setFillColor("none");
      border.setLineColor(Color.BLACK);
      add(border);
      PPTextBox label = new PPTextBox(name);
      label.setFont(LABEL_FONT);
      display = null;
      setFont(VALUE_FONT);
      add(label, LABEL_DX, LABEL_DY);
      setMethod = null;
      for (Method m : getClass().getMethods()) {
         if (m.getName().equals("set") && m.getParameterTypes().length == 1) {
            if (setMethod == null) {
               setMethod = m;
            } else {
               throw new RuntimeException("Ambiguous set method");
            }
         }
      }
   }

/**
 * Returns the name of the variable.
 *
 * @return The name of the variable
 */

   public String getName() {
      return name;
   }

/**
 * Sets the value of the variable.
 *
 * @param value The string value of the variable
 */

   public void setValue(String value) {
      setValue(value, "");
   }

/**
 * Sets the value of the variable.  This version allows the client to change
 * the animation options.
 *
 * @param value The string value of the variable
 * @param options The animation options
 */

   public void setValue(String value, String options) {
      text = value;
      updateDisplay("");
   }

/**
 * Gets the string value of this variable.
 *
 * @return The value of this variable
 */

   public String getValue() {
      return text;
   }

/**
 * Returns the width of the variable box.  Note that <code>getWidth</code>
 * returns the width of the group, which may be different.
 *
 * @return The width of the variable box
 */

   public double getVarWidth() {
      return width;
   }

/**
 * Returns the height of the variable box.  Note that <code>getHeight</code>
 * returns the height of the group, which may be different.
 *
 * @return The height of the variable box
 */

   public double getVarHeight() {
      return height;
   }

/**
 * Returns the center of the variable box.
 *
 * @return The center of the variable box
 */

   @Override
   public Point2D getCenter() {
      return new Point2D.Double(getX() + width / 2, getY() + height / 2);
   }

/**
 * Sets the fill color of the variable.
 *
 * @param color The fill color
 */

   public void setFillColor(Color color) {
      background.setFillColor(color);
   }

/**
 * Sets the font for the value of the variable.
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
 * Erases the value of this variable from the display.
 */

   public void clear() {
      if (display != null) display.disappear("/afterPrev");
   }

/**
 * Sets the word to which this variable is linked.
 *
 * @param word The word to which this variable is linked
 */

   public void setWord(PPWord word) {
      this.word = word;
   }

/**
 * Gets the word to which this variable is linked.
 *
 * @return The word to which this variable is linked
 */

   public PPWord getWord() {
      return word;
   }

/**
 * Adds a change listener to this variable.
 *
 * @param listener The change listener
 */

   public void addChangeListener(ChangeListener listener) {
      listeners.add(listener);
   }

/**
 * Removes a change listener from this variable.
 *
 * @param listener The change listener
 */

   public void removeChangeListener(ChangeListener listener) {
      listeners.remove(listener);
   }

/* Protected methods */

   protected void setParameterFlag(boolean flag) {
      parameterFlag = flag;
   }

   protected boolean isParameter() {
      return parameterFlag;
   }

   protected void setValueFromObject(Object obj) {
      try {
         Object[] args = { obj };
         setMethod.invoke(this, args);
      } catch (Exception ex) {
         throw new RuntimeException(ex.toString());
      }
   }

   protected void updateDisplay(String options) {
      PPRect oldDisplay = display;
      display = new PPRect(getX() + VALUE_DX, getY() + VALUE_DY,
                           width + VALUE_DW, height + VALUE_DH);
      display.setColor(Color.WHITE);
      display.setHorizontalAlignment("Center");
      display.setVerticalAlignment("Middle");
      display.setFont(valueFont);
      display.setText(text);
      getSlide().add(display);
      display.appear("/afterPrev" + options);
      if (oldDisplay != null) oldDisplay.disappear("/withPrev");
      fireChangeListeners();
   }

   protected void fireChangeListeners() {
      ChangeEvent e = new ChangeEvent(this);
      for (ChangeListener listener : listeners) {
         listener.stateChanged(e);
      }
   }

/* Private constants */

   private static final String LABEL_FONT = "Courier New-Bold-18";
   private static final String VALUE_FONT = "Courier New-Bold-18";
   private static final double DEFAULT_WIDTH = 70;
   private static final double DEFAULT_HEIGHT = 30;
   private static final double LABEL_DX = 1;
   private static final double LABEL_DY = -22;
   private static final double VALUE_DX = 2;
   private static final double VALUE_DY = 2;
   private static final double VALUE_DW = -3;
   private static final double VALUE_DH = -5;

/* Private instance variables */

   private ArrayList<ChangeListener> listeners;
   private Font valueFont;
   private Method setMethod;
   private PPRect background;
   private PPRect border;
   private PPRect display;
   private PPWord word;
   private String name;
   private String text;
   private boolean parameterFlag;
   private double height;
   private double width;

}
