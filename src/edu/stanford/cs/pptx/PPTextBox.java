/*
 * File: PPTextBox.java
 * --------------------
 * This file defines the PPTextBox class for manipulating text
 * boxes in a PowerPoint slide.
 */

package edu.stanford.cs.pptx;

/**
 * This class simulates a PowerPoint text box using a <code>PPRect</code>
 * with no fill and no line.  The usual approach to working with a
 * <code>PPTextBox</code> shape is to create a new <code>PPTextBox</code>
 * object and then to add the object to a slide, as illustrated in the
 * following code, which displays the string <code>"hello, world"</code>
 * at the middle of the window.
 *
 *<pre>
 *    PPSlide slide = new PPSlide();
 *    double xc = slide.getWidth() / 2;
 *    double yc = slide.getHeight() / 2;
 *    PPTextBox hello = new PPTextBox("hello, world");
 *    hello.setFont("Helvetica-Bold-36");
 *    slide.add(hello, xc - hello.getWidth() / 2, yc - hello.getHeight() / 2);
 *</pre>
 */

public class PPTextBox extends PPRect {

/**
 * Creates an empty <code>PPTextBox</code> shape.  To use this shape,
 * you need to call <code>setText</code> to change the contents.
 */

   public PPTextBox() {
      setHorizontalAlignment("Left");
      setVerticalAlignment("Top");
      setLineColor("none");
      setFillColor("none");
   }

/**
 * Creates a new <code>PPTextBox</code> shape and sets its contents to
 * the specified string.  The bounds of the text box are set to fit
 * the string.
 *
 * @param str The string contained in the text box
 */

   public PPTextBox(String str) {
      this();
      setText(str);
   }

}
