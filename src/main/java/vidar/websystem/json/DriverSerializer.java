package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.Driver;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/4/2024 12:42 PM
 * description
 */
public class DriverSerializer extends JsonSerializer<Driver> {
    /**
     * @param driver Driver class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(Driver driver, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (driver != null) {
            jsonGenerator.writeString(driver.toString());
        }
    }
}
