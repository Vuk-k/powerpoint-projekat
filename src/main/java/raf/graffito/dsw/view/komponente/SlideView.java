package raf.graffito.dsw.view.komponente;

import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.mts.Slide;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.*;

public class SlideView extends JPanel {
    private JLabel nazivSlidea;
    private JPanel imageGrid = null;
    private JScrollPane scp = null;
    public SlideView(Slide s) {
        nazivSlidea = new JLabel("Naziv:" + s.getNaziv());

        init();
    }
    private void init()
    {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        add(nazivSlidea);
        var buttonGrid = new JPanel(new GridLayout(0, 1));
        var ms = MainFrame.getActionManager();
        buttonGrid.add(new SideToolBar(new AbstractGraffAction[]{
                ms.getAddNewAction(),
                ms.getSelectAction(),
                ms.getMoveAction(),
                ms.getRotateAction(),
                ms.getResizeAction(),
        }));
        buttonGrid.add(new SideToolBar(new AbstractGraffAction[]{
                ms.getChangeColorAction(),
                ms.getRemoveElementAction(),
                ms.getCloneAction(),
                ms.getLayerUpAction(),
                ms.getLayerDownAction(),
        }));
        buttonGrid.add(new SideToolBar(new AbstractGraffAction[] {
                ms.getRedoAction(),
                ms.getUndoAction(),
                ms.getZoomAction()
        }));

        add(buttonGrid);

/*
        imageGrid = new JPanel(new GridLayout(0, 5));
        scp = new JScrollPane(imageGrid);
        scp.setPreferredSize(new Dimension(200, 300));
        scp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ApplicationFramework.getInstance().getUneteSlike().forEach(i -> {
            imageGrid.add(new SideToolBar(new AbstractGraffAction[] { MainFrame.getActionManager().addSelectedImage(new ImageIcon(i.getRenderedImage())) }));
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    imageGrid.revalidate();
                    imageGrid.repaint();
                }
            });
        });

        add(scp);*/
        loadImageGrid();
    }

    public void loadImageGrid() {
        imageGrid = new JPanel(new GridLayout(0, 3));
        scp = new JScrollPane(imageGrid);
        scp.setPreferredSize(new Dimension(220, 300));
        scp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ApplicationFramework.getInstance().getUneteSlike().forEach(i -> {
            imageGrid.add(new SideToolBar(new AbstractGraffAction[] { MainFrame.getActionManager().addSelectedImage(i.getUrl()) }));
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    imageGrid.revalidate();
                    imageGrid.repaint();
                }
            });
        });

        add(scp);
    }
}
