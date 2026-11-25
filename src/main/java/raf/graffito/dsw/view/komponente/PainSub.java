package raf.graffito.dsw.view.komponente;

import lombok.Getter;
import raf.graffito.dsw.model.ISubscriber;
import raf.graffito.dsw.model.mts.*;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.*;

@Getter
public class PainSub extends JTabbedPane implements ISubscriber {
    private Project loadedProject = null;
    private SlajdMaker slajdMaker = null;
    public void load(Project sel) {
        var toremeber = getSelectedIndex();
        removeAll();

        var childrencad = ( sel).getChildren();

        for (int i = 0; i < childrencad.size(); ++i) {
            if (childrencad.get(i) instanceof Presentation) {
                JPanel panel = new JPanel();
                addTab(childrencad.get(i).getNaziv(), panel);
                setBackgroundAt(getTabCount() - 1, ((ColoredProject) sel).getBoja());
            }
        }

        if (getTabCount() <= toremeber) setSelectedIndex(toremeber);

        loadedProject = sel;
    }

    public void loadSlide(Slide s) {
        if (s.getParent() instanceof Project && s.getParent() != loadedProject ||
                s.getParent() instanceof Presentation && s.getParent().getParent() != loadedProject) {
            System.out.println("Slajd nije iz ovog projekta");
            return;
        }

        removeAll();
        load(loadedProject);

        if (s.getParent() instanceof Presentation) {
            String naziv = s.getParent().getNaziv();

            for (int i = 0; i < getTabCount(); ++i) {
                var tab = getTitleAt(i);
                System.out.println(tab + " " + naziv);
                if (tab.equals(naziv)) {
                    setSelectedIndex(i);
                    ((JPanel) getComponentAt(i)).removeAll();
                    ((JPanel) getComponentAt(i)).add((slajdMaker = new SlajdMaker(s)));
                    slajdMaker.update();
                    return;
                }
            }
        }

        removeAll();

        if (getTabCount() == 0) {
            JPanel panel = new JPanel();
            panel.add((slajdMaker = new SlajdMaker(s)));
            slajdMaker.update();
            addTab(s.getNaziv(), panel);
        }
    }

    @Override
    public void notify(Object notf) {
        load((Project) notf);
    }
}
