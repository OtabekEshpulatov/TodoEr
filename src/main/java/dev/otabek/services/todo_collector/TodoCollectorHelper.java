package dev.otabek.services.todo_collector;

import java.util.regex.Pattern;

public class TodoCollectorHelper {

    private final String[] todoPatterns;

    public TodoCollectorHelper(String[] todoPatterns) {
        this.todoPatterns = todoPatterns;
    }


    public boolean isTodo(String str) {
        for (String pattern : todoPatterns) {
            if (Pattern.compile(pattern).matcher(str).find()) {
                return true;
            }
        }
        return false;
    }
}
