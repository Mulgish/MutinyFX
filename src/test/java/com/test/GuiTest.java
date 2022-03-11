package com.test;

import com.mulgish.FxMulti;
import io.smallrye.mutiny.subscription.Cancellable;
import javafx.collections.ListChangeListener;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiTest extends ApplicationTest {

    public void start(final Stage stage) {
        TestApplication.start(stage);
    }

    /**
     * This is called after each test.
     */
    @AfterEach
    public void cleanup() {
        for (int i = 0; i < 5; i++) {
            clickOn(TestApplication.REMOVE_BUTTON, MouseButton.PRIMARY);
        }
    }

    /**
     * Given we have a Multi for a text field
     * When we write some text into the text field
     * Then we expect some items to arrive
     */
    @Test
    public void observableValue() {
        final AtomicInteger counter = new AtomicInteger();


        final Cancellable cancellable = FxMulti.createFrom().observableValue(TestApplication.TEXT_FIELD.textProperty())
                .group().intoMultis().every(Duration.ofMillis(200))
                .onItem().transformToMulti(items -> items.select().last()).merge()
                .log()
                .subscribe().with((change) -> {
                    counter.incrementAndGet();
                });

        clickOn(TestApplication.TEXT_FIELD, MouseButton.PRIMARY);
        write("Item");

        Assertions.assertTrue(counter.get() > 0);
        cancellable.cancel();
    }

    /**
     * Given we have a Multi which filters additions
     * When we add 5 items to the list and remove 1 item
     * Then we expect 5 item events to arrive
     */
    @Test
    public void observableListAdditions() {
        final AtomicInteger counter = new AtomicInteger();

        final Cancellable cancellable = FxMulti.createFrom().observableList(TestApplication.LIST_VIEW.getItems())
                .filter(ListChangeListener.Change::wasAdded)
                .log()
                .subscribe().with((change) -> {
                    counter.incrementAndGet();
                });

        for (int i = 0; i < 5; i++) {
            clickOn(TestApplication.TEXT_FIELD, MouseButton.PRIMARY);
            write("Item " + i);
            clickOn(TestApplication.ADD_BUTTON, MouseButton.PRIMARY);
        }
        clickOn(TestApplication.REMOVE_BUTTON, MouseButton.PRIMARY);

        Assertions.assertEquals(5, counter.get());
        cancellable.cancel();
    }

    /**
     * Given we have a Multi which filters removals
     * When we add 5 items to the list and remove last 2 items
     * Then we expect 2 item events to arrive
     */
    @Test
    public void observableListRemovals() {
        final AtomicInteger counter = new AtomicInteger();
        final Cancellable cancellable = FxMulti.createFrom().observableList(TestApplication.LIST_VIEW.getItems())
                .filter(ListChangeListener.Change::wasRemoved)
                .log()
                .subscribe().with((change) -> {
                    counter.incrementAndGet();
                });

        for (int i = 0; i < 5; i++) {
            clickOn(TestApplication.TEXT_FIELD, MouseButton.PRIMARY);
            write("Item " + i);
            clickOn(TestApplication.ADD_BUTTON, MouseButton.PRIMARY);
        }
        clickOn(TestApplication.REMOVE_BUTTON, MouseButton.PRIMARY);
        clickOn(TestApplication.REMOVE_BUTTON, MouseButton.PRIMARY);

        Assertions.assertEquals(2, counter.get());
        cancellable.cancel();
    }


}
