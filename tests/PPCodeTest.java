/*
 * File: PPTCodeTest.java
 * ----------------------
 * This program is an evolving test of the pptx.code package.
 */

import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.code.PPCodeDisplaySlide;
import edu.stanford.cs.pptx.code.PPConsole;
import edu.stanford.cs.pptx.code.PPFunction;
import edu.stanford.cs.pptx.code.PPIntVar;
import edu.stanford.cs.pptx.code.PPValueTag;

public class PPCodeTest {

   public void run() {
      PPShow ppt = new PPShow();
      PPCodeDisplaySlide slide1 = new PPCodeDisplaySlide();
      slide1.addTitle("Page 1");
      slide1.setNewCode(PAGE1);
      ppt.add(slide1);
      PPCodeDisplaySlide slide2 = new PPCodeDisplaySlide();
      slide2.addTitle("Page 2");
      slide2.setOldCode(PAGE1);
      slide2.setNewCode(PAGE2);
      ppt.add(slide2);
      ppt.save("PPCodeTest.pptx");
      System.out.println("PPCodeTest.pptx");
   }

   public static void main(String[] args) {
      new PPCodeTest().run();
   }

   public static String[] PAGE1 = {
      "#include <iostream>",
      "#include \"gwindow.h\"",
      "#include \"random.h\"",
      "using namespace std;",
      "",
      "/* Constants */",
      "",
      "const double MIN_AREA = 10000;   " +
          "/* Smallest square that will be split */",
      "const double MIN_EDGE = 20;      " +
          "/* Smallest edge length allowed       */",
      "",
      "/* Function prototypes */",
      "",
      "void subdivideCanvas(GWindow & gw, double x, double y,",
      "                                   double width, double height);",
      "",
      "/* Main program */",
      "",
      "int main() {",
      "   GWindow gw;",
      "   subdivideCanvas(gw, 0, 0, gw.getWidth(), gw.getHeight());",
      "   return 0;",
      "}"
   };

   public static String[] PAGE2 = {
      "void subdivideCanvas(GWindow & gw, double x, double y,",
      "                                   double width, double height) {",
      "   if (width * height >= MIN_AREA) {",
      "      if (width > height) {",
      "         double mid = randomReal(MIN_EDGE, width - MIN_EDGE);",
      "         subdivideCanvas(gw, x, y, mid, height);",
      "         subdivideCanvas(gw, x + mid, y, width - mid, height);",
      "         gw.drawLine(x + mid, y, x + mid, y + height);",
      "      } else {",
      "         double mid = randomReal(MIN_EDGE, height - MIN_EDGE);",
      "         subdivideCanvas(gw, x, y, width, mid);",
      "         subdivideCanvas(gw, x, y + mid, width, height - mid);",
      "         gw.drawLine(x, y + mid, x + width, y + mid);",
      "      }",
      "   }"
   };

}
