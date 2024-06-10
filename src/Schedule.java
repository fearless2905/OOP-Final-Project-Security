import java.util.Date;

public class Schedule {
    private Date startTime;
    private Date endTime;
    private Employee employee;

    public Schedule(Date startTime, Employee employee) {
        this.startTime = startTime;
        this.employee = employee;
    }

    public Schedule(Date startTime, Employee employee, Date endTime) {
        this(startTime, employee);
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public long getDuration() {
        return (endTime.getTime() - startTime.getTime()) / (1000 * 60); // Menghitung durasi dalam menit
    }
}
