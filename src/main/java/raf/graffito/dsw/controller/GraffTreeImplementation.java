package raf.graffito.dsw.controller;

import lombok.Getter;
import raf.graffito.dsw.model.mts.*;
import raf.graffito.dsw.view.komponente.GraffTreeView;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class GraffTreeImplementation implements IGraffTree {
    private GraffTreeView graffView;
    @Getter
    private DefaultTreeModel treeModel;

    @Override
    public GraffTreeView generateTree(Workspace workspace) {
        GraffTreeItem root = new GraffTreeItem(workspace);
        treeModel = new DefaultTreeModel(root);
        graffView = new GraffTreeView(treeModel);

        return graffView;
    }

    @Override
    public GraffTreeItem getSelectedItem() {
        return (GraffTreeItem) graffView.getLastSelectedPathComponent();
    }

    @Override
    public GraffNode createNode(GraffNode parent) {
        return null;
    }

    @Override
    public void addChild(GraffTreeItem parent, GraffNode child) {
        if (!(parent.getGNode() instanceof GraffNodeComposite)) return;

        if (((GraffNodeComposite)parent.getGNode()).addChild(child)) parent.add(new GraffTreeItem(child));
        graffView.expandPath(graffView.getSelectionPath());

        SwingUtilities.updateComponentTreeUI(graffView);
    }

    @Override
    public void removeChild() {

    }

}
