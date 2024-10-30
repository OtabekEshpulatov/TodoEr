package dev.otabek.services.todo_collector;

import com.intellij.ide.todo.TodoConfiguration;
import com.intellij.psi.search.TodoPattern;

import java.util.regex.Pattern;

public class TodoCollectorHelper {

    private final TodoPattern[] patterns;

    public TodoCollectorHelper() {
        this.patterns = TodoConfiguration.getInstance().getTodoPatterns();
    }


    public boolean isTodo(String str) {
        for (TodoPattern todoPattern : patterns) {
            String pattern = todoPattern.getPatternString();
            boolean matchFound = Pattern.compile(pattern).matcher(str).find();
            if (matchFound) {
                return true;
            }
        }
        return false;
    }
}
