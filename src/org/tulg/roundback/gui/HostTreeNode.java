package org.tulg.roundback.gui;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by jasonw on 10/9/2016.
 */
public class HostTreeNode extends DefaultMutableTreeNode {

    int hid;
    public HostTreeNode(Object userObject) {
        super(userObject);
    }
    public HostTreeNode(Object userObject, String hid){
        super(userObject);
        this.hid = Integer.parseInt(hid);
    }

}
