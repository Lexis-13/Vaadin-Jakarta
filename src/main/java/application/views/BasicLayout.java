package application.views;

import application.views.ToDoList.DashboardView;
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
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 *
 */
public class BasicLayout extends AppLayout {

    private H1 title;

    public BasicLayout(){
        this.addToDrawer(createNav());
        createHeader();
    }

    public void createHeader(){
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Seitentitel");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Button mode = new Button("Mode");
        Button logout = new Button("Logout");
        HorizontalLayout rightControls = new HorizontalLayout(mode, logout);
        rightControls.setSpacing(true);
        rightControls.getStyle().set("margin-left", "auto").set("padding-right", "10px");

        HorizontalLayout header = new HorizontalLayout(toggle,title, rightControls);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setSpacing(false);
        addToNavbar(header);
    }


    public Component createNav(){
        VerticalLayout verticalLayout = new VerticalLayout();
        Icon icon = VaadinIcon.CLIPBOARD.create(); // oder ein anderes Icon
        Span text = new Span("ToDo-List");

        HorizontalLayout linkLayout = new HorizontalLayout(icon, text);
        linkLayout.setSpacing(true);
        linkLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        RouterLink link = new RouterLink();
        link.add(linkLayout);
        link.setRoute(DashboardView.class);
        link.setId("navbar-link");

        verticalLayout.add(link);

        return verticalLayout;
    }
    public H1 getTitle(){
        return this.title;
    }




}

