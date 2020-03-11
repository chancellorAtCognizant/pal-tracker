package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    Map<Long, TimeEntry> mapEntries;
    Long lastId = 0L;

    public TimeEntry create(TimeEntry timeEntry) {
        if (this.mapEntries == null) {
            this.mapEntries = new HashMap<Long, TimeEntry>();
        }
        this.lastId = this.lastId + 1L;
        timeEntry.setId(this.lastId);
        this.mapEntries.put(this.lastId, timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long id) {
        if (this.mapEntries == null) {
            return null;
        }
        return this.mapEntries.get(id);
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (this.mapEntries == null) {
            this.mapEntries = new HashMap<Long, TimeEntry>();
        }
        timeEntry.setId(id);
        if (this.mapEntries.containsKey(id)) {
            this.mapEntries.replace(id, timeEntry);
            return timeEntry;
        } else {
            return null;
        }
    }

    public List<TimeEntry> list() {
        List<TimeEntry> list = new ArrayList<TimeEntry>();
        if (this.mapEntries != null && this.mapEntries.size() > 0) {
            Set<Long> set = this.mapEntries.keySet();
            for (Long s : set) {
                list.add(this.mapEntries.get(s));
            }
        }
        return list;
    }

    public Boolean delete(long id) {
        if (this.mapEntries != null && this.mapEntries.containsKey(id)) {
            this.mapEntries.remove(id);
            return true;
        }
        return false;
    }
}
