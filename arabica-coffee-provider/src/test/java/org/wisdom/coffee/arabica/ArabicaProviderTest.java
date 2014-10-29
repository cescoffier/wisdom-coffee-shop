package org.wisdom.coffee.arabica;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.wisdom.coffee.api.Coffee;
import org.wisdom.coffee.api.CoffeeProvider;
import org.wisdom.coffee.api.NoMoreCoffeeException;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;


public class ArabicaProviderTest {

    private CoffeeProvider provider;

    @Before
    public void setUp() throws MalformedURLException {
        provider = new ArabicaProvider().start();
    }

    @Test
    public void testGetName() throws Exception {
        assertThat(provider.getName()).isEqualTo("arabica");
    }

    @Test
    public void testGetCoffees() throws Exception {
        assertThat(provider.getCoffees()).hasSize(2);
        Coffee coffee = provider.getCoffees().iterator().next();
        for (int i = 0; i < 20; i++) {
            provider.buy(coffee);
        }
        assertThat(provider.getCoffees()).hasSize(1);
        coffee = provider.getCoffees().iterator().next();
        for (int i = 0; i < 20; i++) {
            provider.buy(coffee);
        }
        assertThat(provider.getCoffees()).isEmpty();
    }

    @Test
    public void testGetStock() throws Exception {
        assertThat(provider.getStock()).hasSize(2);
        Coffee coffee = provider.getCoffees().iterator().next();
        for (int i = 0; i < 20; i++) {
            provider.buy(coffee);
        }
        assertThat(provider.getCoffees()).hasSize(1);
        assertThat(provider.getStock()).hasSize(2);

        assertThat(provider.getStock().get(coffee)).isEqualTo(0);
    }

    @Test(expected = NoMoreCoffeeException.class)
    public void testBuyWhenNoMoreCoffee() throws Exception {
        Coffee coffee = provider.getCoffees().iterator().next();
        for (int i = 0; i < 20; i++) {
            provider.buy(coffee);
        }
        assertThat(provider.getCoffees()).hasSize(1);
        provider.buy(coffee);
    }
}