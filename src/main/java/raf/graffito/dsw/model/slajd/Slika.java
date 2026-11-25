package raf.graffito.dsw.model.slajd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.model.mts.Slide;
import raf.graffito.dsw.serializer.SlikaDeserializer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
@JsonDeserialize(using = SlikaDeserializer.class)
public class Slika extends SlajdElement {
    @JsonIgnore
    private Image image;
    @JsonIgnore
    private Image renderedImage;
    private String url = "";
    @JsonCreator
    protected Slika(){}
    /*public Slika(int x, int y, Image i) {
        setX(x);
        setY(y);
        //setDimensions(i.getWidth(null), i.getHeight(null));
        this.image = i;
        renderedImage = new ImageIcon(i).getImage();
        url = new ImageIcon(i).getImage() + "";
        int z = 10;
    }*/
    public Slika(int x, int y, String url) {
        setX(x);
        setY(y);
        //setDimensions(i.getWidth(null), i.getHeight(null));
        Image i = null;
        url = url.replaceAll("\"", "");
        System.out.println(url);
        try {
            i = new ImageIcon(ImageIO.read(new File(URLDecoder.decode(url, StandardCharsets.UTF_8)))).getImage();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (i == null) return;
        this.image = i;
        renderedImage = new ImageIcon(i).getImage();
        this.url = url;
    }
    public Slika(int x, int y, int width, int height, String url) {
        setX(x);
        setY(y);
        //setDimensions(i.getWidth(null), i.getHeight(null));
        //setDimensions(i.getWidth(null), i.getHeight(null));
        Image i = null;
        url = url.replaceAll("\"", "");
        System.out.println(url);
        try {
            i = new ImageIcon(ImageIO.read(new File(URLDecoder.decode(url, StandardCharsets.UTF_8)))).getImage();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\nUrl: " + url);
        }

        if (i == null) return;
        this.image = i;
        this.url = url;
        if (width >= 0 && height >= 0) renderedImage = new ImageIcon(i.getScaledInstance(width,height,Image.SCALE_SMOOTH)).getImage();
        else {
            renderedImage = new ImageIcon(i).getImage();
        }
        if (renderedImage == null) {
            System.out.println("OPALA");
        }
    }

    public void draw(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        int centerX = getX() + getWidth() / 2;
        int centerY = getY() + getHeight() / 2;
        g2d.rotate(getRotationAngle(), centerX, centerY);

        if (isSelected()) {
            g2d.setColor(Color.GREEN);

            g2d.fill(new Rectangle2D.Double(getX() - selectionOffset / 2, getY() - selectionOffset / 2, getWidth() + selectionOffset, getHeight() + selectionOffset));
            g2d.setColor(Color.RED);

            g2d.fill(new Rectangle2D.Double(getX() - selectionOffset / 4, getY() - selectionOffset / 4, getWidth() + selectionOffset / 2, getHeight() + selectionOffset / 2));
        }

        g2d.drawImage(renderedImage, getX(), getY(), null);

        g2d.setTransform(oldTransform);
    }

    @Override
    public int getWidth() {
        return renderedImage.getWidth(null);
    }

    @Override
    public int getHeight() {
        return renderedImage.getHeight(null);
    }

    @Override
    public void setDimensions(int dx, int dy) {
        System.out.println(getWidth() + ", " + getHeight() + " | " + dx + ", " + dy);
        int w = Math.max(dx + getWidth(), 10);
        int h = Math.max(dy + getHeight(), 10);
        System.out.println(w + ", " + h);
        renderedImage = new ImageIcon(image.getScaledInstance(w, h, Image.SCALE_SMOOTH)).getImage();
        System.out.println(getWidth() + ", " + getHeight());
    }
}
