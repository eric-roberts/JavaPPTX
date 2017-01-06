/*
 * File: PPSavePPTX.java
 * ---------------------
 * This package class encapsulates the extensive code necessary to save a
 * PowerPoint show to a .pptx file.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.hexbyte.HexByteInputStream;
import edu.stanford.cs.pptx.util.PPOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;

class PPSavePPTX {

   public PPSavePPTX(PPShow show) {
      this.show = show;
   }

   public void save(String filename) {
      int nSlides = show.getSlideCount();
      if (filename.startsWith("~/")) {
         filename = System.getProperty("user.home") + filename.substring(1);
      }
      try {
         PPOutputStream os = new PPOutputStream(new File(filename));
         for (int i = 1; i < nSlides; i++) {
            show.getSlide(i).preSaveHook();
         }
         dumpStandardFiles(os);
         dumpSlides(os);
         dumpMedia(os);
         os.close();
      } catch (IOException ex) {
         throw new RuntimeException(ex.toString());
      }
   }

/* Private methods */

   private void dumpStandardFiles(PPOutputStream os) throws IOException {
      dumpContents(os);
      dumpFile(os, "_rels/.rels", DOT_RELS);
      dumpFile(os, "ppt/notesMasters/_rels/notesMaster1.xml.rels",
                   NOTES_MASTER_1_RELS);
      dumpFile(os, "ppt/notesMasters/notesMaster1.xml", NOTES_MASTER_1);
      dumpFile(os, "ppt/presProps.xml", PRES_PROPS);
      dumpFile(os, "ppt/slideLayouts/_rels/slideLayout1.xml.rels",
                   SLIDE_LAYOUT_1_RELS);
      dumpFile(os, "ppt/slideLayouts/_rels/slideLayout10.xml.rels",
                   SLIDE_LAYOUT_10_RELS);
      dumpFile(os, "ppt/slideLayouts/_rels/slideLayout11.xml.rels",
                   SLIDE_LAYOUT_11_RELS);
      dumpFile(os, "ppt/slideLayouts/_rels/slideLayout2.xml.rels",
                   SLIDE_LAYOUT_2_RELS);
      dumpFile(os, "ppt/slideLayouts/_rels/slideLayout3.xml.rels",
                   SLIDE_LAYOUT_3_RELS);
      dumpFile(os, "ppt/slideLayouts/_rels/slideLayout4.xml.rels",
                   SLIDE_LAYOUT_4_RELS);
      dumpFile(os, "ppt/slideLayouts/_rels/slideLayout5.xml.rels",
                   SLIDE_LAYOUT_5_RELS);
      dumpFile(os, "ppt/slideLayouts/_rels/slideLayout6.xml.rels",
                   SLIDE_LAYOUT_6_RELS);
      dumpFile(os, "ppt/slideLayouts/_rels/slideLayout7.xml.rels",
                   SLIDE_LAYOUT_7_RELS);
      dumpFile(os, "ppt/slideLayouts/_rels/slideLayout8.xml.rels",
                   SLIDE_LAYOUT_8_RELS);
      dumpFile(os, "ppt/slideLayouts/_rels/slideLayout9.xml.rels",
                   SLIDE_LAYOUT_9_RELS);
      dumpFile(os, "ppt/slideLayouts/slideLayout1.xml", SLIDE_LAYOUT_1);
      dumpFile(os, "ppt/slideLayouts/slideLayout10.xml", SLIDE_LAYOUT_10);
      dumpFile(os, "ppt/slideLayouts/slideLayout11.xml", SLIDE_LAYOUT_11);
      dumpFile(os, "ppt/slideLayouts/slideLayout2.xml", SLIDE_LAYOUT_2);
      dumpFile(os, "ppt/slideLayouts/slideLayout3.xml", SLIDE_LAYOUT_3);
      dumpFile(os, "ppt/slideLayouts/slideLayout4.xml", SLIDE_LAYOUT_4);
      dumpFile(os, "ppt/slideLayouts/slideLayout5.xml", SLIDE_LAYOUT_5);
      dumpFile(os, "ppt/slideLayouts/slideLayout6.xml", SLIDE_LAYOUT_6);
      dumpFile(os, "ppt/slideLayouts/slideLayout7.xml", SLIDE_LAYOUT_7);
      dumpFile(os, "ppt/slideLayouts/slideLayout8.xml", SLIDE_LAYOUT_8);
      dumpFile(os, "ppt/slideLayouts/slideLayout9.xml", SLIDE_LAYOUT_9);
      dumpFile(os, "ppt/slideMasters/_rels/slideMaster1.xml.rels",
                   SLIDE_MASTER_1_RELS);
      dumpFile(os, "ppt/slideMasters/slideMaster1.xml", SLIDE_MASTER_1);
      dumpFile(os, "ppt/tableStyles.xml", TABLE_STYLES);
      dumpFile(os, "ppt/theme/theme1.xml", THEME_1);
      dumpFile(os, "ppt/theme/theme2.xml", THEME_2);
      dumpFile(os, "ppt/viewProps.xml", VIEW_PROPS);
      dumpFile(os, "ppt/printerSettings/printerSettings1.bin",
                   PRINTER_SETTINGS_1);
      dumpPresentation(os);
      dumpPresentationRels(os);
      dumpDocPropsApp(os);
      dumpDocPropsCore(os);
   }

   private void dumpContents(PPOutputStream os) throws IOException {
      int nSlides = show.getSlideCount();
      os.putNextEntry(new ZipEntry("[Content_Types].xml"));
      os.println("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
      os.print("<Types xmlns='http://schemas.openxmlformats.org/" +
               "package/2006/content-types'>");
      os.print("<Default Extension='bin' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.printerSettings'/>");
      os.print("<Default Extension='rels' " +
               "ContentType='application/vnd.openxmlformats" +
               "-package.relationships+xml'/>");
      os.print("<Default Extension='xml' ContentType='application/xml'/>");
      os.print("<Default Extension='png' ContentType='image/png'/>");
      os.print("<Override PartName='/docProps/app.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.extended-properties+xml'/>");
      os.print("<Override PartName='/docProps/core.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-package.core-properties+xml'/>");
      os.print("<Override PartName='/ppt/notesMasters/notesMaster1.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.notesMaster+xml'/>");
      os.print("<Override PartName='/ppt/presProps.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.presProps+xml'/>");
      os.print("<Override PartName='/ppt/presentation.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.presentation.main+xml'/>");
      os.print("<Override PartName='/ppt/slideLayouts/slideLayout1.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideLayout+xml'/>");
      os.print("<Override PartName='/ppt/slideLayouts/slideLayout2.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideLayout+xml'/>");
      os.print("<Override PartName='/ppt/slideLayouts/slideLayout3.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideLayout+xml'/>");
      os.print("<Override PartName='/ppt/slideLayouts/slideLayout4.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideLayout+xml'/>");
      os.print("<Override PartName='/ppt/slideLayouts/slideLayout5.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideLayout+xml'/>");
      os.print("<Override PartName='/ppt/slideLayouts/slideLayout6.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideLayout+xml'/>");
      os.print("<Override PartName='/ppt/slideLayouts/slideLayout7.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideLayout+xml'/>");
      os.print("<Override PartName='/ppt/slideLayouts/slideLayout8.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideLayout+xml'/>");
      os.print("<Override PartName='/ppt/slideLayouts/slideLayout9.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideLayout+xml'/>");
      os.print("<Override PartName='/ppt/slideLayouts/slideLayout10.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideLayout+xml'/>");
      os.print("<Override PartName='/ppt/slideLayouts/slideLayout11.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideLayout+xml'/>");
      os.print("<Override PartName='/ppt/slideMasters/slideMaster1.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slideMaster+xml'/>");
      for (int i = 1; i <= nSlides; i++) {
         os.print("<Override PartName='/ppt/slides/slide" + i + ".xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.slide+xml'/>");
         os.print("<Override PartName='/ppt/notesSlides/notesSlide" + i +
                  ".xml' ContentType='application/vnd.openxmlformats" +
                  "-officedocument.presentationml.notesSlide+xml'/>");
      }
      os.print("<Override PartName='/ppt/tableStyles.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.tableStyles+xml'/>");
      os.print("<Override PartName='/ppt/theme/theme1.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.theme+xml'/>");
      os.print("<Override PartName='/ppt/theme/theme2.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.theme+xml'/>");
      os.print("<Override PartName='/ppt/viewProps.xml' " +
               "ContentType='application/vnd.openxmlformats" +
               "-officedocument.presentationml.viewProps+xml'/>");
      os.println("</Types>");
   }

   private void dumpDocPropsApp(PPOutputStream os) throws IOException {
      int nSlides = show.getSlideCount();
      os.putNextEntry(new ZipEntry("docProps/app.xml"));
      os.println("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
      os.print("<Properties ");
      os.print("xmlns='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/extended-properties' ");
      os.print("xmlns:vt='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/docPropsVTypes'>");
      os.print("<Application>Microsoft Macintosh PowerPoint</Application>");
      os.print("<PresentationFormat>" + show.getFormat() +
               "</PresentationFormat>");
      os.print("<Slides>" + nSlides + "</Slides>");
      os.print("<Notes>" + nSlides + "</Notes>");
      os.print("<HiddenSlides>0</HiddenSlides>");
      os.print("<MMClips>0</MMClips>");
      os.print("<ScaleCrop>false</ScaleCrop>");
      os.print("<HeadingPairs><vt:vector size='4' baseType='variant'>");
      os.print("<vt:variant><vt:lpstr>Design Template</vt:lpstr>" +
               "</vt:variant>");
      os.print("<vt:variant><vt:i4>1</vt:i4></vt:variant>");
      os.print("<vt:variant><vt:lpstr>Slide Titles</vt:lpstr>" +
               "</vt:variant>");
      os.print("<vt:variant><vt:i4>" + nSlides + "</vt:i4></vt:variant>");
      os.print("</vt:vector></HeadingPairs>");
      os.print("<TitlesOfParts><vt:vector size='" + (nSlides + 1) +
               "' baseType='lpstr'>");
      os.print("<vt:lpstr>Blank Presentation</vt:lpstr>");
      for (int i = 1; i <= nSlides; i++) {
         os.print("<vt:lpstr>" + show.getSlide(i).getTitle() + "</vt:lpstr>");
      }
      os.print("</vt:vector></TitlesOfParts>");
      String company = System.getenv("ORGANIZATION");
      if (company == null) company = "UNKNOWN";
      os.print("<Company>" + company + "</Company>");
      os.print("<LinksUpToDate>false</LinksUpToDate>");
      os.print("<SharedDoc>false</SharedDoc>");
      os.print("<HyperlinksChanged>false</HyperlinksChanged>");
      os.print("<AppVersion>12.0000</AppVersion>");
      os.println("</Properties>");
      os.closeEntry();
   }

   private void dumpDocPropsCore(PPOutputStream os) throws IOException {
      os.putNextEntry(new ZipEntry("docProps/core.xml"));
      os.println("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
      os.print("<cp:coreProperties ");
      os.print("xmlns:cp='http://schemas.openxmlformats.org/package/2006/" +
               "metadata/core-properties' ");
      os.print("xmlns:dc='http://purl.org/dc/elements/1.1/' ");
      os.print("xmlns:dcterms='http://purl.org/dc/terms/' ");
      os.print("xmlns:dcmitype='http://purl.org/dc/dcmitype/' ");
      os.print("xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>");
      os.print("<dc:title>" + show.getTitle() + "</dc:title>");
      os.print("<cp:lastModifiedBy>" + System.getenv("USER") +
               "</cp:lastModifiedBy>");
      os.print("<cp:revision>1</cp:revision>");
      String date = new SimpleDateFormat(W3CDTF_FORMAT).format(new Date());
      os.print("<dcterms:created xsi:type='dcterms:W3CDTF'>" + date +
               "</dcterms:created>");
      os.print("<dcterms:modified xsi:type='dcterms:W3CDTF'>" + date +
               "</dcterms:modified>");
      os.println("</cp:coreProperties>");
      os.closeEntry();
   }

   private void dumpSlides(PPOutputStream os) throws IOException {
      int n = show.getSlideCount();
      for (int i = 1; i <= n; i++) {
         dumpSlide(os, i);
      }
   }

   private void dumpSlide(PPOutputStream os, int index) throws IOException {
      dumpSlideXML(os, index);
      dumpSlideRels(os, index);
      dumpNotesXML(os, index);
      dumpNotesRels(os, index);
   }

   private void dumpPresentation(PPOutputStream os) throws IOException {
      int n = show.getSlideCount();
      os.putNextEntry(new ZipEntry("ppt/presentation.xml"));
      os.println("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
      os.print("<p:presentation ");
      os.print("xmlns:a='http://schemas.openxmlformats.org/" +
               "drawingml/2006/main' ");
      os.print("xmlns:r='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships' ");
      os.print("xmlns:mc='http://schemas.openxmlformats.org/" +
               "markup-compatibility/2006' ");
      os.print("xmlns:mv='urn:schemas-microsoft-com:mac:vml' ");
      os.print("mc:Ignorable='mv' mc:PreserveAttributes='mv:*' ");
      os.print("xmlns:p='http://schemas.openxmlformats.org/" +
               "presentationml/2006/main' ");
      os.print("strictFirstAndLastChars='0' saveSubsetFonts='1'>");
      os.print("<p:sldMasterIdLst>");
      os.print("<p:sldMasterId id='2147483648' r:id='rId1'/>");
      os.print("</p:sldMasterIdLst>");
      os.print("<p:notesMasterIdLst>");
      os.print("<p:notesMasterId r:id='rId4'/>");
      os.print("</p:notesMasterIdLst>");
      os.print("<p:sldIdLst>");
      for (int i = 1; i <= n; i++) {
         os.print("<p:sldId id='" + (516 + i) + "' r:id='rId" +
                  (100 + i) + "'/>");
      }
      os.print("</p:sldIdLst>");
      os.print("<p:sldSz cx='9144000' cy='6858000' type='screen4x3'/>");
      os.print("<p:notesSz cx='9144000' cy='6858000'/>");
      os.print("<p:defaultTextStyle>");
      os.print("<a:defPPr><a:defRPr lang='en-US'/></a:defPPr>");
      os.print("<a:lvl1pPr algn='l' rtl='0' eaLnBrk='0' " +
               "fontAlgn='base' hangingPunct='0'>");
      os.print("<a:spcBef><a:spcPct val='0'/></a:spcBef>");
      os.print("<a:spcAft><a:spcPct val='0'/></a:spcAft>");
      os.print("<a:defRPr sz='1400' kern='1200'>");
      os.print("<a:solidFill><a:schemeClr val='tx1'/></a:solidFill>");
      os.print("<a:latin typeface='Helvetica Neue' pitchFamily='1' " +
               "charset='0'/>");
      os.print("<a:ea typeface='+mn-ea'/>");
      os.print("<a:cs typeface='+mn-cs'/>");
      os.print("</a:defRPr>");
      os.print("</a:lvl1pPr>");
      os.print("<a:lvl2pPr marL='457200' algn='l' rtl='0' eaLnBrk='0' " +
               "fontAlgn='base' hangingPunct='0'>");
      os.print("<a:spcBef><a:spcPct val='0'/></a:spcBef>");
      os.print("<a:spcAft><a:spcPct val='0'/></a:spcAft>");
      os.print("<a:defRPr sz='1400' kern='1200'>");
      os.print("<a:solidFill><a:schemeClr val='tx1'/></a:solidFill>");
      os.print("<a:latin typeface='Helvetica Neue' pitchFamily='1' " +
               "charset='0'/>");
      os.print("<a:ea typeface='+mn-ea'/>");
      os.print("<a:cs typeface='+mn-cs'/>");
      os.print("</a:defRPr>");
      os.print("</a:lvl2pPr>");
      os.print("<a:lvl3pPr marL='914400' algn='l' rtl='0' eaLnBrk='0' " +
               "fontAlgn='base' hangingPunct='0'>");
      os.print("<a:spcBef><a:spcPct val='0'/></a:spcBef>");
      os.print("<a:spcAft><a:spcPct val='0'/></a:spcAft>");
      os.print("<a:defRPr sz='1400' kern='1200'>");
      os.print("<a:solidFill><a:schemeClr val='tx1'/></a:solidFill>");
      os.print("<a:latin typeface='Helvetica Neue' pitchFamily='1' " +
               "charset='0'/>");
      os.print("<a:ea typeface='+mn-ea'/>");
      os.print("<a:cs typeface='+mn-cs'/>");
      os.print("</a:defRPr>");
      os.print("</a:lvl3pPr>");
      os.print("<a:lvl4pPr marL='1371600' algn='l' rtl='0' eaLnBrk='0' " +
               "fontAlgn='base' hangingPunct='0'>");
      os.print("<a:spcBef><a:spcPct val='0'/></a:spcBef>");
      os.print("<a:spcAft><a:spcPct val='0'/></a:spcAft>");
      os.print("<a:defRPr sz='1400' kern='1200'>");
      os.print("<a:solidFill><a:schemeClr val='tx1'/></a:solidFill>");
      os.print("<a:latin typeface='Helvetica Neue' pitchFamily='1' " +
               "charset='0'/>");
      os.print("<a:ea typeface='+mn-ea'/>");
      os.print("<a:cs typeface='+mn-cs'/>");
      os.print("</a:defRPr>");
      os.print("</a:lvl4pPr>");
      os.print("<a:lvl5pPr marL='1828800' algn='l' rtl='0' eaLnBrk='0' " +
               "fontAlgn='base' hangingPunct='0'>");
      os.print("<a:spcBef><a:spcPct val='0'/></a:spcBef>");
      os.print("<a:spcAft><a:spcPct val='0'/></a:spcAft>");
      os.print("<a:defRPr sz='1400' kern='1200'>");
      os.print("<a:solidFill><a:schemeClr val='tx1'/></a:solidFill>");
      os.print("<a:latin typeface='Helvetica Neue' pitchFamily='1' " +
               "charset='0'/>");
      os.print("<a:ea typeface='+mn-ea'/>");
      os.print("<a:cs typeface='+mn-cs'/>");
      os.print("</a:defRPr>");
      os.print("</a:lvl5pPr>");
      os.print("<a:lvl6pPr marL='2286000' algn='l' defTabSz='457200' " +
               "rtl='0' eaLnBrk='1' latinLnBrk='0' hangingPunct='1'>");
      os.print("<a:defRPr sz='1400' kern='1200'>");
      os.print("<a:solidFill><a:schemeClr val='tx1'/></a:solidFill>");
      os.print("<a:latin typeface='Helvetica Neue' pitchFamily='1' " +
               "charset='0'/>");
      os.print("<a:ea typeface='+mn-ea'/>");
      os.print("<a:cs typeface='+mn-cs'/>");
      os.print("</a:defRPr>");
      os.print("</a:lvl6pPr>");
      os.print("<a:lvl7pPr marL='2743200' algn='l' defTabSz='457200' " +
               "rtl='0' eaLnBrk='1' latinLnBrk='0' hangingPunct='1'>");
      os.print("<a:defRPr sz='1400' kern='1200'>");
      os.print("<a:solidFill><a:schemeClr val='tx1'/></a:solidFill>");
      os.print("<a:latin typeface='Helvetica Neue' pitchFamily='1' " +
               "charset='0'/>");
      os.print("<a:ea typeface='+mn-ea'/>");
      os.print("<a:cs typeface='+mn-cs'/>");
      os.print("</a:defRPr>");
      os.print("</a:lvl7pPr>");
      os.print("<a:lvl8pPr marL='3200400' algn='l' defTabSz='457200' " +
               "rtl='0' eaLnBrk='1' latinLnBrk='0' hangingPunct='1'>");
      os.print("<a:defRPr sz='1400' kern='1200'>");
      os.print("<a:solidFill><a:schemeClr val='tx1'/></a:solidFill>");
      os.print("<a:latin typeface='Helvetica Neue' pitchFamily='1' " +
               "charset='0'/>");
      os.print("<a:ea typeface='+mn-ea'/>");
      os.print("<a:cs typeface='+mn-cs'/>");
      os.print("</a:defRPr>");
      os.print("</a:lvl8pPr>");
      os.print("<a:lvl9pPr marL='3657600' algn='l' defTabSz='457200' " +
               "rtl='0' eaLnBrk='1' latinLnBrk='0' hangingPunct='1'>");
      os.print("<a:defRPr sz='1400' kern='1200'>");
      os.print("<a:solidFill><a:schemeClr val='tx1'/></a:solidFill>");
      os.print("<a:latin typeface='Helvetica Neue' pitchFamily='1' " +
               "charset='0'/>");
      os.print("<a:ea typeface='+mn-ea'/>");
      os.print("<a:cs typeface='+mn-cs'/>");
      os.print("</a:defRPr>");
      os.print("</a:lvl9pPr>");
      os.print("</p:defaultTextStyle>");
      os.println("</p:presentation>");
   }

   private void dumpPresentationRels(PPOutputStream os) throws IOException {
      int n = show.getSlideCount();
      os.putNextEntry(new ZipEntry("ppt/_rels/presentation.xml.rels"));
      os.println("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
      os.print("<Relationships ");
      os.print("xmlns='http://schemas.openxmlformats.org/" +
               "package/2006/relationships'>");
      os.print("<Relationship Id='rId1' " +
               "Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/slideMaster' " +
               "Target='slideMasters/slideMaster1.xml'/>");
      os.print("<Relationship Id='rId4' " +
               "Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/notesMaster' " +
               "Target='notesMasters/notesMaster1.xml'/>");
      os.print("<Relationship Id='rId5' " +
               "Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/printerSettings' " +
               "Target='printerSettings/printerSettings1.bin'/>");
      os.print("<Relationship Id='rId6' " +
               "Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/presProps' " +
               "Target='presProps.xml'/>");
      os.print("<Relationship Id='rId7' " +
               "Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/viewProps' " +
               "Target='viewProps.xml'/>");
      os.print("<Relationship Id='rId8' " +
               "Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/theme' " +
               "Target='theme/theme1.xml'/>");
      os.print("<Relationship Id='rId9' " +
               "Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/tableStyles' " +
               "Target='tableStyles.xml'/>");
      for (int i = 1; i <= n; i++) {
         os.print("<Relationship Id='rId" + (100 + i) + "' " +
                  "Type='http://schemas.openxmlformats.org/" +
                  "officeDocument/2006/relationships/slide' " +
                  "Target='slides/slide" + i + ".xml'/>");
      }
      os.println("</Relationships>");
      os.closeEntry();
   }

   private void dumpSlideXML(PPOutputStream os, int index)
                throws IOException {
      os.putNextEntry(new ZipEntry("ppt/slides/slide" +
                                   index + ".xml"));
      show.getSlide(index).dumpSlide(os);
      os.closeEntry();
   }

   private void dumpSlideRels(PPOutputStream os, int index)
                throws IOException {
      PPSlide slide = show.getSlide(index);
      os.putNextEntry(new ZipEntry("ppt/slides/_rels/slide" +
                                   index + ".xml.rels"));
      os.println("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
      os.print("<Relationships ");
      os.print("xmlns='http://schemas.openxmlformats.org/" +
               "package/2006/relationships'>");
      os.print("<Relationship Id='rId1' ");
      os.print("Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/slideLayout' ");
      os.print("Target='../slideLayouts/slideLayout2.xml'/>");
      os.print("<Relationship Id='rId2' ");
      os.print("Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/notesSlide' ");
      os.print("Target='../notesSlides/notesSlide" + index + ".xml'/>");
      slide.dumpSlideRels(os);
      os.println("</Relationships>");
      os.closeEntry();
   }

   private void dumpNotesXML(PPOutputStream os, int index)
      throws IOException {
      os.putNextEntry(new ZipEntry("ppt/notesSlides/notesSlide" +
                                   index + ".xml"));
      os.println("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
      os.print("<p:notes ");
      os.print("xmlns:a='http://schemas.openxmlformats.org/" +
               "drawingml/2006/main' ");
      os.print("xmlns:r='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships' ");
      os.print("xmlns:mc='http://schemas.openxmlformats.org/" +
               "markup-compatibility/2006' ");
      os.print("xmlns:mv='urn:schemas-microsoft-com:mac:vml' ");
      os.print("mc:Ignorable='mv' mc:PreserveAttributes='mv:*' ");
      os.print("xmlns:p='http://schemas.openxmlformats.org/" +
               "presentationml/2006/main'>");
      os.print("<p:cSld><p:spTree><p:nvGrpSpPr>");
      os.print("<p:cNvPr id='0' name='title'/>");
      os.print("<p:cNvGrpSpPr/><p:nvPr/></p:nvGrpSpPr>");
      os.print("<p:grpSpPr><a:xfrm><a:off x='0' y='0'/>");
      os.print("<a:ext cx='0' cy='0'/><a:chOff x='0' y='0'/>");
      os.print("<a:chExt cx='0' cy='0'/></a:xfrm></p:grpSpPr>");
      os.print("<p:sp><p:nvSpPr>");
      os.print("<p:cNvPr id='135170' name='Rectangle 2'/>");
      os.print("<p:cNvSpPr>");
      os.print("<a:spLocks noGrp='1' noRot='1' noChangeAspect='1' " +
               "noChangeArrowheads='1'/>");
      os.print("</p:cNvSpPr><p:nvPr><p:ph type='sldImg'/>");
      os.print("</p:nvPr></p:nvSpPr><p:spPr bwMode='auto'>");
      os.print("<a:xfrm><a:off x='2844800' y='533400'/>");
      os.print("<a:ext cx='3454400' cy='2590800'/></a:xfrm>");
      os.print("<a:prstGeom prst='rect'><a:avLst/></a:prstGeom>");
      os.print("<a:solidFill><a:srgbClr val='FFFFFF'/></a:solidFill>");
      os.print("<a:ln>");
      os.print("<a:solidFill><a:srgbClr val='000000'/></a:solidFill>");
      os.print("<a:miter lim='800000'/><a:headEnd/><a:tailEnd/>");
      os.print("</a:ln>");
      os.print("</p:spPr></p:sp>");
      os.print("<p:sp><p:nvSpPr>");
      os.print("<p:cNvPr id='135171' name='Rectangle 3'/><p:cNvSpPr>");
      os.print("<a:spLocks noGrp='1' noChangeArrowheads='1'/>");
      os.print("</p:cNvSpPr>");
      os.print("<p:nvPr><p:ph type='body' idx='1'/></p:nvPr>");
      os.print("</p:nvSpPr>");
      os.print("<p:spPr bwMode='auto'><a:xfrm>");
      os.print("<a:off x='1219200' y='3276600'/>");
      os.print("<a:ext cx='6705600' cy='3048000'/></a:xfrm>");
      os.print("<a:prstGeom prst='rect'><a:avLst/></a:prstGeom>");
      os.print("<a:solidFill><a:srgbClr val='FFFFFF'/></a:solidFill>");
      os.print("<a:ln>");
      os.print("<a:solidFill><a:srgbClr val='000000'/></a:solidFill>");
      os.print("<a:miter lim='800000'/><a:headEnd/><a:tailEnd/>");
      os.print("</a:ln>");
      os.print("</p:spPr><p:txBody><a:bodyPr>");
      os.print("<a:prstTxWarp prst='textNoShape'>");
      os.print("<a:avLst/></a:prstTxWarp></a:bodyPr>");
      os.print("<a:lstStyle/><a:p><a:endParaRPr lang='en-US'/></a:p>");
      os.print("</p:txBody></p:sp></p:spTree></p:cSld>");
      os.print("<p:clrMapOvr><a:masterClrMapping/></p:clrMapOvr>");
      os.println("</p:notes>");
      os.closeEntry();
   }

   private void dumpNotesRels(PPOutputStream os, int index)
                throws IOException {
      os.putNextEntry(new ZipEntry("ppt/notesSlides/_rels/notesSlide" +
                                   index + ".xml.rels"));
      os.println("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
      os.print("<Relationships ");
      os.print("xmlns='http://schemas.openxmlformats.org/" +
               "package/2006/relationships'>");
      os.print("<Relationship Id='rId1' ");
      os.print("Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/notesMaster' ");
      os.print("Target='../notesMasters/notesMaster1.xml'/>");
      os.print("<Relationship Id='rId2' ");
      os.print("Type='http://schemas.openxmlformats.org/" +
               "officeDocument/2006/relationships/slide' ");
      os.print("Target='../slides/slide" + index + ".xml'/>");
      os.println("</Relationships>");
      os.closeEntry();
   }

   private void dumpMedia(PPOutputStream os) throws IOException {
      PPPicture.dumpImages(os);
   }

   private void dumpFile(PPOutputStream os, String name, String bytes)
                throws IOException {
      os.putNextEntry(new ZipEntry(name));
      os.write(HexByteInputStream.toByteArray(bytes));
      os.closeEntry();
   }

/* Private constants */

   private static final String W3CDTF_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

   private static final String DOT_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F6F6666696365446F63756D656E74222054" +
      "61726765743D227070742F70726573656E746174696F6E2E786D6C222F3E3C52" +
      "656C6174696F6E736869702049643D22724964322220547970653D2268747470" +
      "3A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F7061" +
      "636B6167652F323030362F72656C6174696F6E73686970732F6D657461646174" +
      "612F636F72652D70726F7065727469657322205461726765743D22646F635072" +
      "6F70732F636F72652E786D6C222F3E3C52656C6174696F6E736869702049643D" +
      "22724964332220547970653D22687474703A2F2F736368656D61732E6F70656E" +
      "786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F3230" +
      "30362F72656C6174696F6E73686970732F657874656E6465642D70726F706572" +
      "7469657322205461726765743D22646F6350726F70732F6170702E786D6C222F" +
      "3E3C2F52656C6174696F6E73686970733E";

   public static final String NOTES_MASTER_1_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F7468656D6522205461726765743D222E2E" +
      "2F7468656D652F7468656D65322E786D6C222F3E3C2F52656C6174696F6E7368" +
      "6970733E";

   public static final String NOTES_MASTER_1 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A6E6F7465" +
      "734D617374657220786D6C6E733A613D22687474703A2F2F736368656D61732E" +
      "6F70656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030" +
      "362F6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E" +
      "6F70656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E" +
      "742F323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C" +
      "6E733A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D" +
      "3A6D61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A50" +
      "72657365727665417474726962757465733D226D763A2A2220786D6C6E733A70" +
      "3D22687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E" +
      "6F72672F70726573656E746174696F6E6D6C2F323030362F6D61696E223E3C70" +
      "3A63536C643E3C703A62673E3C703A626750723E3C613A736F6C696446696C6C" +
      "3E3C613A736368656D65436C722076616C3D22626731222F3E3C2F613A736F6C" +
      "696446696C6C3E3C613A6566666563744C73742F3E3C2F703A626750723E3C2F" +
      "703A62673E3C703A7370547265653E3C703A6E76477270537050723E3C703A63" +
      "4E7650722069643D223122206E616D653D22222F3E3C703A634E764772705370" +
      "50722F3E3C703A6E7650722F3E3C2F703A6E76477270537050723E3C703A6772" +
      "70537050723E3C613A7866726D3E3C613A6F666620783D22302220793D223022" +
      "2F3E3C613A6578742063783D2230222063793D2230222F3E3C613A63684F6666" +
      "20783D22302220793D2230222F3E3C613A63684578742063783D223022206379" +
      "3D2230222F3E3C2F613A7866726D3E3C2F703A677270537050723E3C703A7370" +
      "3E3C703A6E76537050723E3C703A634E7650722069643D22393732383222206E" +
      "616D653D2252656374616E676C652032222F3E3C703A634E76537050723E3C61" +
      "3A73704C6F636B73206E6F4772703D223122206E6F4368616E67654172726F77" +
      "68656164733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C" +
      "703A706820747970653D226864722220737A3D2271756172746572222F3E3C2F" +
      "703A6E7650723E3C2F703A6E76537050723E3C703A737050722062774D6F6465" +
      "3D226175746F223E3C613A7866726D3E3C613A6F666620783D22302220793D22" +
      "30222F3E3C613A6578742063783D2233393632343030222063793D2233383130" +
      "3030222F3E3C2F613A7866726D3E3C613A7072737447656F6D20707273743D22" +
      "72656374223E3C613A61764C73742F3E3C2F613A7072737447656F6D3E3C613A" +
      "6E6F46696C6C2F3E3C613A6C6E20773D2239353235223E3C613A6E6F46696C6C" +
      "2F3E3C613A6D69746572206C696D3D22383030303030222F3E3C613A68656164" +
      "456E642F3E3C613A7461696C456E642F3E3C2F613A6C6E3E3C613A6566666563" +
      "744C73742F3E3C2F703A737050723E3C703A7478426F64793E3C613A626F6479" +
      "507220766572743D22686F727A2220777261703D2273717561726522206C496E" +
      "733D223931343430222074496E733D223435373230222072496E733D22393134" +
      "3430222062496E733D22343537323022206E756D436F6C3D22312220616E6368" +
      "6F723D22742220616E63686F724374723D22302220636F6D7061744C6E537063" +
      "3D2231223E3C613A7072737454785761727020707273743D22746578744E6F53" +
      "68617065223E3C613A61764C73742F3E3C2F613A707273745478576172703E3C" +
      "2F613A626F647950723E3C613A6C73745374796C653E3C613A6C766C31705072" +
      "3E3C613A64656652507220737A3D2231323030223E3C613A6C6174696E207479" +
      "7065666163653D2254696D6573204E657720526F6D616E222070697463684661" +
      "6D696C793D22312220636861727365743D2230222F3E3C2F613A646566525072" +
      "3E3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C613A703E3C" +
      "613A656E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A70" +
      "3E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E765370" +
      "50723E3C703A634E7650722069643D22393732383322206E616D653D22526563" +
      "74616E676C652033222F3E3C703A634E76537050723E3C613A73704C6F636B73" +
      "206E6F4772703D223122206E6F4368616E67654172726F7768656164733D2231" +
      "222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820747970" +
      "653D22647422206964783D2231222F3E3C2F703A6E7650723E3C2F703A6E7653" +
      "7050723E3C703A737050722062774D6F64653D226175746F223E3C613A786672" +
      "6D3E3C613A6F666620783D22353138313630302220793D2230222F3E3C613A65" +
      "78742063783D2233393632343030222063793D22333831303030222F3E3C2F61" +
      "3A7866726D3E3C613A7072737447656F6D20707273743D2272656374223E3C61" +
      "3A61764C73742F3E3C2F613A7072737447656F6D3E3C613A6E6F46696C6C2F3E" +
      "3C613A6C6E20773D2239353235223E3C613A6E6F46696C6C2F3E3C613A6D6974" +
      "6572206C696D3D22383030303030222F3E3C613A68656164456E642F3E3C613A" +
      "7461696C456E642F3E3C2F613A6C6E3E3C613A6566666563744C73742F3E3C2F" +
      "703A737050723E3C703A7478426F64793E3C613A626F6479507220766572743D" +
      "22686F727A2220777261703D2273717561726522206C496E733D223931343430" +
      "222074496E733D223435373230222072496E733D223931343430222062496E73" +
      "3D22343537323022206E756D436F6C3D22312220616E63686F723D2274222061" +
      "6E63686F724374723D22302220636F6D7061744C6E5370633D2231223E3C613A" +
      "7072737454785761727020707273743D22746578744E6F5368617065223E3C61" +
      "3A61764C73742F3E3C2F613A707273745478576172703E3C2F613A626F647950" +
      "723E3C613A6C73745374796C653E3C613A6C766C3170507220616C676E3D2272" +
      "223E3C613A64656652507220737A3D2231323030223E3C613A6C6174696E2074" +
      "797065666163653D2254696D6573204E657720526F6D616E2220706974636846" +
      "616D696C793D22312220636861727365743D2230222F3E3C2F613A6465665250" +
      "723E3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C613A703E" +
      "3C613A656E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A" +
      "703E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E7653" +
      "7050723E3C703A634E7650722069643D223831393622206E616D653D22526563" +
      "74616E676C652034222F3E3C703A634E76537050723E3C613A73704C6F636B73" +
      "206E6F4772703D223122206E6F526F743D223122206E6F4368616E6765417370" +
      "6563743D223122206E6F4368616E67654172726F7768656164733D223122206E" +
      "6F54657874456469743D2231222F3E3C2F703A634E76537050723E3C703A6E76" +
      "50723E3C703A706820747970653D22736C64496D6722206964783D2232222F3E" +
      "3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050722062774D6F" +
      "64653D226175746F223E3C613A7866726D3E3C613A6F666620783D2232383434" +
      "3830302220793D22353333343030222F3E3C613A6578742063783D2233343534" +
      "343030222063793D2232353930383030222F3E3C2F613A7866726D3E3C613A70" +
      "72737447656F6D20707273743D2272656374223E3C613A61764C73742F3E3C2F" +
      "613A7072737447656F6D3E3C613A6E6F46696C6C2F3E3C613A6C6E20773D2239" +
      "353235223E3C613A736F6C696446696C6C3E3C613A73726762436C722076616C" +
      "3D22303030303030222F3E3C2F613A736F6C696446696C6C3E3C613A6D697465" +
      "72206C696D3D22383030303030222F3E3C613A68656164456E642F3E3C613A74" +
      "61696C456E642F3E3C2F613A6C6E3E3C2F703A737050723E3C2F703A73703E3C" +
      "703A73703E3C703A6E76537050723E3C703A634E7650722069643D2239373238" +
      "3522206E616D653D2252656374616E676C652035222F3E3C703A634E76537050" +
      "723E3C613A73704C6F636B73206E6F4772703D223122206E6F4368616E676541" +
      "72726F7768656164733D2231222F3E3C2F703A634E76537050723E3C703A6E76" +
      "50723E3C703A706820747970653D22626F64792220737A3D2271756172746572" +
      "22206964783D2233222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C" +
      "703A737050722062774D6F64653D226175746F223E3C613A7866726D3E3C613A" +
      "6F666620783D22313231393230302220793D2233323736363030222F3E3C613A" +
      "6578742063783D2236373035363030222063793D2233303438303030222F3E3C" +
      "2F613A7866726D3E3C613A7072737447656F6D20707273743D2272656374223E" +
      "3C613A61764C73742F3E3C2F613A7072737447656F6D3E3C613A6E6F46696C6C" +
      "2F3E3C613A6C6E20773D2239353235223E3C613A6E6F46696C6C2F3E3C613A6D" +
      "69746572206C696D3D22383030303030222F3E3C613A68656164456E642F3E3C" +
      "613A7461696C456E642F3E3C2F613A6C6E3E3C613A6566666563744C73742F3E" +
      "3C2F703A737050723E3C703A7478426F64793E3C613A626F6479507220766572" +
      "743D22686F727A2220777261703D2273717561726522206C496E733D22393134" +
      "3430222074496E733D223435373230222072496E733D22393134343022206249" +
      "6E733D22343537323022206E756D436F6C3D22312220616E63686F723D227422" +
      "20616E63686F724374723D22302220636F6D7061744C6E5370633D2231223E3C" +
      "613A7072737454785761727020707273743D22746578744E6F5368617065223E" +
      "3C613A61764C73742F3E3C2F613A707273745478576172703E3C2F613A626F64" +
      "7950723E3C613A6C73745374796C652F3E3C613A703E3C613A705072206C766C" +
      "3D2230222F3E3C613A723E3C613A725072206C616E673D22656E2D5553222F3E" +
      "3C613A743E436C69636B20746F2065646974204D617374657220746578742073" +
      "74796C65733C2F613A743E3C2F613A723E3C2F613A703E3C613A703E3C613A70" +
      "5072206C766C3D2231222F3E3C613A723E3C613A725072206C616E673D22656E" +
      "2D5553222F3E3C613A743E5365636F6E64206C6576656C3C2F613A743E3C2F61" +
      "3A723E3C2F613A703E3C613A703E3C613A705072206C766C3D2232222F3E3C61" +
      "3A723E3C613A725072206C616E673D22656E2D5553222F3E3C613A743E546869" +
      "7264206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C613A703E3C" +
      "613A705072206C766C3D2233222F3E3C613A723E3C613A725072206C616E673D" +
      "22656E2D5553222F3E3C613A743E466F75727468206C6576656C3C2F613A743E" +
      "3C2F613A723E3C2F613A703E3C613A703E3C613A705072206C766C3D2234222F" +
      "3E3C613A723E3C613A725072206C616E673D22656E2D5553222F3E3C613A743E" +
      "4669667468206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C2F70" +
      "3A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C" +
      "703A634E7650722069643D22393732383622206E616D653D2252656374616E67" +
      "6C652036222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F47" +
      "72703D223122206E6F4368616E67654172726F7768656164733D2231222F3E3C" +
      "2F703A634E76537050723E3C703A6E7650723E3C703A706820747970653D2266" +
      "74722220737A3D227175617274657222206964783D2234222F3E3C2F703A6E76" +
      "50723E3C2F703A6E76537050723E3C703A737050722062774D6F64653D226175" +
      "746F223E3C613A7866726D3E3C613A6F666620783D22302220793D2236343737" +
      "303030222F3E3C613A6578742063783D2233393632343030222063793D223338" +
      "31303030222F3E3C2F613A7866726D3E3C613A7072737447656F6D2070727374" +
      "3D2272656374223E3C613A61764C73742F3E3C2F613A7072737447656F6D3E3C" +
      "613A6E6F46696C6C2F3E3C613A6C6E20773D2239353235223E3C613A6E6F4669" +
      "6C6C2F3E3C613A6D69746572206C696D3D22383030303030222F3E3C613A6865" +
      "6164456E642F3E3C613A7461696C456E642F3E3C2F613A6C6E3E3C613A656666" +
      "6563744C73742F3E3C2F703A737050723E3C703A7478426F64793E3C613A626F" +
      "6479507220766572743D22686F727A2220777261703D2273717561726522206C" +
      "496E733D223931343430222074496E733D223435373230222072496E733D2239" +
      "31343430222062496E733D22343537323022206E756D436F6C3D22312220616E" +
      "63686F723D22622220616E63686F724374723D22302220636F6D7061744C6E53" +
      "70633D2231223E3C613A7072737454785761727020707273743D22746578744E" +
      "6F5368617065223E3C613A61764C73742F3E3C2F613A70727374547857617270" +
      "3E3C2F613A626F647950723E3C613A6C73745374796C653E3C613A6C766C3170" +
      "50723E3C613A64656652507220737A3D2231323030223E3C613A6C6174696E20" +
      "74797065666163653D2254696D6573204E657720526F6D616E22207069746368" +
      "46616D696C793D22312220636861727365743D2230222F3E3C2F613A64656652" +
      "50723E3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C613A70" +
      "3E3C613A656E6450617261525072206C616E673D22656E2D5553222F3E3C2F61" +
      "3A703E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E76" +
      "537050723E3C703A634E7650722069643D22393732383722206E616D653D2252" +
      "656374616E676C652037222F3E3C703A634E76537050723E3C613A73704C6F63" +
      "6B73206E6F4772703D223122206E6F4368616E67654172726F7768656164733D" +
      "2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A70682074" +
      "7970653D22736C644E756D2220737A3D227175617274657222206964783D2235" +
      "222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050722062" +
      "774D6F64653D226175746F223E3C613A7866726D3E3C613A6F666620783D2235" +
      "3138313630302220793D2236343737303030222F3E3C613A6578742063783D22" +
      "33393632343030222063793D22333831303030222F3E3C2F613A7866726D3E3C" +
      "613A7072737447656F6D20707273743D2272656374223E3C613A61764C73742F" +
      "3E3C2F613A7072737447656F6D3E3C613A6E6F46696C6C2F3E3C613A6C6E2077" +
      "3D2239353235223E3C613A6E6F46696C6C2F3E3C613A6D69746572206C696D3D" +
      "22383030303030222F3E3C613A68656164456E642F3E3C613A7461696C456E64" +
      "2F3E3C2F613A6C6E3E3C613A6566666563744C73742F3E3C2F703A737050723E" +
      "3C703A7478426F64793E3C613A626F6479507220766572743D22686F727A2220" +
      "777261703D2273717561726522206C496E733D223931343430222074496E733D" +
      "223435373230222072496E733D223931343430222062496E733D223435373230" +
      "22206E756D436F6C3D22312220616E63686F723D22622220616E63686F724374" +
      "723D22302220636F6D7061744C6E5370633D2231223E3C613A70727374547857" +
      "61727020707273743D22746578744E6F5368617065223E3C613A61764C73742F" +
      "3E3C2F613A707273745478576172703E3C2F613A626F647950723E3C613A6C73" +
      "745374796C653E3C613A6C766C3170507220616C676E3D2272223E3C613A6465" +
      "6652507220737A3D2231323030223E3C613A6C6174696E207479706566616365" +
      "3D2254696D6573204E657720526F6D616E2220706974636846616D696C793D22" +
      "312220636861727365743D2230222F3E3C2F613A6465665250723E3C2F613A6C" +
      "766C317050723E3C2F613A6C73745374796C653E3C613A703E3C613A666C6420" +
      "69643D227B44454338443436352D414632382D354534302D414244322D343431" +
      "4633423832313246307D2220747970653D22736C6964656E756D223E3C613A72" +
      "5072206C616E673D22656E2D5553222F3E3C613A7050722F3E3C613A743EE280" +
      "B923E280BA3C2F613A743E3C2F613A666C643E3C613A656E6450617261525072" +
      "206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E" +
      "3C2F703A73703E3C2F703A7370547265653E3C2F703A63536C643E3C703A636C" +
      "724D6170206267313D226C743122207478313D22646B3122206267323D226C74" +
      "3222207478323D22646B322220616363656E74313D22616363656E7431222061" +
      "6363656E74323D22616363656E74322220616363656E74333D22616363656E74" +
      "332220616363656E74343D22616363656E74342220616363656E74353D226163" +
      "63656E74352220616363656E74363D22616363656E74362220686C696E6B3D22" +
      "686C696E6B2220666F6C486C696E6B3D22666F6C486C696E6B222F3E3C703A6E" +
      "6F7465735374796C653E3C613A6C766C3170507220616C676E3D226C22207274" +
      "6C3D2230222065614C6E42726B3D22302220666F6E74416C676E3D2262617365" +
      "222068616E67696E6750756E63743D2230223E3C613A7370634265663E3C613A" +
      "7370635063742076616C3D223330303030222F3E3C2F613A7370634265663E3C" +
      "613A7370634166743E3C613A7370635063742076616C3D2230222F3E3C2F613A" +
      "7370634166743E3C613A64656652507220737A3D223132303022206B65726E3D" +
      "2231323030223E3C613A736F6C696446696C6C3E3C613A736368656D65436C72" +
      "2076616C3D22747831222F3E3C2F613A736F6C696446696C6C3E3C613A6C6174" +
      "696E2074797065666163653D2254696D6573204E657720526F6D616E22207069" +
      "74636846616D696C793D22312220636861727365743D2230222F3E3C613A6561" +
      "2074797065666163653D222B6D6E2D6561222F3E3C613A637320747970656661" +
      "63653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F613A6C766C31" +
      "7050723E3C613A6C766C32705072206D61724C3D223435373230302220616C67" +
      "6E3D226C222072746C3D2230222065614C6E42726B3D22302220666F6E74416C" +
      "676E3D2262617365222068616E67696E6750756E63743D2230223E3C613A7370" +
      "634265663E3C613A7370635063742076616C3D223330303030222F3E3C2F613A" +
      "7370634265663E3C613A7370634166743E3C613A7370635063742076616C3D22" +
      "30222F3E3C2F613A7370634166743E3C613A64656652507220737A3D22313230" +
      "3022206B65726E3D2231323030223E3C613A736F6C696446696C6C3E3C613A73" +
      "6368656D65436C722076616C3D22747831222F3E3C2F613A736F6C696446696C" +
      "6C3E3C613A6C6174696E2074797065666163653D2254696D6573204E65772052" +
      "6F6D616E2220706974636846616D696C793D22312220636861727365743D2230" +
      "222F3E3C613A65612074797065666163653D22EFBCADEFBCB320EFBCB0E382B4" +
      "E382B7E38383E382AF2220706974636846616D696C793D223122206368617273" +
      "65743D222D313238222F3E3C613A63732074797065666163653D222B6D6E2D63" +
      "73222F3E3C2F613A6465665250723E3C2F613A6C766C327050723E3C613A6C76" +
      "6C33705072206D61724C3D223931343430302220616C676E3D226C222072746C" +
      "3D2230222065614C6E42726B3D22302220666F6E74416C676E3D226261736522" +
      "2068616E67696E6750756E63743D2230223E3C613A7370634265663E3C613A73" +
      "70635063742076616C3D223330303030222F3E3C2F613A7370634265663E3C61" +
      "3A7370634166743E3C613A7370635063742076616C3D2230222F3E3C2F613A73" +
      "70634166743E3C613A64656652507220737A3D223132303022206B65726E3D22" +
      "31323030223E3C613A736F6C696446696C6C3E3C613A736368656D65436C7220" +
      "76616C3D22747831222F3E3C2F613A736F6C696446696C6C3E3C613A6C617469" +
      "6E2074797065666163653D2254696D6573204E657720526F6D616E2220706974" +
      "636846616D696C793D22312220636861727365743D2230222F3E3C613A656120" +
      "74797065666163653D22EFBCADEFBCB320EFBCB0E382B4E382B7E38383E382AF" +
      "2220706974636846616D696C793D22312220636861727365743D222D31323822" +
      "2F3E3C613A63732074797065666163653D222B6D6E2D6373222F3E3C2F613A64" +
      "65665250723E3C2F613A6C766C337050723E3C613A6C766C34705072206D6172" +
      "4C3D22313337313630302220616C676E3D226C222072746C3D2230222065614C" +
      "6E42726B3D22302220666F6E74416C676E3D2262617365222068616E67696E67" +
      "50756E63743D2230223E3C613A7370634265663E3C613A737063506374207661" +
      "6C3D223330303030222F3E3C2F613A7370634265663E3C613A7370634166743E" +
      "3C613A7370635063742076616C3D2230222F3E3C2F613A7370634166743E3C61" +
      "3A64656652507220737A3D223132303022206B65726E3D2231323030223E3C61" +
      "3A736F6C696446696C6C3E3C613A736368656D65436C722076616C3D22747831" +
      "222F3E3C2F613A736F6C696446696C6C3E3C613A6C6174696E20747970656661" +
      "63653D2254696D6573204E657720526F6D616E2220706974636846616D696C79" +
      "3D22312220636861727365743D2230222F3E3C613A6561207479706566616365" +
      "3D22EFBCADEFBCB320EFBCB0E382B4E382B7E38383E382AF2220706974636846" +
      "616D696C793D22312220636861727365743D222D313238222F3E3C613A637320" +
      "74797065666163653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F" +
      "613A6C766C347050723E3C613A6C766C35705072206D61724C3D223138323838" +
      "30302220616C676E3D226C222072746C3D2230222065614C6E42726B3D223022" +
      "20666F6E74416C676E3D2262617365222068616E67696E6750756E63743D2230" +
      "223E3C613A7370634265663E3C613A7370635063742076616C3D223330303030" +
      "222F3E3C2F613A7370634265663E3C613A7370634166743E3C613A7370635063" +
      "742076616C3D2230222F3E3C2F613A7370634166743E3C613A64656652507220" +
      "737A3D223132303022206B65726E3D2231323030223E3C613A736F6C69644669" +
      "6C6C3E3C613A736368656D65436C722076616C3D22747831222F3E3C2F613A73" +
      "6F6C696446696C6C3E3C613A6C6174696E2074797065666163653D2254696D65" +
      "73204E657720526F6D616E2220706974636846616D696C793D22312220636861" +
      "727365743D2230222F3E3C613A65612074797065666163653D22EFBCADEFBCB3" +
      "20EFBCB0E382B4E382B7E38383E382AF2220706974636846616D696C793D2231" +
      "2220636861727365743D222D313238222F3E3C613A6373207479706566616365" +
      "3D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F613A6C766C357050" +
      "723E3C613A6C766C36705072206D61724C3D22323238363030302220616C676E" +
      "3D226C2220646566546162537A3D22393134343030222072746C3D2230222065" +
      "614C6E42726B3D223122206C6174696E4C6E42726B3D2230222068616E67696E" +
      "6750756E63743D2231223E3C613A64656652507220737A3D223132303022206B" +
      "65726E3D2231323030223E3C613A736F6C696446696C6C3E3C613A736368656D" +
      "65436C722076616C3D22747831222F3E3C2F613A736F6C696446696C6C3E3C61" +
      "3A6C6174696E2074797065666163653D222B6D6E2D6C74222F3E3C613A656120" +
      "74797065666163653D222B6D6E2D6561222F3E3C613A63732074797065666163" +
      "653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F613A6C766C3670" +
      "50723E3C613A6C766C37705072206D61724C3D22323734333230302220616C67" +
      "6E3D226C2220646566546162537A3D22393134343030222072746C3D22302220" +
      "65614C6E42726B3D223122206C6174696E4C6E42726B3D2230222068616E6769" +
      "6E6750756E63743D2231223E3C613A64656652507220737A3D22313230302220" +
      "6B65726E3D2231323030223E3C613A736F6C696446696C6C3E3C613A73636865" +
      "6D65436C722076616C3D22747831222F3E3C2F613A736F6C696446696C6C3E3C" +
      "613A6C6174696E2074797065666163653D222B6D6E2D6C74222F3E3C613A6561" +
      "2074797065666163653D222B6D6E2D6561222F3E3C613A637320747970656661" +
      "63653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F613A6C766C37" +
      "7050723E3C613A6C766C38705072206D61724C3D22333230303430302220616C" +
      "676E3D226C2220646566546162537A3D22393134343030222072746C3D223022" +
      "2065614C6E42726B3D223122206C6174696E4C6E42726B3D2230222068616E67" +
      "696E6750756E63743D2231223E3C613A64656652507220737A3D223132303022" +
      "206B65726E3D2231323030223E3C613A736F6C696446696C6C3E3C613A736368" +
      "656D65436C722076616C3D22747831222F3E3C2F613A736F6C696446696C6C3E" +
      "3C613A6C6174696E2074797065666163653D222B6D6E2D6C74222F3E3C613A65" +
      "612074797065666163653D222B6D6E2D6561222F3E3C613A6373207479706566" +
      "6163653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F613A6C766C" +
      "387050723E3C613A6C766C39705072206D61724C3D2233363537363030222061" +
      "6C676E3D226C2220646566546162537A3D22393134343030222072746C3D2230" +
      "222065614C6E42726B3D223122206C6174696E4C6E42726B3D2230222068616E" +
      "67696E6750756E63743D2231223E3C613A64656652507220737A3D2231323030" +
      "22206B65726E3D2231323030223E3C613A736F6C696446696C6C3E3C613A7363" +
      "68656D65436C722076616C3D22747831222F3E3C2F613A736F6C696446696C6C" +
      "3E3C613A6C6174696E2074797065666163653D222B6D6E2D6C74222F3E3C613A" +
      "65612074797065666163653D222B6D6E2D6561222F3E3C613A63732074797065" +
      "666163653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F613A6C76" +
      "6C397050723E3C2F703A6E6F7465735374796C653E3C2F703A6E6F7465734D61" +
      "737465723E";

   public static final String PRES_PROPS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A70726573" +
      "656E746174696F6E507220786D6C6E733A613D22687474703A2F2F736368656D" +
      "61732E6F70656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F" +
      "323030362F6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D" +
      "61732E6F70656E786D6C666F726D6174732E6F72672F6F6666696365446F6375" +
      "6D656E742F323030362F72656C6174696F6E73686970732220786D6C6E733A6D" +
      "633D22687474703A2F2F736368656D61732E6F70656E786D6C666F726D617473" +
      "2E6F72672F6D61726B75702D636F6D7061746962696C6974792F323030362220" +
      "786D6C6E733A6D763D2275726E3A736368656D61732D6D6963726F736F66742D" +
      "636F6D3A6D61633A766D6C22206D633A49676E6F7261626C653D226D7622206D" +
      "633A5072657365727665417474726962757465733D226D763A2A2220786D6C6E" +
      "733A703D22687474703A2F2F736368656D61732E6F70656E786D6C666F726D61" +
      "74732E6F72672F70726573656E746174696F6E6D6C2F323030362F6D61696E22" +
      "3E3C703A636C724D72753E3C613A73726762436C722076616C3D223030303030" +
      "30222F3E3C613A73726762436C722076616C3D22303030304646222F3E3C613A" +
      "73726762436C722076616C3D22393939393939222F3E3C613A73726762436C72" +
      "2076616C3D22303036364343222F3E3C613A73726762436C722076616C3D2246" +
      "4633333030222F3E3C613A73726762436C722076616C3D22393939393333222F" +
      "3E3C613A73726762436C722076616C3D22333346464646222F3E3C613A737267" +
      "62436C722076616C3D22393933333939222F3E3C613A73726762436C72207661" +
      "6C3D22363636363636222F3E3C2F703A636C724D72753E3C2F703A7072657365" +
      "6E746174696F6E50723E";

   public static final String SLIDE_LAYOUT_1_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654D6173746572222054617267" +
      "65743D222E2E2F736C6964654D6173746572732F736C6964654D617374657231" +
      "2E786D6C222F3E3C2F52656C6174696F6E73686970733E";

   public static final String SLIDE_LAYOUT_10_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654D6173746572222054617267" +
      "65743D222E2E2F736C6964654D6173746572732F736C6964654D617374657231" +
      "2E786D6C222F3E3C2F52656C6174696F6E73686970733E";

   public static final String SLIDE_LAYOUT_11_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654D6173746572222054617267" +
      "65743D222E2E2F736C6964654D6173746572732F736C6964654D617374657231" +
      "2E786D6C222F3E3C2F52656C6174696F6E73686970733E";

   public static final String SLIDE_LAYOUT_2_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654D6173746572222054617267" +
      "65743D222E2E2F736C6964654D6173746572732F736C6964654D617374657231" +
      "2E786D6C222F3E3C2F52656C6174696F6E73686970733E";

   public static final String SLIDE_LAYOUT_3_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654D6173746572222054617267" +
      "65743D222E2E2F736C6964654D6173746572732F736C6964654D617374657231" +
      "2E786D6C222F3E3C2F52656C6174696F6E73686970733E";

   public static final String SLIDE_LAYOUT_4_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654D6173746572222054617267" +
      "65743D222E2E2F736C6964654D6173746572732F736C6964654D617374657231" +
      "2E786D6C222F3E3C2F52656C6174696F6E73686970733E";

   public static final String SLIDE_LAYOUT_5_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654D6173746572222054617267" +
      "65743D222E2E2F736C6964654D6173746572732F736C6964654D617374657231" +
      "2E786D6C222F3E3C2F52656C6174696F6E73686970733E";

   public static final String SLIDE_LAYOUT_6_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654D6173746572222054617267" +
      "65743D222E2E2F736C6964654D6173746572732F736C6964654D617374657231" +
      "2E786D6C222F3E3C2F52656C6174696F6E73686970733E";

   public static final String SLIDE_LAYOUT_7_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654D6173746572222054617267" +
      "65743D222E2E2F736C6964654D6173746572732F736C6964654D617374657231" +
      "2E786D6C222F3E3C2F52656C6174696F6E73686970733E";

   public static final String SLIDE_LAYOUT_8_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654D6173746572222054617267" +
      "65743D222E2E2F736C6964654D6173746572732F736C6964654D617374657231" +
      "2E786D6C222F3E3C2F52656C6174696F6E73686970733E";

   public static final String SLIDE_LAYOUT_9_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "4964312220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654D6173746572222054617267" +
      "65743D222E2E2F736C6964654D6173746572732F736C6964654D617374657231" +
      "2E786D6C222F3E3C2F52656C6174696F6E73686970733E";

   public static final String SLIDE_LAYOUT_1 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644C" +
      "61796F757420786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E222074797065" +
      "3D227469746C65222070726573657276653D2231223E3C703A63536C64206E61" +
      "6D653D225469746C6520536C696465223E3C703A7370547265653E3C703A6E76" +
      "477270537050723E3C703A634E7650722069643D223122206E616D653D22222F" +
      "3E3C703A634E76477270537050722F3E3C703A6E7650722F3E3C2F703A6E7647" +
      "7270537050723E3C703A677270537050723E3C613A7866726D3E3C613A6F6666" +
      "20783D22302220793D2230222F3E3C613A6578742063783D2230222063793D22" +
      "30222F3E3C613A63684F666620783D22302220793D2230222F3E3C613A636845" +
      "78742063783D2230222063793D2230222F3E3C2F613A7866726D3E3C2F703A67" +
      "7270537050723E3C703A73703E3C703A6E76537050723E3C703A634E76507220" +
      "69643D223222206E616D653D225469746C652031222F3E3C703A634E76537050" +
      "723E3C613A73704C6F636B73206E6F4772703D2231222F3E3C2F703A634E7653" +
      "7050723E3C703A6E7650723E3C703A706820747970653D226374725469746C65" +
      "222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C" +
      "613A7866726D3E3C613A6F666620783D223638353830302220793D2232313330" +
      "343235222F3E3C613A6578742063783D2237373732343030222063793D223134" +
      "3730303235222F3E3C2F613A7866726D3E3C2F703A737050723E3C703A747842" +
      "6F64793E3C613A626F647950722F3E3C613A6C73745374796C652F3E3C613A70" +
      "3E3C613A723E3C613A725072206C616E673D22656E2D55532220736D74436C65" +
      "616E3D2230222F3E3C613A743E436C69636B20746F2065646974204D61737465" +
      "72207469746C65207374796C653C2F613A743E3C2F613A723E3C613A656E6450" +
      "617261525072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A74" +
      "78426F64793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A" +
      "634E7650722069643D223322206E616D653D225375627469746C652032222F3E" +
      "3C703A634E76537050723E3C613A73704C6F636B73206E6F4772703D2231222F" +
      "3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820747970653D" +
      "227375625469746C6522206964783D2231222F3E3C2F703A6E7650723E3C2F70" +
      "3A6E76537050723E3C703A737050723E3C613A7866726D3E3C613A6F66662078" +
      "3D22313337313630302220793D2233383836323030222F3E3C613A6578742063" +
      "783D2236343030383030222063793D2231373532363030222F3E3C2F613A7866" +
      "726D3E3C2F703A737050723E3C703A7478426F64793E3C613A626F647950722F" +
      "3E3C613A6C73745374796C653E3C613A6C766C31705072206D61724C3D223022" +
      "20696E64656E743D22302220616C676E3D22637472223E3C613A62754E6F6E65" +
      "2F3E3C613A6465665250722F3E3C2F613A6C766C317050723E3C613A6C766C32" +
      "705072206D61724C3D223435373230302220696E64656E743D22302220616C67" +
      "6E3D22637472223E3C613A62754E6F6E652F3E3C613A6465665250722F3E3C2F" +
      "613A6C766C327050723E3C613A6C766C33705072206D61724C3D223931343430" +
      "302220696E64656E743D22302220616C676E3D22637472223E3C613A62754E6F" +
      "6E652F3E3C613A6465665250722F3E3C2F613A6C766C337050723E3C613A6C76" +
      "6C34705072206D61724C3D22313337313630302220696E64656E743D22302220" +
      "616C676E3D22637472223E3C613A62754E6F6E652F3E3C613A6465665250722F" +
      "3E3C2F613A6C766C347050723E3C613A6C766C35705072206D61724C3D223138" +
      "32383830302220696E64656E743D22302220616C676E3D22637472223E3C613A" +
      "62754E6F6E652F3E3C613A6465665250722F3E3C2F613A6C766C357050723E3C" +
      "613A6C766C36705072206D61724C3D22323238363030302220696E64656E743D" +
      "22302220616C676E3D22637472223E3C613A62754E6F6E652F3E3C613A646566" +
      "5250722F3E3C2F613A6C766C367050723E3C613A6C766C37705072206D61724C" +
      "3D22323734333230302220696E64656E743D22302220616C676E3D2263747222" +
      "3E3C613A62754E6F6E652F3E3C613A6465665250722F3E3C2F613A6C766C3770" +
      "50723E3C613A6C766C38705072206D61724C3D22333230303430302220696E64" +
      "656E743D22302220616C676E3D22637472223E3C613A62754E6F6E652F3E3C61" +
      "3A6465665250722F3E3C2F613A6C766C387050723E3C613A6C766C3970507220" +
      "6D61724C3D22333635373630302220696E64656E743D22302220616C676E3D22" +
      "637472223E3C613A62754E6F6E652F3E3C613A6465665250722F3E3C2F613A6C" +
      "766C397050723E3C2F613A6C73745374796C653E3C613A703E3C613A723E3C61" +
      "3A725072206C616E673D22656E2D55532220736D74436C65616E3D2230222F3E" +
      "3C613A743E436C69636B20746F2065646974204D617374657220737562746974" +
      "6C65207374796C653C2F613A743E3C2F613A723E3C613A656E64506172615250" +
      "72206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F6479" +
      "3E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A634E765072" +
      "2069643D223422206E616D653D2252656374616E676C652034222F3E3C703A63" +
      "4E76537050723E3C613A73704C6F636B73206E6F4772703D223122206E6F4368" +
      "616E67654172726F7768656164733D2231222F3E3C2F703A634E76537050723E" +
      "3C703A6E7650723E3C703A706820747970653D2264742220737A3D2268616C66" +
      "22206964783D223130222F3E3C2F703A6E7650723E3C2F703A6E76537050723E" +
      "3C703A737050723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F64" +
      "793E3C613A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C31" +
      "7050723E3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C" +
      "73745374796C653E3C613A703E3C613A656E6450617261525072206C616E673D" +
      "22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A7370" +
      "3E3C703A73703E3C703A6E76537050723E3C703A634E7650722069643D223522" +
      "206E616D653D2252656374616E676C652035222F3E3C703A634E76537050723E" +
      "3C613A73704C6F636B73206E6F4772703D223122206E6F4368616E6765417272" +
      "6F7768656164733D2231222F3E3C2F703A634E76537050723E3C703A6E765072" +
      "3E3C703A706820747970653D226674722220737A3D2271756172746572222069" +
      "64783D223131222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A" +
      "737050723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C" +
      "613A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C31705072" +
      "3E3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C737453" +
      "74796C653E3C613A703E3C613A656E6450617261525072206C616E673D22656E" +
      "2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C70" +
      "3A73703E3C703A6E76537050723E3C703A634E7650722069643D223622206E61" +
      "6D653D2252656374616E676C652036222F3E3C703A634E76537050723E3C613A" +
      "73704C6F636B73206E6F4772703D223122206E6F4368616E67654172726F7768" +
      "656164733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C70" +
      "3A706820747970653D22736C644E756D2220737A3D2271756172746572222069" +
      "64783D223132222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A" +
      "737050723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C" +
      "613A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C31705072" +
      "3E3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C737453" +
      "74796C653E3C613A703E3C613A666C642069643D227B44444337394532432D33" +
      "4332422D313534412D424246332D4244413839354236334443337D2220747970" +
      "653D22736C6964656E756D223E3C613A725072206C616E673D22656E2D555322" +
      "2F3E3C613A7050722F3E3C613A743EE280B923E280BA3C2F613A743E3C2F613A" +
      "666C643E3C613A656E6450617261525072206C616E673D22656E2D5553222F3E" +
      "3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C2F703A73705472" +
      "65653E3C2F703A63536C643E3C703A636C724D61704F76723E3C613A6D617374" +
      "6572436C724D617070696E672F3E3C2F703A636C724D61704F76723E3C2F703A" +
      "736C644C61796F75743E";

   public static final String SLIDE_LAYOUT_10 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644C" +
      "61796F757420786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E222074797065" +
      "3D22766572745478222070726573657276653D2231223E3C703A63536C64206E" +
      "616D653D225469746C6520616E6420566572746963616C2054657874223E3C70" +
      "3A7370547265653E3C703A6E76477270537050723E3C703A634E765072206964" +
      "3D223122206E616D653D22222F3E3C703A634E76477270537050722F3E3C703A" +
      "6E7650722F3E3C2F703A6E76477270537050723E3C703A677270537050723E3C" +
      "613A7866726D3E3C613A6F666620783D22302220793D2230222F3E3C613A6578" +
      "742063783D2230222063793D2230222F3E3C613A63684F666620783D22302220" +
      "793D2230222F3E3C613A63684578742063783D2230222063793D2230222F3E3C" +
      "2F613A7866726D3E3C2F703A677270537050723E3C703A73703E3C703A6E7653" +
      "7050723E3C703A634E7650722069643D223222206E616D653D225469746C6520" +
      "31222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F4772703D" +
      "2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A70682074" +
      "7970653D227469746C65222F3E3C2F703A6E7650723E3C2F703A6E7653705072" +
      "3E3C703A737050722F3E3C703A7478426F64793E3C613A626F647950722F3E3C" +
      "613A6C73745374796C652F3E3C613A703E3C613A723E3C613A725072206C616E" +
      "673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E436C69" +
      "636B20746F2065646974204D6173746572207469746C65207374796C653C2F61" +
      "3A743E3C2F613A723E3C613A656E6450617261525072206C616E673D22656E2D" +
      "5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A" +
      "73703E3C703A6E76537050723E3C703A634E7650722069643D223322206E616D" +
      "653D22566572746963616C205465787420506C616365686F6C6465722032222F" +
      "3E3C703A634E76537050723E3C613A73704C6F636B73206E6F4772703D223122" +
      "2F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A70682074797065" +
      "3D22626F647922206F7269656E743D227665727422206964783D2231222F3E3C" +
      "2F703A6E7650723E3C2F703A6E76537050723E3C703A737050722F3E3C703A74" +
      "78426F64793E3C613A626F6479507220766572743D22656156657274222F3E3C" +
      "613A6C73745374796C652F3E3C613A703E3C613A705072206C766C3D2230222F" +
      "3E3C613A723E3C613A725072206C616E673D22656E2D55532220736D74436C65" +
      "616E3D2230222F3E3C613A743E436C69636B20746F2065646974204D61737465" +
      "722074657874207374796C65733C2F613A743E3C2F613A723E3C2F613A703E3C" +
      "613A703E3C613A705072206C766C3D2231222F3E3C613A723E3C613A72507220" +
      "6C616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E" +
      "5365636F6E64206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C61" +
      "3A703E3C613A705072206C766C3D2232222F3E3C613A723E3C613A725072206C" +
      "616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E54" +
      "68697264206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C613A70" +
      "3E3C613A705072206C766C3D2233222F3E3C613A723E3C613A725072206C616E" +
      "673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E466F75" +
      "727468206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C613A703E" +
      "3C613A705072206C766C3D2234222F3E3C613A723E3C613A725072206C616E67" +
      "3D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E46696674" +
      "68206C6576656C3C2F613A743E3C2F613A723E3C613A656E6450617261525072" +
      "206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E" +
      "3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A634E76507220" +
      "69643D223422206E616D653D2252656374616E676C652034222F3E3C703A634E" +
      "76537050723E3C613A73704C6F636B73206E6F4772703D223122206E6F436861" +
      "6E67654172726F7768656164733D2231222F3E3C2F703A634E76537050723E3C" +
      "703A6E7650723E3C703A706820747970653D2264742220737A3D2268616C6622" +
      "206964783D223130222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C" +
      "703A737050723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F6479" +
      "3E3C613A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C3170" +
      "50723E3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C73" +
      "745374796C653E3C613A703E3C613A656E6450617261525072206C616E673D22" +
      "656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E" +
      "3C703A73703E3C703A6E76537050723E3C703A634E7650722069643D22352220" +
      "6E616D653D2252656374616E676C652035222F3E3C703A634E76537050723E3C" +
      "613A73704C6F636B73206E6F4772703D223122206E6F4368616E67654172726F" +
      "7768656164733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E" +
      "3C703A706820747970653D226674722220737A3D227175617274657222206964" +
      "783D223131222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A73" +
      "7050723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C61" +
      "3A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C317050723E" +
      "3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C73745374" +
      "796C653E3C613A703E3C613A656E6450617261525072206C616E673D22656E2D" +
      "5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A" +
      "73703E3C703A6E76537050723E3C703A634E7650722069643D223622206E616D" +
      "653D2252656374616E676C652036222F3E3C703A634E76537050723E3C613A73" +
      "704C6F636B73206E6F4772703D223122206E6F4368616E67654172726F776865" +
      "6164733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A" +
      "706820747970653D22736C644E756D2220737A3D227175617274657222206964" +
      "783D223132222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A73" +
      "7050723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C61" +
      "3A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C317050723E" +
      "3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C73745374" +
      "796C653E3C613A703E3C613A666C642069643D227B43373935334237352D3945" +
      "44412D354334382D423045342D4238364138384334313736357D222074797065" +
      "3D22736C6964656E756D223E3C613A725072206C616E673D22656E2D5553222F" +
      "3E3C613A7050722F3E3C613A743EE280B923E280BA3C2F613A743E3C2F613A66" +
      "6C643E3C613A656E6450617261525072206C616E673D22656E2D5553222F3E3C" +
      "2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C2F703A7370547265" +
      "653E3C2F703A63536C643E3C703A636C724D61704F76723E3C613A6D61737465" +
      "72436C724D617070696E672F3E3C2F703A636C724D61704F76723E3C2F703A73" +
      "6C644C61796F75743E";

   public static final String SLIDE_LAYOUT_11 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644C" +
      "61796F757420786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E222074797065" +
      "3D22766572745469746C65416E645478222070726573657276653D2231223E3C" +
      "703A63536C64206E616D653D22566572746963616C205469746C6520616E6420" +
      "54657874223E3C703A7370547265653E3C703A6E76477270537050723E3C703A" +
      "634E7650722069643D223122206E616D653D22222F3E3C703A634E7647727053" +
      "7050722F3E3C703A6E7650722F3E3C2F703A6E76477270537050723E3C703A67" +
      "7270537050723E3C613A7866726D3E3C613A6F666620783D22302220793D2230" +
      "222F3E3C613A6578742063783D2230222063793D2230222F3E3C613A63684F66" +
      "6620783D22302220793D2230222F3E3C613A63684578742063783D2230222063" +
      "793D2230222F3E3C2F613A7866726D3E3C2F703A677270537050723E3C703A73" +
      "703E3C703A6E76537050723E3C703A634E7650722069643D223222206E616D65" +
      "3D22566572746963616C205469746C652031222F3E3C703A634E76537050723E" +
      "3C613A73704C6F636B73206E6F4772703D2231222F3E3C2F703A634E76537050" +
      "723E3C703A6E7650723E3C703A706820747970653D227469746C6522206F7269" +
      "656E743D2276657274222F3E3C2F703A6E7650723E3C2F703A6E76537050723E" +
      "3C703A737050723E3C613A7866726D3E3C613A6F666620783D22363531353130" +
      "302220793D22363039363030222F3E3C613A6578742063783D22313934333130" +
      "30222063793D2235343836343030222F3E3C2F613A7866726D3E3C2F703A7370" +
      "50723E3C703A7478426F64793E3C613A626F6479507220766572743D22656156" +
      "657274222F3E3C613A6C73745374796C652F3E3C613A703E3C613A723E3C613A" +
      "725072206C616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C" +
      "613A743E436C69636B20746F2065646974204D6173746572207469746C652073" +
      "74796C653C2F613A743E3C2F613A723E3C613A656E6450617261525072206C61" +
      "6E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F70" +
      "3A73703E3C703A73703E3C703A6E76537050723E3C703A634E7650722069643D" +
      "223322206E616D653D22566572746963616C205465787420506C616365686F6C" +
      "6465722032222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F" +
      "4772703D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A" +
      "706820747970653D22626F647922206F7269656E743D22766572742220696478" +
      "3D2231222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050" +
      "723E3C613A7866726D3E3C613A6F666620783D223638353830302220793D2236" +
      "3039363030222F3E3C613A6578742063783D2235363736393030222063793D22" +
      "35343836343030222F3E3C2F613A7866726D3E3C2F703A737050723E3C703A74" +
      "78426F64793E3C613A626F6479507220766572743D22656156657274222F3E3C" +
      "613A6C73745374796C652F3E3C613A703E3C613A705072206C766C3D2230222F" +
      "3E3C613A723E3C613A725072206C616E673D22656E2D55532220736D74436C65" +
      "616E3D2230222F3E3C613A743E436C69636B20746F2065646974204D61737465" +
      "722074657874207374796C65733C2F613A743E3C2F613A723E3C2F613A703E3C" +
      "613A703E3C613A705072206C766C3D2231222F3E3C613A723E3C613A72507220" +
      "6C616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E" +
      "5365636F6E64206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C61" +
      "3A703E3C613A705072206C766C3D2232222F3E3C613A723E3C613A725072206C" +
      "616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E54" +
      "68697264206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C613A70" +
      "3E3C613A705072206C766C3D2233222F3E3C613A723E3C613A725072206C616E" +
      "673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E466F75" +
      "727468206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C613A703E" +
      "3C613A705072206C766C3D2234222F3E3C613A723E3C613A725072206C616E67" +
      "3D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E46696674" +
      "68206C6576656C3C2F613A743E3C2F613A723E3C613A656E6450617261525072" +
      "206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E" +
      "3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A634E76507220" +
      "69643D223422206E616D653D2252656374616E676C652034222F3E3C703A634E" +
      "76537050723E3C613A73704C6F636B73206E6F4772703D223122206E6F436861" +
      "6E67654172726F7768656164733D2231222F3E3C2F703A634E76537050723E3C" +
      "703A6E7650723E3C703A706820747970653D2264742220737A3D2268616C6622" +
      "206964783D223130222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C" +
      "703A737050723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F6479" +
      "3E3C613A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C3170" +
      "50723E3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C73" +
      "745374796C653E3C613A703E3C613A656E6450617261525072206C616E673D22" +
      "656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E" +
      "3C703A73703E3C703A6E76537050723E3C703A634E7650722069643D22352220" +
      "6E616D653D2252656374616E676C652035222F3E3C703A634E76537050723E3C" +
      "613A73704C6F636B73206E6F4772703D223122206E6F4368616E67654172726F" +
      "7768656164733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E" +
      "3C703A706820747970653D226674722220737A3D227175617274657222206964" +
      "783D223131222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A73" +
      "7050723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C61" +
      "3A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C317050723E" +
      "3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C73745374" +
      "796C653E3C613A703E3C613A656E6450617261525072206C616E673D22656E2D" +
      "5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A" +
      "73703E3C703A6E76537050723E3C703A634E7650722069643D223622206E616D" +
      "653D2252656374616E676C652036222F3E3C703A634E76537050723E3C613A73" +
      "704C6F636B73206E6F4772703D223122206E6F4368616E67654172726F776865" +
      "6164733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A" +
      "706820747970653D22736C644E756D2220737A3D227175617274657222206964" +
      "783D223132222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A73" +
      "7050723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C61" +
      "3A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C317050723E" +
      "3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C73745374" +
      "796C653E3C613A703E3C613A666C642069643D227B30343634394335372D4243" +
      "35302D313634302D413736412D3139443836344135434533457D222074797065" +
      "3D22736C6964656E756D223E3C613A725072206C616E673D22656E2D5553222F" +
      "3E3C613A7050722F3E3C613A743EE280B923E280BA3C2F613A743E3C2F613A66" +
      "6C643E3C613A656E6450617261525072206C616E673D22656E2D5553222F3E3C" +
      "2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C2F703A7370547265" +
      "653E3C2F703A63536C643E3C703A636C724D61704F76723E3C613A6D61737465" +
      "72436C724D617070696E672F3E3C2F703A636C724D61704F76723E3C2F703A73" +
      "6C644C61796F75743E";

   public static final String SLIDE_LAYOUT_2 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644C" +
      "61796F757420786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E222074797065" +
      "3D226F626A222070726573657276653D2231223E3C703A63536C64206E616D65" +
      "3D225469746C6520616E6420436F6E74656E74223E3C703A7370547265653E3C" +
      "703A6E76477270537050723E3C703A634E7650722069643D223122206E616D65" +
      "3D22222F3E3C703A634E76477270537050722F3E3C703A6E7650722F3E3C2F70" +
      "3A6E76477270537050723E3C703A677270537050723E3C613A7866726D3E3C61" +
      "3A6F666620783D22302220793D2230222F3E3C613A6578742063783D22302220" +
      "63793D2230222F3E3C613A63684F666620783D22302220793D2230222F3E3C61" +
      "3A63684578742063783D2230222063793D2230222F3E3C2F613A7866726D3E3C" +
      "2F703A677270537050723E3C703A73703E3C703A6E76537050723E3C703A634E" +
      "7650722069643D223222206E616D653D225469746C652031222F3E3C703A634E" +
      "76537050723E3C613A73704C6F636B73206E6F4772703D2231222F3E3C2F703A" +
      "634E76537050723E3C703A6E7650723E3C703A706820747970653D227469746C" +
      "65222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050722F" +
      "3E3C703A7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C" +
      "652F3E3C613A703E3C613A723E3C613A725072206C616E673D22656E2D555322" +
      "20736D74436C65616E3D2230222F3E3C613A743E436C69636B20746F20656469" +
      "74204D6173746572207469746C65207374796C653C2F613A743E3C2F613A723E" +
      "3C613A656E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A" +
      "703E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E7653" +
      "7050723E3C703A634E7650722069643D223322206E616D653D22436F6E74656E" +
      "7420506C616365686F6C6465722032222F3E3C703A634E76537050723E3C613A" +
      "73704C6F636B73206E6F4772703D2231222F3E3C2F703A634E76537050723E3C" +
      "703A6E7650723E3C703A7068206964783D2231222F3E3C2F703A6E7650723E3C" +
      "2F703A6E76537050723E3C703A737050722F3E3C703A7478426F64793E3C613A" +
      "626F647950722F3E3C613A6C73745374796C652F3E3C613A703E3C613A705072" +
      "206C766C3D2230222F3E3C613A723E3C613A725072206C616E673D22656E2D55" +
      "532220736D74436C65616E3D2230222F3E3C613A743E436C69636B20746F2065" +
      "646974204D61737465722074657874207374796C65733C2F613A743E3C2F613A" +
      "723E3C2F613A703E3C613A703E3C613A705072206C766C3D2231222F3E3C613A" +
      "723E3C613A725072206C616E673D22656E2D55532220736D74436C65616E3D22" +
      "30222F3E3C613A743E5365636F6E64206C6576656C3C2F613A743E3C2F613A72" +
      "3E3C2F613A703E3C613A703E3C613A705072206C766C3D2232222F3E3C613A72" +
      "3E3C613A725072206C616E673D22656E2D55532220736D74436C65616E3D2230" +
      "222F3E3C613A743E5468697264206C6576656C3C2F613A743E3C2F613A723E3C" +
      "2F613A703E3C613A703E3C613A705072206C766C3D2233222F3E3C613A723E3C" +
      "613A725072206C616E673D22656E2D55532220736D74436C65616E3D2230222F" +
      "3E3C613A743E466F75727468206C6576656C3C2F613A743E3C2F613A723E3C2F" +
      "613A703E3C613A703E3C613A705072206C766C3D2234222F3E3C613A723E3C61" +
      "3A725072206C616E673D22656E2D55532220736D74436C65616E3D2230222F3E" +
      "3C613A743E4669667468206C6576656C3C2F613A743E3C2F613A723E3C613A65" +
      "6E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F" +
      "703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E76537050723E" +
      "3C703A634E7650722069643D223422206E616D653D2252656374616E676C6520" +
      "34222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F4772703D" +
      "223122206E6F4368616E67654172726F7768656164733D2231222F3E3C2F703A" +
      "634E76537050723E3C703A6E7650723E3C703A706820747970653D2264742220" +
      "737A3D2268616C6622206964783D223130222F3E3C2F703A6E7650723E3C2F70" +
      "3A6E76537050723E3C703A737050723E3C613A6C6E2F3E3C2F703A737050723E" +
      "3C703A7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C65" +
      "3E3C613A6C766C317050723E3C613A6465665250722F3E3C2F613A6C766C3170" +
      "50723E3C2F613A6C73745374796C653E3C613A703E3C613A656E645061726152" +
      "5072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64" +
      "793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A634E7650" +
      "722069643D223522206E616D653D2252656374616E676C652035222F3E3C703A" +
      "634E76537050723E3C613A73704C6F636B73206E6F4772703D223122206E6F43" +
      "68616E67654172726F7768656164733D2231222F3E3C2F703A634E7653705072" +
      "3E3C703A6E7650723E3C703A706820747970653D226674722220737A3D227175" +
      "617274657222206964783D223131222F3E3C2F703A6E7650723E3C2F703A6E76" +
      "537050723E3C703A737050723E3C613A6C6E2F3E3C2F703A737050723E3C703A" +
      "7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C653E3C61" +
      "3A6C766C317050723E3C613A6465665250722F3E3C2F613A6C766C317050723E" +
      "3C2F613A6C73745374796C653E3C613A703E3C613A656E645061726152507220" +
      "6C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C" +
      "2F703A73703E3C703A73703E3C703A6E76537050723E3C703A634E7650722069" +
      "643D223622206E616D653D2252656374616E676C652036222F3E3C703A634E76" +
      "537050723E3C613A73704C6F636B73206E6F4772703D223122206E6F4368616E" +
      "67654172726F7768656164733D2231222F3E3C2F703A634E76537050723E3C70" +
      "3A6E7650723E3C703A706820747970653D22736C644E756D2220737A3D227175" +
      "617274657222206964783D223132222F3E3C2F703A6E7650723E3C2F703A6E76" +
      "537050723E3C703A737050723E3C613A6C6E2F3E3C2F703A737050723E3C703A" +
      "7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C653E3C61" +
      "3A6C766C317050723E3C613A6465665250722F3E3C2F613A6C766C317050723E" +
      "3C2F613A6C73745374796C653E3C613A703E3C613A666C642069643D227B4337" +
      "4531424644322D374541302D394234342D394533342D45344635453633423534" +
      "41417D2220747970653D22736C6964656E756D223E3C613A725072206C616E67" +
      "3D22656E2D5553222F3E3C613A7050722F3E3C613A743EE280B923E280BA3C2F" +
      "613A743E3C2F613A666C643E3C613A656E6450617261525072206C616E673D22" +
      "656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E" +
      "3C2F703A7370547265653E3C2F703A63536C643E3C703A636C724D61704F7672" +
      "3E3C613A6D6173746572436C724D617070696E672F3E3C2F703A636C724D6170" +
      "4F76723E3C2F703A736C644C61796F75743E";

   public static final String SLIDE_LAYOUT_3 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644C" +
      "61796F757420786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E222074797065" +
      "3D2273656348656164222070726573657276653D2231223E3C703A63536C6420" +
      "6E616D653D2253656374696F6E20486561646572223E3C703A7370547265653E" +
      "3C703A6E76477270537050723E3C703A634E7650722069643D223122206E616D" +
      "653D22222F3E3C703A634E76477270537050722F3E3C703A6E7650722F3E3C2F" +
      "703A6E76477270537050723E3C703A677270537050723E3C613A7866726D3E3C" +
      "613A6F666620783D22302220793D2230222F3E3C613A6578742063783D223022" +
      "2063793D2230222F3E3C613A63684F666620783D22302220793D2230222F3E3C" +
      "613A63684578742063783D2230222063793D2230222F3E3C2F613A7866726D3E" +
      "3C2F703A677270537050723E3C703A73703E3C703A6E76537050723E3C703A63" +
      "4E7650722069643D223222206E616D653D225469746C652031222F3E3C703A63" +
      "4E76537050723E3C613A73704C6F636B73206E6F4772703D2231222F3E3C2F70" +
      "3A634E76537050723E3C703A6E7650723E3C703A706820747970653D22746974" +
      "6C65222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A73705072" +
      "3E3C613A7866726D3E3C613A6F666620783D223732323331332220793D223434" +
      "3036393030222F3E3C613A6578742063783D2237373732343030222063793D22" +
      "31333632303735222F3E3C2F613A7866726D3E3C2F703A737050723E3C703A74" +
      "78426F64793E3C613A626F6479507220616E63686F723D2274222F3E3C613A6C" +
      "73745374796C653E3C613A6C766C3170507220616C676E3D226C223E3C613A64" +
      "656652507220737A3D22343030302220623D223122206361703D22616C6C222F" +
      "3E3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C613A703E3C" +
      "613A723E3C613A725072206C616E673D22656E2D55532220736D74436C65616E" +
      "3D2230222F3E3C613A743E436C69636B20746F2065646974204D617374657220" +
      "7469746C65207374796C653C2F613A743E3C2F613A723E3C613A656E64506172" +
      "61525072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A747842" +
      "6F64793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A634E" +
      "7650722069643D223322206E616D653D225465787420506C616365686F6C6465" +
      "722032222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F4772" +
      "703D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A7068" +
      "20747970653D22626F647922206964783D2231222F3E3C2F703A6E7650723E3C" +
      "2F703A6E76537050723E3C703A737050723E3C613A7866726D3E3C613A6F6666" +
      "20783D223732323331332220793D2232393036373133222F3E3C613A65787420" +
      "63783D2237373732343030222063793D2231353030313837222F3E3C2F613A78" +
      "66726D3E3C2F703A737050723E3C703A7478426F64793E3C613A626F64795072" +
      "20616E63686F723D2262222F3E3C613A6C73745374796C653E3C613A6C766C31" +
      "705072206D61724C3D22302220696E64656E743D2230223E3C613A62754E6F6E" +
      "652F3E3C613A64656652507220737A3D2232303030222F3E3C2F613A6C766C31" +
      "7050723E3C613A6C766C32705072206D61724C3D223435373230302220696E64" +
      "656E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220737A3D" +
      "2231383030222F3E3C2F613A6C766C327050723E3C613A6C766C33705072206D" +
      "61724C3D223931343430302220696E64656E743D2230223E3C613A62754E6F6E" +
      "652F3E3C613A64656652507220737A3D2231363030222F3E3C2F613A6C766C33" +
      "7050723E3C613A6C766C34705072206D61724C3D22313337313630302220696E" +
      "64656E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220737A" +
      "3D2231343030222F3E3C2F613A6C766C347050723E3C613A6C766C3570507220" +
      "6D61724C3D22313832383830302220696E64656E743D2230223E3C613A62754E" +
      "6F6E652F3E3C613A64656652507220737A3D2231343030222F3E3C2F613A6C76" +
      "6C357050723E3C613A6C766C36705072206D61724C3D22323238363030302220" +
      "696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220" +
      "737A3D2231343030222F3E3C2F613A6C766C367050723E3C613A6C766C377050" +
      "72206D61724C3D22323734333230302220696E64656E743D2230223E3C613A62" +
      "754E6F6E652F3E3C613A64656652507220737A3D2231343030222F3E3C2F613A" +
      "6C766C377050723E3C613A6C766C38705072206D61724C3D2233323030343030" +
      "2220696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A6465665250" +
      "7220737A3D2231343030222F3E3C2F613A6C766C387050723E3C613A6C766C39" +
      "705072206D61724C3D22333635373630302220696E64656E743D2230223E3C61" +
      "3A62754E6F6E652F3E3C613A64656652507220737A3D2231343030222F3E3C2F" +
      "613A6C766C397050723E3C2F613A6C73745374796C653E3C613A703E3C613A70" +
      "5072206C766C3D2230222F3E3C613A723E3C613A725072206C616E673D22656E" +
      "2D55532220736D74436C65616E3D2230222F3E3C613A743E436C69636B20746F" +
      "2065646974204D61737465722074657874207374796C65733C2F613A743E3C2F" +
      "613A723E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A73" +
      "703E3C703A6E76537050723E3C703A634E7650722069643D223422206E616D65" +
      "3D2252656374616E676C652034222F3E3C703A634E76537050723E3C613A7370" +
      "4C6F636B73206E6F4772703D223122206E6F4368616E67654172726F77686561" +
      "64733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A70" +
      "6820747970653D2264742220737A3D2268616C6622206964783D223130222F3E" +
      "3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C613A6C" +
      "6E2F3E3C2F703A737050723E3C703A7478426F64793E3C613A626F647950722F" +
      "3E3C613A6C73745374796C653E3C613A6C766C317050723E3C613A6465665250" +
      "722F3E3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C613A70" +
      "3E3C613A656E6450617261525072206C616E673D22656E2D5553222F3E3C2F61" +
      "3A703E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E76" +
      "537050723E3C703A634E7650722069643D223522206E616D653D225265637461" +
      "6E676C652035222F3E3C703A634E76537050723E3C613A73704C6F636B73206E" +
      "6F4772703D223122206E6F4368616E67654172726F7768656164733D2231222F" +
      "3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820747970653D" +
      "226674722220737A3D227175617274657222206964783D223131222F3E3C2F70" +
      "3A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C613A6C6E2F3E" +
      "3C2F703A737050723E3C703A7478426F64793E3C613A626F647950722F3E3C61" +
      "3A6C73745374796C653E3C613A6C766C317050723E3C613A6465665250722F3E" +
      "3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C613A703E3C61" +
      "3A656E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A703E" +
      "3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E76537050" +
      "723E3C703A634E7650722069643D223622206E616D653D2252656374616E676C" +
      "652036222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F4772" +
      "703D223122206E6F4368616E67654172726F7768656164733D2231222F3E3C2F" +
      "703A634E76537050723E3C703A6E7650723E3C703A706820747970653D22736C" +
      "644E756D2220737A3D227175617274657222206964783D223132222F3E3C2F70" +
      "3A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C613A6C6E2F3E" +
      "3C2F703A737050723E3C703A7478426F64793E3C613A626F647950722F3E3C61" +
      "3A6C73745374796C653E3C613A6C766C317050723E3C613A6465665250722F3E" +
      "3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C613A703E3C61" +
      "3A666C642069643D227B42364639443738312D353739322D373734412D393630" +
      "322D3938354443393836323435377D2220747970653D22736C6964656E756D22" +
      "3E3C613A725072206C616E673D22656E2D5553222F3E3C613A7050722F3E3C61" +
      "3A743EE280B923E280BA3C2F613A743E3C2F613A666C643E3C613A656E645061" +
      "7261525072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478" +
      "426F64793E3C2F703A73703E3C2F703A7370547265653E3C2F703A63536C643E" +
      "3C703A636C724D61704F76723E3C613A6D6173746572436C724D617070696E67" +
      "2F3E3C2F703A636C724D61704F76723E3C2F703A736C644C61796F75743E";

   public static final String SLIDE_LAYOUT_4 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644C" +
      "61796F757420786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E222074797065" +
      "3D2274776F4F626A222070726573657276653D2231223E3C703A63536C64206E" +
      "616D653D2254776F20436F6E74656E74223E3C703A7370547265653E3C703A6E" +
      "76477270537050723E3C703A634E7650722069643D223122206E616D653D2222" +
      "2F3E3C703A634E76477270537050722F3E3C703A6E7650722F3E3C2F703A6E76" +
      "477270537050723E3C703A677270537050723E3C613A7866726D3E3C613A6F66" +
      "6620783D22302220793D2230222F3E3C613A6578742063783D2230222063793D" +
      "2230222F3E3C613A63684F666620783D22302220793D2230222F3E3C613A6368" +
      "4578742063783D2230222063793D2230222F3E3C2F613A7866726D3E3C2F703A" +
      "677270537050723E3C703A73703E3C703A6E76537050723E3C703A634E765072" +
      "2069643D223222206E616D653D225469746C652031222F3E3C703A634E765370" +
      "50723E3C613A73704C6F636B73206E6F4772703D2231222F3E3C2F703A634E76" +
      "537050723E3C703A6E7650723E3C703A706820747970653D227469746C65222F" +
      "3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050722F3E3C70" +
      "3A7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C652F3E" +
      "3C613A703E3C613A723E3C613A725072206C616E673D22656E2D55532220736D" +
      "74436C65616E3D2230222F3E3C613A743E436C69636B20746F2065646974204D" +
      "6173746572207469746C65207374796C653C2F613A743E3C2F613A723E3C613A" +
      "656E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A703E3C" +
      "2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E7653705072" +
      "3E3C703A634E7650722069643D223322206E616D653D22436F6E74656E742050" +
      "6C616365686F6C6465722032222F3E3C703A634E76537050723E3C613A73704C" +
      "6F636B73206E6F4772703D2231222F3E3C2F703A634E76537050723E3C703A6E" +
      "7650723E3C703A706820737A3D2268616C6622206964783D2231222F3E3C2F70" +
      "3A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C613A7866726D" +
      "3E3C613A6F666620783D223638353830302220793D2231393831323030222F3E" +
      "3C613A6578742063783D2233383130303030222063793D223431313438303022" +
      "2F3E3C2F613A7866726D3E3C2F703A737050723E3C703A7478426F64793E3C61" +
      "3A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C317050723E" +
      "3C613A64656652507220737A3D2232383030222F3E3C2F613A6C766C31705072" +
      "3E3C613A6C766C327050723E3C613A64656652507220737A3D2232343030222F" +
      "3E3C2F613A6C766C327050723E3C613A6C766C337050723E3C613A6465665250" +
      "7220737A3D2232303030222F3E3C2F613A6C766C337050723E3C613A6C766C34" +
      "7050723E3C613A64656652507220737A3D2231383030222F3E3C2F613A6C766C" +
      "347050723E3C613A6C766C357050723E3C613A64656652507220737A3D223138" +
      "3030222F3E3C2F613A6C766C357050723E3C613A6C766C367050723E3C613A64" +
      "656652507220737A3D2231383030222F3E3C2F613A6C766C367050723E3C613A" +
      "6C766C377050723E3C613A64656652507220737A3D2231383030222F3E3C2F61" +
      "3A6C766C377050723E3C613A6C766C387050723E3C613A64656652507220737A" +
      "3D2231383030222F3E3C2F613A6C766C387050723E3C613A6C766C397050723E" +
      "3C613A64656652507220737A3D2231383030222F3E3C2F613A6C766C39705072" +
      "3E3C2F613A6C73745374796C653E3C613A703E3C613A705072206C766C3D2230" +
      "222F3E3C613A723E3C613A725072206C616E673D22656E2D55532220736D7443" +
      "6C65616E3D2230222F3E3C613A743E436C69636B20746F2065646974204D6173" +
      "7465722074657874207374796C65733C2F613A743E3C2F613A723E3C2F613A70" +
      "3E3C613A703E3C613A705072206C766C3D2231222F3E3C613A723E3C613A7250" +
      "72206C616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A" +
      "743E5365636F6E64206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E" +
      "3C613A703E3C613A705072206C766C3D2232222F3E3C613A723E3C613A725072" +
      "206C616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A74" +
      "3E5468697264206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C61" +
      "3A703E3C613A705072206C766C3D2233222F3E3C613A723E3C613A725072206C" +
      "616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E46" +
      "6F75727468206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C613A" +
      "703E3C613A705072206C766C3D2234222F3E3C613A723E3C613A725072206C61" +
      "6E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E4669" +
      "667468206C6576656C3C2F613A743E3C2F613A723E3C613A656E645061726152" +
      "5072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64" +
      "793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A634E7650" +
      "722069643D223422206E616D653D22436F6E74656E7420506C616365686F6C64" +
      "65722033222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F47" +
      "72703D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A70" +
      "6820737A3D2268616C6622206964783D2232222F3E3C2F703A6E7650723E3C2F" +
      "703A6E76537050723E3C703A737050723E3C613A7866726D3E3C613A6F666620" +
      "783D22343634383230302220793D2231393831323030222F3E3C613A65787420" +
      "63783D2233383130303030222063793D2234313134383030222F3E3C2F613A78" +
      "66726D3E3C2F703A737050723E3C703A7478426F64793E3C613A626F64795072" +
      "2F3E3C613A6C73745374796C653E3C613A6C766C317050723E3C613A64656652" +
      "507220737A3D2232383030222F3E3C2F613A6C766C317050723E3C613A6C766C" +
      "327050723E3C613A64656652507220737A3D2232343030222F3E3C2F613A6C76" +
      "6C327050723E3C613A6C766C337050723E3C613A64656652507220737A3D2232" +
      "303030222F3E3C2F613A6C766C337050723E3C613A6C766C347050723E3C613A" +
      "64656652507220737A3D2231383030222F3E3C2F613A6C766C347050723E3C61" +
      "3A6C766C357050723E3C613A64656652507220737A3D2231383030222F3E3C2F" +
      "613A6C766C357050723E3C613A6C766C367050723E3C613A6465665250722073" +
      "7A3D2231383030222F3E3C2F613A6C766C367050723E3C613A6C766C37705072" +
      "3E3C613A64656652507220737A3D2231383030222F3E3C2F613A6C766C377050" +
      "723E3C613A6C766C387050723E3C613A64656652507220737A3D223138303022" +
      "2F3E3C2F613A6C766C387050723E3C613A6C766C397050723E3C613A64656652" +
      "507220737A3D2231383030222F3E3C2F613A6C766C397050723E3C2F613A6C73" +
      "745374796C653E3C613A703E3C613A705072206C766C3D2230222F3E3C613A72" +
      "3E3C613A725072206C616E673D22656E2D55532220736D74436C65616E3D2230" +
      "222F3E3C613A743E436C69636B20746F2065646974204D617374657220746578" +
      "74207374796C65733C2F613A743E3C2F613A723E3C2F613A703E3C613A703E3C" +
      "613A705072206C766C3D2231222F3E3C613A723E3C613A725072206C616E673D" +
      "22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E5365636F6E" +
      "64206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C613A703E3C61" +
      "3A705072206C766C3D2232222F3E3C613A723E3C613A725072206C616E673D22" +
      "656E2D55532220736D74436C65616E3D2230222F3E3C613A743E546869726420" +
      "6C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C613A703E3C613A70" +
      "5072206C766C3D2233222F3E3C613A723E3C613A725072206C616E673D22656E" +
      "2D55532220736D74436C65616E3D2230222F3E3C613A743E466F75727468206C" +
      "6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C613A703E3C613A7050" +
      "72206C766C3D2234222F3E3C613A723E3C613A725072206C616E673D22656E2D" +
      "55532220736D74436C65616E3D2230222F3E3C613A743E4669667468206C6576" +
      "656C3C2F613A743E3C2F613A723E3C613A656E6450617261525072206C616E67" +
      "3D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73" +
      "703E3C703A73703E3C703A6E76537050723E3C703A634E7650722069643D2235" +
      "22206E616D653D2252656374616E676C652034222F3E3C703A634E7653705072" +
      "3E3C613A73704C6F636B73206E6F4772703D223122206E6F4368616E67654172" +
      "726F7768656164733D2231222F3E3C2F703A634E76537050723E3C703A6E7650" +
      "723E3C703A706820747970653D2264742220737A3D2268616C6622206964783D" +
      "223130222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050" +
      "723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C613A62" +
      "6F647950722F3E3C613A6C73745374796C653E3C613A6C766C317050723E3C61" +
      "3A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C73745374796C" +
      "653E3C613A703E3C613A656E6450617261525072206C616E673D22656E2D5553" +
      "222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A7370" +
      "3E3C703A6E76537050723E3C703A634E7650722069643D223622206E616D653D" +
      "2252656374616E676C652035222F3E3C703A634E76537050723E3C613A73704C" +
      "6F636B73206E6F4772703D223122206E6F4368616E67654172726F7768656164" +
      "733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A7068" +
      "20747970653D226674722220737A3D227175617274657222206964783D223131" +
      "222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C" +
      "613A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C613A626F6479" +
      "50722F3E3C613A6C73745374796C653E3C613A6C766C317050723E3C613A6465" +
      "665250722F3E3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C" +
      "613A703E3C613A656E6450617261525072206C616E673D22656E2D5553222F3E" +
      "3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C70" +
      "3A6E76537050723E3C703A634E7650722069643D223722206E616D653D225265" +
      "6374616E676C652036222F3E3C703A634E76537050723E3C613A73704C6F636B" +
      "73206E6F4772703D223122206E6F4368616E67654172726F7768656164733D22" +
      "31222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A7068207479" +
      "70653D22736C644E756D2220737A3D227175617274657222206964783D223132" +
      "222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C" +
      "613A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C613A626F6479" +
      "50722F3E3C613A6C73745374796C653E3C613A6C766C317050723E3C613A6465" +
      "665250722F3E3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C" +
      "613A703E3C613A666C642069643D227B46343938424232452D374346372D4334" +
      "34322D424135352D3238443636343633453444467D2220747970653D22736C69" +
      "64656E756D223E3C613A725072206C616E673D22656E2D5553222F3E3C613A70" +
      "50722F3E3C613A743EE280B923E280BA3C2F613A743E3C2F613A666C643E3C61" +
      "3A656E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A703E" +
      "3C2F703A7478426F64793E3C2F703A73703E3C2F703A7370547265653E3C2F70" +
      "3A63536C643E3C703A636C724D61704F76723E3C613A6D6173746572436C724D" +
      "617070696E672F3E3C2F703A636C724D61704F76723E3C2F703A736C644C6179" +
      "6F75743E";

   public static final String SLIDE_LAYOUT_5 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644C" +
      "61796F757420786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E222074797065" +
      "3D2274776F547854776F4F626A222070726573657276653D2231223E3C703A63" +
      "536C64206E616D653D22436F6D70617269736F6E223E3C703A7370547265653E" +
      "3C703A6E76477270537050723E3C703A634E7650722069643D223122206E616D" +
      "653D22222F3E3C703A634E76477270537050722F3E3C703A6E7650722F3E3C2F" +
      "703A6E76477270537050723E3C703A677270537050723E3C613A7866726D3E3C" +
      "613A6F666620783D22302220793D2230222F3E3C613A6578742063783D223022" +
      "2063793D2230222F3E3C613A63684F666620783D22302220793D2230222F3E3C" +
      "613A63684578742063783D2230222063793D2230222F3E3C2F613A7866726D3E" +
      "3C2F703A677270537050723E3C703A73703E3C703A6E76537050723E3C703A63" +
      "4E7650722069643D223222206E616D653D225469746C652031222F3E3C703A63" +
      "4E76537050723E3C613A73704C6F636B73206E6F4772703D2231222F3E3C2F70" +
      "3A634E76537050723E3C703A6E7650723E3C703A706820747970653D22746974" +
      "6C65222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A73705072" +
      "3E3C613A7866726D3E3C613A6F666620783D223435373230302220793D223237" +
      "34363338222F3E3C613A6578742063783D2238323239363030222063793D2231" +
      "313433303030222F3E3C2F613A7866726D3E3C2F703A737050723E3C703A7478" +
      "426F64793E3C613A626F647950722F3E3C613A6C73745374796C653E3C613A6C" +
      "766C317050723E3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F" +
      "613A6C73745374796C653E3C613A703E3C613A723E3C613A725072206C616E67" +
      "3D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E436C6963" +
      "6B20746F2065646974204D6173746572207469746C65207374796C653C2F613A" +
      "743E3C2F613A723E3C613A656E6450617261525072206C616E673D22656E2D55" +
      "53222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A73" +
      "703E3C703A6E76537050723E3C703A634E7650722069643D223322206E616D65" +
      "3D225465787420506C616365686F6C6465722032222F3E3C703A634E76537050" +
      "723E3C613A73704C6F636B73206E6F4772703D2231222F3E3C2F703A634E7653" +
      "7050723E3C703A6E7650723E3C703A706820747970653D22626F647922206964" +
      "783D2231222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A7370" +
      "50723E3C613A7866726D3E3C613A6F666620783D223435373230302220793D22" +
      "31353335313133222F3E3C613A6578742063783D223430343031383822206379" +
      "3D22363339373632222F3E3C2F613A7866726D3E3C2F703A737050723E3C703A" +
      "7478426F64793E3C613A626F6479507220616E63686F723D2262222F3E3C613A" +
      "6C73745374796C653E3C613A6C766C31705072206D61724C3D22302220696E64" +
      "656E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220737A3D" +
      "22323430302220623D2231222F3E3C2F613A6C766C317050723E3C613A6C766C" +
      "32705072206D61724C3D223435373230302220696E64656E743D2230223E3C61" +
      "3A62754E6F6E652F3E3C613A64656652507220737A3D22323030302220623D22" +
      "31222F3E3C2F613A6C766C327050723E3C613A6C766C33705072206D61724C3D" +
      "223931343430302220696E64656E743D2230223E3C613A62754E6F6E652F3E3C" +
      "613A64656652507220737A3D22313830302220623D2231222F3E3C2F613A6C76" +
      "6C337050723E3C613A6C766C34705072206D61724C3D22313337313630302220" +
      "696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220" +
      "737A3D22313630302220623D2231222F3E3C2F613A6C766C347050723E3C613A" +
      "6C766C35705072206D61724C3D22313832383830302220696E64656E743D2230" +
      "223E3C613A62754E6F6E652F3E3C613A64656652507220737A3D223136303022" +
      "20623D2231222F3E3C2F613A6C766C357050723E3C613A6C766C36705072206D" +
      "61724C3D22323238363030302220696E64656E743D2230223E3C613A62754E6F" +
      "6E652F3E3C613A64656652507220737A3D22313630302220623D2231222F3E3C" +
      "2F613A6C766C367050723E3C613A6C766C37705072206D61724C3D2232373433" +
      "3230302220696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A6465" +
      "6652507220737A3D22313630302220623D2231222F3E3C2F613A6C766C377050" +
      "723E3C613A6C766C38705072206D61724C3D22333230303430302220696E6465" +
      "6E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220737A3D22" +
      "313630302220623D2231222F3E3C2F613A6C766C387050723E3C613A6C766C39" +
      "705072206D61724C3D22333635373630302220696E64656E743D2230223E3C61" +
      "3A62754E6F6E652F3E3C613A64656652507220737A3D22313630302220623D22" +
      "31222F3E3C2F613A6C766C397050723E3C2F613A6C73745374796C653E3C613A" +
      "703E3C613A705072206C766C3D2230222F3E3C613A723E3C613A725072206C61" +
      "6E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E436C" +
      "69636B20746F2065646974204D61737465722074657874207374796C65733C2F" +
      "613A743E3C2F613A723E3C2F613A703E3C2F703A7478426F64793E3C2F703A73" +
      "703E3C703A73703E3C703A6E76537050723E3C703A634E7650722069643D2234" +
      "22206E616D653D22436F6E74656E7420506C616365686F6C6465722033222F3E" +
      "3C703A634E76537050723E3C613A73704C6F636B73206E6F4772703D2231222F" +
      "3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820737A3D2268" +
      "616C6622206964783D2232222F3E3C2F703A6E7650723E3C2F703A6E76537050" +
      "723E3C703A737050723E3C613A7866726D3E3C613A6F666620783D2234353732" +
      "30302220793D2232313734383735222F3E3C613A6578742063783D2234303430" +
      "313838222063793D2233393531323838222F3E3C2F613A7866726D3E3C2F703A" +
      "737050723E3C703A7478426F64793E3C613A626F647950722F3E3C613A6C7374" +
      "5374796C653E3C613A6C766C317050723E3C613A64656652507220737A3D2232" +
      "343030222F3E3C2F613A6C766C317050723E3C613A6C766C327050723E3C613A" +
      "64656652507220737A3D2232303030222F3E3C2F613A6C766C327050723E3C61" +
      "3A6C766C337050723E3C613A64656652507220737A3D2231383030222F3E3C2F" +
      "613A6C766C337050723E3C613A6C766C347050723E3C613A6465665250722073" +
      "7A3D2231363030222F3E3C2F613A6C766C347050723E3C613A6C766C35705072" +
      "3E3C613A64656652507220737A3D2231363030222F3E3C2F613A6C766C357050" +
      "723E3C613A6C766C367050723E3C613A64656652507220737A3D223136303022" +
      "2F3E3C2F613A6C766C367050723E3C613A6C766C377050723E3C613A64656652" +
      "507220737A3D2231363030222F3E3C2F613A6C766C377050723E3C613A6C766C" +
      "387050723E3C613A64656652507220737A3D2231363030222F3E3C2F613A6C76" +
      "6C387050723E3C613A6C766C397050723E3C613A64656652507220737A3D2231" +
      "363030222F3E3C2F613A6C766C397050723E3C2F613A6C73745374796C653E3C" +
      "613A703E3C613A705072206C766C3D2230222F3E3C613A723E3C613A72507220" +
      "6C616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E" +
      "436C69636B20746F2065646974204D61737465722074657874207374796C6573" +
      "3C2F613A743E3C2F613A723E3C2F613A703E3C613A703E3C613A705072206C76" +
      "6C3D2231222F3E3C613A723E3C613A725072206C616E673D22656E2D55532220" +
      "736D74436C65616E3D2230222F3E3C613A743E5365636F6E64206C6576656C3C" +
      "2F613A743E3C2F613A723E3C2F613A703E3C613A703E3C613A705072206C766C" +
      "3D2232222F3E3C613A723E3C613A725072206C616E673D22656E2D5553222073" +
      "6D74436C65616E3D2230222F3E3C613A743E5468697264206C6576656C3C2F61" +
      "3A743E3C2F613A723E3C2F613A703E3C613A703E3C613A705072206C766C3D22" +
      "33222F3E3C613A723E3C613A725072206C616E673D22656E2D55532220736D74" +
      "436C65616E3D2230222F3E3C613A743E466F75727468206C6576656C3C2F613A" +
      "743E3C2F613A723E3C2F613A703E3C613A703E3C613A705072206C766C3D2234" +
      "222F3E3C613A723E3C613A725072206C616E673D22656E2D55532220736D7443" +
      "6C65616E3D2230222F3E3C613A743E4669667468206C6576656C3C2F613A743E" +
      "3C2F613A723E3C613A656E6450617261525072206C616E673D22656E2D555322" +
      "2F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E" +
      "3C703A6E76537050723E3C703A634E7650722069643D223522206E616D653D22" +
      "5465787420506C616365686F6C6465722034222F3E3C703A634E76537050723E" +
      "3C613A73704C6F636B73206E6F4772703D2231222F3E3C2F703A634E76537050" +
      "723E3C703A6E7650723E3C703A706820747970653D22626F64792220737A3D22" +
      "7175617274657222206964783D2233222F3E3C2F703A6E7650723E3C2F703A6E" +
      "76537050723E3C703A737050723E3C613A7866726D3E3C613A6F666620783D22" +
      "343634353032352220793D2231353335313133222F3E3C613A6578742063783D" +
      "2234303431373735222063793D22363339373632222F3E3C2F613A7866726D3E" +
      "3C2F703A737050723E3C703A7478426F64793E3C613A626F6479507220616E63" +
      "686F723D2262222F3E3C613A6C73745374796C653E3C613A6C766C3170507220" +
      "6D61724C3D22302220696E64656E743D2230223E3C613A62754E6F6E652F3E3C" +
      "613A64656652507220737A3D22323430302220623D2231222F3E3C2F613A6C76" +
      "6C317050723E3C613A6C766C32705072206D61724C3D22343537323030222069" +
      "6E64656E743D2230223E3C613A62754E6F6E652F3E3C613A6465665250722073" +
      "7A3D22323030302220623D2231222F3E3C2F613A6C766C327050723E3C613A6C" +
      "766C33705072206D61724C3D223931343430302220696E64656E743D2230223E" +
      "3C613A62754E6F6E652F3E3C613A64656652507220737A3D2231383030222062" +
      "3D2231222F3E3C2F613A6C766C337050723E3C613A6C766C34705072206D6172" +
      "4C3D22313337313630302220696E64656E743D2230223E3C613A62754E6F6E65" +
      "2F3E3C613A64656652507220737A3D22313630302220623D2231222F3E3C2F61" +
      "3A6C766C347050723E3C613A6C766C35705072206D61724C3D22313832383830" +
      "302220696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A64656652" +
      "507220737A3D22313630302220623D2231222F3E3C2F613A6C766C357050723E" +
      "3C613A6C766C36705072206D61724C3D22323238363030302220696E64656E74" +
      "3D2230223E3C613A62754E6F6E652F3E3C613A64656652507220737A3D223136" +
      "30302220623D2231222F3E3C2F613A6C766C367050723E3C613A6C766C377050" +
      "72206D61724C3D22323734333230302220696E64656E743D2230223E3C613A62" +
      "754E6F6E652F3E3C613A64656652507220737A3D22313630302220623D223122" +
      "2F3E3C2F613A6C766C377050723E3C613A6C766C38705072206D61724C3D2233" +
      "3230303430302220696E64656E743D2230223E3C613A62754E6F6E652F3E3C61" +
      "3A64656652507220737A3D22313630302220623D2231222F3E3C2F613A6C766C" +
      "387050723E3C613A6C766C39705072206D61724C3D2233363537363030222069" +
      "6E64656E743D2230223E3C613A62754E6F6E652F3E3C613A6465665250722073" +
      "7A3D22313630302220623D2231222F3E3C2F613A6C766C397050723E3C2F613A" +
      "6C73745374796C653E3C613A703E3C613A705072206C766C3D2230222F3E3C61" +
      "3A723E3C613A725072206C616E673D22656E2D55532220736D74436C65616E3D" +
      "2230222F3E3C613A743E436C69636B20746F2065646974204D61737465722074" +
      "657874207374796C65733C2F613A743E3C2F613A723E3C2F613A703E3C2F703A" +
      "7478426F64793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C70" +
      "3A634E7650722069643D223622206E616D653D22436F6E74656E7420506C6163" +
      "65686F6C6465722035222F3E3C703A634E76537050723E3C613A73704C6F636B" +
      "73206E6F4772703D2231222F3E3C2F703A634E76537050723E3C703A6E765072" +
      "3E3C703A706820737A3D227175617274657222206964783D2234222F3E3C2F70" +
      "3A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C613A7866726D" +
      "3E3C613A6F666620783D22343634353032352220793D2232313734383735222F" +
      "3E3C613A6578742063783D2234303431373735222063793D2233393531323838" +
      "222F3E3C2F613A7866726D3E3C2F703A737050723E3C703A7478426F64793E3C" +
      "613A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C31705072" +
      "3E3C613A64656652507220737A3D2232343030222F3E3C2F613A6C766C317050" +
      "723E3C613A6C766C327050723E3C613A64656652507220737A3D223230303022" +
      "2F3E3C2F613A6C766C327050723E3C613A6C766C337050723E3C613A64656652" +
      "507220737A3D2231383030222F3E3C2F613A6C766C337050723E3C613A6C766C" +
      "347050723E3C613A64656652507220737A3D2231363030222F3E3C2F613A6C76" +
      "6C347050723E3C613A6C766C357050723E3C613A64656652507220737A3D2231" +
      "363030222F3E3C2F613A6C766C357050723E3C613A6C766C367050723E3C613A" +
      "64656652507220737A3D2231363030222F3E3C2F613A6C766C367050723E3C61" +
      "3A6C766C377050723E3C613A64656652507220737A3D2231363030222F3E3C2F" +
      "613A6C766C377050723E3C613A6C766C387050723E3C613A6465665250722073" +
      "7A3D2231363030222F3E3C2F613A6C766C387050723E3C613A6C766C39705072" +
      "3E3C613A64656652507220737A3D2231363030222F3E3C2F613A6C766C397050" +
      "723E3C2F613A6C73745374796C653E3C613A703E3C613A705072206C766C3D22" +
      "30222F3E3C613A723E3C613A725072206C616E673D22656E2D55532220736D74" +
      "436C65616E3D2230222F3E3C613A743E436C69636B20746F2065646974204D61" +
      "737465722074657874207374796C65733C2F613A743E3C2F613A723E3C2F613A" +
      "703E3C613A703E3C613A705072206C766C3D2231222F3E3C613A723E3C613A72" +
      "5072206C616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C61" +
      "3A743E5365636F6E64206C6576656C3C2F613A743E3C2F613A723E3C2F613A70" +
      "3E3C613A703E3C613A705072206C766C3D2232222F3E3C613A723E3C613A7250" +
      "72206C616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A" +
      "743E5468697264206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C" +
      "613A703E3C613A705072206C766C3D2233222F3E3C613A723E3C613A72507220" +
      "6C616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E" +
      "466F75727468206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C61" +
      "3A703E3C613A705072206C766C3D2234222F3E3C613A723E3C613A725072206C" +
      "616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C613A743E46" +
      "69667468206C6576656C3C2F613A743E3C2F613A723E3C613A656E6450617261" +
      "525072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F" +
      "64793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A634E76" +
      "50722069643D223722206E616D653D2252656374616E676C652034222F3E3C70" +
      "3A634E76537050723E3C613A73704C6F636B73206E6F4772703D223122206E6F" +
      "4368616E67654172726F7768656164733D2231222F3E3C2F703A634E76537050" +
      "723E3C703A6E7650723E3C703A706820747970653D2264742220737A3D226861" +
      "6C6622206964783D223130222F3E3C2F703A6E7650723E3C2F703A6E76537050" +
      "723E3C703A737050723E3C613A6C6E2F3E3C2F703A737050723E3C703A747842" +
      "6F64793E3C613A626F647950722F3E3C613A6C73745374796C653E3C613A6C76" +
      "6C317050723E3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F61" +
      "3A6C73745374796C653E3C613A703E3C613A656E6450617261525072206C616E" +
      "673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A" +
      "73703E3C703A73703E3C703A6E76537050723E3C703A634E7650722069643D22" +
      "3822206E616D653D2252656374616E676C652035222F3E3C703A634E76537050" +
      "723E3C613A73704C6F636B73206E6F4772703D223122206E6F4368616E676541" +
      "72726F7768656164733D2231222F3E3C2F703A634E76537050723E3C703A6E76" +
      "50723E3C703A706820747970653D226674722220737A3D227175617274657222" +
      "206964783D223131222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C" +
      "703A737050723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F6479" +
      "3E3C613A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C3170" +
      "50723E3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C73" +
      "745374796C653E3C613A703E3C613A656E6450617261525072206C616E673D22" +
      "656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E" +
      "3C703A73703E3C703A6E76537050723E3C703A634E7650722069643D22392220" +
      "6E616D653D2252656374616E676C652036222F3E3C703A634E76537050723E3C" +
      "613A73704C6F636B73206E6F4772703D223122206E6F4368616E67654172726F" +
      "7768656164733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E" +
      "3C703A706820747970653D22736C644E756D2220737A3D227175617274657222" +
      "206964783D223132222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C" +
      "703A737050723E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F6479" +
      "3E3C613A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C3170" +
      "50723E3C613A6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C73" +
      "745374796C653E3C613A703E3C613A666C642069643D227B3938323535443239" +
      "2D333643342D324534382D383945392D3532314344443938444139317D222074" +
      "7970653D22736C6964656E756D223E3C613A725072206C616E673D22656E2D55" +
      "53222F3E3C613A7050722F3E3C613A743EE280B923E280BA3C2F613A743E3C2F" +
      "613A666C643E3C613A656E6450617261525072206C616E673D22656E2D555322" +
      "2F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C2F703A7370" +
      "547265653E3C2F703A63536C643E3C703A636C724D61704F76723E3C613A6D61" +
      "73746572436C724D617070696E672F3E3C2F703A636C724D61704F76723E3C2F" +
      "703A736C644C61796F75743E";

   public static final String SLIDE_LAYOUT_6 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644C" +
      "61796F757420786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E222074797065" +
      "3D227469746C654F6E6C79222070726573657276653D2231223E3C703A63536C" +
      "64206E616D653D225469746C65204F6E6C79223E3C703A7370547265653E3C70" +
      "3A6E76477270537050723E3C703A634E7650722069643D223122206E616D653D" +
      "22222F3E3C703A634E76477270537050722F3E3C703A6E7650722F3E3C2F703A" +
      "6E76477270537050723E3C703A677270537050723E3C613A7866726D3E3C613A" +
      "6F666620783D22302220793D2230222F3E3C613A6578742063783D2230222063" +
      "793D2230222F3E3C613A63684F666620783D22302220793D2230222F3E3C613A" +
      "63684578742063783D2230222063793D2230222F3E3C2F613A7866726D3E3C2F" +
      "703A677270537050723E3C703A73703E3C703A6E76537050723E3C703A634E76" +
      "50722069643D223222206E616D653D225469746C652031222F3E3C703A634E76" +
      "537050723E3C613A73704C6F636B73206E6F4772703D2231222F3E3C2F703A63" +
      "4E76537050723E3C703A6E7650723E3C703A706820747970653D227469746C65" +
      "222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050722F3E" +
      "3C703A7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C65" +
      "2F3E3C613A703E3C613A723E3C613A725072206C616E673D22656E2D55532220" +
      "736D74436C65616E3D2230222F3E3C613A743E436C69636B20746F2065646974" +
      "204D6173746572207469746C65207374796C653C2F613A743E3C2F613A723E3C" +
      "613A656E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A70" +
      "3E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E765370" +
      "50723E3C703A634E7650722069643D223322206E616D653D2252656374616E67" +
      "6C652034222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F47" +
      "72703D223122206E6F4368616E67654172726F7768656164733D2231222F3E3C" +
      "2F703A634E76537050723E3C703A6E7650723E3C703A706820747970653D2264" +
      "742220737A3D2268616C6622206964783D223130222F3E3C2F703A6E7650723E" +
      "3C2F703A6E76537050723E3C703A737050723E3C613A6C6E2F3E3C2F703A7370" +
      "50723E3C703A7478426F64793E3C613A626F647950722F3E3C613A6C73745374" +
      "796C653E3C613A6C766C317050723E3C613A6465665250722F3E3C2F613A6C76" +
      "6C317050723E3C2F613A6C73745374796C653E3C613A703E3C613A656E645061" +
      "7261525072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478" +
      "426F64793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A63" +
      "4E7650722069643D223422206E616D653D2252656374616E676C652035222F3E" +
      "3C703A634E76537050723E3C613A73704C6F636B73206E6F4772703D22312220" +
      "6E6F4368616E67654172726F7768656164733D2231222F3E3C2F703A634E7653" +
      "7050723E3C703A6E7650723E3C703A706820747970653D226674722220737A3D" +
      "227175617274657222206964783D223131222F3E3C2F703A6E7650723E3C2F70" +
      "3A6E76537050723E3C703A737050723E3C613A6C6E2F3E3C2F703A737050723E" +
      "3C703A7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C65" +
      "3E3C613A6C766C317050723E3C613A6465665250722F3E3C2F613A6C766C3170" +
      "50723E3C2F613A6C73745374796C653E3C613A703E3C613A656E645061726152" +
      "5072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64" +
      "793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A634E7650" +
      "722069643D223522206E616D653D2252656374616E676C652036222F3E3C703A" +
      "634E76537050723E3C613A73704C6F636B73206E6F4772703D223122206E6F43" +
      "68616E67654172726F7768656164733D2231222F3E3C2F703A634E7653705072" +
      "3E3C703A6E7650723E3C703A706820747970653D22736C644E756D2220737A3D" +
      "227175617274657222206964783D223132222F3E3C2F703A6E7650723E3C2F70" +
      "3A6E76537050723E3C703A737050723E3C613A6C6E2F3E3C2F703A737050723E" +
      "3C703A7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C65" +
      "3E3C613A6C766C317050723E3C613A6465665250722F3E3C2F613A6C766C3170" +
      "50723E3C2F613A6C73745374796C653E3C613A703E3C613A666C642069643D22" +
      "7B32354645324139332D464133412D444234302D393232352D32304233364341" +
      "42333444327D2220747970653D22736C6964656E756D223E3C613A725072206C" +
      "616E673D22656E2D5553222F3E3C613A7050722F3E3C613A743EE280B923E280" +
      "BA3C2F613A743E3C2F613A666C643E3C613A656E6450617261525072206C616E" +
      "673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A" +
      "73703E3C2F703A7370547265653E3C2F703A63536C643E3C703A636C724D6170" +
      "4F76723E3C613A6D6173746572436C724D617070696E672F3E3C2F703A636C72" +
      "4D61704F76723E3C2F703A736C644C61796F75743E";

   public static final String SLIDE_LAYOUT_7 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644C" +
      "61796F757420786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E222074797065" +
      "3D22626C616E6B222070726573657276653D2231223E3C703A63536C64206E61" +
      "6D653D22426C616E6B223E3C703A7370547265653E3C703A6E76477270537050" +
      "723E3C703A634E7650722069643D223122206E616D653D22222F3E3C703A634E" +
      "76477270537050722F3E3C703A6E7650722F3E3C2F703A6E7647727053705072" +
      "3E3C703A677270537050723E3C613A7866726D3E3C613A6F666620783D223022" +
      "20793D2230222F3E3C613A6578742063783D2230222063793D2230222F3E3C61" +
      "3A63684F666620783D22302220793D2230222F3E3C613A63684578742063783D" +
      "2230222063793D2230222F3E3C2F613A7866726D3E3C2F703A67727053705072" +
      "3E3C703A73703E3C703A6E76537050723E3C703A634E7650722069643D223222" +
      "206E616D653D2252656374616E676C652034222F3E3C703A634E76537050723E" +
      "3C613A73704C6F636B73206E6F4772703D223122206E6F4368616E6765417272" +
      "6F7768656164733D2231222F3E3C2F703A634E76537050723E3C703A6E765072" +
      "3E3C703A706820747970653D2264742220737A3D2268616C6622206964783D22" +
      "3130222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A73705072" +
      "3E3C613A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C613A626F" +
      "647950722F3E3C613A6C73745374796C653E3C613A6C766C317050723E3C613A" +
      "6465665250722F3E3C2F613A6C766C317050723E3C2F613A6C73745374796C65" +
      "3E3C613A703E3C613A656E6450617261525072206C616E673D22656E2D555322" +
      "2F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E" +
      "3C703A6E76537050723E3C703A634E7650722069643D223322206E616D653D22" +
      "52656374616E676C652035222F3E3C703A634E76537050723E3C613A73704C6F" +
      "636B73206E6F4772703D223122206E6F4368616E67654172726F776865616473" +
      "3D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820" +
      "747970653D226674722220737A3D227175617274657222206964783D22313122" +
      "2F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C61" +
      "3A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C613A626F647950" +
      "722F3E3C613A6C73745374796C653E3C613A6C766C317050723E3C613A646566" +
      "5250722F3E3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C61" +
      "3A703E3C613A656E6450617261525072206C616E673D22656E2D5553222F3E3C" +
      "2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A" +
      "6E76537050723E3C703A634E7650722069643D223422206E616D653D22526563" +
      "74616E676C652036222F3E3C703A634E76537050723E3C613A73704C6F636B73" +
      "206E6F4772703D223122206E6F4368616E67654172726F7768656164733D2231" +
      "222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820747970" +
      "653D22736C644E756D2220737A3D227175617274657222206964783D22313222" +
      "2F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C61" +
      "3A6C6E2F3E3C2F703A737050723E3C703A7478426F64793E3C613A626F647950" +
      "722F3E3C613A6C73745374796C653E3C613A6C766C317050723E3C613A646566" +
      "5250722F3E3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C61" +
      "3A703E3C613A666C642069643D227B31454130394144422D333834332D354534" +
      "442D424541352D4344343033304533454635417D2220747970653D22736C6964" +
      "656E756D223E3C613A725072206C616E673D22656E2D5553222F3E3C613A7050" +
      "722F3E3C613A743EE280B923E280BA3C2F613A743E3C2F613A666C643E3C613A" +
      "656E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A703E3C" +
      "2F703A7478426F64793E3C2F703A73703E3C2F703A7370547265653E3C2F703A" +
      "63536C643E3C703A636C724D61704F76723E3C613A6D6173746572436C724D61" +
      "7070696E672F3E3C2F703A636C724D61704F76723E3C2F703A736C644C61796F" +
      "75743E";

   public static final String SLIDE_LAYOUT_8 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644C" +
      "61796F757420786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E222074797065" +
      "3D226F626A5478222070726573657276653D2231223E3C703A63536C64206E61" +
      "6D653D22436F6E74656E7420776974682043617074696F6E223E3C703A737054" +
      "7265653E3C703A6E76477270537050723E3C703A634E7650722069643D223122" +
      "206E616D653D22222F3E3C703A634E76477270537050722F3E3C703A6E765072" +
      "2F3E3C2F703A6E76477270537050723E3C703A677270537050723E3C613A7866" +
      "726D3E3C613A6F666620783D22302220793D2230222F3E3C613A657874206378" +
      "3D2230222063793D2230222F3E3C613A63684F666620783D22302220793D2230" +
      "222F3E3C613A63684578742063783D2230222063793D2230222F3E3C2F613A78" +
      "66726D3E3C2F703A677270537050723E3C703A73703E3C703A6E76537050723E" +
      "3C703A634E7650722069643D223222206E616D653D225469746C652031222F3E" +
      "3C703A634E76537050723E3C613A73704C6F636B73206E6F4772703D2231222F" +
      "3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820747970653D" +
      "227469746C65222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A" +
      "737050723E3C613A7866726D3E3C613A6F666620783D22343537323030222079" +
      "3D22323733303530222F3E3C613A6578742063783D2233303038333133222063" +
      "793D2231313632303530222F3E3C2F613A7866726D3E3C2F703A737050723E3C" +
      "703A7478426F64793E3C613A626F6479507220616E63686F723D2262222F3E3C" +
      "613A6C73745374796C653E3C613A6C766C3170507220616C676E3D226C223E3C" +
      "613A64656652507220737A3D22323030302220623D2231222F3E3C2F613A6C76" +
      "6C317050723E3C2F613A6C73745374796C653E3C613A703E3C613A723E3C613A" +
      "725072206C616E673D22656E2D55532220736D74436C65616E3D2230222F3E3C" +
      "613A743E436C69636B20746F2065646974204D6173746572207469746C652073" +
      "74796C653C2F613A743E3C2F613A723E3C613A656E6450617261525072206C61" +
      "6E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F70" +
      "3A73703E3C703A73703E3C703A6E76537050723E3C703A634E7650722069643D" +
      "223322206E616D653D22436F6E74656E7420506C616365686F6C646572203222" +
      "2F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F4772703D2231" +
      "222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820696478" +
      "3D2231222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050" +
      "723E3C613A7866726D3E3C613A6F666620783D22333537353035302220793D22" +
      "323733303530222F3E3C613A6578742063783D2235313131373530222063793D" +
      "2235383533313133222F3E3C2F613A7866726D3E3C2F703A737050723E3C703A" +
      "7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C653E3C61" +
      "3A6C766C317050723E3C613A64656652507220737A3D2233323030222F3E3C2F" +
      "613A6C766C317050723E3C613A6C766C327050723E3C613A6465665250722073" +
      "7A3D2232383030222F3E3C2F613A6C766C327050723E3C613A6C766C33705072" +
      "3E3C613A64656652507220737A3D2232343030222F3E3C2F613A6C766C337050" +
      "723E3C613A6C766C347050723E3C613A64656652507220737A3D223230303022" +
      "2F3E3C2F613A6C766C347050723E3C613A6C766C357050723E3C613A64656652" +
      "507220737A3D2232303030222F3E3C2F613A6C766C357050723E3C613A6C766C" +
      "367050723E3C613A64656652507220737A3D2232303030222F3E3C2F613A6C76" +
      "6C367050723E3C613A6C766C377050723E3C613A64656652507220737A3D2232" +
      "303030222F3E3C2F613A6C766C377050723E3C613A6C766C387050723E3C613A" +
      "64656652507220737A3D2232303030222F3E3C2F613A6C766C387050723E3C61" +
      "3A6C766C397050723E3C613A64656652507220737A3D2232303030222F3E3C2F" +
      "613A6C766C397050723E3C2F613A6C73745374796C653E3C613A703E3C613A70" +
      "5072206C766C3D2230222F3E3C613A723E3C613A725072206C616E673D22656E" +
      "2D55532220736D74436C65616E3D2230222F3E3C613A743E436C69636B20746F" +
      "2065646974204D61737465722074657874207374796C65733C2F613A743E3C2F" +
      "613A723E3C2F613A703E3C613A703E3C613A705072206C766C3D2231222F3E3C" +
      "613A723E3C613A725072206C616E673D22656E2D55532220736D74436C65616E" +
      "3D2230222F3E3C613A743E5365636F6E64206C6576656C3C2F613A743E3C2F61" +
      "3A723E3C2F613A703E3C613A703E3C613A705072206C766C3D2232222F3E3C61" +
      "3A723E3C613A725072206C616E673D22656E2D55532220736D74436C65616E3D" +
      "2230222F3E3C613A743E5468697264206C6576656C3C2F613A743E3C2F613A72" +
      "3E3C2F613A703E3C613A703E3C613A705072206C766C3D2233222F3E3C613A72" +
      "3E3C613A725072206C616E673D22656E2D55532220736D74436C65616E3D2230" +
      "222F3E3C613A743E466F75727468206C6576656C3C2F613A743E3C2F613A723E" +
      "3C2F613A703E3C613A703E3C613A705072206C766C3D2234222F3E3C613A723E" +
      "3C613A725072206C616E673D22656E2D55532220736D74436C65616E3D223022" +
      "2F3E3C613A743E4669667468206C6576656C3C2F613A743E3C2F613A723E3C61" +
      "3A656E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A703E" +
      "3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E76537050" +
      "723E3C703A634E7650722069643D223422206E616D653D225465787420506C61" +
      "6365686F6C6465722033222F3E3C703A634E76537050723E3C613A73704C6F63" +
      "6B73206E6F4772703D2231222F3E3C2F703A634E76537050723E3C703A6E7650" +
      "723E3C703A706820747970653D22626F64792220737A3D2268616C6622206964" +
      "783D2232222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A7370" +
      "50723E3C613A7866726D3E3C613A6F666620783D223435373230302220793D22" +
      "31343335313030222F3E3C613A6578742063783D223330303833313322206379" +
      "3D2234363931303633222F3E3C2F613A7866726D3E3C2F703A737050723E3C70" +
      "3A7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C653E3C" +
      "613A6C766C31705072206D61724C3D22302220696E64656E743D2230223E3C61" +
      "3A62754E6F6E652F3E3C613A64656652507220737A3D2231343030222F3E3C2F" +
      "613A6C766C317050723E3C613A6C766C32705072206D61724C3D223435373230" +
      "302220696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A64656652" +
      "507220737A3D2231323030222F3E3C2F613A6C766C327050723E3C613A6C766C" +
      "33705072206D61724C3D223931343430302220696E64656E743D2230223E3C61" +
      "3A62754E6F6E652F3E3C613A64656652507220737A3D2231303030222F3E3C2F" +
      "613A6C766C337050723E3C613A6C766C34705072206D61724C3D223133373136" +
      "30302220696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A646566" +
      "52507220737A3D22393030222F3E3C2F613A6C766C347050723E3C613A6C766C" +
      "35705072206D61724C3D22313832383830302220696E64656E743D2230223E3C" +
      "613A62754E6F6E652F3E3C613A64656652507220737A3D22393030222F3E3C2F" +
      "613A6C766C357050723E3C613A6C766C36705072206D61724C3D223232383630" +
      "30302220696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A646566" +
      "52507220737A3D22393030222F3E3C2F613A6C766C367050723E3C613A6C766C" +
      "37705072206D61724C3D22323734333230302220696E64656E743D2230223E3C" +
      "613A62754E6F6E652F3E3C613A64656652507220737A3D22393030222F3E3C2F" +
      "613A6C766C377050723E3C613A6C766C38705072206D61724C3D223332303034" +
      "30302220696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A646566" +
      "52507220737A3D22393030222F3E3C2F613A6C766C387050723E3C613A6C766C" +
      "39705072206D61724C3D22333635373630302220696E64656E743D2230223E3C" +
      "613A62754E6F6E652F3E3C613A64656652507220737A3D22393030222F3E3C2F" +
      "613A6C766C397050723E3C2F613A6C73745374796C653E3C613A703E3C613A70" +
      "5072206C766C3D2230222F3E3C613A723E3C613A725072206C616E673D22656E" +
      "2D55532220736D74436C65616E3D2230222F3E3C613A743E436C69636B20746F" +
      "2065646974204D61737465722074657874207374796C65733C2F613A743E3C2F" +
      "613A723E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A73" +
      "703E3C703A6E76537050723E3C703A634E7650722069643D223522206E616D65" +
      "3D2252656374616E676C652034222F3E3C703A634E76537050723E3C613A7370" +
      "4C6F636B73206E6F4772703D223122206E6F4368616E67654172726F77686561" +
      "64733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A70" +
      "6820747970653D2264742220737A3D2268616C6622206964783D223130222F3E" +
      "3C2F703A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C613A6C" +
      "6E2F3E3C2F703A737050723E3C703A7478426F64793E3C613A626F647950722F" +
      "3E3C613A6C73745374796C653E3C613A6C766C317050723E3C613A6465665250" +
      "722F3E3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C613A70" +
      "3E3C613A656E6450617261525072206C616E673D22656E2D5553222F3E3C2F61" +
      "3A703E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E76" +
      "537050723E3C703A634E7650722069643D223622206E616D653D225265637461" +
      "6E676C652035222F3E3C703A634E76537050723E3C613A73704C6F636B73206E" +
      "6F4772703D223122206E6F4368616E67654172726F7768656164733D2231222F" +
      "3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820747970653D" +
      "226674722220737A3D227175617274657222206964783D223131222F3E3C2F70" +
      "3A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C613A6C6E2F3E" +
      "3C2F703A737050723E3C703A7478426F64793E3C613A626F647950722F3E3C61" +
      "3A6C73745374796C653E3C613A6C766C317050723E3C613A6465665250722F3E" +
      "3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C613A703E3C61" +
      "3A656E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A703E" +
      "3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E76537050" +
      "723E3C703A634E7650722069643D223722206E616D653D2252656374616E676C" +
      "652036222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F4772" +
      "703D223122206E6F4368616E67654172726F7768656164733D2231222F3E3C2F" +
      "703A634E76537050723E3C703A6E7650723E3C703A706820747970653D22736C" +
      "644E756D2220737A3D227175617274657222206964783D223132222F3E3C2F70" +
      "3A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C613A6C6E2F3E" +
      "3C2F703A737050723E3C703A7478426F64793E3C613A626F647950722F3E3C61" +
      "3A6C73745374796C653E3C613A6C766C317050723E3C613A6465665250722F3E" +
      "3C2F613A6C766C317050723E3C2F613A6C73745374796C653E3C613A703E3C61" +
      "3A666C642069643D227B46393841434137412D344444332D364334312D393743" +
      "372D3246324531364242333641317D2220747970653D22736C6964656E756D22" +
      "3E3C613A725072206C616E673D22656E2D5553222F3E3C613A7050722F3E3C61" +
      "3A743EE280B923E280BA3C2F613A743E3C2F613A666C643E3C613A656E645061" +
      "7261525072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478" +
      "426F64793E3C2F703A73703E3C2F703A7370547265653E3C2F703A63536C643E" +
      "3C703A636C724D61704F76723E3C613A6D6173746572436C724D617070696E67" +
      "2F3E3C2F703A636C724D61704F76723E3C2F703A736C644C61796F75743E";

   public static final String SLIDE_LAYOUT_9 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644C" +
      "61796F757420786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E222074797065" +
      "3D227069635478222070726573657276653D2231223E3C703A63536C64206E61" +
      "6D653D225069637475726520776974682043617074696F6E223E3C703A737054" +
      "7265653E3C703A6E76477270537050723E3C703A634E7650722069643D223122" +
      "206E616D653D22222F3E3C703A634E76477270537050722F3E3C703A6E765072" +
      "2F3E3C2F703A6E76477270537050723E3C703A677270537050723E3C613A7866" +
      "726D3E3C613A6F666620783D22302220793D2230222F3E3C613A657874206378" +
      "3D2230222063793D2230222F3E3C613A63684F666620783D22302220793D2230" +
      "222F3E3C613A63684578742063783D2230222063793D2230222F3E3C2F613A78" +
      "66726D3E3C2F703A677270537050723E3C703A73703E3C703A6E76537050723E" +
      "3C703A634E7650722069643D223222206E616D653D225469746C652031222F3E" +
      "3C703A634E76537050723E3C613A73704C6F636B73206E6F4772703D2231222F" +
      "3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820747970653D" +
      "227469746C65222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A" +
      "737050723E3C613A7866726D3E3C613A6F666620783D22313739323238382220" +
      "793D2234383030363030222F3E3C613A6578742063783D223534383634303022" +
      "2063793D22353636373338222F3E3C2F613A7866726D3E3C2F703A737050723E" +
      "3C703A7478426F64793E3C613A626F6479507220616E63686F723D2262222F3E" +
      "3C613A6C73745374796C653E3C613A6C766C3170507220616C676E3D226C223E" +
      "3C613A64656652507220737A3D22323030302220623D2231222F3E3C2F613A6C" +
      "766C317050723E3C2F613A6C73745374796C653E3C613A703E3C613A723E3C61" +
      "3A725072206C616E673D22656E2D55532220736D74436C65616E3D2230222F3E" +
      "3C613A743E436C69636B20746F2065646974204D6173746572207469746C6520" +
      "7374796C653C2F613A743E3C2F613A723E3C613A656E6450617261525072206C" +
      "616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F" +
      "703A73703E3C703A73703E3C703A6E76537050723E3C703A634E765072206964" +
      "3D223322206E616D653D225069637475726520506C616365686F6C6465722032" +
      "222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F4772703D22" +
      "31222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A7068207479" +
      "70653D2270696322206964783D2231222F3E3C2F703A6E7650723E3C2F703A6E" +
      "76537050723E3C703A737050723E3C613A7866726D3E3C613A6F666620783D22" +
      "313739323238382220793D22363132373735222F3E3C613A6578742063783D22" +
      "35343836343030222063793D2234313134383030222F3E3C2F613A7866726D3E" +
      "3C2F703A737050723E3C703A7478426F64793E3C613A626F647950722F3E3C61" +
      "3A6C73745374796C653E3C613A6C766C31705072206D61724C3D22302220696E" +
      "64656E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220737A" +
      "3D2233323030222F3E3C2F613A6C766C317050723E3C613A6C766C3270507220" +
      "6D61724C3D223435373230302220696E64656E743D2230223E3C613A62754E6F" +
      "6E652F3E3C613A64656652507220737A3D2232383030222F3E3C2F613A6C766C" +
      "327050723E3C613A6C766C33705072206D61724C3D223931343430302220696E" +
      "64656E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220737A" +
      "3D2232343030222F3E3C2F613A6C766C337050723E3C613A6C766C3470507220" +
      "6D61724C3D22313337313630302220696E64656E743D2230223E3C613A62754E" +
      "6F6E652F3E3C613A64656652507220737A3D2232303030222F3E3C2F613A6C76" +
      "6C347050723E3C613A6C766C35705072206D61724C3D22313832383830302220" +
      "696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220" +
      "737A3D2232303030222F3E3C2F613A6C766C357050723E3C613A6C766C367050" +
      "72206D61724C3D22323238363030302220696E64656E743D2230223E3C613A62" +
      "754E6F6E652F3E3C613A64656652507220737A3D2232303030222F3E3C2F613A" +
      "6C766C367050723E3C613A6C766C37705072206D61724C3D2232373433323030" +
      "2220696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A6465665250" +
      "7220737A3D2232303030222F3E3C2F613A6C766C377050723E3C613A6C766C38" +
      "705072206D61724C3D22333230303430302220696E64656E743D2230223E3C61" +
      "3A62754E6F6E652F3E3C613A64656652507220737A3D2232303030222F3E3C2F" +
      "613A6C766C387050723E3C613A6C766C39705072206D61724C3D223336353736" +
      "30302220696E64656E743D2230223E3C613A62754E6F6E652F3E3C613A646566" +
      "52507220737A3D2232303030222F3E3C2F613A6C766C397050723E3C2F613A6C" +
      "73745374796C653E3C613A703E3C613A705072206C766C3D2230222F3E3C613A" +
      "656E6450617261525072206C616E673D22656E2D555322206E6F50726F6F663D" +
      "22302220736D74436C65616E3D2230222F3E3C2F613A703E3C2F703A7478426F" +
      "64793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A634E76" +
      "50722069643D223422206E616D653D225465787420506C616365686F6C646572" +
      "2033222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F477270" +
      "3D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820" +
      "747970653D22626F64792220737A3D2268616C6622206964783D2232222F3E3C" +
      "2F703A6E7650723E3C2F703A6E76537050723E3C703A737050723E3C613A7866" +
      "726D3E3C613A6F666620783D22313739323238382220793D2235333637333338" +
      "222F3E3C613A6578742063783D2235343836343030222063793D223830343836" +
      "32222F3E3C2F613A7866726D3E3C2F703A737050723E3C703A7478426F64793E" +
      "3C613A626F647950722F3E3C613A6C73745374796C653E3C613A6C766C317050" +
      "72206D61724C3D22302220696E64656E743D2230223E3C613A62754E6F6E652F" +
      "3E3C613A64656652507220737A3D2231343030222F3E3C2F613A6C766C317050" +
      "723E3C613A6C766C32705072206D61724C3D223435373230302220696E64656E" +
      "743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220737A3D2231" +
      "323030222F3E3C2F613A6C766C327050723E3C613A6C766C33705072206D6172" +
      "4C3D223931343430302220696E64656E743D2230223E3C613A62754E6F6E652F" +
      "3E3C613A64656652507220737A3D2231303030222F3E3C2F613A6C766C337050" +
      "723E3C613A6C766C34705072206D61724C3D22313337313630302220696E6465" +
      "6E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220737A3D22" +
      "393030222F3E3C2F613A6C766C347050723E3C613A6C766C35705072206D6172" +
      "4C3D22313832383830302220696E64656E743D2230223E3C613A62754E6F6E65" +
      "2F3E3C613A64656652507220737A3D22393030222F3E3C2F613A6C766C357050" +
      "723E3C613A6C766C36705072206D61724C3D22323238363030302220696E6465" +
      "6E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220737A3D22" +
      "393030222F3E3C2F613A6C766C367050723E3C613A6C766C37705072206D6172" +
      "4C3D22323734333230302220696E64656E743D2230223E3C613A62754E6F6E65" +
      "2F3E3C613A64656652507220737A3D22393030222F3E3C2F613A6C766C377050" +
      "723E3C613A6C766C38705072206D61724C3D22333230303430302220696E6465" +
      "6E743D2230223E3C613A62754E6F6E652F3E3C613A64656652507220737A3D22" +
      "393030222F3E3C2F613A6C766C387050723E3C613A6C766C39705072206D6172" +
      "4C3D22333635373630302220696E64656E743D2230223E3C613A62754E6F6E65" +
      "2F3E3C613A64656652507220737A3D22393030222F3E3C2F613A6C766C397050" +
      "723E3C2F613A6C73745374796C653E3C613A703E3C613A705072206C766C3D22" +
      "30222F3E3C613A723E3C613A725072206C616E673D22656E2D55532220736D74" +
      "436C65616E3D2230222F3E3C613A743E436C69636B20746F2065646974204D61" +
      "737465722074657874207374796C65733C2F613A743E3C2F613A723E3C2F613A" +
      "703E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E7653" +
      "7050723E3C703A634E7650722069643D223522206E616D653D2252656374616E" +
      "676C652034222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F" +
      "4772703D223122206E6F4368616E67654172726F7768656164733D2231222F3E" +
      "3C2F703A634E76537050723E3C703A6E7650723E3C703A706820747970653D22" +
      "64742220737A3D2268616C6622206964783D223130222F3E3C2F703A6E765072" +
      "3E3C2F703A6E76537050723E3C703A737050723E3C613A6C6E2F3E3C2F703A73" +
      "7050723E3C703A7478426F64793E3C613A626F647950722F3E3C613A6C737453" +
      "74796C653E3C613A6C766C317050723E3C613A6465665250722F3E3C2F613A6C" +
      "766C317050723E3C2F613A6C73745374796C653E3C613A703E3C613A656E6450" +
      "617261525072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A74" +
      "78426F64793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A" +
      "634E7650722069643D223622206E616D653D2252656374616E676C652035222F" +
      "3E3C703A634E76537050723E3C613A73704C6F636B73206E6F4772703D223122" +
      "206E6F4368616E67654172726F7768656164733D2231222F3E3C2F703A634E76" +
      "537050723E3C703A6E7650723E3C703A706820747970653D226674722220737A" +
      "3D227175617274657222206964783D223131222F3E3C2F703A6E7650723E3C2F" +
      "703A6E76537050723E3C703A737050723E3C613A6C6E2F3E3C2F703A73705072" +
      "3E3C703A7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C" +
      "653E3C613A6C766C317050723E3C613A6465665250722F3E3C2F613A6C766C31" +
      "7050723E3C2F613A6C73745374796C653E3C613A703E3C613A656E6450617261" +
      "525072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F" +
      "64793E3C2F703A73703E3C703A73703E3C703A6E76537050723E3C703A634E76" +
      "50722069643D223722206E616D653D2252656374616E676C652036222F3E3C70" +
      "3A634E76537050723E3C613A73704C6F636B73206E6F4772703D223122206E6F" +
      "4368616E67654172726F7768656164733D2231222F3E3C2F703A634E76537050" +
      "723E3C703A6E7650723E3C703A706820747970653D22736C644E756D2220737A" +
      "3D227175617274657222206964783D223132222F3E3C2F703A6E7650723E3C2F" +
      "703A6E76537050723E3C703A737050723E3C613A6C6E2F3E3C2F703A73705072" +
      "3E3C703A7478426F64793E3C613A626F647950722F3E3C613A6C73745374796C" +
      "653E3C613A6C766C317050723E3C613A6465665250722F3E3C2F613A6C766C31" +
      "7050723E3C2F613A6C73745374796C653E3C613A703E3C613A666C642069643D" +
      "227B42303830343246462D424141352D313634372D384631452D414632364643" +
      "3039353944467D2220747970653D22736C6964656E756D223E3C613A72507220" +
      "6C616E673D22656E2D5553222F3E3C613A7050722F3E3C613A743EE280B923E2" +
      "80BA3C2F613A743E3C2F613A666C643E3C613A656E6450617261525072206C61" +
      "6E673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F70" +
      "3A73703E3C2F703A7370547265653E3C2F703A63536C643E3C703A636C724D61" +
      "704F76723E3C613A6D6173746572436C724D617070696E672F3E3C2F703A636C" +
      "724D61704F76723E3C2F703A736C644C61796F75743E";

   public static final String SLIDE_MASTER_1_RELS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C52656C617469" +
      "6F6E736869707320786D6C6E733D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F7061636B6167652F323030362F7265" +
      "6C6174696F6E7368697073223E3C52656C6174696F6E736869702049643D2272" +
      "496431312220547970653D22687474703A2F2F736368656D61732E6F70656E78" +
      "6D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F323030" +
      "362F72656C6174696F6E73686970732F736C6964654C61796F75742220546172" +
      "6765743D222E2E2F736C6964654C61796F7574732F736C6964654C61796F7574" +
      "31312E786D6C222F3E3C52656C6174696F6E736869702049643D227249643132" +
      "2220547970653D22687474703A2F2F736368656D61732E6F70656E786D6C666F" +
      "726D6174732E6F72672F6F6666696365446F63756D656E742F323030362F7265" +
      "6C6174696F6E73686970732F7468656D6522205461726765743D222E2E2F7468" +
      "656D652F7468656D65312E786D6C222F3E3C52656C6174696F6E736869702049" +
      "643D22724964312220547970653D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732F736C6964654C61796F75742220" +
      "5461726765743D222E2E2F736C6964654C61796F7574732F736C6964654C6179" +
      "6F7574312E786D6C222F3E3C52656C6174696F6E736869702049643D22724964" +
      "322220547970653D22687474703A2F2F736368656D61732E6F70656E786D6C66" +
      "6F726D6174732E6F72672F6F6666696365446F63756D656E742F323030362F72" +
      "656C6174696F6E73686970732F736C6964654C61796F75742220546172676574" +
      "3D222E2E2F736C6964654C61796F7574732F736C6964654C61796F7574322E78" +
      "6D6C222F3E3C52656C6174696F6E736869702049643D22724964332220547970" +
      "653D22687474703A2F2F736368656D61732E6F70656E786D6C666F726D617473" +
      "2E6F72672F6F6666696365446F63756D656E742F323030362F72656C6174696F" +
      "6E73686970732F736C6964654C61796F757422205461726765743D222E2E2F73" +
      "6C6964654C61796F7574732F736C6964654C61796F7574332E786D6C222F3E3C" +
      "52656C6174696F6E736869702049643D22724964342220547970653D22687474" +
      "703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F6F" +
      "6666696365446F63756D656E742F323030362F72656C6174696F6E7368697073" +
      "2F736C6964654C61796F757422205461726765743D222E2E2F736C6964654C61" +
      "796F7574732F736C6964654C61796F7574342E786D6C222F3E3C52656C617469" +
      "6F6E736869702049643D22724964352220547970653D22687474703A2F2F7363" +
      "68656D61732E6F70656E786D6C666F726D6174732E6F72672F6F666669636544" +
      "6F63756D656E742F323030362F72656C6174696F6E73686970732F736C696465" +
      "4C61796F757422205461726765743D222E2E2F736C6964654C61796F7574732F" +
      "736C6964654C61796F7574352E786D6C222F3E3C52656C6174696F6E73686970" +
      "2049643D22724964362220547970653D22687474703A2F2F736368656D61732E" +
      "6F70656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E" +
      "742F323030362F72656C6174696F6E73686970732F736C6964654C61796F7574" +
      "22205461726765743D222E2E2F736C6964654C61796F7574732F736C6964654C" +
      "61796F7574362E786D6C222F3E3C52656C6174696F6E736869702049643D2272" +
      "4964372220547970653D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F32303036" +
      "2F72656C6174696F6E73686970732F736C6964654C61796F7574222054617267" +
      "65743D222E2E2F736C6964654C61796F7574732F736C6964654C61796F757437" +
      "2E786D6C222F3E3C52656C6174696F6E736869702049643D2272496438222054" +
      "7970653D22687474703A2F2F736368656D61732E6F70656E786D6C666F726D61" +
      "74732E6F72672F6F6666696365446F63756D656E742F323030362F72656C6174" +
      "696F6E73686970732F736C6964654C61796F757422205461726765743D222E2E" +
      "2F736C6964654C61796F7574732F736C6964654C61796F7574382E786D6C222F" +
      "3E3C52656C6174696F6E736869702049643D22724964392220547970653D2268" +
      "7474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F7267" +
      "2F6F6666696365446F63756D656E742F323030362F72656C6174696F6E736869" +
      "70732F736C6964654C61796F757422205461726765743D222E2E2F736C696465" +
      "4C61796F7574732F736C6964654C61796F7574392E786D6C222F3E3C52656C61" +
      "74696F6E736869702049643D2272496431302220547970653D22687474703A2F" +
      "2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F6F666669" +
      "6365446F63756D656E742F323030362F72656C6174696F6E73686970732F736C" +
      "6964654C61796F757422205461726765743D222E2E2F736C6964654C61796F75" +
      "74732F736C6964654C61796F757431302E786D6C222F3E3C2F52656C6174696F" +
      "6E73686970733E";

   public static final String SLIDE_MASTER_1 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A736C644D" +
      "617374657220786D6C6E733A613D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F" +
      "6D61696E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70" +
      "656E786D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F" +
      "323030362F72656C6174696F6E73686970732220786D6C6E733A6D633D226874" +
      "74703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F" +
      "6D61726B75702D636F6D7061746962696C6974792F323030362220786D6C6E73" +
      "3A6D763D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D" +
      "61633A766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265" +
      "7365727665417474726962757465733D226D763A2A2220786D6C6E733A703D22" +
      "687474703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72" +
      "672F70726573656E746174696F6E6D6C2F323030362F6D61696E223E3C703A63" +
      "536C643E3C703A62673E3C703A626750723E3C613A736F6C696446696C6C3E3C" +
      "613A73726762436C722076616C3D22434346464646222F3E3C2F613A736F6C69" +
      "6446696C6C3E3C613A6566666563744C73742F3E3C2F703A626750723E3C2F70" +
      "3A62673E3C703A7370547265653E3C703A6E76477270537050723E3C703A634E" +
      "7650722069643D223122206E616D653D22222F3E3C703A634E76477270537050" +
      "722F3E3C703A6E7650722F3E3C2F703A6E76477270537050723E3C703A677270" +
      "537050723E3C613A7866726D3E3C613A6F666620783D22302220793D2230222F" +
      "3E3C613A6578742063783D2230222063793D2230222F3E3C613A63684F666620" +
      "783D22302220793D2230222F3E3C613A63684578742063783D2230222063793D" +
      "2230222F3E3C2F613A7866726D3E3C2F703A677270537050723E3C703A73703E" +
      "3C703A6E76537050723E3C703A634E7650722069643D223130323622206E616D" +
      "653D2252656374616E676C652032222F3E3C703A634E76537050723E3C613A73" +
      "704C6F636B73206E6F4772703D223122206E6F4368616E67654172726F776865" +
      "6164733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A" +
      "706820747970653D227469746C65222F3E3C2F703A6E7650723E3C2F703A6E76" +
      "537050723E3C703A737050722062774D6F64653D226175746F223E3C613A7866" +
      "726D3E3C613A6F666620783D223638353830302220793D22363039363030222F" +
      "3E3C613A6578742063783D2237373732343030222063793D2231313433303030" +
      "222F3E3C2F613A7866726D3E3C613A7072737447656F6D20707273743D227265" +
      "6374223E3C613A61764C73742F3E3C2F613A7072737447656F6D3E3C613A6E6F" +
      "46696C6C2F3E3C613A6C6E20773D2239353235223E3C613A6E6F46696C6C2F3E" +
      "3C613A6D69746572206C696D3D22383030303030222F3E3C613A68656164456E" +
      "642F3E3C613A7461696C456E642F3E3C2F613A6C6E3E3C2F703A737050723E3C" +
      "703A7478426F64793E3C613A626F6479507220766572743D22686F727A222077" +
      "7261703D2273717561726522206C496E733D223931343430222074496E733D22" +
      "3435373230222072496E733D223931343430222062496E733D22343537323022" +
      "206E756D436F6C3D22312220616E63686F723D226374722220616E63686F7243" +
      "74723D22302220636F6D7061744C6E5370633D2231223E3C613A707273745478" +
      "5761727020707273743D22746578744E6F5368617065223E3C613A61764C7374" +
      "2F3E3C2F613A707273745478576172703E3C2F613A626F647950723E3C613A6C" +
      "73745374796C652F3E3C613A703E3C613A705072206C766C3D2230222F3E3C61" +
      "3A723E3C613A725072206C616E673D22656E2D5553222F3E3C613A743E436C69" +
      "636B20746F2065646974204D6173746572207469746C65207374796C653C2F61" +
      "3A743E3C2F613A723E3C2F613A703E3C2F703A7478426F64793E3C2F703A7370" +
      "3E3C703A73703E3C703A6E76537050723E3C703A634E7650722069643D223130" +
      "323722206E616D653D2252656374616E676C652033222F3E3C703A634E765370" +
      "50723E3C613A73704C6F636B73206E6F4772703D223122206E6F4368616E6765" +
      "4172726F7768656164733D2231222F3E3C2F703A634E76537050723E3C703A6E" +
      "7650723E3C703A706820747970653D22626F647922206964783D2231222F3E3C" +
      "2F703A6E7650723E3C2F703A6E76537050723E3C703A737050722062774D6F64" +
      "653D226175746F223E3C613A7866726D3E3C613A6F666620783D223638353830" +
      "302220793D2231393831323030222F3E3C613A6578742063783D223737373234" +
      "3030222063793D2234313134383030222F3E3C2F613A7866726D3E3C613A7072" +
      "737447656F6D20707273743D2272656374223E3C613A61764C73742F3E3C2F61" +
      "3A7072737447656F6D3E3C613A6E6F46696C6C2F3E3C613A6C6E20773D223935" +
      "3235223E3C613A6E6F46696C6C2F3E3C613A6D69746572206C696D3D22383030" +
      "303030222F3E3C613A68656164456E642F3E3C613A7461696C456E642F3E3C2F" +
      "613A6C6E3E3C2F703A737050723E3C703A7478426F64793E3C613A626F647950" +
      "7220766572743D22686F727A2220777261703D2273717561726522206C496E73" +
      "3D223931343430222074496E733D223435373230222072496E733D2239313434" +
      "30222062496E733D22343537323022206E756D436F6C3D22312220616E63686F" +
      "723D22742220616E63686F724374723D22302220636F6D7061744C6E5370633D" +
      "2231223E3C613A7072737454785761727020707273743D22746578744E6F5368" +
      "617065223E3C613A61764C73742F3E3C2F613A707273745478576172703E3C2F" +
      "613A626F647950723E3C613A6C73745374796C652F3E3C613A703E3C613A7050" +
      "72206C766C3D2230222F3E3C613A723E3C613A725072206C616E673D22656E2D" +
      "5553222F3E3C613A743E436C69636B20746F2065646974204D61737465722074" +
      "657874207374796C65733C2F613A743E3C2F613A723E3C2F613A703E3C613A70" +
      "3E3C613A705072206C766C3D2231222F3E3C613A723E3C613A725072206C616E" +
      "673D22656E2D5553222F3E3C613A743E5365636F6E64206C6576656C3C2F613A" +
      "743E3C2F613A723E3C2F613A703E3C613A703E3C613A705072206C766C3D2232" +
      "222F3E3C613A723E3C613A725072206C616E673D22656E2D5553222F3E3C613A" +
      "743E5468697264206C6576656C3C2F613A743E3C2F613A723E3C2F613A703E3C" +
      "613A703E3C613A705072206C766C3D2233222F3E3C613A723E3C613A72507220" +
      "6C616E673D22656E2D5553222F3E3C613A743E466F75727468206C6576656C3C" +
      "2F613A743E3C2F613A723E3C2F613A703E3C613A703E3C613A705072206C766C" +
      "3D2234222F3E3C613A723E3C613A725072206C616E673D22656E2D5553222F3E" +
      "3C613A743E4669667468206C6576656C3C2F613A743E3C2F613A723E3C2F613A" +
      "703E3C2F703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E7653" +
      "7050723E3C703A634E7650722069643D223130323822206E616D653D22526563" +
      "74616E676C652034222F3E3C703A634E76537050723E3C613A73704C6F636B73" +
      "206E6F4772703D223122206E6F4368616E67654172726F7768656164733D2231" +
      "222F3E3C2F703A634E76537050723E3C703A6E7650723E3C703A706820747970" +
      "653D2264742220737A3D2268616C6622206964783D2232222F3E3C2F703A6E76" +
      "50723E3C2F703A6E76537050723E3C703A737050722062774D6F64653D226175" +
      "746F223E3C613A7866726D3E3C613A6F666620783D223638353830302220793D" +
      "2236323438343030222F3E3C613A6578742063783D2231393035303030222063" +
      "793D22343537323030222F3E3C2F613A7866726D3E3C613A7072737447656F6D" +
      "20707273743D2272656374223E3C613A61764C73742F3E3C2F613A7072737447" +
      "656F6D3E3C613A6E6F46696C6C2F3E3C613A6C6E20773D2239353235223E3C61" +
      "3A6E6F46696C6C2F3E3C613A6D69746572206C696D3D22383030303030222F3E" +
      "3C613A68656164456E642F3E3C613A7461696C456E642F3E3C2F613A6C6E3E3C" +
      "613A6566666563744C73742F3E3C2F703A737050723E3C703A7478426F64793E" +
      "3C613A626F6479507220766572743D22686F727A2220777261703D2273717561" +
      "726522206C496E733D223931343430222074496E733D22343537323022207249" +
      "6E733D223931343430222062496E733D22343537323022206E756D436F6C3D22" +
      "312220616E63686F723D22742220616E63686F724374723D22302220636F6D70" +
      "61744C6E5370633D2231223E3C613A7072737454785761727020707273743D22" +
      "746578744E6F5368617065223E3C613A61764C73742F3E3C2F613A7072737454" +
      "78576172703E3C2F613A626F647950723E3C613A6C73745374796C653E3C613A" +
      "6C766C317050723E3C613A6465665250723E3C613A6C6174696E207479706566" +
      "6163653D2254696D6573204E657720526F6D616E2220706974636846616D696C" +
      "793D22312220636861727365743D2230222F3E3C2F613A6465665250723E3C2F" +
      "613A6C766C317050723E3C2F613A6C73745374796C653E3C613A703E3C613A65" +
      "6E6450617261525072206C616E673D22656E2D5553222F3E3C2F613A703E3C2F" +
      "703A7478426F64793E3C2F703A73703E3C703A73703E3C703A6E76537050723E" +
      "3C703A634E7650722069643D223130323922206E616D653D2252656374616E67" +
      "6C652035222F3E3C703A634E76537050723E3C613A73704C6F636B73206E6F47" +
      "72703D223122206E6F4368616E67654172726F7768656164733D2231222F3E3C" +
      "2F703A634E76537050723E3C703A6E7650723E3C703A706820747970653D2266" +
      "74722220737A3D227175617274657222206964783D2233222F3E3C2F703A6E76" +
      "50723E3C2F703A6E76537050723E3C703A737050722062774D6F64653D226175" +
      "746F223E3C613A7866726D3E3C613A6F666620783D2233313234323030222079" +
      "3D2236323438343030222F3E3C613A6578742063783D22323839353630302220" +
      "63793D22343537323030222F3E3C2F613A7866726D3E3C613A7072737447656F" +
      "6D20707273743D2272656374223E3C613A61764C73742F3E3C2F613A70727374" +
      "47656F6D3E3C613A6E6F46696C6C2F3E3C613A6C6E20773D2239353235223E3C" +
      "613A6E6F46696C6C2F3E3C613A6D69746572206C696D3D22383030303030222F" +
      "3E3C613A68656164456E642F3E3C613A7461696C456E642F3E3C2F613A6C6E3E" +
      "3C613A6566666563744C73742F3E3C2F703A737050723E3C703A7478426F6479" +
      "3E3C613A626F6479507220766572743D22686F727A2220777261703D22737175" +
      "61726522206C496E733D223931343430222074496E733D223435373230222072" +
      "496E733D223931343430222062496E733D22343537323022206E756D436F6C3D" +
      "22312220616E63686F723D22742220616E63686F724374723D22302220636F6D" +
      "7061744C6E5370633D2231223E3C613A7072737454785761727020707273743D" +
      "22746578744E6F5368617065223E3C613A61764C73742F3E3C2F613A70727374" +
      "5478576172703E3C2F613A626F647950723E3C613A6C73745374796C653E3C61" +
      "3A6C766C3170507220616C676E3D22637472223E3C613A6465665250723E3C61" +
      "3A6C6174696E2074797065666163653D2254696D6573204E657720526F6D616E" +
      "2220706974636846616D696C793D22312220636861727365743D2230222F3E3C" +
      "2F613A6465665250723E3C2F613A6C766C317050723E3C2F613A6C7374537479" +
      "6C653E3C613A703E3C613A656E6450617261525072206C616E673D22656E2D55" +
      "53222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A73703E3C703A73" +
      "703E3C703A6E76537050723E3C703A634E7650722069643D223130333022206E" +
      "616D653D2252656374616E676C652036222F3E3C703A634E76537050723E3C61" +
      "3A73704C6F636B73206E6F4772703D223122206E6F4368616E67654172726F77" +
      "68656164733D2231222F3E3C2F703A634E76537050723E3C703A6E7650723E3C" +
      "703A706820747970653D22736C644E756D2220737A3D22717561727465722220" +
      "6964783D2234222F3E3C2F703A6E7650723E3C2F703A6E76537050723E3C703A" +
      "737050722062774D6F64653D226175746F223E3C613A7866726D3E3C613A6F66" +
      "6620783D22363535333230302220793D2236323438343030222F3E3C613A6578" +
      "742063783D2231393035303030222063793D22343537323030222F3E3C2F613A" +
      "7866726D3E3C613A7072737447656F6D20707273743D2272656374223E3C613A" +
      "61764C73742F3E3C2F613A7072737447656F6D3E3C613A6E6F46696C6C2F3E3C" +
      "613A6C6E20773D2239353235223E3C613A6E6F46696C6C2F3E3C613A6D697465" +
      "72206C696D3D22383030303030222F3E3C613A68656164456E642F3E3C613A74" +
      "61696C456E642F3E3C2F613A6C6E3E3C613A6566666563744C73742F3E3C2F70" +
      "3A737050723E3C703A7478426F64793E3C613A626F6479507220766572743D22" +
      "686F727A2220777261703D2273717561726522206C496E733D22393134343022" +
      "2074496E733D223435373230222072496E733D223931343430222062496E733D" +
      "22343537323022206E756D436F6C3D22312220616E63686F723D22742220616E" +
      "63686F724374723D22302220636F6D7061744C6E5370633D2231223E3C613A70" +
      "72737454785761727020707273743D22746578744E6F5368617065223E3C613A" +
      "61764C73742F3E3C2F613A707273745478576172703E3C2F613A626F64795072" +
      "3E3C613A6C73745374796C653E3C613A6C766C3170507220616C676E3D227222" +
      "3E3C613A6465665250723E3C613A6C6174696E2074797065666163653D225469" +
      "6D6573204E657720526F6D616E2220706974636846616D696C793D2231222063" +
      "6861727365743D2230222F3E3C2F613A6465665250723E3C2F613A6C766C3170" +
      "50723E3C2F613A6C73745374796C653E3C613A703E3C613A666C642069643D22" +
      "7B37383435434544382D443235322D413934332D423542412D42444539454239" +
      "38434141307D2220747970653D22736C6964656E756D223E3C613A725072206C" +
      "616E673D22656E2D5553222F3E3C613A7050722F3E3C613A743EE280B923E280" +
      "BA3C2F613A743E3C2F613A666C643E3C613A656E6450617261525072206C616E" +
      "673D22656E2D5553222F3E3C2F613A703E3C2F703A7478426F64793E3C2F703A" +
      "73703E3C2F703A7370547265653E3C2F703A63536C643E3C703A636C724D6170" +
      "206267313D226C743122207478313D22646B3122206267323D226C7432222074" +
      "78323D22646B322220616363656E74313D22616363656E74312220616363656E" +
      "74323D22616363656E74322220616363656E74333D22616363656E7433222061" +
      "6363656E74343D22616363656E74342220616363656E74353D22616363656E74" +
      "352220616363656E74363D22616363656E74362220686C696E6B3D22686C696E" +
      "6B2220666F6C486C696E6B3D22666F6C486C696E6B222F3E3C703A736C644C61" +
      "796F757449644C73743E3C703A736C644C61796F757449642069643D22323134" +
      "373438333634392220723A69643D2272496431222F3E3C703A736C644C61796F" +
      "757449642069643D22323134373438333635302220723A69643D227249643222" +
      "2F3E3C703A736C644C61796F757449642069643D223231343734383336353122" +
      "20723A69643D2272496433222F3E3C703A736C644C61796F757449642069643D" +
      "22323134373438333635322220723A69643D2272496434222F3E3C703A736C64" +
      "4C61796F757449642069643D22323134373438333635332220723A69643D2272" +
      "496435222F3E3C703A736C644C61796F757449642069643D2232313437343833" +
      "3635342220723A69643D2272496436222F3E3C703A736C644C61796F75744964" +
      "2069643D22323134373438333635352220723A69643D2272496437222F3E3C70" +
      "3A736C644C61796F757449642069643D22323134373438333635362220723A69" +
      "643D2272496438222F3E3C703A736C644C61796F757449642069643D22323134" +
      "373438333635372220723A69643D2272496439222F3E3C703A736C644C61796F" +
      "757449642069643D22323134373438333635382220723A69643D227249643130" +
      "222F3E3C703A736C644C61796F757449642069643D2232313437343833363539" +
      "2220723A69643D227249643131222F3E3C2F703A736C644C61796F757449644C" +
      "73743E3C703A74785374796C65733E3C703A7469746C655374796C653E3C613A" +
      "6C766C3170507220616C676E3D22637472222072746C3D2230222065614C6E42" +
      "726B3D22302220666F6E74416C676E3D2262617365222068616E67696E675075" +
      "6E63743D2230223E3C613A7370634265663E3C613A7370635063742076616C3D" +
      "2230222F3E3C2F613A7370634265663E3C613A7370634166743E3C613A737063" +
      "5063742076616C3D2230222F3E3C2F613A7370634166743E3C613A6465665250" +
      "7220737A3D2234343030223E3C613A736F6C696446696C6C3E3C613A73636865" +
      "6D65436C722076616C3D22747832222F3E3C2F613A736F6C696446696C6C3E3C" +
      "613A6C6174696E2074797065666163653D2254696D6573204E657720526F6D61" +
      "6E2220706974636846616D696C793D22312220636861727365743D2230222F3E" +
      "3C613A65612074797065666163653D222B6D6A2D6561222F3E3C613A63732074" +
      "797065666163653D222B6D6A2D6373222F3E3C2F613A6465665250723E3C2F61" +
      "3A6C766C317050723E3C613A6C766C3270507220616C676E3D22637472222072" +
      "746C3D2230222065614C6E42726B3D22302220666F6E74416C676E3D22626173" +
      "65222068616E67696E6750756E63743D2230223E3C613A7370634265663E3C61" +
      "3A7370635063742076616C3D2230222F3E3C2F613A7370634265663E3C613A73" +
      "70634166743E3C613A7370635063742076616C3D2230222F3E3C2F613A737063" +
      "4166743E3C613A64656652507220737A3D2234343030223E3C613A736F6C6964" +
      "46696C6C3E3C613A736368656D65436C722076616C3D22747832222F3E3C2F61" +
      "3A736F6C696446696C6C3E3C613A6C6174696E2074797065666163653D225469" +
      "6D6573204E657720526F6D616E2220706974636846616D696C793D2231222063" +
      "6861727365743D2230222F3E3C2F613A6465665250723E3C2F613A6C766C3270" +
      "50723E3C613A6C766C3370507220616C676E3D22637472222072746C3D223022" +
      "2065614C6E42726B3D22302220666F6E74416C676E3D2262617365222068616E" +
      "67696E6750756E63743D2230223E3C613A7370634265663E3C613A7370635063" +
      "742076616C3D2230222F3E3C2F613A7370634265663E3C613A7370634166743E" +
      "3C613A7370635063742076616C3D2230222F3E3C2F613A7370634166743E3C61" +
      "3A64656652507220737A3D2234343030223E3C613A736F6C696446696C6C3E3C" +
      "613A736368656D65436C722076616C3D22747832222F3E3C2F613A736F6C6964" +
      "46696C6C3E3C613A6C6174696E2074797065666163653D2254696D6573204E65" +
      "7720526F6D616E2220706974636846616D696C793D2231222063686172736574" +
      "3D2230222F3E3C2F613A6465665250723E3C2F613A6C766C337050723E3C613A" +
      "6C766C3470507220616C676E3D22637472222072746C3D2230222065614C6E42" +
      "726B3D22302220666F6E74416C676E3D2262617365222068616E67696E675075" +
      "6E63743D2230223E3C613A7370634265663E3C613A7370635063742076616C3D" +
      "2230222F3E3C2F613A7370634265663E3C613A7370634166743E3C613A737063" +
      "5063742076616C3D2230222F3E3C2F613A7370634166743E3C613A6465665250" +
      "7220737A3D2234343030223E3C613A736F6C696446696C6C3E3C613A73636865" +
      "6D65436C722076616C3D22747832222F3E3C2F613A736F6C696446696C6C3E3C" +
      "613A6C6174696E2074797065666163653D2254696D6573204E657720526F6D61" +
      "6E2220706974636846616D696C793D22312220636861727365743D2230222F3E" +
      "3C2F613A6465665250723E3C2F613A6C766C347050723E3C613A6C766C357050" +
      "7220616C676E3D22637472222072746C3D2230222065614C6E42726B3D223022" +
      "20666F6E74416C676E3D2262617365222068616E67696E6750756E63743D2230" +
      "223E3C613A7370634265663E3C613A7370635063742076616C3D2230222F3E3C" +
      "2F613A7370634265663E3C613A7370634166743E3C613A737063506374207661" +
      "6C3D2230222F3E3C2F613A7370634166743E3C613A64656652507220737A3D22" +
      "34343030223E3C613A736F6C696446696C6C3E3C613A736368656D65436C7220" +
      "76616C3D22747832222F3E3C2F613A736F6C696446696C6C3E3C613A6C617469" +
      "6E2074797065666163653D2254696D6573204E657720526F6D616E2220706974" +
      "636846616D696C793D22312220636861727365743D2230222F3E3C2F613A6465" +
      "665250723E3C2F613A6C766C357050723E3C613A6C766C36705072206D61724C" +
      "3D223435373230302220616C676E3D22637472222072746C3D2230222065614C" +
      "6E42726B3D22302220666F6E74416C676E3D2262617365222068616E67696E67" +
      "50756E63743D2230223E3C613A7370634265663E3C613A737063506374207661" +
      "6C3D2230222F3E3C2F613A7370634265663E3C613A7370634166743E3C613A73" +
      "70635063742076616C3D2230222F3E3C2F613A7370634166743E3C613A646566" +
      "52507220737A3D2234343030223E3C613A736F6C696446696C6C3E3C613A7363" +
      "68656D65436C722076616C3D22747832222F3E3C2F613A736F6C696446696C6C" +
      "3E3C613A6C6174696E2074797065666163653D2254696D657322207069746368" +
      "46616D696C793D2238342220636861727365743D2230222F3E3C2F613A646566" +
      "5250723E3C2F613A6C766C367050723E3C613A6C766C37705072206D61724C3D" +
      "223931343430302220616C676E3D22637472222072746C3D2230222065614C6E" +
      "42726B3D22302220666F6E74416C676E3D2262617365222068616E67696E6750" +
      "756E63743D2230223E3C613A7370634265663E3C613A7370635063742076616C" +
      "3D2230222F3E3C2F613A7370634265663E3C613A7370634166743E3C613A7370" +
      "635063742076616C3D2230222F3E3C2F613A7370634166743E3C613A64656652" +
      "507220737A3D2234343030223E3C613A736F6C696446696C6C3E3C613A736368" +
      "656D65436C722076616C3D22747832222F3E3C2F613A736F6C696446696C6C3E" +
      "3C613A6C6174696E2074797065666163653D2254696D65732220706974636846" +
      "616D696C793D2238342220636861727365743D2230222F3E3C2F613A64656652" +
      "50723E3C2F613A6C766C377050723E3C613A6C766C38705072206D61724C3D22" +
      "313337313630302220616C676E3D22637472222072746C3D2230222065614C6E" +
      "42726B3D22302220666F6E74416C676E3D2262617365222068616E67696E6750" +
      "756E63743D2230223E3C613A7370634265663E3C613A7370635063742076616C" +
      "3D2230222F3E3C2F613A7370634265663E3C613A7370634166743E3C613A7370" +
      "635063742076616C3D2230222F3E3C2F613A7370634166743E3C613A64656652" +
      "507220737A3D2234343030223E3C613A736F6C696446696C6C3E3C613A736368" +
      "656D65436C722076616C3D22747832222F3E3C2F613A736F6C696446696C6C3E" +
      "3C613A6C6174696E2074797065666163653D2254696D65732220706974636846" +
      "616D696C793D2238342220636861727365743D2230222F3E3C2F613A64656652" +
      "50723E3C2F613A6C766C387050723E3C613A6C766C39705072206D61724C3D22" +
      "313832383830302220616C676E3D22637472222072746C3D2230222065614C6E" +
      "42726B3D22302220666F6E74416C676E3D2262617365222068616E67696E6750" +
      "756E63743D2230223E3C613A7370634265663E3C613A7370635063742076616C" +
      "3D2230222F3E3C2F613A7370634265663E3C613A7370634166743E3C613A7370" +
      "635063742076616C3D2230222F3E3C2F613A7370634166743E3C613A64656652" +
      "507220737A3D2234343030223E3C613A736F6C696446696C6C3E3C613A736368" +
      "656D65436C722076616C3D22747832222F3E3C2F613A736F6C696446696C6C3E" +
      "3C613A6C6174696E2074797065666163653D2254696D65732220706974636846" +
      "616D696C793D2238342220636861727365743D2230222F3E3C2F613A64656652" +
      "50723E3C2F613A6C766C397050723E3C2F703A7469746C655374796C653E3C70" +
      "3A626F64795374796C653E3C613A6C766C31705072206D61724C3D2233343239" +
      "30302220696E64656E743D222D3334323930302220616C676E3D226C22207274" +
      "6C3D2230222065614C6E42726B3D22302220666F6E74416C676E3D2262617365" +
      "222068616E67696E6750756E63743D2230223E3C613A7370634265663E3C613A" +
      "7370635063742076616C3D223230303030222F3E3C2F613A7370634265663E3C" +
      "613A7370634166743E3C613A7370635063742076616C3D2230222F3E3C2F613A" +
      "7370634166743E3C613A62754368617220636861723D22E280A2222F3E3C613A" +
      "64656652507220737A3D2233323030223E3C613A736F6C696446696C6C3E3C61" +
      "3A736368656D65436C722076616C3D22747831222F3E3C2F613A736F6C696446" +
      "696C6C3E3C613A6C6174696E2074797065666163653D2254696D6573204E6577" +
      "20526F6D616E2220706974636846616D696C793D22312220636861727365743D" +
      "2230222F3E3C613A65612074797065666163653D222B6D6E2D6561222F3E3C61" +
      "3A63732074797065666163653D222B6D6E2D6373222F3E3C2F613A6465665250" +
      "723E3C2F613A6C766C317050723E3C613A6C766C32705072206D61724C3D2237" +
      "34323935302220696E64656E743D222D3238353735302220616C676E3D226C22" +
      "2072746C3D2230222065614C6E42726B3D22302220666F6E74416C676E3D2262" +
      "617365222068616E67696E6750756E63743D2230223E3C613A7370634265663E" +
      "3C613A7370635063742076616C3D223230303030222F3E3C2F613A7370634265" +
      "663E3C613A7370634166743E3C613A7370635063742076616C3D2230222F3E3C" +
      "2F613A7370634166743E3C613A62754368617220636861723D22E28093222F3E" +
      "3C613A64656652507220737A3D2232383030223E3C613A736F6C696446696C6C" +
      "3E3C613A736368656D65436C722076616C3D22747831222F3E3C2F613A736F6C" +
      "696446696C6C3E3C613A6C6174696E2074797065666163653D2254696D657320" +
      "4E657720526F6D616E2220706974636846616D696C793D223122206368617273" +
      "65743D2230222F3E3C613A65612074797065666163653D22EFBCADEFBCB320EF" +
      "BCB0E382B4E382B7E38383E382AF2220706974636846616D696C793D22312220" +
      "636861727365743D222D313238222F3E3C2F613A6465665250723E3C2F613A6C" +
      "766C327050723E3C613A6C766C33705072206D61724C3D223131343330303022" +
      "20696E64656E743D222D3232383630302220616C676E3D226C222072746C3D22" +
      "30222065614C6E42726B3D22302220666F6E74416C676E3D2262617365222068" +
      "616E67696E6750756E63743D2230223E3C613A7370634265663E3C613A737063" +
      "5063742076616C3D223230303030222F3E3C2F613A7370634265663E3C613A73" +
      "70634166743E3C613A7370635063742076616C3D2230222F3E3C2F613A737063" +
      "4166743E3C613A62754368617220636861723D22E280A2222F3E3C613A646566" +
      "52507220737A3D2232343030223E3C613A736F6C696446696C6C3E3C613A7363" +
      "68656D65436C722076616C3D22747831222F3E3C2F613A736F6C696446696C6C" +
      "3E3C613A6C6174696E2074797065666163653D2254696D6573204E657720526F" +
      "6D616E2220706974636846616D696C793D22312220636861727365743D223022" +
      "2F3E3C613A65612074797065666163653D22EFBCADEFBCB320EFBCB0E382B4E3" +
      "82B7E38383E382AF2220706974636846616D696C793D22312220636861727365" +
      "743D222D313238222F3E3C2F613A6465665250723E3C2F613A6C766C33705072" +
      "3E3C613A6C766C34705072206D61724C3D22313630303230302220696E64656E" +
      "743D222D3232383630302220616C676E3D226C222072746C3D2230222065614C" +
      "6E42726B3D22302220666F6E74416C676E3D2262617365222068616E67696E67" +
      "50756E63743D2230223E3C613A7370634265663E3C613A737063506374207661" +
      "6C3D223230303030222F3E3C2F613A7370634265663E3C613A7370634166743E" +
      "3C613A7370635063742076616C3D2230222F3E3C2F613A7370634166743E3C61" +
      "3A62754368617220636861723D22E28093222F3E3C613A64656652507220737A" +
      "3D2232303030223E3C613A736F6C696446696C6C3E3C613A736368656D65436C" +
      "722076616C3D22747831222F3E3C2F613A736F6C696446696C6C3E3C613A6C61" +
      "74696E2074797065666163653D2254696D6573204E657720526F6D616E222070" +
      "6974636846616D696C793D22312220636861727365743D2230222F3E3C613A65" +
      "612074797065666163653D22EFBCADEFBCB320EFBCB0E382B4E382B7E38383E3" +
      "82AF2220706974636846616D696C793D22312220636861727365743D222D3132" +
      "38222F3E3C2F613A6465665250723E3C2F613A6C766C347050723E3C613A6C76" +
      "6C35705072206D61724C3D22323035373430302220696E64656E743D222D3232" +
      "383630302220616C676E3D226C222072746C3D2230222065614C6E42726B3D22" +
      "302220666F6E74416C676E3D2262617365222068616E67696E6750756E63743D" +
      "2230223E3C613A7370634265663E3C613A7370635063742076616C3D22323030" +
      "3030222F3E3C2F613A7370634265663E3C613A7370634166743E3C613A737063" +
      "5063742076616C3D2230222F3E3C2F613A7370634166743E3C613A6275436861" +
      "7220636861723D22C2BB222F3E3C613A64656652507220737A3D223230303022" +
      "3E3C613A736F6C696446696C6C3E3C613A736368656D65436C722076616C3D22" +
      "747831222F3E3C2F613A736F6C696446696C6C3E3C613A6C6174696E20747970" +
      "65666163653D2254696D6573204E657720526F6D616E2220706974636846616D" +
      "696C793D22312220636861727365743D2230222F3E3C613A6561207479706566" +
      "6163653D22EFBCADEFBCB320EFBCB0E382B4E382B7E38383E382AF2220706974" +
      "636846616D696C793D22312220636861727365743D222D313238222F3E3C2F61" +
      "3A6465665250723E3C2F613A6C766C357050723E3C613A6C766C36705072206D" +
      "61724C3D22323531343630302220696E64656E743D222D323238363030222061" +
      "6C676E3D226C222072746C3D2230222065614C6E42726B3D22302220666F6E74" +
      "416C676E3D2262617365222068616E67696E6750756E63743D2230223E3C613A" +
      "7370634265663E3C613A7370635063742076616C3D223230303030222F3E3C2F" +
      "613A7370634265663E3C613A7370634166743E3C613A7370635063742076616C" +
      "3D2230222F3E3C2F613A7370634166743E3C613A62754368617220636861723D" +
      "22C2BB222F3E3C613A64656652507220737A3D2232303030223E3C613A736F6C" +
      "696446696C6C3E3C613A736368656D65436C722076616C3D22747831222F3E3C" +
      "2F613A736F6C696446696C6C3E3C613A6C6174696E2074797065666163653D22" +
      "2B6D6E2D6C74222F3E3C2F613A6465665250723E3C2F613A6C766C367050723E" +
      "3C613A6C766C37705072206D61724C3D22323937313830302220696E64656E74" +
      "3D222D3232383630302220616C676E3D226C222072746C3D2230222065614C6E" +
      "42726B3D22302220666F6E74416C676E3D2262617365222068616E67696E6750" +
      "756E63743D2230223E3C613A7370634265663E3C613A7370635063742076616C" +
      "3D223230303030222F3E3C2F613A7370634265663E3C613A7370634166743E3C" +
      "613A7370635063742076616C3D2230222F3E3C2F613A7370634166743E3C613A" +
      "62754368617220636861723D22C2BB222F3E3C613A64656652507220737A3D22" +
      "32303030223E3C613A736F6C696446696C6C3E3C613A736368656D65436C7220" +
      "76616C3D22747831222F3E3C2F613A736F6C696446696C6C3E3C613A6C617469" +
      "6E2074797065666163653D222B6D6E2D6C74222F3E3C2F613A6465665250723E" +
      "3C2F613A6C766C377050723E3C613A6C766C38705072206D61724C3D22333432" +
      "393030302220696E64656E743D222D3232383630302220616C676E3D226C2220" +
      "72746C3D2230222065614C6E42726B3D22302220666F6E74416C676E3D226261" +
      "7365222068616E67696E6750756E63743D2230223E3C613A7370634265663E3C" +
      "613A7370635063742076616C3D223230303030222F3E3C2F613A737063426566" +
      "3E3C613A7370634166743E3C613A7370635063742076616C3D2230222F3E3C2F" +
      "613A7370634166743E3C613A62754368617220636861723D22C2BB222F3E3C61" +
      "3A64656652507220737A3D2232303030223E3C613A736F6C696446696C6C3E3C" +
      "613A736368656D65436C722076616C3D22747831222F3E3C2F613A736F6C6964" +
      "46696C6C3E3C613A6C6174696E2074797065666163653D222B6D6E2D6C74222F" +
      "3E3C2F613A6465665250723E3C2F613A6C766C387050723E3C613A6C766C3970" +
      "5072206D61724C3D22333838363230302220696E64656E743D222D3232383630" +
      "302220616C676E3D226C222072746C3D2230222065614C6E42726B3D22302220" +
      "666F6E74416C676E3D2262617365222068616E67696E6750756E63743D223022" +
      "3E3C613A7370634265663E3C613A7370635063742076616C3D22323030303022" +
      "2F3E3C2F613A7370634265663E3C613A7370634166743E3C613A737063506374" +
      "2076616C3D2230222F3E3C2F613A7370634166743E3C613A6275436861722063" +
      "6861723D22C2BB222F3E3C613A64656652507220737A3D2232303030223E3C61" +
      "3A736F6C696446696C6C3E3C613A736368656D65436C722076616C3D22747831" +
      "222F3E3C2F613A736F6C696446696C6C3E3C613A6C6174696E20747970656661" +
      "63653D222B6D6E2D6C74222F3E3C2F613A6465665250723E3C2F613A6C766C39" +
      "7050723E3C2F703A626F64795374796C653E3C703A6F746865725374796C653E" +
      "3C613A6465665050723E3C613A646566525072206C616E673D22656E2D555322" +
      "2F3E3C2F613A6465665050723E3C613A6C766C31705072206D61724C3D223022" +
      "20616C676E3D226C2220646566546162537A3D22393134343030222072746C3D" +
      "2230222065614C6E42726B3D223122206C6174696E4C6E42726B3D2230222068" +
      "616E67696E6750756E63743D2231223E3C613A64656652507220737A3D223138" +
      "303022206B65726E3D2231323030223E3C613A736F6C696446696C6C3E3C613A" +
      "736368656D65436C722076616C3D22747831222F3E3C2F613A736F6C69644669" +
      "6C6C3E3C613A6C6174696E2074797065666163653D222B6D6E2D6C74222F3E3C" +
      "613A65612074797065666163653D222B6D6E2D6561222F3E3C613A6373207479" +
      "7065666163653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F613A" +
      "6C766C317050723E3C613A6C766C32705072206D61724C3D2234353732303022" +
      "20616C676E3D226C2220646566546162537A3D22393134343030222072746C3D" +
      "2230222065614C6E42726B3D223122206C6174696E4C6E42726B3D2230222068" +
      "616E67696E6750756E63743D2231223E3C613A64656652507220737A3D223138" +
      "303022206B65726E3D2231323030223E3C613A736F6C696446696C6C3E3C613A" +
      "736368656D65436C722076616C3D22747831222F3E3C2F613A736F6C69644669" +
      "6C6C3E3C613A6C6174696E2074797065666163653D222B6D6E2D6C74222F3E3C" +
      "613A65612074797065666163653D222B6D6E2D6561222F3E3C613A6373207479" +
      "7065666163653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F613A" +
      "6C766C327050723E3C613A6C766C33705072206D61724C3D2239313434303022" +
      "20616C676E3D226C2220646566546162537A3D22393134343030222072746C3D" +
      "2230222065614C6E42726B3D223122206C6174696E4C6E42726B3D2230222068" +
      "616E67696E6750756E63743D2231223E3C613A64656652507220737A3D223138" +
      "303022206B65726E3D2231323030223E3C613A736F6C696446696C6C3E3C613A" +
      "736368656D65436C722076616C3D22747831222F3E3C2F613A736F6C69644669" +
      "6C6C3E3C613A6C6174696E2074797065666163653D222B6D6E2D6C74222F3E3C" +
      "613A65612074797065666163653D222B6D6E2D6561222F3E3C613A6373207479" +
      "7065666163653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F613A" +
      "6C766C337050723E3C613A6C766C34705072206D61724C3D2231333731363030" +
      "2220616C676E3D226C2220646566546162537A3D22393134343030222072746C" +
      "3D2230222065614C6E42726B3D223122206C6174696E4C6E42726B3D22302220" +
      "68616E67696E6750756E63743D2231223E3C613A64656652507220737A3D2231" +
      "38303022206B65726E3D2231323030223E3C613A736F6C696446696C6C3E3C61" +
      "3A736368656D65436C722076616C3D22747831222F3E3C2F613A736F6C696446" +
      "696C6C3E3C613A6C6174696E2074797065666163653D222B6D6E2D6C74222F3E" +
      "3C613A65612074797065666163653D222B6D6E2D6561222F3E3C613A63732074" +
      "797065666163653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F61" +
      "3A6C766C347050723E3C613A6C766C35705072206D61724C3D22313832383830" +
      "302220616C676E3D226C2220646566546162537A3D2239313434303022207274" +
      "6C3D2230222065614C6E42726B3D223122206C6174696E4C6E42726B3D223022" +
      "2068616E67696E6750756E63743D2231223E3C613A64656652507220737A3D22" +
      "3138303022206B65726E3D2231323030223E3C613A736F6C696446696C6C3E3C" +
      "613A736368656D65436C722076616C3D22747831222F3E3C2F613A736F6C6964" +
      "46696C6C3E3C613A6C6174696E2074797065666163653D222B6D6E2D6C74222F" +
      "3E3C613A65612074797065666163653D222B6D6E2D6561222F3E3C613A637320" +
      "74797065666163653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C2F" +
      "613A6C766C357050723E3C613A6C766C36705072206D61724C3D223232383630" +
      "30302220616C676E3D226C2220646566546162537A3D22393134343030222072" +
      "746C3D2230222065614C6E42726B3D223122206C6174696E4C6E42726B3D2230" +
      "222068616E67696E6750756E63743D2231223E3C613A64656652507220737A3D" +
      "223138303022206B65726E3D2231323030223E3C613A736F6C696446696C6C3E" +
      "3C613A736368656D65436C722076616C3D22747831222F3E3C2F613A736F6C69" +
      "6446696C6C3E3C613A6C6174696E2074797065666163653D222B6D6E2D6C7422" +
      "2F3E3C613A65612074797065666163653D222B6D6E2D6561222F3E3C613A6373" +
      "2074797065666163653D222B6D6E2D6373222F3E3C2F613A6465665250723E3C" +
      "2F613A6C766C367050723E3C613A6C766C37705072206D61724C3D2232373433" +
      "3230302220616C676E3D226C2220646566546162537A3D223931343430302220" +
      "72746C3D2230222065614C6E42726B3D223122206C6174696E4C6E42726B3D22" +
      "30222068616E67696E6750756E63743D2231223E3C613A64656652507220737A" +
      "3D223138303022206B65726E3D2231323030223E3C613A736F6C696446696C6C" +
      "3E3C613A736368656D65436C722076616C3D22747831222F3E3C2F613A736F6C" +
      "696446696C6C3E3C613A6C6174696E2074797065666163653D222B6D6E2D6C74" +
      "222F3E3C613A65612074797065666163653D222B6D6E2D6561222F3E3C613A63" +
      "732074797065666163653D222B6D6E2D6373222F3E3C2F613A6465665250723E" +
      "3C2F613A6C766C377050723E3C613A6C766C38705072206D61724C3D22333230" +
      "303430302220616C676E3D226C2220646566546162537A3D2239313434303022" +
      "2072746C3D2230222065614C6E42726B3D223122206C6174696E4C6E42726B3D" +
      "2230222068616E67696E6750756E63743D2231223E3C613A6465665250722073" +
      "7A3D223138303022206B65726E3D2231323030223E3C613A736F6C696446696C" +
      "6C3E3C613A736368656D65436C722076616C3D22747831222F3E3C2F613A736F" +
      "6C696446696C6C3E3C613A6C6174696E2074797065666163653D222B6D6E2D6C" +
      "74222F3E3C613A65612074797065666163653D222B6D6E2D6561222F3E3C613A" +
      "63732074797065666163653D222B6D6E2D6373222F3E3C2F613A646566525072" +
      "3E3C2F613A6C766C387050723E3C613A6C766C39705072206D61724C3D223336" +
      "35373630302220616C676E3D226C2220646566546162537A3D22393134343030" +
      "222072746C3D2230222065614C6E42726B3D223122206C6174696E4C6E42726B" +
      "3D2230222068616E67696E6750756E63743D2231223E3C613A64656652507220" +
      "737A3D223138303022206B65726E3D2231323030223E3C613A736F6C69644669" +
      "6C6C3E3C613A736368656D65436C722076616C3D22747831222F3E3C2F613A73" +
      "6F6C696446696C6C3E3C613A6C6174696E2074797065666163653D222B6D6E2D" +
      "6C74222F3E3C613A65612074797065666163653D222B6D6E2D6561222F3E3C61" +
      "3A63732074797065666163653D222B6D6E2D6373222F3E3C2F613A6465665250" +
      "723E3C2F613A6C766C397050723E3C2F703A6F746865725374796C653E3C2F70" +
      "3A74785374796C65733E3C2F703A736C644D61737465723E";

   public static final String TABLE_STYLES =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C613A74626C53" +
      "74796C654C737420786D6C6E733A613D22687474703A2F2F736368656D61732E" +
      "6F70656E786D6C666F726D6174732E6F72672F64726177696E676D6C2F323030" +
      "362F6D61696E22206465663D227B35433232353434412D374545362D34333432" +
      "2D423034382D3835424443394644314333417D222F3E";

   public static final String THEME_1 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C613A7468656D" +
      "6520786D6C6E733A613D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F6D61696E" +
      "22206E616D653D22426C616E6B2050726573656E746174696F6E223E3C613A74" +
      "68656D65456C656D656E74733E3C613A636C72536368656D65206E616D653D22" +
      "426C616E6B2050726573656E746174696F6E2031223E3C613A646B313E3C613A" +
      "73726762436C722076616C3D22303030303030222F3E3C2F613A646B313E3C61" +
      "3A6C74313E3C613A73726762436C722076616C3D22464646464646222F3E3C2F" +
      "613A6C74313E3C613A646B323E3C613A73726762436C722076616C3D22303030" +
      "303030222F3E3C2F613A646B323E3C613A6C74323E3C613A73726762436C7220" +
      "76616C3D22383038303830222F3E3C2F613A6C74323E3C613A616363656E7431" +
      "3E3C613A73726762436C722076616C3D22303043433939222F3E3C2F613A6163" +
      "63656E74313E3C613A616363656E74323E3C613A73726762436C722076616C3D" +
      "22333333334343222F3E3C2F613A616363656E74323E3C613A616363656E7433" +
      "3E3C613A73726762436C722076616C3D22464646464646222F3E3C2F613A6163" +
      "63656E74333E3C613A616363656E74343E3C613A73726762436C722076616C3D" +
      "22303030303030222F3E3C2F613A616363656E74343E3C613A616363656E7435" +
      "3E3C613A73726762436C722076616C3D22414145324341222F3E3C2F613A6163" +
      "63656E74353E3C613A616363656E74363E3C613A73726762436C722076616C3D" +
      "22324432444239222F3E3C2F613A616363656E74363E3C613A686C696E6B3E3C" +
      "613A73726762436C722076616C3D22434343434646222F3E3C2F613A686C696E" +
      "6B3E3C613A666F6C486C696E6B3E3C613A73726762436C722076616C3D224232" +
      "42324232222F3E3C2F613A666F6C486C696E6B3E3C2F613A636C72536368656D" +
      "653E3C613A666F6E74536368656D65206E616D653D22426C616E6B2050726573" +
      "656E746174696F6E223E3C613A6D616A6F72466F6E743E3C613A6C6174696E20" +
      "74797065666163653D2254696D6573222F3E3C613A6561207479706566616365" +
      "3D22222F3E3C613A63732074797065666163653D22222F3E3C2F613A6D616A6F" +
      "72466F6E743E3C613A6D696E6F72466F6E743E3C613A6C6174696E2074797065" +
      "666163653D2254696D6573222F3E3C613A65612074797065666163653D22222F" +
      "3E3C613A63732074797065666163653D22222F3E3C2F613A6D696E6F72466F6E" +
      "743E3C2F613A666F6E74536368656D653E3C613A666D74536368656D65206E61" +
      "6D653D224F6666696365223E3C613A66696C6C5374796C654C73743E3C613A73" +
      "6F6C696446696C6C3E3C613A736368656D65436C722076616C3D227068436C72" +
      "222F3E3C2F613A736F6C696446696C6C3E3C613A6772616446696C6C20726F74" +
      "5769746853686170653D2231223E3C613A67734C73743E3C613A677320706F73" +
      "3D2230223E3C613A736368656D65436C722076616C3D227068436C72223E3C61" +
      "3A74696E742076616C3D223530303030222F3E3C613A7361744D6F642076616C" +
      "3D22333030303030222F3E3C2F613A736368656D65436C723E3C2F613A67733E" +
      "3C613A677320706F733D223335303030223E3C613A736368656D65436C722076" +
      "616C3D227068436C72223E3C613A74696E742076616C3D223337303030222F3E" +
      "3C613A7361744D6F642076616C3D22333030303030222F3E3C2F613A73636865" +
      "6D65436C723E3C2F613A67733E3C613A677320706F733D22313030303030223E" +
      "3C613A736368656D65436C722076616C3D227068436C72223E3C613A74696E74" +
      "2076616C3D223135303030222F3E3C613A7361744D6F642076616C3D22333530" +
      "303030222F3E3C2F613A736368656D65436C723E3C2F613A67733E3C2F613A67" +
      "734C73743E3C613A6C696E20616E673D22313632303030303022207363616C65" +
      "643D2231222F3E3C2F613A6772616446696C6C3E3C613A6772616446696C6C20" +
      "726F745769746853686170653D2231223E3C613A67734C73743E3C613A677320" +
      "706F733D2230223E3C613A736368656D65436C722076616C3D227068436C7222" +
      "3E3C613A73686164652076616C3D223531303030222F3E3C613A7361744D6F64" +
      "2076616C3D22313330303030222F3E3C2F613A736368656D65436C723E3C2F61" +
      "3A67733E3C613A677320706F733D223830303030223E3C613A736368656D6543" +
      "6C722076616C3D227068436C72223E3C613A73686164652076616C3D22393330" +
      "3030222F3E3C613A7361744D6F642076616C3D22313330303030222F3E3C2F61" +
      "3A736368656D65436C723E3C2F613A67733E3C613A677320706F733D22313030" +
      "303030223E3C613A736368656D65436C722076616C3D227068436C72223E3C61" +
      "3A73686164652076616C3D223934303030222F3E3C613A7361744D6F64207661" +
      "6C3D22313335303030222F3E3C2F613A736368656D65436C723E3C2F613A6773" +
      "3E3C2F613A67734C73743E3C613A6C696E20616E673D22313632303030303022" +
      "207363616C65643D2230222F3E3C2F613A6772616446696C6C3E3C2F613A6669" +
      "6C6C5374796C654C73743E3C613A6C6E5374796C654C73743E3C613A6C6E2077" +
      "3D223935323522206361703D22666C61742220636D70643D22736E672220616C" +
      "676E3D22637472223E3C613A736F6C696446696C6C3E3C613A736368656D6543" +
      "6C722076616C3D227068436C72223E3C613A73686164652076616C3D22393530" +
      "3030222F3E3C613A7361744D6F642076616C3D22313035303030222F3E3C2F61" +
      "3A736368656D65436C723E3C2F613A736F6C696446696C6C3E3C613A70727374" +
      "446173682076616C3D22736F6C6964222F3E3C2F613A6C6E3E3C613A6C6E2077" +
      "3D22323534303022206361703D22666C61742220636D70643D22736E67222061" +
      "6C676E3D22637472223E3C613A736F6C696446696C6C3E3C613A736368656D65" +
      "436C722076616C3D227068436C72222F3E3C2F613A736F6C696446696C6C3E3C" +
      "613A70727374446173682076616C3D22736F6C6964222F3E3C2F613A6C6E3E3C" +
      "613A6C6E20773D22333831303022206361703D22666C61742220636D70643D22" +
      "736E672220616C676E3D22637472223E3C613A736F6C696446696C6C3E3C613A" +
      "736368656D65436C722076616C3D227068436C72222F3E3C2F613A736F6C6964" +
      "46696C6C3E3C613A70727374446173682076616C3D22736F6C6964222F3E3C2F" +
      "613A6C6E3E3C2F613A6C6E5374796C654C73743E3C613A656666656374537479" +
      "6C654C73743E3C613A6566666563745374796C653E3C613A6566666563744C73" +
      "743E3C613A6F757465725368647720626C75725261643D223430303030222064" +
      "6973743D22323030303022206469723D22353430303030302220726F74576974" +
      "6853686170653D2230223E3C613A73726762436C722076616C3D223030303030" +
      "30223E3C613A616C7068612076616C3D223338303030222F3E3C2F613A737267" +
      "62436C723E3C2F613A6F75746572536864773E3C2F613A6566666563744C7374" +
      "3E3C2F613A6566666563745374796C653E3C613A6566666563745374796C653E" +
      "3C613A6566666563744C73743E3C613A6F757465725368647720626C75725261" +
      "643D2234303030302220646973743D22323330303022206469723D2235343030" +
      "3030302220726F745769746853686170653D2230223E3C613A73726762436C72" +
      "2076616C3D22303030303030223E3C613A616C7068612076616C3D2233353030" +
      "30222F3E3C2F613A73726762436C723E3C2F613A6F75746572536864773E3C2F" +
      "613A6566666563744C73743E3C2F613A6566666563745374796C653E3C613A65" +
      "66666563745374796C653E3C613A6566666563744C73743E3C613A6F75746572" +
      "5368647720626C75725261643D2234303030302220646973743D223233303030" +
      "22206469723D22353430303030302220726F745769746853686170653D223022" +
      "3E3C613A73726762436C722076616C3D22303030303030223E3C613A616C7068" +
      "612076616C3D223335303030222F3E3C2F613A73726762436C723E3C2F613A6F" +
      "75746572536864773E3C2F613A6566666563744C73743E3C613A7363656E6533" +
      "643E3C613A63616D65726120707273743D226F7274686F677261706869634672" +
      "6F6E74223E3C613A726F74206C61743D223022206C6F6E3D223022207265763D" +
      "2230222F3E3C2F613A63616D6572613E3C613A6C69676874526967207269673D" +
      "227468726565507422206469723D2274223E3C613A726F74206C61743D223022" +
      "206C6F6E3D223022207265763D2231323030303030222F3E3C2F613A6C696768" +
      "745269673E3C2F613A7363656E6533643E3C613A737033643E3C613A62657665" +
      "6C5420773D2236333530302220683D223235343030222F3E3C2F613A73703364" +
      "3E3C2F613A6566666563745374796C653E3C2F613A6566666563745374796C65" +
      "4C73743E3C613A626746696C6C5374796C654C73743E3C613A736F6C69644669" +
      "6C6C3E3C613A736368656D65436C722076616C3D227068436C72222F3E3C2F61" +
      "3A736F6C696446696C6C3E3C613A6772616446696C6C20726F74576974685368" +
      "6170653D2231223E3C613A67734C73743E3C613A677320706F733D2230223E3C" +
      "613A736368656D65436C722076616C3D227068436C72223E3C613A74696E7420" +
      "76616C3D223430303030222F3E3C613A7361744D6F642076616C3D2233353030" +
      "3030222F3E3C2F613A736368656D65436C723E3C2F613A67733E3C613A677320" +
      "706F733D223430303030223E3C613A736368656D65436C722076616C3D227068" +
      "436C72223E3C613A74696E742076616C3D223435303030222F3E3C613A736861" +
      "64652076616C3D223939303030222F3E3C613A7361744D6F642076616C3D2233" +
      "3530303030222F3E3C2F613A736368656D65436C723E3C2F613A67733E3C613A" +
      "677320706F733D22313030303030223E3C613A736368656D65436C722076616C" +
      "3D227068436C72223E3C613A73686164652076616C3D223230303030222F3E3C" +
      "613A7361744D6F642076616C3D22323535303030222F3E3C2F613A736368656D" +
      "65436C723E3C2F613A67733E3C2F613A67734C73743E3C613A70617468207061" +
      "74683D22636972636C65223E3C613A66696C6C546F52656374206C3D22353030" +
      "30302220743D222D38303030302220723D2235303030302220623D2231383030" +
      "3030222F3E3C2F613A706174683E3C2F613A6772616446696C6C3E3C613A6772" +
      "616446696C6C20726F745769746853686170653D2231223E3C613A67734C7374" +
      "3E3C613A677320706F733D2230223E3C613A736368656D65436C722076616C3D" +
      "227068436C72223E3C613A74696E742076616C3D223830303030222F3E3C613A" +
      "7361744D6F642076616C3D22333030303030222F3E3C2F613A736368656D6543" +
      "6C723E3C2F613A67733E3C613A677320706F733D22313030303030223E3C613A" +
      "736368656D65436C722076616C3D227068436C72223E3C613A73686164652076" +
      "616C3D223330303030222F3E3C613A7361744D6F642076616C3D223230303030" +
      "30222F3E3C2F613A736368656D65436C723E3C2F613A67733E3C2F613A67734C" +
      "73743E3C613A7061746820706174683D22636972636C65223E3C613A66696C6C" +
      "546F52656374206C3D2235303030302220743D2235303030302220723D223530" +
      "3030302220623D223530303030222F3E3C2F613A706174683E3C2F613A677261" +
      "6446696C6C3E3C2F613A626746696C6C5374796C654C73743E3C2F613A666D74" +
      "536368656D653E3C2F613A7468656D65456C656D656E74733E3C613A6F626A65" +
      "637444656661756C74733E3C613A73704465663E3C613A737050722062774D6F" +
      "64653D226175746F223E3C613A7866726D3E3C613A6F666620783D2230222079" +
      "3D2230222F3E3C613A6578742063783D2231222063793D2231222F3E3C2F613A" +
      "7866726D3E3C613A6375737447656F6D3E3C613A61764C73742F3E3C613A6764" +
      "4C73742F3E3C613A61684C73742F3E3C613A63786E4C73742F3E3C613A726563" +
      "74206C3D22302220743D22302220723D22302220623D2230222F3E3C613A7061" +
      "74684C73742F3E3C2F613A6375737447656F6D3E3C613A736F6C696446696C6C" +
      "3E3C613A736368656D65436C722076616C3D22616363656E7431222F3E3C2F61" +
      "3A736F6C696446696C6C3E3C613A6C6E20773D223935323522206361703D2266" +
      "6C61742220636D70643D22736E672220616C676E3D22637472223E3C613A736F" +
      "6C696446696C6C3E3C613A736368656D65436C722076616C3D22747831222F3E" +
      "3C2F613A736F6C696446696C6C3E3C613A70727374446173682076616C3D2273" +
      "6F6C6964222F3E3C613A726F756E642F3E3C613A68656164456E642074797065" +
      "3D226E6F6E652220773D226D656422206C656E3D226D6564222F3E3C613A7461" +
      "696C456E6420747970653D226E6F6E652220773D226D656422206C656E3D226D" +
      "6564222F3E3C2F613A6C6E3E3C613A6566666563744C73742F3E3C2F613A7370" +
      "50723E3C613A626F6479507220766572743D22686F727A2220777261703D2273" +
      "717561726522206C496E733D223931343430222074496E733D22343537323022" +
      "2072496E733D223931343430222062496E733D22343537323022206E756D436F" +
      "6C3D22312220616E63686F723D22742220616E63686F724374723D2230222063" +
      "6F6D7061744C6E5370633D2231223E3C613A7072737454785761727020707273" +
      "743D22746578744E6F5368617065223E3C613A61764C73742F3E3C2F613A7072" +
      "73745478576172703E3C2F613A626F647950723E3C613A6C73745374796C653E" +
      "3C613A646566505072206D61724C3D223022206D6172523D22302220696E6465" +
      "6E743D22302220616C676E3D226C2220646566546162537A3D22393134343030" +
      "222072746C3D2230222065614C6E42726B3D22302220666F6E74416C676E3D22" +
      "6261736522206C6174696E4C6E42726B3D2230222068616E67696E6750756E63" +
      "743D2230223E3C613A6C6E5370633E3C613A7370635063742076616C3D223130" +
      "30303030222F3E3C2F613A6C6E5370633E3C613A7370634265663E3C613A7370" +
      "635063742076616C3D2230222F3E3C2F613A7370634265663E3C613A73706341" +
      "66743E3C613A7370635063742076616C3D2230222F3E3C2F613A737063416674" +
      "3E3C613A6275436C7254782F3E3C613A6275537A54782F3E3C613A6275466F6E" +
      "7454782F3E3C613A62754E6F6E652F3E3C613A7461624C73742F3E3C613A6465" +
      "66525072206B756D696D6F6A693D223022206C616E673D22656E2D5553222073" +
      "7A3D22313430302220623D22312220693D22302220753D226E6F6E6522207374" +
      "72696B653D226E6F537472696B6522206361703D226E6F6E6522206E6F726D61" +
      "6C697A65483D22302220626173656C696E653D22302220736D74436C65616E3D" +
      "2230223E3C613A6C6E3E3C613A6E6F46696C6C2F3E3C2F613A6C6E3E3C613A73" +
      "6F6C696446696C6C3E3C613A736368656D65436C722076616C3D22747831222F" +
      "3E3C2F613A736F6C696446696C6C3E3C613A6566666563744C73742F3E3C613A" +
      "6C6174696E2074797065666163653D2254696D6573204E657720526F6D616E22" +
      "20706974636846616D696C793D2238342220636861727365743D2230222F3E3C" +
      "2F613A6465665250723E3C2F613A6465665050723E3C2F613A6C73745374796C" +
      "653E3C2F613A73704465663E3C613A6C6E4465663E3C613A737050722062774D" +
      "6F64653D226175746F223E3C613A7866726D3E3C613A6F666620783D22302220" +
      "793D2230222F3E3C613A6578742063783D2231222063793D2231222F3E3C2F61" +
      "3A7866726D3E3C613A6375737447656F6D3E3C613A61764C73742F3E3C613A67" +
      "644C73742F3E3C613A61684C73742F3E3C613A63786E4C73742F3E3C613A7265" +
      "6374206C3D22302220743D22302220723D22302220623D2230222F3E3C613A70" +
      "6174684C73742F3E3C2F613A6375737447656F6D3E3C613A736F6C696446696C" +
      "6C3E3C613A736368656D65436C722076616C3D22616363656E7431222F3E3C2F" +
      "613A736F6C696446696C6C3E3C613A6C6E20773D223935323522206361703D22" +
      "666C61742220636D70643D22736E672220616C676E3D22637472223E3C613A73" +
      "6F6C696446696C6C3E3C613A736368656D65436C722076616C3D22747831222F" +
      "3E3C2F613A736F6C696446696C6C3E3C613A70727374446173682076616C3D22" +
      "736F6C6964222F3E3C613A726F756E642F3E3C613A68656164456E6420747970" +
      "653D226E6F6E652220773D226D656422206C656E3D226D6564222F3E3C613A74" +
      "61696C456E6420747970653D226E6F6E652220773D226D656422206C656E3D22" +
      "6D6564222F3E3C2F613A6C6E3E3C613A6566666563744C73742F3E3C2F613A73" +
      "7050723E3C613A626F6479507220766572743D22686F727A2220777261703D22" +
      "73717561726522206C496E733D223931343430222074496E733D223435373230" +
      "222072496E733D223931343430222062496E733D22343537323022206E756D43" +
      "6F6C3D22312220616E63686F723D22742220616E63686F724374723D22302220" +
      "636F6D7061744C6E5370633D2231223E3C613A70727374547857617270207072" +
      "73743D22746578744E6F5368617065223E3C613A61764C73742F3E3C2F613A70" +
      "7273745478576172703E3C2F613A626F647950723E3C613A6C73745374796C65" +
      "3E3C613A646566505072206D61724C3D223022206D6172523D22302220696E64" +
      "656E743D22302220616C676E3D226C2220646566546162537A3D223931343430" +
      "30222072746C3D2230222065614C6E42726B3D22302220666F6E74416C676E3D" +
      "226261736522206C6174696E4C6E42726B3D2230222068616E67696E6750756E" +
      "63743D2230223E3C613A6C6E5370633E3C613A7370635063742076616C3D2231" +
      "3030303030222F3E3C2F613A6C6E5370633E3C613A7370634265663E3C613A73" +
      "70635063742076616C3D2230222F3E3C2F613A7370634265663E3C613A737063" +
      "4166743E3C613A7370635063742076616C3D2230222F3E3C2F613A7370634166" +
      "743E3C613A6275436C7254782F3E3C613A6275537A54782F3E3C613A6275466F" +
      "6E7454782F3E3C613A62754E6F6E652F3E3C613A7461624C73742F3E3C613A64" +
      "6566525072206B756D696D6F6A693D223022206C616E673D22656E2D55532220" +
      "737A3D22313430302220623D22312220693D22302220753D226E6F6E65222073" +
      "7472696B653D226E6F537472696B6522206361703D226E6F6E6522206E6F726D" +
      "616C697A65483D22302220626173656C696E653D22302220736D74436C65616E" +
      "3D2230223E3C613A6C6E3E3C613A6E6F46696C6C2F3E3C2F613A6C6E3E3C613A" +
      "736F6C696446696C6C3E3C613A736368656D65436C722076616C3D2274783122" +
      "2F3E3C2F613A736F6C696446696C6C3E3C613A6566666563744C73742F3E3C61" +
      "3A6C6174696E2074797065666163653D2254696D6573204E657720526F6D616E" +
      "2220706974636846616D696C793D2238342220636861727365743D2230222F3E" +
      "3C2F613A6465665250723E3C2F613A6465665050723E3C2F613A6C7374537479" +
      "6C653E3C2F613A6C6E4465663E3C2F613A6F626A65637444656661756C74733E" +
      "3C613A6578747261436C72536368656D654C73743E3C613A6578747261436C72" +
      "536368656D653E3C613A636C72536368656D65206E616D653D22426C616E6B20" +
      "50726573656E746174696F6E2031223E3C613A646B313E3C613A73726762436C" +
      "722076616C3D22303030303030222F3E3C2F613A646B313E3C613A6C74313E3C" +
      "613A73726762436C722076616C3D22464646464646222F3E3C2F613A6C74313E" +
      "3C613A646B323E3C613A73726762436C722076616C3D22303030303030222F3E" +
      "3C2F613A646B323E3C613A6C74323E3C613A73726762436C722076616C3D2238" +
      "3038303830222F3E3C2F613A6C74323E3C613A616363656E74313E3C613A7372" +
      "6762436C722076616C3D22303043433939222F3E3C2F613A616363656E74313E" +
      "3C613A616363656E74323E3C613A73726762436C722076616C3D223333333343" +
      "43222F3E3C2F613A616363656E74323E3C613A616363656E74333E3C613A7372" +
      "6762436C722076616C3D22464646464646222F3E3C2F613A616363656E74333E" +
      "3C613A616363656E74343E3C613A73726762436C722076616C3D223030303030" +
      "30222F3E3C2F613A616363656E74343E3C613A616363656E74353E3C613A7372" +
      "6762436C722076616C3D22414145324341222F3E3C2F613A616363656E74353E" +
      "3C613A616363656E74363E3C613A73726762436C722076616C3D223244324442" +
      "39222F3E3C2F613A616363656E74363E3C613A686C696E6B3E3C613A73726762" +
      "436C722076616C3D22434343434646222F3E3C2F613A686C696E6B3E3C613A66" +
      "6F6C486C696E6B3E3C613A73726762436C722076616C3D22423242324232222F" +
      "3E3C2F613A666F6C486C696E6B3E3C2F613A636C72536368656D653E3C613A63" +
      "6C724D6170206267313D226C743122207478313D22646B3122206267323D226C" +
      "743222207478323D22646B322220616363656E74313D22616363656E74312220" +
      "616363656E74323D22616363656E74322220616363656E74333D22616363656E" +
      "74332220616363656E74343D22616363656E74342220616363656E74353D2261" +
      "6363656E74352220616363656E74363D22616363656E74362220686C696E6B3D" +
      "22686C696E6B2220666F6C486C696E6B3D22666F6C486C696E6B222F3E3C2F61" +
      "3A6578747261436C72536368656D653E3C613A6578747261436C72536368656D" +
      "653E3C613A636C72536368656D65206E616D653D22426C616E6B205072657365" +
      "6E746174696F6E2032223E3C613A646B313E3C613A73726762436C722076616C" +
      "3D22303030303030222F3E3C2F613A646B313E3C613A6C74313E3C613A737267" +
      "62436C722076616C3D22464646464646222F3E3C2F613A6C74313E3C613A646B" +
      "323E3C613A73726762436C722076616C3D22303030304646222F3E3C2F613A64" +
      "6B323E3C613A6C74323E3C613A73726762436C722076616C3D22464646463030" +
      "222F3E3C2F613A6C74323E3C613A616363656E74313E3C613A73726762436C72" +
      "2076616C3D22464639393030222F3E3C2F613A616363656E74313E3C613A6163" +
      "63656E74323E3C613A73726762436C722076616C3D22303046464646222F3E3C" +
      "2F613A616363656E74323E3C613A616363656E74333E3C613A73726762436C72" +
      "2076616C3D22414141414646222F3E3C2F613A616363656E74333E3C613A6163" +
      "63656E74343E3C613A73726762436C722076616C3D22444144414441222F3E3C" +
      "2F613A616363656E74343E3C613A616363656E74353E3C613A73726762436C72" +
      "2076616C3D22464643414141222F3E3C2F613A616363656E74353E3C613A6163" +
      "63656E74363E3C613A73726762436C722076616C3D22303045374537222F3E3C" +
      "2F613A616363656E74363E3C613A686C696E6B3E3C613A73726762436C722076" +
      "616C3D22464630303030222F3E3C2F613A686C696E6B3E3C613A666F6C486C69" +
      "6E6B3E3C613A73726762436C722076616C3D22393639363936222F3E3C2F613A" +
      "666F6C486C696E6B3E3C2F613A636C72536368656D653E3C613A636C724D6170" +
      "206267313D22646B3222207478313D226C743122206267323D22646B31222074" +
      "78323D226C74322220616363656E74313D22616363656E74312220616363656E" +
      "74323D22616363656E74322220616363656E74333D22616363656E7433222061" +
      "6363656E74343D22616363656E74342220616363656E74353D22616363656E74" +
      "352220616363656E74363D22616363656E74362220686C696E6B3D22686C696E" +
      "6B2220666F6C486C696E6B3D22666F6C486C696E6B222F3E3C2F613A65787472" +
      "61436C72536368656D653E3C613A6578747261436C72536368656D653E3C613A" +
      "636C72536368656D65206E616D653D22426C616E6B2050726573656E74617469" +
      "6F6E2033223E3C613A646B313E3C613A73726762436C722076616C3D22303030" +
      "303030222F3E3C2F613A646B313E3C613A6C74313E3C613A73726762436C7220" +
      "76616C3D22464646464343222F3E3C2F613A6C74313E3C613A646B323E3C613A" +
      "73726762436C722076616C3D22383038303030222F3E3C2F613A646B323E3C61" +
      "3A6C74323E3C613A73726762436C722076616C3D22363636363333222F3E3C2F" +
      "613A6C74323E3C613A616363656E74313E3C613A73726762436C722076616C3D" +
      "22333339393333222F3E3C2F613A616363656E74313E3C613A616363656E7432" +
      "3E3C613A73726762436C722076616C3D22383030303030222F3E3C2F613A6163" +
      "63656E74323E3C613A616363656E74333E3C613A73726762436C722076616C3D" +
      "22464646464532222F3E3C2F613A616363656E74333E3C613A616363656E7434" +
      "3E3C613A73726762436C722076616C3D22303030303030222F3E3C2F613A6163" +
      "63656E74343E3C613A616363656E74353E3C613A73726762436C722076616C3D" +
      "22414443414144222F3E3C2F613A616363656E74353E3C613A616363656E7436" +
      "3E3C613A73726762436C722076616C3D22373330303030222F3E3C2F613A6163" +
      "63656E74363E3C613A686C696E6B3E3C613A73726762436C722076616C3D2230" +
      "3033334343222F3E3C2F613A686C696E6B3E3C613A666F6C486C696E6B3E3C61" +
      "3A73726762436C722076616C3D22464643433636222F3E3C2F613A666F6C486C" +
      "696E6B3E3C2F613A636C72536368656D653E3C613A636C724D6170206267313D" +
      "226C743122207478313D22646B3122206267323D226C743222207478323D2264" +
      "6B322220616363656E74313D22616363656E74312220616363656E74323D2261" +
      "6363656E74322220616363656E74333D22616363656E74332220616363656E74" +
      "343D22616363656E74342220616363656E74353D22616363656E743522206163" +
      "63656E74363D22616363656E74362220686C696E6B3D22686C696E6B2220666F" +
      "6C486C696E6B3D22666F6C486C696E6B222F3E3C2F613A6578747261436C7253" +
      "6368656D653E3C613A6578747261436C72536368656D653E3C613A636C725363" +
      "68656D65206E616D653D22426C616E6B2050726573656E746174696F6E203422" +
      "3E3C613A646B313E3C613A73726762436C722076616C3D22303030303030222F" +
      "3E3C2F613A646B313E3C613A6C74313E3C613A73726762436C722076616C3D22" +
      "464646464646222F3E3C2F613A6C74313E3C613A646B323E3C613A7372676243" +
      "6C722076616C3D22303030303030222F3E3C2F613A646B323E3C613A6C74323E" +
      "3C613A73726762436C722076616C3D22333333333333222F3E3C2F613A6C7432" +
      "3E3C613A616363656E74313E3C613A73726762436C722076616C3D2244444444" +
      "4444222F3E3C2F613A616363656E74313E3C613A616363656E74323E3C613A73" +
      "726762436C722076616C3D22383038303830222F3E3C2F613A616363656E7432" +
      "3E3C613A616363656E74333E3C613A73726762436C722076616C3D2246464646" +
      "4646222F3E3C2F613A616363656E74333E3C613A616363656E74343E3C613A73" +
      "726762436C722076616C3D22303030303030222F3E3C2F613A616363656E7434" +
      "3E3C613A616363656E74353E3C613A73726762436C722076616C3D2245424542" +
      "4542222F3E3C2F613A616363656E74353E3C613A616363656E74363E3C613A73" +
      "726762436C722076616C3D22373337333733222F3E3C2F613A616363656E7436" +
      "3E3C613A686C696E6B3E3C613A73726762436C722076616C3D22344434443444" +
      "222F3E3C2F613A686C696E6B3E3C613A666F6C486C696E6B3E3C613A73726762" +
      "436C722076616C3D22454145414541222F3E3C2F613A666F6C486C696E6B3E3C" +
      "2F613A636C72536368656D653E3C613A636C724D6170206267313D226C743122" +
      "207478313D22646B3122206267323D226C743222207478323D22646B32222061" +
      "6363656E74313D22616363656E74312220616363656E74323D22616363656E74" +
      "322220616363656E74333D22616363656E74332220616363656E74343D226163" +
      "63656E74342220616363656E74353D22616363656E74352220616363656E7436" +
      "3D22616363656E74362220686C696E6B3D22686C696E6B2220666F6C486C696E" +
      "6B3D22666F6C486C696E6B222F3E3C2F613A6578747261436C72536368656D65" +
      "3E3C613A6578747261436C72536368656D653E3C613A636C72536368656D6520" +
      "6E616D653D22426C616E6B2050726573656E746174696F6E2035223E3C613A64" +
      "6B313E3C613A73726762436C722076616C3D22303030303030222F3E3C2F613A" +
      "646B313E3C613A6C74313E3C613A73726762436C722076616C3D224646464646" +
      "46222F3E3C2F613A6C74313E3C613A646B323E3C613A73726762436C72207661" +
      "6C3D22303030303030222F3E3C2F613A646B323E3C613A6C74323E3C613A7372" +
      "6762436C722076616C3D22383038303830222F3E3C2F613A6C74323E3C613A61" +
      "6363656E74313E3C613A73726762436C722076616C3D22464643433636222F3E" +
      "3C2F613A616363656E74313E3C613A616363656E74323E3C613A73726762436C" +
      "722076616C3D22303030304646222F3E3C2F613A616363656E74323E3C613A61" +
      "6363656E74333E3C613A73726762436C722076616C3D22464646464646222F3E" +
      "3C2F613A616363656E74333E3C613A616363656E74343E3C613A73726762436C" +
      "722076616C3D22303030303030222F3E3C2F613A616363656E74343E3C613A61" +
      "6363656E74353E3C613A73726762436C722076616C3D22464645324238222F3E" +
      "3C2F613A616363656E74353E3C613A616363656E74363E3C613A73726762436C" +
      "722076616C3D22303030304537222F3E3C2F613A616363656E74363E3C613A68" +
      "6C696E6B3E3C613A73726762436C722076616C3D22434330304343222F3E3C2F" +
      "613A686C696E6B3E3C613A666F6C486C696E6B3E3C613A73726762436C722076" +
      "616C3D22433043304330222F3E3C2F613A666F6C486C696E6B3E3C2F613A636C" +
      "72536368656D653E3C613A636C724D6170206267313D226C743122207478313D" +
      "22646B3122206267323D226C743222207478323D22646B322220616363656E74" +
      "313D22616363656E74312220616363656E74323D22616363656E743222206163" +
      "63656E74333D22616363656E74332220616363656E74343D22616363656E7434" +
      "2220616363656E74353D22616363656E74352220616363656E74363D22616363" +
      "656E74362220686C696E6B3D22686C696E6B2220666F6C486C696E6B3D22666F" +
      "6C486C696E6B222F3E3C2F613A6578747261436C72536368656D653E3C613A65" +
      "78747261436C72536368656D653E3C613A636C72536368656D65206E616D653D" +
      "22426C616E6B2050726573656E746174696F6E2036223E3C613A646B313E3C61" +
      "3A73726762436C722076616C3D22303030303030222F3E3C2F613A646B313E3C" +
      "613A6C74313E3C613A73726762436C722076616C3D22464646464646222F3E3C" +
      "2F613A6C74313E3C613A646B323E3C613A73726762436C722076616C3D223030" +
      "30303030222F3E3C2F613A646B323E3C613A6C74323E3C613A73726762436C72" +
      "2076616C3D22383038303830222F3E3C2F613A6C74323E3C613A616363656E74" +
      "313E3C613A73726762436C722076616C3D22433043304330222F3E3C2F613A61" +
      "6363656E74313E3C613A616363656E74323E3C613A73726762436C722076616C" +
      "3D22303036364646222F3E3C2F613A616363656E74323E3C613A616363656E74" +
      "333E3C613A73726762436C722076616C3D22464646464646222F3E3C2F613A61" +
      "6363656E74333E3C613A616363656E74343E3C613A73726762436C722076616C" +
      "3D22303030303030222F3E3C2F613A616363656E74343E3C613A616363656E74" +
      "353E3C613A73726762436C722076616C3D22444344434443222F3E3C2F613A61" +
      "6363656E74353E3C613A616363656E74363E3C613A73726762436C722076616C" +
      "3D22303035434537222F3E3C2F613A616363656E74363E3C613A686C696E6B3E" +
      "3C613A73726762436C722076616C3D22464630303030222F3E3C2F613A686C69" +
      "6E6B3E3C613A666F6C486C696E6B3E3C613A73726762436C722076616C3D2230" +
      "3039393030222F3E3C2F613A666F6C486C696E6B3E3C2F613A636C7253636865" +
      "6D653E3C613A636C724D6170206267313D226C743122207478313D22646B3122" +
      "206267323D226C743222207478323D22646B322220616363656E74313D226163" +
      "63656E74312220616363656E74323D22616363656E74322220616363656E7433" +
      "3D22616363656E74332220616363656E74343D22616363656E74342220616363" +
      "656E74353D22616363656E74352220616363656E74363D22616363656E743622" +
      "20686C696E6B3D22686C696E6B2220666F6C486C696E6B3D22666F6C486C696E" +
      "6B222F3E3C2F613A6578747261436C72536368656D653E3C613A657874726143" +
      "6C72536368656D653E3C613A636C72536368656D65206E616D653D22426C616E" +
      "6B2050726573656E746174696F6E2037223E3C613A646B313E3C613A73726762" +
      "436C722076616C3D22303030303030222F3E3C2F613A646B313E3C613A6C7431" +
      "3E3C613A73726762436C722076616C3D22464646464646222F3E3C2F613A6C74" +
      "313E3C613A646B323E3C613A73726762436C722076616C3D2230303030303022" +
      "2F3E3C2F613A646B323E3C613A6C74323E3C613A73726762436C722076616C3D" +
      "22383038303830222F3E3C2F613A6C74323E3C613A616363656E74313E3C613A" +
      "73726762436C722076616C3D22333339394646222F3E3C2F613A616363656E74" +
      "313E3C613A616363656E74323E3C613A73726762436C722076616C3D22393946" +
      "464343222F3E3C2F613A616363656E74323E3C613A616363656E74333E3C613A" +
      "73726762436C722076616C3D22464646464646222F3E3C2F613A616363656E74" +
      "333E3C613A616363656E74343E3C613A73726762436C722076616C3D22303030" +
      "303030222F3E3C2F613A616363656E74343E3C613A616363656E74353E3C613A" +
      "73726762436C722076616C3D22414443414646222F3E3C2F613A616363656E74" +
      "353E3C613A616363656E74363E3C613A73726762436C722076616C3D22384145" +
      "374239222F3E3C2F613A616363656E74363E3C613A686C696E6B3E3C613A7372" +
      "6762436C722076616C3D22434330304343222F3E3C2F613A686C696E6B3E3C61" +
      "3A666F6C486C696E6B3E3C613A73726762436C722076616C3D22423242324232" +
      "222F3E3C2F613A666F6C486C696E6B3E3C2F613A636C72536368656D653E3C61" +
      "3A636C724D6170206267313D226C743122207478313D22646B3122206267323D" +
      "226C743222207478323D22646B322220616363656E74313D22616363656E7431" +
      "2220616363656E74323D22616363656E74322220616363656E74333D22616363" +
      "656E74332220616363656E74343D22616363656E74342220616363656E74353D" +
      "22616363656E74352220616363656E74363D22616363656E74362220686C696E" +
      "6B3D22686C696E6B2220666F6C486C696E6B3D22666F6C486C696E6B222F3E3C" +
      "2F613A6578747261436C72536368656D653E3C2F613A6578747261436C725363" +
      "68656D654C73743E3C2F613A7468656D653E";

   public static final String THEME_2 =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C613A7468656D" +
      "6520786D6C6E733A613D22687474703A2F2F736368656D61732E6F70656E786D" +
      "6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F6D61696E" +
      "22206E616D653D224F6666696365205468656D65223E3C613A7468656D65456C" +
      "656D656E74733E3C613A636C72536368656D65206E616D653D22223E3C613A64" +
      "6B313E3C613A73726762436C722076616C3D22303030303030222F3E3C2F613A" +
      "646B313E3C613A6C74313E3C613A73726762436C722076616C3D224646464646" +
      "46222F3E3C2F613A6C74313E3C613A646B323E3C613A73726762436C72207661" +
      "6C3D22303030303030222F3E3C2F613A646B323E3C613A6C74323E3C613A7372" +
      "6762436C722076616C3D22383038303830222F3E3C2F613A6C74323E3C613A61" +
      "6363656E74313E3C613A73726762436C722076616C3D22424245304533222F3E" +
      "3C2F613A616363656E74313E3C613A616363656E74323E3C613A73726762436C" +
      "722076616C3D22333333333939222F3E3C2F613A616363656E74323E3C613A61" +
      "6363656E74333E3C613A73726762436C722076616C3D22464646464646222F3E" +
      "3C2F613A616363656E74333E3C613A616363656E74343E3C613A73726762436C" +
      "722076616C3D22303030303030222F3E3C2F613A616363656E74343E3C613A61" +
      "6363656E74353E3C613A73726762436C722076616C3D22444145444546222F3E" +
      "3C2F613A616363656E74353E3C613A616363656E74363E3C613A73726762436C" +
      "722076616C3D22324432443841222F3E3C2F613A616363656E74363E3C613A68" +
      "6C696E6B3E3C613A73726762436C722076616C3D22303039393939222F3E3C2F" +
      "613A686C696E6B3E3C613A666F6C486C696E6B3E3C613A73726762436C722076" +
      "616C3D22393943433030222F3E3C2F613A666F6C486C696E6B3E3C2F613A636C" +
      "72536368656D653E3C613A666F6E74536368656D65206E616D653D224F666669" +
      "6365223E3C613A6D616A6F72466F6E743E3C613A6C6174696E20747970656661" +
      "63653D2243616C69627269222F3E3C613A65612074797065666163653D22222F" +
      "3E3C613A63732074797065666163653D22222F3E3C613A666F6E742073637269" +
      "70743D224A70616E222074797065666163653D22EFBCADEFBCB320EFBCB0E382" +
      "B4E382B7E38383E382AF222F3E3C613A666F6E74207363726970743D2248616E" +
      "67222074797065666163653D22EBA791EC9D8020EAB3A0EB9495222F3E3C613A" +
      "666F6E74207363726970743D2248616E73222074797065666163653D22E5AE8B" +
      "E4BD93222F3E3C613A666F6E74207363726970743D2248616E74222074797065" +
      "666163653D22E696B0E7B4B0E6988EE9AB94222F3E3C613A666F6E7420736372" +
      "6970743D2241726162222074797065666163653D2254696D6573204E65772052" +
      "6F6D616E222F3E3C613A666F6E74207363726970743D22486562722220747970" +
      "65666163653D2254696D6573204E657720526F6D616E222F3E3C613A666F6E74" +
      "207363726970743D2254686169222074797065666163653D22416E6773616E61" +
      "204E6577222F3E3C613A666F6E74207363726970743D22457468692220747970" +
      "65666163653D224E79616C61222F3E3C613A666F6E74207363726970743D2242" +
      "656E67222074797065666163653D225672696E6461222F3E3C613A666F6E7420" +
      "7363726970743D2247756A72222074797065666163653D22536872757469222F" +
      "3E3C613A666F6E74207363726970743D224B686D72222074797065666163653D" +
      "224D6F6F6C426F72616E222F3E3C613A666F6E74207363726970743D224B6E64" +
      "61222074797065666163653D2254756E6761222F3E3C613A666F6E7420736372" +
      "6970743D2247757275222074797065666163653D225261617669222F3E3C613A" +
      "666F6E74207363726970743D2243616E73222074797065666163653D22457570" +
      "68656D6961222F3E3C613A666F6E74207363726970743D224368657222207479" +
      "7065666163653D22506C616E746167656E657420436865726F6B6565222F3E3C" +
      "613A666F6E74207363726970743D2259696969222074797065666163653D224D" +
      "6963726F736F6674205969204261697469222F3E3C613A666F6E742073637269" +
      "70743D2254696274222074797065666163653D224D6963726F736F6674204869" +
      "6D616C617961222F3E3C613A666F6E74207363726970743D2254686161222074" +
      "797065666163653D224D5620426F6C69222F3E3C613A666F6E74207363726970" +
      "743D2244657661222074797065666163653D224D616E67616C222F3E3C613A66" +
      "6F6E74207363726970743D2254656C75222074797065666163653D2247617574" +
      "616D69222F3E3C613A666F6E74207363726970743D2254616D6C222074797065" +
      "666163653D224C61746861222F3E3C613A666F6E74207363726970743D225379" +
      "7263222074797065666163653D2245737472616E67656C6F2045646573736122" +
      "2F3E3C613A666F6E74207363726970743D224F72796122207479706566616365" +
      "3D224B616C696E6761222F3E3C613A666F6E74207363726970743D224D6C796D" +
      "222074797065666163653D224B617274696B61222F3E3C613A666F6E74207363" +
      "726970743D224C616F6F222074797065666163653D22446F6B4368616D706122" +
      "2F3E3C613A666F6E74207363726970743D2253696E6822207479706566616365" +
      "3D2249736B6F6F6C6120506F7461222F3E3C613A666F6E74207363726970743D" +
      "224D6F6E67222074797065666163653D224D6F6E676F6C69616E204261697469" +
      "222F3E3C613A666F6E74207363726970743D2256696574222074797065666163" +
      "653D2254696D6573204E657720526F6D616E222F3E3C613A666F6E7420736372" +
      "6970743D2255696768222074797065666163653D224D6963726F736F66742055" +
      "6967687572222F3E3C2F613A6D616A6F72466F6E743E3C613A6D696E6F72466F" +
      "6E743E3C613A6C6174696E2074797065666163653D2243616C69627269222F3E" +
      "3C613A65612074797065666163653D22222F3E3C613A63732074797065666163" +
      "653D22222F3E3C613A666F6E74207363726970743D224A70616E222074797065" +
      "666163653D22EFBCADEFBCB320EFBCB0E382B4E382B7E38383E382AF222F3E3C" +
      "613A666F6E74207363726970743D2248616E67222074797065666163653D22EB" +
      "A791EC9D8020EAB3A0EB9495222F3E3C613A666F6E74207363726970743D2248" +
      "616E73222074797065666163653D22E5AE8BE4BD93222F3E3C613A666F6E7420" +
      "7363726970743D2248616E74222074797065666163653D22E696B0E7B4B0E698" +
      "8EE9AB94222F3E3C613A666F6E74207363726970743D22417261622220747970" +
      "65666163653D22417269616C222F3E3C613A666F6E74207363726970743D2248" +
      "656272222074797065666163653D22417269616C222F3E3C613A666F6E742073" +
      "63726970743D2254686169222074797065666163653D22436F72646961204E65" +
      "77222F3E3C613A666F6E74207363726970743D22457468692220747970656661" +
      "63653D224E79616C61222F3E3C613A666F6E74207363726970743D2242656E67" +
      "222074797065666163653D225672696E6461222F3E3C613A666F6E7420736372" +
      "6970743D2247756A72222074797065666163653D22536872757469222F3E3C61" +
      "3A666F6E74207363726970743D224B686D72222074797065666163653D224461" +
      "756E50656E68222F3E3C613A666F6E74207363726970743D224B6E6461222074" +
      "797065666163653D2254756E6761222F3E3C613A666F6E74207363726970743D" +
      "2247757275222074797065666163653D225261617669222F3E3C613A666F6E74" +
      "207363726970743D2243616E73222074797065666163653D2245757068656D69" +
      "61222F3E3C613A666F6E74207363726970743D22436865722220747970656661" +
      "63653D22506C616E746167656E657420436865726F6B6565222F3E3C613A666F" +
      "6E74207363726970743D2259696969222074797065666163653D224D6963726F" +
      "736F6674205969204261697469222F3E3C613A666F6E74207363726970743D22" +
      "54696274222074797065666163653D224D6963726F736F66742048696D616C61" +
      "7961222F3E3C613A666F6E74207363726970743D225468616122207479706566" +
      "6163653D224D5620426F6C69222F3E3C613A666F6E74207363726970743D2244" +
      "657661222074797065666163653D224D616E67616C222F3E3C613A666F6E7420" +
      "7363726970743D2254656C75222074797065666163653D2247617574616D6922" +
      "2F3E3C613A666F6E74207363726970743D2254616D6C22207479706566616365" +
      "3D224C61746861222F3E3C613A666F6E74207363726970743D22537972632220" +
      "74797065666163653D2245737472616E67656C6F20456465737361222F3E3C61" +
      "3A666F6E74207363726970743D224F727961222074797065666163653D224B61" +
      "6C696E6761222F3E3C613A666F6E74207363726970743D224D6C796D22207479" +
      "7065666163653D224B617274696B61222F3E3C613A666F6E7420736372697074" +
      "3D224C616F6F222074797065666163653D22446F6B4368616D7061222F3E3C61" +
      "3A666F6E74207363726970743D2253696E68222074797065666163653D224973" +
      "6B6F6F6C6120506F7461222F3E3C613A666F6E74207363726970743D224D6F6E" +
      "67222074797065666163653D224D6F6E676F6C69616E204261697469222F3E3C" +
      "613A666F6E74207363726970743D2256696574222074797065666163653D2241" +
      "7269616C222F3E3C613A666F6E74207363726970743D22556967682220747970" +
      "65666163653D224D6963726F736F667420556967687572222F3E3C2F613A6D69" +
      "6E6F72466F6E743E3C2F613A666F6E74536368656D653E3C613A666D74536368" +
      "656D65206E616D653D224F6666696365223E3C613A66696C6C5374796C654C73" +
      "743E3C613A736F6C696446696C6C3E3C613A736368656D65436C722076616C3D" +
      "227068436C72222F3E3C2F613A736F6C696446696C6C3E3C613A677261644669" +
      "6C6C20726F745769746853686170653D2231223E3C613A67734C73743E3C613A" +
      "677320706F733D2230223E3C613A736368656D65436C722076616C3D22706843" +
      "6C72223E3C613A74696E742076616C3D223530303030222F3E3C613A7361744D" +
      "6F642076616C3D22333030303030222F3E3C2F613A736368656D65436C723E3C" +
      "2F613A67733E3C613A677320706F733D223335303030223E3C613A736368656D" +
      "65436C722076616C3D227068436C72223E3C613A74696E742076616C3D223337" +
      "303030222F3E3C613A7361744D6F642076616C3D22333030303030222F3E3C2F" +
      "613A736368656D65436C723E3C2F613A67733E3C613A677320706F733D223130" +
      "30303030223E3C613A736368656D65436C722076616C3D227068436C72223E3C" +
      "613A74696E742076616C3D223135303030222F3E3C613A7361744D6F64207661" +
      "6C3D22333530303030222F3E3C2F613A736368656D65436C723E3C2F613A6773" +
      "3E3C2F613A67734C73743E3C613A6C696E20616E673D22313632303030303022" +
      "207363616C65643D2231222F3E3C2F613A6772616446696C6C3E3C613A677261" +
      "6446696C6C20726F745769746853686170653D2231223E3C613A67734C73743E" +
      "3C613A677320706F733D2230223E3C613A736368656D65436C722076616C3D22" +
      "7068436C72223E3C613A73686164652076616C3D223531303030222F3E3C613A" +
      "7361744D6F642076616C3D22313330303030222F3E3C2F613A736368656D6543" +
      "6C723E3C2F613A67733E3C613A677320706F733D223830303030223E3C613A73" +
      "6368656D65436C722076616C3D227068436C72223E3C613A7368616465207661" +
      "6C3D223933303030222F3E3C613A7361744D6F642076616C3D22313330303030" +
      "222F3E3C2F613A736368656D65436C723E3C2F613A67733E3C613A677320706F" +
      "733D22313030303030223E3C613A736368656D65436C722076616C3D22706843" +
      "6C72223E3C613A73686164652076616C3D223934303030222F3E3C613A736174" +
      "4D6F642076616C3D22313335303030222F3E3C2F613A736368656D65436C723E" +
      "3C2F613A67733E3C2F613A67734C73743E3C613A6C696E20616E673D22313632" +
      "303030303022207363616C65643D2230222F3E3C2F613A6772616446696C6C3E" +
      "3C2F613A66696C6C5374796C654C73743E3C613A6C6E5374796C654C73743E3C" +
      "613A6C6E20773D223935323522206361703D22666C61742220636D70643D2273" +
      "6E672220616C676E3D22637472223E3C613A736F6C696446696C6C3E3C613A73" +
      "6368656D65436C722076616C3D227068436C72223E3C613A7368616465207661" +
      "6C3D223935303030222F3E3C613A7361744D6F642076616C3D22313035303030" +
      "222F3E3C2F613A736368656D65436C723E3C2F613A736F6C696446696C6C3E3C" +
      "613A70727374446173682076616C3D22736F6C6964222F3E3C2F613A6C6E3E3C" +
      "613A6C6E20773D22323534303022206361703D22666C61742220636D70643D22" +
      "736E672220616C676E3D22637472223E3C613A736F6C696446696C6C3E3C613A" +
      "736368656D65436C722076616C3D227068436C72222F3E3C2F613A736F6C6964" +
      "46696C6C3E3C613A70727374446173682076616C3D22736F6C6964222F3E3C2F" +
      "613A6C6E3E3C613A6C6E20773D22333831303022206361703D22666C61742220" +
      "636D70643D22736E672220616C676E3D22637472223E3C613A736F6C69644669" +
      "6C6C3E3C613A736368656D65436C722076616C3D227068436C72222F3E3C2F61" +
      "3A736F6C696446696C6C3E3C613A70727374446173682076616C3D22736F6C69" +
      "64222F3E3C2F613A6C6E3E3C2F613A6C6E5374796C654C73743E3C613A656666" +
      "6563745374796C654C73743E3C613A6566666563745374796C653E3C613A6566" +
      "666563744C73743E3C613A6F757465725368647720626C75725261643D223430" +
      "3030302220646973743D22323030303022206469723D22353430303030302220" +
      "726F745769746853686170653D2230223E3C613A73726762436C722076616C3D" +
      "22303030303030223E3C613A616C7068612076616C3D223338303030222F3E3C" +
      "2F613A73726762436C723E3C2F613A6F75746572536864773E3C2F613A656666" +
      "6563744C73743E3C2F613A6566666563745374796C653E3C613A656666656374" +
      "5374796C653E3C613A6566666563744C73743E3C613A6F757465725368647720" +
      "626C75725261643D2234303030302220646973743D2232333030302220646972" +
      "3D22353430303030302220726F745769746853686170653D2230223E3C613A73" +
      "726762436C722076616C3D22303030303030223E3C613A616C7068612076616C" +
      "3D223335303030222F3E3C2F613A73726762436C723E3C2F613A6F7574657253" +
      "6864773E3C2F613A6566666563744C73743E3C2F613A6566666563745374796C" +
      "653E3C613A6566666563745374796C653E3C613A6566666563744C73743E3C61" +
      "3A6F757465725368647720626C75725261643D2234303030302220646973743D" +
      "22323330303022206469723D22353430303030302220726F7457697468536861" +
      "70653D2230223E3C613A73726762436C722076616C3D22303030303030223E3C" +
      "613A616C7068612076616C3D223335303030222F3E3C2F613A73726762436C72" +
      "3E3C2F613A6F75746572536864773E3C2F613A6566666563744C73743E3C613A" +
      "7363656E6533643E3C613A63616D65726120707273743D226F7274686F677261" +
      "7068696346726F6E74223E3C613A726F74206C61743D223022206C6F6E3D2230" +
      "22207265763D2230222F3E3C2F613A63616D6572613E3C613A6C696768745269" +
      "67207269673D227468726565507422206469723D2274223E3C613A726F74206C" +
      "61743D223022206C6F6E3D223022207265763D2231323030303030222F3E3C2F" +
      "613A6C696768745269673E3C2F613A7363656E6533643E3C613A737033643E3C" +
      "613A626576656C5420773D2236333530302220683D223235343030222F3E3C2F" +
      "613A737033643E3C2F613A6566666563745374796C653E3C2F613A6566666563" +
      "745374796C654C73743E3C613A626746696C6C5374796C654C73743E3C613A73" +
      "6F6C696446696C6C3E3C613A736368656D65436C722076616C3D227068436C72" +
      "222F3E3C2F613A736F6C696446696C6C3E3C613A6772616446696C6C20726F74" +
      "5769746853686170653D2231223E3C613A67734C73743E3C613A677320706F73" +
      "3D2230223E3C613A736368656D65436C722076616C3D227068436C72223E3C61" +
      "3A74696E742076616C3D223430303030222F3E3C613A7361744D6F642076616C" +
      "3D22333530303030222F3E3C2F613A736368656D65436C723E3C2F613A67733E" +
      "3C613A677320706F733D223430303030223E3C613A736368656D65436C722076" +
      "616C3D227068436C72223E3C613A74696E742076616C3D223435303030222F3E" +
      "3C613A73686164652076616C3D223939303030222F3E3C613A7361744D6F6420" +
      "76616C3D22333530303030222F3E3C2F613A736368656D65436C723E3C2F613A" +
      "67733E3C613A677320706F733D22313030303030223E3C613A736368656D6543" +
      "6C722076616C3D227068436C72223E3C613A73686164652076616C3D22323030" +
      "3030222F3E3C613A7361744D6F642076616C3D22323535303030222F3E3C2F61" +
      "3A736368656D65436C723E3C2F613A67733E3C2F613A67734C73743E3C613A70" +
      "61746820706174683D22636972636C65223E3C613A66696C6C546F5265637420" +
      "6C3D2235303030302220743D222D38303030302220723D223530303030222062" +
      "3D22313830303030222F3E3C2F613A706174683E3C2F613A6772616446696C6C" +
      "3E3C613A6772616446696C6C20726F745769746853686170653D2231223E3C61" +
      "3A67734C73743E3C613A677320706F733D2230223E3C613A736368656D65436C" +
      "722076616C3D227068436C72223E3C613A74696E742076616C3D223830303030" +
      "222F3E3C613A7361744D6F642076616C3D22333030303030222F3E3C2F613A73" +
      "6368656D65436C723E3C2F613A67733E3C613A677320706F733D223130303030" +
      "30223E3C613A736368656D65436C722076616C3D227068436C72223E3C613A73" +
      "686164652076616C3D223330303030222F3E3C613A7361744D6F642076616C3D" +
      "22323030303030222F3E3C2F613A736368656D65436C723E3C2F613A67733E3C" +
      "2F613A67734C73743E3C613A7061746820706174683D22636972636C65223E3C" +
      "613A66696C6C546F52656374206C3D2235303030302220743D22353030303022" +
      "20723D2235303030302220623D223530303030222F3E3C2F613A706174683E3C" +
      "2F613A6772616446696C6C3E3C2F613A626746696C6C5374796C654C73743E3C" +
      "2F613A666D74536368656D653E3C2F613A7468656D65456C656D656E74733E3C" +
      "613A6F626A65637444656661756C74732F3E3C613A6578747261436C72536368" +
      "656D654C73742F3E3C2F613A7468656D653E";

   public static final String VIEW_PROPS =
      "3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554" +
      "462D3822207374616E64616C6F6E653D22796573223F3E0D0A3C703A76696577" +
      "507220786D6C6E733A613D22687474703A2F2F736368656D61732E6F70656E78" +
      "6D6C666F726D6174732E6F72672F64726177696E676D6C2F323030362F6D6169" +
      "6E2220786D6C6E733A723D22687474703A2F2F736368656D61732E6F70656E78" +
      "6D6C666F726D6174732E6F72672F6F6666696365446F63756D656E742F323030" +
      "362F72656C6174696F6E73686970732220786D6C6E733A6D633D22687474703A" +
      "2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F6D6172" +
      "6B75702D636F6D7061746962696C6974792F323030362220786D6C6E733A6D76" +
      "3D2275726E3A736368656D61732D6D6963726F736F66742D636F6D3A6D61633A" +
      "766D6C22206D633A49676E6F7261626C653D226D7622206D633A507265736572" +
      "7665417474726962757465733D226D763A2A2220786D6C6E733A703D22687474" +
      "703A2F2F736368656D61732E6F70656E786D6C666F726D6174732E6F72672F70" +
      "726573656E746174696F6E6D6C2F323030362F6D61696E223E3C703A6E6F726D" +
      "616C5669657750722070726566657253696E676C65566965773D2231223E3C70" +
      "3A726573746F7265644C65667420737A3D223332373837222F3E3C703A726573" +
      "746F726564546F7020737A3D223930393239222F3E3C2F703A6E6F726D616C56" +
      "69657750723E3C703A736C6964655669657750723E3C703A63536C6456696577" +
      "50722073686F774775696465733D2231223E3C703A635669657750723E3C703A" +
      "7363616C653E3C613A7378206E3D223130352220643D22313030222F3E3C613A" +
      "7379206E3D223130352220643D22313030222F3E3C2F703A7363616C653E3C70" +
      "3A6F726967696E20783D222D313133362220793D222D313034222F3E3C2F703A" +
      "635669657750723E3C703A67756964654C73743E3C703A6775696465206F7269" +
      "656E743D22686F727A2220706F733D2232313630222F3E3C703A677569646520" +
      "706F733D2232383830222F3E3C2F703A67756964654C73743E3C2F703A63536C" +
      "645669657750723E3C2F703A736C6964655669657750723E3C703A6F75746C69" +
      "6E655669657750723E3C703A635669657750723E3C703A7363616C653E3C613A" +
      "7378206E3D2233332220643D22313030222F3E3C613A7379206E3D2233332220" +
      "643D22313030222F3E3C2F703A7363616C653E3C703A6F726967696E20783D22" +
      "302220793D2230222F3E3C2F703A635669657750723E3C2F703A6F75746C696E" +
      "655669657750723E3C703A6E6F746573546578745669657750723E3C703A6356" +
      "69657750723E3C703A7363616C653E3C613A7378206E3D223130302220643D22" +
      "313030222F3E3C613A7379206E3D223130302220643D22313030222F3E3C2F70" +
      "3A7363616C653E3C703A6F726967696E20783D22302220793D2230222F3E3C2F" +
      "703A635669657750723E3C2F703A6E6F746573546578745669657750723E3C70" +
      "3A736F727465725669657750723E3C703A635669657750723E3C703A7363616C" +
      "653E3C613A7378206E3D2236362220643D22313030222F3E3C613A7379206E3D" +
      "2236362220643D22313030222F3E3C2F703A7363616C653E3C703A6F72696769" +
      "6E20783D22302220793D2230222F3E3C2F703A635669657750723E3C2F703A73" +
      "6F727465725669657750723E3C703A6772696453706163696E672063783D2237" +
      "38303238383030222063793D223738303238383030222F3E3C2F703A76696577" +
      "50723E";

   public static final String PRINTER_SETTINGS_1 =
      "0200000001000000201800003C3F786D6C2076657273696F6E3D22312E302220" +
      "656E636F64696E673D225554462D38223F3E0A3C21444F435459504520706C69" +
      "7374205055424C494320222D2F2F4170706C652F2F44544420504C4953542031" +
      "2E302F2F454E222022687474703A2F2F7777772E6170706C652E636F6D2F4454" +
      "44732F50726F70657274794C6973742D312E302E647464223E0A3C706C697374" +
      "2076657273696F6E3D22312E30223E0A3C646963743E0A093C6B65793E636F6D" +
      "2E6170706C652E7072696E742E50616765466F726D61742E504D486F72697A6F" +
      "6E74616C5265733C2F6B65793E0A093C646963743E0A09093C6B65793E636F6D" +
      "2E6170706C652E7072696E742E7469636B65742E63726561746F723C2F6B6579" +
      "3E0A09093C737472696E673E636F6D2E6170706C652E6A6F627469636B65743C" +
      "2F737472696E673E0A09093C6B65793E636F6D2E6170706C652E7072696E742E" +
      "7469636B65742E6974656D41727261793C2F6B65793E0A09093C61727261793E" +
      "0A0909093C646963743E0A090909093C6B65793E636F6D2E6170706C652E7072" +
      "696E742E50616765466F726D61742E504D486F72697A6F6E74616C5265733C2F" +
      "6B65793E0A090909093C7265616C3E37323C2F7265616C3E0A090909093C6B65" +
      "793E636F6D2E6170706C652E7072696E742E7469636B65742E7374617465466C" +
      "61673C2F6B65793E0A090909093C696E74656765723E303C2F696E7465676572" +
      "3E0A0909093C2F646963743E0A09093C2F61727261793E0A093C2F646963743E" +
      "0A093C6B65793E636F6D2E6170706C652E7072696E742E50616765466F726D61" +
      "742E504D4F7269656E746174696F6E3C2F6B65793E0A093C646963743E0A0909" +
      "3C6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E63726561" +
      "746F723C2F6B65793E0A09093C737472696E673E636F6D2E6170706C652E6A6F" +
      "627469636B65743C2F737472696E673E0A09093C6B65793E636F6D2E6170706C" +
      "652E7072696E742E7469636B65742E6974656D41727261793C2F6B65793E0A09" +
      "093C61727261793E0A0909093C646963743E0A090909093C6B65793E636F6D2E" +
      "6170706C652E7072696E742E50616765466F726D61742E504D4F7269656E7461" +
      "74696F6E3C2F6B65793E0A090909093C696E74656765723E313C2F696E746567" +
      "65723E0A090909093C6B65793E636F6D2E6170706C652E7072696E742E746963" +
      "6B65742E7374617465466C61673C2F6B65793E0A090909093C696E7465676572" +
      "3E303C2F696E74656765723E0A0909093C2F646963743E0A09093C2F61727261" +
      "793E0A093C2F646963743E0A093C6B65793E636F6D2E6170706C652E7072696E" +
      "742E50616765466F726D61742E504D5363616C696E673C2F6B65793E0A093C64" +
      "6963743E0A09093C6B65793E636F6D2E6170706C652E7072696E742E7469636B" +
      "65742E63726561746F723C2F6B65793E0A09093C737472696E673E636F6D2E61" +
      "70706C652E6A6F627469636B65743C2F737472696E673E0A09093C6B65793E63" +
      "6F6D2E6170706C652E7072696E742E7469636B65742E6974656D41727261793C" +
      "2F6B65793E0A09093C61727261793E0A0909093C646963743E0A090909093C6B" +
      "65793E636F6D2E6170706C652E7072696E742E50616765466F726D61742E504D" +
      "5363616C696E673C2F6B65793E0A090909093C7265616C3E313C2F7265616C3E" +
      "0A090909093C6B65793E636F6D2E6170706C652E7072696E742E7469636B6574" +
      "2E7374617465466C61673C2F6B65793E0A090909093C696E74656765723E303C" +
      "2F696E74656765723E0A0909093C2F646963743E0A09093C2F61727261793E0A" +
      "093C2F646963743E0A093C6B65793E636F6D2E6170706C652E7072696E742E50" +
      "616765466F726D61742E504D566572746963616C5265733C2F6B65793E0A093C" +
      "646963743E0A09093C6B65793E636F6D2E6170706C652E7072696E742E746963" +
      "6B65742E63726561746F723C2F6B65793E0A09093C737472696E673E636F6D2E" +
      "6170706C652E6A6F627469636B65743C2F737472696E673E0A09093C6B65793E" +
      "636F6D2E6170706C652E7072696E742E7469636B65742E6974656D4172726179" +
      "3C2F6B65793E0A09093C61727261793E0A0909093C646963743E0A090909093C" +
      "6B65793E636F6D2E6170706C652E7072696E742E50616765466F726D61742E50" +
      "4D566572746963616C5265733C2F6B65793E0A090909093C7265616C3E37323C" +
      "2F7265616C3E0A090909093C6B65793E636F6D2E6170706C652E7072696E742E" +
      "7469636B65742E7374617465466C61673C2F6B65793E0A090909093C696E7465" +
      "6765723E303C2F696E74656765723E0A0909093C2F646963743E0A09093C2F61" +
      "727261793E0A093C2F646963743E0A093C6B65793E636F6D2E6170706C652E70" +
      "72696E742E50616765466F726D61742E504D566572746963616C5363616C696E" +
      "673C2F6B65793E0A093C646963743E0A09093C6B65793E636F6D2E6170706C65" +
      "2E7072696E742E7469636B65742E63726561746F723C2F6B65793E0A09093C73" +
      "7472696E673E636F6D2E6170706C652E6A6F627469636B65743C2F737472696E" +
      "673E0A09093C6B65793E636F6D2E6170706C652E7072696E742E7469636B6574" +
      "2E6974656D41727261793C2F6B65793E0A09093C61727261793E0A0909093C64" +
      "6963743E0A090909093C6B65793E636F6D2E6170706C652E7072696E742E5061" +
      "6765466F726D61742E504D566572746963616C5363616C696E673C2F6B65793E" +
      "0A090909093C7265616C3E313C2F7265616C3E0A090909093C6B65793E636F6D" +
      "2E6170706C652E7072696E742E7469636B65742E7374617465466C61673C2F6B" +
      "65793E0A090909093C696E74656765723E303C2F696E74656765723E0A090909" +
      "3C2F646963743E0A09093C2F61727261793E0A093C2F646963743E0A093C6B65" +
      "793E636F6D2E6170706C652E7072696E742E7375625469636B65742E70617065" +
      "725F696E666F5F7469636B65743C2F6B65793E0A093C646963743E0A09093C6B" +
      "65793E504D5050445061706572436F64654E616D653C2F6B65793E0A09093C64" +
      "6963743E0A0909093C6B65793E636F6D2E6170706C652E7072696E742E746963" +
      "6B65742E63726561746F723C2F6B65793E0A0909093C737472696E673E636F6D" +
      "2E6170706C652E6A6F627469636B65743C2F737472696E673E0A0909093C6B65" +
      "793E636F6D2E6170706C652E7072696E742E7469636B65742E6974656D417272" +
      "61793C2F6B65793E0A0909093C61727261793E0A090909093C646963743E0A09" +
      "090909093C6B65793E504D5050445061706572436F64654E616D653C2F6B6579" +
      "3E0A09090909093C737472696E673E4C65747465723C2F737472696E673E0A09" +
      "090909093C6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E" +
      "7374617465466C61673C2F6B65793E0A09090909093C696E74656765723E303C" +
      "2F696E74656765723E0A090909093C2F646963743E0A0909093C2F6172726179" +
      "3E0A09093C2F646963743E0A09093C6B65793E504D54696F676150617065724E" +
      "616D653C2F6B65793E0A09093C646963743E0A0909093C6B65793E636F6D2E61" +
      "70706C652E7072696E742E7469636B65742E63726561746F723C2F6B65793E0A" +
      "0909093C737472696E673E636F6D2E6170706C652E6A6F627469636B65743C2F" +
      "737472696E673E0A0909093C6B65793E636F6D2E6170706C652E7072696E742E" +
      "7469636B65742E6974656D41727261793C2F6B65793E0A0909093C6172726179" +
      "3E0A090909093C646963743E0A09090909093C6B65793E504D54696F67615061" +
      "7065724E616D653C2F6B65793E0A09090909093C737472696E673E6E612D6C65" +
      "747465723C2F737472696E673E0A09090909093C6B65793E636F6D2E6170706C" +
      "652E7072696E742E7469636B65742E7374617465466C61673C2F6B65793E0A09" +
      "090909093C696E74656765723E303C2F696E74656765723E0A090909093C2F64" +
      "6963743E0A0909093C2F61727261793E0A09093C2F646963743E0A09093C6B65" +
      "793E636F6D2E6170706C652E7072696E742E50616765466F726D61742E504D41" +
      "646A757374656450616765526563743C2F6B65793E0A09093C646963743E0A09" +
      "09093C6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E6372" +
      "6561746F723C2F6B65793E0A0909093C737472696E673E636F6D2E6170706C65" +
      "2E6A6F627469636B65743C2F737472696E673E0A0909093C6B65793E636F6D2E" +
      "6170706C652E7072696E742E7469636B65742E6974656D41727261793C2F6B65" +
      "793E0A0909093C61727261793E0A090909093C646963743E0A09090909093C6B" +
      "65793E636F6D2E6170706C652E7072696E742E50616765466F726D61742E504D" +
      "41646A757374656450616765526563743C2F6B65793E0A09090909093C617272" +
      "61793E0A0909090909093C696E74656765723E303C2F696E74656765723E0A09" +
      "09090909093C696E74656765723E303C2F696E74656765723E0A090909090909" +
      "3C7265616C3E3733343C2F7265616C3E0A0909090909093C7265616C3E353736" +
      "3C2F7265616C3E0A09090909093C2F61727261793E0A09090909093C6B65793E" +
      "636F6D2E6170706C652E7072696E742E7469636B65742E7374617465466C6167" +
      "3C2F6B65793E0A09090909093C696E74656765723E303C2F696E74656765723E" +
      "0A090909093C2F646963743E0A0909093C2F61727261793E0A09093C2F646963" +
      "743E0A09093C6B65793E636F6D2E6170706C652E7072696E742E50616765466F" +
      "726D61742E504D41646A75737465645061706572526563743C2F6B65793E0A09" +
      "093C646963743E0A0909093C6B65793E636F6D2E6170706C652E7072696E742E" +
      "7469636B65742E63726561746F723C2F6B65793E0A0909093C737472696E673E" +
      "636F6D2E6170706C652E6A6F627469636B65743C2F737472696E673E0A090909" +
      "3C6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E6974656D" +
      "41727261793C2F6B65793E0A0909093C61727261793E0A090909093C64696374" +
      "3E0A09090909093C6B65793E636F6D2E6170706C652E7072696E742E50616765" +
      "466F726D61742E504D41646A75737465645061706572526563743C2F6B65793E" +
      "0A09090909093C61727261793E0A0909090909093C7265616C3E2D31383C2F72" +
      "65616C3E0A0909090909093C7265616C3E2D31383C2F7265616C3E0A09090909" +
      "09093C7265616C3E3737343C2F7265616C3E0A0909090909093C7265616C3E35" +
      "39343C2F7265616C3E0A09090909093C2F61727261793E0A09090909093C6B65" +
      "793E636F6D2E6170706C652E7072696E742E7469636B65742E7374617465466C" +
      "61673C2F6B65793E0A09090909093C696E74656765723E303C2F696E74656765" +
      "723E0A090909093C2F646963743E0A0909093C2F61727261793E0A09093C2F64" +
      "6963743E0A09093C6B65793E636F6D2E6170706C652E7072696E742E50617065" +
      "72496E666F2E504D50617065724E616D653C2F6B65793E0A09093C646963743E" +
      "0A0909093C6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E" +
      "63726561746F723C2F6B65793E0A0909093C737472696E673E636F6D2E617070" +
      "6C652E6A6F627469636B65743C2F737472696E673E0A0909093C6B65793E636F" +
      "6D2E6170706C652E7072696E742E7469636B65742E6974656D41727261793C2F" +
      "6B65793E0A0909093C61727261793E0A090909093C646963743E0A0909090909" +
      "3C6B65793E636F6D2E6170706C652E7072696E742E5061706572496E666F2E50" +
      "4D50617065724E616D653C2F6B65793E0A09090909093C737472696E673E6E61" +
      "2D6C65747465723C2F737472696E673E0A09090909093C6B65793E636F6D2E61" +
      "70706C652E7072696E742E7469636B65742E7374617465466C61673C2F6B6579" +
      "3E0A09090909093C696E74656765723E303C2F696E74656765723E0A09090909" +
      "3C2F646963743E0A0909093C2F61727261793E0A09093C2F646963743E0A0909" +
      "3C6B65793E636F6D2E6170706C652E7072696E742E5061706572496E666F2E50" +
      "4D556E61646A757374656450616765526563743C2F6B65793E0A09093C646963" +
      "743E0A0909093C6B65793E636F6D2E6170706C652E7072696E742E7469636B65" +
      "742E63726561746F723C2F6B65793E0A0909093C737472696E673E636F6D2E61" +
      "70706C652E6A6F627469636B65743C2F737472696E673E0A0909093C6B65793E" +
      "636F6D2E6170706C652E7072696E742E7469636B65742E6974656D4172726179" +
      "3C2F6B65793E0A0909093C61727261793E0A090909093C646963743E0A090909" +
      "09093C6B65793E636F6D2E6170706C652E7072696E742E5061706572496E666F" +
      "2E504D556E61646A757374656450616765526563743C2F6B65793E0A09090909" +
      "093C61727261793E0A0909090909093C696E74656765723E303C2F696E746567" +
      "65723E0A0909090909093C696E74656765723E303C2F696E74656765723E0A09" +
      "09090909093C7265616C3E3733343C2F7265616C3E0A0909090909093C726561" +
      "6C3E3537363C2F7265616C3E0A09090909093C2F61727261793E0A0909090909" +
      "3C6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E73746174" +
      "65466C61673C2F6B65793E0A09090909093C696E74656765723E303C2F696E74" +
      "656765723E0A090909093C2F646963743E0A0909093C2F61727261793E0A0909" +
      "3C2F646963743E0A09093C6B65793E636F6D2E6170706C652E7072696E742E50" +
      "61706572496E666F2E504D556E61646A75737465645061706572526563743C2F" +
      "6B65793E0A09093C646963743E0A0909093C6B65793E636F6D2E6170706C652E" +
      "7072696E742E7469636B65742E63726561746F723C2F6B65793E0A0909093C73" +
      "7472696E673E636F6D2E6170706C652E6A6F627469636B65743C2F737472696E" +
      "673E0A0909093C6B65793E636F6D2E6170706C652E7072696E742E7469636B65" +
      "742E6974656D41727261793C2F6B65793E0A0909093C61727261793E0A090909" +
      "093C646963743E0A09090909093C6B65793E636F6D2E6170706C652E7072696E" +
      "742E5061706572496E666F2E504D556E61646A75737465645061706572526563" +
      "743C2F6B65793E0A09090909093C61727261793E0A0909090909093C7265616C" +
      "3E2D31383C2F7265616C3E0A0909090909093C7265616C3E2D31383C2F726561" +
      "6C3E0A0909090909093C7265616C3E3737343C2F7265616C3E0A090909090909" +
      "3C7265616C3E3539343C2F7265616C3E0A09090909093C2F61727261793E0A09" +
      "090909093C6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E" +
      "7374617465466C61673C2F6B65793E0A09090909093C696E74656765723E303C" +
      "2F696E74656765723E0A090909093C2F646963743E0A0909093C2F6172726179" +
      "3E0A09093C2F646963743E0A09093C6B65793E636F6D2E6170706C652E707269" +
      "6E742E5061706572496E666F2E7070642E504D50617065724E616D653C2F6B65" +
      "793E0A09093C646963743E0A0909093C6B65793E636F6D2E6170706C652E7072" +
      "696E742E7469636B65742E63726561746F723C2F6B65793E0A0909093C737472" +
      "696E673E636F6D2E6170706C652E6A6F627469636B65743C2F737472696E673E" +
      "0A0909093C6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E" +
      "6974656D41727261793C2F6B65793E0A0909093C61727261793E0A090909093C" +
      "646963743E0A09090909093C6B65793E636F6D2E6170706C652E7072696E742E" +
      "5061706572496E666F2E7070642E504D50617065724E616D653C2F6B65793E0A" +
      "09090909093C737472696E673E5553204C65747465723C2F737472696E673E0A" +
      "09090909093C6B65793E636F6D2E6170706C652E7072696E742E7469636B6574" +
      "2E7374617465466C61673C2F6B65793E0A09090909093C696E74656765723E30" +
      "3C2F696E74656765723E0A090909093C2F646963743E0A0909093C2F61727261" +
      "793E0A09093C2F646963743E0A09093C6B65793E636F6D2E6170706C652E7072" +
      "696E742E7469636B65742E41504956657273696F6E3C2F6B65793E0A09093C73" +
      "7472696E673E30302E32303C2F737472696E673E0A09093C6B65793E636F6D2E" +
      "6170706C652E7072696E742E7469636B65742E747970653C2F6B65793E0A0909" +
      "3C737472696E673E636F6D2E6170706C652E7072696E742E5061706572496E66" +
      "6F5469636B65743C2F737472696E673E0A093C2F646963743E0A093C6B65793E" +
      "636F6D2E6170706C652E7072696E742E7469636B65742E41504956657273696F" +
      "6E3C2F6B65793E0A093C737472696E673E30302E32303C2F737472696E673E0A" +
      "093C6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E747970" +
      "653C2F6B65793E0A093C737472696E673E636F6D2E6170706C652E7072696E74" +
      "2E50616765466F726D61745469636B65743C2F737472696E673E0A3C2F646963" +
      "743E0A3C2F706C6973743E0A02000000970C00003C3F786D6C2076657273696F" +
      "6E3D22312E302220656E636F64696E673D225554462D38223F3E0A3C21444F43" +
      "5459504520706C697374205055424C494320222D2F2F4170706C652F2F445444" +
      "20504C49535420312E302F2F454E222022687474703A2F2F7777772E6170706C" +
      "652E636F6D2F445444732F50726F70657274794C6973742D312E302E64746422" +
      "3E0A3C706C6973742076657273696F6E3D22312E30223E0A3C646963743E0A09" +
      "3C6B65793E636F6D2E6170706C652E7072696E742E446F63756D656E74546963" +
      "6B65742E504D53706F6F6C466F726D61743C2F6B65793E0A093C646963743E0A" +
      "09093C6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E6372" +
      "6561746F723C2F6B65793E0A09093C737472696E673E636F6D2E6170706C652E" +
      "6A6F627469636B65743C2F737472696E673E0A09093C6B65793E636F6D2E6170" +
      "706C652E7072696E742E7469636B65742E6974656D41727261793C2F6B65793E" +
      "0A09093C61727261793E0A0909093C646963743E0A090909093C6B65793E636F" +
      "6D2E6170706C652E7072696E742E446F63756D656E745469636B65742E504D53" +
      "706F6F6C466F726D61743C2F6B65793E0A090909093C737472696E673E617070" +
      "6C69636174696F6E2F7064663C2F737472696E673E0A090909093C6B65793E63" +
      "6F6D2E6170706C652E7072696E742E7469636B65742E7374617465466C61673C" +
      "2F6B65793E0A090909093C696E74656765723E303C2F696E74656765723E0A09" +
      "09093C2F646963743E0A09093C2F61727261793E0A093C2F646963743E0A093C" +
      "6B65793E636F6D2E6170706C652E7072696E742E5072696E7453657474696E67" +
      "732E504D436F6C6F7253796E6350726F66696C6549443C2F6B65793E0A093C64" +
      "6963743E0A09093C6B65793E636F6D2E6170706C652E7072696E742E7469636B" +
      "65742E63726561746F723C2F6B65793E0A09093C737472696E673E636F6D2E61" +
      "70706C652E6A6F627469636B65743C2F737472696E673E0A09093C6B65793E63" +
      "6F6D2E6170706C652E7072696E742E7469636B65742E6974656D41727261793C" +
      "2F6B65793E0A09093C61727261793E0A0909093C646963743E0A090909093C6B" +
      "65793E636F6D2E6170706C652E7072696E742E5072696E7453657474696E6773" +
      "2E504D436F6C6F7253796E6350726F66696C6549443C2F6B65793E0A09090909" +
      "3C696E74656765723E313538303C2F696E74656765723E0A090909093C6B6579" +
      "3E636F6D2E6170706C652E7072696E742E7469636B65742E7374617465466C61" +
      "673C2F6B65793E0A090909093C696E74656765723E303C2F696E74656765723E" +
      "0A0909093C2F646963743E0A09093C2F61727261793E0A093C2F646963743E0A" +
      "093C6B65793E636F6D2E6170706C652E7072696E742E5072696E745365747469" +
      "6E67732E504D436F706965733C2F6B65793E0A093C646963743E0A09093C6B65" +
      "793E636F6D2E6170706C652E7072696E742E7469636B65742E63726561746F72" +
      "3C2F6B65793E0A09093C737472696E673E636F6D2E6170706C652E6A6F627469" +
      "636B65743C2F737472696E673E0A09093C6B65793E636F6D2E6170706C652E70" +
      "72696E742E7469636B65742E6974656D41727261793C2F6B65793E0A09093C61" +
      "727261793E0A0909093C646963743E0A090909093C6B65793E636F6D2E617070" +
      "6C652E7072696E742E5072696E7453657474696E67732E504D436F706965733C" +
      "2F6B65793E0A090909093C696E74656765723E313C2F696E74656765723E0A09" +
      "0909093C6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E73" +
      "74617465466C61673C2F6B65793E0A090909093C696E74656765723E303C2F69" +
      "6E74656765723E0A0909093C2F646963743E0A09093C2F61727261793E0A093C" +
      "2F646963743E0A093C6B65793E636F6D2E6170706C652E7072696E742E507269" +
      "6E7453657474696E67732E504D436F7079436F6C6C6174653C2F6B65793E0A09" +
      "3C646963743E0A09093C6B65793E636F6D2E6170706C652E7072696E742E7469" +
      "636B65742E63726561746F723C2F6B65793E0A09093C737472696E673E636F6D" +
      "2E6170706C652E6A6F627469636B65743C2F737472696E673E0A09093C6B6579" +
      "3E636F6D2E6170706C652E7072696E742E7469636B65742E6974656D41727261" +
      "793C2F6B65793E0A09093C61727261793E0A0909093C646963743E0A09090909" +
      "3C6B65793E636F6D2E6170706C652E7072696E742E5072696E7453657474696E" +
      "67732E504D436F7079436F6C6C6174653C2F6B65793E0A090909093C74727565" +
      "2F3E0A090909093C6B65793E636F6D2E6170706C652E7072696E742E7469636B" +
      "65742E7374617465466C61673C2F6B65793E0A090909093C696E74656765723E" +
      "303C2F696E74656765723E0A0909093C2F646963743E0A09093C2F6172726179" +
      "3E0A093C2F646963743E0A093C6B65793E636F6D2E6170706C652E7072696E74" +
      "2E5072696E7453657474696E67732E504D4669727374506167653C2F6B65793E" +
      "0A093C646963743E0A09093C6B65793E636F6D2E6170706C652E7072696E742E" +
      "7469636B65742E63726561746F723C2F6B65793E0A09093C737472696E673E63" +
      "6F6D2E6170706C652E6A6F627469636B65743C2F737472696E673E0A09093C6B" +
      "65793E636F6D2E6170706C652E7072696E742E7469636B65742E6974656D4172" +
      "7261793C2F6B65793E0A09093C61727261793E0A0909093C646963743E0A0909" +
      "09093C6B65793E636F6D2E6170706C652E7072696E742E5072696E7453657474" +
      "696E67732E504D4669727374506167653C2F6B65793E0A090909093C696E7465" +
      "6765723E313C2F696E74656765723E0A090909093C6B65793E636F6D2E617070" +
      "6C652E7072696E742E7469636B65742E7374617465466C61673C2F6B65793E0A" +
      "090909093C696E74656765723E303C2F696E74656765723E0A0909093C2F6469" +
      "63743E0A09093C2F61727261793E0A093C2F646963743E0A093C6B65793E636F" +
      "6D2E6170706C652E7072696E742E5072696E7453657474696E67732E504D4C61" +
      "7374506167653C2F6B65793E0A093C646963743E0A09093C6B65793E636F6D2E" +
      "6170706C652E7072696E742E7469636B65742E63726561746F723C2F6B65793E" +
      "0A09093C737472696E673E636F6D2E6170706C652E6A6F627469636B65743C2F" +
      "737472696E673E0A09093C6B65793E636F6D2E6170706C652E7072696E742E74" +
      "69636B65742E6974656D41727261793C2F6B65793E0A09093C61727261793E0A" +
      "0909093C646963743E0A090909093C6B65793E636F6D2E6170706C652E707269" +
      "6E742E5072696E7453657474696E67732E504D4C617374506167653C2F6B6579" +
      "3E0A090909093C696E74656765723E323134373438333634373C2F696E746567" +
      "65723E0A090909093C6B65793E636F6D2E6170706C652E7072696E742E746963" +
      "6B65742E7374617465466C61673C2F6B65793E0A090909093C696E7465676572" +
      "3E303C2F696E74656765723E0A0909093C2F646963743E0A09093C2F61727261" +
      "793E0A093C2F646963743E0A093C6B65793E636F6D2E6170706C652E7072696E" +
      "742E5072696E7453657474696E67732E504D5061676552616E67653C2F6B6579" +
      "3E0A093C646963743E0A09093C6B65793E636F6D2E6170706C652E7072696E74" +
      "2E7469636B65742E63726561746F723C2F6B65793E0A09093C737472696E673E" +
      "636F6D2E6170706C652E6A6F627469636B65743C2F737472696E673E0A09093C" +
      "6B65793E636F6D2E6170706C652E7072696E742E7469636B65742E6974656D41" +
      "727261793C2F6B65793E0A09093C61727261793E0A0909093C646963743E0A09" +
      "0909093C6B65793E636F6D2E6170706C652E7072696E742E5072696E74536574" +
      "74696E67732E504D5061676552616E67653C2F6B65793E0A090909093C617272" +
      "61793E0A09090909093C696E74656765723E313C2F696E74656765723E0A0909" +
      "0909093C696E74656765723E323134373438333634373C2F696E74656765723E" +
      "0A090909093C2F61727261793E0A090909093C6B65793E636F6D2E6170706C65" +
      "2E7072696E742E7469636B65742E7374617465466C61673C2F6B65793E0A0909" +
      "09093C696E74656765723E303C2F696E74656765723E0A0909093C2F64696374" +
      "3E0A09093C2F61727261793E0A093C2F646963743E0A093C6B65793E636F6D2E" +
      "6170706C652E7072696E742E7469636B65742E41504956657273696F6E3C2F6B" +
      "65793E0A093C737472696E673E30302E32303C2F737472696E673E0A093C6B65" +
      "793E636F6D2E6170706C652E7072696E742E7469636B65742E747970653C2F6B" +
      "65793E0A093C737472696E673E636F6D2E6170706C652E7072696E742E507269" +
      "6E7453657474696E67735469636B65743C2F737472696E673E0A3C2F64696374" +
      "3E0A3C2F706C6973743E0A";

/* Private instance variables */

   private PPShow show;

}
