import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class ScheduleManagerUI {

    @SuppressWarnings("deprecation")
    public static void tambahJadwalKustom(ScheduleManager scheduleManager) {
        String guardName = JOptionPane.showInputDialog("Masukkan Nama:");
        if (guardName == null || guardName.isEmpty()) {
            return; // membatalkan input nama
        }
        String location = JOptionPane.showInputDialog("Masukkan lokasi Berjaga :");
        if (location == null || location.isEmpty()) {
            return; // membatalkan input lokasi
        }

        String durationInput = JOptionPane.showInputDialog("Masukkan durasi (dalam menit):");
        int duration = 0;
        try {
            duration = Integer.parseInt(durationInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Durasi tidak valid. Harap masukkan nomor yang valid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mendapatkan waktu saat ini
        Date now = new Date();

        // Membuat model waktu untuk JSpinner
        SpinnerDateModel timeModel = new SpinnerDateModel(now, null, null, Calendar.MINUTE);
        JSpinner startTimeSpinner = new JSpinner(timeModel);

        // Menambahkan edit agar waktu bisa diubah
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(timeEditor);

        // Membuat model tanggal untuk JSpinner
        SpinnerDateModel dateSpinnerModel = new SpinnerDateModel(now, null, null, Calendar.DAY_OF_MONTH);
        JSpinner dateSpinner = new JSpinner(dateSpinnerModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "EEEE, dd MMMM yyyy");
        dateSpinner.setEditor(dateEditor);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Mulai Pukul :"));
        panel.add(startTimeSpinner);
        panel.add(new JLabel("")); // Memberi jarak
        panel.add(new JLabel("")); // Memberi jarak
        panel.add(new JLabel("Tanggal Mulai :"));
        panel.add(dateSpinner);
        panel.add(new JLabel("")); // Memberi jarak
        panel.add(new JLabel("")); // Memberi jarak

        int option = JOptionPane.showConfirmDialog(null, panel, "Waktu Mulai", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            Date startTime = (Date) startTimeSpinner.getValue();
            Date startDate = (Date) dateSpinner.getValue();

            // Set tanggal mulai yang dimasukkan pengguna ke waktu yang dipilih pada Jspinner
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.set(Calendar.HOUR_OF_DAY, startTime.getHours());
            calendar.set(Calendar.MINUTE, startTime.getMinutes());
            startTime = calendar.getTime();
            //
            // Memperoleh waktu akhir dari input waktu mulai
            calendar.add(Calendar.MINUTE, duration);
            Date endTime = calendar.getTime();

            // Menambahkan jadwal
            Employee employee = new SecurityGuard(guardName, location);
            scheduleManager.addSchedule(new Schedule(startTime, employee, endTime));
        }
    }

    public static void editJadwalKustom(ScheduleManager scheduleManager, String name, String location, int duration,
            String startTimeStr, int row) {
    }
}
