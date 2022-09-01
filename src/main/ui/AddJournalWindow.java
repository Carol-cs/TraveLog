package ui;

import model.JournalCollection;
import model.TravelJournal;

import javax.swing.*;
import java.awt.*;

// Creates the popup window that allows the user to add a new journal.
public class AddJournalWindow extends JournalWindow {


    // EFFECTS: sets up the popup window that allows the user to add a new journal
    public AddJournalWindow(JDialog dialog, TravelJournal travelJournal, JournalCollection journalCollection,
                            JTextArea textAreaNotEditable, DefaultListModel<TravelJournal> journalListModel,
                            JLabel numOfJournal) {
        super(dialog, travelJournal, journalCollection, textAreaNotEditable, journalListModel, numOfJournal);
    }

    @Override
    protected void setUpTitleField() {
        // does nothing

    }

    @Override
    protected void setUpDateField() {
        // does nothing
    }

    @Override
    protected void setUpLocationField() {
        // does nothing
    }

    @Override
    protected void setUpTextArea() {
        // does nothing
    }

    // MODIFIES: this
    // EFFECTS: sets up the Add button on the button panel
    @Override
    protected void setUpRightButton() {
        Icon addButtonIcon = new ImageIcon("data/images/addIcon.png");
        JButton addBtn = new JButton("Add", addButtonIcon);
        addBtn.setFont(new Font("SansSerif", Font.PLAIN, 20));
        addBtn.setActionCommand("add");
        addBtn.addActionListener(this);
        buttonPanel.add(addBtn);
    }


}
