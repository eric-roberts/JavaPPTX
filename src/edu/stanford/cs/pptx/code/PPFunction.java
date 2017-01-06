/*
 * File: PPFunction.java
 * ---------------------
 * This class represents a function that the user can step through on a
 * PPCodeTraceSlide.
 */

package edu.stanford.cs.pptx.code;

import edu.stanford.cs.pptx.PPRect;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

/**
 * This abstract class represents a function that the user can step
 * through on a <code>PPCodeTraceSlide</code>.  Concrete subclasses must
 * define a constructor that sets the code and size of the frame.
 * Subclasses must also override the <code>stepThrough</code> method.
 */

public abstract class PPFunction {

/**
 * Steps through the operation of the function, indicating the points
 * at which the user must click to continue by making calls to
 * <code>highlightLine</code>.  The <code>stepThrough</code> function
 * returns the value of the call, which should be <code>null</code>
 * for functions that have no return value.
 *
 * @return The value of the function
 */

   public abstract Object stepThrough();

/**
 * Gets the name of the function.
 *
 * @return The name of the function
 */

   public String getName() {
      return name;
   }

/**
 * Sets the frame height for this function.  Clients should call this method
 * only if the frame height is different from that set by the slide.
 *
 * @param height The frame height in pixels
 */

   public void setFrameHeight(double height) {
      frameHeight = height;
   }

/**
 * Gets the frame height for this function.
 *
 * @return The frame height in pixels
 */

   public double getFrameHeight() {
      return (frameHeight == 0) ? slide.getFrameHeight() : frameHeight;
   }

/**
 * Sets the line spacing for this function.  Clients should call this method
 * only if the line spacing is different from that set by the slide.
 *
 * @param spacing The line spacing in points
 */

   public void setSpacing(double spacing) {
      this.spacing = spacing;
   }

/**
 * Gets the line spacing.
 *
 * @return The line spacing in points
 */

   public double getSpacing() {
      return (spacing == 0) ? slide.getSpacing() : spacing;
   }

/**
 * Sets the font for the code box.
 *
 * @param font The font for the code box
 */

   public void setFont(Font font) {
      this.font = font;
   }

/**
 * Sets the font for the code box.  In this version of <code>setFont</code>,
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
 * Gets the font for the code box.
 *
 * @return The font for the code box
 */

   public Font getFont() {
      return (font == null) ? slide.getFont() : font;
   }

/**
 * Sets the code for the function.
 *
 * @param code An array of strings containing the code for the function
 */

   public void setCode(String[] code) {
      this.code = code;
   }

/**
 * Gets the code for the function.
 *
 * @return An array of strings containing the code for the function
 */

   public String[] getCode() {
      return code;
   }

/**
 * Adds a parameter variable.
 *
 * @param var The parameter variable
 */

   public void addParameter(PPVariable var) {
      var.setParameterFlag(true);
      addLocalVariable(var);
   }

/**
 * Adds a local variable.
 *
 * @param var The local variable
 */

   public void addLocalVariable(PPVariable var) {
      getCurrentFrame().addVariable(var);
   }

/**
 * Marks the start of the local variable declarations.
 */

   public void startDeclarations() {
      /* Empty */
   }

/**
 * Marks the end of the local variable declarations.
 */

   public void endDeclarations() {
      getCurrentFrame().initParameters();
   }

/**
 * Calls the named function with the specified arguments.
 *
 * @param name The function name
 * @param args The list of arguments
 * @return The value of the function
 */

   public Object call(String name, Object... args) {
      return slide.call(name, args);
   }

/**
 * Gets the console object, if any.
 *
 * @return The console object, or <code>null</code> if no console exists
 */

   public PPConsole getConsole() {
      return slide.getConsole();
   }

/**
 * Prints a line to the console.
 *
 * @param obj The object to be printed
 */

   public void println(Object obj) {
      slide.println(obj);
   }

/**
 * Advances the console to the next line.
 */

   public void println() {
      slide.println();
   }

/**
 * Simulates reading an input line on the console.
 *
 * @param prompt The prompt string
 * @param input The user input
 */

   public void readLine(String prompt, String input) {
      slide.readLine(prompt, input);
   }

/**
 * Highlights the specified line.
 *
 * @param line The line number
 */

   public void highlightLine(int line) {
      PPRect box = createHighlightBox(getCodeBounds(line));
      getSlide().add(box);
      box.appear("/afterPrev");
      box.disappear("/onClick");
   }

/**
 * Highlights the line in the frame containing the specified string.
 *
 * @param str The string you're looking for
 */

