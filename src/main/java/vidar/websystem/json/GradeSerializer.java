package vidar.websystem.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vidar.websystem.domain.Grade;

import java.io.IOException;

/**
 * @author yishi.xing
 * create datetime 5/14/2024 1:43 PM
 * description
 */
public class GradeSerializer extends JsonSerializer<Grade> {
    /**
     * @param grade Grade class object
     * @param jsonGenerator generator
     * @param serializerProvider serializer provider
     * @throws IOException exception
     */
    @Override
    public void serialize(Grade grade, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (grade != null)
            jsonGenerator.writeString(grade.getAlias());
    }
}
