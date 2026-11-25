package raf.graffito.dsw.model;
import lombok.Getter;
import raf.graffito.dsw.model.logger.Logger;
import raf.graffito.dsw.model.logger.LoggerFactory;
import raf.graffito.dsw.model.messages.MessageGenerator;
import raf.graffito.dsw.model.mts.GraffObjectFactory;
import raf.graffito.dsw.model.mts.GraffTreeSelectionListener;
import raf.graffito.dsw.model.mts.Workspace;
import raf.graffito.dsw.model.slajd.ElementFactory;
import raf.graffito.dsw.model.slajd.Slika;
import raf.graffito.dsw.view.komponente.SlideView;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationFramework {
    @Getter
    static ApplicationFramework instance;
    @Getter
    Workspace workspace;
    @Getter
    MessageGenerator messageGenerator;
    @Getter
    GraffObjectFactory graffObjectFactory;
    @Getter
    ElementFactory elementFactory;
    @Getter
    List<Slika> uneteSlike;
    static {
      instance = new ApplicationFramework();
    }

    private ApplicationFramework() {
    }

    public void initialize(){
        workspace = new Workspace("Workspace");
        messageGenerator = new MessageGenerator();
        messageGenerator.addSuscriber(LoggerFactory.dajLogger("file"));
        messageGenerator.addSuscriber(LoggerFactory.dajLogger("console"));
        graffObjectFactory = new GraffObjectFactory();
        elementFactory = new ElementFactory();
        uneteSlike = new ArrayList<>();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setVisible(true);
        uneteSlike.add(new Slika(-1, -1, 50, 50, getClass().getResource("/images/kanta.png").getPath()));
        uneteSlike.add(new Slika(-1, -1, 50, 50, getClass().getResource("/images/circle.png").getPath()));
        uneteSlike.add(new Slika(-1, -1, 50, 50, getClass().getResource("/images/slide.png").getPath()));
        uneteSlike.add(new Slika(-1, -1, 50, 50, getClass().getResource("/images/presentation.png").getPath()));
    }

    public void loadNewImage(String newImage) {
        for (int i = 0; i < uneteSlike.size(); ++i) {
            if (uneteSlike.get(i).getUrl().equals(newImage)) return;
        }

        uneteSlike.add(new Slika(-1, -1, 50, 50, newImage));
        var dp = MainFrame.getInstance().getContentPane().getDesniPanel();
        if (dp.getContent() instanceof SlideView) {
            dp.loadComponent(dp.getLoadedComponent());
        }
    }
}
