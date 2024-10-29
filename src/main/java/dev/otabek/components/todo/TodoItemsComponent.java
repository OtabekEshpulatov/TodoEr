package dev.otabek.components.todo;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import dev.otabek.$;
import dev.otabek.components.TextLikeButton;
import dev.otabek.components.TruncatedLabel;
import dev.otabek.dtos.TodoItemDto;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collection;

public class TodoItemsComponent extends JBScrollPane {


    public TodoItemsComponent(Collection<TodoItemDto> items, Project project) {
        super(new TodoItemsPanel(items, project));
        setBorder(null);
    }


    private static class TodoItemsPanel extends JPanel {

        private final Collection<TodoItemDto> items;
        private final Project project;

        public TodoItemsPanel(Collection<TodoItemDto> items, Project project) {
            this.items = items;
            this.project = project;

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            build();
        }

        @NotNull
        static Box getBox(TodoItemDto item, Project project) {
            TruncatedLabel todoTextComponent = new TruncatedLabel(item.text(), 96);
            TextLikeButton viewMoreComponent = new TextLikeButton("view");

            viewMoreComponent.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    $.openFileAtLine(project, item.classPath(), item.rowNumber(), item.columnNumber());
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
