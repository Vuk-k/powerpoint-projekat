package raf.graffito.dsw.view.komponente;

import raf.graffito.dsw.model.WindowState;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.print.attribute.standard.JobKOctets;
import javax.swing.*;

public class MyToolBar extends JToolBar {
    JRadioButton small = null;
    JRadioButton normal = null;
    JRadioButton full = null;
    ButtonGroup bg = null;
    public MyToolBar() {
        super(HORIZONTAL);
        setFloatable(false);
        add(MainFrame.getActionManager().getExitAction());
        add(MainFrame.getActionManager().getAddNewProjectAction());
        add(MainFrame.getActionManager().getAddNewPresentationAction());
        add(MainFrame.getActionManager().getAddNewSlideAction());
        add(MainFrame.getActionManager().getRemoveAction());
        small = new JRadioButton(MainFrame.getActionManager().getSmallWIndowAction());
        normal = new JRadioButton(MainFrame.getActionManager().getNormalWindowAction());
        full = new JRadioButton(MainFrame.getActionManager().getFullScreenWindowAction());
        bg = new ButtonGroup();


        bg.add(normal);
        bg.add(small);
        bg.add(full);
        add(small);
        add(normal);
        add(full);

        bg.setSelected(normal.getModel(), true);


        add(MainFrame.getActionManager().getAboutUsAction());
    }

    public void setWindowState(WindowState ws) {
        switch (ws) {
            case SMALL: bg.setSelected(small.getModel(), true); break;
            case NORMAL: bg.setSelected(normal.getModel(), true); break;
            case LARGE: bg.setSelected(full.getModel(), true); break;
        }
    }
}
