/*
 * File: PPAutoShape.java
 * ----------------------
 * This file implements the class representing a PowerPoint AutoShape object.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.util.PPOutputStream;
import edu.stanford.cs.pptx.util.PPUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;

/**
 * This abstract class corresponds to AutoShape objects in PowerPoint.
 * The AutoShape class supports a large variety of subclasses, each of
 * which can contain text.  The specific type of object is determined
 * by the result of the abstract method <code>presetGeometry</code>,
 * which must be defined for each subclass.
 */

public abstract class PPAutoShape extends PPTextShape {

/**
 * Returns a printable string representation of this shape.
 *
 * @return A printable string representation of this shape
 */

   public String toString() {
      String str = super.toString();
      return getTypeName() + ":" + str.substring(str.indexOf(':') + 1);
   }

/**
 * Sets the options for this shape.
 * The string may contain the following option specifications:
 *
 * <table width=100% border=0 cellspacing=6 cellpadding=0 summary="">
 * <tr><td><code>/fillColor:</code><i>color</i>
 *     <br><code>/fill:</code><i>color</i></td>
 *     <td>Sets the fill color for this shape.  Colors may be specified
 *         using their Java names or as a six-digit hexadecimal string
 *         with two digits for each of the red, green, and blue
 *         components.  Specifying <code>none</code> as the
 *         fill color indicates an unfilled shape.</td></tr>
 * <tr><td><code>/lineColor:</code><i>color</i>
 *     <br><code>/line:</code><i>color</i></td>
 *     <td>Sets the line color for this line using the same format
 *         for colors as in the <code>/fill</code> option.</td></tr>
 * <tr><td><code>/lineWeight:</code><i>pixels</i>
 *     <br><code>/weight:</code><i>pixels</i></td>
 *     <td>Specifies the line weight in pixels.</td></tr>
 * <tr><td><code>/font:</code><i>font</i></td>
 *     <td>Sets the font for any text embedded in this shape.
 *         Fonts are specified as strings in the form
 *         <code>&nbsp;&nbsp;&nbsp;"</code><i>family</i
 *         ><code>-</code><i>style</i
 *         ><code>-</code><i>size</i
 *         ><code>"</code></td></tr>
 * <tr><td><code>/align:</code><i>alignment</i></td>
 *     <td>Sets the alignment for any text embedded in this shape.
 *         The legal values for the <i>alignment</i> property are
 *         <code>left</code>,
 *         <code>right</code>,
 *         <code>center</code>, and
 *         <code>justify</code>.
 *         For convenience, these names are defined as option keys,
 *         so that you can use <code>/center</code> as a
 *         shorthand for <code>/align:center</code>.</td></tr>
 * </table>
 *
 * @param options A string specifying the options for this shape
 */

   public void setOptions(String options) {
      super.setOptions(options);
   }

/* Protected methods */

/**
 * Creates a new <code>PPAutoShape</code> object.
 */

   protected PPAutoShape() {
      setHorizontalAlignment("Center");
      setVerticalAlignment("Middle");
   }

/**
 * Returns the preset geometry for this shape, which is the value of the
 * <code>a:prstGeom</code> field in the OOXML specification.  A complete
 * list of the predefined shape types is available from the URL
 * <code><a href="http://www.datypic.com/sc/ooxml/a-prst-5.html"
 * >http://www.datypic.com/sc/ooxml/a-prst-5.html</a></code>.
 *
 * @return The preset geometry string for this shape
 */

   protected abstract String presetGeometry();

