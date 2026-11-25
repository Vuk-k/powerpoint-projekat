package raf.graffito.dsw.model.slajd;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Text extends SlajdElement {
    @Getter
    @Setter
    private String text;
    private int height;
    private int width;
    public Text(int x, int y, String text) {
        setX(x);
        setY(y);
        setLayer(2);
        this.text = text;
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return height;
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        width = g2d.getFontMetrics().stringWidth(text);
        height = g2d.getFontMetrics().getHeight();
        int centerX = getX() + getWidth() / 2;
        int centerY = getY() + getHeight() / 2;
        g2d.rotate(getRotationAngle(), centerX, centerY); // [cite: 93]
        Font f = new Font("Ariel", Font.PLAIN, 12);
        g2d.setFont(f);

        /*if (isSelected()) {
            g2d.setColor(Color.GREEN);

            g2d.fill(new Rectangle2D.Double(getX() - 2.5, getY() - 2.5, width + 5, height + 5));
            g2d.setColor(Color.RED);

            g2d.fill(new Rectangle2D.Double(getX(), getY(), width + 2.5, height + 2.5));
        }*/

        g2d.setColor(getColor());

        g2d.drawString(text, centerX, centerY);
        g2d.fill(new Rectangle2D.Double(getX(), getY(), width, height));

        g2d.setTransform(oldTransform);
    }
}
