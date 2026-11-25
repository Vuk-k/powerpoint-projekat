package raf.graffito.dsw.controller;

import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.mts.GraffNode;
import raf.graffito.dsw.model.mts.GraffNodeComposite;
import raf.graffito.dsw.model.mts.Project;
import raf.graffito.dsw.model.mts.Workspace;
import raf.graffito.dsw.serializer.JacksonSerializer;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class SaveAsAction extends AbstractGraffAction {
    SaveAsAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
        putValue(NAME, "Save as"); // Ime akcije
        putValue(SHORT_DESCRIPTION, "Sacuvaj projekat as"); // Tooltip
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
        if (!(MainFrame.getInstance().getContentPane()
                .getGraffTree().getSelectedItem().getGNode() instanceof Project)) {

            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("MORATE DA SELEKTUJETE PROJEKAT ZA ČUVANJE", "error"));
            return;
        }

        Project p = (Project) MainFrame.getInstance().getContentPane()
                .getGraffTree().getSelectedItem().getGNode();

        if (p == null) return;

        JacksonSerializer js = new JacksonSerializer();

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Sačuvaj projekat");
        chooser.setFileFilter(new FileNameExtensionFilter("JSON fajl", "json"));

        chooser.setSelectedFile(new File(p.getNaziv().replaceAll("\\s+", "_") + ".json"));

        int result = chooser.showSaveDialog(MainFrame.getInstance());
        if (result != JFileChooser.APPROVE_OPTION)
            return;

        File file = chooser.getSelectedFile();


        if (!file.getName().toLowerCase().endsWith(".json")) {
            file = new File(file.getParentFile(), file.getName() + ".json");
        }


        if (file.exists()) {
            int overwrite = JOptionPane.showConfirmDialog(
                    MainFrame.getInstance(),
                    "Fajl već postoji. Da li želite da ga zamenite?",
                    "Potvrda",
                    JOptionPane.YES_NO_OPTION
            );
            if (overwrite != JOptionPane.YES_OPTION) return;
        }
        GraffNode parent = p.getParent();
        int index = ((Workspace)parent).getNumberOfProjects();
        p.osiroci();
        p.setUrl(file.getAbsolutePath());
        p.setSacuvan(true);
        js.saveProject(p, file);
        JOptionPane.showMessageDialog(MainFrame.getInstance(),
                "Projekat uspešno sačuvan:\n" + file.getAbsolutePath());
        p.udomi((GraffNodeComposite) parent, index - 1);
    }
}
