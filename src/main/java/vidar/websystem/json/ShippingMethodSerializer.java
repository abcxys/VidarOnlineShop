package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.ShippingMethod;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/15/2024 3:37 PM
 * description
 */
public class ShippingMethodSerializer extends JsonSerializer<ShippingMethod> {
    /**
     * @param shippingMethod ShippingMethod class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(ShippingMethod shippingMethod, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (shippingMethod != null){
            jsonGenerator.writeString(shippingMethod.toString());
        }
    }
}
