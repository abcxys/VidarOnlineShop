package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.ReturnStatus;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/9/2024 3:43 PM
 * description
 */
public class ReturnStatusSerializer extends JsonSerializer<ReturnStatus> {
    /**
     * @param returnStatus ReturnStatus class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(ReturnStatus returnStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (returnStatus != null) {
            jsonGenerator.writeNumber(returnStatus.getId());
        }
    }
}
