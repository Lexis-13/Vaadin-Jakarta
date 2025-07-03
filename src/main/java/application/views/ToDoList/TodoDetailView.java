package application.views.ToDoList;

import application.database.*;
import application.views.BasicLayout;
import application.views.components.DonutChart;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.tabs.*;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route("detail")
@PageTitle("Aufgabenübersicht")
public class TodoDetailView extends BasicLayout {

    private DataLoader dataLoader;
    private User currentUser;
    private Map<TodoList, List<Todo>> todosByList;

    public TodoDetailView() {
        dataLoader = new DataLoader();
        List<User> users = dataLoader.getUsers();

        currentUser = users.get(0);
        todosByList = dataLoader.getTodosGroupedByListForUser(currentUser);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassName("container");
        container.setSizeFull();

        VerticalLayout sidebar = createSidebar();

        VerticalLayout mainContent = new VerticalLayout();
        mainContent.setSizeFull();
        mainContent.addClassName("main-content");
        mainContent.getElement().getStyle()
                .set("background-color", "var(--card-background)")
                .set("padding", "1rem 2rem")
                .set("box-sizing", "border-box")
                .set("overflow-y", "auto");

        // Tabs für TodoLists
        Tabs tabs = new Tabs();
        tabs.setWidthFull();
        tabs.addClassName("todo-tabs");

        // Content Panels für jede Liste
        Div tabsContent = new Div();
        tabsContent.setWidthFull();

        for (Map.Entry<TodoList, List<Todo>> entry : todosByList.entrySet()) {
            TodoList todoList = entry.getKey();
            List<Todo> todos = entry.getValue();

            Tab tab = new Tab(todoList.getName());
            tabs.add(tab);

            VerticalLayout taskLayout = createEditableTaskList(todos);
            taskLayout.setVisible(false);
            taskLayout.addClassName("tab-content");
            tabsContent.add(taskLayout);
        }

        if (!(tabs.getComponentCount() == (0))) {
            // Erster Tab und Content sichtbar machen
            tabs.setSelectedIndex(0);
            tabsContent.getChildren().skip(0).findFirst().ifPresent(c -> c.setVisible(true));
        }

        // Tab-Auswahl-Listener: Sichtbarkeit umschalten
        tabs.addSelectedChangeListener(event -> {
            int selected = tabs.getSelectedIndex();
            int i = 0;
            for (var child : tabsContent.getChildren().toList()) {
                child.setVisible(i == selected);
                i++;
            }
        });

        mainContent.add(tabs, tabsContent);
        mainContent.expand(tabsContent);

        container.add(sidebar, mainContent);

        HorizontalLayout topbar = createTopbar(currentUser);
        Footer footer = createFooter();

        VerticalLayout page = new VerticalLayout(topbar, container, footer);
        page.setSizeFull();
        page.setPadding(false);
        page.setSpacing(false);
        page.expand(container);

        setContent(page);
        getElement().getStyle().set("height", "100vh");
    }

    private VerticalLayout createEditableTaskList(List<Todo> todos) {
        VerticalLayout taskList = new VerticalLayout();
        taskList.setSpacing(false);
        taskList.setPadding(false);
        taskList.setWidthFull();

        LocalDateTime now = LocalDateTime.now();

        for (Todo todo : todos) {
            HorizontalLayout row = new HorizontalLayout();
            row.setWidthFull();
            row.setAlignItems(FlexComponent.Alignment.CENTER);
            row.getStyle().set("padding", "0.3rem 0.5rem");
            row.getStyle().set("border-bottom", "1px solid var(--border-color)");

            Checkbox checkbox = new Checkbox(todo.isDone());
            checkbox.addValueChangeListener(e -> todo.setDone(e.getValue()));

            Span description = new Span(todo.getDescription());
            description.getStyle().set("flex-grow", "1");
            description.getStyle().set("font-weight", todo.isDone() ? "normal" : "600");
            description.getStyle().set("text-decoration", todo.isDone() ? "line-through" : "none");
            description.getStyle().set("color", todo.isDone() ? "var(--text-secondary)" : "var(--text-primary)");

            // Optional: Du kannst hier ein TextField nutzen, um Beschreibung editierbar zu machen, oder per Dialog etc.

            Span priority = new Span();
            priority.getElement().setProperty("innerHTML", getPriorityIcon(todo.getPriority().name()));
            priority.getStyle().set("min-width", "60px");
            priority.getStyle().set("text-align", "center");

            Span overdue = new Span();
            if (!todo.isDone() && todo.getDueDate() != null && todo.getDueDate().isBefore(now)) {
                overdue.setText("⚠️");
                overdue.getElement().setProperty("title", "Überfällig");
                overdue.getStyle().set("color", "var(--error-color)");
                overdue.getStyle().set("margin-left", "0.5rem");
            }

            row.add(checkbox, description, overdue, priority);
            taskList.add(row);
        }

        return taskList;
    }

