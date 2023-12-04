package pl.canthideinbush.akashaquesteditor.app.dynamic.blocks;

import org.jetbrains.annotations.NotNull;
import pl.canthideinbush.akashaquesteditor.io.AnnotationSerialized;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

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
}
