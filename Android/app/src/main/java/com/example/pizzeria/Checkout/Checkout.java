package com.example.pizzeria.Checkout;

public class Checkout {
    private static Checkout checkout;

    private Checkout(){
    };

    //Singleton design pattern
    public static Checkout getInstance(){
        if(checkout == null){
            checkout = new Checkout();
        }

        return checkout;
    }



}
