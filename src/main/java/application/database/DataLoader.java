package application.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

public class DataLoader {

    private List<User> users = new ArrayList<>();
    private List<Todo> todos = new ArrayList<>();
    private List<TodoList> todoLists = new ArrayList<>();

    private Map<Long, User> userMap = new HashMap<>();
    private Map<Long, TodoList> todoListMap = new HashMap<>();

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
                    // USER;id;username;passwordHash;email;firstName;lastName;company;department
                    String[] parts = line.split(";", -1);
                    if (parts.length >= 9) {
                        User user = new User();
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
                    } else {
                        System.err.println("❌ USER-Daten unvollständig: " + line);
                    }

                } else if (line.startsWith("TODOLIST")) {
                    // TODOLIST;id;name
                    String[] parts = line.split(";", 3);
                    if (parts.length >= 3) {
                        TodoList list = new TodoList();
                        list.setId(Long.parseLong(parts[1]));
                        list.setName(parts[2]);
                        todoLists.add(list);
                        todoListMap.put(list.getId(), list);
                    } else {
                        System.err.println("❌ Ungültiges TODOLIST-Format, zu wenige Felder: " + line);
                    }

                } else if (line.startsWith("TODO")) {
                    // TODO;id;description;done;dueDate;priority;userId;listIds
                    String[] parts = line.split(";", 8);
                    if (parts.length >= 8) {
                        Todo todo = new Todo();
                        todo.setId(Long.parseLong(parts[1]));
                        todo.setDescription(parts[2]);
                        todo.setDone(Boolean.parseBoolean(parts[3]));
                        todo.setDueDate(LocalDateTime.parse(parts[4]));
                        todo.setPriority(Priority.valueOf(parts[5]));

                        Long userId = Long.parseLong(parts[6]);
                        User user = userMap.get(userId);
                        if (user != null) {
                            todo.setUser(user);
                        }

                        List<TodoList> lists = new ArrayList<>();
                        for (String idStr : parts[7].split(",")) {
                            try {
                                long listId = Long.parseLong(idStr.trim());
                                TodoList list = todoListMap.get(listId);
                                if (list != null) {
                                    lists.add(list);
                                } else {
                                    System.err.println("⚠️ Liste mit ID " + listId + " nicht gefunden für TODO: " + todo.getDescription());
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("❌ Ungültige Listen-ID im TODO-Eintrag: " + idStr);
                            }
                        }
                        todo.setLists(lists);

                        todos.add(todo);
                    } else {
                        System.err.println("❌ Ungültiges TODO-Format, zu wenige Felder: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Fehler beim Einlesen der Datei: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public List<TodoList> getTodoLists() {
        return todoLists;
    }

    public List<Todo> getTodosForUser(User user) {
        List<Todo> result = new ArrayList<>();
        for (Todo t : todos) {
            if (t.getUser() != null && t.getUser().getId().equals(user.getId())) {
                result.add(t);
            }
        }
        return result;
    }

    public Map<TodoList, List<Todo>> getTodosGroupedByListForUser(User user) {
        Map<TodoList, List<Todo>> grouped = new LinkedHashMap<>();
        for (TodoList list : todoLists) {
            grouped.put(list, new ArrayList<>());
        }

        for (Todo todo : getTodosForUser(user)) {
            for (TodoList list : todo.getLists()) {
                grouped.computeIfAbsent(list, k -> new ArrayList<>()).add(todo);
            }
        }

        // Optional: Listen ohne Todos entfernen
        grouped.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        return grouped;
    }
}
