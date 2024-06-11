import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private DefaultTableModel tableModel;
    private JTable scheduleTable;

    public DeleteEditor(JCheckBox checkBox, DefaultTableModel tableModel, JTable scheduleTable) {
        super(checkBox);
        this.tableModel = tableModel;
        this.scheduleTable = scheduleTable;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int response = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                int editingRow = scheduleTable.getEditingRow();
                int modelRow = scheduleTable.convertRowIndexToModel(editingRow);

                // Hapus jadwal dari scheduleManager
                ScheduleManager scheduleManager = Main.scheduleManager;
                Schedule scheduleToRemove = scheduleManager.getSchedules().get(modelRow);
                scheduleManager.removeSchedule(scheduleToRemove);

                // Hapus baris dari tabel
                tableModel.removeRow(modelRow);

                // Refresh table to reset state
                SwingUtilities.invokeLater(() -> {
                    Main.refreshTabelJadwal();
                });
            }
        }
        isPushed = false;
        return new String(label);
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    public void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
