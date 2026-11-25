package raf.graffito.dsw.model.mts;

import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.messages.ErrorMessage;
import raf.graffito.dsw.model.messages.WarnningMessage;

public class Workspace extends GraffNodeComposite {
    public Workspace(String naziv) {
        super(null, naziv);
    }
    private Workspace(GraffNode parent, String naziv) {
        super(parent, naziv);
    }

    @Override
    public boolean addChild(GraffNode child) {
        if (child != null && child instanceof Project) { //KT1 Zadatak 2/1 "Workspace može sadržati više projekata"
            Project p = (Project) child;
            if (!this.getChildren().contains(p)) {
                this.getChildren().add(p);
            } else ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("WORKSPACE_ADD_CHILD_CHILD_IS_INCLUDED", "warnning"));

            return true;
        }

        if (child == null) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("WORKSPACE_ADD_CHILD_CHILD_IS_NULL", "error"));
            return false;
        }

        if (!(child instanceof Project) ) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("WORKSPACE_ADD_CHILD_CHILD_IS_ILLIGAL", "error"));
            return false;
        }

        ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("WORKSPACE_ADD_CHILD_UNKNOWN_ERROR", "error"));
        return false;
    }

    public boolean sadrziProjekat(String naziv) {
        for (int i = 0; i < children.size(); ++i) {
            var c = children.get(i);
            if (c instanceof Project && c.getNaziv().equals(naziv)) return true;
        }

        return false;
    }

    public int getNumberOfProjects() {
        return children.size();
    }

    @Override
    public void removeChild(GraffNode child) {
        children.remove(child);
        child.osiroci();
    }
}
