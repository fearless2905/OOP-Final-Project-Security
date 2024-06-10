import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ScheduleTableManager {
    private static DefaultTableModel tableModel;
    private static JTable scheduleTable;

    public static void setTableModel(DefaultTableModel model) {
        tableModel = model;
    }

    public static void setScheduleTable(JTable table) {
        scheduleTable = table;
    }

    @SuppressWarnings("deprecation")
    public static void refreshTable(ScheduleManager scheduleManager) {
        tableModel.setRowCount(0); // Menghapus semua baris yang ada sebelumnya di tabel

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", new Locale("id", "ID"));
        for (Schedule schedule : scheduleManager.getSchedules()) {
            String startTimeFormatted = dateFormat.format(schedule.getStartTime());
            String endTimeFormatted = dateFormat.format(schedule.getEndTime());
            Object[] rowData = {
                    schedule.getEmployee().getName(),
                    ((SecurityGuard) schedule.getEmployee()).getLocation(),
                    schedule.getDuration(),
                    startTimeFormatted,
                    endTimeFormatted,
                    "Hapus"
            };
            tableModel.addRow(rowData); // Menambahkan baris data ke model tabel
        }

        // Menambahkan aksi untuk tombol hapus
        scheduleTable.getColumn("Hapus").setCellRenderer(new DeleteRenderer());
        scheduleTable.getColumn("Hapus").setCellEditor(new DeleteEditor(new JCheckBox(), tableModel, scheduleTable));
    }
}
