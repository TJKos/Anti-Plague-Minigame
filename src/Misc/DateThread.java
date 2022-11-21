package Misc;

import Renderers.DateRenderer;
import java.time.LocalDate;

public class DateThread extends Thread {
    private final Object object;
    private LocalDate date;
    private LocalDate firstDate;
    private boolean updateDate;
    private DateRenderer dateRenderer;

    public DateThread(Object object) {
        date = LocalDate.now();
        firstDate = date;
        updateDate = true;
        this.object = object;
    }

    @Override
    public void run() {
        while (updateDate) {

            synchronized (object) {
                date = date.plusDays(1);
                dateRenderer.setValue(date, false);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getFirstDate() {
        return firstDate;
    }

    public void setUpdateDate(boolean updateDate) {
        this.updateDate = updateDate;
    }

    public void setDateRenderer(DateRenderer dateRenderer){
        this.dateRenderer = dateRenderer;
    }
}
