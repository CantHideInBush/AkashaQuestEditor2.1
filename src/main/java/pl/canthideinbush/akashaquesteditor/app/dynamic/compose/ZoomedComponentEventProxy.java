package pl.canthideinbush.akashaquesteditor.app.dynamic.compose;

import pl.canthideinbush.akashaquesteditor.app.dynamic.CenterAbleComponent;
import pl.canthideinbush.akashaquesteditor.app.dynamic.Zoomable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ZoomedComponentEventProxy extends JPanel {

    private final JLayeredPane intercepted;
    private final HashMap<CenterAbleComponent, Component[]> registeredForDrag = new HashMap<>();

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
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
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
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = e.getPoint();
                Component component = dragged != null ? dragged : getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    if (dragged != null) {
                        CenterAbleComponent optional = getOptionalDragComponent(component);
                        if (optional != null) {
                            Container container = optional.getContainer();
                            Component parent = container.getParent();
                            Point converted = new Point((int) ((e.getLocationOnScreen().x - parent.getLocationOnScreen().x) / getZoom()), (int) ((e.getLocationOnScreen().y - parent.getLocationOnScreen().y) / getZoom()));

                            if (dragMouseOffset == null) {
                                dragMouseOffset = new Point(optional.getContainer().getX() - converted.x,  optional.getContainer().getY() - converted.y);
                            }

                            converted.x = converted.x + dragMouseOffset.x;
                            converted.y = converted.y + dragMouseOffset.y;

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
                            active.dispatchEvent(new MouseEvent((Component) e.getSource(), MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), e.getModifiersEx(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
                        }
                        active = component;
                        component.dispatchEvent(new MouseEvent((Component) e.getSource(), MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), e.getModifiersEx(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
                    }



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
            if (component.contains(local)) {
                return component.getComponentAt(local);
            }
        }
        return intercepted;
    }

    public void registerDrag(CenterAbleComponent component) {
        registeredForDrag.put(component, component.getContainer().getComponents());
    }

    public CenterAbleComponent getOptionalDragComponent(Component component) {
        for (Map.Entry<CenterAbleComponent, Component[]> entry : registeredForDrag.entrySet()) {
            if (entry.getKey().equals(component)) return entry.getKey();
            else if (Arrays.asList(entry.getValue()).contains(component)) {
                return entry.getKey();
            }
        }
        return null;
    }


}
