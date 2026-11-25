package raf.graffito.dsw.controller;

import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.messages.ErrorMessage;
import raf.graffito.dsw.model.mts.*;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class AddNewSlideAction extends AbstractGraffAction {
    public AddNewSlideAction() {
        putValue(SMALL_ICON, loadIcon("/images/slide_add.png")); // Postavljanje ikonice
        putValue(NAME, "Add new slide"); // Ime akcije
        putValue(SHORT_DESCRIPTION, "Dodaj novi slajd u izabranu kolekciju"); // Tooltip
    }

    // Metoda za učitavanje ikonice iz resursa
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
        var graffTree = MainFrame.getInstance().getContentPane().getGraffTree();
        if (((GraffTreeItem) graffTree.getSelectedItem()) == null) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("NOTHING_SELECTED", "error"));
            return;
        }
        if (((GraffTreeItem) graffTree.getSelectedItem()).getGNode() instanceof Slide) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("NEW_SLIDE_ERROR_NO_ADDING_TO_SLIDES", "error"));
            return;
        }
        var sel = (GraffTreeItem) graffTree.getSelectedItem();
        if (sel == null) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("NEW_SLIDE_ERROR_NO_OBJECT_SELECTED", "error"));
            return;
        }
        int snum = sel.getGNode() instanceof Workspace ? 0 : ((sel.getGNode()instanceof Presentation? ((Presentation)sel.getGNode()).getNumborOfSlides() : ((Project)sel.getGNode()).getNumberOfSlides()) + 1);
        int iter = 0;
        while (true)
        {
            if (sel.getGNode().pretraziPoImenu("Slajd " + (snum + iter)) != null) {
                iter++;
                continue;
            }
            graffTree.addChild(sel, ApplicationFramework.getInstance().getGraffObjectFactory().getComponent("Slide", new Object[] { sel.getGNode(), "Slajd " + (snum + iter) }));
            break;
        }
    }
}
