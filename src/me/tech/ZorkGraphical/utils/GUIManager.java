package me.tech.ZorkGraphical.utils;

import me.tech.ZorkGraphical.Zork;
import me.tech.ZorkGraphical.items.InventorySlotType;
import me.tech.ZorkGraphical.items.Item;
import me.tech.ZorkGraphical.items.Weapon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GUIManager {
    public JFrame frame;
    public JTextArea log;
    public JTextField input;
    public JButton enter;
    public JPanel mainPanel;
    private Zork zork;
    public JMenuBar menu;
    private JList inventoryList;
    public JProgressBar experience;

    public GUIManager(Zork z) {
        this.zork = z;
        frame = new JFrame("Mad Russian: Initial Impact!");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setLayout(new StackLayout());
        frame.setResizable(false);
        experience = new JProgressBar();
        experience.setStringPainted(true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        menu = new JMenuBar();
        JMenu m = new JMenu("View");
        JMenuItem inventory = new JMenuItem("Command Help");
        JMenuItem recipes = new JMenuItem("Crafting Recipes");

        String commandString = "";
        for(String command : Zork.getInstance().commandHandler.descriptions.keySet()){
            commandString += command + ": " + Zork.getInstance().commandHandler.getDescription(command) + "\n";
        }
        commandString = commandString.trim();
        final String descs = commandString;

        inventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(frame, descs);
            }
        });
        recipes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                final JFrame recipeFrame = new JFrame("Crafting Recipes");
                final String[] recipes = Zork.getInstance().getRecipes().keySet().toArray(new String[]{});
                final JList list = new JList(recipes);
                list.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (true) {
                            list.setSelectedIndex(list.locationToIndex(e.getPoint()));
                            if (!list.isSelectionEmpty()) {
                                String slist = "You need: \n";
                                for(Item i : Zork.getInstance().getRecipes().get(recipes[list.getSelectedIndex()]).getRequirements()){
                                    slist += i.getName() + "\n";
                                }
                                slist = slist.trim();
                                JOptionPane.showMessageDialog(recipeFrame, slist);
                            }
                        }
                    }
                });
                recipeFrame.add(list);
                recipeFrame.pack();
                recipeFrame.setLocationRelativeTo(frame);
                recipeFrame.setResizable(false);
                recipeFrame.setVisible(true);
            }
        });
        m.add(inventory);
        m.add(recipes);
        menu.add(m);

        frame.add(menu);

        inventoryList = new JList();

        log = new JTextArea(25, 50);
        log.setEditable(false);
        log.setBackground(new Color(0, 0, 0));
        log.setForeground(new Color(255, 255, 255));
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(log);
        JScrollPane iScrollPane = new JScrollPane(inventoryList);
        new SmartScroller(scrollPane);

        input = new JTextField();
        input.setPreferredSize(new Dimension(650, 25));

        enter = new JButton("Enter");

        GridBagLayout gridBag = new GridBagLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(gridBag);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 4;
        gbc.gridwidth = 1;

        mainPanel.add(scrollPane, gbc);

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 1;

        mainPanel.add(experience, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.gridwidth = 1;

        mainPanel.add(input, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.gridwidth = 1;

        mainPanel.add(enter, gbc);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;

        mainPanel.add(iScrollPane, gbc);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        final JPopupMenu itemOptions = new JPopupMenu("Options");
        JMenu equip = new JMenu("Equip");
        JMenuItem right = new JMenuItem("Right Hand");
        JMenuItem left = new JMenuItem("Left Hand");
        equip.add(right);
        equip.add(left);
        itemOptions.add(equip);

        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!inventoryList.isSelectionEmpty()) {
                    int index = inventoryList.getSelectedIndex();
                    Item i = Zork.getInstance().getPlayer().getInventory().getFullInventory().get(index);
                    Zork.getInstance().getPlayer().getInventory().equip(InventorySlotType.RIGHT_HAND, i);
                }
            }
        });

        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!inventoryList.isSelectionEmpty()) {
                    int index = inventoryList.getSelectedIndex();
                    Item i = Zork.getInstance().getPlayer().getInventory().getFullInventory().get(index);
                    Zork.getInstance().getPlayer().getInventory().equip(InventorySlotType.LEFT_HAND, i);
                }
            }
        });

        inventoryList.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    inventoryList.setSelectedIndex(inventoryList.locationToIndex(e.getPoint()));
                    if (!inventoryList.isSelectionEmpty()) {
                        itemOptions.show(inventoryList, e.getPoint().x, e.getPoint().y);
                    }
                }
            }
        });

        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (input.getText().length() > 0) {
                    zork.commandHandler.handle(input.getText());
                    input.setText("");
                    input.requestFocus();
                }
            }
        });

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (input.getText().length() > 0) {
                    zork.commandHandler.handle(input.getText());
                    input.setText("");
                    input.requestFocus();
                }
            }
        });
    }

    public void updateExp(int level, int exp){
        int maxExp = Experience.getRequiredExpForLevel(level);
        experience.setMaximum(maxExp);
        experience.setValue(exp);
        experience.setStringPainted(true);
        experience.setString("Level " + level + " - " + exp + "/" + maxExp);
    }

    public void updateInventory() {
        int size = Zork.getInstance().getPlayer().getInventory().getFullInventory().size();
        ArrayList<String> name = new ArrayList<String>();
        for (InventorySlotType slot : Zork.getInstance().getPlayer().getInventory().getItems().keySet()) {
            if (!slot.equals(InventorySlotType.INVENTORY)) {
                if (Zork.getInstance().getPlayer().getInventory().getSlot(slot).size() > 0) {
                    Item it = Zork.getInstance().getPlayer().getInventory().getItemInSlot(slot);
                    if (it instanceof Weapon) {
                        name.add(it.getName() + " - " + it.getDurability() + " - (" + slot.toString().substring(0, 1) + ")");
                    } else {
                        name.add(it.getName() + " - (" + slot.toString().substring(0, 1) + ")");
                    }
                }
            } else {
                for (Item n : Zork.getInstance().getPlayer().getInventory().getSlot(InventorySlotType.INVENTORY)) {
                    if(n instanceof Weapon)
                        name.add(n.getName() + " - " + n.getDurability());
                    else
                        name.add(n.getName());
                }
            }
        }
        inventoryList.setListData(name.toArray(new String[]{}));
    }
}
