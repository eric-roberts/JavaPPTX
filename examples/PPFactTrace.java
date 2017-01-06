/*
 * File: PPFactTrace.java
 * ----------------------
 * This program traces the factorial function.
 */

import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.code.PPCodeTraceSlide;
import edu.stanford.cs.pptx.code.PPConsole;
import edu.stanford.cs.pptx.code.PPFunction;
import edu.stanford.cs.pptx.code.PPValueTag;
import edu.stanford.cs.pptx.code.PPVar;
import java.awt.geom.Rectangle2D;

public class PPFactTrace {

   public void run() {
      PPShow ppt = new PPShow();
      PPCodeTraceSlide slide = new FactSlide();
      ppt.add(slide);
      ppt.save("PPFactTrace.pptx");
      System.out.println("PPFactTrace.pptx");
   }

   public static void main(String[] args) {
      new PPFactTrace().run();
   }

}

class FactSlide extends PPCodeTraceSlide {

   public FactSlide() {
      setMaxStackDepth(6);
      addTitle("PPFactTrace");
      double cx = (getWidth() - CONSOLE_WIDTH) / 2;
      double cy = getHeight() - CONSOLE_HEIGHT - 20;
      addConsole("Console", cx, cy, CONSOLE_WIDTH, CONSOLE_HEIGHT);
      setFrameHeight(200);
      defineFunction("run", new Run());
      defineFunction("fact", new Fact());
      call("run");
   }

   public PPConsole getConsole() {
      return console;
   }

/* Constants */

   private static final double CONSOLE_WIDTH = 350;
   private static final double CONSOLE_HEIGHT = 100;

/* Private instance variables */

   private PPConsole console;

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
      highlightLine("Console.readInt");
      highlight("Console.readInt(\"Enter n: \")");
      readLine("Enter n: ", "4");
      n.set(4);
      highlightLine("println");
      highlight("fact(n)");
      int result = (Integer) call("fact", n.get());
      Rectangle2D bb = getCodeBounds("println", "fact(n)");
      PPValueTag tag = new PPValueTag(bb, result);
      getSlide().add(tag);
      tag.appear("/afterPrev");
      tag.disappear();
      println(n.get() + "! = " + result);
      return null;
   }

   private static final String[] CODE = {
      "function run() {",
      "   var n = Console.readInt(\"Enter n: \");",
      "   println(n + \"! = \" + fact(n));",
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
         highlightLine("return n");
         highlight("fact(n - 1)");
         int result = (Integer) call("fact", n.get() - 1);
         Rectangle2D bb = getCodeBounds("return n", "fact(n - 1)");
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
