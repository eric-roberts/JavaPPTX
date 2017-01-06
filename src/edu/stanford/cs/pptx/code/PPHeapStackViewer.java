/*
 * File: PPHeapStackViewer.java
 * ----------------------------
 * This class manages a heap-stack diagram.
 */

package edu.stanford.cs.pptx.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Stack;

public class PPHeapStackViewer {

/**
 * Creates a new single-column heap-stack viewer at the specified point.
 * The height extends from the specified y coordinate to the bottom of the
 * slide.
 *
 * @param slide The <code>PPCodeTraceSlide</code> to which the viewer belongs
 * @param x The left edge of the stack region
 * @param y The top edge of the stack region
 */

   public PPHeapStackViewer(PPCodeTraceSlide slide, double x, double y) {
      this(slide, x, y, LEFT_MARGIN + RIGHT_MARGIN + PPWord.DEFAULT_WIDTH,
                        slide.getHeight() - y);
   }

/**
 * Creates a new heap-stack viewer with the specified bounds.
 *
 * @param slide The <code>PPCodeTraceSlide</code> to which the viewer belongs
 * @param x The left edge of the stack region
 * @param y The top edge of the stack region
 * @param width The width of the stack region
 * @param height The height of the stack region
 */

   public PPHeapStackViewer(PPCodeTraceSlide slide,
                            double x, double y, double width, double height) {
      this.slide = slide;
      bb = new Rectangle2D.Double(x, y, width, height);
      hp = 0x1000;
      sp = 0x10000;
      addrMap = new HashMap<Integer,PPWord>();
      callStack = new Stack<PPWord>();
      setLeftMargin(LEFT_MARGIN);
      setRightMargin(RIGHT_MARGIN);
      setTopMargin(TOP_MARGIN);
      setBottomMargin(BOTTOM_MARGIN);
      setFont(VALUE_FONT);
      setAddressFont(ADDRESS_FONT);
      setLabelFont(LABEL_FONT);
      setLabelOrientation("label-address");
   }

/**
 * Gets the <code>PPCodeTraceSlide</code> that controls this viewer.
 *
 * @return The <code>PPCodeTraceSlide</code> that controls this viewer
 */

   public PPCodeTraceSlide getSlide() {
      return slide;
   }

/**
 * Adds the word to the heap.
 *
 * @param word The new heap word
 */

   public void addToHeap(PPWord word) {
      addToHeap(word, null);
   }

/**
 * Adds the word to the heap with an optional label.
 *
 * @param word The new heap word
 * @param label The label on the word
 */

   public void addToHeap(PPWord word, String label) {
      word.setFont(valueFont);
      word.setAddressFont(ADDRESS_FONT);
      word.setLabelFont(LABEL_FONT);
      word.setLabelOrientation(orientation);
      if (label != null) word.setLabel(label);
      word.setAddress(hp);
      hp += 4;
      heapY += word.getWordHeight();
      slide.add(word, heapX, heapY);
      word.appear("/afterPrev");
   }

/**
 * Pushes the variable onto the heap-stack viewer.
 *
 * @param var The variable
 */

   public void pushVar(PPVariable var) {
      PPWord word = new PPWord();
      word.setFont(valueFont);
      word.setAddressFont(ADDRESS_FONT);
      word.setLabelFont(LABEL_FONT);
      word.setLabelOrientation(orientation);
      word.setLabel(var.getName());
      pushWord(word);
      var.setWord(word);
   }

/**
 * Pushes the word onto the stack.
 *
 * @param word The word being pushed
 */

   public void pushWord(PPWord word) {
      word.setAddress(sp -= 4);
      addrMap.put(sp, word);
      stackY -= word.getWordHeight();
      slide.add(word, stackX - word.getWordWidth(), stackY);
      word.appear("/afterPrev");
   }

/**
 * Pushes the stack frame in the heap-stack viewer.
 *
 * @param sf The stack frame
 */

