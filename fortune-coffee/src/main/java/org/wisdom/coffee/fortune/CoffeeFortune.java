package org.wisdom.coffee.fortune;


import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.io.FileUtils;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wisdom.api.annotations.Service;
import org.wisdom.api.configuration.ApplicationConfiguration;
import org.wisdom.api.content.Json;
import org.wisdom.coffee.api.Fortune;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@Service
public class CoffeeFortune implements Fortune {


    @Requires
    ApplicationConfiguration configuration;

    @Requires
    Json json;

    private JsonNode quotes;
    private Random random = new Random();
    private final static Logger LOGGER = LoggerFactory.getLogger(CoffeeFortune.class);

    @Validate
    public void load() throws IOException {
        File fortune = configuration.getFileWithDefault("coffee.fortune", "conf/coffee.json");
        if (!fortune.isFile()) {
            LOGGER
                    .error("Cannot load the fortune file: {}", fortune.getAbsolutePath());
            throw new IllegalStateException("No fortune file found");
        }

        quotes = json.parse(FileUtils.readFileToString(fortune));
        LOGGER.error("{} quotes loaded", quotes.size());
    }


    @Override
    public String get() {
        int index = random.nextInt(quotes.size());
        return quotes.get(index).asText();
    }
}
