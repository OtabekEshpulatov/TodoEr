package dev.otabek;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import java.nio.file.Paths;

public class $ {

    public static void openFileAtLine(Project project, String filePath, int line, int column) {
        // Find the virtual file from the given file path
        VirtualFile file = LocalFileSystem.getInstance().findFileByPath(Paths.get(filePath).toAbsolutePath().toString());

        if (file != null) {
            ApplicationManager.getApplication().invokeLater(() -> {
                FileEditorManager.getInstance(project).openFile(file, true);

                // Get the current editor for the file
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                if (editor != null) {
                    CaretModel caretModel = editor.getCaretModel();

                    // Move to the specific line and column
                    caretModel.moveToOffset(editor.getDocument().getLineStartOffset(line - 1) + column - 1);

                    editor.getScrollingModel().scrollToCaret(ScrollType.CENTER);
                }
            });
        } else {
            System.out.println("File not found: " + filePath);
        }
    }
}
