package pl.canthideinbush.akashaquesteditor.app.dynamic.blocks;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.io.AnnotationSerialized;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NPCBlock extends ConversationBlock implements AnnotationSerialized {
    public NPCBlock(String name) {
        super(name);
    }

    public NPCBlock(Map<String, Object> map) {
        super(map);
    }


    @Override
    Collection<Class<? extends WorkspaceBlock<?>>> getAllowedIns() {
        return Collections.singleton(PlayerBlock.class);
    }

    @Override
    Collection<Class<? extends WorkspaceBlock<?>>> getAllowedOuts() {
        return Collections.singleton(PlayerBlock.class);
    }
    

    @Override
    protected void setLooks() {

    }

    @Override
    String who() {
        return "NPC";
    }

    @Override
    protected Color defaultBorderColor() {
        return Color.decode("#E54B4B");
    }

    @Override
    public JPopupMenu getMenu() {
        JPopupMenu menu = super.getMenu();

        JCheckBoxMenuItem isStartOptionItem = new JCheckBoxMenuItem("Opcja startowa");
        List<String> startOptions = Application.instance.sessionContainer.session.activeConversation.startOptions;
        isStartOptionItem.setState(startOptions.contains(uuid.toString()));
        isStartOptionItem.addActionListener(e -> {
            if (isStartOptionItem.getState()) {
                startOptions.add(uuid.toString());
            }
            else startOptions.remove(uuid.toString());
        });

        menu.add(isStartOptionItem);

        return menu;
    }
}
