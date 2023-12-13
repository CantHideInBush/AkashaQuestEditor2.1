package pl.canthideinbush.akashaquesteditor.app.components.quest;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;
import java.awt.*;

public class InstructionsTable extends JTable {

    private TableColumn eventsColumn;
    private TableColumn conditionsColumn;
    private TableColumn objectivesColumn;

    public InstructionsTable() {
        initialize();
        initializeComponents();
    }

    private void initializeComponents() {
        eventsColumn = new TableColumn();
        addColumn(eventsColumn);
        setValueAt("Eventy", 0, 0);

        conditionsColumn = new TableColumn();
        addColumn(conditionsColumn);

        objectivesColumn = new TableColumn();
        addColumn(objectivesColumn);
    }

    private void initialize() {
        setBorder(new LineBorder(Color.BLACK));
    }

}