   public void pushFrame(PPStackFrame sf) {
      PPWord word = new PPWord();
      word = new PPWord(word.getWordWidth(), BOUNDARY_HEIGHT);
      word.setFillColor(Color.LIGHT_GRAY);
      pushWord(word);
      callStack.push(word);
   }

/**
 * Pops the stack frame in the heap-stack viewer.
 *
 * @param sf The stack frame
 */

   public void popFrame(PPStackFrame sf) {
      for (PPVariable var : sf.getVariables()) {
         PPWord word = var.getWord();
         word.clear();
         word.disappear("/afterPrev");
         stackY += word.getWordHeight();
         sp += 4;
      }
      if (!callStack.isEmpty()) {
         PPWord word = callStack.pop();
         word.disappear("/afterPrev");
         stackY += word.getWordHeight();
         sp += 4;
      }
   }

/**
 * Updates the value of the variable in the heap-stack viewer.
 *
 * @param var The variable
 */

   public void updateVar(PPVariable var) {
      PPWord word = var.getWord();
      if (word != null) word.set(var.getValue());
   }

/**
 * Sets the left margin for the heap.
 *
 * @param margin The left margin
 */

   public void setLeftMargin(double margin) {
      heapX = bb.getX() + margin;
   }

/**
 * Sets the right margin for the stack.
 *
 * @param margin The right margin
 */

   public void setRightMargin(double margin) {
      stackX = bb.getX() + bb.getWidth() - margin;
   }

/**
 * Sets the top margin for the heap.
 *
 * @param margin The top margin
 */

   public void setTopMargin(double margin) {
      heapY = bb.getY() + margin;
   }

/**
 * Sets the bottom margin for the stack.
 *
 * @param margin The bottom margin
 */

   public void setBottomMargin(double margin) {
      stackY = bb.getY() + bb.getHeight() - margin;
   }

/**
 * Sets the font for the values in the stack
 *
 * @param font The font for the values in the stack
 */

   public void setFont(Font font) {
      valueFont = font;
   }

/**
 * Sets the font for the values in the stack.  In this version of
 * <code>setFont</code>, the font is expressed as a string in the form
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
 * @return The font used to display the values
 */

   public Font getFont() {
      return valueFont;
   }

/**
 * Sets the font for the labels used in the stack.
 *
 * @param font The font for the labels
 */

   public void setLabelFont(Font font) {
      labelFont = font;
   }

/**
 * Sets the font for the labels in this stack.  In this version of
 * <code>setLabelFont</code>, the font is expressed as a string in the form
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
 * @return The font used to display the labels
 */

   public Font getLabelFont() {
      return labelFont;
   }

/**
 * Sets the font for the addresses used in this stack.
 *
 * @param font The font for the addresses
 */

   public void setAddressFont(Font font) {
      addressFont = font;
   }

/**
 * Sets the font for the addresses used in this stack.  In this version of
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
 * @return The font used to display the addresses
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

/* Private constants */

   private static final String ADDRESS_FONT = "Helvetica Neue-Bold-14";
   private static final String LABEL_FONT = "Courier New-Bold-18";
   private static final String VALUE_FONT = "Courier New-Bold-18";
   private static final double BOTTOM_MARGIN = 16;
   private static final double BOUNDARY_HEIGHT = 4;
   private static final double LEFT_MARGIN = 40;
   private static final double RIGHT_MARGIN = 40;
   private static final double TOP_MARGIN = 1;

/* Private instance variables */

   private Font addressFont;
   private Font labelFont;
   private Font valueFont;
   private HashMap<Integer,PPWord> addrMap;
   private PPCodeTraceSlide slide;
   private Stack<PPWord> callStack;
   private String orientation;
   private Rectangle2D bb;
   private double heapX;
   private double heapY;
   private double stackX;
   private double stackY;
   private int hp;
   private int sp;

}
