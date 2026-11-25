package raf.graffito.dsw.model.mts;

public abstract class GraffLeaf extends GraffNode {
    public GraffLeaf(GraffNode parent, String naziv) {
        super(parent, naziv);
    }

    protected GraffLeaf() {
    }
}
