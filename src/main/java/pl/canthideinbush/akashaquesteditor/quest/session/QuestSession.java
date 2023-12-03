package pl.canthideinbush.akashaquesteditor.quest.session;

import org.jetbrains.annotations.NotNull;
import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.io.ISerialization;
import pl.canthideinbush.akashaquesteditor.io.SelfAttach;
import pl.canthideinbush.akashaquesteditor.quest.objects.Conversation;
import pl.canthideinbush.akashaquesteditor.quest.objects.Instruction;
import pl.canthideinbush.akashaquesteditor.quest.objects.JournalEntry;
import pl.canthideinbush.akashaquesteditor.quest.objects.PackageFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestSession implements SelfAttach {

    public ArrayList<EditorConversation> conversations = new ArrayList<>();
    public String activeConversation;

    public QuestSession() {
        ISerialization.register(this);
    }

    public static @NotNull QuestSession deserialize(Map<String, Object> data) {
        QuestSession questSession = new QuestSession();
        questSession.deserializeFromMap(data);
        return questSession;
    }

    public void attach() {
        Application.instance.createNewSession(this);
    }

    public PackageFile generatePackage() {
        PackageFile packageFile = new PackageFile("package");

        return null;
    }

    public ArrayList<Conversation> generateConversations() {
        ArrayList<Conversation> conversations = new ArrayList<>();
        return conversations;
    }

    private List<Instruction> generateEvents() {


        return null;
    }
    private List<Instruction> generateConditions() {


        return null;
    }

    private List<Instruction> generateObjectives() {


        return null;
    }

    private List<JournalEntry> generateJournalEntries() {

        return null;
    }




}
