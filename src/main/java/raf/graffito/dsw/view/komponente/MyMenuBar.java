package raf.graffito.dsw.view.komponente;



import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        var am = MainFrame.getActionManager();

        fileMenu.add(am.getExitAction());
        fileMenu.add(am.getAddNewProjectAction());
        fileMenu.add(am.getAddNewPresentationAction());
        fileMenu.add(am.getAddNewSlideAction());
        fileMenu.add(am.getRemoveAction());
        fileMenu.add(am.getSmallWIndowAction());
        fileMenu.add(am.getNormalWindowAction());
        fileMenu.add(am.getFullScreenWindowAction());
        fileMenu.add(am.getSaveAction());
        fileMenu.add(am.getSaveAsAction());
        fileMenu.add(am.getLoadAction());
        fileMenu.add(am.getSaveAsTemplateAction());
        fileMenu.add(am.getLoadTemplateAction());



        fileMenu.add(MainFrame.getActionManager().getAboutUsAction());
        JMenu slajdMenu = new JMenu("Slajd");
        slajdMenu.add(am.getAddNewAction());
        slajdMenu.add(am.getSelectAction());
        slajdMenu.add(am.getMoveAction());
        slajdMenu.add(am.getRotateAction());
        slajdMenu.add(am.getResizeAction());
        slajdMenu.add(am.getChangeColorAction());
        slajdMenu.add(am.getRemoveElementAction());
        slajdMenu.add(am.getCloneAction());
        slajdMenu.add(am.getLayerUpAction());
        slajdMenu.add(am.getLayerDownAction());
        slajdMenu.add(am.getRedoAction());
        slajdMenu.add(am.getUndoAction());
        slajdMenu.add(am.getZoomAction());
        add(fileMenu);
        add(slajdMenu);

    }
}
