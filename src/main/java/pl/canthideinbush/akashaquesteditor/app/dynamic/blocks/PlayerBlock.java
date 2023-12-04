package pl.canthideinbush.akashaquesteditor.app.dynamic.blocks;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class PlayerBlock extends ConversationBlock {
    public PlayerBlock(String name) {
        super(name);
    }

    public PlayerBlock(Map<String, Object> data) {
        super(data);
    }

    @Override
    protected void setLooks() {


    }

    @Override
    Collection<Class<? extends WorkspaceBlock<?>>> getAllowedIns() {
        return Collections.singleton(NPCBlock.class);
    }

    @Override
    Collection<Class<? extends WorkspaceBlock<?>>> getAllowedOuts() {
        return Collections.singleton(NPCBlock.class);
    }

    @Override
    protected Color defaultBorderColor() {
        return Color.decode("#3E8914");
    }

    @Override
    String who() {
        return "Gracz";
    }
}
