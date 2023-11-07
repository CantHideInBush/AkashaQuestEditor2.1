package pl.canthideinbush.akashaquesteditor.io;

public class Serialization {

    public Serialization() {
        ClassScan scan = new ClassScan("pl.canthideinbush.akashaquesteditor");
        scan.getClassesAnnotatedBy(Load.class).forEach(System.out::println);
    }


}
