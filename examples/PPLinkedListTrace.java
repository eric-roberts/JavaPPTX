/*
 * File: PPLinkedListTrace.java
 * ----------------------------
 * This program tests the heap-stack diagram facility with a linked-list
 * example.
 */

import edu.stanford.cs.pptx.PPPointer;
import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.code.PPCodeTraceSlide;
import edu.stanford.cs.pptx.code.PPFunction;
import edu.stanford.cs.pptx.code.PPValueTag;
import edu.stanford.cs.pptx.code.PPVar;
import edu.stanford.cs.pptx.code.PPWord;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PPLinkedListTrace {

   public void run() {
      PPShow ppt = new PPShow();
      PPCodeTraceSlide slide = new HSSlide();
      ppt.add(slide);
      ppt.save("LinkedListTrace.pptx");
      System.out.println("LinkedListTrace.pptx");
   }

   public static void main(String[] args) {
      new PPLinkedListTrace().run();
   }

}

class HSSlide extends PPCodeTraceSlide {

   public HSSlide() {
      addTitle("LinkedListTrace");
      setMaxStackDepth(1);
      double sx = SIDE_MARGIN + TRACE_WIDTH + DISPLAY_SEP;
      double sw = getWidth() - sx - SIDE_MARGIN;
      setStackFrameRegion(SIDE_MARGIN, TOP_MARGIN, TRACE_WIDTH, TRACE_HEIGHT);
      setHeapStackRegion(sx, TOP_MARGIN);
      getHeapStackViewer().setLabelOrientation("label");
      setFrameHeight(FRAME_HEIGHT);
      defineFunction("run", new Run());
      defineFunction("rlist", new RList());
      call("run");
   }

   private static final double SIDE_MARGIN = 30;
   private static final double TRACE_WIDTH = 450;
   private static final double TRACE_HEIGHT = 400;
   private static final double DISPLAY_SEP = 40;
   private static final double TOP_MARGIN = 80;
   private static final double FRAME_HEIGHT = 200;

}

class Run extends PPFunction {

   public Run() {
      setCode(CODE);
   }

   @Override
   public Object stepThrough() {
      startDeclarations();
      PPVar<String> list = new PPVar<String>("list");
      addLocalVariable(list);
      endDeclarations();
      highlightLine("var list");
      highlight("rlist(3)");
      PPWord word = (PPWord) call("rlist", 3);
      if (word == null) {
         list.set("null");
      } else {
         Point2D[] points = PPPointer.createPointArray(list, word, 50);
         PPPointer.adjustEnd(points, 0, -6);
         PPPointer ptr = new PPPointer(points);
         getSlide().add(ptr);
         ptr.appear("/afterPrev");
         ptr = new PPPointer(list.getWord(), word, -77);
         getSlide().add(ptr);
         ptr.appear("/afterPrev");
      }
      return null;
   }

   private static final String[] CODE = {
      "function run() {",
      "   var list = rlist(3);",
      "}"
   };

}

class RList extends PPFunction {

   public RList() {
      setCode(CODE);
   }

   @Override
   public Object stepThrough() {
      startDeclarations();
      PPVar<Integer> n = new PPVar<Integer>("n");
      PPVar<String> tail = new PPVar<String>("tail");
      addParameter(n);
      addLocalVariable(tail);
      endDeclarations();
      PPPointer p1 = null;
      PPPointer p2 = null;
      PPWord result = null;
      highlightLine("if (n === 0)");
      if (n.get() == 0) {
         highlightLine("return null");
      } else {
         highlightLine("var tail");
         highlight("rlist(n - 1)");
         PPWord word = (PPWord) call("rlist", n.get() - 1);
         if (word == null) {
            tail.set("null");
         } else {
            Point2D[] points = PPPointer.createPointArray(tail, word, 50);
            PPPointer.adjustEnd(points, 0, -6);
            p1 = new PPPointer(points);
            getSlide().add(p1);
            p1.appear("/afterPrev");
            p2 = new PPPointer(tail.getWord(), word, -(45 + 8 * n.get()));
            getSlide().add(p2);
            p2.appear("/afterPrev");
         }
         highlightLine("return {");
         highlight("{ data : n, link : tail }");
         PPWord data = allocateWord("data");
         PPWord link = allocateWord("link");
         data.set(n.get().toString());
         if (p1 == null) {
            link.set("null");
         } else {
            PPPointer p3 = new PPPointer(link, word, -(45 + 8 * n.get()));
            getSlide().add(p3);
            p3.appear("/afterPrev");
         }
         result = data;
      }
      if (p1 != null) p1.disappear("/afterPrev");
      if (p2 != null) p2.disappear("/afterPrev");
      return result;
   }

   private static final String[] CODE = {
      "function rlist(n) {",
      "   if (n === 0) {",
      "      return null;",
      "   } else {",
      "      var tail = rlist(n - 1);",
      "      return { data : n, link : tail };",
      "   }",
      "}"
   };

}
