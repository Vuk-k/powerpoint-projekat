package raf.graffito.dsw.controller.slide;

import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.slajd.Krug;
import raf.graffito.dsw.model.slajd.Pravougaonik;
import raf.graffito.dsw.model.slajd.Text;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Arrays;

public class AddNewAction extends AbstractGraffAction {
    String[] availableElements = {
            "Pravougaonik", "Krug", "Image", "Logo"
    };

    public AddNewAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK)); // Ovo je prečica za izlaz
        putValue(NAME, "add"); // Ime akcije
        putValue(SHORT_DESCRIPTION, "Add new object to slide"); // Tooltip
        putValue(SMALL_ICON, loadIcon("/images/add_rectangle.png"));
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
        String option = (String) JOptionPane.showInputDialog(MainFrame.getInstance(),
                "Izaberi opciju:", "Input",
                JOptionPane.INFORMATION_MESSAGE, null, availableElements, availableElements[0]);
        if (Arrays.stream(availableElements).toList().contains(option)) {
            var el = ApplicationFramework.getInstance().getElementFactory().dajElement(option);
            if (el != null) MainFrame.getInstance().getContentPane().getPain().getSlajdMaker().addNewElement(el);
            else System.out.println("Opcija " + option + " is null");
            MainFrame.getInstance().getContentPane().getPain().getSlajdMaker().repaint();
        }
    }
}
