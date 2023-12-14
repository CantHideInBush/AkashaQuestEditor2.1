package pl.canthideinbush.akashaquesteditor.app.components.quest;

import pl.canthideinbush.akashaquesteditor.quest.objects.Instruction;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Map;

public class InstructionsTable extends JPanel {

    private final Map<String, String> instructions;

    public InstructionsTable(Map<String, String> instructions) {
        initialize();
        this.instructions = instructions;
    }

    private void initialize() {
        setBackground(Color.LIGHT_GRAY);
    }

}
