/*
 * File: PPTextShape.java
 * ----------------------
 * This abstract class represents the class of shapes that contain
 * text within an outline.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.options.OptionParser;
import edu.stanford.cs.pptx.util.PPUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;

/**
 * This abstract class represents the class of shapes that contain
 * text within an outline.
 */

public class PPTextShape extends PPSimpleShape {

   public PPTextShape() {
      text = "";
      lineSpacing = 1.0;
      setFont(new Font("Times New Roman", Font.PLAIN, 24));
      boundsFixed = false;
      wordWrapFlag = true;
   }

/**
 * Sets the text of the shape.  If no bounds have been explicitly set,
 * this method also sets the bounding box of the text according to the
 * contents.  Note that the origin of the bounding box is the upper
 * left corner and not the baseline.
 *
 * @param text The new contents for the text shape
 */

   public void setText(String text) {
      this.text = text;
      fixBounds();
   }

/**
 * Returns the text of this shape.
 *
 * @return The text inside this shape
 */

   public String getText() {
      return text;
   }

/**
 * Sets the font for the entire text.
 *
 * @param font The new font for the text
 */

   public void setFont(Font font) {
      this.font = font;
      fm = PPUtil.getEmptyContainer().getFontMetrics(font);
      fixBounds();
   }

/**
 * Sets the font for the entire text.  In this version of
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
 * Returns the text font.
 *
 * @return The font for the text
 */

   public Font getFont() {
      return font;
   }

/**
 * Returns the metrics for the text font.  Unfortunately, these metrics
 * are not compatible across platforms.
 *
 * @return The font metrics for the TextBox
 */

   public FontMetrics getFontMetrics() {
      return fm;
   }

/**
 * Sets the font color.
 *
 * @param color The new font color
 */

   public void setFontColor(Color color) {
      this.color = color;
   }

/**
 * Sets the font color to the color indicated by <code>str</code>,
 * which is either a standard color name or a string in the
 * form <code>"#rrggbb"</code>.
 *
 * @param str The new color expressed as a string
 */

   public void setFontColor(String str) {
      setFontColor(PPUtil.decodeColor(str));
   }

/**
 * Returns the font color.
 *
 * @return The font color for the text
 */

   public Color getFontColor() {
      return color;
   }

/**
 * Sets the horizontal alignment for the text in this shape.
 * The alignment must be one of the following strings: <code>"Left"</code>,
 * <code>"Center"</code>, <code>"Right"</code>, or <code>"Justify"</code>.
 *
 * @param alignment A string specifying the horizontal alignment
 */

   public void setHorizontalAlignment(String alignment) {
      hAlign = alignment;
   }

/**
 * Returns the horizontal alignment for the text in this shape. The
 * alignment will always be one of the following strings: <code>"Left"</code>,
 * <code>"Center"</code>, <code>"Right"</code>, or <code>"Justify"</code>.
 *
 * @return A string specifying the horizontal alignment
 */

   public String getHorizontalAlignment() {
      return hAlign;
   }

/**
 * Sets the vertical alignment for the text in this shape.
 * The alignment must be one of the following strings: <code>"Top"</code>,
 * <code>"Middle"</code>, or <code>"Bottom"</code>.
 *
 * @param alignment A string specifying the horizontal alignment
 */

   public void setVerticalAlignment(String alignment) {
      vAlign = alignment;
   }

/**
 * Returns the vertical alignment for the text in this shape.  The
 * alignment will always be one of the following strings: <code>"Top"</code>,
 * <code>"Middle"</code>, or <code>"Bottom"</code>.
 *
 * @return A string specifying the vertical alignment
 */

   public String getVerticalAlignment() {
      return vAlign;
   }

/**
 * Sets the line spacing for the text in this shape.  The line spacing is
 * proportional to the font size so that 1.0 represents normal spacing,
 * 2.0 is double spacing, and so on.
 *
 * @param spacing The line spacing
 */

   public void setLineSpacing(double spacing) {
      lineSpacing = spacing;
   }

/**
 * Returns the current line spacing.
 *
 * @return The current line spacing
 */

   public double getLineSpacing() {
      return lineSpacing;
   }

/**
 * Sets the word wrap flag that indicates whether text in the box
 * wraps around at the end of each line.
 *
 * @param flag The new setting of the word wrap flag
 */

