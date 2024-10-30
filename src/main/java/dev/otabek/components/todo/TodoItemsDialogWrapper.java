package dev.otabek.components.todo;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBScrollPane;
import dev.otabek.$;
import dev.otabek.components.TextLikeButton;
import dev.otabek.components.TruncatedLabel;
import dev.otabek.dtos.TodoItemDto;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
        JComponent component;
        if (items.isEmpty()) {
            component = new TodoNoItemsToShowComponent();
        } else {
            JBScrollPane scrollPane = new JBScrollPane(new TodoItemsPanel());
            scrollPane.setBorder(null);
            component = scrollPane;
        }

        Box box = Box.createHorizontalBox();
        box.add(component);
        box.setPreferredSize(new Dimension(700, 600));

        return box;
    }


    private class TodoItemsPanel extends JPanel {

        public TodoItemsPanel() {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            build();
        }

        Box getBox(TodoItemDto item, Project project) {
            TruncatedLabel todoTextComponent = new TruncatedLabel(item.text(), 96);
            TextLikeButton viewMoreComponent = new TextLikeButton("view");

            viewMoreComponent.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    $.openFileAtLine(project, item.classPath(), item.rowNumber(), item.columnNumber());

                    TodoItemsDialogWrapper.super.close(OK_EXIT_CODE);
                }
            });


            var horizontalBox = Box.createHorizontalBox();
            horizontalBox.add(todoTextComponent);
            horizontalBox.add(Box.createHorizontalGlue());
            horizontalBox.add(viewMoreComponent);
            return horizontalBox;
        }

        private void build() {
            for (TodoItemDto item : items) {
                var horizontalBox = getBox(item, project);
                this.add(horizontalBox);
            }
        }

    }

}
