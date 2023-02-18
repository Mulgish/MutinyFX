module com.test {
    requires com.mulgish.mutinyfx;
    requires org.junit.platform.engine;
    requires org.junit.jupiter.engine;
    requires org.junit.jupiter.api;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires io.smallrye.mutiny;
    requires org.testfx.junit5;
    requires org.testfx;

    exports com.test;
    opens com.test;
}
