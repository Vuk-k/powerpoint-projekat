package raf.graffito.dsw.view.komponente;

import com.sun.tools.javac.Main;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.mts.*;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraffTreeView extends JTree {


    public GraffTreeView(DefaultTreeModel model) {
        setModel(model);
        GraffTreeRenderer render = new GraffTreeRenderer();
        addTreeSelectionListener(new GraffTreeSelectionListener());
        setCellEditor(new GraffTreeCellEditor(this, render));
        setCellRenderer(render);
        setEditable(false);
        setDragEnabled(true);
        setDropMode(DropMode.ON_OR_INSERT);
        setTransferHandler(new GraffTreeTransferHandler());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                var sel = MainFrame.getInstance().getContentPane().getGraffTree().getSelectedItem();

                if (sel == null) return;

                if (sel.getGNode() instanceof Workspace) return;

                if ((e.getClickCount() == 1 && sel.getGNode() instanceof Slide) || e.getClickCount() == 2) {
                    if (sel.getGNode() instanceof Project) {
                        var pain = MainFrame.getInstance().getContentPane().getPain();
                        pain.load((Project) sel.getGNode());
                    }


                    var desniPanel = MainFrame.getInstance().getContentPane().getDesniPanel();
                    desniPanel.loadComponent(sel.getGNode());
                    if (sel.getGNode() instanceof Slide) {
                        MainFrame.getInstance().getContentPane().getPain().loadSlide((Slide) sel.getGNode());
                    }
                }

                if (e.getClickCount() == 3) {
                    if (sel.getGNode() instanceof Project) {
                        String option = (String) JOptionPane.showInputDialog(MainFrame.getInstance(),
                                "Izaberi opciju:", "Input",
                                JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Promeni naziv", "Promeni boju","Promeni Autora"}, "Promeni naziv");

                        //System.out.println(option);
                        if (option == null) return;
                        if (option.equals("Promeni naziv")) {
                            String noviNaziv = JOptionPane.showInputDialog(MainFrame.getInstance(), "Unesi naziv: ").trim();
                            //System.out.println("|" + noviNaziv + "|");
                            if (noviNaziv == null || noviNaziv == "" || noviNaziv.length() == 0) return;

                            if (sel.getGNode().getParent().pretraziPoImenu(noviNaziv) != null) {
                                ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("NAME_ALREADY_EXISTS", "error"));
                                return;
                            }
                            sel.setName(noviNaziv);
                            ((Project) sel.getGNode()).notifySubscriers(sel.getGNode());

                            SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getContentPane().getProjectTree());
                        } else if (option.equals("Promeni boju")) {
                            JColorChooser colorChooser = new JColorChooser();
                            Color boja = JColorChooser.showDialog(MainFrame.getInstance(),"Izaberi Boju",Color.WHITE);
                            ((ColoredProject)sel.getGNode()).setBoja(boja);
                            ((ColoredProject)sel.getGNode()).notifySubscriers(sel.getGNode());



                        } else if(option.equals("Promeni Autora")){

                            String noviAutor = JOptionPane.showInputDialog(MainFrame.getInstance(), "Unesi naziv autora: ");
                                    if(noviAutor == null || noviAutor == "") return;

                             ((Project) sel.getGNode()).setAuthor(noviAutor);



                        }
                        return;
                    }

                    String noviNaziv = JOptionPane.showInputDialog(MainFrame.getInstance(), "Unesi naziv: ");
                    if (noviNaziv == null || noviNaziv == "") return;

                    if (sel.getGNode().getParent().pretraziPoImenu(noviNaziv) != null) {
                        ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("NAME_ALREADY_EXISTS", "error"));
                        return;
                    }
                    sel.setName(noviNaziv);
                    sel.getGNode().notifySubscriers(sel.getGNode());

                    SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getContentPane().getProjectTree());
                }
            }
        });
    }
}
