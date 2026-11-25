package raf.graffito.dsw.model.mts;

import lombok.Getter;

import javax.swing.tree.DefaultMutableTreeNode;

public class GraffTreeItem extends DefaultMutableTreeNode {
    @Getter
    private GraffNode gNode;

    public GraffTreeItem(GraffNode gNode) {
        this.gNode = gNode;
    }

    public void setName(String name) {
        gNode.setNaziv(name);
    }

    @Override
    public String toString() {
        return gNode.getNaziv();
    }
}