    private VerticalLayout createSidebar() {
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.addClassName("sidebar");
        sidebar.setWidth("250px");
        sidebar.setPadding(true);
        sidebar.setSpacing(false);

        H2 sidebarTitle = new H2("Dein Dashboard");
        sidebarTitle.getElement().getStyle().set("color", "var(--primary-color)");

        Nav nav = new Nav();
        nav.add(createSidebarLink("Übersicht", false));
        nav.add(createSidebarLink("Aufgaben", true));
        nav.add(createSidebarLink("Kalender", false));
        nav.add(createSidebarLink("Einstellungen", false));

        sidebar.add(sidebarTitle, nav);
        return sidebar;
    }

    private HorizontalLayout createStatusNumbers(long erledigtCount, long ueberfaelligCount, long offenCount) {
        VerticalLayout statusNumbers = new VerticalLayout();
        statusNumbers.addClassName("status-numbers");
        statusNumbers.getStyle()
                .set("padding", "1rem")
                .set("background-color", "var(--tertiary-color)")
                .set("border-radius", "6px")
                .set("min-width", "120px")
                .set("box-sizing", "border-box")
                .set("margin-right", "1rem");

        statusNumbers.add(createStatusParagraph("🟢 Erledigt: " + erledigtCount, "erledigt"));
        statusNumbers.add(createStatusParagraph("⚠️ Überfällig: " + ueberfaelligCount, "ueberfaellig"));
        statusNumbers.add(createStatusParagraph("🔵 Offen: " + offenCount, "offen"));

        HorizontalLayout container = new HorizontalLayout(statusNumbers);
        container.setPadding(false);
        container.setMargin(false);
        return container;
    }

    private Paragraph createStatusParagraph(String text, String styleClass) {
        Paragraph p = new Paragraph(text);
        p.addClassName(styleClass);
        p.getStyle()
                .set("font-weight", "600")
                .set("font-size", "1.1rem")
                .set("margin", "0.15rem 0");
        return p;
    }

    private DonutChart createDonutChart(long erledigtCount, long ueberfaelligCount, long offenCount) {
        DonutChart donutChart = new DonutChart(erledigtCount, ueberfaelligCount, offenCount);
        donutChart.getStyle()
                .set("margin", "0")
                .set("max-width", "350px")
                .set("min-width", "350px")
                .set("height", "350px");
        return donutChart;
    }

    private HorizontalLayout createTopbar(User user) {
        HorizontalLayout topbar = new HorizontalLayout();
        topbar.addClassName("topbar");
        topbar.setWidthFull();
        topbar.setHeight("60px");
        topbar.getStyle()
                .set("background-color", "var(--tertiary-color)")
                .set("border-bottom", "1px solid #222")
                .set("color", "var(--text-primary)")
                .set("user-select", "none");
        topbar.setPadding(true);
        topbar.setAlignItems(FlexComponent.Alignment.CENTER);
        topbar.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        String username = user.getFirstName() + " " + user.getLastName();

        Span userInfo = new Span();
        userInfo.addClassName("user-info");
        userInfo.getStyle()
                .set("font-weight", "600")
                .set("display", "flex")
                .set("align-items", "center")
                .set("gap", "0.5rem");
        userInfo.setText("Hallo, " + username);

        Icon userIcon = VaadinIcon.USER.create();
        userIcon.addClassName("user-icon");
        userIcon.getStyle()
                .set("width", "32px")
                .set("height", "32px")
                .set("fill", "var(--primary-color)");

        userInfo.getElement().insertChild(0, userIcon.getElement());
        topbar.add(userInfo);

        return topbar;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassName("footer");
        footer.getStyle()
                .set("background-color", "var(--tertiary-color)")
                .set("padding", "1rem")
                .set("text-align", "center")
                .set("font-size", "0.9rem")
                .set("color", "var(--text-secondary)")
                .set("border-top", "1px solid #222")
                .set("user-select", "none");

        Anchor impressum = new Anchor("#", "Impressum");
        Anchor datenschutz = new Anchor("#", "Datenschutz");

        impressum.getStyle().set("color", "var(--primary-color)").set("margin", "0 0.5rem");
        datenschutz.getStyle().set("color", "var(--primary-color)").set("margin", "0 0.5rem");

        footer.add(new Text("© 2025 Dein Dashboard • "), impressum, new Text(" • "), datenschutz);
        return footer;
    }

    private Anchor createSidebarLink(String text, boolean active) {
        String route;
        switch (text) {
            case "Übersicht" -> route = "";
            case "Aufgaben" -> route = "detail";
            case "Kalender" -> route = "kalender";
            case "Einstellungen" -> route = "einstellungen";
            default -> route = "";
        }

        Anchor link = new Anchor(route, text);
        link.getElement().getClassList().add("sidebar-link");
        if (active) {
            link.getElement().getClassList().add("active");
        }
        return link;
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
}
