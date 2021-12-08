/*
 * Copyright 2017 Jacob Hassel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mulgish;

import javafx.beans.value.ObservableValue;

import java.util.Objects;

/**
 * Wrapper for an item in {@link ObservableValue} with some helper methods
 *
 * @param <T> type used in {@link ObservableValue}
 */
public class Item<T> {
    private final T oldValue;
    private final T newValue;

    public Item(final T oldValue, final T newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public T getOldValue() {
        return oldValue;
    }

    public T getNewValue() {
        return newValue;
    }

    public boolean changed() {
        return !Objects.equals(oldValue, newValue);
    }

    @Override
    public String toString() {
        return "Item{" +
                "oldValue=" + oldValue +
                ", newValue=" + newValue +
                '}';
    }
}
