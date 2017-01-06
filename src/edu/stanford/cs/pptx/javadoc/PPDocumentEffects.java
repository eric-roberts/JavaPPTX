/*
 * File: PPDocumentEffects.java
 * ----------------------------
 * This file is a utility that reads the code from the effects package
 * and extracts the HTML documentation.
 */

package edu.stanford.cs.pptx.javadoc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class PPDocumentEffects {

   public void run() {
      try {
         PrintWriter wr = new PrintWriter(
                             new BufferedWriter(new FileWriter(HTMLFILE)));
         String[] files = new File(EFFECTS).list();
         Arrays.sort(files);
         dumpHTMLHeader(wr);
         for (String name : files) {
            if (name.endsWith("Effect.java")) {
               String path = EFFECTS + "/" + name;
               String effect = name.substring(0, name.length() - 11);
               BufferedReader rd = new BufferedReader(new FileReader(path));
               dumpEffect(effect, rd, wr);
               rd.close();
            }
         }
         dumpHTMLTrailer(wr);
         wr.close();
      } catch (IOException ex) {
         System.err.println(ex.toString());
      }
   }

   private void dumpHTMLHeader(PrintWriter wr) {
      wr.println(DOCTYPE);
      wr.println("<html lang='en'");
      wr.println("<head>");
      wr.println("<title>PowerPoint Effects</title>");
      wr.println("</head>");
      wr.println("<body>");
      wr.println("The <code>edu.stanford.cs.pptx</code> package");
      wr.println("implements the following effects:<br/>");
   }

   private void dumpHTMLTrailer(PrintWriter wr) {
      wr.println("</body>");
      wr.println("</html>");
   }

   private void dumpEffect(String effect, BufferedReader rd, PrintWriter wr)
           throws IOException {
      boolean started = false;
      while (true) {
         String line = rd.readLine();
         if (line == null) break;
         if (started) {
            if (line.equals(" */")) break;
            wr.println(line.substring(2));
         } else if (line.equals("/**")) {
            wr.println("<br/><code><b>" + effect + "</b></code><br/>");
            started = true;
         }
      }
   }

   public static void main(String[] args) {
      new PPDocumentEffects().run();
   }

/* Private constants */

   private static final String HTMLFILE = "doc/effects.html";
   private static final String EFFECTS = "java/pptx/effect";
   private static final String DOCTYPE =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\"" +
      "\"http://www.w3.org/TR/html4/frameset.dtd\">";

}
