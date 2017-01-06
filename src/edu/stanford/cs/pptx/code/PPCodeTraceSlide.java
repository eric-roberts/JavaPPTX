/*
 * File: PPCodeTraceSlide.java
 * ---------------------------
 * This class defines a slide that can display a step-by-step trace of a
 * program.
 */

package edu.stanford.cs.pptx.code;

import edu.stanford.cs.pptx.PPSlide;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.Stack;
import java.util.TreeMap;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class defines a slide that can display a step-by-step trace of
 * a program.
 */

public class PPCodeTraceSlide extends PPSlide implements ChangeListener {

/**
 * Creates a new <code>PPCodeTraceSlide</code> object.
 */

   public PPCodeTraceSlide() {
      stack = new Stack<PPStackFrame>();
      functions = new TreeMap<String,PPFunction>();
      setStackFrameRegion(SIDE_MARGIN, TOP_MARGIN,
                          getWidth() - 2 * SIDE_MARGIN,
                          getHeight() - TOP_MARGIN);
      setFont(DEFAULT_FONT);
      setSpacing(DEFAULT_SPACING);
      setFrameHeight(DEFAULT_FRAME_HEIGHT);
      setMaxStackDepth(1);
   }

/**
 * Defines a function with the specified name.
 *
 * @param name The name of the function
 * @param fn The <code>PPFunction</code> that implements it
 */

   public void defineFunction(String name, PPFunction fn) {
      functions.put(name, fn);
      fn.setName(name);
      fn.setSlide(this);
   }

/**
 * Gets the function with the specified name.
 *
 * @param name The name of the function
 * @return The function with the specified name
 */

   public PPFunction getFunction(String name) {
      return functions.get(name);
   }

/**
 * Calls the named function with the specified arguments.
 *
 * @param name The function name
 * @param args The list of arguments
 * @return The value of the function
 */

   public Object call(String name, Object... args) {
      PPFunction fn = getFunction(name);
      PPStackFrame sf = fn.createStackFrame();
      sf.setCode(fn.getCode());
      sf.setArgumentList(args);
      add(sf);
      int depth = getStackDepth();
      if (depth > 0) sf.pushFrame();
      if (hsViewer != null && depth > 0) hsViewer.pushFrame(sf);
      pushFrame(sf);
      Object result = fn.stepThrough();
      popFrame();
      if (hsViewer != null && depth > 0) hsViewer.popFrame(sf);
      if (depth > 0) sf.popFrame();
      return result;
   }

/**
 * Sets the region on the slide used to display stack frames.
 *
 * @param x The left edge of the stack frame region
 * @param y The top edge of the stack frame region
 * @param width The width of the stack frame region
 * @param height The height of the stack frame region
 */

   public void setStackFrameRegion(double x, double y,
                                   double width, double height) {
      frameRegion = new Rectangle2D.Double(x, y, width, height);
   }

/**
 * Sets the region on the slide used to display a stack diagram.  If this
 * region is not initialized, no stack diagram is maintained.  This version
 * uses the default values for the display size.
 *
 * @param x The left edge of the heap-stack region
 * @param y The top edge of the heap-stack region
 */

   public void setHeapStackRegion(double x, double y) {
      hsViewer = new PPHeapStackViewer(this, x, y);
   }

/**
 * Sets the region on the slide used to display a stack diagram.  If this
 * region is not initialized, no stack diagram is maintained.
 *
 * @param x The left edge of the heap-stack region
 * @param y The top edge of the heap-stack region
 * @param width The width of the heap-stack region
 * @param height The height of the heap-stack region
 */

   public void setHeapStackRegion(double x, double y,
                                  double width, double height) {
      hsViewer = new PPHeapStackViewer(this, x, y, width, height);
   }

/**
 * Sets the maximum depth of the call stack.  This value is used to
 * calculate the size of each staggered frame.
 *
 * @param depth The maximum depth of the call stack
 */

   public void setMaxStackDepth(int depth) {
      maxDepth = depth;
   }

/**
 * Gets the heap-stack viewer.
 *
 * @return The heap-stack viewer
 */

