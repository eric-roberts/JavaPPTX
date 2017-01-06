/*
 * File: PPEffectTest.java
 * -----------------------
 * This example creates slides that illustrate the animation effects in
 * the edu.stanford.cs.pptx package.
 */

import edu.stanford.cs.pptx.PPGroup;
import edu.stanford.cs.pptx.PPOval;
import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.PPSlide;
import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * This class creates a Powerpoint show with several slides, each
 * of which illustrates the operation of some feature of the
 * <code>edu.stanford.cs.pptx</code> package in isolation.
 */

public class PPEffectTest {

   public void run() {
      PPShow ppt = new PPShow();
      ppt.add(testAppearSlide());
      ppt.add(testCheckerboardSlide());
      ppt.add(testFadeSlide());
      ppt.add(testFadedZoomSlide());
      ppt.add(testFlyEffectSlide());
      ppt.add(testZoomSlide());
      ppt.add(testChangeColorSlide());
      ppt.add(testSpinSlide());
      ppt.add(testGrowSlide());
      ppt.add(testWipeSlide());
      ppt.add(testMotionPathSlide());
      ppt.save("PPEffectTest.pptx");
      System.out.println("PPEffectTest.pptx");
   }

   private PPSlide testAppearSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Appear Test");
      PPRect box = new PPRect(300, 60);
      double x = (slide.getWidth() - box.getWidth()) / 2;
      box.setFillColor(Color.WHITE);
      box.setText("Click to start");
      slide.add(box, x, 0.20 * slide.getHeight());
      box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Appears on click");
      slide.add(box, x, 0.40 * slide.getHeight());
      box.appear();
      box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Appears with previous");
      slide.add(box, x, 0.60 * slide.getHeight());
      box.appear("/withPrev");
      box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Appears one second later");
      slide.add(box, x, 0.80 * slide.getHeight());
      box.appear("/afterPrev/delay:1");
      return slide;
   }

   private PPSlide testCheckerboardSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Checkerboard Test");
      PPRect box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Click for checkerboard");
      slide.add(box, (slide.getWidth() - box.getWidth()) / 2,
                     (slide.getHeight() - box.getHeight()) / 2);
      box.addAnimation("CheckerboardOut/across");
      box.addAnimation("CheckerboardIn/down");
      return slide;
   }

   private PPSlide testFadeSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Fade Test");
      PPRect box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Click for fade test");
      slide.add(box, (slide.getWidth() - box.getWidth()) / 2,
                     (slide.getHeight() - box.getHeight()) / 2);
      box.addAnimation("FadeOut");
      box.addAnimation("FadeIn");
      return slide;
   }

   private PPSlide testFadedZoomSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("FadedZoom Test");
      PPRect box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Click for faded zoom test");
      slide.add(box, (slide.getWidth() - box.getWidth()) / 2,
                     (slide.getHeight() - box.getHeight()) / 2);
      box.addAnimation("FadedZoomOut");
      box.addAnimation("FadedZoomIn");
      return slide;
   }

   private PPSlide testFlyEffectSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Fly Effect Test");
      double x0 = (slide.getWidth() - 75) / 2;
      double y0 = slide.getHeight() / 2;
      PPRect nw = flyBox("NW");
      PPRect n = flyBox("N");
      PPRect ne = flyBox("NE");
      PPRect w = flyBox("W");
      PPRect e = flyBox("E");
      PPRect sw = flyBox("SW");
      PPRect s = flyBox("S");
      PPRect se = flyBox("SE");
      slide.add(nw, x0 - 100, y0 - 100);
      slide.add(n, x0, y0 - 100);
      slide.add(ne, x0 + 100, y0 - 100);
      slide.add(w, x0 - 100, y0);
      slide.add(e, x0 + 100, y0);
      slide.add(sw, x0 - 100, y0 + 100);
      slide.add(s, x0, y0 + 100);
      slide.add(se, x0 + 100, y0 + 100);
      nw.addAnimation("FlyIn/fromTopLeft/onClick");
      n.addAnimation("FlyIn/fromTop/afterPrev");
      ne.addAnimation("FlyIn/fromTopRight/afterPrev");
      w.addAnimation("FlyIn/fromLeft/afterPrev");
      e.addAnimation("FlyIn/fromRight/afterPrev");
      sw.addAnimation("FlyIn/fromBottomLeft/afterPrev");
      s.addAnimation("FlyIn/fromBottom/afterPrev");
      se.addAnimation("FlyIn/fromBottomRight/afterPrev");
      nw.addAnimation("FlyOut/toTopLeft/onClick");
      n.addAnimation("FlyOut/toTop/afterPrev");
      ne.addAnimation("FlyOut/toTopRight/afterPrev");
      w.addAnimation("FlyOut/toLeft/afterPrev");
      e.addAnimation("FlyOut/toRight/afterPrev");
      sw.addAnimation("FlyOut/toBottomLeft/afterPrev");
      s.addAnimation("FlyOut/toBottom/afterPrev");
      se.addAnimation("FlyOut/toBottomRight/afterPrev");
      return slide;
   }

   private PPRect flyBox(String label) {
      PPRect box = new PPRect(75, 75);
      box.setFillColor(Color.WHITE);
      box.setText(label);
      return box;
   }

   private PPSlide testWipeSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Wipe Test");
      PPRect box = new PPRect(300, 60);
      double x = (slide.getWidth() - box.getWidth()) / 2;
      box.setFillColor(Color.WHITE);
      box.setText("Click to test wipe from left");
      slide.add(box, x, 0.20 * slide.getHeight());
      box.addAnimation("WipeOut/fromLeft/veryFast");
      box.addAnimation("WipeIn/fromLeft/veryFast");
      box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Click to test wipe from right");
      slide.add(box, x, 0.40 * slide.getHeight());
      box.appear("/afterPrev");
      box.addAnimation("WipeOut/fromRight/veryFast");
      box.addAnimation("WipeIn/fromRight/veryFast");
      box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Click to test wipe from top");
      slide.add(box, x, 0.60 * slide.getHeight());
      box.appear("/afterPrev");
      box.addAnimation("WipeOut/fromTop/veryFast");
      box.addAnimation("WipeIn/fromTop/veryFast");
      box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Click to test wipe from bottom");
      slide.add(box, x, 0.80 * slide.getHeight());
      box.appear("/afterPrev");
      box.addAnimation("WipeOut/fromBottom/veryFast");
      box.addAnimation("WipeIn/fromBottom/veryFast");
      return slide;
   }

   private PPSlide testZoomSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Zoom Test");
      PPRect box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Click for zoom test");
      slide.add(box, (slide.getWidth() - box.getWidth()) / 2,
                     (slide.getHeight() - box.getHeight()) / 2);
      box.addAnimation("ZoomOut");
      box.addAnimation("ZoomIn");
      return slide;
   }

   private PPSlide testChangeColorSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Change Color Test");
      PPRect box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setLineColor(Color.BLACK);
      box.setLineWeight(2);
      box.setText("Click to change colors");
      slide.add(box, (slide.getWidth() - box.getWidth()) / 2,
                     (slide.getHeight() - box.getHeight()) / 2);
      box.addAnimation("ChangeFillColor/color:yellow/medium");
      box.addAnimation("ChangeLineColor/color:red/medium");
      return slide;
   }

   private PPSlide testSpinSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Spin Test");
      PPRect box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Click for spin test");
      slide.add(box, (slide.getWidth() - box.getWidth()) / 2,
                     (slide.getHeight() - box.getHeight()) / 2);
      box.addAnimation("Spin/angle:360/acc/dec");
      return slide;
   }

   private PPSlide testGrowSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Grow Test");
      PPRect box = new PPRect(300, 60);
      box.setFillColor(Color.WHITE);
      box.setText("Click for grow test");
      slide.add(box, (slide.getWidth() - box.getWidth()) / 2,
                     (slide.getHeight() - box.getHeight()) / 2);
      box.addAnimation("Grow/sf:1.5/acc/dec");
      box.addAnimation("Shrink/sf:0.5/acc/dec");
      return slide;
   }

   private PPSlide testMotionPathSlide() {
      PPSlide slide = new PPSlide();
      slide.addTitle("Motion Path Test");
      PPRect box = new PPRect(300, 60);
      box.setFillColor(Color.GREEN);
      PPOval oval = new PPOval(300, 60);
      oval.setColor(Color.WHITE);
      oval.setText("Click for motion test");
      PPGroup group = new PPGroup();
      group.add(box);
      group.add(oval);
      slide.add(group, (slide.getWidth() - group.getWidth()) / 2,
                       (slide.getHeight() - group.getHeight()) / 2);
      Point2D pt = group.getInitialLocation();      
      double x = pt.getX();
      double y = pt.getY();
      group.move(200, 0, "/onClick");
      group.move(0, 100, "/onClick");
      group.curveTo(x, y + 100, x, y + 100, x, y, "/onClick");
      return slide;
   }

/* Main program */

   public static void main(String[] args) {
      new PPEffectTest().run();
   }

}
