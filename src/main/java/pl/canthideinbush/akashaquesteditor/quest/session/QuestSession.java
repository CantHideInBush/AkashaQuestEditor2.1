package pl.canthideinbush.akashaquesteditor.quest.session;

import org.jetbrains.annotations.NotNull;
import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.io.ISerialization;
import pl.canthideinbush.akashaquesteditor.io.SF;
import pl.canthideinbush.akashaquesteditor.io.SelfAttach;
import pl.canthideinbush.akashaquesteditor.quest.objects.Conversation;
import pl.canthideinbush.akashaquesteditor.quest.objects.Instruction;
import pl.canthideinbush.akashaquesteditor.quest.objects.JournalEntry;
import pl.canthideinbush.akashaquesteditor.quest.objects.PackageFile;

import java.util.*;
import java.util.stream.Collectors;

public class QuestSession implements SelfAttach {

    public ArrayList<EditorConversation> conversations = new ArrayList<>();

    @SF
    public EditorConversation activeConversation;

    @SF
    public Map<String, LinkedHashMap<String, String>> instructions = new HashMap<>();

    @SF
    public String recentExportPath = "/";

    @SF
    public String recentFilePath = "/";


    public QuestSession() {
        ISerialization.register(this);
        instructions.putIfAbsent("events", new LinkedHashMap<>());
        instructions.putIfAbsent("conditions", new LinkedHashMap<>());
        instructions.putIfAbsent("objectives", new LinkedHashMap<>());
    }

    public static @NotNull QuestSession deserialize(Map<String, Object> data) {
        QuestSession questSession = new QuestSession();
        questSession.deserializeFromMap(data);
        return questSession;
    }

    public void attach() {
        Application.instance.clean();
        Application.instance.createNewSession(this);
    }

    public PackageFile generatePackage() {
        PackageFile packageFile = new PackageFile("package");
        packageFile.addEvents(generateEvents());
        packageFile.addConditions(generateConditions());
        packageFile.addObjectives(generateObjectives());
        for (EditorConversation conversation : conversations) {
            packageFile.addNPC(conversation.npcId, conversation.getName());
        }

        return packageFile;
    }

    public ArrayList<Conversation> generateConversations() {
        ArrayList<Conversation> conversations = new ArrayList<>();
        for (EditorConversation eConversation : this.conversations) {
            conversations.add(eConversation.generateConversation());
        }

        return conversations;
    }

    private List<Instruction> generateEvents() {
        if (!instructions.containsKey("events")) return Collections.emptyList();
        return instructions.get("events").entrySet().stream().map(entry -> new Instruction(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }
    private List<Instruction> generateConditions() {
        if (!instructions.containsKey("conditions")) return Collections.emptyList();
        return instructions.get("conditions").entrySet().stream().map(entry -> new Instruction(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    private List<Instruction> generateObjectives() {
        if (!instructions.containsKey("objectives")) return Collections.emptyList();
        return instructions.get("objectives").entrySet().stream().map(entry -> new Instruction(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    private List<JournalEntry> generateJournalEntries() {

        return null;
    }

    public List<String> getConversationNames() {
        return conversations.stream().map(editorConversation -> editorConversation.getName().toLowerCase()).collect(Collectors.toList());
    }


    public void setActiveConversation(EditorConversation editorConversation) {
        if (activeConversation.equals(editorConversation)) return;
        activeConversation.deactivate();
        activeConversation = editorConversation;
        editorConversation.activate();
    }

    public void removeActiveConversation() {
        activeConversation.deactivate();
        conversations.remove(activeConversation);
        activeConversation.terminate();
        if (!conversations.isEmpty()) {
            activeConversation = conversations.get(0);
            activeConversation.activate();
        }
        else activeConversation = null;
    }
}
