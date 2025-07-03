package application.views.ToDoList;

import java.util.List;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.TextField;

public class NoteGroup extends VerticalLayout {

    private H4 titleLabel = new H4();
    private VerticalLayout taskList = new VerticalLayout();

    public NoteGroup() {
        addClassName("note-group");
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        titleLabel.addClassName("note-group-title");
        add(titleLabel, taskList);

        taskList.setSpacing(false);
        taskList.setPadding(false);
        taskList.setWidthFull();
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setTasks(List<Task> tasks) {
        taskList.removeAll();

        for (Task task : tasks) {
            HorizontalLayout taskRow = new HorizontalLayout();
            taskRow.addClassName("task-row");
            taskRow.setWidthFull();
            taskRow.setAlignItems(Alignment.CENTER);
            taskRow.getStyle().set("padding", "0.3rem 0.5rem");
            taskRow.getStyle().set("border-bottom", "1px solid var(--border-color)");

            Checkbox doneCheckbox = new Checkbox();
            doneCheckbox.setValue(task.isDone());
            doneCheckbox.setReadOnly(true);

            Span description = new Span(task.getDescription());
            description.getStyle().set("flex-grow", "1");
            description.getStyle().set("font-weight", task.isDone() ? "normal" : "600");
            description.getStyle().set("text-decoration", task.isDone() ? "line-through" : "none");
            description.getStyle().set("color", task.isDone() ? "var(--text-secondary)" : "var(--text-primary)");

            Span priority = new Span();
            priority.getElement().setProperty("innerHTML", getPriorityIcon(task.getPriority()));
            priority.getStyle().set("min-width", "60px");
            priority.getStyle().set("text-align", "center");

            Span overdue = new Span();
            if (task.isOverdue()) {
                overdue.setText("⚠️");
                overdue.getElement().setProperty("title", "Überfällig");
                overdue.getStyle().set("color", "var(--error-color)");
                overdue.getStyle().set("margin-left", "0.5rem");
            }

            taskRow.add(doneCheckbox, description, overdue, priority);

            taskList.add(taskRow);
        }
    }

    private String getPriorityIcon(String priority) {
        switch (priority.toLowerCase()) {
            case "hoch":
                return "🔴 Hoch";
            case "mittel":
                return "🟠 Mittel";
            case "niedrig":
                return "🟢 Niedrig";
            default:
                return "";
        }
    }

    // Statische innere Klasse Task
    public static class Task {
        private boolean done;
        private String description;
        private String priority;
        private boolean overdue;

        public Task(boolean done, String description, String priority) {
            this(done, description, priority, false);
        }

        public Task(boolean done, String description, String priority, boolean overdue) {
            this.done = done;
            this.description = description;
            this.priority = priority;
            this.overdue = overdue;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public boolean isOverdue() {
            return overdue;
        }

        public void setOverdue(boolean overdue) {
            this.overdue = overdue;
        }
    }
}
