package raf.graffito.dsw.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.slajd.Slika;

import java.io.IOException;

public class SlikaDeserializer extends JsonDeserializer<Slika> {
    @Override
    public Slika deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String url = node.has("url") ? node.get("url").toString():null;
        System.out.println(url);
        int x = node.has("x") ? node.get("x").asInt() : 0;
        int y = node.has("y") ? node.get("y").asInt() : 0;
        int width = node.has("width") ? node.get("width").asInt() : 0;
        int height = node.has("height") ? node.get("height").asInt() : 0;
        url = url.replaceAll("\"", "");
        ApplicationFramework.getInstance().loadNewImage(url);
        return new Slika(x,y, width, height,url);

    }
}
