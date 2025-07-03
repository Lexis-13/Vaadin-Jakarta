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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route("")
@PageTitle("Dashboard")
public class DashboardView extends BasicLayout {

    public DashboardView() {
        DataLoader dataLoader = new DataLoader();
        List<User> users = dataLoader.getUsers();

        HorizontalLayout container = new HorizontalLayout();
        container.addClassName("container");
        container.setSizeFull();

        // Sidebar
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.addClassName("sidebar");
        sidebar.setWidth("250px");
        sidebar.setPadding(true);
        sidebar.setSpacing(false);

        H2 sidebarTitle = new H2("Dein Dashboard");
        sidebarTitle.getElement().getStyle().set("color", "var(--primary-color)");

        Nav nav = new Nav();
        nav.add(createSidebarLink("Übersicht", true));
        nav.add(createSidebarLink("Aufgaben", false));
        nav.add(createSidebarLink("Kalender", false));
        nav.add(createSidebarLink("Einstellungen", false));

        sidebar.add(sidebarTitle, nav);

        // Main Content
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.setSizeFull();
        mainContent.addClassName("main-content");
        mainContent.getElement().getStyle().set("background-color", "var(--card-background)");
        mainContent.getElement().getStyle().set("padding", "1rem 2rem");
        mainContent.getElement().getStyle().set("box-sizing", "border-box");
        mainContent.getElement().getStyle().set("overflow-y", "auto");

        // Status Box
        HorizontalLayout statusBox = new HorizontalLayout();
        statusBox.addClassName("status-box");
        statusBox.getStyle().set("background-color", "var(--tertiary-color)");
        statusBox.getStyle().set("padding", "1rem");
        statusBox.getStyle().set("border-radius", "6px");
        statusBox.getStyle().set("margin-bottom", "1.5rem");
        statusBox.setWidthFull();

        long erledigtCount = dataLoader.getTodos().stream().filter(Todo::isDone).count();
        long ueberfaelligCount = dataLoader.getTodos().stream()
                .filter(todo -> !todo.isDone() && todo.getDueDate() != null && todo.getDueDate().isBefore(java.time.LocalDateTime.now()))
                .count();

        Paragraph erledigt = new Paragraph("🟢 Erledigt: " + erledigtCount);
        erledigt.addClassName("erledigt");
        erledigt.getStyle().set("font-weight", "600");
        erledigt.getStyle().set("font-size", "1rem");

        Paragraph ueberfaellig = new Paragraph("⚠️ Überfällig: " + ueberfaelligCount);
        ueberfaellig.addClassName("ueberfaellig");
        ueberfaellig.getStyle().set("font-weight", "600");
        ueberfaellig.getStyle().set("font-size", "1rem");

        statusBox.add(erledigt, ueberfaellig);

        // Aufgabenliste Abschnitt
        VerticalLayout taskSection = new VerticalLayout();
        taskSection.addClassName("task-section");
        taskSection.setWidthFull();

        H3 taskTitle = new H3("Aufgabenlisten");
        taskTitle.getElement().getStyle().set("color", "var(--primary-color)");

        // Suchfeld
        TextField searchInput = new TextField();
        searchInput.setPlaceholder("Suche Aufgaben...");
        searchInput.setWidthFull();
        searchInput.getElement().setAttribute("type", "search");
        searchInput.addClassName("search-bar");
        searchInput.getElement().getStyle().set("margin-bottom", "1rem");
        searchInput.getElement().getStyle().set("padding", "0.5rem 0.75rem");
        searchInput.getElement().getStyle().set("border-radius", "4px");
        searchInput.getElement().getStyle().set("border", "1px solid #444");
        searchInput.getElement().getStyle().set("background-color", "var(--background-color)");
        searchInput.getElement().getStyle().set("color", "var(--text-primary)");
        searchInput.getElement().getStyle().set("box-sizing", "border-box");

        taskSection.add(taskTitle, searchInput);

        if (!users.isEmpty()) {
            User firstUser = users.get(0);
            Map<TodoList, List<Todo>> todosByList = dataLoader.getTodosGroupedByListForUser(firstUser);

            for (Map.Entry<TodoList, List<Todo>> entry : todosByList.entrySet()) {
                TodoList list = entry.getKey();
                List<Todo> todos = entry.getValue();
                List<NoteGroup.Task> tasks = convertTodosToNoteTasks(todos);

                NoteGroup noteGroup = new NoteGroup();
                noteGroup.setTitle("📋 " + list.getName());
                noteGroup.setTasks(tasks);

                taskSection.add(noteGroup);
            }

        } else {
            taskSection.add(new Paragraph("Keine Benutzer gefunden."));
        }

        // Chart Container
        Div chartContainer = new Div();
        chartContainer.setId("chart-container");
        chartContainer.setText("Hier kommt das Diagramm rein.");
        chartContainer.getStyle().set("max-width", "600px");
        chartContainer.getStyle().set("margin", "2rem auto");
        chartContainer.getStyle().set("background-color", "var(--tertiary-color)");
        chartContainer.getStyle().set("padding", "1rem");
        chartContainer.getStyle().set("border-radius", "8px");
        chartContainer.getStyle().set("box-sizing", "border-box");
        chartContainer.setWidthFull();

        mainContent.add(statusBox, taskSection, chartContainer);
        mainContent.expand(taskSection);

        // Topbar
        HorizontalLayout topbar = new HorizontalLayout();
        topbar.addClassName("topbar");
        topbar.setWidthFull();
        topbar.setHeight("60px");
        topbar.getStyle().set("background-color", "var(--tertiary-color)");
        topbar.getStyle().set("border-bottom", "1px solid #222");
        topbar.setPadding(true);
        topbar.setAlignItems(FlexComponent.Alignment.CENTER);
        topbar.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        topbar.getStyle().set("color", "var(--text-primary)");
        topbar.getStyle().set("user-select", "none");

        String username = users.isEmpty() ? "Gast" : users.get(0).getFirstName() + " " + users.get(0).getLastName();

        Span userInfo = new Span();
        userInfo.addClassName("user-info");
        userInfo.getStyle().set("font-weight", "600");
        userInfo.getStyle().set("display", "flex");
        userInfo.getStyle().set("align-items", "center");
        userInfo.getStyle().set("gap", "0.5rem");
        userInfo.setText("Hallo, " + username);

        Icon userIcon = VaadinIcon.USER.create();
        userIcon.addClassName("user-icon");
        userIcon.getStyle().set("width", "32px");
        userIcon.getStyle().set("height", "32px");
        userIcon.getStyle().set("fill", "var(--primary-color)");

        userInfo.getElement().insertChild(0, userIcon.getElement());
        topbar.add(userInfo);

        // Footer
        Footer footer = new Footer();
        footer.addClassName("footer");
        footer.getStyle().set("background-color", "var(--tertiary-color)");
        footer.getStyle().set("padding", "1rem");
        footer.getStyle().set("text-align", "center");
        footer.getStyle().set("font-size", "0.9rem");
        footer.getStyle().set("color", "var(--text-secondary)");
        footer.getStyle().set("border-top", "1px solid #222");
        footer.getStyle().set("user-select", "none");

        Anchor impressum = new Anchor("#", "Impressum");
        Anchor datenschutz = new Anchor("#", "Datenschutz");
        impressum.getStyle().set("color", "var(--primary-color)");
        datenschutz.getStyle().set("color", "var(--primary-color)");
        impressum.getStyle().set("margin", "0 0.5rem");
        datenschutz.getStyle().set("margin", "0 0.5rem");

        footer.add(new Text("© 2025 Dein Dashboard • "), impressum, new Text(" • "), datenschutz);

        VerticalLayout page = new VerticalLayout();
        page.setSizeFull();
        page.setPadding(false);
        page.setSpacing(false);
        page.add(topbar, container, footer);
        page.expand(container);

        container.add(sidebar, mainContent);

        setContent(page);
        getElement().getStyle().set("height", "100vh");
    }

    private Anchor createSidebarLink(String text, boolean active) {
        Anchor link = new Anchor("#", text);
        link.getElement().getClassList().add("sidebar-link");
        if (active) {
            link.getElement().getClassList().add("active");
        }
        return link;
    }

    private List<NoteGroup.Task> convertTodosToNoteTasks(List<Todo> todos) {
        return todos.stream().map(todo -> {
            boolean done = todo.isDone();
            String description = todo.getDescription();
            String priority = todo.getPriority() != null ? todo.getPriority().name().toLowerCase() : "niedrig";
            boolean overdue = !done && todo.getDueDate() != null && todo.getDueDate().isBefore(java.time.LocalDateTime.now());
            return new NoteGroup.Task(done, description, priority, overdue);
        }).collect(Collectors.toList());
    }
}
