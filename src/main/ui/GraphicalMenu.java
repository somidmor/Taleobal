package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicalMenu extends JDesktopPane {
    private Button addButton = new Button("add");
    private Button editButton = new Button("edit");
    private Button likeButton = new Button("like");
    private Button seeMostLikedButton = new Button("most liked");
    private Button saveButton = new Button("save");
    private Button loadButton = new Button("load");
    private Button goToRootButton = new Button("root");
    private Button quitButton = new Button("quit");
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private GridBagConstraints gc;

    public GraphicalMenu(Dimension dimension) {
        super();
        Dimension size = new Dimension(dimension.width, dimension.height / 10);
        setMaximumSize(size);
        addButtonToButtons();
        setLayout(new GridBagLayout());
        setUpGridBagConstraints();
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 4; x++) {
                gc.gridy = y;
                gc.gridx = x;
                add(buttons.get(4 * y + x), gc); //0,1,2,3,4,5,6,7
            }
        }
    }

    private void setUpGridBagConstraints() {
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
    }

    private void addButtonToButtons() {
        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(likeButton);
        buttons.add(seeMostLikedButton);
        buttons.add(saveButton);
        buttons.add(loadButton);
        buttons.add(goToRootButton);
        buttons.add(quitButton);
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getGoToRootButton() {
        return goToRootButton;
    }

    public Button getLikeButton() {
        return likeButton;
    }

    public Button getLoadButton() {
        return loadButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getSeeMostLikedButton() {
        return seeMostLikedButton;
    }
}
