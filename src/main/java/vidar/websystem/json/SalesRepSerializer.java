package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.SalesRep;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 4/24/2024 1:16 PM
 * description
 */
public class SalesRepSerializer extends JsonSerializer<SalesRep> {
    /**
     * @param salesRep SalesRep class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(SalesRep salesRep,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (salesRep != null) {
            jsonGenerator.writeString(salesRep.getAlias());
        }
    }
}
