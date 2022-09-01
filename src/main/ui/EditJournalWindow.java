package ui;

import model.JournalCollection;
import model.TravelJournal;

import javax.swing.*;
import java.awt.*;

// Creates the popup window that allows the user to edit a journal.
public class EditJournalWindow extends JournalWindow {

    // EFFECTS: sets up the popup window that allows the user to edit a journal
    public EditJournalWindow(JDialog dialog, TravelJournal travelJournal, JournalCollection journalCollection,
                             JTextArea textAreaNotEditable, DefaultListModel<TravelJournal> journalListModel,
                             JLabel numOfJournal) {
        super(dialog, travelJournal, journalCollection, textAreaNotEditable, journalListModel, numOfJournal);
    }

    // MODIFIES: this
    // EFFECTS: adds the original title of the selected travel journal to the title field of the popup window
    @Override
    protected void setUpTitleField() {
        titleField.setText(travelJournal.getTitle());


    }

    // MODIFIES: this
    // EFFECTS: adds the original date of the selected travel journal to the date field of the popup window
    @Override
    protected void setUpDateField() {
        dateField.setText(travelJournal.getDate());
    }

    // MODIFIES: this
    // EFFECTS: adds the original location of the selected travel journal to the location field of the popup window
    @Override
    protected void setUpLocationField() {
        locationField.setText(travelJournal.getLocation());
    }

    // MODIFIES: this
    // EFFECTS: adds the original text of the selected travel journal to the title area of the popup window
    @Override
    protected void setUpTextArea() {
        textArea.setText(travelJournal.getText());
    }

    // MODIFIES: this
    // EFFECTS: sets up the OK button on the button panel
    @Override
    protected void setUpRightButton() {
        Icon okButtonIcon = new ImageIcon("data/images/ok.png");
        JButton okBtn = new JButton("OK", okButtonIcon);
        okBtn.setFont(new Font("SansSerif", Font.PLAIN, 20));
        okBtn.setActionCommand("ok");
        okBtn.addActionListener(this);
        buttonPanel.add(okBtn);
    }


}
