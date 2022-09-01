package ui;

import model.JournalCollection;
import model.TravelJournal;
import model.exceptions.EmptyInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;


// Represents the popup window that allows the user to add or edit a journal.
public abstract class JournalWindow extends JPanel implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private JPanel panel;
    private JPanel titleDateLocationPanel;
    private JPanel textPanel;
    protected JPanel buttonPanel;

    protected JTextField titleField;
    protected JTextField dateField;
    protected JTextField locationField;

    protected JTextArea textArea;

    private JDialog dialog;

    protected TravelJournal travelJournal;
    protected JournalCollection journalCollection;
    private JTextArea textAreaNotEditable;
    private DefaultListModel<TravelJournal> journalListModel;
    private JLabel numOfJournal;


    // EFFECTS: sets up the popup window that allows the user to add or edit a journal
    public JournalWindow(JDialog dialog, TravelJournal travelJournal, JournalCollection journalCollection,
                         JTextArea textAreaNotEditable, DefaultListModel<TravelJournal> journalListModel,
                         JLabel numOfJournal) {

        panel = new JPanel();
        panel.setPreferredSize((new Dimension(WIDTH, HEIGHT)));
        panel.setLayout(null);

        this.dialog = dialog;
        this.travelJournal = travelJournal;
        this.journalCollection = journalCollection;
        this.textAreaNotEditable = textAreaNotEditable;
        this.journalListModel = journalListModel;
        this.numOfJournal = numOfJournal;

        setUpAllPanels();

        setUpTitle();
        setUpDate();
        setUpLocation();
        setUpText();

        setUpButton();

        panel.add(titleDateLocationPanel);
        panel.add(textPanel);
        panel.add(buttonPanel);

        panel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up all sub-panels, including the panel for inputting title, date, and location,
    //          the panel for inputting text, and the panel for placing buttons
    private void setUpAllPanels() {
        titleDateLocationPanel = new JPanel();
        titleDateLocationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titleDateLocationPanel.setLocation(0, 0);
        titleDateLocationPanel.setSize(WIDTH, HEIGHT / 4);
        titleDateLocationPanel.setBorder(BorderFactory.createLineBorder(TraveLogAppGUI.BLUE));

        textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        textPanel.setLocation(0, HEIGHT / 4);
        textPanel.setSize(WIDTH, HEIGHT / 2 + 50);
        textPanel.setBorder(BorderFactory.createLineBorder(TraveLogAppGUI.BLUE));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setLocation(0, HEIGHT / 4 + HEIGHT / 2 + 50);
        buttonPanel.setSize(WIDTH, HEIGHT / 4);
        buttonPanel.setBorder(BorderFactory.createLineBorder(TraveLogAppGUI.BLUE));
    }

    // MODIFIES: this
    // EFFECTS: sets up the title label and the field for inputting the title
    private void setUpTitle() {
        JLabel title = new JLabel("Title: ");
        title.setFont(new Font("SansSerif", Font.PLAIN, 20));
        titleDateLocationPanel.add(title);

        titleField = new JTextField(20);
        titleField.setFont(new Font("SansSerif", Font.PLAIN, 20));

        setUpTitleField();

        titleDateLocationPanel.add(titleField);
        titleDateLocationPanel.add(addEmptySpace(50));
    }


    // MODIFIES: this
    // EFFECTS: sets up the date label and the field for inputting the date
    private void setUpDate() {
        JLabel date = new JLabel("Date: ");
        date.setFont(new Font("SansSerif", Font.PLAIN, 20));
        titleDateLocationPanel.add(date);

        String time = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        dateField = new JTextField(time, 20);
        dateField.setFont(new Font("SansSerif", Font.PLAIN, 20));

        setUpDateField();

        titleDateLocationPanel.add(dateField);
        titleDateLocationPanel.add(addEmptySpace(50));

    }

    // MODIFIES: this
    // EFFECTS: sets up the location label and the field for inputting the location
    private void setUpLocation() {
        JLabel location = new JLabel("Location: ");
        location.setFont(new Font("SansSerif", Font.PLAIN, 20));
        titleDateLocationPanel.add(location);

        locationField = new JTextField(20);
        locationField.setFont(new Font("SansSerif", Font.PLAIN, 20));

        setUpLocationField();

        titleDateLocationPanel.add(locationField);

    }


    // EFFECTS: Helper method for adding empty space between components
    private JLabel addEmptySpace(int num) {
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < num; i++) {
            space.append(" ");
        }

        return new JLabel(space.toString());
    }

    // MODIFIES: this
    // EFFECTS: sets up the text label and the field for inputting the text
    private void setUpText() {
        JLabel text = new JLabel("Text: ");
        text.setFont(new Font("SansSerif", Font.PLAIN, 20));
        textPanel.add(text);

        textArea = new JTextArea();
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 20));

        setUpTextArea();

        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 10, HEIGHT / 2));
        textPanel.add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: abstract method to set up the text of the title field
    protected abstract void setUpTitleField();

    // MODIFIES: this
    // EFFECTS: abstract method to set up the text of the date field
    protected abstract void setUpDateField();

    // MODIFIES: this
    // EFFECTS: abstract method to set up the text of the location field
    protected abstract void setUpLocationField();

    // MODIFIES: this
    // EFFECTS: abstract method to set up the text of the text area
    protected abstract void setUpTextArea();


    // MODIFIES: this
    // EFFECTS: sets up the Cancel button and the right (Add/Edit) button
    private void setUpButton() {
        setUpCancelButton();
        setUpRightButton();

    }

    // MODIFIES: this
    // EFFECTS: sets up the cancel button and adds it to the button panel
    private void setUpCancelButton() {
        Icon cancelButtonIcon = new ImageIcon("data/images/cancel.png");
        JButton cancelBtn = new JButton("Cancel", cancelButtonIcon);
        cancelBtn.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cancelBtn.setActionCommand("cancel");
        cancelBtn.addActionListener(this);

        buttonPanel.add(cancelBtn);
        buttonPanel.add(Box.createHorizontalStrut(200));

    }

    // MODIFIES: this
    // EFFECTS: abstract method to set up the right button on the button panel
    protected abstract void setUpRightButton();

    // getters
    // EFFECTS: returns the main panel
    public JPanel getJPanel() {
        return panel;
    }

    // EFFECTS: returns the input title as a string
    public String getTitleField() {
        return titleField.getText();
    }

    // EFFECTS: returns the input date as a string
    public String getDateField() {
        return dateField.getText();
    }

    // EFFECTS: returns the input location as a string
    public String getLocationField() {
        return locationField.getText();
    }

    // EFFECTS: returns the input text as a string
    public String getTextArea() {
        return textArea.getText();
    }

    // MODIFIES: this
    // EFFECTS: implements an action listener to detect which button is pressed on the window for adding or editing a
    //          new journal
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            doAddJournal();
        } else if (e.getActionCommand().equals("ok")) {
            doEditJournal();
        } else if (e.getActionCommand().equals("cancel")) {
            dialog.dispose();
        }
    }


    // MODIFIES: this
    // EFFECTS:  adds and sets a new journal if the set title is not already taken and there are no empty inputs,
    //           updates the journal list, text area and the label showing the number of journals
    //           If the set is already taken, pops up an error message
    //           If there are empty inputs, throws EmptyInputException and pops up an error message
    private void doAddJournal() {
        try {
            travelJournal = new TravelJournal();
            setUpTravelJournalAttributes();

            if (! journalCollection.addJournal(travelJournal)) {
                duplicateTitleErrorDialog();
                travelJournal = null; // delete the object
            } else {
                dialog.dispose();
                textAreaNotEditable.setText("");
                setUpNumOfJournal(journalCollection.numOfJournals());
                journalListModel.clear();
                updateJournalList();
            }

        } catch (EmptyInputException e) {
            emptyInputErrorDialog();
            travelJournal = null; // delete the object
        }
    }

    // MODIFIES: this
    // EFFECTS:  edits a journal if the reset title is not already taken and there are no empty inputs,
    //           updates the journal list and the text area based on changes
    //           If the reset is already taken, pops up an error message
    //           If there are empty inputs, throws EmptyInputException and pops up an error message
    private void doEditJournal() {
        try {
            setUpTravelJournalAttributes();

            if (journalCollection.findJournalByTitle(getTitleField()) != travelJournal
                    && journalCollection.findJournalByTitle(getTitleField()) != null) {
                duplicateTitleErrorDialog();

            } else {
                textAreaNotEditable.setText(travelJournal.getText());
                dialog.dispose();
            }
        } catch (EmptyInputException e) {
            emptyInputErrorDialog();
        }
    }

    // MODIFIES: this
    // EFFECTS: helper method to set up all travel journal attributes, including title, date, location and text based
    //          on the inputs
    private void setUpTravelJournalAttributes() throws EmptyInputException {
        travelJournal.setTitle(getTitleField());
        travelJournal.setDate(getDateField());
        travelJournal.setLocation(getLocationField());
        travelJournal.setText(getTextArea());
    }

    // EFFECTS: create a popup message dialog to alert that the title is already taken
    private void duplicateTitleErrorDialog() {
        JOptionPane.showMessageDialog(null, "The title \""
                        + getTitleField() + "\" is already taken. "
                        + "\nPlease choose a different title!",
                "Duplicate Title Error", JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: create a popup message dialog to alert that there are invalid empty inputs
    private void emptyInputErrorDialog() {
        JOptionPane.showMessageDialog(null, "ALL INPUTS CANNOT BE EMPTY, PLEASE TRY"
                + " AGAIN", "Empty Input Error", JOptionPane.ERROR_MESSAGE);
    }


    // MODIFIES: this
    // EFFECTS: updates the journal list by looping through the journal collection
    private void updateJournalList() {
        for (TravelJournal tj : journalCollection.getJournalCollection()) {
            journalListModel.addElement(tj);
        }
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





}
