package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.PlankSize;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/14/2024 1:36 PM
 * description
 */
public class PlankSizeSerializer extends JsonSerializer<PlankSize> {
    /**
     * @param plankSize PlankSize class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(PlankSize plankSize, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (plankSize != null)
            jsonGenerator.writeString(plankSize.toString());
    }
}
