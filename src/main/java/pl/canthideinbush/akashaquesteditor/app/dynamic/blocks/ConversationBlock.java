package pl.canthideinbush.akashaquesteditor.app.dynamic.blocks;

import org.bukkit.configuration.ConfigurationSection;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.WorkspaceBlock;
import pl.canthideinbush.akashaquesteditor.quest.objects.ConversationOption;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

public abstract class ConversationBlock extends WorkspaceBlock<ConversationOption> {

    public ConversationBlock(String name) {
        setName(name);
        initialize();
    }

    private void initialize() {
        setSize(new Dimension(250, 150));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createDashedBorder(Color.BLACK, 2f, 2f, 1.5f, false),
                getName()
        , 0, 0, getFont().deriveFont(15f)));
        setOpaque(true);
    }

    @Override
    public ConversationOption create() {
        return null;
    }

    @Override
    public void load(ConfigurationSection section) {

    }

    @Override
    public void save(ConfigurationSection section) {

    }

    public void centerIn(Container component, Point point) {
        setLocation(point.x - getWidth() / 2, point.y - getHeight() / 2);
        component.add(this);
    }


}
