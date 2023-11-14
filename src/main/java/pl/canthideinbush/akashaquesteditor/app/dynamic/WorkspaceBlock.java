package pl.canthideinbush.akashaquesteditor.app.dynamic;

import pl.canthideinbush.akashaquesteditor.app.components.QuestComponent;
import pl.canthideinbush.akashaquesteditor.quest.objects.QuestObject;

import javax.swing.*;
import java.awt.*;

public abstract class WorkspaceBlock<T extends QuestObject> extends JPanel implements QuestComponent<T> {

}
