package raf.graffito.dsw.controller;

import raf.graffito.dsw.model.AbstractGraffAction;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.messages.MessageGenerator;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveAsTemplateAction extends AbstractGraffAction {
    SaveAsTemplateAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
        putValue(NAME, "Save as a template"); // Ime akcije
        putValue(SHORT_DESCRIPTION, "Sacuvaj projekat kao sablon"); // Tooltip
    }
    @Override
    public Icon loadIcon(String path) {
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!(MainFrame.getInstance().getContentPane()
                .getGraffTree().getSelectedItem().getGNode() instanceof Project)) {

            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("MORATE DA SELEKTUJETE PROJEKAT ZA ČUVANJE", "error"));
            return;
        }

        Project p = (Project) MainFrame.getInstance().getContentPane()
                .getGraffTree().getSelectedItem().getGNode();

        if (p == null) return;

        JacksonSerializer js = new JacksonSerializer();

        if (Files.notExists(Path.of("src/main/resources/templates/"))) {
            try {
                Files.createDirectories(Path.of("src/main/resources/templates/"));
            } catch (IOException ex) {
                MessageGenerator mg = ApplicationFramework.getInstance().getMessageGenerator();
                mg.notifySubscriers(mg.generateMessage("ERROR_WITH_MAKING_TEMPLATES_FOLDER", "error"));
                return;
            }
        }

        File file = new File("src/main/resources/templates/" + p.getNaziv().replaceAll("\\s+", "_")+ "_template" + ".json");


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
        p.setSacuvan(false);
        js.saveProject(p, file);
        JOptionPane.showMessageDialog(MainFrame.getInstance(),
                "Projekat uspešno sačuvan:\n" + file.getAbsolutePath());
        p.udomi((GraffNodeComposite) parent, index - 1);
    }
}
