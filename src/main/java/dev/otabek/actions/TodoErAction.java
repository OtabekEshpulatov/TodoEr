package dev.otabek.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import dev.otabek.components.todo.TodoItemsDialogWrapper;
import dev.otabek.dtos.TodoItemDto;
import dev.otabek.services.TodoErService;
import dev.otabek.services.TodoErServiceImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class TodoErAction extends AnAction {


    private final TodoErService service = TodoErServiceImpl.getInstance();

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        VirtualFile[] data = event.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        if (data != null && project != null) {
            long started = System.currentTimeMillis();
            Collection<TodoItemDto> todos = service.getTodos(data);
            long ended = System.currentTimeMillis();

            System.out.printf("collecting todos took %d ms %n", ended - started);
            new TodoItemsDialogWrapper(todos, project).show();
        }

    }
}
