package pl.canthideinbush.akashaquesteditor.app.dynamic.animations;

import java.awt.*;

public class ResizeAnimation implements Animation {

    private final Component component;
    private final Dimension goal;
    private final double duration;
    private Dimension originalDimension;
    private int interval;
    private int xAdd;
    private int yAdd;
    private Dimension dimension;

    public ResizeAnimation(Component component, Dimension goal, int duration) {
        this.component = component;
        this.goal = goal;
        this.duration = duration;
        originalDimension = component.getPreferredSize();
        setFields();
    }

    private void setFields() {
        dimension = new Dimension(originalDimension);
        interval = 1;
        xAdd = Math.max(1, (int) (interval / duration * (goal.width - originalDimension.width)));
        yAdd = Math.max(1, (int) (interval / duration * (goal.height - originalDimension.height)));
    }

    @Override
    public int interval() {
        return interval;
    }

    @Override
    public void cancel() {
        dimension = new Dimension(originalDimension);
        component.setPreferredSize(originalDimension);
        thread.interrupt();
    }

    @Override
    public void complete() {
        component.setPreferredSize(goal);
    }

    @Override
    public boolean progressAnimation() {
        dimension.width += xAdd;
        dimension.height += yAdd;

        component.setPreferredSize(dimension);
        component.getParent().repaint();

        return true;
    }

    Thread thread;

    @Override
    public Thread getAnimationThread() {
        return thread;
    }

    @Override
    public void setAnimationThread(Thread thread) {
        this.thread = thread;
    }
}
