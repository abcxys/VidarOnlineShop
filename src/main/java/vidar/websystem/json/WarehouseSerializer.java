package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.Warehouse;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 4/25/2024 3:21 PM
 * description
 */
public class WarehouseSerializer extends JsonSerializer<Warehouse> {
    /**
     * @param warehouse Warehouse class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(Warehouse warehouse,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (warehouse != null){
            jsonGenerator.writeString(warehouse.toString());
        }
    }
}
