package gui.control;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import data.data.DataManager;
import data.data.addables.UUG;
import data.data.addables.User;
import data.data.addables.UserGroup;

import java.awt.*;

/**
 * panel with the tree view of users and groups.
 */
public class SideViewPanel extends JPanel {
    private final DataManager dataManager = DataManager.getInstance();
    private JTree tree;

    public SideViewPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 450));
        setBorder(BorderFactory.createTitledBorder("Tree View"));
        buildTree();
    }

    public void buildTree() {
        UserGroup rootGroup = dataManager.getRootGroup();
        DefaultMutableTreeNode root = buildTreeNode(rootGroup);

        tree = new JTree(root);
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if (value instanceof DefaultMutableTreeNode) {
                    Object userValue = ((DefaultMutableTreeNode) value).getUserObject();

                    // uses Composite design to call getId from either user or group
                    if (userValue instanceof UUG)
                        setText(((UUG)userValue).getId());

                    // Groups have a directory icon.
                    if (userValue instanceof UserGroup)
                        setIcon(UIManager.getIcon("FileView.directoryIcon"));
                }
                return this;
            }
        });

        removeAll();
        add(tree);
    }

    //update the tree view
    public void refresh() {
        buildTree();
        validate();
        repaint();
    }

    public User getSelectedUser() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (node != null) {
            Object userObject = node.getUserObject();
            if (userObject instanceof User)
                return (User) userObject;
        }

        return null;
    }

    /**
     * Build the tree nodes from the root group.
     */
    private DefaultMutableTreeNode buildTreeNode(UserGroup group) {
        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode(group);
        for (User user : group.getUsers()) {
            topNode.add(new DefaultMutableTreeNode(user));
        }

        for (UserGroup subgroup : group.getSubgroups()) {
            DefaultMutableTreeNode subgroupNode = buildTreeNode(subgroup);
            topNode.add(subgroupNode);
        }

        return topNode;
    }
}
