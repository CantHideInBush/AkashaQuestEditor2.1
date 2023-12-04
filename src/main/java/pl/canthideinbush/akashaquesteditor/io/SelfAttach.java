package pl.canthideinbush.akashaquesteditor.io;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;

public interface SelfAttach extends AnnotationSerialized {

    void attach();
    default List<Class<? extends SelfAttach>> dependencies() {
        return Collections.emptyList();
    }
}
