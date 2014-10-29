package coffee;

import org.wisdom.api.annotations.Service;
import org.wisdom.api.exceptions.ExceptionMapper;
import org.wisdom.api.http.Result;
import org.wisdom.api.http.Results;
import org.wisdom.coffee.api.NoMoreCoffeeException;

@Service
public class MyExceptionMapper implements ExceptionMapper<NoMoreCoffeeException> {
    @Override
    public Class<NoMoreCoffeeException> getExceptionClass() {
        return NoMoreCoffeeException.class;
    }

    @Override
    public Result toResult(NoMoreCoffeeException e) {
        return Results.notFound("Sorry, the coffee " + e.getCoffee() + " is not available right now.");
    }
}
