package pl.canthideinbush.akashaquesteditor.app.dynamic.compose;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.Zoomable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class DragZoomPanel extends JScrollPane {


    private final JLayeredPane component;
    public ZoomedComponentEventProxy zoomedComponentEventProxy;
    private double viewY;
    private double viewX;

    public DragZoomPanel(JLayeredPane component) {
        super(component);
        if (!(component instanceof Zoomable)) {
            throw new IllegalArgumentException("Component must implement Zoomable interface!");
        }
        this.component = component;
        initialize();
    }


    public void configureComponent() {

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                this.screenOrigin = e.getLocationOnScreen();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                screenOrigin = null;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double rotation = e.getWheelRotation();
                double currentZoom = ((Zoomable) component).getZoom();
                double oldZoom = currentZoom;
                Rectangle oldViewSize = getZoomedViewSize();
                if (rotation < 0) {
                    currentZoom += 0.25;
                }
                else currentZoom -= 0.25;
                currentZoom = Math.max(0.5, currentZoom);
                currentZoom = Math.min(1, currentZoom);
                ((Zoomable) component).setZoom(currentZoom);

                Rectangle currentViewSize = getZoomedViewSize();



                fixInBounds();
                getViewport().setViewPosition(new Point((int) viewX, (int) viewY));
                Application.instance.sessionContainer.composerInfoBar.updateXYDisplay();
                //TODO: Add view scaling
                component.repaint();
            }

            Point screenOrigin;

            @Override
            public void mouseDragged(MouseEvent e) {
                if (screenOrigin != null) {
                    double x = e.getLocationOnScreen().getX() - screenOrigin.getX();
                    double y = e.getLocationOnScreen().getY() - screenOrigin.getY();

                    viewX = viewX - x;
                    viewY = viewY - y;
                    fixInBounds();
                    getViewport().setViewPosition(new Point((int) viewX, (int) viewY));

                    screenOrigin = e.getLocationOnScreen();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
        };

        component.addMouseListener(adapter);
        component.addMouseMotionListener(adapter);
        component.addMouseWheelListener(adapter);

        zoomedComponentEventProxy = new ZoomedComponentEventProxy(component);
    }

    private Rectangle getZoomedViewSize() {
        Rectangle rect = new Rectangle();

        System.out.println(getComponentZoom());
        rect.width = (int) (getWidth() / getComponentZoom());
        rect.height = (int) (getHeight() / getComponentZoom());

        return rect;
    }

    public double fixInBoundsX(double x) {
        if (x < 0) {
            x = 0;
        }
        if (x > component.getWidth() - getWidth()) {
            x = component.getWidth() - getWidth();
        }

        return x;
    }

    public double fixInBoundsY(double y) {
        if (y < 0) {
            y = 0;
        }
        if (y > component.getHeight() - getHeight()) {
            y = component.getHeight() - getHeight();
        }

        return y;
    }

    public void fixInBounds() {
        viewX = fixInBoundsX(viewX);
        viewY = fixInBoundsY(viewY);
    }

    public double getComponentZoom() {
        return ((Zoomable) component).getZoom();
    }



    private void initialize() {
        setAutoscrolls(false);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    }


}
