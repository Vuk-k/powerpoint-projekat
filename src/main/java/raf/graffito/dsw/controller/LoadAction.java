package raf.graffito.dsw.controller;

import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.messages.MessageGenerator;
import raf.graffito.dsw.model.mts.*;
import raf.graffito.dsw.serializer.JacksonSerializer;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class LoadAction extends AbstractGraffAction {
    public LoadAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        putValue(NAME, "Load"); // Ime akcije
        putValue(SHORT_DESCRIPTION, "Load project"); // Tooltip
    }
    @Override
    public Icon loadIcon(String path) {
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (MainFrame.getInstance().getContentPane().getGraffTree().getSelectedItem() == null) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("NOTHING_SELECTED", "error"));
            return;
        }

        if(MainFrame.getInstance().getContentPane().getGraffTree().getSelectedItem().getGNode() instanceof Workspace) {
            JacksonSerializer jack = new JacksonSerializer();

            JFileChooser chooser = new JFileChooser();
            var option = chooser.showOpenDialog(MainFrame.getInstance());

            if (option != JFileChooser.APPROVE_OPTION) return;

            File file = chooser.getSelectedFile();
            final Project p = jack.loadProject(file);
            var sel = MainFrame.getInstance().getContentPane().getGraffTree().getSelectedItem();
            Workspace wspc = ((Workspace)MainFrame.getInstance().getContentPane().getGraffTree().getSelectedItem().getGNode());
            /*var p = ApplicationFramework.getInstance().getGraffObjectFactory().getComponent("ColoredProject", new Object[] {
                    pr.getNaziv(),
                    pr.getAuthor(), wspc
            });*/
            if (wspc.sadrziProjekat(p.getNaziv())) {
                var mg = ApplicationFramework.getInstance().getMessageGenerator();
                mg.notifySubscriers(mg.generateMessage("ERROR_CANT_LOAD_PROJECT_DUE_TO_PROJECT_NAME_ALREADY_EXISTING", "error"));
                return;
            }
            var graffTree = MainFrame.getInstance().getContentPane().getGraffTree();
            graffTree.addChild(sel, p);
            p.udomi(wspc);
            var deca = p.getChildren();
            p.odlazakPoMleko();

            var toAdd = sel.getLastChild();

            deca.forEach(d -> {
                d.osiroci();
                if (d instanceof Presentation) {
                    graffTree.addChild((GraffTreeItem) toAdd, d);
                    d.udomi((GraffNodeComposite) p);

                    var ch = ((Presentation)d).getChildren();
                    ((Presentation)d).odlazakPoMleko();

                    var presentation = ((GraffTreeItem) toAdd).getLastChild();

                    ch.forEach(slajd -> {
                        if (slajd instanceof Slide) {
                            slajd.udomi((GraffNodeComposite) d);
                            graffTree.addChild((GraffTreeItem) presentation, slajd);
                        }
                    });
                } else {
                    d.udomi((GraffNodeComposite) p);
                    graffTree.addChild((GraffTreeItem) toAdd, d);
                }
            });




            //MainFrame.getInstance().getContentPane().getGraffTree().generateTree((Workspace)MainFrame.getInstance().getContentPane().getGraffTree().getSelectedItem().getGNode());



        }


        else {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("MORATE DA SELEKTUJETE WORKSPACE", "error"));
            return;


        }
    }
}
