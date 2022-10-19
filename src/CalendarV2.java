import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        calendarDates.entrySet().removeIf(item -> item.getValue() == work);

        LocalDate[] lastDateMovedTo = {LocalDate.MIN};
        calendarDates.keySet().stream().sorted().forEach(date -> {
            if (date.isAfter(work.StartDate)) {
                lastDateMovedTo[0] = moveCalendarItemBackwards(calendarDates.get(date), date, lastDateMovedTo[0]);
            }
        });

        System.out.println();
    }

    public LocalDate moveCalendarItemBackwards(Work workToMove, LocalDate date, LocalDate lastDateMovedTo) {
        LocalDate dateToMoveTo = date.minusDays(Long.parseLong("1"));
        LocalDate freeSpot = date;

        //As long as
        while (true) {
            //if (calendarDates.containsKey(dateToMoveTo)) break;
            if (!dateToMoveTo.isAfter(lastDateMovedTo))
                break; //Stop if we're past the last date that a work item was moved to.
            if (workToMove.StartDate.isAfter(dateToMoveTo)) break; //Stop if start date of work being moved is reached.
            if (calendarDates.get(dateToMoveTo) == workToMove)
                break; //Stop if the work being moved is the same as the one for which the date is being checked.
            if (LocalDate.now().isEqual(date)) break; //Stop if reached today's date.

            //System.out.println("oldDate: " + dateToMove);
            //System.out.println("newDate: " + dateToMoveTo);
            dateToMoveTo = dateToMoveTo.minusDays(1L);
            if (!calendarDates.containsKey(dateToMoveTo)) {
                if (dateToMoveTo.getDayOfWeek() != DayOfWeek.SATURDAY && dateToMoveTo.getDayOfWeek() != DayOfWeek.SUNDAY) {
                    freeSpot = dateToMoveTo;
                }
            }
        }
        dateToMoveTo = dateToMoveTo.plusDays(1L);
        //System.out.println(date + "->" + dateToMoveTo);

        if (!calendarDates.containsKey(freeSpot)) {
            calendarDates.put(freeSpot, workToMove);
            calendarDates.remove(date);
            //System.out.println("boop" + dateToMoveTo);
            return freeSpot;
        }

        return lastDateMovedTo;
    }
}

