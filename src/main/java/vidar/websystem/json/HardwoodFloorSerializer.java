package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.HardwoodFloor;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/16/2024 10:02 AM
 * description
 */
public class HardwoodFloorSerializer extends JsonSerializer<HardwoodFloor> {
    /**
     * @param hardwoodFloor HardwoodFloor class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(HardwoodFloor hardwoodFloor, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (hardwoodFloor != null) {
            jsonGenerator.writeString(hardwoodFloor.getSize().getWidthInInch() + '"' + ' ' +
                    hardwoodFloor.getSpecies().getName().substring(hardwoodFloor.getSpecies().getName().lastIndexOf(" ") + 1) + " " +
                    hardwoodFloor.getColor().getName() + '-' + hardwoodFloor.getGrade().getAlias() + " " +
                    hardwoodFloor.getSize().getSquarefootPerCarton() + " " + hardwoodFloor.getBatchNumber());
        }
    }
}
