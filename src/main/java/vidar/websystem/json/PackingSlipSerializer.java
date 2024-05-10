package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.PackingSlip;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/10/2024 11:52 AM
 * description
 */
public class PackingSlipSerializer extends JsonSerializer<PackingSlip> {
    /**
     * @param packingSlip PackingSlip class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(PackingSlip packingSlip, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (packingSlip != null) {
            jsonGenerator.writeNumber(packingSlip.getId());
        }
    }
}
