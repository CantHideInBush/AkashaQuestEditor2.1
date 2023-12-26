package pl.canthideinbush.akashaquesteditor.io;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.QuestMenuBar;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class IO {

    public static void save() {
        if (Application.instance.sessionContainer == null) {
            JOptionPane.showMessageDialog(Application.instance, "Żadne zadanie nie jest otwarte w programie", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        File recentFile = new File(Application.instance.sessionContainer.session.recentFilePath);
        if (recentFile.exists()) {
            fileChooser.setSelectedFile(recentFile);
        }
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(Application.instance);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
            }
            Application.instance.sessionContainer.session.recentFilePath = file.getAbsolutePath();
            ISerialization.serialize(file);
        }
    }
    public static void load() {

        if (!QuestMenuBar.shouldOverride()) {
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(Application.instance);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.exists()) {
                System.out.println("Wskazany plik nie istnieje");
                JOptionPane.showMessageDialog(Application.instance, "Wskazany plik nie istnieje", "Błąd odczytu", JOptionPane.ERROR_MESSAGE);
            }
            else {
                ISerialization.deserialize(file);
                Application.instance.sessionContainer.session.recentFilePath = file.getAbsolutePath();
            }
        }
    }

    public static void export() {
        if (Application.instance.sessionContainer == null) {
            JOptionPane.showMessageDialog(Application.instance, "Żadne zadanie nie jest otwarte w programie", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(Application.instance.sessionContainer.session.recentExportPath));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(Application.instance);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Application.instance.sessionContainer.session.recentExportPath = file.getAbsolutePath();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (
                        IOException e) {
                    throw new RuntimeException(e);
                }
            }
            ISerialization.exportToDir(file);
        }
    }




}
