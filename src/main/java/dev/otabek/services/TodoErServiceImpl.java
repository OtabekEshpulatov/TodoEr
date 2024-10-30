package dev.otabek.services;

import com.intellij.openapi.vfs.VirtualFile;
import dev.otabek.dtos.TodoItemDto;
import dev.otabek.services.todo_collector.TodoCollectorHelper;
import dev.otabek.services.todo_collector.TodoCollectorTask;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;

public class TodoErServiceImpl implements TodoErService {

    final static String[] SUPPORTED_EXTENSIONS = {"java", "kt"};

    private static TodoErServiceImpl service;

    private static @NotNull LinkedList<String> getAllSupportedFilesPath(VirtualFile[] data) {
        LinkedList<String> files = new LinkedList<>();
        for (VirtualFile datum : data) {
            if (datum.isDirectory()) {
                Collection<File> childFiles = FileUtils.listFiles(new File(datum.getPath()), SUPPORTED_EXTENSIONS, true);
                for (File childFile : childFiles) {
                    files.add(childFile.getAbsolutePath());
                }
            } else {
                files.add(datum.getPath());
            }
        }
        return files;
    }

    public static TodoErServiceImpl getInstance() {
        if (service == null) {
            service = new TodoErServiceImpl();
        }
        return service;
    }

    @Override
    public Collection<TodoItemDto> getTodos(VirtualFile[] data) {
        if (data == null) return null;
        LinkedList<String> filePaths = getAllSupportedFilesPath(data);

        var forkJoinPool = ForkJoinPool.commonPool();
        var todoHelper = new TodoCollectorHelper();

        System.out.printf("n.of files to check for todos: %d %n", filePaths.size());
        var collectorTask = new TodoCollectorTask(filePaths, todoHelper);

        forkJoinPool.execute(collectorTask);


        return collectorTask.join();
    }

}
