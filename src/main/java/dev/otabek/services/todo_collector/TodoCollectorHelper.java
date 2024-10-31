package dev.otabek.services.todo_collector;

import com.intellij.ide.todo.TodoConfiguration;
import com.intellij.psi.search.TodoPattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TodoCollectorHelper {

    private final TodoPattern[] patterns;

    public TodoCollectorHelper() {
        this.patterns = TodoConfiguration.getInstance().getTodoPatterns();
    }


    public boolean isTodo(String str) {
        for (TodoPattern todoPattern : patterns) {
            Matcher matcher = Pattern.compile(todoPattern.getPatternString(),
                    Pattern.CASE_INSENSITIVE).matcher(str);
            boolean matchFound = matcher.find();
            if (matchFound) {
                return true;
            }
        }
        return false;
    }
}
