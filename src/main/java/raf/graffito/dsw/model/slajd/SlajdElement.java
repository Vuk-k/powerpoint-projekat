package raf.graffito.dsw.model.slajd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.model.mts.Presentation;
import raf.graffito.dsw.model.mts.Project;
import raf.graffito.dsw.model.mts.Slide;
import raf.graffito.dsw.serializer.ColorDeserializer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "VrstaElementa"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = Pravougaonik.class, name = "Pravougaonik"),
        @JsonSubTypes.Type(value = Krug.class, name = "Krug"),
        @JsonSubTypes.Type(value = Slika.class,name = "Slika"),
        @JsonSubTypes.Type(value = Logo.class,name = "Logo")
})


@Getter
@Setter
public abstract class SlajdElement implements ISlajdElement {
    private int x, y, width, height;
    private double rotationAngle = 0;
    private boolean isSelected = false;
    @JsonDeserialize(using = ColorDeserializer.class)
    private Color color;
    int layer = 1;
    double selectionOffset = 7.5;
    @JsonCreator
    protected SlajdElement(){}

    @Override
    public void setDimensions(int dx, int dy) {
        this.width = Math.max(10, this.width + dx);
        this.height = Math.max(10, this.height + dy);
    }
    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void rotate(int side) {
        rotationAngle += Math.toRadians(side * 2);
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();

        int centerX = x + width / 2;
        int centerY = y + height / 2;
        g2d.rotate(rotationAngle, centerX, centerY); // [cite: 93]
        if (isSelected) {
            g2d.setColor(Color.GREEN);

            g2d.fill(new Rectangle2D.Double(x - selectionOffset / 2, y - selectionOffset / 2, width + selectionOffset, height + selectionOffset));
            g2d.setColor(Color.RED);

            g2d.fill(new Rectangle2D.Double(x - selectionOffset / 4, y - selectionOffset / 4, width + selectionOffset / 2, height + selectionOffset / 2));
        }

        g2d.setColor(color);

        g2d.fill(new Rectangle2D.Double(x, y, width, height));

        g2d.setTransform(oldTransform);
    }
    public void layerUp() {
        layer++;
    }
    public void layerDown() {
        layer--;
    }

    @Override
    public SlajdElement clone() {
        return null;
    }

}
