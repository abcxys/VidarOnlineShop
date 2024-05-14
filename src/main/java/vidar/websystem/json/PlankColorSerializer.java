package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.PlankColor;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/14/2024 1:29 PM
 * description
 */
public class PlankColorSerializer extends JsonSerializer<PlankColor> {
    /**
     * @param plankColor PlankColor class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(PlankColor plankColor,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (plankColor != null) {
            jsonGenerator.writeString(plankColor.toString());
        }
    }
}
