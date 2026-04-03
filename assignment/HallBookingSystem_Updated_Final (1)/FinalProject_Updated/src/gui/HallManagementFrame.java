package gui;

import model.*;
import util.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * HallManagementFrame.java
 * Displays all halls in a table.
 * - In "view only" mode: customers see available halls with filters.
 * - In "manage" mode: schedulers can add, edit, delete, and toggle availability.
 */
public class HallManagementFrame extends JFrame {

    private final User currentUser;
    private final boolean viewOnly; // true = customer view, false = scheduler manage
    private DefaultTableModel tableModel;
    private JTable hallTable;
    private JComboBox<String> filterType;
    private JTextField filterCapacity;

    public HallManagementFrame(User currentUser, boolean viewOnly) {
        this.currentUser = currentUser;
        this.viewOnly = viewOnly;
        setTitle(viewOnly ? "Available Halls" : "Manage Halls");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 480);
        setLocationRelativeTo(null);
        initComponents();
        loadHalls();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Filter Panel (top) ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter Halls"));

        filterPanel.add(new JLabel("Hall Type:"));
        filterType = new JComboBox<>(new String[]{ "All", Hall.TYPE_AUDITORIUM, Hall.TYPE_BANQUET, Hall.TYPE_MEETING });
        filterPanel.add(filterType);

        filterPanel.add(new JLabel("  Min Capacity:"));
        filterCapacity = new JTextField(6);
        filterPanel.add(filterCapacity);

        JButton filterBtn = new JButton("Apply Filter");
        filterPanel.add(filterBtn);

        JButton clearBtn = new JButton("Clear");
        filterPanel.add(clearBtn);

