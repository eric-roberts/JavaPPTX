/*
 * File: PPCodeImage.java
 * ----------------------
 * This class makes it possible to create an image of code that is
 * compatible across platforms, even when the font metrics change.
 */

package edu.stanford.cs.pptx.code;

import edu.stanford.cs.pptx.PPPicture;
import edu.stanford.cs.pptx.util.PPUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * This class is a <code>PPPicture</code> subclass containing an image of
 * a block of code rendered at high resolution.  The advantage of using
 * the <code>PPCodeImage</code> shape type is that font metrics work
 * compatibly across platforms.
 *
 * <p>The following code generates a simple <code>PPCodeImage</code>
 * containing the C code for the classic <code>"hello, world"</code>
 * program:</p>
 *
 *<pre>
 *    PPShow ppt = new PPShow();
 *    PPSlide slide = new PPSlide();
 *    double codeWidth = 300;
 *    double codeHeight = 200;
 *    String[] code = { "function run() {",
 *                      "   println(\"hello, world\\n\");",
 *                      "}" };
 *    double xc = slide.getWidth() / 2;
 *    double yc = slide.getHeight() / 2;
 *    PPCodeImage cimage = new PPCodeImage(codeWidth, codeHeight);
 *    cimage.setFont("Monospaced-Bold-24");
 *    cimage.setCode(code);
 *    PPRect cbox = new PPRect(codeWidth, codeHeight);
 *    cbox.setFillColor(Color.WHITE);
 *    slide.add(cbox, xc - codeWidth / 2, yc - codeHeight / 2);
 *    slide.add(cimage, xc - codeWidth / 2, yc - codeHeight / 2);
 *    ppt.add(slide);
 *</pre>
 */

public class PPCodeImage extends PPPicture {

/**
 * Creates a <code>PPCodeImage</code> with the specified dimensions.
 * Clients should set all parameters (font, size, margins) and then
 * call <code>setCode</code> to finalize the image.
 *
 * @param width The width of the image
 * @param height The height of the image
 */

   public PPCodeImage(double width, double height) {
      this.width = width;
      this.height = height;
      setBackground(Color.WHITE);
      setLeftMargin(DEFAULT_LEFT_MARGIN);
      setTopMargin(DEFAULT_TOP_MARGIN);
      setSpacing(DEFAULT_SPACING);
      setScaleFactor(DEFAULT_SF);
      setFont(DEFAULT_FONT);
      setBounds(0, 0, width, height);
   }

/**
 * Sets the code and finalizes the image.
 *
 * @param code An array of lines containing the code
 */

   public void setCode(String[] code) {
      this.code = code;
      setImage(createCodeImage());
      setScale(1.0 / sf);
   }

/**
 * Sets the background color for the code image.
 *
 * @param color The background color for the code image
 */

   public void setBackground(Color color) {
      bg = color;
   }

/**
 * Gets the background color for the code image.
 *
 * @return The background color for the code image
 */

   public Color setBackground() {
      return bg;
   }

/**
 * Sets the line spacing.
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
      return spacing;
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
      return font;
   }

/**
 * Sets the scale factor, which determines the resolution of the image.
 *
 * @param sf The scale factor
 */

   public void setScaleFactor(double sf) {
      this.sf = sf;
   }

/**
 * Gets the scale factor.
 *
 * @return The scale factor
 */

   public double getScaleFactor() {
      return sf;
   }

/**
 * Sets the left margin for the code box.
 *
 * @param margin The left margin for the code box
 */

   public void setLeftMargin(double margin) {
      leftMargin = margin;
   }

/**
 * Gets the left margin for the code box.
 *
 * @return The left margin for the code box
 */

   public double getLeftMargin() {
      return leftMargin;
   }

/**
 * Sets the top margin for the code box.
 *
 * @param margin The top margin for the code box
 */

   public void setTopMargin(double margin) {
      topMargin = margin;
   }

/**
 * Gets the top margin for the code box.
 *
 * @return The top margin for the code box
 */

   public double getTopMargin() {
      return topMargin;
   }

/**
 * Returns the rectangular region that bounds the specified line.
 *
 * @param line The line number
 * @return A <code>Rectangle2D</code> that encloses the entire line
 */

