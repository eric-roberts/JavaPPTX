/*
 * File: PPStackFrame.java
 * -----------------------
 * This class represents a stack frame in the pptx.code package.
 */

package edu.stanford.cs.pptx.code;

import edu.stanford.cs.pptx.PPGroup;
import edu.stanford.cs.pptx.PPRect;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * This class represents a stack frame in the <code>pptx.code</code>
 * package.
 */

public class PPStackFrame extends PPGroup {

/**
 * Creates a <code>PPStackFrame</code> with the specified bounds.
 *
 * @param x The x coordinate of the upper left corner
 * @param y The y coordinate of the upper left corner
 * @param width The width of the stack frame in pixels
 * @param height The height of the stack frame in pixels
 */

   public PPStackFrame(double x, double y, double width, double height) {
      background = new PPRect(x, y, width, height);
      border = new PPRect(x, y, width, height);
      background.setColor(Color.WHITE);
      add(background);
      codeImage = new PPCodeImage(width - 1, height - 1);
      codeImage.setInitialLocation(x, y);
      add(codeImage);
      border.setFillColor("none");
      border.setLineColor(Color.BLACK);
      add(border);
      variables = new ArrayList<PPVariable>();
   }

/**
 * Creates a <code>PPStackFrame</code> from the bounding box.
 *
 * @param bb The bounding box
 */

   public PPStackFrame(Rectangle2D bb) {
      this(bb.getX(), bb.getY(), bb.getWidth(), bb.getHeight());
   }

/**
 * Adds a new variable to the stack frame.
 *
 * @param var The variable to be added
 */

   public void addVariable(PPVariable var) {
      double dx = -(var.getVarWidth() + VAR_SEP);
      for (PPVariable v : variables) {
         v.setInitialLocation(v.getX() + dx, v.getY());
      }
      var.setSlide(getSlide());
      double x = border.getX() + border.getWidth() + dx;
      double y = border.getY() + border.getHeight() - VAR_SEP -
                 var.getVarHeight();
      add(var, x, y);
      variables.add(var);
   }

/**
 * Makes this stack frame appear as if in a function call.  The standard
 * visual model is to have the frame fly in from the bottom right.
 */

   public void pushFrame() {
      pushFrame("FlyIn/afterPrev/fromBottomRight/veryFast");
   }

/**
 * Makes this stack frame appear as if in a function call.  This version
 * of the function takes an option string, which can be used to override
 * the default animation behavior.
 *
 * @param options The option string
 */

   public void pushFrame(String options) {
      addAnimation(options);
   }

/**
 * Makes this stack frame disappear as when a function returns.  The
 * standard visual model is to have the frame zoom and fade out.
 */

   public void popFrame() {
      popFrame("FadedZoomOut/afterPrev/veryFast");
   }

/**
 * Makes this stack frame disappear as when a function returns.  This version
 * of the function takes an option string, which can be used to override
 * the default animation behavior.
 *
 * @param options The option string
 */

   public void popFrame(String options) {
      for (PPVariable var : variables) {
         var.clear();
      }
      addAnimation(options);
   }

/**
 * Sets the code for the stack frame.  Clients must set all the properties
 * that affect the appearance of the code before calling <code>setCode</code>.
 *
 * @param code The array of lines that comprise the code
 */

   public void setCode(String[] code) {
      codeImage.setCode(code);
   }

/**
 * Sets the line spacing.
 *
 * @param spacing The line spacing in points
 */

   public void setSpacing(double spacing) {
      codeImage.setSpacing(spacing);
   }

/**
 * Sets the font for the code.
 *
 * @param font The font for the code
 */

   public void setFont(Font font) {
      codeImage.setFont(font);
   }

/**
 * Sets the font for the code.  In this version of <code>setFont</code>,
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
 * Sets the left margin for the code box.
 *
 * @param margin The left margin
 */

   public void setLeftMargin(double margin) {
      codeImage.setLeftMargin(margin);
   }

/**
 * Sets the top margin for the code box.
 *
 * @param margin The top margin
 */

   public void setTopMargin(double margin) {
      codeImage.setTopMargin(margin);
   }

/**
 * Returns the rectangular region that bounds the specified line.
 *
 * @param line The line number
 * @return A <code>Rectangle2D</code> that encloses the entire line
 */

   public Rectangle2D getCodeBounds(int line) {
      return codeImage.getCodeBounds(line);
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
      return codeImage.getCodeBounds(line, p1, p2);
   }

/* Protected methods */

   protected ArrayList<PPVariable> getVariables() {
      return variables;
   }

   protected void setArgumentList(Object[] list) {
      args = list;
   }

   protected void initParameters() {
      int index = 0;
      for (PPVariable var : variables) {
         ((PPCodeTraceSlide) getSlide()).registerVariable(var);
         if (var.isParameter()) {
            var.setValueFromObject(args[index++]);
         }
      }
   }

/* Private constants */

   private static final double VAR_SEP = 6;

/* Private instance variables */

   private ArrayList<PPVariable> variables;
   private Object[] args;
   private PPCodeImage codeImage;
   private PPRect background;
   private PPRect border;

}
