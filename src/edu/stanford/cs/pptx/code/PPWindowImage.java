/*
 * File: PPWindowImage.java
 * ------------------------
 * This class creates an image that represents an on-screen Macintosh window.
 */

package edu.stanford.cs.pptx.code;

import edu.stanford.cs.pptx.PPPicture;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * This class is a <code>PPPicture</code> subclass that represents an
 * on-screen Macintosh window rendered at high resolution.
 */

public class PPWindowImage extends PPPicture {

/**
 * Creates a <code>PPWindowImage</code> with the specified title and size.
 *
 * @param title The window title
 * @param width The width of the image
 * @param height The height of the image
 */

   public PPWindowImage(String title, double width, double height) {
      this.title = title;
      sw = SF * width;
      sh = SF * height;
      setImage(createWindowImage());
      setBounds(0, 0, width, height);
   }

/**
 * Returns the height of the window title bar.
 *
 * @return The height of the window title bar
 */

   public static double getTitleBarHeight() {
      return TITLE_BAR_HEIGHT;
   }

/* Private methods */

   private Image createWindowImage() {
      int iw = (int) (sw + 0.99);
      int ih = (int) (sh + 0.99);
      BufferedImage image = new BufferedImage(iw, ih,
                                              BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = image.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                         RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      fillBackground(g);
      drawTitleBar(g);
      drawWindowBody(g);
      return image;
   }

   private void fillBackground(Graphics2D g) {
      Graphics2D g2 = (Graphics2D) g.create();
      Rectangle2D window = new Rectangle2D.Double(0, 0, sw, sh);
      g2.setComposite(AlphaComposite.Clear);
      g2.fill(window);
      g2.dispose();
   }

   private void drawTitleBar(Graphics2D g) {
      Graphics2D g2 = (Graphics2D) g.create();
      RoundRectangle2D titleBar = createTitleBarShape();
      double h = SF * (TITLE_BAR_HEIGHT + CORNER_RADIUS) + 1;
      g2.setPaint(new GradientPaint(0, 0, grayColor(GRAY_TOP),
                                    0, (float) h, grayColor(GRAY_BOTTOM)));
      g2.fill(titleBar);
      g2.setColor(Color.DARK_GRAY);
      g2.draw(titleBar);
      g2.setColor(Color.BLACK);
      int scaledSize = (int) Math.round(SF * TITLE_FONT_SIZE);
      g2.setFont(Font.decode(TITLE_FONT_FAMILY + "-" + scaledSize));
      FontMetrics fm = g2.getFontMetrics();
      int x = (int) Math.round((sw - fm.stringWidth(title)) / 2);
      int y = (int) Math.round(SF * (TITLE_BAR_HEIGHT / 2 + TITLE_DY));
      g2.drawString(title, x, y);
      drawBall(g2, RED_BALL, 0);
      drawBall(g2, AMBER_BALL, 1);
      drawBall(g2, GREEN_BALL, 2);
      g2.dispose();
   }

   private void drawBall(Graphics2D g, String imageName, int index) {
      double h = SF * TITLE_BAR_HEIGHT;
      int d = (int) Math.round(h * BALL_FRACTION);
      int x = (int) Math.round(SF * (BALL_X0 + index * BALL_DX));
      int y = (int) Math.round((h - d) / 2);
      Image image = PPPicture.loadImage(imageName);
      g.drawImage(image, x, y, d, d, null);
   }
      
   private void drawWindowBody(Graphics2D g) {
      Rectangle2D body = createBodyShape();
      g.setColor(Color.WHITE);
      g.fill(body);
      g.setColor(Color.DARK_GRAY);
      g.draw(body);
   }

   private Rectangle2D createBodyShape() {
      double x = 0;
      double y = SF * TITLE_BAR_HEIGHT;
      double w = sw - 1;
      double h = sh - y - 1;
      return new Rectangle2D.Double(x, y, w, h);
   }

   private RoundRectangle2D createTitleBarShape() {
      double w = sw - 1;
      double h = SF * (TITLE_BAR_HEIGHT + CORNER_RADIUS) + 1;
      double d = 2 * SF * CORNER_RADIUS;
      return new RoundRectangle2D.Double(0, 0, w, h, d, d);
   }

   private Color grayColor(double gray) {
      int xx = (int) (gray * 0xFF);
      return new Color(xx, xx, xx);
   }

/* Images for the title bar controls */

   private static final String AMBER_BALL = 
      "data:image/png;base64," +
      "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" +
      "XOrBG91dtSfnIrnQFdk/d2IbO5IObC/niJOdmM7aUmpTpOxktbOxNhJXuSNh" +
      "RWX20VkR7+XMXOuKiNmurGtdcHmAK8yc0eUMm1huOG120PoOTjg5tBJuT3fm" +
      "CsQVLzn8EygyYS2FrnXSWOeEpA2VkLLdFWn7Re53yDrpgewzLdkRRvClDqQT" +
      "O9mVdNPpTtk9KKInJfWmOI08vz6krw3LjT42jNtL6aPTU8O3hzqvXCPncpdS" +
      "fDozKttTZDu2q426+VmnWlFoC7a3CdIOejCdNaTUepT6lRqJCcsZOMudKLeS" +
      "9jOJZ2DFMGqfTXGBv6crjjFqTcT62MS2adMmncCevT2c4OPpjKBZrAQkr65z" +
      "YzKvjKT1ItPARiekbHVG6i53yv0KGYdrIfM4Bf/UiJ35nhPb95TbirQh7dhZ" +
      "ir7ShSK6wuLXjXSnvB46vT8S/Xj5rF9Xnovnu9aFEUsud+Y1OGoutdWueaEN" +
      "hbamzBa86Y05HzRUoyv9YAOk7quHlN21kLr9K468qhyBLprQ5V/qOCmpiRJU" +
      "HK1xnGMiZrjiwWgXnOUkZuLNohYLbdu2dSWwZzlz649DnXBlsjMeLeCktcoN" +
      "CRsodiPz6sZKnLTIllJStlVG6k5XpgQ3pO+vgowfqyPzKAWfpOCfG1Iuo/hi" +
      "U3aUkXypNTvehiLaUkh7RlsnyumiBGl0+wi0Y/P8OmvnuN6O52vHG9ZGO7/c" +
      "SJ8WnJSa8eY2501ujKwTjXjDv0XGkVoc9px091RXwSCjLXmTqwqaBG8nc7Gr" +
      "XdV8Es8Ai5nvxtWYK64OcoWZO8qtIWI9zXau7vwljo10ws2ZzngiuXW9GxK3" +
      "uCCZkZm8tTKhUMpMsbLdmUnfhWKZEva5IeOQOztAuSdqcrb9mnLrUm4DyvXQ" +
      "aczON6WI7ymlOSy+rUgbRZ6irf5a+t7iJ7TVXm3Htubwb8ko5Q27zNHh01Sd" +
      "W93I84047OuT79QNzjxejzeb6epQVbaR88JuV6Ts0PojwZLEtJa4hjJX6kIV" +
      "WipIotiU9a4qcuOWuCF0Fut6ll5m7sgYqQi8CexZS7GnRlXC/bkuCOPM+JIT" +
      "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" +
      "F7QI6i6OXAAAAABJRU5ErkJggg==";

   private static final String GREEN_BALL = 
      "data:image/png;base64," +
      "iVBORw0KGgoAAAANSUhEUgAAAFYAAABWCAYAAABVVmH3AAAnnUlEQVR42uWd" +
      "aVxW17n28ztvkg6n82natM3b2tO0p+1Jk6gMD4OIIA5MoqIiOKDihLOioiKg" +
      "ouCECs6gIKiIDDLP8zyoOIEDjtGkH/LBj/3gh+u97rX3hofHBzRt2iRv/f2u" +
      "7GfYe+21/uta932vjZI33via/BkxYsT7I0eODKFi+TqDx0aql3pOwULy2SOq" +
      "VT93B4+zbGxsPnB1dX3zjX/nPx9//PEwglhOKFnUX63A+3sl0AupMJmsfxuY" +
      "HHAEgXby+OJLhDmUuv6/hcxBeRNmJfWCwleoWkL2+0bDlFhna2sbTHVS+Jqp" +
      "y87OLuQbF4/Z8QnseO+XBeJnyxzxi+XOeHe1kzr+LNThS2lX7+OEbwLQd9nZ" +
      "JOoFhS+itxd+iO+FjsDPV5vwu/DR+FOEO/4SNR42MT6w2zkJ9rv8YB+rHW1j" +
      "fPEhv/szz/nDpjH45RonfH+FDd4O/Rhf9L4ie3v7FOn71xIqO+hHff5FBvT9" +
      "lTb4cZgJv9zsjP+OHo0RsZ5w2eeDiQnTMenoLExPXog5p0MxL2UZtbxPwaeX" +
      "IShpESYfmQ3vhEB4xE+HbZwv3t/hgXcinPGj9Sb8YK0d/nPZyC8C+DkB+3+t" +
      "Yqm4lJ16QeFVGrbaFR9Ge8J2z2SMSgzE2BPB8Du1EAFnQrEwIwyrcjYhPH8r" +
      "ootisLM0DnFle7C3PB57Kw70i+9jS3djW9FObMnbjrDsCCzO2ICZaSvgd3qx" +
      "atPlcBDs9k7BRzu88PtNbnidvolkLBzT977ypW8ymWopvI7+N8ID4+JnIPDU" +
      "UizLDMeWwljEVhxEfM0RJNafxommNJxpPYtz7edwoeMCsi5nIftyNnKu5Fgo" +
      "W30n55xry0Ba6zl17ZGGVByqT0Zc5SFsKYrF8uzNCEwNhVdiEGx3+uKXG5zx" +
      "mn1tpIZ9JVAdHR0/4M0/f52ODosYBVPcJEw+OhdrMzcivuQAUpvOIO9qPqp7" +
      "qtBwtx7N91rQ0tuG1vutaLvfhvb77erYpt4PVOv9FrT26lLvqd52qgNN95pR" +
      "1V2p2k5tScM+Qt50aRvmMoS4MGS8v93jteDSvRIaTP9SqLyxq4ODw18pvEqj" +
      "EgLgn7IYy7PCsaM4Fifqj+ES3VbDwXcQ3q1PbuDOs27cfdZD3ebrHqXbT7v5" +
      "3U3c5Pcv67qSXCvn3VHX3kHvs/uqjW5+3vmgHbW3qwg4l4DPYB9XRlhuNAIY" +
      "LsYcmYXhu3zwGv1/Tk34V0Edy5v97ZVAd03BHC75DblR2F+dyCWezmWdg5Lr" +
      "xajjgNvuN+Hqw8u48eQaup/epG7pIswn19XnIgFp/vo6j9efdPVJff7kupoE" +
      "uV6O8tnVR5d5j2bU36lBxa1S5F7JRXrbeSTUJWNzYRzmpK2Ex8EZrwNXxur9" +
      "T4Xq7OzsyhDwNwpDacyeaViRvhqHKg8isyOTzqmhO9voog500EkdD/j6YTsu" +
      "P+xQukIIVx5d0Y+dfZ/3f9ev/u/aVRsDZbSrtd2p6/KDdhVCWnoFdANyrxbg" +
      "UO1JrM3azPgbiD9HeeAVY/rnwZWY6uTk9DmFwfRh5ERMOj4fKxhHD1cnoODq" +
      "JcbORrVk7+rL+/rjLnQJxIed+sAHwhHoL0k+t/iu/UHLoDImTu7R9fiq5ngV" +
      "Pm6ovrT1tqKIK+dU02lsyo/CNJZ1dswBQ42Nek6ZvuyS6t1XQZUiflbyUkQU" +
      "7MTRumPM3Fmo4zLseNCKawR5U5YwB2k4cjCIQwEzJEv8VWrnfftXRqe6bxcd" +
      "3/X4ivq88W4dym+W4AwTXFQRQwNrY4fYyXhvzegh4X5p1YLUqQwBtRSs6QdL" +
      "RmL4Th/MPL0UMSW7Wf6cQ/H1IhXb2u5rIFoZT1vvN3IpUjy+DpjBJG219Dag" +
      "ubd+CDXoGvi+RZf0RSZcS3DVyGjPwC72fS7hmgj3F+utj1VX45dS53KWkoa4" +
      "CX4X4YZJJxdgc0EMk8NZlk8V7HyTckz7gzYFouleHUuqGqWme7UMD/Waev8B" +
      "8Xppd3DV6vestlCNul5CyzWuIHG09Pk8a+cdJXEISFkCm92+eCfMcdAxk0nK" +
      "PwR11KhRftQLCtb063AXuB+YhtDM9ThQfRj5XZc4oHoFs/leg3qtDbCaDq7S" +
      "dLdf8nnjvRqq1ioc+dxSTWZqNNq+W2Vdd6yr4U61mhi1AmiCxru1KiyktqRi" +
      "U0EU/JLn4w873PHdlcMx2Ngp/787rrq4uHxOwZre28iif7cfgrmz2VUai/TW" +
      "M6pzAlNcUUMX1PSUo+Z2GVVqVbV3RGWMxeUEUfmS5HP53pB2XsWAo7RRc6d0" +
      "0HsYqu5hPyg5VneLypWkn5qza1F4LR+JtYexJHMtnA96472tJgw2fuo5Gb33" +
      "hcGOHj06aYhG8YeYcfA9OU/t65ObjyGvKxNVPSXKKQKzsrsQFd0FA9WT3/e6" +
      "sicPlbe587pToFRzp/AlGd8NVOGAY9VttnU7T7U3oG0z9d3/VgHKbxXSAAUo" +
      "vZHPmjofZTcKGWcr1QqpuFWmXLu5MApTT8/F8D2e+PkW+0EZkFH6F3XrBF70" +
      "goI1fW+jHYbv9UVQ+nLEVezl3j6Ns53DTnNQ3cXqWHorl8oZqO7svtdlfF3e" +
      "k0PYf7/k+rJuvV2ztvvem39+MwfFN3KZWC+x1Mphf7NRQBVeyyXoYjq5nN8V" +
      "Iq01DbEVcZifuQijjk7HsBhXfGftcAzGQli9dhXAC3oHa+g/VnyEH0ayaD4a" +
      "gGU54ThSn4jsK+fZySwUXr+oKxMF1y+g8Ob5IVUkuvUP6BXtmyv/+nnkXUun" +
      "zuESj7lXM1gSXuAxE/nXLipjZF++iJTmJOyv2o+VOesxLnk2/hg/Ft+LsB0U" +
      "rLB6rZ9E8KRgCoPp7Y3D8W6sM9ySA7GhcAtSWo8yDGSwcxnIvXYGOddSkNOV" +
      "guyuU3x9mp9R179aST+kP1lXk6hkXOQx8/KpPl28zP5ePo+MjnRuvU8gofYg" +
      "NuZvgX/aAtge9cVPdzngLY57CC4hrwQ7ZsyYTgrW9Obaj/HdyJH4XbwTfNKm" +
      "YVPxJqS0HOWspyuYmVdO4sLl40oZ+vHClePIvHqcg/lqJPeWPmSoPh3rV6e5" +
      "+F3nSZxtO4mkxiPYX70X4YXhCMyYDfsTE/Dubgd8e+tIvLnmYwzCpmtI1/JL" +
      "78Gg/p91w/GtiJH48U5b/DnRGVPOTkV40XocbTiI8x0nVGfTOw4irSP+G6YD" +
      "SGs/hDNtCUhuOsTQtg+7KyMRVrAWM84Gw+mEL3673wk/irHFt7aMGAysuNZv" +
      "KLdWurm5wZqk0R8S6rt77PDxEYI954ewwqWIr9mF023xSG3fh+S2XUhqjflG" +
      "6GTrDpxo2YmTVFLzfpxsisfRxljsr4nEjvIwrMibB/9zU+Cc5I4/HHLAT3fb" +
      "4fs7bPDWhuFW+ZBd7WBuHcYTXli76NsMAT/ZYYtf7bXDb+PtMfKoM/zOemF5" +
      "/mzsrAzD4cYoHG/ehmPN0TjeEmVVxyx0/JWKfI1zhr7HADVrOso+Hm2KRELD" +
      "Vhys28FjJIFuw5HGGByojcGuynBsLFqJ+dkBmHTWk2BH4X8STPgZDfXDXZpr" +
      "/yP0Q6twyfDlvxQyduzYCHd3d1jqraUf4sfhNvi/u+3x+4Mm/Ik3sTnmCM80" +
      "NwRnT0VY0Xxsr1yGuOow7KvdiIMN4UhoEm1EQuNGHGrcQHFn1hiGAw3rlA7y" +
      "dULT+iG0DoeaVqvj0Oet53m6pH2zewxUGPbXS/82YG/NRmoD9lC7qzeqo/Z+" +
      "I02yHptKliA0bxamZ/hgXKorHJKcGPoc8Ov99nhHXLvdBm/TaNZYEe4Oa2A7" +
      "KVjqWwzY70TZ4H069S+HHfDRUQfYnnCEe4oLppz3wrxsf6wsCEJ4SQi2VyzD" +
      "7pq12Fe3lgNZg/0Na7CvfhX21odiT/0S7Na1p34p9jeGKu1t6H/dL4YYUVNo" +
      "vxo1WZ67l23t0aXar1tMyXFp3zGudil2VocipmrZS9pBU4gxoitC1RiW5wdi" +
      "9kVfeKWNwahTTrA76cgxO+J/DpkwLN6En8ba4jt07ZsrP4IVXjcHQJ0wYcIw" +
      "fvjCGtjvbBqBn3EJSMMjjjvChlDlZnJTjzOuhDuRzvXDioIAdmweO7gYMdVL" +
      "EFu7CHF1mmLrQqgFAxRH7a7nsX6+Og5QwwLsbQzBPjPJe/nc8ly5XiRt7qqd" +
      "j5iaudjJ486ahXwfwr4sxPaqhdhWaalFqq9R1KbSEKwvnkeoMzE7yxc+Z8dg" +
      "9Gkn2HOcIznej2kmce0fyOA9hsMfMta+ve5ja2Dh4eHxsblbl/MDWOrtVR/h" +
      "J1Ej8Rs29iHdKjcSOSRzg3DaBOdUB3icdcLUTFfMvzQRKwr9saE0AFsrAxFV" +
      "FYhopSBsq55FBQ7QjtpA7KwPwK5BFNvwsgaeM4PXi6Yjpm4GttfMVO1GVxvH" +
      "IN57FiIrg7C5fCY2lQcohZcFaioNwsaSuQgrnsMVNxNL8vwJ1Qc+55jhz3Cc" +
      "pxxgI2B1uMMZ/j4gg98yJPwXXfvtzSNgjRlZhvWB5QdZVsGyKP4vVgLvH9Tc" +
      "6pjkCCcdqkOKCY6p9hidZo+J5x0wPcsF8y6Nw9KCiVhd4oV1pb4IK/NDeLk/" +
      "NlVMxZZKP0RU+WErFVnth221foipn0IwU4eQ/yCaqq7dUe+H7XW+iKqZpNrc" +
      "aqaIqsnYXOGv7r+e/ZD+rCmZhJVFU5UBlhdOxZL8SViY5425OV6YnulOp7pg" +
      "TJqjGptNkgNGnHBQRxvDuYQrrv0lE9kPGGvfWv2RNbCFfWDHjRv3VwqWkll5" +
      "J85OZUUJAc7JTgwBPKaaMOqMPUYR6uh0e3ics4fPBQdMy3LCrBxnzM8fhUUF" +
      "7lhaOA6rSiZgbdl4hJV7YkO5FzZXeiGi2guRNV7YVudNMFS970vaUT+J8CYr" +
      "eP3HKTrQSboErA+ia70J04ftiryxpcoLmyq9sb7cG+vKvHl/L/bDi/2ZiMX5" +
      "noQ5Ua2w2dkTMO2iKyZfGAVPrjz3M9rY7Gkc21Mcc7J+pKFsCVfMJSx+IRUC" +
      "Dff2xhGwwu252iyMHz/+fQqWemvZX/CTyBEqDEjScmTDYxh3xqQ40qUmjDlr" +
      "DzfK/ZwdPM7bYUKGHXwy7TAlyw7Tc+0wK9+EeQWOWFjohKUlzlhVNgZryt0Q" +
      "VuGG8Co3bK52R0TNWETWeiC6biLlOUDb6jTwLx+119HqvZe6dmvNOGypnkig" +
      "nthU5YH1FWOxrsJd3W91mRtWlrpjSaEHFhZ4YH6eO2bnuGD6xVHwu+AMT662" +
      "sedMcE0Ts8gqNMFEsHa6BLI94ToIXJpLWPyG4eDHzDtvhY+ANXaE+4GADbH2" +
      "paoGYlgNHDBxQ+AAJzbslsJZTeUxvR/oOGp8hoC1xcQLtvC9aIupObaYkWeL" +
      "WQW2CC60RUiRPZaUOGBZmQNWljtibYUzwipHY0OVCzZVu2JzjRu2WGhrjTu2" +
      "1ooEvqatSu7qOzlHrttcPQYbq6UtVzVpayvGYHX5aKwokwl1UPddWOSI+Zzk" +
      "4HxHBF5ygH+WAyZdMMEzw55Q7eHK8bikaSvQ6YwW5gSuIQdKQqDAHU7X/p7h" +
      "QFbyt7dYB0stkYogloKl/jNsOH4Va4c/SjVAsM4KLMusM5xhOtUcqGemLbwp" +
      "H0KdnG0L/1wNbGC+DeHaYF6RLRaW2HKQdggts8eKcg3wampdpUAeRTCjsJGg" +
      "B6jaBeGEtlngU+HV/d/J+WG8Vq5fW+GEVWxrRZmjmrylpQ5YXGKPBcW26t5z" +
      "C7VJnpnH1XSJqyrbjgawg1emNgYxiaxAA64zw5xjigZYSQdrSpa464g/sUIY" +
      "xvLzR4yzby7/EFb4xQrYDGtgZVMwjJuCDxPZ8DETRrNhcatHugnjz9urpS9A" +
      "vcyAilOnX+qHGkSoswttEFxkowa5iHCXltpx8IRbZiJcBwVXwKzTIRkSaGFV" +
      "zhpwHeh6M5jrKp2wusJRAZV2lrM9mbTFbF/uI/eT+8r9ZXKlP9IvmXTpp/RX" +
      "+i1jELiy+iS0GYAFrrhXAIucWSUIXAkHH9JoktB/yjgrK9sKv4w3Jk6c2EjB" +
      "Uu9EjMDvGF9HHNXc6n7aEROYMScyHnlxCXlztqVjvmZQZxDqTB2oDGZukbjV" +
      "BvMtoZZrUFdRllAF3HpzB1ebqc+pozSnEu4a5VYH1aa0LfdYXKqtkPlmcKVP" +
      "AXnaxA8Gd+y5gXA195qUXFhaupKBhERJYn9McMCvGA5kZVvh1/qGl5dXr6en" +
      "Jyz1863D8b/xtrA97gAX2QykOsEz3RFe500qSfllax2bYgbVWPoGUMOlS8yA" +
      "rjJzqEDcYGXZb6kZWpvMwkIfZD0crNQhG+41AEufzJ2rAL8GXE0muKVqoXBU" +
      "cv9u7Ld77dXKtsLv0Rv8z3NrYN8l2A/jbVgNOKikNTHNCT7MoL4M+pOzNJgi" +
      "FU/ZySADaLENQkoMh9qrJSqDXaPDFMdtMgO4tWYME5Ibk5Mbs7w7yyd3llFj" +
      "lbbXjVXvzSXnRKmk5qauNUAbbpZwIfdZwzAhsVwmdIkOWPpmAJ6pA56WOxCu" +
      "5A4DrgFYkvVYyS00l1RGToQrrv09t7iysq3wey6OhaW+zcL3l1HD8dEBG4w6" +
      "aVJu9Up3gm+GAyZfNGFqttYhI54aUMUZC3WHimMGAnVRQLcISGZzgaOBJERq" +
      "Z/047GoYh7iG8djTOEFJXsdaSM6Rc3fUaeCljUgF2U21LRMm7t9gAXgp+7OI" +
      "IUImPdjCvYZzJQFL7jDguuuSymH8WRNDoRPG0mTiWqnr/3jAHj9nSWqNoVWw" +
      "3137EX6zfThGHrSB20l7eLJw9mEBPZmbAMmo/vrSD7AC1YijK/X4Kctd3CQD" +
      "juDANWdqjhRIBjCBuK9pIvY3eWJ/sy6+3veSJvYBl+tj6j363G04uQ+wChXO" +
      "anKlP1p40BKbuXPFILL6fHXXCtzxOmBJarIBmsDcMpGhcJzuWlnJfzlgi3cH" +
      "A+vt7Q1LCdj/3jEcDglcGiyOfZi0Jp91wNSLrAFz7AYkKXOooXq2N5KSUadu" +
      "6QPqrkAYztxLSAaweII81OKDxFaRr4V8+iTnyLnGdXv7QGtONgdsDnetDldC" +
      "k6woo2qYZZbUpuhwvc0By+aHVZAkbc9zDiokutO1zgQrofIXUSNgjaF1sOs0" +
      "sM6JvMkpe0xm8PZnGJieY/9SkjKHarn0ZWAGUHGVDF6WuHKmAumNBB3YYQI8" +
      "1u6HE+2TcbJjCpI7/ZHc4a9ey2eGjlOHddgJCrS3asuALE7eycmTVdEHt1or" +
      "1dbqFYQB1wgLQWbOFbh+WQZcO0zMkGchhEp5M8d4pmvhwEnA0rGDgvXx8XlO" +
      "wVx9YA/bYlKKPfxZuwZkmBCUYyJU2yGhmi99gSoOMhwqQOObvZDQornySNsk" +
      "BUrBbCfMjqk43TkNqZenI+1KAM5cnoEUvu7XNPV9Es87wfPlWmlD2kpQTvbq" +
      "CxWyMqIN59ZocM2dG6rHXKNiELhGnSuVjnJupraJ8ObYfUTmYFl2/eWQvQJr" +
      "yU+YvuHr69tLwVIGWL9UewRwGcxiGJjFXcvsAumI7WtDFZfuNXOouO247spT" +
      "dKWAEmgGzLNXA3G+axYyLHS+Kwjn+N3ZqzPVeak6bGlD2jKcLKFC7iUrQyZU" +
      "+hBpxbkr9YRmXo4puJf6a1yB68MdmlRCvswvkrwliUsyd1ZgTfhF9AhY4fdI" +
      "HFv7KrAzuQxmc38dnCcPVrhVLJLdjZ0qpySmvgqq4dIjrZOUOwVGSh/ImQqa" +
      "wMu8NhtZ1+ci+3rwoJLvL/A8Of8cr0u/MlMHPE0BPtbmp7tXCw0D4NZo9e8A" +
      "uKUD4QaaJTOBOzmLNXsmS8xMR/hdcIQ3k/h4c7BRVsG2imPTJ02aBEtZgp2T" +
      "5YgFeQ4IKTBhcbG96pBkf4lZRn06GNREM5dqS32GAqrBnKOA5dyYh0s35ivl" +
      "3VyA/JshViXf5fJcuUauVYDp5HROkoQKuYeEhwTdubstnWsWFsQQK1S8tR8Q" +
      "b823vhIWprDEnHKRCfyCE7x1x0oo+IBg3yVYK/wy3vDz84u2BnbYto8HgA1m" +
      "KFh8iTNcwKK7xGSR/UepXdNWHaokjz1NE/qgai7Vlrw47LwONOe6wNQgFtxc" +
      "iKJbi1HUvQTFVEn3UpR2h/ZJ3hfr38l5+TxfrpUJUS7umq0mK1UPD3JPubfh" +
      "XElqA8ICV5cYYpURb/UNhFHfmm99p2bbsyLSwHrpMVYeo/75oFbHWrIj0x1v" +
      "TJ48OYAvYKn3uEFwStTABjHGLswkUDp2ZRH3+GVaTJW9urGTkhpVsr+UPJI8" +
      "xC2SucWp4lJj2QsAcVseoRTeWoTiWzrEnlCU316OitsrlCpFd1YqVd1ZpY7G" +
      "d3KewC7itdJGHidGJukiAYuDZfLknif0uHuQsf2lsEC40nfDtUZICLay9ZVq" +
      "yJ9gp0goMJLXCQe15X9n6whY4RcsoeB9woWlfhU5HI6sY/1SNLCLuRxW5RNo" +
      "MV1a3r/Xl2Vl1KlSUimoTZ59y18yvUA1YqiCShDiOgFa1rNMA0lw1XdXo6ZP" +
      "a1B3b52uMHWUz+QcAS3XyLXShubgEIaI+ap9mbx+uFMU3HizsCAGECNI3w3X" +
      "GiWY5XOFmUzYgdkOmMEYO41VgYCVB1Kmoyb8fr8drLGjPlA/muGLv1o7wXSI" +
      "mTHZjmC5384l1EIHbCimS8u59KtG9z006Y+r41U9KfHtuL78z1yZoaDKgGXg" +
      "AkCWswG0mpAMiPUEKGroXY/G3o1ouh9upo3q8/o+yP2Axe3SpuHel+FqYcGo" +
      "FqQU63ety8ASzOKhzaw8VkIEG0SwMwQsN0ujkxww/LAJw/bYW4P6vO/vcU2Z" +
      "MiWLgqVsDozExJMM6Gn2CM1xwPpCJ2wuHoWIcu75zXZURq0qy01KKsn+Scqp" +
      "M1TGz1Iu1Za+OKyCS7mKDhU4AqqxdwOadJDN9zeh5cFmtD7YgjYzyXv5XL6X" +
      "c+t7BfBa5eBK3b1aeFiswowGd5ZKagJXJtpIaObxVnaG5olsqdkTsWCWlcH5" +
      "9pib44jZDAUBzDW+qSa4HNeeU/96t91LzKZOndr/w0S+WW4NrN3+kRh/gksi" +
      "nbVqtiPCC52xtdQFWyvpUvUgxdimaiEgvtmzL1nJYCSmyvIXpxpQy3uW97lU" +
      "oAokBfO+wIxA28Ot6HgYiY5HUS+Ln7c9jFCQZRLEwYZ7jRgs95BEKBMpcM+z" +
      "YpBQJBMtE35Ij7cxanfmrsyxwSyRGc90Ba6UlQtYBc1n0paqSMB6pZjgdMwe" +
      "f+LmwBozOrb/x98MtsMI9wUFcw3fawvXozaYdob1KmcsvMAZkaWjsa1iDLZz" +
      "VyNPmGLqjRAwURXnsi014qqRqCSDi5u0pb9aW/aEInCUMwlLoF1+FE1tw5XH" +
      "261KvuvUAct1A927TrlXhQYz50r1ce5qkKpIjHiruVZ7QiaulZBmXtuqn0Yw" +
      "mS2VspJV0CIm7bkEO0OecDE02jC+DttvD0teomnTpg38+1v+/v6dFCzlkMis" +
      "d8oOS7jrCM93RnSpK3aVc+nXjVOdMx6mSGklnZY6UgYhIUCDGqIv/xVq4AKg" +
      "UXepwDGACriuxzG49mTnAHU9iekXv7/6eAeuELC5e6UtaVNWQB9cxl1ZJVKO" +
      "yYYi/erMvpAgrpVEJhWM5tqBta04dyUrn1XFJqxkXlmS56iDtce4JFuGAZZZ" +
      "e+xghVfXS393ix+utgZ2RPwIeDLOLmLg3nTJBdtKxiC2wgO76/of82n1an9p" +
      "JXFNQoA4Rpwj8U+Wv0CV5Ssg2h4YLt2mYAnE65/swo1PYgfV9Se7NNgEfOVx" +
      "v3ulLcO9cg8tqS1XCS1fT2ZSO0uNKxMv4UqSrJhCKplIyydhLCOl8pEKaJUC" +
      "y1DAOn66PPA+aYs/sn61xooKewlsQEDAMNr4BQVLjTtmgxAug/BcF0QXj0Fc" +
      "uQf21zH7N/v0PVA5rocAqQJkJySDMeKqJJdaJpoGs+UvQASOQBJgNz+Jw82n" +
      "u3HLUp9ouqkU1wdYHGyEB8O9ktzkHhK/JTnKhPaHhNlqwqVSkXB1gGaQlSau" +
      "VQ9ravrhqp+rVRCwAZYV0awM7r4YEscct8HvGCKtcXopDBh/+EWltQvcDo3A" +
      "HNazay8yxuaPwe7ScUio8cbxxkk41qLt/yU5SAgwEtYlK26VkmkAVMIRlwqw" +
      "W0/3oPvp3iEloG8+jVPnW7pXkps4V+5hJDQjmckEy0TLhGuxVqsQxLWxZpsG" +
      "qW1ly6t+1F7hokrL1azdpYaXWt43xQ5OR0YMBrV20L/RPWPGDO/p06fDmgIZ" +
      "Z1dxSxdxyVWBTazzQVIjXdrM/X+H9lAl3WxnZdSrMjhVVhkhgFlfXGbuVAFm" +
      "wOt5us+qtO/39Dl5gHvZluFcuYe4VlaIVAoSa2WC5RmElF9GhWBsGoxHjMYD" +
      "cgEsFc9WlpThRc5YzTCw8IIJMxgGJHGZEm0wCKOhf1Ea4XZSsKZF51jLZo9C" +
      "bLEHEqu8kdwwGanN/kjvDFBuuGCxs5JByeCUW3s1t0pcNGJqP9Q9Q0I1B2vI" +
      "3L3SloSFTiZBzbXhWiLjSpEVIxMsfZIK4eyAJObT9/xW6vC+H2BWuyOqzBWb" +
      "WF6uYpk5l271O22P0SfsMQibrlf+kyTG2uDBwAZxKazIcMKOPA8klBNs/RSc" +
      "bZ6Bi53cWV2bq1whTjXfCFTrmwDNrRGaWxkCxG3mTrUEaz0cDARrwJW2jJgr" +
      "rpWaWDYdEmuN8ssIB1KtpOqll/aQRns4Lg+NFGBWO7sqxqrqR6qgldxxBaXa" +
      "YTwT+GBcqJDX+gd0hNtLwZoWnnXA1lw3HCzzQlLdZJxtCkBOxxzkX5vf94TK" +
      "eAYgbq3l4Bp1t0oclMH3h4A9rwC6Rz/HHKi5+uFKrDZCguFaWSmVZklMSq8M" +
      "s3CgbRi0nzxoP94h4NoJiGVyjirijizHGUtYDU09JbF1JAZh0vvav8pv5syZ" +
      "E3jBi8HghmW5YE/xBBytnIT0xhnIJdjirhCU3Vra94Sq0mzLKm5t10srGXy/" +
      "WwcDOxBcv/qhvhQW9IRmuFbuaZRe0icBKztASawSDuTZrbhW4KofWBJwQpMP" +
      "DlR5qRwSmeeKNRedsECSFmPrYCyE1Rf697S8IImCNS0574jIS244VOaNtHqC" +
      "bZ+D0qsLUX1zGWpur0LtHf2hCndEfeWV7tb+2LrnpRAw0Km7XwPswIpBhQTG" +
      "b4njUnrJ/Y0NgxZnF6jnFhlmzxCkrj3ZNhknm/1wpNEXByu9EFfigc25o7kp" +
      "4qaAJdZgHKj0L/yvv1k+vBsYGPg5BWtalzUKu4vH41TVNGQ3E+zlRai5sRxN" +
      "t9ei+Q5dara7aleVQLRKWioMcOkO5dahwHYP0N6+6+Q7mTCJtQJW7mvUtAZY" +
      "if8SDsS1/T91mK6qmlOsbo7VcbtbPgExRe5YyyQ9m7W7L8PAIAyeBwUFvfd3" +
      "/c4CzogfG3gxGNzIXHcklk5CRv0sFLUtQvU1gu1ei9Y7G9B2X6tXZZDmu6vX" +
      "DwPWZelU8+uMCkHF2Yf9NW2VWXWgPbc1fuowC2evzERq+3QkN07B4UofunUc" +
      "trCkFLf6pw4KFYT6j/0eRDaSxEZgTesvcLOQ74lTldOR3TgX5ZeXoPHGWrT1" +
      "bMTle1tx5eE2tTS17Wqs5tRnBPOMQJ7t69eQYWDg+26LEDJg82CUXgTbrsfZ" +
      "er2mNX92qx6My491uuaqiiadlY0k4gNlnojKc8OaTGfMYQgYbNxUypfyy3bY" +
      "UO1gN4nMYelV4ou06kAUtSxG3dU1aLu5CVduR6KrdxuuP96lARGYn+7D7U/3" +
      "486n8Ury2oAr33cPmrB2W4VruXGQ1WDUtEbZZf54UWJ/NXNA+a1QFF9biEuX" +
      "5yKzLQinG6YisdJb1edhDAELWa/7MwRYG++sWbMaGSa/nF8qGRwc/C4b/JyC" +
      "NcXmeuJYiT8ya4NR2hqKhqvr0HFzC7ruRuPmo13o+WRfH8y7nx3Avc8OKd39" +
      "9EA/5GcCea/uaMtYaiFLx+vXybMGKbuuqgQWrWK78fRLxXzG/kbmgJqby1Wy" +
      "laSb3hCAwxW+iCtmCMh1xVIm5hncvg8y1udk8eX+MsnZs2d/QH1OwZriLnnj" +
      "ZNl0ZNXPQ2lbKBq7wnClZytu3IvB7YfxuPfsEHo/pT5LwP3PEqnD+jGRnyfi" +
      "Hr+7Q9D9Lt6ru3ivDnIgTDnvZfebVQdPLJ6AMeZL7JccUHOd8fZyCC42z0Zy" +
      "5TTsLpiILTmuWMa4Otj4KElW/5xfIskZc+UN/jbEzZFaORM59fNR3rYcrdc2" +
      "4WrPNvT00rFP6NSnhPrsMB5+egyPPjtOndB1HA8+O6IgG4ANFxsyAJrLcL9M" +
      "lvnEyET0x9vtuPIgGp33ItDSsx71N1ajjLkgp3ku0moDkFDqyyTshuVDQ/3b" +
      "nDlz/rm/35A3GEvJjTCYzlQGIrc+BFVta9B0JRxXu7ej+x7h3j+Iew8T8fDp" +
      "MTz+9CQ++SyZOqWOjz87iYcK8GHdxQlm0lxtONyaBK4BWMFlyLj+aKeK81fv" +
      "RqqEWn9tDRPsUuQ1L0BqNUNAiR92XpqAocaij/WfC9Us5rryZs9f0SE6dwFK" +
      "Wpah/vIGdN6Ixo2e3bh97yB6H9K1n9Cxz07gyadJOtxThJukHPzws2MEfNTC" +
      "1ZY6rqSde6QvtCiwdHj3E4Lt3YGuO1Ho7GFNe22dClE5DfORWhWA+BIvROeO" +
      "fRXU5/8yqOYxd+7cuZ9TGErnq+egqGkZajvWo60rEtduxaLn7gHcvZ/QB/jx" +
      "M8O9mnMF3GNK3j/97PRL0ibhpDrn0aeciGeE++wowwzBPj2Enif7cOv+HnTd" +
      "Ztl1g1AZ78sEKkOUrKbE4sl4Vb8pSVT/2l/Ma14tULUUXqX8+uWoaAlDQ+dm" +
      "dHSxtr21WwG+Q8D3HiTiwSeMu08F0nElcbMAVDD/qsscLEPJIzmX18kE3X2Q" +
      "wLYOqXjedWc7Om9GMsZv5oSuQ3HLUlysm4eU8kAcKZr6yr4SauOXnv2/6J/o" +
      "6Og32ZGk14GbU7MCJU2rUNO2Ho0E3E7AXTfjcL2bpdJtxuDeQwqQgL7/6IgC" +
      "/eRTPQ5/KkpW7hbo4nQFlBMj4eXmbS797liGnCg0dW1ATcdaFYYkHKVXBuF4" +
      "yTS8Th+plNDQ0K/2l59buNdv3rx5n1N4lfLrQwl4JSpbNqChg9n6yja6OAZd" +
      "dPGNHpZXd+jke4f0cHEED55wmT86qo6GO/uB7qX749B5PRqtXRGo79yAspYV" +
      "uNSwCGerZiOpdDpep0/Uc+rr8+v6rYSGlNcciFJZYziqWjajri0CzZe5x7/C" +
      "4v7aLhUqBPKtO/G4dZvJSD/Ke/lcXN51axc6rm3jxESjrn0LQ816FDeuRHbN" +
      "cnyRPlDpX/nSf50/CxYsmDB//vxeCl9UVS2RaGjfjubOGLQSsoC2dmzhJIjb" +
      "q1s3o6JpM4rqw/F33K9X+vqN+v/NSOxlx4PZ8U4KXzN1USHSx2/0/ymJg/AL" +
      "CQmp5PHFVwy0VvryjQdq+WfJkiXDFi5cGEHIXRT+RRJ3hlH/Hv83Og70A2o5" +
      "B55F/fVLBPmcKvy3gvkK0O8vWrSITEJi6eoMqpHqpZ5TsJB89ohqpbKoHZTE" +
      "8w++Lsv8/wGFpudxDXM1FgAAAABJRU5ErkJggg==";

   private static final String RED_BALL = 
      "data:image/png;base64," +
      "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";

/* Private constants */

   private static final String TITLE_FONT_FAMILY = "Lucida Grande";
   private static final int TITLE_FONT_SIZE = 12;
   private static final double SF = 3;
   private static final double TITLE_BAR_HEIGHT = 18;
   private static final double CORNER_RADIUS = 4;
   private static final double GRAY_TOP = 0.95;
   private static final double GRAY_BOTTOM = 0.80;
   private static final double BALL_FRACTION = 0.65;
   private static final double BALL_X0 = 8;
   private static final double BALL_DX = 18;
   private static final double TITLE_DY = 4;

/* Private instance variables */

   private String title;
   private double sh;
   private double sw;

}
