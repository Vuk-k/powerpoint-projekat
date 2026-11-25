package raf.graffito.dsw.controller;

import com.sun.tools.javac.Main;
import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.model.WindowState;
import raf.graffito.dsw.view.komponente.SlajdMaker;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NormalWindowAction extends AbstractGraffAction {
    public NormalWindowAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F6, ActionEvent.ALT_MASK));
        putValue(NAME, "HormalWindow"); // Ime akcije
        putValue(SHORT_DESCRIPTION, "Normal window size"); // Tooltip
    }

    @Override
    public Icon loadIcon(String path) {
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().setWindowState(WindowState.NORMAL);
    }
}
