package raf.graffito.dsw.model.mts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.messages.ErrorMessage;
import raf.graffito.dsw.model.messages.WarnningMessage;

public class Presentation extends GraffNodeComposite {
    private String title;
    private String author;
    @JsonCreator
    protected Presentation()
    {}

    public Presentation(GraffNode parent, String naziv, String autor) {
        super(parent, naziv);
        title = naziv;
        this.author = autor;
    }

    @Override
    public boolean addChild(GraffNode child) {
        if (child != null && child instanceof Slide) { //KT1 Zadatak 2/1 "prezentacija može sadržati samo slajdove"
            Slide s = (Slide) child;

            if (!this.getChildren().contains(s)) {
                this.getChildren().add(s);
                notifySubscriers(this);
            } else ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("PRESENTATION_ADD_CHILD_CHILD_IS_INCLUDED", "warnning"));

            return true;
        }

        if (child == null) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("PRESENTATION_ADD_CHILD_CHILD_IS_NULL", "error"));
            return false;
        }

        if (!(child instanceof Slide)) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("PRESENTATION_ADD_CHILD_CHILD_IS_ILLIGAL", "error"));
            return false;
        }

        ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("PRESENTATION_ADD_CHILD_UNKNOWN_ERROR", "error"));
        return false;
    }
    @JsonIgnore
    public int getNumborOfSlides() {
        return children.size();
    }

    public boolean sadrziSlajd(String naziv) {
        for (int i = 0; i < children.size(); ++i) {
            var c = children.get(i);
            if (c instanceof Slide && c.getNaziv().equals(naziv)) return true;
        }

        return false;
    }

    @Override
    public void removeChild(GraffNode child) {
        children.remove(child);
        child.osiroci();
    }


}
