package raf.graffito.dsw.view.komponente;

import com.sun.tools.javac.Main;
import raf.graffito.dsw.controller.slide.MoveAction;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.messages.Message;
import raf.graffito.dsw.model.mts.Slide;
import raf.graffito.dsw.model.slajd.*;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;

public class SlajdMaker  extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    Slide s;
    java.util.List<SlajdState> redoStack = new Stack<>();
    java.util.List<SlajdState> undoStack = new Stack<>();
    ActionMode am;
    SlajdElement selItem = null;
    private Point lastMousePos = null;
    private AffineTransform currentTransform = new AffineTransform();
    private final int HEIGHT = 2000;
    private final int WIDTH = 2000;
    private static final double MAX_ZOOM = 1.0;
    private static final double MIN_ZOOM = 0.1;
    private static final double ZOOM_INCREMENT = 0.05;
    private double scaleFactor = 1.0;
    int l = 0;
    boolean loaded = false;
    private Cursor c;
    public SlajdMaker(Slide s) {
        this.s = s;
        am = ActionMode.NONE;
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        requestFocus();
        c = new Cursor(Cursor.DEFAULT_CURSOR);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new DefaultFocusManager());
        updateScale();

        //s.getElementiSlajda().add(new Pravougaonik(100, 100, 50, 80, Color.BLUE));
        //s.getElementiSlajda().add(new Pravougaonik(250, 150, 100, 50, Color.BLUE));
    }

    public void changeColorOfSelectedElement() {
        if (selItem == null) return;
        Color c = JColorChooser.showDialog(MainFrame.getInstance(),"Izaberi Boju", selItem.getColor());

        undoStack.add(new SlajdState(selItem, ActionMode.CHANGE_COLOR));
        selItem.setColor(c);
        undoLasDone = false;
        update();
    }
    public void updateCursor() {
        switch (am) {
            case SELECT:
                c = new Cursor(Cursor.CROSSHAIR_CURSOR);
                break;
            case MOVE:
                c = new Cursor(Cursor.MOVE_CURSOR);
                break;
            case RESIZE:
                c = new Cursor(Cursor.SE_RESIZE_CURSOR);
                break;
            default: c = Cursor.getDefaultCursor();
        }

        update();
    }
    boolean undoLasDone = false;
    public void redo() {
        var stack = redoStack;
        if (stack.size() == 0) return;
        SlajdElement changedElemenet = stack.getLast().getChangedElement();
        ActionMode oldMode = stack.getLast().getChange();

        undoStack.add(new SlajdState(changedElemenet, oldMode));


        switch (oldMode) {
            case RESIZE:
            case ROTATE:
            case MOVE:
                while (stack.size() > 0 && changedElemenet == stack.getLast().getChangedElement() && oldMode == stack.getLast().getChange()) {
                    stack.removeLast().revertChange();
                }
                break;
            case CHANGE_COLOR:
                stack.removeLast().revertChange();
                break;
            case ADD_ELEMENT:
                stack.removeLast();
                s.getElementiSlajda().remove(changedElemenet);
                break;
            case REMOVE_ELEMENT:
                stack.removeLast();
                s.getElementiSlajda().add(changedElemenet);
                s.sortElements();
                break;
        }

        undoLasDone = true;
        update();
    }
    public void undo() {
        var stack = undoStack;
        if (!undoLasDone || stack.size() == 0) return;

        SlajdElement changedElemenet = stack.getLast().getChangedElement();
        ActionMode oldMode = stack.getLast().getChange();

        redoStack.add(new SlajdState(changedElemenet, oldMode));

        switch (oldMode) {
            case RESIZE:
            case ROTATE:
            case MOVE:
                while (stack.size() > 0 && changedElemenet == stack.getLast().getChangedElement() && oldMode == stack.getLast().getChange()) {
                    stack.removeLast().revertChange();
                }
                break;
            case CHANGE_COLOR:
                stack.removeLast().revertChange();
                break;
            case REMOVE_ELEMENT:
                stack.removeLast();
                s.getElementiSlajda().remove(changedElemenet);
                break;
            case ADD_ELEMENT:
                stack.removeLast();
                s.getElementiSlajda().add(changedElemenet);
                s.sortElements();
                break;
        }

        update();
    }
    public boolean daLiImaMesta() {
        int usedPoints = 0;
        var elements = s.getElementiSlajda();
        int n = elements.size();

        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0;j < WIDTH; ++j) {
                for (int k = 0; k < n; ++k) {
                    var e = elements.get(k);
                    if (e.getX() < j && j < e.getX() + e.getWidth() && e.getY() < i && i < e.getY() + e.getHeight()) {
                        usedPoints++;
                        break;
                    }
                }
            }
        }

        double zauzetost = (double)usedPoints / (double)(HEIGHT * WIDTH);
        System.out.println(usedPoints + ", " + zauzetost);
        return zauzetost < 0.8;
    }
    private Point transformPoint(Point p) {
        try {
            AffineTransform inverseTransform = currentTransform.createInverse();
            Point2D.Double src = new Point2D.Double(p.x, p.y);
            Point2D.Double dest = new Point2D.Double();
            inverseTransform.transform(src, dest);
            return new Point((int) dest.x, (int) dest.y);
        } catch (Exception e) {
            return p;
        }
    }
    public void setActiveAction(ActionMode am) {
        if (this.am == am) {
            this.am = ActionMode.NONE;
            updateCursor();
            return;
        }
        this.am = am;
        updateCursor();
        update();
    }

    public void removeSelectedElement() {
        if (selItem == null) return;
        redoStack.add(new SlajdState(selItem, ActionMode.REMOVE_ELEMENT));
        s.getElementiSlajda().remove(selItem);
        undoLasDone = false;
        update();
    }
    public void setSmallScale() {
        scaleFactor = 0.1;
        setDimensionsToScale();
        if (loaded) update();
    }
    public void setNormalScale() {
        scaleFactor = 0.3;
        setDimensionsToScale();
        if (loaded) update();
    }
    public void setLargeScale() {
        scaleFactor = 0.5;
        setDimensionsToScale();
        if (loaded) update();
    }
    public void updateScale() {
        switch (MainFrame.getInstance().getWindowState()) {
            case SMALL: setSmallScale(); break;
            case NORMAL: setNormalScale(); break;
            case LARGE: setLargeScale(); break;
        }
    }
    public void addNewElement(SlajdElement sElement) {
        if (!loaded) {
            currentTransform = new AffineTransform();
            currentTransform.scale(scaleFactor, scaleFactor);
            loaded = true;
        }
        if (!daLiImaMesta()) {
            var mg = ApplicationFramework.getInstance().getMessageGenerator();
            mg.notifySubscriers(mg.generateMessage("ERROR_NOT_ENOUGH_SPACE_<_80_%", "error"));
            return;
        }
        redoStack.add(new SlajdState(sElement, ActionMode.ADD_ELEMENT));
        s.getElementiSlajda().add(sElement);
        s.sortElements();
        undoLasDone = false;
        update();
    }
    public void moveSelectedElementToLowerLayer() {
        if (selItem == null) return;

        selItem.layerDown();
        s.sortElements();
        undoLasDone = false;
        update();
    }
    public void moveSelectedElementToHigherLayer() {
        if (selItem == null) return;

        selItem.layerUp();
        s.sortElements();
        undoLasDone = false;
        update();
    }
    public void copySelectedElement() {
        if (selItem == null) return;

        SlajdElement se = selItem.clone();
        if (se == null) return;

        selItem.setSelected(false);
        selItem = se;
        se.move(20, 20);
        se.setSelected(true);

        addNewElement(se);
        update();
    }

    public void update() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!loaded) {
                    currentTransform = new AffineTransform();
                    currentTransform.scale(scaleFactor, scaleFactor);
                    loaded = true;
                }
                revalidate();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setTransform(currentTransform);
        for (int i = 0; i < s.getElementiSlajda().size(); ++i) {
            s.getElementiSlajda().get(i).draw(g2d);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (am == ActionMode.SELECT) {
            var lastSelected = selItem;
            if (selItem != null) {
                selItem.setSelected(false);
                selItem = null;
            }

            for (int i = s.getElementiSlajda().size() - 1; i >= 0; --i) {
                SlajdElement item = s.getElementiSlajda().get(i);
                //if (item == lastSelected) continue;

                Point transformedPoint = transformPoint(e.getPoint());
                //System.out.println(item.getX() + " <= " + transformedPoint.x + " && " + transformedPoint.x + " <= " + item.getX() + " + " +
                        //item.getWidth() + " && " + item.getY() + " <= " + transformedPoint.y + " && " + transformedPoint.y + " <= " + item.getY() + " + " + item.getHeight());
                if (item.getX() <= transformedPoint.x &&
                        transformedPoint.x <= item.getX() + item.getWidth() &&
                        item.getY() <= transformedPoint.y && transformedPoint.y <= item.getY() + item.getHeight()) {
                    selItem = item;
                    selItem.setSelected(true);
                }
            }
        }
        update();
    }

    @Override
    public void mousePressed(MouseEvent e) {

        lastMousePos = transformPoint(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(c);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selItem == null || lastMousePos == null) return;


        Point currPos = transformPoint(e.getPoint());
        int dx = currPos.x - lastMousePos.x;
        int dy = currPos.y - lastMousePos.y;

        switch (am) {
            case MOVE:
                redoStack.add(new SlajdState(selItem, ActionMode.MOVE));
                selItem.move(dx, dy);
                undoLasDone = false;
                break;
            case RESIZE:
                redoStack.add(new SlajdState(selItem, ActionMode.RESIZE));
                selItem.setDimensions(dx, dy);
                undoLasDone = false;
        }

        lastMousePos = currPos;
        update();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (am != ActionMode.ZOOM && am != ActionMode.ROTATE) return;

        int rotation = e.getWheelRotation();

        if (am == ActionMode.ROTATE) {
            if (selItem == null) return;

            selItem.rotate(rotation);
        } else {
            if (rotation < 0 && scaleFactor < MAX_ZOOM) {
                scaleFactor += ZOOM_INCREMENT;
                scaleFactor = Math.min(scaleFactor, MAX_ZOOM);
            } else if (rotation > 0 && scaleFactor > MIN_ZOOM) {
                scaleFactor -= ZOOM_INCREMENT;
                scaleFactor = Math.max(scaleFactor, MIN_ZOOM);
            }



            //System.out.println("ZOOM SET TO: " + scaleFactor);
            currentTransform = new AffineTransform();
            currentTransform.scale(scaleFactor, scaleFactor);

            setDimensionsToScale();

        }
        //System.out.println(rotation);
        update();
    }

    public void setDimensions(Dimension d) {
        setSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
    }

    public void  setDimensionsToScale() {
        setDimensions(new Dimension((int)(WIDTH * scaleFactor), (int)(HEIGHT * scaleFactor)));
    }
}
