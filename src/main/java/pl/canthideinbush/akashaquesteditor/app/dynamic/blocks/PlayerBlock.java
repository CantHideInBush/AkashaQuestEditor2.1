package pl.canthideinbush.akashaquesteditor.app.dynamic.blocks;

import java.util.Collection;
import java.util.Collections;

public class PlayerBlock extends ConversationBlock {
    public PlayerBlock(String name) {
        super(name);
    }

    @Override
    Collection<Class<? extends WorkspaceBlock<?>>> getAllowedIns() {
        return Collections.singleton(NPCBlock.class);
    }

    @Override
    Collection<Class<? extends WorkspaceBlock<?>>> getAllowedOuts() {
        return Collections.singleton(NPCBlock.class);
    }
}
