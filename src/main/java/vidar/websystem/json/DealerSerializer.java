package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.Dealer;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 4/24/2024 12:12 PM
 * description
 */
public class DealerSerializer extends JsonSerializer<Dealer> {
    /**
     * @param dealer Dealer class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(Dealer dealer,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (dealer != null)
            jsonGenerator.writeString(dealer.getCompanyName());
    }
}
