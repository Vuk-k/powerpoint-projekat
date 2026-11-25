package raf.graffito.dsw.model.mts;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.model.IPublisher;
import raf.graffito.dsw.model.ISubscriber;
import raf.graffito.dsw.view.prozori.MainFrame;

import java.util.ArrayList;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tip"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = Presentation.class, name = "Prezentacija"),
        @JsonSubTypes.Type(value = Slide.class, name = "Slajd"),
        @JsonSubTypes.Type(value = Project.class,name = "Projekat")
})

public abstract class GraffNode implements IPublisher {
    @JsonBackReference
    @Getter
    GraffNode parent;
    @Getter
    @Setter
    String naziv;

    ArrayList<ISubscriber> subs = new ArrayList<>();



    public GraffNode(GraffNode parent, String naziv) {
        this.parent = parent;
        this.naziv = naziv;
       if(!(this instanceof Workspace)) MainFrame.getInstance().subscribeDesniPanel(this);
    }
    @JsonCreator
    protected GraffNode() {
    }

    public GraffNode pretraziPoImenu(String ime) {
        if (this instanceof GraffNodeComposite) {
            GraffNodeComposite gnc = (GraffNodeComposite) this;
            var deca = gnc.getChildren();
            for (int i = 0; i < deca.size(); ++i) {
                if (deca.get(i).naziv.equals(ime)) return deca.get(i);
                else {
                    var res = deca.get(i).pretraziPoImenu(ime);
                    if (res != null) {
                        return res;
                    }
                }
            }

            return null;
        }

        return naziv.equals(ime) ? this : null;
    }
    public void osiroci() {
        if (parent == null) return;
        if (((GraffNodeComposite)parent).children.contains(this)) ((GraffNodeComposite)parent).removeChild(this);
        parent = null;
    }
    public void udomi(GraffNodeComposite parent, int index) {
        if (this.parent != null) return;

        this.parent = parent;
        parent.children.add(index, this);
    }
    public void udomi(GraffNodeComposite parent) {
        if (this.parent != null) return;

        this.parent = parent;
        parent.addChild(this);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof GraffNode) {
            return this.getNaziv().equals(((GraffNode)obj).getNaziv());
        }

        return false;
    }

    @Override
    public boolean addSuscriber(ISubscriber s) {
        subs.add(s);
        return true;
    }

    @Override
    public boolean removeSubscriber(ISubscriber s) {
        subs.remove(s);
        return true;
    }

    @Override
    public void notifySubscriers(Object notf) {
        for(ISubscriber s : subs)
        {
            s.notify(notf);

        }
    }
}
