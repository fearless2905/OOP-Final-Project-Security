import java.util.ArrayList;
import java.util.List;

public class ScheduleManager {
    private List<Schedule> schedules;
    private int shiftDuration; // Durasi waktu jaga dalam satuan jam

    public ScheduleManager(int shiftDuration) {
        this.shiftDuration = shiftDuration;
        schedules = new ArrayList<>();
    }

    public void removeSchedule(Schedule scheduleToRemove) {
        schedules.remove(scheduleToRemove);
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public List<Schedule> getShiftSchedules() {
        List<Schedule> shiftSchedules = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getStartTime().getHours() == 8 && schedule.getStartTime().getMinutes() == 0) { // Ganti dengan kondisi yang sesuai
                shiftSchedules.add(schedule);
            }
        }
        return shiftSchedules;
    }
}
