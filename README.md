# JavaPPTX
A Java library for creating PowerPoint slides

This package makes it possible to generate animated PowerPoint slides by writing programs in Java that specify the animation steps algorithmically, making it possible to construct complex animation sequences that are impossible to generate manually using PowerPoint's standard user interface. The primary purpose of this package is to support the creation of single slide rather than an entire slide show.

The current version of the package supports the following object types:

  Line segments (PPLine)
  Rectangles (PPRect)
  Ovals (PPOval)
  Images (PPPicture)
  Text boxes (PPTextBox)
  Pie-shaped wedges (PPPie)
  Stars (PPStar)
  Groups (PPGroup)
  Paths (PPPath)
  Pointer paths (PPPointer)
  Rectangular callouts (PPRectCallout)
  Oval callouts (PPOvalCallout)

In addition, the package supports the following animations:

  Appear/Disappear
  FadeIn/FadeOut
  FlyIn/FlyOut
  WipeIn/WipeOut
  ZoomIn/ZoomOut
  FadedZoomIn/FadedZoomOut
  CheckerboardIn/CheckerboardOut
  Spin
  Grow/Shrink
  ChangeFillColor
  ChangeLineColor
  Motion paths with linear or bezier motions

These capabilities are only a tiny subset of the features available in PowerPoint but include (particularly given the motion path capability) the most important features for algorithm animation. Illustrations of how to use the current set of features may be found in the examples directory. Javadoc pages for the available classes and methods appear in the doc directory.
