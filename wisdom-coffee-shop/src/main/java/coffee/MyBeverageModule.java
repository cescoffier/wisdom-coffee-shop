package coffee;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.felix.ipojo.annotations.Requires;
import org.wisdom.api.annotations.Service;
import org.wisdom.coffee.api.Beverage;
import org.wisdom.coffee.api.Fortune;

import java.io.IOException;

@Service(Module.class)
public class MyBeverageModule extends SimpleModule {

    @Requires
    Fortune fortune;

    public MyBeverageModule() {
        super("My Beverage Module");
        addSerializer(Beverage.class, new JsonSerializer<Beverage>() {
            @Override
            public void serialize(Beverage value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                System.out.println("Serializing " + value);
                jgen.writeStartObject();
                provider.defaultSerializeField("coffee", value.getCoffee(), jgen);
                jgen.writeStringField("fortune", fortune.get());
                jgen.writeEndObject();
            }
        });

    }
}
