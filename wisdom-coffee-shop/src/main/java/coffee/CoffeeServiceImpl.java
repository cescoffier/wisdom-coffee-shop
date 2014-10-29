package coffee;


import org.apache.felix.ipojo.annotations.Requires;
import org.wisdom.api.annotations.Service;
import org.wisdom.coffee.api.Beverage;
import org.wisdom.coffee.api.Coffee;
import org.wisdom.coffee.api.CoffeeProvider;
import org.wisdom.coffee.api.NoMoreCoffeeException;
import service.CoffeeService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Requires(specification = CoffeeProvider.class)
    Collection<CoffeeProvider> providers;

    @Override
    public Beverage buy(String name) throws NoMoreCoffeeException {
        CoffeeProvider provider = getProviderForCoffee(name);
        if (provider == null) {
            throw new NoMoreCoffeeException(name);
        } else {
            Coffee coffee = findCoffee(provider, name);
            if (coffee == null) {
                throw new NoMoreCoffeeException(name);
            }
            return provider.buy(coffee);
        }
    }

    @Override
    public Set<Coffee> getAvailableCoffee() {
        return getProviders()
                .stream()
                .flatMap((provider) -> provider.getCoffees().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<CoffeeProvider> getProviders() {
        return new HashSet<>(providers);
    }

    @Override
    public Coffee getCoffee(String name) {
        for (CoffeeProvider provider : providers) {
            for (Coffee coffee : provider.getCoffees()) {
                if (name.equalsIgnoreCase(coffee.getName())) {
                    return coffee;
                }
            }
        }
        return null;
    }

    private CoffeeProvider getProviderForCoffee(String name) {
        for (CoffeeProvider provider : providers) {
            for (Coffee coffee : provider.getCoffees()) {
                if (name.equalsIgnoreCase(coffee.getName())) {
                    return provider;
                }
            }
        }
        return null;
    }


    private Coffee findCoffee(CoffeeProvider provider, String name) {
        for (Coffee coffee : provider.getCoffees()) {
            if (name.equalsIgnoreCase(coffee.getName())) {
                return coffee;
            }
        }
        return null;
    }
}
