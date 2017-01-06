/*
 * File: ShrinkEffect.java
 * -----------------------
 * This class is effectively a synonym for the Grow effect.
 */

package edu.stanford.cs.pptx.effect;

/**
 * This emphasis effect scales the shape relative to its center.  The legal
 * options are:
 *
 * <table border=0>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/onClick</code></td>
 *     <td>Effect takes place after a mouse click</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/afterPrev</code></td>
 *     <td>Effect takes place after the previous one</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/withPrev</code></td>
 *     <td>Effect takes place with the previous one</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/delay:</code><i>time</i></td>
 *     <td>Effect is delayed by <i>time</i> seconds</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/duration:</code><i>time</i></td>
 *     <td>Effect lasts <i>time</i> seconds</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/acc</code></td>
 *     <td>The effect accelerates at the beginning</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/dec</code></td>
 *     <td>The effect decelerates at the end</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/sf:</code><i>value</i>
 *     <td>Specifies the scale factor in both dimensions</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/sx:</code><i>value</i>
 *     <td>Specifies the scale factor in the <i>x</i> dimension only</td></tr>
 * <tr><td style="padding-left:2em; width:10em;">
 *     <code>/sy:</code><i>value</i>
 *     <td>Specifies the scale factor in the <i>y</i> dimension only</td></tr>
 * </table>
 */

public class ShrinkEffect extends GrowEffect {

   /* Empty */

}
