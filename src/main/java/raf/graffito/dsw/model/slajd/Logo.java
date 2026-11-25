package raf.graffito.dsw.model.slajd;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Logo extends SlajdElement {
    @JsonCreator
    protected Logo() {}
    public Logo(int x, int y, int width, int height, Color c) {
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

            g2d.fill(new Rectangle2D.Double(getX() - selectionOffset / 2, getY() - selectionOffset / 2, getWidth() + selectionOffset, getHeight() + selectionOffset));
            g2d.setColor(Color.RED);

            g2d.fill(new Rectangle2D.Double(getX() - selectionOffset / 4, getY() - selectionOffset / 4, getWidth() + selectionOffset / 2, getHeight() + selectionOffset / 2));
        }

        double sectionW = (double)(getWidth()) / 3;
        double sectionH = (double)(getHeight()) / 3;
        double sectionCircleW = (double)(getWidth()) / (Math.PI * 6);
        double sectionCircleH = (double)(getHeight()) / (Math.PI * 6);

        g2d.setColor(getColor());

        g2d.fill(new Rectangle2D.Double(getX(), getY() + sectionH, getWidth(), sectionH));
        g2d.fill(new Rectangle2D.Double(getX() + sectionW, getY(), sectionW, getHeight()));
        g2d.fillOval((int)(getX() + getWidth() * 0.1), (int)(getY() + getHeight() * 0.1), (int)(getWidth() * 0.8), (int)(getHeight() * 0.8));

        g2d.setTransform(oldTransform);
    }
}
