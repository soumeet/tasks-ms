package io.todo.task.util;

import io.todo.task.model.Priority;
import io.todo.task.model.Status;

import java.time.OffsetDateTime;

public class CommonMethods {
    public static boolean isNotNullOrEmpty(Object o) {
        return o != null;
    }
    public static boolean isNotNullOrEmpty(String s) {
        return s != null && !"".equals(s);
    }

    public static boolean filterUsing(String value, String filter) {
        if(isNotNullOrEmpty(filter)) {
            if (isNotNullOrEmpty(value))
                return value.contains(filter);
            return false;
        }
        return true;
    }

    public static boolean filterUsing(String value, OffsetDateTime filter) {
        if(isNotNullOrEmpty(filter)) {
            if (isNotNullOrEmpty(value))
                return OffsetDateTime.parse(value).compareTo(filter) == 0;
            return false;
        }
        return true;
    }

    public static boolean filterUsing(Status value, Status filter) {
        if(isNotNullOrEmpty(filter)) {
            return value.getValue().equals(filter.getValue());
        }
        return true;
    }

    public static boolean filterUsing(Priority value, Priority filter) {
        if(isNotNullOrEmpty(filter)) {
            return value.getValue().equals(filter.getValue());
        }
        return true;
    }
}
