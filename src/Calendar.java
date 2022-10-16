import java.time.LocalDate;

public class Calendar {
    LocalDate[] vacationDays = new LocalDate[20];
    Work[] calendarDates = new Work[365];

    //If empty spot reduce daysToShuffle by 1?
    public Calendar addWork(Work work) {
        int daysToAdd = work.days;
        int daysToShuffle = daysToAdd;
        int startDate = work.StartDate.getDayOfYear() - 1;

        for (int i = 0; i < daysToAdd; i++) {
            if (calendarDates[startDate + i] != null) shuffleForward(startDate + i, daysToShuffle);
            else daysToShuffle--;
            calendarDates[startDate + i] = work;
        }

        return this;
    }

    public void shuffleForward(int date, int daysToShuffle) {
        if (calendarDates[date + daysToShuffle] != null) {
            shuffleForward((date) + daysToShuffle, 1);
        }

        calendarDates[date + daysToShuffle] = calendarDates[date];
    }
}