   @Override
   protected void dumpShape(PPOutputStream os) {
      Color fillColor = getFillColor();
      if (PPUtil.isBackgroundFill(fillColor)) {
         os.print("<p:sp useBgFill='1'>");
      } else {
         os.print("<p:sp>");
      }
      os.print("<p:nvSpPr>");
      os.print("<p:cNvPr id='" + getShapeId() + "' " +
               "name='" + getName() + "'/>");
      os.print("<p:cNvSpPr/>");
      os.print("<p:nvPr/>");
      os.print("</p:nvSpPr>");
      os.print("<p:spPr bwMode='auto'>");
      os.print("<a:xfrm>");
      Point2D pt = getInitialLocation();
      os.print("<a:off " + os.getOffsetTag(pt.getX(), pt.getY()) + "/>");
      os.print("<a:ext cx='" + PPUtil.pointsToUnits(getWidth()) + "' " +
               "cy='" + PPUtil.pointsToUnits(getHeight()) + "'/>");
      os.print("</a:xfrm>");
      os.print("<a:prstGeom prst='" + presetGeometry() + "'>");
      dumpShapeParameters(os);
      os.print("</a:prstGeom>");
      if (fillColor == null) {
         os.print("<a:noFill/>");
      } else if (!PPUtil.isBackgroundFill(fillColor)) {
         os.print("<a:solidFill>");
         os.print(PPUtil.getColorTag(fillColor));
         os.print("</a:solidFill>");
      }
      Color lineColor = getLineColor();
      if (lineColor == null) {
         os.print("<a:ln><a:noFill/></a:ln>");
      } else {
         os.print("<a:ln w='" + PPUtil.pointsToUnits(getLineWeight()) +
                  "' cap='flat' cmpd='sng' algn='ctr'>");
         os.print("<a:solidFill>");
         os.print(PPUtil.getColorTag(getLineColor()));
         os.print("</a:solidFill>");
         os.print("<a:prstDash val='solid'/>");
         os.print("<a:round/>");
         os.print("<a:headEnd" + getArrowTag(getStartArrow()) + "/>");
         os.print("<a:tailEnd" + getArrowTag(getEndArrow()) + "/>");
         os.print("</a:ln>");
      }
      os.print("<a:effectLst/>");
      os.print("</p:spPr>");
      os.print("<p:txBody>");
      os.print(getBodyTag());
      os.print("<a:prstTxWarp prst='textNoShape'>");
      os.print("<a:avLst/>");
      os.print("</a:prstTxWarp>");
      os.print("</a:bodyPr>");
      os.print("<a:lstStyle/>");
      os.print("<a:p>");
      os.print("<a:pPr marL='0' marR='0' indent='0' " +
               "algn='" + getHAlignTag() + "' " +
               "defTabSz='914400' rtl='0' eaLnBrk='0' fontAlgn='base' " +
               "latinLnBrk='0' hangingPunct='0'>");
      os.print("<a:lnSpc>");
      os.print("<a:spcPct val='" +
               (int) Math.round(100000 * getLineSpacing()) + "'/>");
      os.print("</a:lnSpc>");
      os.print("<a:spcBef>");
      os.print("<a:spcPct val='0'/>");
      os.print("</a:spcBef>");
      os.print("<a:spcAft>");
      os.print("<a:spcPct val='0'/>");
      os.print("</a:spcAft>");
      os.print("<a:buClrTx/>");
      os.print("<a:buSzTx/>");
      os.print("<a:buFontTx/>");
      os.print("<a:buNone/>");
      os.print("<a:tabLst/>");
      os.print("</a:pPr>");
      Font font = getFont();
      Color color = getFontColor();
      String text = getText();
      if (text == null) {
         os.print("<a:endParaRPr kumimoji='0' lang='en-US' " +
                  getSizeTag() + " " +
                  (font.isBold() ? "b='1' " : "b='0' ") +
                  (font.isItalic() ? "i='1' " : "i='0' ") +
                  "u='none' strike='noStrike' cap='none' " +
                  "normalizeH='0' baseline='0' smtClean='0'>");
      } else {
         os.print("<a:r><a:rPr kumimoji='0' lang='en-US' " +
                  getSizeTag() + " " +
                  (font.isBold() ? "b='1' " : "b='0' ") +
                  (font.isItalic() ? "i='1' " : "i='0' ") +
                  "u='none' strike='noStrike' cap='none' " +
                  "normalizeH='0' baseline='0' smtClean='0'>");
      }
      os.print("<a:ln>");
      os.print("<a:noFill/>");
      os.print("</a:ln>");
      os.print("<a:solidFill>");
      if (color == null) {
         os.print("<a:schemeClr val='tx1'/>");
      } else {
         os.print(PPUtil.getColorTag(color));
      }
      os.print("</a:solidFill>");
      os.print("<a:effectLst/>");
      os.print("<a:latin typeface='" + font.getFamily() +
               "' pitchFamily='84' charset='0'/>");
      if (text == null) {
         os.print("</a:endParaRPr>");
      } else {
         os.print("</a:rPr><a:t>" + escapeXML(text) + "</a:t></a:r>");
      }
      os.print("</a:p>");
      os.print("</p:txBody>");
      os.print("</p:sp>");
   }

   protected void dumpShapeParameters(PPOutputStream os) {
      os.print("<a:avLst/>");
   }

}
