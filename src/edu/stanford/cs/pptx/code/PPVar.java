/*
 * File: PPVar.java
 * ----------------
 * This class represents a typed variable in a stack frame.
 */

package edu.stanford.cs.pptx.code;

/**
 * This class represents a typed variable in a stack frame.  The type
 * parameter is the value type.
 */

public class PPVar<T> extends PPVariable {

/**
 * Returns a new unnamed variable with the default dimensions.
 */

   public PPVar() {
      super();
   }

/**
 * Returns a new variable object with the default dimensions.
 *
 * @param name The name of the variable
 */

   public PPVar(String name) {
      super(name);
   }

/**
 * Returns a new variable object with the specified width and the default
 * height.
 *
 * @param name The name of the variable
 * @param width The width of the variable in pixels
 */

   public PPVar(String name, double width) {
      super(name, width);
   }

/**
 * Returns a new variable object with the specified name and dimensions.
 *
 * @param name The name of the variable
 * @param width The width of the variable in pixels
 * @param height The height of the variable in pixels
 */

   public PPVar(String name, double width, double height) {
      super(name, width, height);
   }

/**
 * Sets the value of the variable.
 *
 * @param value The value of the variable
 */

   public void set(T value) {
      set(value, "");
   }

/**
 * Sets the value of the variable.  This version allows the client to change
 * the animation options.
 *
 * @param value The value of the variable
 * @param options The animation options
 */

   public void set(T value, String options) {
      this.value = value;
      setValue(value.toString());
   }

/**
 * Gets the value of this variable.
 *
 * @return The value of this variable
 */

   public T get() {
      return value;
   }

/* Private instance variables */

   private T value;

}
