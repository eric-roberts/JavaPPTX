/*
 * File: PPPicture.java
 * --------------------
 * This class represents a image-containing shape in a PowerPoint slide.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.util.PPOutputStream;
import edu.stanford.cs.pptx.util.PPUtil;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.xml.bind.DatatypeConverter;

/**
 * This class represents a shape in a PowerPoint slide consisting of an image.
 * The image may be specified either as a Java image or by supplying a path
 * name containing the image file.  This use of pictures is illustrated
 * by the following code, which adds a Stanford logo image to the center
 * of the slide, displaying the image in a 200x200 square:
 *
 *<pre>
 *    PPSlide slide = new PPSlide();
 *    double xc = slide.getWidth() / 2;
 *    double yc = slide.getHeight() / 2;
 *    PPPicture logo = new PPPicture("images/StanfordLogo.gif");
 *    logo.setBounds(xc - 100, yc - 100, 200, 200);
 *    slide.add(logo);
 *</pre>
 */

public class PPPicture extends PPSimpleShape {

/**
 * Creates a new <code>PPPicture</code> shape for which the image will
 * be assigned later using <code>setImage</code>.
 */

   public PPPicture() {
      image = null;
   }

/**
 * Creates a new <code>PPPicture</code> shape from the image stored in
 * the file with the specified path.
 *
 * @param path The path name of the image file
 */

   public PPPicture(String path) {
      this.path = path;
      image = nameToImageMap.get(path);
      if (image == null) {
         image = loadImage(path);
         nameToImageMap.put(path, image);
         imageToNameMap.put(image, path);
      }
      setName(path.substring(path.lastIndexOf("/") + 1));
      initPicture();
   }

/**
 * Creates a new <code>PPPicture</code> shape from the specified image.
 *
 * @param image The underlying image
 */

   public PPPicture(Image image) {
      this.image = image;
      path = imageToNameMap.get(image);
      if (path == null) {
         path = "image" + getShapeId() + ".png";
         nameToImageMap.put(path, image);
         imageToNameMap.put(image, path);
      }
      initPicture();
   }

/**
 * Sets the image for a picture.
 *
 * @param image The image
 */

   public void setImage(Image image) {
      this.image = image;
      path = "image" + getShapeId() + ".png";
      nameToImageMap.put(path, image);
      imageToNameMap.put(image, path);
      initPicture();
   }

/**
 * Returns the stored image.
 *
 * @return The image
 */

   public Image getImage() {
      return image;
   }

/**
 * Returns the path to this image
 *
 * @return The path to this image
 */

   public String getPath() {
      return path;
   }

/**
 * Returns the default size of the image
 *
 * @return The default size of the image
 */

   public Dimension2D getDefaultSize() {
      return defaultSize;
   }

/* Public static methods */

/**
 * Loads an image from the specified path.  The strategy for finding the image
 * consists of the following steps:
 *
 * <ol>
 * <li>Check to see if an image with that name has already been defined.  If
 *     so, return that image.</li>
 *
 * <li>Check to see if there is a resource available with that name whose
 *     contents can be read as an <code>Image</code>.  If so, read the image
 *     from the resource file.</li>
 *
 * <li>Load the image from a file with the specified name, relative to the
 *     current directory.</li>
 * </ol>
 *
 * Unlike the <code>getImage</code> method in the <code>Applet</code> class,
 * <code>loadImage</code> waits for an image to be fully loaded before
 * returning.
 *
 * @param path The path name of a file containing the image
 * @return A fully loaded <code>Image</code> object
 */

   public static Image loadImage(String path) {
      Image image = null;
      Toolkit toolkit = Toolkit.getDefaultToolkit();
      if (path.startsWith("data:")) {
         image = readDataImage(path);
      } else if (path.startsWith("http:")) {
         try {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            if (isResource(url) || connection.getContentLength() > 0) {
               Object content = connection.getContent();
               if (content instanceof ImageProducer) {
                  image = toolkit.createImage((ImageProducer) content);
               } else if (content != null) {
                  image = toolkit.getImage(url);
               }
            }
         } catch (MalformedURLException ex) {
            throw new RuntimeException("loadImage: Malformed URL");
         } catch (IOException ex) {
            /* Empty */
         }
      }
      if (image == null) image = toolkit.getImage(path);
      if (image == null) {
         throw new RuntimeException("Cannot find an image named " + path);
      }
      MediaTracker tracker = new MediaTracker(PPUtil.getEmptyContainer());
      tracker.addImage(image, 0);
      try {
         tracker.waitForID(0);
      } catch (InterruptedException ex) {
         throw new RuntimeException("Image loading process interrupted");
      }
      return image;
   }

/* Protected methods */

