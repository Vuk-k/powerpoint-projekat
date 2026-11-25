package raf.graffito.dsw.controller;

import lombok.Getter;
import raf.graffito.dsw.controller.slide.*;

import javax.swing.*;

public class ActionManager {
    @Getter
    private ExitAction exitAction = null;
    @Getter
    private AboutUsAction aboutUsAction = null;
    @Getter
    private AddNewProjectAction addNewProjectAction = null;
    @Getter
    private AddNewPresentationAction addNewPresentationAction = null;
    @Getter
    private AddNewSlideAction addNewSlideAction = null;
    @Getter
    private RemoveAction removeAction = null;
    @Getter
    private AddNewAction addNewAction = null;
    @Getter
    private MoveAction moveAction = null;
    @Getter
    private SelectAction selectAction = null;
    @Getter
    private ResizeAction resizeAction = null;
    @Getter
    private RotateAction rotateAction = null;
    @Getter
    private ZoomAction zoomAction = null;
    @Getter
    private ChangeColorAction changeColorAction = null;
    @Getter
    private RemoveElementAction removeElementAction = null;
    @Getter
    private CloneAction cloneAction = null;
    @Getter
    private LayerUpAction layerUpAction = null;
    @Getter
    private LayerDownAction layerDownAction = null;
    @Getter
    private SmallWIndowAction smallWIndowAction = null;
    @Getter
    private NormalWindowAction normalWindowAction = null;
    @Getter
    private FullScreenWindowAction fullScreenWindowAction = null;
    @Getter
    private RedoAction redoAction = null;
    @Getter
    private UndoAction undoAction = null;
    @Getter
    private SaveAction saveAction = null;
    @Getter
    private LoadAction loadAction = null;
    @Getter
    private SaveAsAction saveAsAction = null;
    @Getter
    private LoadTemplateAction loadTemplateAction = null;
    @Getter
    private SaveAsTemplateAction saveAsTemplateAction = null;


    public AddSelectedImage addSelectedImage(String icon) {
        return new AddSelectedImage(icon);
    }
    public ActionManager() {
        exitAction = new ExitAction();
        aboutUsAction = new AboutUsAction();
        addNewProjectAction = new AddNewProjectAction();
        addNewPresentationAction = new AddNewPresentationAction();
        addNewSlideAction = new AddNewSlideAction();
        removeAction = new RemoveAction();
        addNewAction = new AddNewAction();
        moveAction = new MoveAction();
        selectAction = new SelectAction();
        resizeAction = new ResizeAction();
        rotateAction = new RotateAction();
        zoomAction = new ZoomAction();
        changeColorAction = new ChangeColorAction();
        removeElementAction = new RemoveElementAction();
        cloneAction = new CloneAction();
        layerUpAction = new LayerUpAction();
        layerDownAction = new LayerDownAction();
        smallWIndowAction = new SmallWIndowAction();
        normalWindowAction = new NormalWindowAction();
        fullScreenWindowAction = new FullScreenWindowAction();
        redoAction = new RedoAction();
        undoAction = new UndoAction();
        saveAction = new SaveAction();
        loadAction = new LoadAction();
        saveAsAction = new SaveAsAction();
        saveAsTemplateAction = new SaveAsTemplateAction();
        loadTemplateAction = new LoadTemplateAction();
    }

}
