/*
 * File: PPCombinationsTrace.java
 * ------------------------------
 * This program creates a full trace of the combinations function.
 */

import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.code.PPCodeTraceSlide;
import edu.stanford.cs.pptx.code.PPConsole;
import edu.stanford.cs.pptx.code.PPFunction;
import edu.stanford.cs.pptx.code.PPValueTag;
import edu.stanford.cs.pptx.code.PPVar;
import java.awt.geom.Rectangle2D;

public class PPCombinationsTrace {

   public void run() {
      PPShow ppt = new PPShow();
      PPCodeTraceSlide slide = new FactSlide();
      ppt.add(slide);
      ppt.save("PPCombinationsTrace.pptx");
      System.out.println("PPCombinationsTrace.pptx");
   }

   public static void main(String[] args) {
      new PPCombinationsTrace().run();
   }

}

class FactSlide extends PPCodeTraceSlide {

   public FactSlide() {
      setMaxStackDepth(3);
      setFont("Courier New-Bold-17");
      addTitle("PPCombinationsTrace");
      double cx = (getWidth() - CONSOLE_WIDTH) / 2;
      double cy = getHeight() - CONSOLE_HEIGHT - 20;
      addConsole("Console", cx, cy, CONSOLE_WIDTH, CONSOLE_HEIGHT);
      setFrameHeight(200);
      defineFunction("run", new Run());
      defineFunction("combinations", new Combinations());
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
      PPVar<Integer> k = new PPVar<Integer>("k");
      addLocalVariable(k);
      endDeclarations();
      highlightLine("Enter n:");
      highlight("Console.readInt(\"Enter n: \")");
      readLine("Enter n: ", "5");
      n.set(5);
      highlightLine("Enter k:");
      highlight("Console.readInt(\"Enter k: \")");
      readLine("Enter k: ", "2");
      k.set(2);
      highlightLine("println");
      highlight("combinations(n, k)");
      int result = (Integer) call("combinations", n.get(), k.get());
      Rectangle2D bb = getCodeBounds("println", "combinations(n, k)");
      PPValueTag tag = new PPValueTag(bb, result);
      getSlide().add(tag);
      tag.appear("/afterPrev");
      tag.disappear();
      println("C(" + n.get() + ", " + k.get() + ") = " + result);
      return null;
   }

   private static final String[] CODE = {
      "function run() {",
      "   var n = Console.readInt(\"Enter n: \");",
      "   var k = Console.readInt(\"Enter k: \");",
      "   println(\"C(\" + n + \", \" + k + \" = \" + combinations(n, k));",
      "}"
   };

}

class Combinations extends PPFunction {

   public Combinations() {
      setCode(CODE);
   }

   @Override
   public Object stepThrough() {
      startDeclarations();
      PPVar<Integer> n = new PPVar<Integer>("n");
      PPVar<Integer> k = new PPVar<Integer>("k");
      addParameter(n);
      addParameter(k);
      endDeclarations();
      highlightLine("return");
      highlight("fact(n)");
      int f1 = (Integer) call("fact", n.get());
      Rectangle2D bb = getCodeBounds("return", "fact(n)");
      PPValueTag tag1 = new PPValueTag(bb, f1);
      getSlide().add(tag1);
      tag1.appear("/afterPrev");
      highlight("fact(k)");
      int f2 = (Integer) call("fact", k.get());
      bb = getCodeBounds("return", "fact(k)");
      PPValueTag tag2 = new PPValueTag(bb, f2);
      getSlide().add(tag2);
      tag2.appear("/afterPrev");
      highlight("fact(n - k)");
      int f3 = (Integer) call("fact", n.get() - k.get());
      bb = getCodeBounds("return", "fact(n - k)");
      PPValueTag tag3 = new PPValueTag(bb, f3);
      getSlide().add(tag3);
      tag3.appear("/afterPrev");
      tag1.disappear();
      tag2.disappear("/withPrev");
      tag3.disappear("/withPrev");
      return f1 / (f2 * f3);
   }

   private static final String[] CODE = {
      "function combinations(n, k) {",
      "   return fact(n) / ( fact(k) * fact(n - k) );",
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
      PPVar<Integer> result = new PPVar<Integer>("result");
      PPVar<Integer> i = new PPVar<Integer>("i");
      addParameter(n);
      addLocalVariable(result);
      addLocalVariable(i);
      endDeclarations();
      highlightLine("result = 1");
      result.set(1);
      highlightLine("for");
      highlight("var i = 1");
      i.set(1);
      while (i.get() <= n.get()) {
         highlightLine("result *=");
         result.set(result.get() * i.get());
         highlight("i++");
         i.set(i.get() + 1);
      }
      highlightLine("return");
      return result.get();
   }

   private static final String[] CODE = {
      "function fact(n) {",
      "   var result = 1;",
      "   for (var i = 1; i <= n; i++) {",
      "      result *= n;",
      "   }",
      "   return result;",
      "}"
   };

}
