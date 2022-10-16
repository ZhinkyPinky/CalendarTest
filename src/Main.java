import java.time.LocalDate;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        CalendarV2 calendar = new CalendarV2();

        Work windows = new Work("Fönster", LocalDate.of(2022, 5, 10), 10);
        Work door = new Work("Dörr", LocalDate.of(2022, 5, 15), 5);
        Work fence = new Work("Staket", LocalDate.of(2022, 5, 21), 2);
        Work roof = new Work("Tak", LocalDate.of(2022, 5, 25), 2);

        calendar.addWork(windows)
                .addWork(door)
                .addWork(fence)
                .addWork(roof);


        int year = LocalDate.now().getYear();
        int month = 0;
        int day = 0;
        /*
        for (int i = 0; i < calendar.calendarDates.length; i++) {
            System.out.print(LocalDate.ofYearDay(2022, i + 1) + " : ");
            if (calendar.calendarDates[i] != null) {
                System.out.println(calendar.calendarDates[i].name + " : " + calendar.calendarDates[i].StartDate + " : " + calendar.calendarDates[i].days);
            } else {
                System.out.println("empty");
            }
        }
         */

        for (Map.Entry<LocalDate, Work> entry : calendar.calendarDates.entrySet()) {
            System.out.print(entry.getKey() + " : ");
            if (entry.getValue() != null)
                System.out.println(entry.getValue().name + ", " + entry.getValue().StartDate + ", " + entry.getValue().days);
        }

        System.out.println(calendar.calendarDates);
    }
}