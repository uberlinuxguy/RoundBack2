package org.tulg.roundback.gui;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Created by jasonw on 9/25/2016.
 */
public class GuiTreeRenderer extends DefaultTreeCellRenderer {
    public GuiTreeRenderer () {

    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        setIcon(new ImageIcon("assets/icons/file-doc.png"));
        if( !leaf ){

            if(expanded){
                setIcon(new ImageIcon("assets/icons/folder-open-5.png"));
            } else {
                setIcon(new ImageIcon("assets/icons/folder-3.png"));
            }
        }
        return this;
    }
}
