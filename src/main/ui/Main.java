package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

// Used to run the TraveLog application
public class Main {

    // EFFECTS: runs the graphical user interface of the TraveLog application
    public static void main(String[] args) {
        try {
            new TraveLogAppGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        } catch (IOException e) {
            System.out.println("Unable to add image");
        }
    }
}
