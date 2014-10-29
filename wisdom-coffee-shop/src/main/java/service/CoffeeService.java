package service;


import org.wisdom.coffee.api.Beverage;
import org.wisdom.coffee.api.Coffee;
import org.wisdom.coffee.api.CoffeeProvider;
import org.wisdom.coffee.api.NoMoreCoffeeException;

import java.util.Set;

public interface CoffeeService {

    public Beverage buy(String name) throws NoMoreCoffeeException;

    public Set<Coffee> getAvailableCoffee();

    public Set<CoffeeProvider> getProviders();

    public Coffee getCoffee(String name);
}
