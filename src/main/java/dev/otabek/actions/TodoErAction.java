package dev.otabek.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import dev.otabek.components.todo.TodoItemsDialogWrapper;
import dev.otabek.dtos.TodoItemDto;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

public class TodoErAction extends AnAction {


    final static String[] SUPPORTED_EXTENSIONS = {"java", "kt"};

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

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {

        VirtualFile[] data = event.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);

        if (data != null) {
            new TodoItemsDialogWrapper(getTodos(data), event.getProject())
                    .show();
        }

    }

    private Collection<TodoItemDto> getTodos(VirtualFile[] data) {
        LinkedList<String> files = getAllSupportedFilesPath(data);

        return files.stream().map(path -> new TodoItemDto(path, path, 1, 1)).toList();
    }
}
