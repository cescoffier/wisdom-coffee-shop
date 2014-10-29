package coffee;

import org.apache.felix.ipojo.annotations.Requires;
import org.wisdom.api.DefaultController;
import org.wisdom.api.annotations.*;
import org.wisdom.api.content.Json;
import org.wisdom.api.http.HttpMethod;
import org.wisdom.api.http.Result;
import org.wisdom.api.http.websockets.Publisher;
import org.wisdom.coffee.api.Beverage;
import org.wisdom.coffee.api.Coffee;
import org.wisdom.coffee.api.Fortune;
import org.wisdom.coffee.api.NoMoreCoffeeException;
import service.CoffeeService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Controller
@Path("/coffee")
public class CoffeeController extends DefaultController {

    @Requires
    CoffeeService service;

    @Requires
    Publisher publisher;


    @Route(method = HttpMethod.GET, uri = "")
    public Result getAllCoffees() {
        return ok(service.getAvailableCoffee()).json();
    }

    @Route(method = HttpMethod.GET, uri = "/{name}")
    public Result getCoffee(@Parameter("name") @NotNull @Size(min = 3, max = 150) String name) {
        Coffee coffee = service.getCoffee(name);
        if (coffee == null) {
            return notFound("Coffee " + name + " not found");
        }
        return ok(coffee).json();
    }

    @Route(method = HttpMethod.POST, uri = "/{name}")
    public Result buy(@Parameter("name") @NotNull @Size(min = 3, max = 150) String name) throws NoMoreCoffeeException {
        final Beverage beverage = service.buy(name);
        publisher.publish("/coffee/last", name);
        return ok(beverage).json();
    }

}
