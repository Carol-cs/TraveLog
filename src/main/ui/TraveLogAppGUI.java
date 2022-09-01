package ui;

import model.Event;
import model.EventLog;
import model.JournalCollection;
import model.TravelJournal;
import model.exceptions.EmptyInputException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Represents the main graphical user interface of the application, including the main menu panel and the browse panel.
public class TraveLogAppGUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/journalCollection.json";

    public static final Color BLUE = new Color(67, 135, 230);

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JFrame frame;

    private JPanel mainMenuPanel;
    private JPanel browsePanel;

    private JournalCollection journalCollection;

    // two panels on the browse panel
    private JPanel buttonPanel;
    private JPanel searchPanel;

    private JournalWindow journalWindow;

    private JList<TravelJournal> journalList;
    private DefaultListModel<TravelJournal> journalListModel;

    private JPopupMenu popupMenu;
    private JMenuItem edit;
    private JMenuItem delete;

    private JSplitPane splitPane;
    private JTextArea textAreaNotEditable;

    private JLabel numOfJournal;
    private JComboBox<String> comboBox;
    private JTextField searchField;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: sets up the main window (frame)
    public TraveLogAppGUI() throws IOException {
        init();

        frame = new JFrame("TraveLog");
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.setPreferredSize((new Dimension(WIDTH, HEIGHT)));

        Image icon = Toolkit.getDefaultToolkit().getImage("data/images/icon.png");
        frame.setIconImage(icon);

        setUpMainMenuPanel();
        changePanelTo(mainMenuPanel);

        setUpPopupMenu();

        doLoadJournalCollection();
        doSaveJournalCollection();
    }


    // CITATION: this method is modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: creates a load file prompt window. Loads the journal collection from the file if the user clicks "Yes".
    private void doLoadJournalCollection() {
        Icon loadPromptIcon = new ImageIcon("data/images/load.png");
        int option = JOptionPane.showConfirmDialog(null, "Do you want to load your last saved "
                        + "travel journals?", "Load File Prompt",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, loadPromptIcon);

        if (option == JOptionPane.YES_OPTION) {
            try {
                journalCollection = jsonReader.read();
                updateJournalListModel(journalCollection.getJournalCollection());
            } catch (IOException | EmptyInputException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // CITATION: this method is modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: creates a save file prompt window. Saves the journal collection to the file if the user clicks "Yes".
    private void doSaveJournalCollection() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                Icon savePromptIcon = new ImageIcon("data/images/save.png");
                int option = JOptionPane.showConfirmDialog(null, "Do you want to save your "
                                + "travel journals before closing?", "Save File Prompt",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, savePromptIcon);
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter.open();
                        jsonWriter.write(journalCollection);
                        jsonWriter.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Unable to write to file: " + JSON_STORE);
                    }

                    printLog(EventLog.getInstance());
                    System.exit(0);
                } else if (option == JOptionPane.NO_OPTION) {
                    printLog(EventLog.getInstance());
                    System.exit(0);
                }

            }
        });
    }

    // CITATION: This method is modeled from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // EFFECTS: prints out the event log in the console
    private void printLog(EventLog el) {
        for (Event next: el) {
            System.out.println(next.toString() + "\n");
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes the json data, journal collection, label for displaying the number of journals, journal
    //          list and its model, split pane for displaying journals, and text area for displaying journal text.
    private void init() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        journalCollection = new JournalCollection();

        numOfJournal = new JLabel();
        numOfJournal.setFont(new Font("SansSerif", Font.PLAIN, 15));
        numOfJournal.setForeground(BLUE);

        journalList = new JList<>();
        journalListModel = new DefaultListModel<>();

        splitPane = new JSplitPane();
        textAreaNotEditable = new JTextArea();
    }

    // CITATION: Learned about popup menu on a JList from https://www.rgagnon.com/javadetails/java-0393.html
    // MODIFIES: this
    // EFFECTS: sets up the popup menu for the journal list so that the user can choose to edit or delete a specific
    //          journal by right-clicking on it
    private void setUpPopupMenu() {
        popupMenu = new JPopupMenu();
        edit = new JMenuItem("Edit");
        popupMenu.add(edit);
        popupMenu.add(new JPopupMenu.Separator());
        delete = new JMenuItem("Delete");
        popupMenu.add(delete);

        journalList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me) && !journalList.isSelectionEmpty()
                        && journalList.locationToIndex(me.getPoint()) == journalList.getSelectedIndex()) {
                    popupMenu.show(journalList, me.getX(), me.getY());
                }
            }
        });

        edit.addActionListener(this);
        delete.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: sets up the panel for main menu
    private void setUpMainMenuPanel() throws IOException {
        mainMenuPanel = new JPanel();
        mainMenuPanel.setPreferredSize((new Dimension(WIDTH, HEIGHT)));
        setUpImagePanel();
        setUpButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets up the image panel on the main menu panel
    private void setUpImagePanel() throws IOException {
        JPanel imagePanel = new JPanel();
        imagePanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
        imagePanel.setPreferredSize((new Dimension(WIDTH - 50, HEIGHT / 2)));
        BufferedImage logoImg = ImageIO.read(new File("data/images/logo.png"));
        JLabel logo = new JLabel(new ImageIcon(logoImg));
        imagePanel.add(logo);

        mainMenuPanel.add(imagePanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up the button panel on the main menu panel
    private void setUpButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
        buttonPanel.setPreferredSize((new Dimension(WIDTH - 50, HEIGHT / 3)));

        Icon addButtonIcon = new ImageIcon("data/images/addIcon.png");
        JButton addBtn = new JButton("Add New Journal", addButtonIcon);
        addBtn.setFont(new Font("SansSerif", Font.PLAIN, 25));
        addBtn.setActionCommand("add new journal");
        addBtn.addActionListener(this);

        Icon browseButtonIcon = new ImageIcon("data/images/browseIcon.png");
        JButton browseBtn = new JButton("Browse All Journals", browseButtonIcon);
        browseBtn.setFont(new Font("SansSerif", Font.PLAIN, 25));
        browseBtn.setActionCommand("browse all journals");
        browseBtn.addActionListener(this);

        buttonPanel.add(addBtn);
        buttonPanel.add(Box.createHorizontalStrut(80));
        buttonPanel.add(browseBtn);

        mainMenuPanel.add(buttonPanel);

    }

    // CITATION: Learned ActionListener from https://stackoverflow.com/a/6578266
    // MODIFIES: this
    // EFFECTS: implements an action listener to detect which button is pressed on the main menu panel and the browse
    //          panel, and which menu item is selected in the popup menu.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add new journal")) {
            doAddNewJournal();
        } else if (e.getActionCommand().equals("browse all journals")) {
            doBrowseAllJournals();
        } else if (e.getSource() == edit) {
            doEditJournal();
        } else if (e.getSource() == delete) {
            doDeleteJournal();
        } else if (e.getActionCommand().equals("back")) {
            changePanelTo(mainMenuPanel);
        } else if (e.getActionCommand().equals("view all journals")) {
            doViewAllJournalsAfterSearch();

        }
    }

    // MODIFIES: this
    // EFFECTS: creates a popup window to add a new journal
    private void doAddNewJournal() {
        JOptionPane jop = new JOptionPane();
        JDialog dialog = jop.createDialog("Add New Journal");
        dialog.setSize(700, 500);

        journalWindow = new AddJournalWindow(dialog, null, journalCollection,
                textAreaNotEditable, journalListModel, numOfJournal);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setContentPane(journalWindow.getJPanel());
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: sets up the browse panel and makes the frame display the browse panel
    private void doBrowseAllJournals() {
        textAreaNotEditable.setText("");
        setUpBrowsePanel();
        changePanelTo(browsePanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a popup window to edit the selected journal
    private void doEditJournal() {
        TravelJournal selectedJournal = journalList.getSelectedValue();
        JOptionPane jop = new JOptionPane();
        JDialog dialog = jop.createDialog("Edit The Journal");
        dialog.setSize(700, 500);

        journalWindow = new EditJournalWindow(dialog, selectedJournal, journalCollection,
                textAreaNotEditable, journalListModel, numOfJournal);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setContentPane(journalWindow.getJPanel());
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: deletes the selected journal, updates the journal list, text area and the label showing the number of
    //          journals
    private void doDeleteJournal() {
        TravelJournal selectedJournal = journalList.getSelectedValue();
        journalListModel.removeElement(selectedJournal);
        journalCollection.deleteJournalByTitle(selectedJournal.getTitle());
        textAreaNotEditable.setText("");
        setUpNumOfJournal(journalCollection.numOfJournals());
    }

    // MODIFIES: this
    // EFFECTS: allows to display all journals in the journal collection and update the text area and the label showing
    //          the number of journals after searching for journal(s).
    private void doViewAllJournalsAfterSearch() {
        textAreaNotEditable.setText("");
        setUpNumOfJournal(journalCollection.numOfJournals());
        journalListModel.clear();
        updateJournalListModel(journalCollection.getJournalCollection());
    }

    // MODIFIES: this
    // EFFECTS: changes the panel displayed in the frame to the given panel.
    private void changePanelTo(JPanel panel) {
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: sets up the browse panel and all sub-panels on the browse panel
    private void setUpBrowsePanel() {
        browsePanel = new JPanel();
        browsePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // sets up the panel for the split pane
        JPanel splitPanel = new JPanel();
        splitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        splitPanel.setLocation(0, 0);
        splitPanel.setPreferredSize(new Dimension(WIDTH - 50, 400));

        setUpBrowseSplitPane();
        splitPanel.add(splitPane);

        // sets up the panel for the search panel and button panel
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new BorderLayout());
        toolPanel.setPreferredSize(new Dimension(WIDTH - 50, 160));
        toolPanel.setBorder(BorderFactory.createLineBorder(BLUE));

        setUpBrowseSearchPanel();
        setUpBrowseButtonPanel();

        toolPanel.add(buttonPanel, BorderLayout.EAST);
        toolPanel.add(searchPanel, BorderLayout.WEST);

        browsePanel.add(splitPanel);
        browsePanel.add(toolPanel);
    }


    // CITATION: Learned JList, ListSelectionListener and DefaultListModel from
    //           https://github.com/BranislavLazic/SwingTutorials/blob/master/src/main/java/JListTutorial.java
    // MODIFIES: this
    // EFFECTS: sets up the entire split pane, including the journal list and text area.
    //          implement the list selection listener to detect which journal is selected and display the corresponding
    //          text.
    private void setUpBrowseSplitPane() {

        splitPane.setPreferredSize(new Dimension(WIDTH - 50, 400));
        splitPane.setOneTouchExpandable(true);

        // sets up the journal list
        journalList.setModel(journalListModel);
        journalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        journalList.getSelectionModel().addListSelectionListener(e -> {
            if (!journalList.isSelectionEmpty()) {
                TravelJournal tj = journalList.getSelectedValue();
                textAreaNotEditable.setText(tj.getText());
            }
        });

        splitPane.setLeftComponent(new JScrollPane(journalList));


        // sets up the text area
        textAreaNotEditable.setLineWrap(true);
        textAreaNotEditable.setEditable(false);

        JPanel textPanel = new JPanel();

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textAreaNotEditable, BorderLayout.CENTER);

        splitPane.setRightComponent(new JScrollPane(textPanel));

    }

    // MODIFIES: this
    // EFFECTS: sets up the button panel on the browse panel and all buttons on the button panel
    private void setUpBrowseButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(WIDTH / 2 - 25, 200 - 40));

        JLabel space = new JLabel();
        space.setPreferredSize(new Dimension(WIDTH / 2 - 200, 30));
        buttonPanel.add(space);

        setUpButtonsOnButtonPanel("addIconSmall.png", "Add", "add new journal");
        setUpButtonsOnButtonPanel("back.png", "Back", "back");

    }

    // MODIFIES: this
    // EFFECTS: helper method for setting all buttons on the button panel of the browse panel
    private void setUpButtonsOnButtonPanel(String filename, String buttonText, String command) {
        Icon buttonIcon = new ImageIcon("data/images/" + filename);
        JButton button = new JButton(buttonText, buttonIcon);
        button.setPreferredSize(new Dimension(WIDTH / 2 - 200, 30));
        button.setFont(new Font("SansSerif", Font.PLAIN, 15));
        buttonPanel.add(button);
        button.setActionCommand(command);
        button.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: sets up the search panel on the browse panel
    private void setUpBrowseSearchPanel() {
        searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(WIDTH / 2 - 25, 200 - 40));
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


        setUpNumOfJournal(journalCollection.numOfJournals());
        searchPanel.add(numOfJournal);

        searchPanel.add(Box.createHorizontalStrut(WIDTH / 2 - 25));

        setUpComboBox();
        setUpSearchField();
        setUpSearchBtnAndDoSearch();

        searchPanel.add(Box.createHorizontalStrut(WIDTH / 2 - 25));

        setUpViewAllBtn();

    }

    // MODIFIES: this
    // EFFECTS: sets up the combo box on the search panel, allows the user to select "title", "date" or "location"
    private void setUpComboBox() {
        String[] choices = {"title", "date", "location"};
        comboBox = new JComboBox<>(choices);
        comboBox.setSelectedIndex(0);
        searchPanel.add(comboBox);
    }

    // MODIFIES: this
    // EFFECTS: sets up the text field used to input the search content
    private void setUpSearchField() {
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension((WIDTH / 2 - 25) / 2, 20));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        searchPanel.add(searchField);
    }

    // CITATION: Learned to bind a key to a JButton from https://stackoverflow.com/a/19067043
    // MODIFIES: this
    // EFFECTS: searches the journal(s) by title, date or location, updates the journal list, text area and the label
    //          showing the number of journals
    //          sets up the button that allows the user to search the journal(s) when clicked
    //          also binds the ENTER key to the button
    private void setUpSearchBtnAndDoSearch() {
        Icon searchButtonIcon = new ImageIcon("data/images/search.png");
        JButton searchBtn = new JButton(searchButtonIcon);
        searchBtn.setPreferredSize(new Dimension(35, 30));
        searchPanel.add(searchBtn);

        AbstractAction buttonPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaNotEditable.setText("");
                searchJournalBy();
            }
        };

        searchBtn.addActionListener(buttonPressed);
        searchBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER_Pressed");
        searchBtn.getActionMap().put("ENTER_Pressed", buttonPressed);
    }

    // MODIFIES: this
    // EFFECTS: searches journals by title, date, or location.
    //          If the input in the search field is empty, an error message will pop up
    private void searchJournalBy() {
        if (!searchField.getText().isEmpty()) {
            if (comboBox.getSelectedItem() == "title") {
                searchByTitle();
            } else if (comboBox.getSelectedItem() == "date") {
                searchByDate();
            } else if (comboBox.getSelectedItem() == "location") {
                searchByLocation();
            }
        } else {
            journalList.clearSelection();
            JOptionPane.showMessageDialog(null, "Search field cannot be empty！",
                    "Empty Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: implements search by title, updates the journal list and the label showing the number of journals
    private void searchByTitle() {
        if (journalCollection.findJournalByTitle(searchField.getText()) != null) {
            setUpNumOfJournal(1);
            journalListModel.clear();
            journalListModel.addElement(journalCollection.findJournalByTitle(searchField.getText()));
        } else {
            setUpNumOfJournal(0);
            journalListModel.clear();
        }
    }

    // MODIFIES: this
    // EFFECTS: implements search by date, updates the journal list and the label showing the number of journals
    private void searchByDate() {
        ArrayList<TravelJournal> journalList = journalCollection.findJournalsByDate(searchField.getText());
        setUpNumOfJournal(journalList.size());
        journalListModel.clear();
        updateJournalListModel(journalList);
    }

    // MODIFIES: this
    // EFFECTS: implements search by location, updates the journal list and the label showing the number of journals
    private void searchByLocation() {
        ArrayList<TravelJournal> journalList = journalCollection.findJournalsByLocation(searchField.getText());
        setUpNumOfJournal(journalList.size());
        journalListModel.clear();
        updateJournalListModel(journalList);
    }


    // MODIFIES: this
    // EFFECTS: sets up the button to display all journals in the journal collection when clicked.
    private void setUpViewAllBtn() {
        Icon viewAllButtonIcon = new ImageIcon("data/images/viewAll.png");
        JButton viewAllBtn = new JButton("View All Journals", viewAllButtonIcon);
        viewAllBtn.setPreferredSize(new Dimension(WIDTH / 2 - 200, 30));
        viewAllBtn.setFont(new Font("SansSerif", Font.PLAIN, 15));
        searchPanel.add(viewAllBtn);
        viewAllBtn.setActionCommand("view all journals");
        viewAllBtn.addActionListener(this);
    }


    // MODIFIES: this
    // EFFECTS: helper method to set the text of the label showing the number of journals based on the singular and
    //          plural
    private void setUpNumOfJournal(int num) {
        if (num == 1) {
            numOfJournal.setText("Find 1 Journal");
        } else {
            numOfJournal.setText("Find " + num + " Journals");
        }

    }

    // MODIFIES: this
    // EFFECTS: updates the journal list by adding each element in the given list.
    private void updateJournalListModel(ArrayList<TravelJournal> travelJournalList) {
        for (TravelJournal tj : travelJournalList) {
            journalListModel.addElement(tj);
        }
    }
}





