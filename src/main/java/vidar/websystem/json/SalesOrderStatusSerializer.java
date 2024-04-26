package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.SalesOrderStatus;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 4/26/2024 3:35 PM
 * description
 */
public class SalesOrderStatusSerializer extends JsonSerializer<SalesOrderStatus> {
    /**
     * @param salesOrderStatus SalesOrderStatus class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(SalesOrderStatus salesOrderStatus,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (salesOrderStatus != null){
            jsonGenerator.writeString(salesOrderStatus.toString());
        }
    }
}
