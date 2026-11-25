package raf.graffito.dsw.model.slajd;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Krug extends SlajdElement {
    @JsonCreator
    protected Krug(){}
    public Krug(int x, int y, int height, int width, Color c) {
        setX(x);
        setY(y);
        setHeight(height);
        setWidth(width);
        setColor(c);
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        int centerX = getX() + getWidth() / 2;
        int centerY = getY() + getHeight() / 2;
        g2d.rotate(getRotationAngle(), centerX, centerY); // [cite: 93]
        if (isSelected()) {
            g2d.setColor(Color.GREEN);

            g2d.fillOval((int)(getX() - getSelectionOffset() / 2), (int)(getY() -  getSelectionOffset() / 2), (int)(getWidth() +  getSelectionOffset() / 2), (int)(getHeight() +  getSelectionOffset()));
            g2d.setColor(Color.RED);

            g2d.fillOval((int)(getX() - getSelectionOffset() / 4), (int)(getY() -  getSelectionOffset() / 4), (int)(getWidth() +  getSelectionOffset() / 2),(int) (getHeight() +  getSelectionOffset() / 2));
        }

        g2d.setColor(getColor());

        g2d.fillOval(getX(), getY(), getWidth(), getHeight());

        g2d.setTransform(oldTransform);
    }
}
