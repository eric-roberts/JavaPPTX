/*
 * File: PPSimpleShapes.java
 * -------------------------
 * This program offers a simple example of the edu.stanford.cs.pptx package.
 */

import edu.stanford.cs.pptx.PPOval;
import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.PPSlide;
import java.awt.Color;

/**
 * This class creates a PowerPoint show whose single slide
 * begins with a red square and then superimposes a green circle
 * when the user clicks the mouse.
 */

public class PPSimpleShapes {

   public static void main(String[] args) {
      PPShow ppt = new PPShow();
      PPSlide slide = new PPSlide();
      slide.addTitle("Simple Shapes");
      double x = (ppt.getWindowWidth() - SQUARE_SIZE) / 2;
      double y = (ppt.getWindowHeight() - SQUARE_SIZE) / 2;
      PPRect square = new PPRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
      square.setColor(Color.RED);
      PPOval circle = new PPOval(x, y, SQUARE_SIZE, SQUARE_SIZE);
      circle.setColor(Color.GREEN);
      slide.add(square);
      slide.add(circle);
      circle.appear();
      square.disappear();
      ppt.add(slide);
      ppt.save("PPSimpleShapes.pptx");
      System.out.println("PPSimpleShapes.pptx");
   }

   private static final double SQUARE_SIZE = 200;

}
