package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.WoodSpecies;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/14/2024 1:39 PM
 * description
 */
public class WoodSpeciesSerializer extends JsonSerializer<WoodSpecies> {
    /**
     * @param woodSpecies WoodSpecies class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(WoodSpecies woodSpecies, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (woodSpecies != null)
            jsonGenerator.writeString(woodSpecies.toString());

    }
}