   @Override
   protected void dumpShape(PPOutputStream os) {
      String shortName = path.substring(path.lastIndexOf("/") + 1);
      os.print("<p:pic>");
      os.print("<p:nvPicPr>");
      os.print("<p:cNvPr id='" + getShapeId() + "' " +
               "name='" + getName() + "' descr='" + shortName + "'/>");
      os.print("<p:cNvPicPr>");
      os.print("<a:picLocks noChangeAspect='1'/>");
      os.print("</p:cNvPicPr>");
      os.print("<p:nvPr/>");
      os.print("</p:nvPicPr>");
      os.print("<p:blipFill>");
      os.print("<a:blip r:embed='rId" + (100 + getShapeId()) + "'/>");
      os.print("<a:stretch>");
      os.print("<a:fillRect/>");
      os.print("</a:stretch>");
      os.print("</p:blipFill>");
      os.print("<p:spPr>");
      os.print("<a:xfrm>");
      Point2D pt = getInitialLocation();
      os.print("<a:off " + os.getOffsetTag(pt.getX(), pt.getY()) + "/>");
      os.print("<a:ext cx='" + PPUtil.pointsToUnits(getWidth()) + "' " +
               "cy='" + PPUtil.pointsToUnits(getHeight()) + "'/>");
      os.print("</a:xfrm>");
      os.print("<a:prstGeom prst='rect'>");
      os.print("<a:avLst/>");
      os.print("</a:prstGeom>");
      os.print("</p:spPr>");
      os.print("</p:pic>");
   }

   @Override
   protected void dumpShapeRels(PPOutputStream os) {
      String shortName = path.substring(path.lastIndexOf("/") + 1);
      os.print("<Relationship Id='rId" + (100 + getShapeId()) + "' ");
      os.print("Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/image' ");
      os.print("Target='../media/" + shortName + "'/>");
   }

   protected static void dumpImages(PPOutputStream os) throws IOException {
      for (String path : nameToImageMap.keySet()) {
         Image image = nameToImageMap.get(path);
         String name = path.substring(path.lastIndexOf("/") + 1);
         os.putNextEntry(new ZipEntry("ppt/media/" + name));
         os.write(convertToPNG(image));
         os.closeEntry();
      }
   }

/* Private methods */

   private void initPicture() {
      defaultSize = new Dimension(image.getWidth(null), image.getHeight(null));
      Point2D pt = getInitialLocation();
      setBounds(pt.getX(), pt.getY(),
                defaultSize.getWidth(), defaultSize.getHeight());
   }

   private static byte[] convertToPNG(Image image) {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      int width = image.getWidth(null);
      int height = image.getHeight(null);
      BufferedImage bi = new BufferedImage(width, height,
                                           BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = bi.createGraphics();
      g.setComposite(AlphaComposite.Src);
      g.drawImage(image, 0, 0, null);
      g.dispose();
      ImageOutputStream ios = new MemoryCacheImageOutputStream(out);
      try {
         if (!ImageIO.write(bi, "PNG", ios)) {
            throw new IOException("ImageIO.write failed");
         }
         ios.close();
      } catch (IOException ex) {
         throw new RuntimeException("saveImage: " + ex.getMessage());
      }
      return out.toByteArray();
   }

   private static boolean isResource(URL url) {
      String name = url.toString().toLowerCase();
      return name.startsWith("jar:") || name.startsWith("file:");
   }

   private static Image readDataImage(String url) {
      int p0 = url.indexOf(",") + 1;
      if (p0 == 0) throw new RuntimeException("Malformed data URL");
      byte[] data = DatatypeConverter.parseBase64Binary(url.substring(p0));
      return Toolkit.getDefaultToolkit().createImage(data);
   }

/* Private instance variables */

   private Dimension defaultSize;
   private Image image;
   private String path;

/* Static variables */

   private static HashMap<String,Image> nameToImageMap =
      new HashMap<String,Image>();
   private static HashMap<Image,String> imageToNameMap =
      new HashMap<Image,String>();
}
