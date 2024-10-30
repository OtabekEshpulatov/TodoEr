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

    private static final int FILES_PER_TASK_THRESHOLD = 10;

    // list of files paths. if we find any todos in any file, we create todoItem object
    private final List<String> filePaths;

    private final TodoCollectorHelper collectorHelper;

    public TodoCollectorTask(List<String> filePaths, TodoCollectorHelper collectorHelper) {
        this.filePaths = filePaths;
        this.collectorHelper = collectorHelper;
    }

    @Override
    protected Collection<TodoItemDto> compute() {
        if (filePaths.size() > FILES_PER_TASK_THRESHOLD) {
            return invokeSubtasks();
        } else {
            return processTask();
        }
    }

    private Collection<TodoItemDto> processTask() {
        LinkedList<TodoItemDto> todos = new LinkedList<>();
        for (String filePath : filePaths) {
            todos.addAll(collectTodos(filePath));
        }

        return todos;
    }

    private LinkedList<TodoItemDto> invokeSubtasks() {
        int overallFilesCount = filePaths.size();
        List<String> filesFirstPart = this.filePaths.subList(0, overallFilesCount / 2);
        List<String> filesSecondPart = this.filePaths.subList(overallFilesCount / 2, overallFilesCount);

        var firstTask = new TodoCollectorTask(filesFirstPart, collectorHelper);
        var secondTask = new TodoCollectorTask(filesSecondPart, collectorHelper);

        invokeAll(firstTask, secondTask);

        LinkedList<TodoItemDto> todos = new LinkedList<>();
        todos.addAll(firstTask.join());
        todos.addAll(secondTask.join());
        return todos;
    }


    private Collection<TodoItemDto> collectTodos(String filePath) {
        LinkedList<TodoItemDto> todos = new LinkedList<>();

        try {
            List<String> allLines = Files.readAllLines(Path.of(filePath));

            for (int i = 0; i < allLines.size(); i++) {
                String line = allLines.get(i).trim();
                if ($.isEmpty(line)) continue;

                if (collectorHelper.isTodo(line)) {
                    var todo = new TodoItemDto(filePath, line, i + 1, 1);
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
