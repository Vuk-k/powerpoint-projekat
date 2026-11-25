package raf.graffito.dsw.model.mts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.serializer.ColorDeserializer;

import java.awt.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColoredProject extends DecoratedProject {
    @Getter
    @Setter
    @JsonDeserialize(using = ColorDeserializer.class)
    Color boja = Color.WHITE;
    @JsonCreator
    public ColoredProject()
    {}

    public ColoredProject(Project dekorisanProjekat) {
        super(dekorisanProjekat.getTitle(), dekorisanProjekat.getAuthor(), dekorisanProjekat.getParent());
    }


}