   public void setWordWrapFlag(boolean flag) {
      wordWrapFlag = flag;
   }

/**
 * Returns the current setting of the word wrap flag.
 *
 * @return The current setting of the word wrap flag
 */

   public boolean getWordWrapFlag() {
      return wordWrapFlag;
   }

/**
 * Sets the left margin for the text.
 *
 * @param margin The new left margin
 */

   public void setLeftMargin(double margin) {
      marginLeft = margin;
   }

/**
 * Returns the left margin for the text.
 *
 * @return The left margin for the text box
 */

   public double getLeftMargin() {
      return marginLeft;
   }

/**
 * Sets the right margin for the text.
 *
 * @param margin The new right margin
 */

   public void setRightMargin(double margin) {
      marginRight = margin;
   }

/**
 * Returns the right margin for the text.
 *
 * @return The right margin for the text box
 */

   public double getRightMargin() {
      return marginRight;
   }

/**
 * Sets the top margin for the text.
 *
 * @param margin The new top margin
 */

   public void setTopMargin(double margin) {
      marginTop = margin;
   }

/**
 * Returns the top margin for the text.
 *
 * @return The top margin for the text box
 */

   public double getTopMargin() {
      return marginTop;
   }

/**
 * Sets the bottom margin for the text.
 *
 * @param margin The new bottom margin
 */

   public void setBottomMargin(double margin) {
      marginBottom = margin;
   }

/**
 * Returns the bottom margin for the text.
 *
 * @return The bottom margin for the text box
 */

   public double getBottomMargin() {
      return marginBottom;
   }

/**
 * Sets the initial bounding rectangle for this shape on the slide.
 *
 * @param bounds A <code>Rectangle2D</code> value specifying the initial bounds
 */

   public void setBounds(Rectangle2D bounds) {
      super.setBounds(bounds);
      boundsFixed = true;
   }

/* Protected methods */

   protected String escapeXML(String str) {
      String html = "";
      int n = str.length();
      for (int i = 0; i < n; i++) {
         char ch = str.charAt(i);
         switch (ch) {
          case '<': html += "&lt;"; break;
          case '&': html += "&amp;"; break;
          case '>': html += "&gt;"; break;
          default: html += ch; break;
         }
      }
      return html;
   }

   protected String getHAlignTag() {
      if (hAlign.equalsIgnoreCase("Left")) return "l";
      if (hAlign.equalsIgnoreCase("Right")) return "r";
      if (hAlign.equalsIgnoreCase("Justify")) return "just";
      return "ctr";
   }

   protected String getVAlignTag() {
      if (vAlign.equalsIgnoreCase("Top")) return "t";
      if (vAlign.equalsIgnoreCase("Bottom")) return "b";
      return "ctr";
   }

   protected String getSizeTag() {
      return "sz='" + Math.round(100 * font.getSize2D()) + "'";
   }

   protected String getBodyTag() {
      return "<a:bodyPr vert='horz' wrap='square' " +
             "lIns='" + PPUtil.pointsToUnits(marginLeft) +
             "' tIns='" + PPUtil.pointsToUnits(marginTop) +
             "' rIns='" + PPUtil.pointsToUnits(marginRight) +
             "' bIns='" + PPUtil.pointsToUnits(marginBottom) +
             "' anchor='" + getVAlignTag() + "' anchorCtr='0' " +
             "numCol='1' rtlCol='0' compatLnSpc='1'>";
   }

   protected OptionParser createOptionParser() {
      return new PPTextShapeOptionParser(this);
   }

   protected void fixBounds() {
      if (!boundsFixed) {
         int width = PPUtil.getPaddedWidth(text, fm);
         int height = fm.getHeight();
         Rectangle2D r = new Rectangle2D.Double(getX(), getY(), width, height);
         setBounds(r);
         boundsFixed = false;
      }
   }

/* Private instance variables */

   private Color color;
   private Font font;
   private FontMetrics fm;
   private String text;
   private String hAlign;
   private String vAlign;
   private boolean boundsFixed;
   private boolean wordWrapFlag;
   private double lineSpacing;
   private double marginBottom;
   private double marginLeft;
   private double marginRight;
   private double marginTop;

}
