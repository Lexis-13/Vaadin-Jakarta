package application.views.ToDoList;

import application.views.ToDoList.NoteGroup;
import application.views.ToDoList.NoteGroup.Task;
import java.util.List;
import java.util.Arrays;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import application.views.BasicLayout;

@Route("")
@PageTitle("Dashboard")
public class DashboardView extends BasicLayout {

    public DashboardView() {
        // Gesamtes Container-Layout mit Sidebar und Main Content
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

        // Main Content Bereich
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

        Paragraph erledigt = new Paragraph("🟢 Erledigt: 3");
        erledigt.addClassName("erledigt");
        erledigt.getStyle().set("font-weight", "600");
        erledigt.getStyle().set("font-size", "1rem");

        Paragraph ueberfaellig = new Paragraph("⚠️ Überfällig: 2");
        ueberfaellig.addClassName("ueberfaellig");
        ueberfaellig.getStyle().set("font-weight", "600");
        ueberfaellig.getStyle().set("font-size", "1rem");

        statusBox.add(erledigt, ueberfaellig);

        // Aufgabenliste Abschnitt
        VerticalLayout taskSection = new VerticalLayout();
        taskSection.addClassName("task-section");
        taskSection.setWidthFull();

        H3 taskTitle = new H3("Aufgabenliste");
        taskTitle.getElement().getStyle().set("color", "var(--primary-color)");

        // Suchfeld
        Input searchInput = new Input();
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

        // Persönliche Aufgaben
        List<Task> personalTasks = Arrays.asList(
                new Task(false, "Einkaufen gehen", "mittel"),
                new Task(true, "Geburtstagskarte schreiben", "niedrig"),
                new Task(false, "Rechnung bezahlen", "hoch", true)
        );

        // Arbeit Aufgaben
        List<Task> workTasks = Arrays.asList(
                new Task(false, "Meeting vorbereiten", "hoch"),
                new Task(true, "Bericht abschicken", "mittel")
        );

        taskSection.add(taskTitle, searchInput);
        taskSection.add(createNoteGroup("📌 Persönlich", personalTasks));
        taskSection.add(createNoteGroup("💼 Arbeit", workTasks));

        // Chart Container (Platzhalter, hier kannst du später den Chart einfügen)
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

        // Zusammensetzen MainContent
        mainContent.add(statusBox, taskSection, chartContainer);
        mainContent.expand(taskSection);

        // Topbar mit Benutzerinfo (oben rechts)
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

        Span userInfo = new Span();
        userInfo.addClassName("user-info");
        userInfo.getStyle().set("font-weight", "600");
        userInfo.getStyle().set("display", "flex");
        userInfo.getStyle().set("align-items", "center");
        userInfo.getStyle().set("gap", "0.5rem");
        userInfo.setText("Hallo, Max Mustermann");

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

        // Seite zusammensetzen: Topbar + Container + Footer
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

    private NoteGroup createNoteGroup(String title, List<Task> tasks) {
        NoteGroup group = new NoteGroup();
        group.setTitle(title);
        group.setTasks(tasks);
        return group;
    }
}