   public PPHeapStackViewer getHeapStackViewer() {
      return hsViewer;
   }

/**
 * Adds a console to this slide with the specified title and bounds.
 *
 * @param title The title of the console
 * @param x The x coordinate of the left edge of the console
 * @param y The x coordinate of the top edge of the console
 * @param width The width of the console
 * @param height The height of the console
 */

   public void addConsole(String title, double x, double y,
                                        double width, double height) {
      console = new PPConsole(title, x, y, width, height);
      add(console);
   }

/**
 * Gets the console displayed on this slide.
 *
 * @return The console displayed on this slide
 */

   public PPConsole getConsole() {
      return console;
   }

/**
 * Prints a line to the console.
 *
 * @param obj The object to be printed
 */

   public void println(Object obj) {
      console.println(obj);
   }

/**
 * Advances the console to the next line.
 */

   public void println() {
      console.println();
   }

/**
 * Simulates reading an input line on the console.
 *
 * @param prompt The prompt string
 * @param input The user input
 */

   public void readLine(String prompt, String input) {
      console.readLine(prompt, input);
   }

/**
 * Sets the default frame height for functions that don't override it.
 *
 * @param height The height in pixels
 */

   public void setFrameHeight(double height) {
      frameHeight = height;
   }

/**
 * Gets the default frame height.
 *
 * @return The default frame height
 */

   public double getFrameHeight() {
      return frameHeight;
   }

/**
 * Sets the line spacing for stack frames in this trace.
 *
 * @param spacing The line spacing in points
 */

   public void setSpacing(double spacing) {
      this.spacing = spacing;
   }

/**
 * Gets the line spacing for stack frames in this trace.
 *
 * @return The line spacing in points
 */

   public double getSpacing() {
      return spacing;
   }

/**
 * Sets the font for stack frames in this trace.
 *
 * @param font The font for stack frames in this trace
 */

   public void setFont(Font font) {
      this.font = font;
   }

/**
 * Sets the font for stack frames in this trace.  In this version of
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
 * Gets the font for the code box.
 *
 * @return The font for the code box
 */

   public Font getFont() {
      return font;
   }

/**
 * Allocates a new heap word.
 *
 * @return A new heap word
 */

   public PPWord allocateWord() {
      return allocateWord(null);
   }

/**
 * Allocates a new heap word with the specified label.
 *
 * @param label The label for the word
 * @return A new heap word
 */

   public PPWord allocateWord(String label) {
      PPWord word = new PPWord();
      if (hsViewer != null) hsViewer.addToHeap(word, label);
      return word;
   }

/* ChangeListener */

   @Override
   public void stateChanged(ChangeEvent e) {
      PPVariable var = (PPVariable) e.getSource();
      hsViewer.updateVar(var);
   }

/* Protected methods */

   protected void pushFrame(PPStackFrame sf) {
      stack.push(sf);
   }

   protected void popFrame() {
      stack.pop();
   }

   protected void registerVariable(PPVariable var) {
      if (hsViewer != null) {
         var.addChangeListener(this);
         hsViewer.pushVar(var);
      }
   }
      
   protected int getStackDepth() {
      return stack.size();
   }

   protected PPStackFrame getCurrentFrame() {
      return (stack.isEmpty()) ? null : stack.peek();
   }

   protected Rectangle2D getFrameBounds() {
      double x = frameRegion.getX() + stack.size() * FRAME_DX;
      double y = frameRegion.getY() + stack.size() * FRAME_DY;
      double w = frameRegion.getWidth() - (maxDepth - 1) * FRAME_DX;
      double h = frameHeight;
      return new Rectangle2D.Double(x, y, w, h);
   }

/* Private constants */

   private static final String DEFAULT_FONT = "Courier New-Bold-18";
   private static final double DEFAULT_FRAME_HEIGHT = 300;
   private static final double DEFAULT_SPACING = 22;
   private static final double FRAME_DX = 10;
   private static final double FRAME_DY = 24;
   private static final double SIDE_MARGIN = 20;
   private static final double TOP_MARGIN = 85;

/* Private instance variables */

   private Font font;
   private PPConsole console;
   private PPHeapStackViewer hsViewer;
   private Rectangle2D frameRegion;
   private Stack<PPStackFrame> stack;
   private TreeMap<String,PPFunction> functions;
   private double frameHeight;
   private double spacing;
   private int maxDepth;

}
