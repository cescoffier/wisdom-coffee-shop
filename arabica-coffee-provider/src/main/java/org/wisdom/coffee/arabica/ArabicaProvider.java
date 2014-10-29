package org.wisdom.coffee.arabica;

import org.apache.felix.ipojo.annotations.Validate;
import org.wisdom.api.annotations.Service;
import org.wisdom.coffee.api.*;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArabicaProvider implements CoffeeProvider {


    private Map<Coffee, Integer> provided = new HashMap<>();

    @Validate
    public CoffeeProvider start() throws MalformedURLException {
        provided.put(new DefaultCoffee("Capuccino",
                "Espresso, hot milk, and steamed-milk foam",
                "/assets/pics/capuccino.jpg",
                2.00), 20);

        provided.put(new DefaultCoffee("Americano",
                "coffee prepared by adding hot water to espresso, giving it a similar strength to, but different flavor" +
                        " from, regular drip coffee. ",
                "/assets/pics/americano.jpg",
                2.00), 20);
        return this;
    }

    @Override
    public String getName() {
        return "arabica";
    }

    @Override
    public synchronized Collection<Coffee> getCoffees() {
        return provided.entrySet().stream()
                .filter((entry) -> entry.getValue() != 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.<Coffee>toList());
    }

    @Override
    public synchronized Map<Coffee, Integer> getStock() {
        return new HashMap<>(provided);
    }

    @Override
    public synchronized Beverage buy(Coffee coffee) throws NoMoreCoffeeException {
        if (!provided.containsKey(coffee)) {
            throw new NoMoreCoffeeException(coffee);
        }
        int stock = provided.get(coffee);
        if (stock == 0) {
            throw new NoMoreCoffeeException(coffee);
        } else {
            provided.put(coffee, --stock);
        }
        return new Beverage(coffee);
    }
}
