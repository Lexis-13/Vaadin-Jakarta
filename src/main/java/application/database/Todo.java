package application.database;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private boolean done;

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    // Ein Todo gehört genau zu einer User (Besitzer)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Ein Todo gehört zu genau einer TodoList
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_list_id")
    private TodoList todoList;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public TodoList getTodoList() { return todoList; }
    public void setTodoList(TodoList todoList) { this.todoList = todoList; }

    // equals & hashCode: Identität basiert nur auf ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todo)) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return description + " (" + (priority != null ? priority : "Keine Priorität") + ")";
    }
}
