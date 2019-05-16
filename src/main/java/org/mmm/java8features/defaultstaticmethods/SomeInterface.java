package org.mmm.java8features.defaultstaticmethods;

public interface SomeInterface {

  static void staticMethod() {
    System.out.println("The static method in the interface");
  }

  void method();

  default void defaultMethod() {
    System.out.println("The default implementation of the method in the interface");
  }
}
