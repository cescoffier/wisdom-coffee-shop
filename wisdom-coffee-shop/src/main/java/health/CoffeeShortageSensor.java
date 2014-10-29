package health;

import org.apache.felix.ipojo.annotations.Requires;
import org.wisdom.api.annotations.Service;
import org.wisdom.monitor.service.HealthCheck;
import service.CoffeeService;

@Service
public class CoffeeShortageSensor implements HealthCheck {

    @Requires
    CoffeeService service;

    @Override
    public String name() {
        return "Coffee Shortage";
    }

    @Override
    public boolean check() throws Exception {
        return ! service.getAvailableCoffee().isEmpty();
    }
}
