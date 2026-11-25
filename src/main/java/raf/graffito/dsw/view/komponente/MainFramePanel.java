package raf.graffito.dsw.view.komponente;

import lombok.Getter;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.controller.GraffTreeImplementation;

import javax.crypto.spec.DESedeKeySpec;
import javax.swing.*;
import java.awt.*;

public class MainFramePanel extends JPanel {
    @Getter
    GraffTreeImplementation graffTree;
    @Getter
    PainSub pain;
    @Getter
    DesniPanel desniPanel;
    @Getter
    JLabel detaljiProjekta;
    @Getter
    JLabel autorProjekta;
    @Getter
    JLabel detaljiPrezentacije;
    @Getter
    JTree projectTree;



    public MainFramePanel() {
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());

        graffTree = new GraffTreeImplementation();
        System.out.println(ApplicationFramework.getInstance());
        projectTree = graffTree.generateTree(ApplicationFramework.getInstance().getWorkspace());

        JScrollPane scroll = new JScrollPane(projectTree);
        scroll.setMinimumSize(new Dimension(200, 150));
        pain = new PainSub();

        detaljiProjekta = new JLabel("Naziv Projekta:");
        autorProjekta = new JLabel("Autor Projekta:");
        detaljiPrezentacije = new JLabel("Naziv Prezentacije:");

        Dimension d = new Dimension(200,100);
        scroll.setPreferredSize(d);
        add(scroll,BorderLayout.WEST);

        add(pain,BorderLayout.CENTER);
        pain.setPreferredSize(new Dimension(700,100));
        pain.setMinimumSize(new Dimension(700,100));
        desniPanel = new DesniPanel();
        desniPanel.setPreferredSize(new Dimension(300,100));
        add(desniPanel,BorderLayout.EAST);
    }
}
