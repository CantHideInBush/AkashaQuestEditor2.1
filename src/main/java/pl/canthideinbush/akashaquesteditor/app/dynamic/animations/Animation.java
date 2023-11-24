package pl.canthideinbush.akashaquesteditor.app.dynamic.animations;

import javax.swing.*;

public interface Animation {

    int interval();

    void cancel();

    void complete();

    boolean progressAnimation();



}
