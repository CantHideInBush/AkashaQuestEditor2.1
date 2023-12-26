package pl.canthideinbush.akashaquesteditor.app.components;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.CSS;
import java.awt.*;
import java.beans.BeanProperty;

public class TextPane extends JTextPane {

    public TextPane() {
    }

    protected int getColumnWidth() {
        if (columnWidth == 0) {
            FontMetrics metrics = getFontMetrics(getFont());
            columnWidth = metrics.charWidth('m');
        }
        return columnWidth;
    }

    protected int getRowHeight() {
        if (rowHeight == 0) {
            FontMetrics metrics = getFontMetrics(getFont());
            rowHeight = metrics.getHeight();
        }
        return rowHeight;
    }

    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d = (d == null) ? new Dimension(400,400) : d;
        Insets insets = getInsets();

        if (columns != 0) {
            d.width = Math.max(d.width, columns * getColumnWidth() +
                    insets.left + insets.right);
        }
        if (rows != 0) {
            d.height = Math.max(d.height, rows * getRowHeight() +
                    insets.top + insets.bottom);
        }
        return d;
    }

    @BeanProperty(bound = false)
    public Dimension getPreferredScrollableViewportSize() {
        Dimension size = super.getPreferredScrollableViewportSize();
        size = (size == null) ? new Dimension(400,400) : size;
        Insets insets = getInsets();

        size.width = (columns == 0) ? size.width :
                columns * getColumnWidth() + insets.left + insets.right;
        size.height = (rows == 0) ? size.height :
                rows * getRowHeight() + insets.top + insets.bottom;
        return size;
    }

    public void setDefaultFont(Font font) {
        MutableAttributeSet set = getInputAttributes();
        StyleConstants.FontConstants.setFontFamily(set, font.getFamily());
        StyleConstants.FontConstants.setFontSize(set, font.getSize());
        StyleConstants.FontConstants.setItalic(set, font.isItalic());
        StyleConstants.FontConstants.setBold(set, font.isBold());
    }


    private int rows;
    private int columns;
    private int columnWidth;
    private int rowHeight;
    private boolean wrap;
    private boolean word;

}
