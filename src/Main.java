import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
                    return column == 5; // Tombol Hapus
                }
            };
            scheduleTable.setModel(tableModel);
            
            //Menambahkan renderer dan editor untuk tombol hapus
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