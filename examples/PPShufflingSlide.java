/*
 * File: PPShufflingSlide.java
 * ---------------------------
 * This file builds a relatively sophisticated slide that makes
 * extensive use of motion paths.
 */

import edu.stanford.cs.pptx.PPLine;
import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.PPSlide;
import edu.stanford.cs.pptx.PPTextBox;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class builds a slide that illustrates the operation of
 * the shuffling machine described by Persi Diaconis at Don Knuth's
 * 64th birthday celebration.  The cards from the deck are shifted
 * randomly into one of eight bins, which are then reassembled to
 * create a new deck.  Unfortunately, the randomization is quite
 * weak.  The bottom card has approximately a 1 in 16 chance of
 * becoming the top card on the next cycle.
 */

public class PPShufflingSlide {

   public static void main(String[] args) {
      ppt = new PPShow();
      slide = new PPSlide();
//      slide.addTitle("Shuffling");
      rgen = new Random();
      rgen.setSeed(2);
      initMachine();
      initDeck();
      deckToBins();
      binsToDeck();
      ppt.add(slide);
      ppt.save("PPShufflingSlide.pptx");
      System.out.println("PPShufflingSlide.pptx");
   }

   private static void initDeck() {
      double x = DECK_X;
      double y = DECK_Y;
      deck = new ArrayList<PPCard>();
      for (int i = 0; i < 52; i++) {
         Color color = Color.getHSBColor((float) 0.87 * i / 52,
                                         (float) 1.0, (float) 1.0);
         if (i == 51) color = Color.WHITE;
         PPCard card = new PPCard(x, y);
         card.setColor(color);
         slide.add(card);
         deck.add(card);
         y += CARD_HEIGHT + DECK_SEP;
      }
   }

   private static void initMachine() {
      bins = new ArrayList<ArrayList<PPCard>>();
      drawEdge(MACHINE_X, MACHINE_Y, MACHINE_WIDTH, 0);
      drawEdge(MACHINE_X + MACHINE_WIDTH, MACHINE_Y, 0, MACHINE_HEIGHT);
      for (int i = 0; i < N_BINS; i++) {
         double x = MACHINE_X;
         double y = MACHINE_Y + (i + 1) * MACHINE_HEIGHT / N_BINS;
         drawEdge(x, y, MACHINE_WIDTH, 0);
         bins.add(new ArrayList<PPCard>());
      }
   }

   private static void deckToBins() {
      double x0 = DECK_X;
      double x1 = x0 + 1.2 * CARD_WIDTH;
      double x3 = MACHINE_X + BIN_X_DELTA;
      double x2 = x3 - 1.2 * CARD_WIDTH;
      double binHeight = MACHINE_HEIGHT / N_BINS;
      for (int i = 0; i < 52; i++) {
         String speed = "/speed:" + ((i < TRACE_FORWARD - 1) ? 250 : 1500);
         String option = (i < TRACE_FORWARD) ? "/onClick" : "/afterPrev";
         PPCard card = drawTopCard();
         int bin = rgen.nextInt(N_BINS);
         lastBin = bin;
         boolean top = (i == 51) || (i == 5) || rgen.nextBoolean();
         double y0 = DECK_Y + i * (CARD_HEIGHT + DECK_SEP);
         double y1 = MACHINE_Y + (bin + 1) * binHeight - CARD_HEIGHT
                               - BIN_Y_DELTA;
         if (top) y1 -= bins.get(bin).size() * CARD_HEIGHT;
         if (i == 3) option = "/afterPrev";
         card.moveTo(x1, y0, option + speed + "/acc");
         card.moveTo(x2, y1, speed);
         option = "/afterPrev";
         if (top) {
            bins.get(bin).add(card);
         } else {
            for (int j = 0; j < bins.get(bin).size(); j++) {
               bins.get(bin).get(j).move(0, -CARD_HEIGHT, option);
               option = "/withPrev";
            }
            bins.get(bin).add(0, card);
         }
         card.moveTo(x3, y1, option + speed + "/dec");
      }
   }

