package raf.graffito.dsw.controller;

import com.sun.tools.javac.Main;
import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.model.WindowState;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SmallWIndowAction extends AbstractGraffAction {
    public SmallWIndowAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.ALT_MASK));
        putValue(NAME, "SmallWindow"); // Ime akcije
        putValue(SHORT_DESCRIPTION, "Exit"); // Tooltip
    }

    @Override
    public Icon loadIcon(String path) {
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().setWindowState(WindowState.SMALL);
    }
}
