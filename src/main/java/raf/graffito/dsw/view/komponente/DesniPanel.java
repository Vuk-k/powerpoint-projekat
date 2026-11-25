package raf.graffito.dsw.view.komponente;

import lombok.Getter;
import raf.graffito.dsw.model.ISubscriber;
import raf.graffito.dsw.model.mts.*;

import javax.swing.*;

public class DesniPanel extends JPanel implements ISubscriber {
    @Getter
    private JPanel content = null;
    @Getter
    private GraffNode loadedComponent = null;
    public DesniPanel(){
        setDefault();
        add(content);

    }

    public void loadComponent(GraffNode gNode) {
        remove(content);
        content = null;
        if (gNode instanceof Workspace) setDefault();
        if (gNode instanceof Slide) content = new SlideView((Slide)gNode);
        if (gNode instanceof Project) content = new ProjectView((Project)gNode);
        if (gNode instanceof Presentation) content = new PresentationView((Presentation)gNode);
        add(content);
        SwingUtilities.updateComponentTreeUI(this);
        loadedComponent = gNode;
    }

    public void setDefault() {
        loadedComponent = null;
        content = new JPanel();
        content.add(new JLabel("NEW WORKSPACE"));
    }

    @Override
    public void notify(Object notf) {
        loadComponent((GraffNode) notf);
    }
}
