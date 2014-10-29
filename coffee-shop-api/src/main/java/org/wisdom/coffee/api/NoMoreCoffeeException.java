package org.wisdom.coffee.api;


public class NoMoreCoffeeException extends Exception {

    private final String coffee;

    public NoMoreCoffeeException(Coffee coffee) {
        super("Sorry, we ran out of " + coffee.getName());
        this.coffee = coffee.getName();
    }

    public NoMoreCoffeeException(String name) {
        super("Sorry, we ran out of " + name);
        coffee = name;
    }


    public String getCoffee() {
        return coffee;
    }
}
