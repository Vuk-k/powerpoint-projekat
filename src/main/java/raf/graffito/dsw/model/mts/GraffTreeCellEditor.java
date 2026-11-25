package raf.graffito.dsw.model.mts;

import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.messages.ErrorMessage;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class GraffTreeCellEditor extends DefaultTreeCellEditor implements ActionListener {

    private Object clickedOn = null;
    private JTextField edit = null;
    public GraffTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
    }

    public Component getTreeCellEditorComponent(JTree tree, Object node, boolean sel, boolean extended, boolean leaf, int row) {
        clickedOn = node;
        edit = new JTextField(node.toString());
        edit.addActionListener(this);

        return edit;
    }


    public void actionPerformed(ActionEvent e) {

        if (!(clickedOn instanceof GraffTreeItem)) return;

        //System.out.println(e.getActionCommand());

        GraffTreeItem clicked = (GraffTreeItem) clickedOn;

        GraffNodeComposite p;

        if ((p = (GraffNodeComposite) clicked.getGNode().getParent()) != null) {
            for (int i = 0; i < p.getChildren().size(); ++i) {
                if (e.getActionCommand().equals(p.getChildren().get(i).getNaziv())) {
                    ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(new ErrorMessage("RENAME_NODE_NAME_ALREADY_EXISTS"));
                    return;
                }
            }
        }

        clicked.setName(e.getActionCommand());
    }

    public boolean isCellEditable(EventObject e) {
        if (e instanceof MouseEvent)
            if (((MouseEvent) e).getClickCount() == 3) {
                return true;
            }
        return false;
    }
}
