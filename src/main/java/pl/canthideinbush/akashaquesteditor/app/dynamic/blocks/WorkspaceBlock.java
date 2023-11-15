package pl.canthideinbush.akashaquesteditor.app.dynamic.blocks;

import pl.canthideinbush.akashaquesteditor.app.components.QuestComponent;
import pl.canthideinbush.akashaquesteditor.quest.objects.QuestObject;

import javax.swing.*;
import java.util.Collection;

public abstract class WorkspaceBlock<T extends QuestObject> extends JPanel implements QuestComponent<T> {

    abstract Collection<Class<? extends WorkspaceBlock<?>>> getAllowedIns();
    abstract Collection<Class<? extends WorkspaceBlock<?>>> getAllowedOuts();

}
