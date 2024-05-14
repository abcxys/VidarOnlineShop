package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.PlankType;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/14/2024 1:41 PM
 * description
 */
public class PlankTypeSerializer extends JsonSerializer<PlankType> {
    /**
     * @param plankType PlankType class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(PlankType plankType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (plankType != null)
            jsonGenerator.writeString(plankType.toString());
    }
}
