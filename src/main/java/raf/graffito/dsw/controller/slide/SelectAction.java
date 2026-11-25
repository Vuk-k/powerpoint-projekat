package raf.graffito.dsw.controller.slide;

import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.model.slajd.ActionMode;
import raf.graffito.dsw.model.slajd.Pravougaonik;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

public class SelectAction extends AbstractGraffAction {
    public SelectAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK)); // Ovo je prečica za izlaz
        putValue(NAME, "Select"); // Ime akcije
        putValue(SHORT_DESCRIPTION, "Selects object of a slide"); // Tooltip
        putValue(SMALL_ICON, loadIcon("/images/selection.png"));
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
        MainFrame.getInstance().getContentPane().getPain().getSlajdMaker().setActiveAction(ActionMode.SELECT);
    }
}