   public void highlightLine(String str) {
      highlightLine(findCodeLine(str));
   }

/**
 * Highlights the specified region of the line.
 *
 * @param line The line number
 * @param p1 The starting index in the line
 * @param p2 The ending index in the line
 */

   public void highlight(int line, int p1, int p2) {
      PPRect box = createHighlightBox(getCodeBounds(line, p1, p2));
      getSlide().add(box);
      box.appear("/afterPrev");
      box.disappear("/onClick");
   }

/**
 * Highlights the specified text.
 *
 * @param str The text you're looking for
 */

   public void highlight(String str) {
      int index = findCodeLine(str);
      String line = code[index];
      int p1 = line.indexOf(str);
      highlight(index, p1, p1 + str.length());
   }

/**
 * Creates a highlight box that encloses the specified region.
 *
 * @param bb The bounding box
 * @return A <code>PPRect</code> that encloses the box
 */

   public PPRect createHighlightBox(Rectangle2D bb) {
      PPRect box = new PPRect(bb);
      box.setFillColor("none");
      box.setLineColor(Color.RED);
      box.setLineWeight(2);
      return box;
   }

/**
 * Returns the rectangular region that bounds the specified line.
 *
 * @param line The line number
 * @return A <code>Rectangle2D</code> that encloses the entire line
 */

   public Rectangle2D getCodeBounds(int line) {
      return getCurrentFrame().getCodeBounds(line);
   }

/**
 * Returns the rectangular region that bounds the line containing the
 * specified string.
 *
 * @param str The string identifying the desired line
 * @return A <code>Rectangle2D</code> that encloses the entire line
 */

   public Rectangle2D getCodeBounds(String str) {
      return getCodeBounds(findCodeLine(str));
   }

/**
 * Returns the rectangular region that bounds the specified substring of
 * the code line.
 *
 * @param line The line number
 * @param p1 The index of the starting character
 * @param p2 The index of the character following the end
 * @return A <code>Rectangle2D</code> that encloses the code
 */

   public Rectangle2D getCodeBounds(int line, int p1, int p2) {
      return getCurrentFrame().getCodeBounds(line, p1, p2);
   }

/**
 * Returns the rectangular region that bounds the first occurrence of
 * <code>substring</code> on the specified line.
 *
 * @param line The line number
 * @param substring The substring you want to highlight
 * @return A <code>Rectangle2D</code> that encloses the code
 */

   public Rectangle2D getCodeBounds(int line, String substring) {
      String text = code[line];
      int p1 = text.indexOf(substring);
      return getCodeBounds(line, p1, p1 + substring.length());
   }

/**
 * Returns the rectangular region that bounds the first occurrence of
 * <code>substring</code> on the line containing <code>str</code>.
 *
 * @param str The string identifying the desired line
 * @param substring The substring you want to highlight
 * @return A <code>Rectangle2D</code> that encloses the code
 */

   public Rectangle2D getCodeBounds(String str, String substring) {
      int line = findCodeLine(str);
      String text = code[line];
      int p1 = text.indexOf(substring);
      return getCodeBounds(line, p1, p1 + substring.length());
   }

/**
 * Finds the code line containing the specified text.
 *
 * @param str The text you're looking for
 * @return Returns the line number from the code
 */

   public int findCodeLine(String str) {
      int nLines = code.length;
      for (int i = 0; i < nLines; i++) {
         if (code[i].contains(str)) return i;
      }
      return -1;
   }

/**
 * Allocates a new heap word.
 *
 * @return A new heap word
 */

   public PPWord allocateWord() {
      return slide.allocateWord();
   }

/**
 * Allocates a new heap word with the specified label.
 *
 * @param label The label for the word
 * @return A new heap word
 */

   public PPWord allocateWord(String label) {
      return slide.allocateWord(label);
   }

/* Protected methods */

   protected void setName(String name) {
      this.name = name;
   }

   protected void setSlide(PPCodeTraceSlide slide) {
      this.slide = slide;
   }

   protected PPCodeTraceSlide getSlide() {
      return slide;
   }

   protected PPStackFrame createStackFrame() {
      Rectangle2D bb = slide.getFrameBounds();
      bb.setRect(bb.getX(), bb.getY(), bb.getWidth(), getFrameHeight());
      PPStackFrame frame = new PPStackFrame(bb);
      frame.setFont(getFont());
      frame.setSpacing(getSpacing());
      return frame;
   }
      
   protected PPStackFrame getCurrentFrame() {
      return slide.getCurrentFrame();
   }

/* Private instance variables */

   private Font font;
   private PPCodeTraceSlide slide;
   private String name;
   private String[] code;
   private double frameHeight;
   private double spacing;

}
