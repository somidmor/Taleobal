package ui;

import model.Cell;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/* Represents a Graphical cell having an object Cell and a select button
 */
public class GraphicalCell extends JPanel {
    private Cell cell;
    private Button selectButton;
    private Button changeColorButton;
    private JTextPane cellText;

    /*
     * EFFECTS: construct a pane with a button and a text filed which is the text of its actual cell
     * it also generates a random color and assign it to the cell
     */
    public GraphicalCell(Cell cell) {
        super();
        this.cell = cell;
        cellText = new JTextPane();
        selectButton = new Button("Select");
        changeColorButton = new Button("Change color");
        Dimension size = new Dimension(100, 50);
        setPreferredSize(size);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(blackLine);
        setupCellText();


        cellText.setText(cell.getContent());
        cellText.setEditable(false);

        add(changeColorButton, BorderLayout.NORTH);
        add(cellText, BorderLayout.CENTER);
        add(selectButton, BorderLayout.SOUTH);
        changeColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellText.setBackground(TaleobalApp.randomColor());
            }
        });
    }

    /*
     * EFFECTS: set up the cell text and align the text to center also change the color
     * to a new random color
     * it also generates a random color and assign it to the cell
     */
    public void setupCellText() {
        cellText = new JTextPane();
        StyledDocument documentStyle = cellText.getStyledDocument();
        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
        cellText.setBackground(TaleobalApp.randomColor());
    }


    //getters
    public Button getSelectButton() {
        return selectButton;
    }

    public Cell getCell() {
        return cell;
    }
}
