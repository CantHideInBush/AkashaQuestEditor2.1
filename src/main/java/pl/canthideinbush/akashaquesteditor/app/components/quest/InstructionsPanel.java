package pl.canthideinbush.akashaquesteditor.app.components.quest;

import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimationContainer;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.components.ResizableIcon;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static pl.canthideinbush.akashaquesteditor.app.components.Popups.tick;

public class InstructionsPanel extends JPanel {

    public InstructionsPanel() {
        initialize();
        initializeComponents();
    }

    Component[] resize = new Component[5];

    private void initializeComponents() {
        addCreateInstructionPanel();
    }

    private void addCreateInstructionPanel() {
        JPanel createInstructionPanel = new JPanel();
        resize[0] = createInstructionPanel;
        createInstructionPanel.setLayout(new GridBagLayout());
        createInstructionPanel.setMinimumSize(new Dimension(500, 125));
        createInstructionPanel.setPreferredSize(new Dimension(1100, 125));
        createInstructionPanel.setBorder(new LineBorder(Color.BLACK));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.8;
        constraints.weighty = 1;


        JTextField instructionField = new JTextField();
        Dimension preferred = new Dimension(0, 25);
        instructionField.setPreferredSize(preferred);
        createInstructionPanel.add(instructionField, constraints);

        JComboBox<String> categoriesBox = new JComboBox<>();
        categoriesBox.addItem("event");
        categoriesBox.addItem("condition");
        categoriesBox.addItem("objective");
        categoriesBox.setPreferredSize(preferred);

        constraints.gridx = 1;
        constraints.weightx = 0.1;
        createInstructionPanel.add(categoriesBox, constraints);

        ResizableIcon confirmButton = new ResizableIcon(Popups.tickStatic.getImage(), Popups.tick);
        confirmButton.setPreferredSize(new Dimension(20, 20));
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true));

        ResizeAnimationContainer cbContainer = Popups.createResizableComponent(confirmButton, new Dimension(45, 45), new Dimension(50, 50));
        constraints.gridx = 2;
        constraints.weightx = 0.1;
        constraints.gridheight = 3;
        createInstructionPanel.add(cbContainer, constraints);



        add(createInstructionPanel);
    }

    private void initialize() {
        setPreferredSize(new Dimension(9999, 9999));
        this.setLayout(new FlowLayout());
        integrateResize();
    }

    private void integrateResize() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (Component component : resize) {
                    if (component != null) {
                        component.setPreferredSize(new Dimension(getWidth() - 50, component.getPreferredSize().height));
                    }
                }
                updateUI();
            }
        });
    }



}
