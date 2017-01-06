# JavaPPTX
A Java library for creating PowerPoint slides

<p>This package makes it possible to generate animated PowerPoint slides by writing programs in Java
that specify the animation steps algorithmically, making it possible to construct complex animation
sequences that are impossible to generate manually using PowerPoint's standard user interface.
The primary purpose of this package is to support the creation of single slide rather than an entire slide show.</p>

<p>The current version of the package supports the following object types:</p>

<ul>
<li>Line segments (PPLine)</li>
<li>Rectangles (PPRect)</li>
<li>Ovals (PPOval)</li>
<li>Images (PPPicture)</li>
<li>Text boxes (PPTextBox)</li>
<li>Pie-shaped wedges (PPPie)</li>
<li>Stars (PPStar)</li>
<li>Groups (PPGroup)</li>
<li>Paths (PPPath)</li>
<li>Pointer paths (PPPointer)</li>
<li>Rectangular callouts (PPRectCallout)</li>
<li>Oval callouts (PPOvalCallout)</li>
</ul>

<p>In addition, the package supports the following animations:</p>

<ul>
<li>Appear/Disappear</li>
<li>FadeIn/FadeOut</li>
<li>FlyIn/FlyOut</li>
<li>WipeIn/WipeOut</li>
<li>ZoomIn/ZoomOut</li>
<li>FadedZoomIn/FadedZoomOut</li>
<li>CheckerboardIn/CheckerboardOut</li>
<li>Spin</li>
<li>Grow/Shrink</li>
<li>ChangeFillColor</li>
<li>ChangeLineColor</li>
<li>Motion paths with linear or bezier motions</li>
</ul>

<p>These capabilities are only a tiny subset of the features available in PowerPoint but include
(particularly given the motion path capability) the most important features for algorithm animation.
Illustrations of how to use the current set of features may be found in the examples directory.
Javadoc pages for the available classes and methods appear in the doc directory.</p>
