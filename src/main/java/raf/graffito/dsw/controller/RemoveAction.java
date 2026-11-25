package raf.graffito.dsw.controller;

import com.sun.tools.javac.Main;
import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.mts.*;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class RemoveAction extends AbstractGraffAction {
    public RemoveAction() {
        putValue(SMALL_ICON, loadIcon("/images/kanta.png")); // Postavljanje ikonice
        putValue(NAME, "Remove"); // Ime akcije
        putValue(SHORT_DESCRIPTION, "Obrsi komponentu"); // Tooltip
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
        var graffTree = MainFrame.getInstance().getContentPane().getGraffTree();
        var sel = (GraffTreeItem) graffTree.getSelectedItem();
        if (sel == null) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("NOTHING_SELECTED", "error"));
            return;
        }
        if (sel.getGNode() instanceof Workspace) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("CANNOT_DELETE_WORKSPACE", "error"));
            return;
        }

        if (sel.getGNode() instanceof Project) {
            MainFrame.getInstance().getContentPane().getPain().removeAll();
        } else if (sel.getGNode().getParent() instanceof Project) {
            MainFrame.getInstance().getContentPane().getPain().load((Project) sel.getGNode().getParent());
        }
        ((GraffNodeComposite)sel.getGNode().getParent()).removeChild(sel.getGNode());
        sel.getGNode().osiroci();
        sel.removeFromParent();
        graffTree.getTreeModel().reload();
    }
}
