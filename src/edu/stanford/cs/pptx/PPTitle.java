/*
 * File: PPTitle.java
 * ------------------
 * This package class represents the title of a PowerPoint slide.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.util.PPOutputStream;
import edu.stanford.cs.pptx.util.PPUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;

class PPTitle extends PPTextShape {

   public PPTitle(String title) {
      setText(title);
   }

   @Override
   protected void dumpShape(PPOutputStream os) {
      os.print("<p:sp>");
      os.print("<p:nvSpPr>");
      os.print("<p:cNvPr id='" + getShapeId() + "' name='Title 1'/>");
      os.print("<p:cNvSpPr>");
      os.print("<a:spLocks noGrp='1'/>");
      os.print("</p:cNvSpPr>");
      os.print("<p:nvPr>");
      os.print("<p:ph type='title'/>");
      os.print("</p:nvPr>");
      os.print("</p:nvSpPr>");
      os.print("<p:spPr>");
      os.print("<a:xfrm>");
      Point2D pt = getInitialLocation();
      os.print("<a:off " + os.getOffsetTag(pt.getX(), pt.getY()) + "/>");
      os.print("<a:ext cx='" + PPUtil.pointsToUnits(getWidth()) + "' " +
               "cy='" + PPUtil.pointsToUnits(getHeight()) + "'/>");
      os.print("</a:xfrm>");
      os.print("</p:spPr>");
      os.print("<p:txBody>");
      os.print("<a:bodyPr/>");
      os.print("<a:lstStyle/>");
      os.print("<a:p>");
      os.print("<a:r>");
      os.print("<a:rPr lang='en-US' " + getSizeTag() +
               " dirty='0' smtClean='0'>");
      Font font = getFont();
      Color color = getFontColor();
      String text = getText();
      if (text == null) text = "";
      if (color != null) {
         os.print("<a:solidFill>");
         os.print(PPUtil.getColorTag(color));
         os.print("</a:solidFill>");
      }
      os.print("<a:latin typeface='" + font.getFamily() + "'/>");
      os.print("<a:cs typeface='" + font.getFamily() + "'/>");
      os.print("</a:rPr>");
      os.print("<a:t>" + escapeXML(text) + "</a:t>");
      os.print("</a:r>");
      os.print("<a:endParaRPr lang='en-US' " + getSizeTag() + " dirty='0'>");
      if (color != null) {
         os.print("<a:solidFill>");
         os.print(PPUtil.getColorTag(color));
         os.print("</a:solidFill>");
      }
      os.print("<a:latin typeface='" + font.getFamily() + "'/>");
      os.print("<a:cs typeface='" + font.getFamily() + "'/>");
      os.print("</a:endParaRPr>");
      os.print("</a:p>");
      os.print("</p:txBody>");
      os.print("</p:sp>");
   }

}
