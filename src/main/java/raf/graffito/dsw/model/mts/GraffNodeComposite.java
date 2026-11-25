package raf.graffito.dsw.model.mts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class GraffNodeComposite extends GraffNode {
    @JsonManagedReference
    @Getter
    List<GraffNode> children;
    public GraffNodeComposite(GraffNode parent, String naziv) {
        super(parent, naziv);
        children = new ArrayList<>();
    }
    @JsonCreator
    protected GraffNodeComposite() {
        children=new ArrayList<>();
    }
    public void odlazakPoMleko() {
        children = new ArrayList<>();
    }
    public abstract boolean addChild(GraffNode child);
    public abstract void removeChild(GraffNode child);

}
