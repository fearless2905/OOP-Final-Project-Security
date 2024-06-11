import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    static ScheduleManager scheduleManager = new ScheduleManager(8);
    static DefaultTableModel tableModel;
    static JTable scheduleTable;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Jadwal Tugas Security UPN Veteran Jawa Timur");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);
            frame.setLayout(new BorderLayout());

            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton addButton = new JButton("Tambahkan Jadwal");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ScheduleManagerUI.tambahJadwalKustom(scheduleManager);
                    ScheduleTableManager.refreshTable(scheduleManager);
                }
            });
            topPanel.add(addButton);

            JPanel centerPanel = new JPanel(new BorderLayout());
            scheduleTable = new JTable();
            tableModel = new DefaultTableModel(new Object[]{"Nama", "Lokasi", "Durasi (menit)", "Waktu Mulai", "Waktu Selesai", "Hapus"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 5; // Hanya kolom "Hapus" yang bisa diedit (berisi tombol)
                }
            };
            scheduleTable.setModel(tableModel);
            
            // Tambahkan renderer dan editor untuk tombol hapus
            TableColumn column = scheduleTable.getColumnModel().getColumn(5);
            column.setCellRenderer(new ButtonRenderer());
            column.setCellEditor(new ButtonEditor(new JCheckBox(), scheduleTable));

            JScrollPane scrollPane = new JScrollPane(scheduleTable);
            centerPanel.add(scrollPane, BorderLayout.CENTER);

            ScheduleTableManager.setTableModel(tableModel);
            ScheduleTableManager.setScheduleTable(scheduleTable);

            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(centerPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }

    public static void refreshTabelJadwal() {
    }
}

// Renderer untuk tombol hapus
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "Hapus" : value.toString());
        return this;
    }
}

// Editor untuk tombol hapus
class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                int selectedRow = table.convertRowIndexToModel(table.getEditingRow());
                ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "Hapus" : value.toString();
        button.setText(label);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return new String(label);
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
