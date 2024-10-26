package dev.otabek.components.todo;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import dev.otabek.dtos.TodoItemDto;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

public class TodoItemsDialogWrapper extends DialogWrapper {


    private final Collection<TodoItemDto> items;
    private final Project project;

    public TodoItemsDialogWrapper(Collection<TodoItemDto> items, Project project) {
        super(true);
        if (items == null) throw new NullPointerException("Todo items cannot be null");
        if (project == null) throw new NullPointerException("Project cannot be null");

        this.items = items;
        this.project = project;

        configure();
    }

    private void configure() {
        setTitle("Your Todos");
        super.init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        if (items.isEmpty()) {
            return new TodoNoItemsToShowComponent();
        } else {
            return new TodoItemsComponent(items, project);
        }
    }


    @Override
    public boolean isResizable() {
        return true;
    }


}
