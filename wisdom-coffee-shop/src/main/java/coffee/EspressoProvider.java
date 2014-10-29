package coffee;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.felix.ipojo.annotations.Validate;
import org.wisdom.api.annotations.Service;
import org.wisdom.api.annotations.scheduler.Every;
import org.wisdom.api.scheduler.Scheduled;
import org.wisdom.coffee.api.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class EspressoProvider implements CoffeeProvider, Scheduled {

    private Coffee espresso;
    private int stock;

    @Validate
    public CoffeeProvider start() throws MalformedURLException {
        espresso = new DefaultCoffee("Espresso",
                "Espresso is coffee brewed by forcing a small amount of nearly boiling water under pressure " +
                        "through finely ground coffee beans.",
                "/assets/img/espresso.jpg",
                2.00);
        stock = 5;
        return this;
    }

    @Override
    public String getName() {
        return "My Espresso Provider";
    }

    @Override
    public synchronized Collection<Coffee> getCoffees() {
        if (stock != 0) {
            return ImmutableList.of(espresso);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public synchronized Map<Coffee, Integer> getStock() {
        return ImmutableMap.of(espresso, stock);
    }

    @Override
    public synchronized Beverage buy(Coffee coffee) throws NoMoreCoffeeException {
        if (stock != 0) {
            stock--;
            return new Beverage(coffee);
        } else {
            throw new NoMoreCoffeeException(coffee);
        }
    }

    @Every("1m")
    public synchronized void refill() {
        if (stock == 0) {
            System.out.println("Refilling espresso");
            stock = 5;
        }
    }
}
