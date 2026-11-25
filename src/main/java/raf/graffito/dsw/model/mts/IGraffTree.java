package raf.graffito.dsw.model.mts;

import raf.graffito.dsw.view.komponente.GraffTreeView;

public interface IGraffTree {
    GraffNode createNode(GraffNode parent);
    void addChild(GraffTreeItem parent, GraffNode child);
    void removeChild();
    GraffTreeView generateTree(Workspace workspace);
    GraffTreeItem getSelectedItem();
}
