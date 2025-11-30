package com.mulgish;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

import static com.mulgish.Utils.runOnFxThread;

public class FxMultiCreate {

    public static final FxMultiCreate INSTANCE = new FxMultiCreate();

    /**
     * Singleton
     */
    private FxMultiCreate() {
    }

    /**
     * Creates a new {@link Multi} that emits {@link ObservableValue} changes.
     * Note that a hot stream is created (Changes are emitted even if there are no subscribers).
     *
     * @param observableValue JavaFx {@link ObservableValue}
     * @param <T>             the type of item emitted by the produced Multi is wrapped in {@link Item}
     * @return the new {@link Multi}
     */
    public <T> Multi<Item<T>> observableValue(ObservableValue<T> observableValue) {
//        checkFxThread();

        final BroadcastProcessor<Item<T>> processor = BroadcastProcessor.create();

        processor.onNext(new Item<>(null, observableValue.getValue()));

        final ChangeListener<T> changeListener = (observableValue1, oldValue, newValue) -> {
            processor.onNext(new Item<>(oldValue, newValue));
        };

        observableValue.addListener(changeListener);

        return processor.onTermination().invoke(() -> {
            runOnFxThread(() -> observableValue.removeListener(changeListener));
        });
    }

    /**
     * Creates a new {@link Multi} that emits {@link ObservableList} changes.
     * Note that a hot stream is created (Changes are emitted even if there are no subscribers).
     *
     * @param observableList JavaFx {@link ObservableList}
     * @param <T>            the type of item emitted by the produced Multi is wrapped in {@link javafx.collections.ListChangeListener.Change}
     * @return the new {@link Multi}
     */
    public <T> Multi<ListChangeListener.Change<? extends T>> observableList(ObservableList<T> observableList) {
//        checkFxThread();

        final BroadcastProcessor<ListChangeListener.Change<? extends T>> processor = BroadcastProcessor.create();

        final ListChangeListener<T> changeListener = change -> {
            if (change.next()) {
                processor.onNext(change);
            }
        };

        observableList.addListener(changeListener);

        return processor.onTermination().invoke(() -> {
            runOnFxThread(() -> observableList.removeListener(changeListener));
        });
    }

    /**
     * Creates a new {@link Multi} that emits {@link javafx.collections.ObservableSet} changes.
     * Note that a hot stream is created (Changes are emitted even if there are no subscribers).
     *
     * @param observableSet JavaFx {@link javafx.collections.ObservableSet}
     * @param <T>           the type of item emitted by the produced Multi is wrapped in {@link javafx.collections.SetChangeListener.Change}
     * @return the new {@link Multi}
     */
    public <T> Multi<SetChangeListener.Change<? extends T>> observableSet(ObservableSet<T> observableSet) {
//        checkFxThread();

        final BroadcastProcessor<SetChangeListener.Change<? extends T>> processor = BroadcastProcessor.create();

        final SetChangeListener<T> changeListener = processor::onNext;

        observableSet.addListener(changeListener);

        return processor.onTermination().invoke(() -> {
            runOnFxThread(() -> observableSet.removeListener(changeListener));
        });
    }

    /**
     * Creates a new {@link Multi} that emits {@link javafx.collections.ObservableMap} changes.
     * Note that a hot stream is created (Changes are emitted even if there are no subscribers).
     *
     * @param observableMap JavaFx {@link javafx.collections.ObservableMap}
     * @param <T>           the type of item emitted by the produced Multi is wrapped in {@link javafx.collections.MapChangeListener.Change}
     * @return the new {@link Multi}
     */
    public <T, V> Multi<MapChangeListener.Change<? extends T, ? extends V>> observableMap(ObservableMap<T, V> observableMap) {
//        checkFxThread();

        final BroadcastProcessor<MapChangeListener.Change<? extends T, ? extends V>> processor = BroadcastProcessor.create();

        final MapChangeListener<T, V> changeListener = processor::onNext;

        observableMap.addListener(changeListener);

        return processor.onTermination().invoke(() -> {
            runOnFxThread(() -> observableMap.removeListener(changeListener));
        });
    }
}
