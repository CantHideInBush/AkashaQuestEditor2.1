package pl.canthideinbush.akashaquesteditor.app.components.quest.items;

import org.bukkit.inventory.ItemFlag;
import pl.canthideinbush.akashaquesteditor.quest.objects.Item;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;

public class FlagsComponent extends JPanel {

    public final HashMap<ItemFlag, JCheckBox> flags = new HashMap<>();
    public FlagsComponent() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        initializeComponents(constraints);
        setBorder(new CompoundBorder(new EmptyBorder(1, 1, 1, 1), new LineBorder(Color.GRAY, 1, true)));
    }

    private void initializeComponents(GridBagConstraints constraints) {
        for (ItemFlag flag : ItemFlag.values()) {
            constraints.gridx = 0;
            JLabel flagName = new JLabel(flag.name());
            flagName.setPreferredSize(new Dimension(flagName.getPreferredSize().width, 25));
            flagName.setVerticalAlignment(SwingConstants.BOTTOM);
            flagName.setHorizontalAlignment(SwingConstants.CENTER);
            add(flagName, constraints);
            JCheckBox checkBox = new JCheckBox();
            checkBox.setPreferredSize(new Dimension(checkBox.getPreferredSize().width, 25));
            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
            checkBox.setVerticalAlignment(SwingConstants.TOP);
            constraints.gridx = 1;
            add(checkBox, constraints);
            flags.put(flag, checkBox);
            constraints.gridy++;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, (int) (12.5 * getComponentCount()));
    }
}
