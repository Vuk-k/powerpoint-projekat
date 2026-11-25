package raf.graffito.dsw.controller;

import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.view.prozori.AboutUsFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class AboutUsAction extends AbstractGraffAction {
    public AboutUsAction() {
        putValue(SMALL_ICON, loadIcon("/images/info.png")); // Postavljanje ikonice
        putValue(NAME, "Info"); // Ime akcije
        putValue(SHORT_DESCRIPTION, "Informacije o studentima"); // Tooltip
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
            System.err.println("File " + path + " not found");
        }
        return icon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AboutUsFrame abf = new AboutUsFrame();
    }
}
