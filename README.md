# MutinyFX

![Maven Central](https://img.shields.io/maven-central/v/com.mulgish/mutinyfx?style=for-the-badge)

[Mutiny](https://smallrye.io/smallrye-mutiny/) bindings for JavaFX

### Binaries:

```xml
<dependency>
    <groupId>com.mulgish</groupId>
    <artifactId>mutinyfx</artifactId>
    <version>1.0.1</version>
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

Observing list additions

```java
FxMulti.createFrom().observableList(observableList)
    .filter(ListChangeListener.Change::wasAdded)
    .subscribe().with((change) -> {
      System.out.println("Item(s) added: " + change.getAddedSubList());
    });
```

Observing user input every 1sec

```java
FxMulti.createFrom().observableValue(textField)
    .group().intoMultis().every(Duration.ofMillis(1000))
    .onItem().transformToMulti(items -> items.select().last()).merge()
    .subscribe().with((change) -> {
      System.out.println("User entered: " + change.getNewValue());
    });
```

### Links:

[Home page](https://mulgish.github.io/MutinyFX/)

[Maven index](https://search.maven.org/artifact/com.mulgish/mutinyfx)
