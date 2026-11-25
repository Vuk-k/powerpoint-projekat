package raf.graffito.dsw.model.slajd;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import raf.graffito.dsw.model.mts.Presentation;

import java.awt.*;

public class Pravougaonik extends SlajdElement {
    @JsonCreator
    protected Pravougaonik(){};
    public Pravougaonik(int x, int y, int height, int width, Color color) {
        setX(x);
        setY(y);
        setHeight(height);
        setWidth(width);
        setColor(color);
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
    }

    @Override
    public SlajdElement clone() {
        SlajdElement copy = new Pravougaonik(getX(), getY(), getHeight(), getWidth(), getColor());
        return copy;
    }
}
