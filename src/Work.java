import java.time.LocalDate;

public class Work {
    String name;
    LocalDate StartDate;
    int days;

    public Work(String name, LocalDate startDate, int days) {
        this.name = name;
        StartDate = startDate;
        this.days = days;
    }
}
