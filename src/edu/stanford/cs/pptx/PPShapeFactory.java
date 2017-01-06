/*
 * File: PPShapeFactory.java
 * -------------------------
 * This interface defines classes that can create PPShape objects.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.PPShape;

/**
 * This interface defines the set of classes that can create a PowerPoint
 * shape.
 */

public interface PPShapeFactory {

/**
 * Creates a new shape as determined by the implementation of the factory.
 *
 * @return The new <code>PPShape</code> object
 */

   public PPShape createShape();

}
