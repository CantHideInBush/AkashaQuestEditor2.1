package pl.canthideinbush.akashaquesteditor.io;

import java.io.*;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassScan {


    private final ArrayList<Class<?>> foundClasses = new ArrayList<>();
    public ClassScan(String pkg) {
        getClasses(pkg);
    }

    public void getClasses(String pkg) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String javaPackage = pkg.replace(".", "/");
        try (InputStream inputStream = classLoader.getResourceAsStream(javaPackage)) {
            assert inputStream != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> lines = reader.lines().toList();
            lines.stream().parallel().filter(l -> l.matches("^[a-zA-Z0-9]*\\.class$")).forEach(
                    l -> {
                        try {
                            foundClasses.add(Class.forName(pkg + "." + l.replace(".class", "")));
                        } catch (
                                ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
            lines.stream().parallel().filter(l -> l.matches("^[a-zA-Z0-9]*$")).map(l -> pkg + "." + l).forEach(this::getClasses);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Class<?>> getClassesAnnotatedBy(Class<? extends Annotation> c) {
        return foundClasses.stream().parallel().filter(clazz -> clazz.isAnnotationPresent(c)).collect(Collectors.toList());
    }



}
