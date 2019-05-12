package org.mmm.java8features.lamdas;


import java.util.ArrayList;
import java.util.List;



public class InterfaceImplementationExamples {

    //interface definition for inline implementation with Lambdas

    @FunctionalInterface
    interface FunctionalInterface1 {

        void singleMethod();
    }

    @FunctionalInterface
    interface FunctionalInterface2{

        void singleMethod(String s);
    }

    @FunctionalInterface
    interface FunctionalInterface3{

        void singleMethod(String s, String p);
    }


    public static void main(String[] args){

        FunctionalInterface1 functionalInterface1= ()->System.out.println("Hello world FunctionalInterface1");

        functionalInterface1.singleMethod();


        FunctionalInterface2 functionalInterface2= (p)->System.out.println(p);

        functionalInterface2.singleMethod("Hello world FunctionalInterface2");

        FunctionalInterface3 functionalInterface3=(s,p)->System.out.println(s+p);

        functionalInterface3.singleMethod("Hello world ","FunctionalInterface3");


    }



}