   private static void binsToDeck() {
      ArrayList<Integer> order = new ArrayList<Integer>();
      for (int i = 0; i < N_BINS; i++) {
         if (i != lastBin) {
            order.add(i);
         }
      }
      Collections.shuffle(order);
      order.add(lastBin);
      double x0 = DECK_X;
      double x1 = x0 + 1.2 * CARD_WIDTH;
      double x3 = MACHINE_X + BIN_X_DELTA;
      double x2 = x3 - 1.2 * CARD_WIDTH;
      double binHeight = MACHINE_HEIGHT / N_BINS;
      for (int i = 0; i < N_BINS; i++) {
         int bin = order.get(i);
         double y0 = DECK_Y + (51 - deck.size()) * (CARD_HEIGHT + DECK_SEP);
         double y1 = MACHINE_Y + (bin + 1) * binHeight - CARD_HEIGHT
                               - BIN_Y_DELTA;
         String speed = "/speed:" + ((i < TRACE_BACKWARD - 1) ? 250 : 1500);
         String option = (i < TRACE_BACKWARD) ? "/onClick" : "/afterPrev";
         int count = bins.get(bin).size();
         for (int j = 0; j < count; j++) {
            PPCard card = bins.get(bin).get(j);
            card.move(x2 - x3, 0, option + speed + "/acc");
            option = "/withPrev";
         }
         option = "/afterPrev";
         double dx = x1 - x2;
         double dy = y0 - y1;
         double distance = Math.sqrt(dx + dx + dy * dy);
         double duration = distance / ((i < TRACE_BACKWARD) ? 250 : 1500);
         for (int j = 0; j < count; j++) {
            PPCard card = bins.get(bin).get(j);
            card.move(x1 - x2, dy - j * DECK_SEP,
                      option + "/duration:" + duration);
            option = "/withPrev";
         }
         option = "/afterPrev";
         for (int j = 0; j < count; j++) {
            PPCard card = bins.get(bin).get(j);
            card.move(x0 - x1, 0, option + speed + "/dec");
            deck.add(card);
            option = "/withPrev";
         }
         bins.get(bin).clear();
      }
   }

   private static PPCard drawTopCard() {
      PPCard card = deck.get(0);
      deck.remove(0);
      return card;
   }

   private static void drawEdge(double x, double y, double dx, double dy) {
      PPLine edge = new PPLine(x, y, x + dx + ((dx == 0) ? 0 : 0.5),
                                     y + dy + ((dy == 0) ? 0 : 0.5));
      edge.setLineColor(Color.BLACK);
      edge.setLineWeight(MACHINE_EDGE_WEIGHT);
      slide.add(edge);
   }

   private static final int N_BINS = 8;
   private static final int TRACE_FORWARD = 4;
   private static final int TRACE_BACKWARD = 2;

   private static final double DECK_X = 270;
   private static final double DECK_Y = 120;
   private static final double DECK_SEP = 2.74;
   private static final double CARD_WIDTH = PPCard.CARD_WIDTH;
   private static final double CARD_HEIGHT = PPCard.CARD_HEIGHT;
   private static final double MACHINE_X = 500;
   private static final double MACHINE_Y = 100;
   private static final double MACHINE_WIDTH = CARD_WIDTH + 15;
   private static final double MACHINE_HEIGHT = 350;
   private static final double MACHINE_EDGE_WEIGHT = 2;
   private static final double BIN_X_DELTA = 5;
   private static final double BIN_Y_DELTA = 0.71;

   private static PPShow ppt;
   private static PPSlide slide;
   private static ArrayList<PPCard> deck;
   private static ArrayList<ArrayList<PPCard>> bins;
   private static Random rgen;
   private static int lastBin;

}

class PPCard extends PPRect {

   public static final double CARD_HEIGHT = 3.55;
   public static final double CARD_WIDTH = 85;

   public PPCard(double x, double y) {
      super(x, y, CARD_WIDTH, CARD_HEIGHT);
   }

   public void setColor(Color color) {
      setFillColor(color);
   }

}
