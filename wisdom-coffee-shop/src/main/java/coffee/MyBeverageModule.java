package coffee;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.felix.ipojo.annotations.*;
import org.wisdom.api.content.JacksonModuleRepository;
import org.wisdom.coffee.api.Beverage;
import org.wisdom.coffee.api.Fortune;

import java.io.IOException;

@Component(immediate = true)
@Instantiate
public class MyBeverageModule  {

    @Requires
    JacksonModuleRepository repository;

    @Requires
    Fortune fortune;
    private SimpleModule module;

    @Validate
    public void start() {
        module = new SimpleModule("coffee-module");
        module.addSerializer(Beverage.class, new JsonSerializer<Beverage>() {
            @Override
            public void serialize(Beverage value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                System.out.println("Serializing " + value);
                jgen.writeStartObject();
                provider.defaultSerializeField("coffee", value.getCoffee(), jgen);
                jgen.writeStringField("fortune", fortune.get());
                jgen.writeEndObject();
            }
        });
        repository.register(module);
    }

    @Invalidate
    public void stop() {
        repository.unregister(module);
    }
}
