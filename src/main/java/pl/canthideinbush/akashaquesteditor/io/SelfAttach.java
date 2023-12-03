package pl.canthideinbush.akashaquesteditor.io;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;

public interface SelfAttach extends ConfigurationSerializable {

    void attach();

    default @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        Class<?> c = getClass();
        for (Field field : c.getDeclaredFields()) {
            if (field.isAnnotationPresent(SF.class)) {
                field.setAccessible(true);
                try {
                    data.put(field.getName(), field.get(this));
                } catch (
                        IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return data;
    }

    default void deserializeFromMap(Map<String, Object> data) {
        Class<?> c = getClass();
        for (Field field : c.getDeclaredFields()) {
            if (field.isAnnotationPresent(SF.class)) {
                field.setAccessible(true);
                try {
                    if (data.containsKey(field.getName())) {
                        field.set(this, data.get(field.getName()));
                    }
                } catch (
                        IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    default List<Class<? extends SelfAttach>> dependencies() {
        return Collections.emptyList();
    }
}
