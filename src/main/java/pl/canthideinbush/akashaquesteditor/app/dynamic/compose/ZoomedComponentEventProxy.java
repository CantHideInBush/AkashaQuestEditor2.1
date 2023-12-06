package pl.canthideinbush.akashaquesteditor.app.dynamic.compose;

import pl.canthideinbush.akashaquesteditor.app.components.CenterAbleComponent;
import pl.canthideinbush.akashaquesteditor.app.components.Zoomable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class ZoomedComponentEventProxy extends JPanel {

    private final JLayeredPane intercepted;
    private final HashMap<CenterAbleComponent, List<Component>> registeredForDrag = new HashMap<>();

    public ZoomedComponentEventProxy(JLayeredPane intercepted) {
        if (!(intercepted instanceof Zoomable)) {
            throw new IllegalArgumentException("Component must implement Zoomable interface!");
        }
        this.intercepted = intercepted;
        initialize();
        initializeIntercepting();
    }

    private void initialize() {
        setSize(intercepted.getPreferredSize());
        setLocation(0, 0);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        intercepted.add(this);
        intercepted.setLayer(this, JLayeredPane.DRAG_LAYER + 1);
    }


    private void initializeIntercepting() {
        MouseAdapter adapter = new MouseAdapter() {

            private Point dragMouseOffset;
            Component dragged = null;


            private Component active;

            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    e.setSource(component);
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    e.setSource(component);
                    dragged = component;
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragged = null;
                dragMouseOffset = null;
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    e.setSource(component);
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    e.setSource(component);
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    e.setSource(component);
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    e.setSource(component);
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = e.getPoint();
                Component component = dragged != null ? dragged : getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    e.setSource(component);
                    if (dragged != null) {
                        CenterAbleComponent optional = getOptionalDragComponent(component);
                        int precision = 0;
                        if (e.isShiftDown()) precision += 15;
                        if (e.isAltDown()) precision += 15;
                        if (precision != 0 && optional != null) {
                            Container container = optional.getContainer();
                            Component parent = container.getParent();
                            Point converted = new Point((int) ((e.getLocationOnScreen().x - parent.getLocationOnScreen().x) / getZoom()), (int) ((e.getLocationOnScreen().y - parent.getLocationOnScreen().y) / getZoom()));

                            if (dragMouseOffset == null) {
                                dragMouseOffset = new Point(optional.getContainer().getX() - converted.x,  optional.getContainer().getY() - converted.y);
                            }

                            converted.x = converted.x + dragMouseOffset.x;
                            converted.y = converted.y + dragMouseOffset.y;

                            converted.x = converted.x - converted.x % precision;
                            converted.y = converted.y - converted.y % precision;

                            container.setLocation(converted);
                            parent.repaint();
                        }

                    }
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    if (active == null || !active.equals(component)) {
                        if (active != null) {
                            active.dispatchEvent(new MouseEvent(active, MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), e.getModifiersEx(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
                        }
                        active = component;
                        component.dispatchEvent(new MouseEvent(component, MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), e.getModifiersEx(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
                    }


                    e.setSource(component);
                    component.dispatchEvent(e);
                }
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        addMouseWheelListener(adapter);
    }

    private double getZoom() {
        return ((Zoomable) intercepted).getZoom();
    }

    private Component getZoomComponentAt(int x, int y) {



        return getComponent((int) (x / getZoom()), (int) (y / getZoom()));
    }

    private Component getComponent(int x, int y) {
        for (Component component : intercepted.getComponents()) {
            if (component.equals(this) || component.equals(intercepted)) continue;
            Point local = SwingUtilities.convertPoint(intercepted, x, y, component);
            if (!component.contains(local)) {
                continue;
            }

            Component child;
            while (!(child = component.getComponentAt(local)).equals(component)) {
                local = SwingUtilities.convertPoint(component, local, child);
                component = child;
            }

            return component;
        }
        return intercepted;
    }

    public void registerDrag(CenterAbleComponent component) {
        registeredForDrag.put(component, getAllChildren(component.getContainer()));
    }

    public static ArrayList<Component> getAllChildren(Container component) {
        ArrayList<Component> list = new ArrayList<>();
        for (Component child : component.getComponents()) {
            if (child instanceof Container) list.addAll(getAllChildren((Container) child));
            list.add(child);
        }
        return list;
    }

    public CenterAbleComponent getOptionalDragComponent(Component component) {
        for (Map.Entry<CenterAbleComponent, List<Component>> entry : registeredForDrag.entrySet()) {
            if (entry.getKey().equals(component)) return entry.getKey();
            else if (entry.getValue().contains(component)) {
                return entry.getKey();
            }
        }
        return null;
    }


}
