package pl.canthideinbush.akashaquesteditor.app.components.quest;

import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimationContainer;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.components.ResizableIcon;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class InstructionsPanel extends JPanel {

    private ResizeAnimationContainer cbResizeContainer;

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
        constraints.insets = new Insets(0, 5, 0, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.8;
        constraints.weighty = 1;


        JTextField instructionField = new JTextField();
        instructionField.setHorizontalAlignment(SwingConstants.CENTER);
        instructionField.setFont(instructionField.getFont().deriveFont(20f));
        Dimension preferred = new Dimension(0, 40);
        instructionField.setPreferredSize(preferred);
        createInstructionPanel.add(instructionField, constraints);

        JComboBox<String> categoriesBox = new JComboBox<>();
        ((JLabel) categoriesBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
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

        cbResizeContainer = Popups.createResizableComponent(confirmButton, new Dimension(45, 45), new Dimension(50, 50), 150);

        constraints.gridx = 2;
        constraints.weightx = 0;
        constraints.gridheight = 1;
        createInstructionPanel.add(cbResizeContainer, constraints);

        add(createInstructionPanel);


    }

    public void fixAnimation() {
        cbResizeContainer.centerComponent();
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
