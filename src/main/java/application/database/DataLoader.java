package application.database;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DataLoader {

    private final List<User> users = new ArrayList<>();
    private final List<Todo> todos = new ArrayList<>();
    private final List<TodoList> todoLists = new ArrayList<>();

    private final Map<Long, User> userMap = new HashMap<>();
    private final Map<Long, TodoList> todoListMap = new HashMap<>();
    private final Map<Long, Todo> todoMap = new HashMap<>();

    public DataLoader() {
        loadData();
    }

    private void loadData() {
        Path path = Path.of("data.txt");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("USER")) {
                    parseUser(line);
                } else if (line.startsWith("TODOLIST")) {
                    parseTodoList(line);
                } else if (line.startsWith("TODO")) {
                    parseTodo(line);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Fehler beim Einlesen der Datei: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void parseUser(String line) {
        String[] parts = line.split(";", -1);
        if (parts.length >= 9) {
            User user = new User();
            try {
                user.setId(Long.parseLong(parts[1]));
                user.setUsername(parts[2]);
                user.setPasswordHash(parts[3]);
                user.setEmail(parts[4]);
                user.setFirstName(parts[5]);
                user.setLastName(parts[6]);
                user.setCompany(parts[7]);
                user.setDepartment(parts[8]);
                users.add(user);
                userMap.put(user.getId(), user);
            } catch (NumberFormatException e) {
                System.err.println("❌ Ungültige Benutzer-ID: " + parts[1]);
            }
        } else {
            System.err.println("❌ USER-Daten unvollständig: " + line);
        }
    }

    private void parseTodoList(String line) {
        String[] parts = line.split(";", 3);
        if (parts.length >= 3) {
            try {
                TodoList list = new TodoList();
                list.setId(Long.parseLong(parts[1]));
                list.setName(parts[2]);
                todoLists.add(list);
                todoListMap.put(list.getId(), list);
            } catch (NumberFormatException e) {
                System.err.println("❌ Ungültige TodoList-ID: " + parts[1]);
            }
        } else {
            System.err.println("❌ Ungültiges TODOLIST-Format: " + line);
        }
    }

    private void parseTodo(String line) {
        String[] parts = line.split(";", 8);
        if (parts.length >= 8) {
            try {
                Todo todo = new Todo();
                todo.setId(Long.parseLong(parts[1]));
                todo.setDescription(parts[2]);
                todo.setDone(Boolean.parseBoolean(parts[3]));

                // dueDate kann null sein -> Prüfen
                if (!parts[4].isBlank()) {
                    todo.setDueDate(LocalDateTime.parse(parts[4]));
                }

                try {
                    todo.setPriority(Priority.valueOf(parts[5].toUpperCase()));
                } catch (IllegalArgumentException e) {
                    todo.setPriority(Priority.NIEDRIG);
                }

                long todoListId = Long.parseLong(parts[6]);
                TodoList list = todoListMap.get(todoListId);
                todo.setTodoList(list);

                long userId = Long.parseLong(parts[7]);
                User user = userMap.get(userId);
                todo.setUser(user);

                todos.add(todo);
            } catch (Exception e) {
                System.err.println("❌ Fehler beim Parsen TODO: " + line);
                e.printStackTrace();
            }
        } else {
            System.err.println("❌ TODO-Daten unvollständig: " + line);
        }
    }
    public synchronized void addTodoList(TodoList list) {
        if (list == null) {
            throw new IllegalArgumentException("TodoList oder ID darf nicht null sein");
        }
        if (todoListMap.containsKey(list.getId())) {
            throw new IllegalArgumentException("TodoList mit ID " + list.getId() + " existiert bereits");
        }
        todoLists.add(list);
        todoListMap.put(list.getId(), list);
    }

    // Öffentliche Zugriffs-Methoden

    public List<User> getUsers() {
        return List.copyOf(users);
    }

    public List<Todo> getTodosForUser(User user) {
        if (user == null) return List.of();
        return todos.stream()
                .filter(todo -> todo.getUser() != null && todo.getUser().equals(user))
                .toList();
    }

    public Map<TodoList, List<Todo>> getTodosGroupedByListForUser(User user) {
        if (user == null) return Map.of();
        return todos.stream()
                .filter(todo -> todo.getUser() != null && todo.getUser().equals(user))
                .collect(Collectors.groupingBy(Todo::getTodoList));
    }
}
