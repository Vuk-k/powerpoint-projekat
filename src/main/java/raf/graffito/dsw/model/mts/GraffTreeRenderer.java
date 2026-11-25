package raf.graffito.dsw.model.mts;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class GraffTreeRenderer extends DefaultTreeCellRenderer {
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        URL img = null;

        if (((GraffTreeItem) value).getGNode() instanceof Workspace) {
            img = getClass().getResource("/images/work-table.png");
        }

        if (((GraffTreeItem) value).getGNode() instanceof Project) {
            img = getClass().getResource("/images/project.png");
        }

        if (((GraffTreeItem) value).getGNode() instanceof Presentation) {
            img = getClass().getResource("/images/presentation.png");
        }

        if (((GraffTreeItem) value).getGNode() instanceof Slide) {
            img = getClass().getResource("/images/slide.png");
        }

        Icon i = null;

        if (img != null) {
            i = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(20, 20, 1));
        }

        setIcon(i);

        return this;
    }
}
