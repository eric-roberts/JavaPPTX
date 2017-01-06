/*
 * File: PPSimpleExamples.java
 * ---------------------------
 * This example creates slides that illustrate individual features
 * of the edu.stanford.cs.pptx package.
 */

import edu.stanford.cs.pptx.*;
import java.awt.*;

/**
 * This class creates a PowerPoint show with several slides, each
 * of which illustrates the operation of some feature of the
 * <code>edu.stanford.cs.pptx</code> package in isolation.
 */

public class PPSimpleExamples {

/*
 * The main program creates a PowerPoint show and then adds slides for
 * individual tests.
 */

   public static void main(String[] args) {
      PPShow ppt = new PPShow();
      ppt.add(simplePPLineTest());
      ppt.add(simplePPRectTest());
      ppt.add(simplePPOvalTest());
      ppt.add(simplePPTextBoxTest());
      ppt.add(simplePPPictureTest());
      ppt.add(simplePPGroupTest());
      ppt.save("PPSimpleExamples.pptx");
      System.out.println("PPSimpleExamples.pptx");
   }

   private static PPSlide simplePPLineTest() {
      PPSlide slide = new PPSlide();
      slide.addTitle("PPLineTest");
      double width = slide.getWidth();
      double height = slide.getHeight();
      PPLine diagonalNWtoSE = new PPLine(0, 0, width, height);
      PPLine diagonalSWtoNE = new PPLine(0, height, width, 0);
      slide.add(diagonalNWtoSE);
      slide.add(diagonalSWtoNE);
      return slide;
   }

   private static PPSlide simplePPRectTest() {
      PPSlide slide = new PPSlide();
      slide.addTitle("PPRectTest");
      PPRect square = new PPRect(100, 100);
      square.setColor(Color.RED);
      slide.add(square, 200, 100);
      return slide;
   }

   private static PPSlide simplePPOvalTest() {
      PPSlide slide = new PPSlide();
      slide.addTitle("PPOvalTest");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      PPOval circle = new PPOval(xc - 50, yc - 50, 100, 100);
      slide.add(circle);
      return slide;
   }

   private static PPSlide simplePPTextBoxTest() {
      PPSlide slide = new PPSlide();
      slide.addTitle("PPTextBoxTest");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      PPTextBox hello = new PPTextBox("hello, world");
      hello.setFont("Helvetica-Bold-36");
      slide.add(hello, xc - hello.getWidth() / 2, yc - hello.getHeight() / 2);
      return slide;
   }

   private static PPSlide simplePPPictureTest() {
      PPSlide slide = new PPSlide();
      slide.addTitle("PPPictureTest");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      PPPicture logo = new PPPicture("images/StanfordLogo.png");
      logo.setBounds(xc - 100, yc - 100, 200, 200);
      slide.add(logo);
      return slide;
   }

   private static PPSlide simplePPGroupTest() {
      PPSlide slide = new PPSlide();
      slide.addTitle("PPGroupTest");
      double xc = slide.getWidth() / 2;
      double yc = slide.getHeight() / 2;
      PPGroup group = new PPGroup();
      group.add(new PPRect(200, 100, "/color:red"));
      group.add(new PPOval(200, 100, "/color:green"));
      slide.add(group, xc - group.getWidth() / 2, yc - group.getHeight() / 2);
      group.appear();
      return slide;
   }

}
