package raf.graffito.dsw.view.komponente;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class WorkspaceTreeComponent extends JTree {
    private DefaultMutableTreeNode root;

    public WorkspaceTreeComponent() {
        super(new DefaultMutableTreeNode("Workspace"));
        root = (DefaultMutableTreeNode) getDefaultTreeModel().getRoot();
        setRootVisible(true);

    }
}
