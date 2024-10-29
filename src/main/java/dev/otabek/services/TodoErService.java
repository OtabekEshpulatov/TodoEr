package dev.otabek.services;


import com.intellij.openapi.vfs.VirtualFile;
import dev.otabek.dtos.TodoItemDto;

import java.util.Collection;

public interface TodoErService {


    Collection<TodoItemDto> getTodos(VirtualFile[] virtualFiles);
}
