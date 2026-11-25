package raf.graffito.dsw.controller;

import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.mts.GraffTreeSelectionListener;
import raf.graffito.dsw.model.mts.Workspace;
import raf.graffito.dsw.view.komponente.WorkspaceTreeComponent;

public class WorkspaceController {
    public Workspace workspace;
    public WorkspaceTreeComponent workspaceTreeComponent;

    public WorkspaceController() {
        workspace = (Workspace) ApplicationFramework.getInstance().getGraffObjectFactory().getComponent("Workspace", new Object[] { "Workspace" });
        workspaceTreeComponent = new WorkspaceTreeComponent();
    }
}
