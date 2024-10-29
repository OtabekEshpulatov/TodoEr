package dev.otabek.services.todo_collector;

import dev.otabek.$;
import dev.otabek.dtos.TodoItemDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class TodoCollectorTask extends RecursiveTask<Collection<TodoItemDto>> {

    // list of files paths. if we find any todos in any file, we create todoItem object
    private final List<String> filePaths;
    private final TodoCollectorHelper patternHelper;

    public TodoCollectorTask(List<String> filePaths, TodoCollectorHelper patternHelper) {
        this.filePaths = filePaths;
        this.patternHelper = patternHelper;
    }

    @Override
    protected Collection<TodoItemDto> compute() {
        final int FILES_PER_TASK_THRESHOLD = 10;
        if (filePaths.size() > FILES_PER_TASK_THRESHOLD) {
            List<TodoCollectorTask> subtasks = createSubtasks();
            invokeAll(subtasks);

            LinkedList<TodoItemDto> todos = new LinkedList<>();
            for (TodoCollectorTask subtask : subtasks) {
                todos.addAll(subtask.join());
            }
            return todos;
        } else {
            return processTask();
        }
    }

    private List<TodoCollectorTask> createSubtasks() {
        int overallFilesCount = filePaths.size();
        List<String> firstPart = this.filePaths.subList(0, overallFilesCount / 2);
        List<String> secondPart = this.filePaths.subList(overallFilesCount / 2, overallFilesCount);

        TodoCollectorTask firstTask = new TodoCollectorTask(firstPart, patternHelper);
        TodoCollectorTask secondTask = new TodoCollectorTask(secondPart, patternHelper);
        return List.of(firstTask, secondTask);
    }

    private Collection<TodoItemDto> processTask() {
        LinkedList<TodoItemDto> todos = new LinkedList<>();
        for (String filePath : filePaths) {
            todos.addAll(collectTodos(filePath));
        }

        return todos;
    }

    private Collection<TodoItemDto> collectTodos(String filePath) {
        LinkedList<TodoItemDto> todos = new LinkedList<>();

        try {
            List<String> allLines = Files.readAllLines(Path.of(filePath));

            for (int i = 0; i < allLines.size(); i++) {
                String line = allLines.get(i).trim();
                if ($.isEmpty(line)) continue;

                if (patternHelper.isTodo(line)) {
                    var todo = new TodoItemDto(filePath, line, i + 1, 0);
                    todos.add(todo);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath);
            return null;
        }

        return todos;
    }
}
