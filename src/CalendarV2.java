import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CalendarV2 {
    LocalDate[] vacationDays = new LocalDate[20];
    ArrayList<Work> workItems = new ArrayList<>();
    HashMap<LocalDate, Work> calendarDates = new HashMap<>();

    public CalendarV2 addWork(Work work) {
        workItems.add(work);

        int daysToAdd = work.days;
        int daysToShuffleForward = daysToAdd;
        int startDate = work.StartDate.getDayOfYear();

        for (int i = 0; i < daysToAdd; i++) {
            LocalDate date = LocalDate.ofYearDay(LocalDate.now().getYear(), startDate + i);

            /*If the date where the new work is getting added already contains something the content that is already there needs
              to be shuffled forward x days decided by the daysToShuffleForward variable.*/
            if (calendarDates.get(date) != null) shuffleForward(startDate + i, daysToShuffleForward);
            else daysToShuffleForward--;/*If the spot where new work is being added is free all future work that needs to be
            shuffled forward should be shuffled forward one day less.*/

            //Add work to the specified date.
            calendarDates.put(date, work);
        }

        return this;
    }

    public void shuffleForward(int dayOfYear, int daysToShuffle) {
        LocalDate oldDate = LocalDate.ofYearDay(LocalDate.now().getYear(), dayOfYear);
        LocalDate newDate = LocalDate.ofYearDay(LocalDate.now().getYear(), dayOfYear + daysToShuffle);
        /*If the date where the work-object is trying to get shuffled to is already taken, the work-object
        that is already there should be shuffled forward one day.*/
        if (calendarDates.containsKey(newDate)) shuffleForward(dayOfYear + daysToShuffle, 1);

        /*If the date is free the work-object that is getting shuffled forward can be added. */
        calendarDates.put(newDate, calendarDates.get(oldDate));
    }

    public void removeWork(Work work) {
        LocalDate startDateOfRemoved = work.StartDate;

        calendarDates.entrySet().removeIf(item -> item.getValue() == work);

        /*
        Iterator<Map.Entry<LocalDate, Work>> iterator = calendarDates.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<LocalDate, Work> item = iterator.next();
            LocalDate key = item.getKey();
            if (key.isAfter(startDateOfRemoved)) {
                moveCalendarItemBackwards(key);
            }
        }
         */

        for (int i = 0; i < calendarDates.size(); i++) {
            LocalDate date = startDateOfRemoved.plusDays(i);
            if (calendarDates.containsKey(date)) {
                moveCalendarItemBackwards(date);

                //if (calendarDates.containsKey(date)) System.out.println(date + ": " + calendarDates.get(date).name);
            }
        }

    }

    public void moveCalendarItemBackwards(LocalDate date) {
        LocalDate dateToMove = date;
        LocalDate newDate = date.minusDays(Long.parseLong("1"));

        while (!calendarDates.containsKey(newDate)) {

            /*
            System.out.println("oldDate: " + dateToMove);
            System.out.println("newDate: " + newDate);
             */
            calendarDates.put(newDate, calendarDates.get(dateToMove));
            calendarDates.remove(dateToMove);

            dateToMove = newDate;
            newDate = newDate.minusDays(Long.parseLong("1"));
        }
    }
}

