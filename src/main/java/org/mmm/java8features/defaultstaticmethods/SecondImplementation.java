package org.mmm.java8features.defaultstaticmethods;

public class SecondImplementation implements SomeInterface {

  public static void main(String[] args) {

    SecondImplementation secondImplementation = new SecondImplementation();

    //Call the mandatory overriding of the interface method
    secondImplementation.method();

    //Call the default implementation of the interface method
    secondImplementation.defaultMethod();

    //Call the static method of the interface
    SomeInterface.staticMethod();
  }

  @Override
  public void method() {
    System.out
        .println("Implementation of the mandatory method in the interface implemented by " + this);
  }
}
