package raf.graffito.dsw.model.mts;

import lombok.Getter;
import lombok.Setter;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class GraffTreeSelectionListener implements TreeSelectionListener {
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        GraffTreeItem graffItem = (GraffTreeItem) path.getLastPathComponent();
    }


}
