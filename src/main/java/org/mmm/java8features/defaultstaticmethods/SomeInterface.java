package org.mmm.java8features.defaultstaticmethods;

public interface SomeInterface {

    void method();

    default void defaultMethod(){
        System.out.println("The default implementation of the method in the interface");
    }

    static void staticMethod(){
        System.out.println("The static method in the interface");
    }
}
