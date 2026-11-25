package raf.graffito.dsw.view.komponente;

import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;

public class SideToolBar extends JToolBar {
    public SideToolBar(AbstractGraffAction[] Actions) {
        super(HORIZONTAL);
        setFloatable(false);
        for (var a : Actions) {
            add(a);
        }
    }
}
