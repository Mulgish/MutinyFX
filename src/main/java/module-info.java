module com.mulgish.mutinyfx {
    requires javafx.base;
    requires javafx.graphics;
    requires io.smallrye.mutiny;

    opens com.mulgish;

    exports com.mulgish;
}
