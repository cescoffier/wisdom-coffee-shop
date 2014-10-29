package org.wisdom.coffee.api;


import java.util.Collection;
import java.util.Map;

public interface CoffeeProvider {

    public String getName();

    public Collection<Coffee> getCoffees();

    public Map<Coffee, Integer> getStock();

    public Beverage buy(Coffee coffee) throws NoMoreCoffeeException;

}
