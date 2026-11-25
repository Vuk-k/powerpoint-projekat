package raf.graffito.dsw.model.mts;

import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.messages.MessageGenerator;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class GraffTreeTransferHandler extends TransferHandler {
    private GraffTreeItem draggedItem;
    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }
    @Override
    protected Transferable createTransferable(JComponent c) {
        JTree tree = (JTree) c;
        TreePath path = tree.getSelectionPath();

        if (path == null) return null;

        draggedItem = (GraffTreeItem) path.getLastPathComponent();
        return new StringSelection("node"); // dummy, ne koristimo podatke
    }

    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDrop()) return false;

        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        TreePath targetPath = dl.getPath();

        if (targetPath == null || draggedItem == null) return false;

        DefaultMutableTreeNode targetNode =
                (DefaultMutableTreeNode) targetPath.getLastPathComponent();

        var target = ((GraffTreeItem) targetNode).getGNode();
        var dragged = ((GraffTreeItem) draggedItem).getGNode();

        if (targetNode.isNodeChild(draggedItem) && !target.getClass().equals(dragged.getClass())) return true;

        if (targetNode == draggedItem) return false;
        //System.out.println("problem");

        if (draggedItem.isNodeDescendant(targetNode)) return false;

        if (target instanceof Slide) return false;
        if (target instanceof Workspace) {
            if (dragged instanceof Project) {
                for (var c : ((Workspace) target).children) {
                    if (c.getNaziv().equals(dragged.getNaziv())) return false;
                }

                return true;
            }

            return false;
        }
        if (target instanceof Project) {
            if (dragged instanceof Presentation || dragged instanceof Slide) {
                for (var c : ((Project)target).children) {
                    if (c instanceof Presentation == dragged instanceof Presentation && c.getNaziv().equals(dragged.getNaziv())) return false;
                }

                return true;
            }
            return false;
        }
        if (target instanceof Presentation) {
            if (dragged instanceof Slide) {
                return !((Presentation)target).sadrziSlajd(dragged.naziv);
            }
            return false;
        }

        return true;
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) return false;

        JTree tree = (JTree) support.getComponent();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        TreePath targetPath = dl.getPath();
        DefaultMutableTreeNode targetNode =
                (DefaultMutableTreeNode) targetPath.getLastPathComponent();

        int index = dl.getChildIndex();

        if (index == -1) {
            index = targetNode.getChildCount();
        }

        model.removeNodeFromParent(draggedItem);
        if (index > targetNode.getChildCount()) {
            index--;
            //System.out.println("LOL");
        }
        // dodaj na novu
        try {
            model.insertNodeInto(draggedItem, targetNode, index);
        } catch(Exception e) {
            ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("DRAG_NODE_INSERT_UNKNOWN_ERROR", "Error"));
            System.out.println(e.getMessage());
        }

        draggedItem.getGNode().osiroci();
        draggedItem.getGNode().udomi((GraffNodeComposite)((GraffTreeItem)targetNode).getGNode(), index);

        tree.expandPath(targetPath);
        tree.setSelectionPath(new TreePath(draggedItem.getPath()));

        return true;
    }
}
