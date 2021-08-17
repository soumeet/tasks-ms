package io.todo.task.util;

public class CommonMethods {
    public static boolean isNotNullOrEmpty(Object o) {
        return o != null;
    }

    public static boolean isNotNullOrEmpty(String s) {
        return s != null && !"".equals(s);
    }
}
