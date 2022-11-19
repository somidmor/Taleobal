package ui;

import model.Cell;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class GraphicalCell extends JPanel {
    private Cell cell;
    private Button selectButton;

    public GraphicalCell(Cell cell) {
        super();
        this.cell = cell;
        selectButton = new Button("Select");
        Dimension size = new Dimension(100, 50);
        setPreferredSize(size);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(blackLine);


        JTextPane cellText = new JTextPane();
        StyledDocument documentStyle = cellText.getStyledDocument();
        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
        //String text =
        cellText.setText(cell.getContent());
        cellText.setEditable(false);

        add(cellText, BorderLayout.CENTER);
        add(selectButton, BorderLayout.SOUTH);
    }

    public Button getSelectButton() {
        return selectButton;
    }

    public Cell getCell() {
        return cell;
    }
}
