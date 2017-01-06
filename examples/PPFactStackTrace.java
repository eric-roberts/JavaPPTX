/*
 * File: PPFactStackTrace.java
 * ---------------------------
 * This program tests the stack diagram facility using the fact function.
 */

import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.code.PPCodeTraceSlide;
import edu.stanford.cs.pptx.code.PPFunction;
import edu.stanford.cs.pptx.code.PPValueTag;
import edu.stanford.cs.pptx.code.PPVar;
import java.awt.geom.Rectangle2D;

public class PPFactStackTrace {

   public void run() {
      PPShow ppt = new PPShow();
      PPCodeTraceSlide slide = new HSSlide();
      ppt.add(slide);
      ppt.save("PPFactStackTrace.pptx");
      System.out.println("PPFactStackTrace.pptx");
   }

   public static void main(String[] args) {
      new PPFactStackTrace().run();
   }

}

class HSSlide extends PPCodeTraceSlide {

   public HSSlide() {
      addTitle("PPFactStackTrace");
      setMaxStackDepth(6);
      double sx = SIDE_MARGIN + TRACE_WIDTH + DISPLAY_SEP;
      double sw = getWidth() - sx - SIDE_MARGIN;
      setStackFrameRegion(SIDE_MARGIN, TOP_MARGIN, TRACE_WIDTH, TRACE_HEIGHT);
      setHeapStackRegion(sx, TOP_MARGIN, sw, TRACE_HEIGHT);
      setFrameHeight(FRAME_HEIGHT);
      defineFunction("run", new Run());
      defineFunction("fact", new Fact());
      call("run");
   }

   private static final double SIDE_MARGIN = 30;
   private static final double TRACE_WIDTH = 450;
   private static final double TRACE_HEIGHT = 400;
   private static final double DISPLAY_SEP = 20;
   private static final double TOP_MARGIN = 80;
   private static final double FRAME_HEIGHT = 180;

}

class Run extends PPFunction {

   public Run() {
      setCode(CODE);
   }

   @Override
   public Object stepThrough() {
      startDeclarations();
      PPVar<Integer> n = new PPVar<Integer>("n");
      addLocalVariable(n);
      endDeclarations();
      highlightLine("n = 4");
      n.set(4);
      highlightLine("println");
      highlight("fact(n)");
      int result = (Integer) call("fact", n.get());
      Rectangle2D bb = getCodeBounds("println", "fact(n)");     
      PPValueTag tag = new PPValueTag(bb, result);
      getSlide().add(tag);
      tag.appear("/afterPrev");
      tag.disappear();
      return null;
   }

   private static final String[] CODE = {
      "function run() {",
      "   var n = 4;",
      "   println( fact(n) );",
      "}"
   };

}

class Fact extends PPFunction {

   public Fact() {
      setCode(CODE);
   }

   @Override
   public Object stepThrough() {
      startDeclarations();
      PPVar<Integer> n = new PPVar<Integer>("n");
      addParameter(n);
      endDeclarations();
      highlightLine("if");
      if (n.get() == 0) {
         highlightLine("return 1");
         return 1;
      } else {
         highlightLine("return n *");
         highlight("fact(n - 1)");
         int result = (Integer) call("fact", n.get() - 1);
         Rectangle2D bb = getCodeBounds("return n *", "fact(n - 1)");
         PPValueTag tag = new PPValueTag(bb, result);
         getSlide().add(tag);
         tag.appear("/afterPrev");
         tag.disappear();
         return n.get() * result;
      }
   }

   private static final String[] CODE = {
      "function fact(n) {",
      "   if (n === 0) {",
      "      return 1;",
      "   } else {",
      "      return n * fact(n - 1);",
      "   }",
      "}"
   };

}
