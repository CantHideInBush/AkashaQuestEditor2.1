package pl.canthideinbush.akashaquesteditor.quest.session;

import pl.canthideinbush.akashaquesteditor.io.Load;
import pl.canthideinbush.akashaquesteditor.quest.objects.Conversation;
import pl.canthideinbush.akashaquesteditor.quest.objects.Instruction;
import pl.canthideinbush.akashaquesteditor.quest.objects.JournalEntry;
import pl.canthideinbush.akashaquesteditor.quest.objects.PackageFile;

import java.util.ArrayList;
import java.util.List;

@Load
public class QuestSession {


    public QuestSession() {

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
