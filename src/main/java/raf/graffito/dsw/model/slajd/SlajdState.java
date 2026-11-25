package raf.graffito.dsw.model.slajd;

import lombok.Getter;
import raf.graffito.dsw.controller.ActionManager;
import raf.graffito.dsw.model.mts.Slide;
import raf.graffito.dsw.view.komponente.SlajdMaker;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SlajdState {
    private SlajdElement changedElement = null;
    private SlajdElement oldState = null;
    private ActionMode change;
    public  SlajdState(SlajdElement se, ActionMode am) {
        this.changedElement = se;

        oldState = se.clone();
        change = am;
    }

    public SlajdElement revertChange() {
        changedElement.setX(oldState.getX());
        changedElement.setY(oldState.getY());
        changedElement.setColor(oldState.getColor());
        changedElement.setLayer(oldState.getLayer());
        changedElement.setHeight(oldState.getHeight());
        changedElement.setWidth(oldState.getWidth());
        changedElement.setRotationAngle(oldState.getRotationAngle());

        return changedElement;
    }
}
