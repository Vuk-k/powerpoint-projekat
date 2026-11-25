package raf.graffito.dsw.model.mts;

public abstract class DecoratedNode extends GraffNode{
    GraffNode node;

    public DecoratedNode(GraffNode node) {
        super(node.parent,node.naziv);
        this.node = node;
    }


}
