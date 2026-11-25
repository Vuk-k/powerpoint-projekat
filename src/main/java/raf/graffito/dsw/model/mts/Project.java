package raf.graffito.dsw.model.mts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.IPublisher;
import raf.graffito.dsw.model.ISubscriber;
import raf.graffito.dsw.view.prozori.MainFrame;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonSubTypes({
        @JsonSubTypes.Type(value = ColoredProject.class, name = "ColoredProject"),
})
public class Project extends GraffNodeComposite{
    private String title;
    private String author;
    private int number;
    private boolean sacuvan = false;
    private String url;
    @JsonCreator
    public Project()
    {

    }

    public Project(String title, String author, GraffNode parent) {
        super(parent, title);
        this.title = title;
        this.author = author;
        this.number = 0;
        MainFrame.getInstance().subscribeDesniPanel(this);
    }

    public Project(String title, String author, int number, GraffNode parent) {
        super(parent, title);
        this.title = title;
        this.author = author;
        this.number = number;
    }
    @Override
    public boolean addChild(GraffNode child) {
        if (child != null && (child instanceof Presentation || child instanceof Slide)) { //KT1 Zadatak 2/1 "projekat može sadržati prezentacije ili slajdove"
            GraffNode c = (GraffNode) child;

            if (!this.getChildren().contains(c)) {
                this.getChildren().add(c);
                notifySubscriers(this);
            } else ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("PROJECT_ADD_CHILD_CHILD_IS_INCLUDED", "warnning"));

            return true;
        }



        if (child == null) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("PROJECT_ADD_CHILD_CHILD_IS_NULL", "error"));
            return false;
        }

        if (!(child instanceof Presentation || child instanceof Slide)) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("PROJECT_ADD_CHILD_CHILD_IS_ILLIGAL", "error"));
            return false;
        }

        ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("PROJECT_ADD_CHILD_UNKNOWN_ERROR", "error"));
        return false;
    }

    public int getNumberOfSlides() {
        int count = 0;

        for (int i = 0; i < children.size(); ++i) {
            var c = children.get(i);
            if (c instanceof Presentation) count += ((Presentation)c).getNumborOfSlides();
            else if (c instanceof Slide) count++;
        }

        return count;
    }

    public int getNumberOfPresentations() {
        int count = 0;

        for (int i = 0; i < children.size(); ++i) {
            var c = children.get(i);
            if (c instanceof Presentation) count++;
        }

        return count;
    }

    public boolean sadrziPrezentaciju(String naziv) {
        for (int i = 0; i < children.size(); ++i) {
            var c = children.get(i);
            if (c instanceof Presentation && c.getNaziv().equals(naziv)) return true;
        }

        return false;
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
        if (children.size() == 1) {
            //error
            return;
        }

        children.remove(child);
        child.osiroci();
        notifySubscriers(this);
    }


    public void setTitle(String title) {
        this.title = title;
        notifySubscriers(this);
    }

    public void setAuthor(String author) {
        this.author = author;
        notifySubscriers(this);
    }


}