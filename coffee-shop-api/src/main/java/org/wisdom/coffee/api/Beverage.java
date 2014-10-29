package org.wisdom.coffee.api;


public class Beverage {

    private final Coffee coffee;

    public Beverage(Coffee coffee) {
        this.coffee = coffee;
    }

    public Coffee getCoffee() {
        return coffee;
    }

}
