package org.mmm.java8features.defaultstaticmethods;

public class FirstImplementation implements SomeInterface {

  @Override
  public void method() {
    System.out
        .println("Implementation of the mandatory method in the interface implemented by " + this);
  }

  @Override
  public void defaultMethod() {

    System.out.println("Overriding of the default method , implemented by " + this);
  }


  public static void main(String[] args) {

    FirstImplementation firstImplementation = new FirstImplementation();

    //Call the mandatory overriding of the interface method
    firstImplementation.method();

    //Call the optional implementation of the interface method
    firstImplementation.defaultMethod();

    //Call the static method of the interface
    SomeInterface.staticMethod();
  }
}
