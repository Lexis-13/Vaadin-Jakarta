package application.views;

import application.views.ToDoList.DashboardView;
import application.views.ToDoList.TasksView;
import com.gmail.umit.PaperToggleButton;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;


/**
 *
 */
public class BasicLayout extends AppLayout {

    private H1 title = new H1();

    public BasicLayout(){
        this.addToDrawer(createNav());
        createHeader();
        createFooter();
        setDrawerOpened(false);
    }

    public void createHeader(){
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("id", "drawerToggle");

        this.title = new H1();
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Button mode = new Button("Mode");
        Button logout = new Button("Logout", e ->{
            getUI().ifPresent(ui -> ui.getPage().setLocation("/logout"));
        });

        HorizontalLayout rightControls = new HorizontalLayout(mode, logout);
        rightControls.setSpacing(true);
        rightControls.getStyle().set("margin-left", "auto").set("padding-right", "10px");

        HorizontalLayout header = new HorizontalLayout(toggle,title, rightControls);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setSpacing(false);

        addToNavbar(header);

        // JavaScript Hover-Listener für Drawer öffnen
        UI.getCurrent().getPage().executeJs("""
    const toggle = document.querySelector('#drawerToggle');
    const appLayout = document.querySelector('vaadin-app-layout');

    if (toggle && appLayout && appLayout.shadowRoot) {
        const drawer = appLayout.shadowRoot.querySelector('[part="drawer"]');

        if (drawer) {
            // Öffnen beim Hover über den Toggle
            toggle.addEventListener('mouseenter', () => {
                appLayout.setAttribute('drawer-opened', '');
            });

            // Schließen beim Verlassen des Toggle
            toggle.addEventListener('mouseleave', () => {
                setTimeout(() => {
                    if (!drawer.matches(':hover') && !toggle.matches(':hover')) {
                        appLayout.removeAttribute('drawer-opened');
                    }
                }, 200);
            });

            // Schließen beim Verlassen des Drawer
            drawer.addEventListener('mouseleave', () => {
                setTimeout(() => {
                    if (!drawer.matches(':hover') && !toggle.matches(':hover')) {
                        appLayout.removeAttribute('drawer-opened');
                    }
                }, 200);
            });
        }
    }
""");
    }

    public void createFooter(){
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.setAlignItems(FlexComponent.Alignment.END);
    }

    public Component createNav(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(true);
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        //Dashboardlink
        Icon dbicon = VaadinIcon.CLIPBOARD.create(); // oder ein anderes Icon
        Span dbtext = new Span("Dashboard");
        HorizontalLayout dbdisplay = new HorizontalLayout(dbicon, dbtext);
        RouterLink db = new RouterLink();
        db.add(dbdisplay);
        db.setRoute(DashboardView.class);

        //Aufgaben
        Icon tasksIcon = VaadinIcon.CHECK.create();
        Span tasksText = new Span("Tasks");
        HorizontalLayout tasks = new HorizontalLayout(tasksIcon, tasksText);
        RouterLink task = new RouterLink();
        task.add(tasks);
        task.setRoute(TasksView.class);

        //Kalender

        //Einstellungen

        verticalLayout.add(db, task);

        return verticalLayout;
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public String getTitle(){
        return this.title.getText();
    }



}

