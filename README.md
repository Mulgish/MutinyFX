# MutinyFX

JavaFX bindings for [Mutiny](https://smallrye.io/smallrye-mutiny/)

### Binaries:

```xml
<dependency>
    <groupId>com.mulgish</groupId>
    <artifactId>mutinyfx</artifactId>
    <version>0.0.2</version>
</dependency>
```

### Usage:

```java
FxMulti.createFrom().observableValue(observableValue);
FxMulti.createFrom().observableList(observableList);
FxMulti.createFrom().observableSet(observableSet);
FxMulti.createFrom().observableMap(observableMap);
```

### Examples:

Observing list events and filtering out additions

```java
FxMulti.createFrom().observableList(observableList)
        .filter(ListChangeListener.Change::wasAdded)
        .subscribe().with((change)->{
        System.out.println("Item(s) added: "+change.getAddedSubList());
        });
```

Observing text field changes and observing user input every 200ms

```java
FxMulti.createFrom().observableValue(textField)
        .group().intoMultis().every(Duration.ofMillis(200))
        .onItem().transformToMulti(items->items.select().last()).merge()
        .subscribe().with((change)->{
        System.out.println("User entered: "+change.getNewValue());
        });
```
