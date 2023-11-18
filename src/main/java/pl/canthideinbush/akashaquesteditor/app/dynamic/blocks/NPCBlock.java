package pl.canthideinbush.akashaquesteditor.app.dynamic.blocks;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;

public class NPCBlock extends ConversationBlock {
    public NPCBlock(String name) {
        super(name);
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
}
