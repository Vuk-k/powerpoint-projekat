package raf.graffito.dsw.serializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.awt.Color;
import java.io.IOException;

public class ColorDeserializer extends JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        int red = node.has("red") ? node.get("red").asInt() : 0;
        int green = node.has("green") ? node.get("green").asInt() : 0;
        int blue = node.has("blue") ? node.get("blue").asInt() : 0;
        int alpha = node.has("alpha") ? node.get("alpha").asInt() : 255;

        return new Color(red, green, blue, alpha);
    }
}