        mainPanel.add(filterPanel, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = { "Hall ID", "Name", "Type", "Capacity", "Rate/Hour (RM)", "Available" };
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        hallTable = new JTable(tableModel);
        hallTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainPanel.add(new JScrollPane(hallTable), BorderLayout.CENTER);

        // --- Button Panel (bottom) ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        if (!viewOnly) {
            // Scheduler mode buttons
            JButton addBtn    = new JButton("Add Hall");
            JButton editBtn   = new JButton("Edit Hall");
            JButton deleteBtn = new JButton("Delete Hall");
            JButton toggleBtn = new JButton("Toggle Availability");

            addBtn.setBackground(new Color(20, 120, 20)); addBtn.setForeground(Color.WHITE);
            editBtn.setBackground(new Color(30, 80, 160)); editBtn.setForeground(Color.WHITE);
            deleteBtn.setBackground(new Color(180, 50, 50)); deleteBtn.setForeground(Color.WHITE);

            addBtn.addActionListener(e -> showAddHallDialog());
            editBtn.addActionListener(e -> showEditHallDialog());
            deleteBtn.addActionListener(e -> deleteSelectedHall());
            toggleBtn.addActionListener(e -> toggleAvailability());

            btnPanel.add(addBtn);
            btnPanel.add(editBtn);
            btnPanel.add(deleteBtn);
            btnPanel.add(toggleBtn);
        }

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        btnPanel.add(closeBtn);

        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Filter events
        filterBtn.addActionListener(e -> loadHalls());
        clearBtn.addActionListener(e -> { filterType.setSelectedIndex(0); filterCapacity.setText(""); loadHalls(); });
    }

    /** Load (or reload) halls into the table, applying any active filters. */
    private void loadHalls() {
        tableModel.setRowCount(0);
        List<Hall> halls = FileManager.readHalls();

        String selectedType = (String) filterType.getSelectedItem();
        String capacityStr  = filterCapacity.getText().trim();
        int minCapacity = 0;
        if (!capacityStr.isEmpty()) {
            try { minCapacity = Integer.parseInt(capacityStr); }
            catch (NumberFormatException ex) { /* ignore bad input */ }
        }

        for (Hall h : halls) {
            // Apply type filter
            if (!selectedType.equals("All") && !h.getHallType().equals(selectedType)) continue;
            // Apply capacity filter
            if (minCapacity > 0 && h.getCapacity() < minCapacity) continue;
            // In view-only mode, show only available halls
            if (viewOnly && !h.isAvailable()) continue;

            tableModel.addRow(new Object[]{
                h.getHallId(), h.getHallName(), h.getHallType(),
                h.getCapacity(), String.format("%.2f", h.getRatePerHour()),
                h.isAvailable() ? "Yes" : "No"
            });
        }
    }

    /** Dialog to add a new hall. */
    private void showAddHallDialog() {
        JTextField nameField = new JTextField(15);
        JComboBox<String> typeBox = new JComboBox<>(new String[]{
            Hall.TYPE_AUDITORIUM, Hall.TYPE_BANQUET, Hall.TYPE_MEETING
        });
        JTextField capacityField = new JTextField(10);
        JTextField rateField = new JTextField(10);

        // Auto-fill when type changes
        typeBox.addActionListener(e -> {
            String t = (String) typeBox.getSelectedItem();
            capacityField.setText(String.valueOf(Hall.getDefaultCapacity(t)));
            rateField.setText(String.valueOf(Hall.getDefaultRate(t)));
        });
        // Trigger once
        typeBox.setSelectedIndex(0);
        capacityField.setText(String.valueOf(Hall.getDefaultCapacity(Hall.TYPE_AUDITORIUM)));
        rateField.setText(String.valueOf(Hall.getDefaultRate(Hall.TYPE_AUDITORIUM)));

        Object[] fields = {
            "Hall Name:", nameField,
            "Hall Type:", typeBox,
            "Capacity:", capacityField,
            "Rate Per Hour (RM):", rateField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add New Hall", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Hall name is required."); return; }

            try {
                int cap = Integer.parseInt(capacityField.getText().trim());
                double rate = Double.parseDouble(rateField.getText().trim());
                String id = FileManager.generateId("H");
                Hall newHall = new Hall(id, name, (String) typeBox.getSelectedItem(), cap, rate, true);

                List<Hall> halls = FileManager.readHalls();
                halls.add(newHall);
                FileManager.writeHalls(halls);
                loadHalls();
                JOptionPane.showMessageDialog(this, "Hall added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capacity and rate must be valid numbers.");
            }
        }
    }

    /** Dialog to edit the selected hall. */
    private void showEditHallDialog() {
        int row = hallTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a hall to edit."); return; }

        String hallId = (String) tableModel.getValueAt(row, 0);
        List<Hall> halls = FileManager.readHalls();
        Hall hall = null;
        for (Hall h : halls) { if (h.getHallId().equals(hallId)) { hall = h; break; } }
        if (hall == null) return;

        JTextField nameField     = new JTextField(hall.getHallName(), 15);
        JTextField capacityField = new JTextField(String.valueOf(hall.getCapacity()), 10);
        JTextField rateField     = new JTextField(String.valueOf(hall.getRatePerHour()), 10);

        Object[] fields = {
            "Hall Name:", nameField,
            "Capacity:", capacityField,
            "Rate Per Hour (RM):", rateField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Edit Hall", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                hall.setHallName(nameField.getText().trim());
                hall.setCapacity(Integer.parseInt(capacityField.getText().trim()));
                hall.setRatePerHour(Double.parseDouble(rateField.getText().trim()));
                FileManager.writeHalls(halls);
                loadHalls();
                JOptionPane.showMessageDialog(this, "Hall updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capacity and rate must be valid numbers.");
            }
        }
    }

    /** Delete the selected hall. */
    private void deleteSelectedHall() {
        int row = hallTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a hall to delete."); return; }

        String hallId = (String) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this hall?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            List<Hall> halls = FileManager.readHalls();
            halls.removeIf(h -> h.getHallId().equals(hallId));
            FileManager.writeHalls(halls);
            loadHalls();
            JOptionPane.showMessageDialog(this, "Hall deleted.");
        }
    }

    /** Toggle the availability of the selected hall. */
    private void toggleAvailability() {
        int row = hallTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a hall first."); return; }

        String hallId = (String) tableModel.getValueAt(row, 0);
        List<Hall> halls = FileManager.readHalls();
        for (Hall h : halls) {
            if (h.getHallId().equals(hallId)) {
                h.setAvailable(!h.isAvailable());
                break;
            }
        }
        FileManager.writeHalls(halls);
        loadHalls();
    }
}
