package com.test;

import com.mulgish.FxMulti;
import io.smallrye.mutiny.subscription.Cancellable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ObservableCollectionTest extends ApplicationTest{

    @Test
    public void observableMap() throws InterruptedException {
        final Map<String, String> map = new HashMap<>();
        final ObservableMap<String, String> observableMap = FXCollections.observableMap(map);

        final CountDownLatch countDownLatch = new CountDownLatch(3);

        final Cancellable cancellable = FxMulti.createFrom().observableMap(observableMap)
                .log()
                .subscribe().with((change) -> {
                    countDownLatch.countDown();
                });

        //Create, modify,  delete
        observableMap.put("a", "a");
        observableMap.put("a", "b");
        observableMap.remove("a");

        final boolean allItemsReceived = countDownLatch.await(2, TimeUnit.SECONDS);
        Assertions.assertTrue(allItemsReceived);
        cancellable.cancel();
    }

    @Test
    public void observableSet() throws InterruptedException {
        final Set<String> set = new HashSet<>();
        final ObservableSet<String> observableSet = FXCollections.observableSet(set);

        final CountDownLatch countDownLatch = new CountDownLatch(6);

        final Cancellable cancellable = FxMulti.createFrom().observableSet(observableSet)
                .log()
                .subscribe().with((change) -> {
                    countDownLatch.countDown();
                });

        observableSet.add("a");
        observableSet.add("b");
        observableSet.add("c");
        observableSet.clear();

        final boolean allItemsReceived = countDownLatch.await(2, TimeUnit.SECONDS);
        Assertions.assertTrue(allItemsReceived);
        cancellable.cancel();
    }

    @Test
    public void observableListAddAll() throws InterruptedException {
        final List<String> list = new LinkedList<>();
        final ObservableList<String> observableList = FXCollections.observableList(list);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Cancellable cancellable = FxMulti.createFrom().observableList(observableList)
                .log()
                .subscribe().with((change) -> {
                    Assertions.assertEquals(3, change.getAddedSize());
                    countDownLatch.countDown();
                });

        observableList.addAll("a", "b", "c");
        final boolean allItemsReceived = countDownLatch.await(2, TimeUnit.SECONDS);
        Assertions.assertTrue(allItemsReceived);
        cancellable.cancel();
    }


}
