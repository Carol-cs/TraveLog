///* Used the following sources as references to implement the structure of this class:
//   https://github.students.cs.ubc.ca/CPSC210/TellerApp
//   https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// */
//
//package ui;
//
//import model.JournalCollection;
//import model.TravelJournal;
//import model.exceptions.EmptyInputException;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Scanner;
//
//// Represent travel log application
//public class TraveLogApp {
//    private static final String JSON_STORE = "./data/journalCollection.json";
//    private static final String EMPTY_INPUT_WARNING = "INVALID INPUT. INPUTS CANNOT BE EMPTY! PLEASE TRY AGAIN: ";
//    private JournalCollection journalCollection;
//    private Scanner input;
//    private JsonWriter jsonWriter;
//    private JsonReader jsonReader;
//
//    // EFFECTS: runs the travel log application
//    public TraveLogApp() throws FileNotFoundException {
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);
//        runTraveLog();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: keeps the application running and processes user input
//    private void runTraveLog() {
//        boolean running = true;
//        String command = null;
//
//        initJournalCollection();
//
//        while (running) {
//            displayMenu();
//            command = input.nextLine();
//            command = command.toLowerCase();
//
//            if (command.equals("quit")) {
//                running = false;
//            } else {
//                processCommand(command);
//            }
//
//        }
//
//        System.out.println("\nHope you enjoy your next journey!");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: initializes a journal collection
//    private void initJournalCollection() {
//        journalCollection = new JournalCollection();
//        input = new Scanner(System.in);
//        input.useDelimiter("\n");
//    }
//
//    // EFFECTS: displays the main menu to user
//    private void displayMenu() {
//        System.out.println("\nWelcome to TraveLog! Please select an option below:");
//        System.out.println("\t   add -> add a new travel journal");
//        System.out.println("\t  view -> view all travel journals");
//        System.out.println("\tupdate -> update a travel journal");
//        System.out.println("\t  find -> find a travel journal");
//        System.out.println("\t   del -> delete a travel journal");
//        System.out.println("\t  save -> save all travel journals to file");
//        System.out.println("\t  load -> load saved travel journals from file");
//        System.out.println("\t  quit -> quit the application");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: processes user command in the main menu
//    private void processCommand(String command) {
//        if (command.equals("add")) {
//            doAddNewJournal();
//        } else if (command.equals("view")) {
//            doViewAllJournals();
//        } else if (command.equals("update")) {
//            doUpdateJournal();
//        } else if (command.equals("find")) {
//            doFindJournal();
//        } else if (command.equals("del")) {
//            doDeleteJournal();
//        } else if (command.equals("save")) {
//            doSaveJournalCollection();
//        } else if (command.equals("load")) {
//            doLoadJournalCollection();
//        } else {
//            System.out.println("Selection is invalid! Please try again:");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: creates a new travel journal and adds it to the journal collection
//    private void doAddNewJournal() {
//        TravelJournal travelJournal = new TravelJournal();
//
//        doAddNewJournalTitle(travelJournal);
//        doAddNewJournalDate(travelJournal);
//        doAddNewJournalLocation(travelJournal);
//        doAddNewJournalText(travelJournal);
//
//        journalCollection.addJournal(travelJournal);
//        System.out.println("New travel journal successfully added!");
//
//    }
//
//
//    // MODIFIES: this, travelJournal
//    // EFFECTS: adds a title for the newly added journal
//    private void doAddNewJournalTitle(TravelJournal travelJournal) {
//        System.out.println("Enter the journal title: ");
//        String title = input.nextLine();
//        try {
//            travelJournal.setTitle(tryNewTitle(title));
//        } catch (EmptyInputException e) {
//            System.err.println(EMPTY_INPUT_WARNING);
//            doAddNewJournalTitle(travelJournal);
//
//        }
//    }
//
//    // MODIFIES: this, travelJournal
//    // EFFECTS: adds a date for the newly added journal
//    private void doAddNewJournalDate(TravelJournal travelJournal) {
//        System.out.println("Enter the travel date: ");
//        String date = input.nextLine();
//        try {
//            travelJournal.setDate(date);
//        } catch (EmptyInputException e) {
//            System.err.println(EMPTY_INPUT_WARNING);
//            doAddNewJournalDate(travelJournal);
//        }
//    }
//
//    // MODIFIES: this, travelJournal
//    // EFFECTS: adds a location for the newly added journal
//    private void doAddNewJournalLocation(TravelJournal travelJournal) {
//        System.out.println("Enter the travel location: ");
//        String location = input.nextLine();
//        try {
//            travelJournal.setLocation(location);
//        } catch (EmptyInputException e) {
//            System.err.println(EMPTY_INPUT_WARNING);
//            doAddNewJournalLocation(travelJournal);
//        }
//    }
//
//    // MODIFIES: this, travelJournal
//    // EFFECTS: adds a text for the newly added journal
//    private void doAddNewJournalText(TravelJournal travelJournal) {
//        System.out.println("Enter the text (type \"done\" on a new line when finished): ");
//        String text = inputParagraph();
//        try {
//            travelJournal.setText(text);
//        } catch (EmptyInputException e) {
//            System.err.println(EMPTY_INPUT_WARNING);
//            doAddNewJournalText(travelJournal);
//        }
//    }
//
//
//    // EFFECTS: IF there are journals in the journal collection, displays the number of journals and all journals
//    //          OTHERWISE, reminded the user that no journals are available.
//    private void doViewAllJournals() {
//        if (journalCollection.isEmpty()) {
//            System.out.println("You haven't added any travel journal yet.");
//        } else {
//            System.out.println("-----------------------------------");
//            String word;
//            if (journalCollection.numOfJournals() > 1) {
//                word = " JOURNALS";
//            } else {
//                word = " JOURNAL";
//            }
//            System.out.println("TOTAL " + journalCollection.numOfJournals() + word + "\n");
//            System.out.println(journalCollection.viewAllJournals());
//            System.out.println("-----------------------------------");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: allows the user to update the title, text, location and date of a journal
//    private void doUpdateJournal() {
//        String updateCommand = null;
//
//        if (journalCollection.isEmpty()) {
//            System.out.println("You haven't added any travel journal yet.");
//        } else {
//            System.out.println("Enter the title of the journal that needs to be updated: ");
//            String title = input.nextLine();
//
//            title = tryFindTitle(title);
//
//            TravelJournal travelJournal = journalCollection.findJournalByTitle(title);
//
//            displayUpdateMenu(title);
//
//            updateCommand = input.nextLine();
//            updateCommand = updateCommand.toLowerCase();
//
//            processUpdateCommand(travelJournal, updateCommand);
//        }
//
//
//    }
//
//    // MODIFIES: this, travelJournal
//    // EFFECTS: allows the user to update the title, text, location and date of the journal when it is known which
//    //          journal needs to be updated.
//    private void doUpdateJournalKnownTitle(TravelJournal travelJournal) {
//        String updateCommand = null;
//
//        displayUpdateMenu(travelJournal.getTitle());
//
//        updateCommand = input.nextLine();
//        updateCommand = updateCommand.toLowerCase();
//
//        processUpdateCommand(travelJournal, updateCommand);
//    }
//
//    // EFFECTS: displays the menu for updating the journal to the user
//    private void displayUpdateMenu(String title) {
//        System.out.println("\nWhat change would you like to make to the journal titled \"" + title + "\"?");
//        System.out.println("\t   title -> update the title");
//        System.out.println("\t    date -> update the date");
//        System.out.println("\tlocation -> update the location");
//        System.out.println("\t    text -> update the text");
//    }
//
//    // MODIFIES: this, travelJournal
//    // EFFECTS: processes the user's command for updating a journal
//    private void processUpdateCommand(TravelJournal travelJournal, String updateCommand) {
//        if (updateCommand.equals("title")) {
//            doUpdateTitle(travelJournal);
//        } else if (updateCommand.equals("date")) {
//            doUpdateDate(travelJournal);
//        } else if (updateCommand.equals("location")) {
//            doUpdateLocation(travelJournal);
//        } else if (updateCommand.equals("text")) {
//            doUpdateText(travelJournal);
//        } else {
//            System.out.println("Selection is invalid! Please try again:");
//            doUpdateJournalKnownTitle(travelJournal);
//        }
//    }
//
//    // MODIFIES: this, travelJournal
//    // EFFECTS: updates the title of a journal
//    private void doUpdateTitle(TravelJournal travelJournal) {
//        System.out.println("Enter the new journal title: ");
//        String title = input.nextLine();
//
//        try {
//            travelJournal.setTitle(tryNewTitle(title));
//            System.out.println("The title has been updated successfully!");
//        } catch (EmptyInputException e) {
//            System.err.println(EMPTY_INPUT_WARNING);
//            doUpdateTitle(travelJournal);
//        }
//
//
//    }
//
//    // MODIFIES: this, travelJournal
//    // EFFECTS: updates the travel date of a journal
//    private void doUpdateDate(TravelJournal travelJournal) {
//        System.out.println("Enter the new travel date: ");
//        String date = input.nextLine();
//
//        try {
//            travelJournal.setDate(date);
//            System.out.println("The date has be updated successfully!");
//
//        } catch (EmptyInputException e) {
//            System.err.println(EMPTY_INPUT_WARNING);
//            doUpdateDate(travelJournal);
//        }
//
//    }
//
//    // MODIFIES: this, travelJournal
//    // EFFECTS: updates the travel location of a journal
//    private void doUpdateLocation(TravelJournal travelJournal) {
//        System.out.println("Enter the new travel location: ");
//        String location = input.nextLine();
//
//        try {
//            travelJournal.setLocation(location);
//            System.out.println("The location has be updated successfully!");
//        } catch (EmptyInputException e) {
//            System.err.println(EMPTY_INPUT_WARNING);
//            doUpdateLocation(travelJournal);
//        }
//
//    }
//
//    // MODIFIES: this, travelJournal
//    // EFFECTS: updates the text of a journal
//    private void doUpdateText(TravelJournal travelJournal) {
//        System.out.println("Enter the new text (type \"done\" on a new line when finished): ");
//        String text = inputParagraph();
//
//        try {
//            travelJournal.setText(text);
//            System.out.println("The text has be updated successfully!");
//        } catch (EmptyInputException e) {
//            System.err.println(EMPTY_INPUT_WARNING);
//            doUpdateText(travelJournal);
//        }
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: allows the user to find journals by title, date and location
//    private void doFindJournal() {
//        String findCommand = null;
//
//        if (journalCollection.isEmpty()) {
//            System.out.println("You haven't added any travel journal yet.");
//        } else {
//            System.out.println("How would you like to search your journal(s)? ");
//            displayFindMenu();
//            findCommand = input.nextLine();
//            findCommand = findCommand.toLowerCase();
//
//            processFindCommand(findCommand);
//
//        }
//
//
//    }
//
//    // EFFECTS: displays the menu for finding journal(s) to the user
//    private void displayFindMenu() {
//        System.out.println("Find journals by: ");
//        System.out.println("\tt -> by title");
//        System.out.println("\td -> by date");
//        System.out.println("\tl -> by location");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: processes the user's command for finding journal(s)
//    private void processFindCommand(String findCommand) {
//        if (findCommand.equals("t")) {
//            doFindByTitle();
//        } else if (findCommand.equals("d")) {
//            doFindByDate();
//        } else if (findCommand.equals("l")) {
//            doFindByLocation();
//        } else {
//            System.out.println("Selection is invalid! Please try again:");
//            doFindJournal();
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: allows the user to find a journal by title
//    private void doFindByTitle() {
//        System.out.println("Enter the title of the journal you want to find: ");
//        String title = input.nextLine();
//
//        if (journalCollection.findJournalByTitle(title) == null) {
//            System.out.println("No journal titled \"" + title + "\". Please try again:");
//            doFindByTitle();
//        } else {
//            System.out.println("-----------Search Result----------");
//            System.out.println(journalCollection.findJournalByTitle(title).toString());
//            System.out.println("-----------------------------------");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: allows the user to find journals by date
//    private void doFindByDate() {
//        System.out.println("Enter the date of the journal you want to find: ");
//        String date = input.nextLine();
//
//        if (journalCollection.findJournalsByDate(date).isEmpty()) {
//            System.out.println("No journal with the date \"" + date + "\". Please try again:");
//            doFindByDate();
//        } else {
//            System.out.println("-----------Search Results----------");
//            StringBuilder result = new StringBuilder();
//            for (TravelJournal tj: journalCollection.findJournalsByDate(date)) {
//                result.append(tj.toString()).append("\n");
//            }
//            System.out.println(result);
//            System.out.println("----------------------------------");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: allows the user to find journals by location
//    private void doFindByLocation() {
//        System.out.println("Enter the travel location recorded in the journal that you want to find: ");
//        String location = input.nextLine();
//
//        if (journalCollection.findJournalsByLocation(location).isEmpty()) {
//            System.out.println("No journal about the location \"" + location + "\". Please try again:");
//            doFindByLocation();
//        } else {
//            System.out.println("-----------Search Results----------");
//            StringBuilder result = new StringBuilder();
//            for (TravelJournal tj: journalCollection.findJournalsByLocation(location)) {
//                result.append(tj.toString()).append("\n");
//            }
//            System.out.println(result);
//            System.out.println("----------------------------------");
//        }
//    }
//
//
//    // MODIFIES: this
//    // EFFECTS: allows the user to delete a journal from the journal collection
//    private void doDeleteJournal() {
//        if (journalCollection.isEmpty()) {
//            System.out.println("You haven't added any travel journal yet.");
//        } else {
//            System.out.println("Enter the title of the journal that needs to be deleted: ");
//            String title = input.nextLine();
//            title = tryFindTitle(title);
//            journalCollection.deleteJournalByTitle(title);
//            System.out.println("Journal titled \"" + title + "\" was deleted successfully!");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: allows the user to input multiple lines (a paragraph) and returns that paragraph.
//    private String inputParagraph() {
//        StringBuilder para = new StringBuilder();
//        while (input.hasNext()) {
//            String line = input.nextLine();
//            if (line.equals("done")) {
//                break;
//            }
//            para.append(line).append("\n");
//        }
//        return para.toString();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: IF the newly added journal title or the updated title is not taken, then returns this title
//    //          OTHERWISE: asks for another title until getting one that is not taken and returns it
//    private String tryNewTitle(String title) {
//
//        while (! (journalCollection.findJournalByTitle(title) == null)) {
//            System.out.println("The title \"" + title + "\" is already taken. Please choose a different title: ");
//            title = input.nextLine();
//        }
//
//        return title;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: IF the input title is found, then returns this title
//    //          OTHERWISE: asks for another title until getting one that can be found and returns it
//    private String tryFindTitle(String title) {
//
//        while (journalCollection.findJournalByTitle(title) == null) {
//            System.out.println("Journal titled \"" + title + "\" is NOT found. Please enter the title of an"
//                    + " existing journal: ");
//            title = input.nextLine();
//        }
//
//        return title;
//    }
//
//    // EFFECTS: saves the journal collection to file
//    private void doSaveJournalCollection() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(journalCollection);
//            jsonWriter.close();
//            System.out.println("Saved all travel journals to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads journal collection from file
//    private void doLoadJournalCollection() {
//        try {
//            journalCollection = jsonReader.read();
//            System.out.println("Loaded saved travel journals from " + JSON_STORE);
//        } catch (IOException | EmptyInputException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }
//}
