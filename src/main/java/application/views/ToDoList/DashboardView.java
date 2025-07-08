package application.views.ToDoList;

import application.database.*;
import application.views.BasicLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import application.views.components.DonutChart;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route("Dashboard")
@PageTitle("Dashboard")
public class DashboardView extends BasicLayout {

    public DashboardView() {
        String title = "Dashboard";
        setTitle(title);
        DataLoader dataLoader = new DataLoader();
        List<User> users = dataLoader.getUsers();

        HorizontalLayout container = new HorizontalLayout();
        container.addClassName("container");
        container.setSizeFull();


        // Main Content
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.setSizeFull();
        mainContent.addClassName("main-content");
        mainContent.getElement().getStyle().set("background-color", "var(--card-background)");
        mainContent.getElement().getStyle().set("padding", "1rem 2rem");
        mainContent.getElement().getStyle().set("box-sizing", "border-box");
        mainContent.getElement().getStyle().set("overflow-y", "auto");

//        if (!users.isEmpty()) {
//            User firstUser = users.get(0);
//            List<Todo> userTodos = dataLoader.getTodosForUser(firstUser);
//
//            long erledigtCount = userTodos.stream().filter(Todo::isDone).count();
//            long ueberfaelligCount = userTodos.stream()
//                    .filter(todo -> !todo.isDone() && todo.getDueDate() != null && todo.getDueDate().isBefore(java.time.LocalDateTime.now()))
//                    .count();
//            long offenCount = userTodos.size() - erledigtCount - ueberfaelligCount;
//
//            // Statuszahlen vertikal untereinander
//            VerticalLayout statusNumbers = new VerticalLayout();
//            statusNumbers.addClassName("status-numbers");
//            statusNumbers.getStyle().set("padding", "1rem");
//            statusNumbers.getStyle().set("background-color", "var(--tertiary-color)");
//            statusNumbers.getStyle().set("border-radius", "6px");
//            statusNumbers.getStyle().set("min-width", "120px");
//            statusNumbers.getStyle().set("box-sizing", "border-box");
//            statusNumbers.getStyle().set("margin-right", "1rem");  // Kleiner Abstand rechts
//
//            Paragraph erledigt = new Paragraph("🟢 Erledigt: " + erledigtCount);
//            erledigt.addClassName("erledigt");
//            erledigt.getStyle().set("font-weight", "600");
//            erledigt.getStyle().set("font-size", "1.1rem");
//            erledigt.getStyle().set("margin", "0.15rem 0");
//
//            Paragraph ueberfaellig = new Paragraph("⚠️ Überfällig: " + ueberfaelligCount);
//            ueberfaellig.addClassName("ueberfaellig");
//            ueberfaellig.getStyle().set("font-weight", "600");
//            ueberfaellig.getStyle().set("font-size", "1.1rem");
//            ueberfaellig.getStyle().set("margin", "0.15rem 0");
//
//            Paragraph offen = new Paragraph("🔵 Offen: " + offenCount);
//            offen.addClassName("offen");
//            offen.getStyle().set("font-weight", "600");
//            offen.getStyle().set("font-size", "1.1rem");
//            offen.getStyle().set("margin", "0.15rem 0");
//
//            statusNumbers.add(erledigt, ueberfaellig, offen);
//
//            // DonutChart größer und ohne großen Rand
//            DonutChart donutChart = new DonutChart(erledigtCount, ueberfaelligCount, offenCount);
//            donutChart.getStyle().set("margin", "0"); // kein margin
//            donutChart.getStyle().set("max-width", "350px");
//            donutChart.getStyle().set("min-width", "350px");
//            donutChart.getStyle().set("height", "350px");
//
//            // Container für Statuszahlen und Chart nebeneinander, mit kleinem Abstand
//            HorizontalLayout statusAndChart = new HorizontalLayout(statusNumbers, donutChart);
//            statusAndChart.addClassName("status-and-chart");
//            statusAndChart.setWidthFull();
//            statusAndChart.setAlignItems(FlexComponent.Alignment.CENTER);
//            statusAndChart.setSpacing(false);  // kein großer Abstand
//            statusAndChart.getStyle().set("gap", "0.5rem"); // aber trotzdem etwas Lücke
//
//            // Aufgabenliste
//            VerticalLayout taskSection = new VerticalLayout();
//            taskSection.addClassName("task-section");
//            taskSection.setWidthFull();
//
//            H3 taskTitle = new H3("Aufgabenlisten");
//            taskTitle.getElement().getStyle().set("color", "var(--primary-color)");
//
//            TextField searchInput = new TextField();
//            searchInput.setPlaceholder("Suche Aufgaben...");
//            searchInput.setWidthFull();
//            searchInput.getElement().setAttribute("type", "search");
//            searchInput.addClassName("search-bar");
//            searchInput.getElement().getStyle().set("margin-bottom", "1rem");
//            searchInput.getElement().getStyle().set("padding", "0.5rem 0.75rem");
//            searchInput.getElement().getStyle().set("border-radius", "4px");
//            searchInput.getElement().getStyle().set("border", "1px solid #444");
//            searchInput.getElement().getStyle().set("background-color", "var(--background-color)");
//            searchInput.getElement().getStyle().set("box-sizing", "border-box");
//
//            taskSection.add(taskTitle, searchInput);
//
//            Map<TodoList, List<Todo>> todosByList = dataLoader.getTodosGroupedByListForUser(firstUser);
//
//            for (Map.Entry<TodoList, List<Todo>> entry : todosByList.entrySet()) {
//                TodoList list = entry.getKey();
//                List<Todo> todos = entry.getValue();
////                List<NoteGroup.Task> tasks = convertTodosToNoteTasks(todos);
//
//                NoteGroup noteGroup = new NoteGroup();
//                noteGroup.setTitle("📋 " + list.getName());
////                noteGroup.setTasks(tasks);
//
//                taskSection.add(noteGroup);
//            }
//
//            mainContent.add(statusAndChart, taskSection);
//            mainContent.expand(taskSection);
//
//        }
    }
}

//    private Anchor createSidebarLink(String text, boolean active) {
//        Anchor link = new Anchor("#", text);
//        link.getElement().getClassList().add("sidebar-link");
//        if (active) {
//            link.getElement().getClassList().add("active");
//        }
//        return link;
//    }
//
//    private List<NoteGroup.Task> convertTodosToNoteTasks(List<Todo> todos) {
//        return todos.stream().map(todo -> {
//            boolean done = todo.isDone();
//            String description = todo.getDescription();
//            String priority = todo.getPriority() != null ? todo.getPriority().name().toLowerCase() : "niedrig";
//            boolean overdue = !done && todo.getDueDate() != null && todo.getDueDate().isBefore(java.time.LocalDateTime.now());
//            return new NoteGroup.Task(done, description, priority, overdue);
//        }).collect(Collectors.toList());
//    }
//}
