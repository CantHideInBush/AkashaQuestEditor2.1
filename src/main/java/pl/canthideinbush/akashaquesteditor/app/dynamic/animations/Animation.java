package pl.canthideinbush.akashaquesteditor.app.dynamic.animations;

public interface Animation {

    int interval();

    void cancel();

    void complete();

    boolean isComplete();

    void progressAnimation();

    Thread getAnimationThread();

    void setAnimationThread(Thread thread);

    default void start() {
        if (getAnimationThread() != null) {
            getAnimationThread().interrupt();
            cancel();
        }
        Thread thread = new Thread(() -> {
            while (true) {
                if (isComplete()) {
                    complete();
                    return;
                }
                else progressAnimation();
                try {
                    Thread.sleep(interval());
                } catch (
                        InterruptedException ignored) {

                }
            }
        });
        setAnimationThread(thread);
        thread.start();
    }

}
