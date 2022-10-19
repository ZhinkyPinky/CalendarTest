import java.time.LocalDate;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        CalendarV2 calendar = new CalendarV2();

        Work windows = new Work("Fönster", LocalDate.of(2022, 5, 10), 10);
        Work door = new Work("Dörr", LocalDate.of(2022, 5, 15), 5);
        Work fence = new Work("Staket", LocalDate.of(2022, 5, 23), 2);
        Work roof = new Work("Tak", LocalDate.of(2022, 5, 17), 6);

        calendar.addWork(windows)
                .addWork(roof)
                .addWork(door)
                .addWork(fence);

        printCalendarV2(calendar);
        System.out.println();

        calendar.removeWork(door);
        printCalendarV2(calendar);
        calendar.removeWork(roof);

        int year = LocalDate.now().getYear();
        int month = 0;
        int day = 0;

        //Calendar
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

        //CalendarV2
        printCalendarV2(calendar);
    }

    private static void printCalendarV2(CalendarV2 calendar) {
        calendar.calendarDates.keySet().stream().sorted().forEach(key -> {
            System.out.print(key + " : " + key.getDayOfWeek() + " : ");
            System.out.println(calendar.calendarDates.get(key).name + ", " + calendar.calendarDates.get(key).StartDate + ", " + calendar.calendarDates.get(key).days);
        });
        /*
        for (Map.Entry<LocalDate, Work> entry : calendar.calendarDates.entrySet()) {
            System.out.print(entry.getKey() + " : ");
            System.out.println(entry.getValue().name + ", " + entry.getValue().StartDate + ", " + entry.getValue().days);
        }
         */
    }


}