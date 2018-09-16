package musicbeans.entities;

import android.support.annotation.NonNull;

import java.util.Date;

public abstract class Posts implements Comparable {

    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int compareTo(@NonNull Posts o) {
        return getDate().compareTo(o.getDate());
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
