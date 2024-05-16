package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.ReturnSlip;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/16/2024 11:28 AM
 * description
 */
public class ReturnSlipSerializer extends JsonSerializer<ReturnSlip> {
    /**
     * @param returnSlip ReturnSlip class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(ReturnSlip returnSlip, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (returnSlip != null) {
            jsonGenerator.writeString(returnSlip.toString());
        }

    }
}
