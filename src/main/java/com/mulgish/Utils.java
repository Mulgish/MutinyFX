package com.mulgish;

import javafx.application.Platform;

public class Utils {

    protected static void checkFxThread() {
        if (!Platform.isFxApplicationThread()) {
            throw new IllegalStateException("Only FX thread can emmit items!");
        }
    }

    protected static void runOnFxThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }

}
