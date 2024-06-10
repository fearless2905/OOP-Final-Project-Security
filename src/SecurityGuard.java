public class SecurityGuard extends Employee {
    private String location;

    public SecurityGuard(String name, String location) {
        super(name);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
