package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.PackingStatus;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/4/2024 12:40 PM
 * description
 */
public class PackingStatusSerializer extends JsonSerializer<PackingStatus> {
    /**
     * @param packingStatus PackingStatus class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(PackingStatus packingStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (packingStatus != null) {
            jsonGenerator.writeNumber(packingStatus.getId());
        }
    }
}
