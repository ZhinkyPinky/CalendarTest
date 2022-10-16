import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CalendarV2 {
    LocalDate[] vacationDays = new LocalDate[20];
    HashMap<LocalDate, Work> calendarDates = new HashMap<>();

    //If empty spot reduce daysToShuffle by 1?
    public CalendarV2 addWork(Work work) {
        int daysToAdd = work.days;
        int daysToShuffle = daysToAdd;
        int startDate = work.StartDate.getDayOfYear();

        for (int i = 0; i < daysToAdd; i++) {
            if (calendarDates.get(LocalDate.ofYearDay(LocalDate.now().getYear(), startDate + i)) != null)
                shuffleForward(startDate + i, daysToShuffle);
            else daysToShuffle--;

            calendarDates.put(LocalDate.ofYearDay(LocalDate.now().getYear(), startDate + i), work);
        }

        return this;
    }

    public void shuffleForward(int date, int daysToShuffle) {
        if (calendarDates.containsKey(LocalDate.ofYearDay(LocalDate.now().getYear(), date + daysToShuffle)))
            shuffleForward((date) + daysToShuffle, 1);

        /*
        System.out.println(LocalDate.ofYearDay(LocalDate.now().getYear(), date));
        System.out.println(
                calendarDates.get(LocalDate.ofYearDay(LocalDate.now().getYear(), date)).name + ", " +
                        calendarDates.get(LocalDate.ofYearDay(LocalDate.now().getYear(), date)).StartDate + ", " +
                        calendarDates.get(LocalDate.ofYearDay(LocalDate.now().getYear(), date)).days);

         */
        calendarDates.put(
                LocalDate.ofYearDay(LocalDate.now().getYear(), date + daysToShuffle),
                calendarDates.get(LocalDate.ofYearDay(LocalDate.now().getYear(), date)));
    }
}