   public Rectangle2D getCodeBounds(int line) {
      String str = code[line];
      int p1 = 0;
      while (p1 < str.length() && str.charAt(p1) == ' ') {
         p1++;
      }
      int p2 = str.length();
      while (p2 >= 0 && str.charAt(p2 - 1) == ' ') {
         p2--;
      }
      return getCodeBounds(line, p1, p2);
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
      String str = code[line];
      String prefix = str.substring(0, p1);
      FontMetrics fm = PPUtil.getEmptyContainer().getFontMetrics(font);
      double x = getX() + leftMargin + fm.stringWidth(prefix) + X_DELTA;
      double y = getY() + topMargin + line * spacing + Y_DELTA;
      str = str.substring(p1, p2);
      double width = fm.stringWidth(str) + W_DELTA;
      double height = spacing + H_DELTA;
      if (str.endsWith(")") || str.endsWith("]") || str.endsWith("}")) {
         width += P_DELTA;
      }
      return new Rectangle2D.Double(x, y, width, height);
   }

/* Protected methods */

/**
 * Paints the code image using the graphics context.  Subclasses can
 * override this method to change the syntax coloring.  This version
 * uses blue for comments and black for all other text.
 *
 * @param g The graphics context
 */

// Fix comment coloring to allow multiple comments on a line

   protected void paintCodeImage(Graphics2D g) {
      FontMetrics fm = g.getFontMetrics();
      double dy = sf * spacing;
      boolean inComment = false;
      x0 = leftMargin;
      y0 = topMargin + fm.getAscent() / sf;
      int x = (int) Math.round(sf * x0);
      int y = (int) Math.round(sf * y0);
      for (int i = 0; i < code.length; i++) {
         String line = code[i];
         if (inComment) {
            g.setColor(Color.BLUE);
            int starSlash = line.indexOf("*" + "/");
            if (starSlash == -1) {
               g.drawString(line, x, y);
            } else {
               String prefix = line.substring(0, starSlash + 2);
               String suffix = line.substring(starSlash + 2);
               g.drawString(prefix, x, y);
               g.setColor(Color.BLACK);
               g.drawString(suffix, x + fm.stringWidth(prefix), y);
               inComment = false;
            }
         } else {
            int slashSlash = line.indexOf("/" + "/");
            if (slashSlash == -1) {
               int slashStar = line.indexOf("/" + "*");
               if (slashStar == -1) {
                  g.drawString(line, x, y);
               } else {
                  String prefix = line.substring(0, slashStar);
                  String suffix = line.substring(slashStar);
                  g.drawString(prefix, x, y);
                  int x1 = x + fm.stringWidth(prefix);
                  int starSlash = suffix.indexOf("*" + "/");
                  if (starSlash == -1) {
                     g.setColor(Color.BLUE);
                     g.drawString(suffix, x1, y);
                     inComment = true;
                  } else {
                     String comment = suffix.substring(0, starSlash + 2);
                     suffix = suffix.substring(starSlash + 2);
                     int x2 = x1 + fm.stringWidth(comment);
                     g.setColor(Color.BLUE);
                     g.drawString(comment, x1, y);
                     g.setColor(Color.BLACK);
                     g.drawString(suffix, x2, y);
                  }
               }
            }
         }   
         y = (int) Math.round(y + dy);
      }
   }

/* Private methods */

   private Image createCodeImage() {
      int iw = (int) (sf * width + 0.99);
      int ih = (int) (sf * height + 0.99);
      BufferedImage bi = new BufferedImage(iw, ih,
                                           BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = bi.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                         RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      Font scaledFont = font.deriveFont((float) (font.getSize() * sf));
      g.setColor(bg);
      g.fillRect(0, 0, iw, ih);
      g.setColor(Color.BLACK);
      g.setFont(scaledFont);
      paintCodeImage(g);
      return bi;
   }

/* Private constants */

   private static final String DEFAULT_FONT = "Courier New-Bold-14";
   private static final double DEFAULT_LEFT_MARGIN = 8;
   private static final double DEFAULT_SF = 2.0;
   private static final double DEFAULT_SPACING = 16;
   private static final double DEFAULT_TOP_MARGIN = 3;
   private static final double H_DELTA = -1;
   private static final double W_DELTA = 5;
   private static final double P_DELTA = -2;
   private static final double X_DELTA = -3;
   private static final double Y_DELTA = -1;

/* Private instance variables */

   private Color bg;
   private Font font;
   private String[] code;
   private double height;
   private double leftMargin;
   private double sf;
   private double spacing;
   private double topMargin;
   private double width;
   private double x0;
   private double y0;

}
