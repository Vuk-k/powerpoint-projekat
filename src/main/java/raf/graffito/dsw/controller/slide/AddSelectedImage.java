package raf.graffito.dsw.controller.slide;

import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.model.slajd.Slika;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class AddSelectedImage extends AbstractGraffAction {
    private String icon = null;
    public AddSelectedImage(String icon) {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK)); // Ovo je prečica za izlaz
        putValue(SMALL_ICON, loadIcon(icon)); // Postavljanje ikonice
        putValue(NAME, "Add image"); // Ime akcije
        putValue(SHORT_DESCRIPTION, "Adds an image to the slide"); // Tooltip
        this.icon = icon;
    }
    @Override
    public Icon loadIcon(String path) {
        Icon icon = null;
        URL ImageURL = getClass().getResource(path); // URL služi za pronalaženje resursa unutar JAR fajla ili klase
        if (ImageURL != null) {
            Image img = new ImageIcon(ImageURL).getImage(); // Napravimo Image iz ImageIcon
            Image newImg = img.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
            icon = new ImageIcon(newImg);
        } else {
            Image i = null;
            try {
                i = new ImageIcon(ImageIO.read(new File(URLDecoder.decode(path, StandardCharsets.UTF_8)))).getImage();
            } catch (IOException e) {
                System.out.println(e.getMessage() + "\nUrl: " + path);
            }
            icon = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            //System.err.println("File " + path + " not found");
        }
        return icon;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getContentPane().getPain().getSlajdMaker().addNewElement(new Slika(20, 20, icon));
    }
}
