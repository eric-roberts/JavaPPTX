/*
 * File: PPShapeTest.java
 * ----------------------
 * This example creates slides that test the individual shapes in the
 * edu.stanford.cs.pptx package.
 */

import edu.stanford.cs.pptx.PPGroup;
import edu.stanford.cs.pptx.PPLine;
import edu.stanford.cs.pptx.PPOval;
import edu.stanford.cs.pptx.PPOvalCallout;
import edu.stanford.cs.pptx.PPPicture;
import edu.stanford.cs.pptx.PPPie;
import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.PPSlide;
import edu.stanford.cs.pptx.PPStar;
import edu.stanford.cs.pptx.PPTextBox;
import java.awt.Color;

/**
 * This class creates a Powerpoint show with several slides, each
 * of which illustrates one of the basic shapes in the
 * <code>edu.stanford.cs.pptx</code> package in isolation.
 */

public class PPShapeTest {

   public void run() {
      PPShow ppt = new PPShow();
      ppt.add(testDiagonalsSlide());
      ppt.add(testLineSlide());
      ppt.add(testRectSlide());
      ppt.add(testOvalSlide());
      ppt.add(testStarSlide());
      ppt.add(testPieSlide());
      ppt.add(testOvalCalloutSlide());
      ppt.add(testTextBoxSlide());
      ppt.add(testPictureSlide());
      ppt.add(testGroupSlide());
      ppt.save("PPShapeTest.pptx");
      System.out.println("PPShapeTest.pptx");
   }

   private PPSlide testDiagonalsSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Test Diagonals");
      slide.add(new PPLine(0, 0, slide.getWidth(), slide.getHeight()));
      slide.add(new PPLine(0, slide.getHeight(), slide.getWidth(), 0));
      return slide;
   }

   private PPSlide testLineSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Test PPLine Class");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      double r = 100;
      double dx = 0;
      double dy = 0;
      Color color = null;
      for (int i = 0; i < 8; i++) {
         switch (i) {
          case 0: dx = 0; dy = -r; color = Color.RED; break;
          case 1: dx = r; dy = -r; color = Color.ORANGE; break;
          case 2: dx = r; dy = 0; color = Color.YELLOW; break;
          case 3: dx = r; dy = r; color = Color.GREEN; break;
          case 4: dx = 0; dy = r; color = Color.CYAN; break;
          case 5: dx = -r; dy = r; color = Color.BLUE; break;
          case 6: dx = -r; dy = 0; color = Color.MAGENTA; break;
          case 7: dx = -r; dy = -r; color = Color.PINK; break;
         }
         PPLine line = new PPLine(xc, yc, xc + dx, yc + dy);
         line.setColor(color);
         line.setLineWeight(2);
         slide.add(line);
      }
      return slide;
   }

   private PPSlide testRectSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Test PPRect Class");
      PPRect rect1 = new PPRect(200, 100);
      rect1.setFillColor(Color.RED);
      rect1.setText("Red Fill");
      slide.add(rect1, slide.getWidth() / 3 - rect1.getWidth() / 2,
                       slide.getHeight() / 3 - rect1.getHeight() / 2);
      PPRect rect2 = new PPRect(200, 100);
      rect2.setFillColor("none");
      rect2.setText("No Fill");
      slide.add(rect2, 2 * slide.getWidth() / 3 - rect2.getWidth() / 2,
                       slide.getHeight() / 3 - rect2.getHeight() / 2);
      PPRect rect3 = new PPRect(200, 100);
      rect3.setFillColor(Color.RED);
      rect3.setLineColor("none");
      rect3.setText("No Line");
      slide.add(rect3, slide.getWidth() / 3 - rect3.getWidth() / 2,
                       2 * slide.getHeight() / 3 - rect3.getHeight() / 2);
      PPRect rect4 = new PPRect(200, 100);
      rect4.setFillColor("bg");
      rect4.setText("Background Fill");
      slide.add(rect4, 2 * slide.getWidth() / 3 - rect4.getWidth() / 2,
                       2 * slide.getHeight() / 3 - rect4.getHeight() / 2);
      return slide;
   }

   private PPSlide testOvalSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Test PPOval Class");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      PPOval oval = new PPOval(xc - 100, yc - 50, 200, 100);
      oval.setFillColor(Color.WHITE);
      slide.add(oval);
      return slide;
   }

   private PPSlide testStarSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Test PPStar Class");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      PPStar star = new PPStar(300, 300);
      star.setFillColor(Color.RED);
      slide.add(star, xc - star.getWidth() / 2, yc - star.getHeight() / 2);
      return slide;
   }

   private PPSlide testOvalCalloutSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Test PPOvalCallout Class");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      PPOvalCallout callout = new PPOvalCallout(xc - 100, yc - 50, 200, 100);
      callout.setFillColor(new Color(0xFFFF66));
      callout.setWedgePoint(-125, -75);
      slide.add(callout);
      return slide;
   }

   private PPSlide testPieSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Test PPPie Class");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      PPPie pie = new PPPie(xc - 300, yc - 150, 150, 150, 45, 90);
      pie.setFillColor(Color.YELLOW);
      slide.add(pie);
      pie = new PPPie(xc + 150, yc - 150, 150, 150, -45, 180);
      pie.setFillColor(Color.GREEN);
      slide.add(pie);
      pie = new PPPie(xc - 300, yc + 50, 150, 150, 180, -90);
      pie.setFillColor(Color.MAGENTA);
      slide.add(pie);
      pie = new PPPie(xc + 150, yc + 50, 150, 150, -90, -180);
      pie.setFillColor(Color.BLUE);
      slide.add(pie);
      return slide;
   }

   private PPSlide testTextBoxSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Test PPTextBox Class");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      PPTextBox text = new PPTextBox("hello");
      slide.add(text, xc - text.getWidth() / 2, yc - text.getHeight() / 2);
      return slide;
   }

   private PPSlide testPictureSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Test PPPicture Class");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      PPPicture logo = new PPPicture("images/StanfordLogo.png");
      logo.setBounds(xc - 100, yc - 100, 200, 200);
      slide.add(logo);
      return slide;
   }

   private PPSlide testGroupSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Test PPGroup Class");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      PPGroup group = new PPGroup();
      PPRect rect = new PPRect(200, 100);
      rect.setColor(Color.RED);
      group.add(rect);
      PPOval oval = new PPOval(200, 100);
      oval.setColor(Color.GREEN);
      group.add(oval);
      slide.add(group, xc - group.getWidth() / 2, yc - group.getHeight() / 2);
      return slide;
   }

/* Main program */

   public static void main(String[] args) {
      new PPShapeTest().run();
   }

}
