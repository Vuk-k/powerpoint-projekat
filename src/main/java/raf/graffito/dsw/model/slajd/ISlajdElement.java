package raf.graffito.dsw.model.slajd;

import java.awt.*;

public interface ISlajdElement {
    public ISlajdElement clone();
    public void setDimensions(int dx, int dy);
    public void move(int dx, int dy);
    public void rotate(int side);
    public void draw(Graphics2D g2d);
}
