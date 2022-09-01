# TraveLog

## A Travel Logging Application

#### What will the application do?

This application is for recording travel. Users can document their travel experiences so that they can look back on 
wonderful trips afterwards. This app allows users to add new journals and record the locations and dates of their trips. 
Users can also edit completed journals or delete journals they no longer want to keep. Moreover, the app helps users 
categorize and organize their journals based on locations and dates. It also allows users to find journals by title, 
location and date.

#### Who will use it?

This application is designed for those who love to travel and write about travel memories such as plans or expectations, 
experiences and reflections, to document their beautiful memories and also to learn from their travel experiences. The 
app is also suitable for those who prefer to keep a travel journal digitally for efficient and permanent recording, and 
also for better organizing and finding the journals.

#### Why is the project of interest to you

As a travel enthusiast, I have always found travel logging software useful and rewarding as it allows me to conveniently 
record the interesting experiences and feelings I have while travelling. Furthermore, I find it challenging to organize 
my travel journals because many new thoughts pop up and want to be recorded, which can make handwritten journals messy,
and they can easily get lost or damaged. Therefore, an application that can help me document and organize/categorize my 
travel journals is a great solution for me.

## User Stories

- As a user, I want to be able to add a new travel journal to my journal collection
- As a user, I want to be able to view a list of travel journals in my journal collection
- As a user, I want to be able to update my completed travel journals
- As a user, I want to be able to delete travel journals that I no longer want to keep
- As a user, I want to be able to find my travel journals by title, location and date
- As a user, I want to be prompted with the option to save all travel journals to file when the application ends
- As a user, I want to be prompted with the option to load all travel journals from file when the application starts

## Instructions for Grader

- You can generate the first required event by adding a new travel journal by clicking the **Add New Journal** button on 
the main menu. However, it will not lead you to see all the journals in the journal collection. You can click 
**Browse All Journals** to see them. You can also choose to add a new travel journal by clicking the **Add** button at 
the bottom right of the browse panel, which allows you to immediately see all journals including the newly added one on 
the split panel above.

- After clicking the **Add New Journal** or **Add** button, a dialog will pop up for you to enter the title, date, 
location and text of your journal. After finishing entering the required entries, you can proceed to click the **Add** 
button to create a new travel journal, or click the **Cancel** button to forfeit it. After clicking the **Add** button, 
if the title you entered is already taken, or if any entry is empty, an error message will pop up to alert you.

- On the browse panel, you can click on each journal on the left side of the split panel to see the corresponding text 
on the right panel.

- You can generate the second required event by choosing to edit or delete a specific journal from the popup menu. You 
must select a journal in the journal list and then right-click on it to make the popup menu appear. If you choose to 
edit the journal, a dialog will pop up for you to edit the title, date, location and text of the selected journal. After
finishing editing, you can proceed to click the **OK** button, or click the **Cancel** button to forfeit. After clicking
the **OK** button, if the title you entered is already taken, or if any entry is empty, an error message will pop up to 
alert you. If you choose to delete the journal, the journal will be immediately removed from the journal list.

- You can see the total number of journals or the number of journals found after searching on the label above the search 
field.

- You can search for journals by typing the title, date or location in the search field at the bottom left of the browse 
panel. You can use the combo box to select which attributes of the journal you want to search by. You can click the 
**Search** button beside the search field or press the "ENTER" key on the keyboard to trigger a search. If the search
field is empty when you trigger a search, an error message will pop up to alert you.

- You can click on **View All Journals** below the search field to enable you to see all journals again after searching.

- You can return to the main menu by clicking the **Back** button at the bottom right of the browse panel.

- You can locate my visual components by looking at the logo "TraveLog" on the main menu, the icons on the buttons, and 
the icons on the popup windows for loading and saving.

- You will be prompted with the option to save all travel journals to file when closing the application. You can refuse 
to save the data by selecting **No**, and you can save the data by selecting **Yes**. You can close the popup window to
cancel closing the application. 

- You will be prompted with the option to load all travel journals from file when the application starts. You can refuse 
to load data by selecting **No** or by closing the popup window. You can choose to load data by clicking **Yes**.

## Phase 4: Task 2

A representative sample of the events that occur when the program runs:

Tue Aug 09 17:09:55 PDT 2022\
Added a travel journal titled "Canada's Wonderland Tour"

Tue Aug 09 17:09:55 PDT 2022\
Added a travel journal titled "Universal Studios Japan Tour"

Tue Aug 09 17:10:32 PDT 2022\
Added a travel journal titled "Niagara Falls Tour"

Tue Aug 09 17:10:38 PDT 2022\
Removed the travel journal titled "Universal Studios Japan Tour"

Tue Aug 09 17:11:02 PDT 2022\
Removed the travel journal titled "Canada's Wonderland Tour"

## Phase 4: Task 3

Refactoring I want to do to improve the project design:

- I want to refactor the code to reduce coupling between the `JournalWindow` and `TravelJournal` classes. I want to use 
the `JournalCollection` class as an interface between these two classes to reduce coupling.

- I want to split the `TraveLogAppGUI` class to improve the cohesion of my code. The `TraveLogAppGUI` class contains 
many functionalities, such as writing/reading json files, GUI parts, searching, deleting and listening to most buttons. 
I want to split this class to avoid overwhelming it.

- There are some similar methods in the `JournalWindow` and `TraveLogAppGUI` classes. I want to create an abstract class
for them to extend to reduce duplicate code.

- I want to enhance the single point of control over the dimension and layout of the graphical components for easy 
adjustment.

- I want to create another exception called `DuplicateTitleException` so that when a new journal is added and the title 
is duplicated, this exception can be thrown instead of adding an if statement to the `ui` package to handle it.