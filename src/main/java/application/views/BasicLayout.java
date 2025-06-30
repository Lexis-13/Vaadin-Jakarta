package application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;


/**
 *
 */
public class BasicLayout extends AppLayout {

    H1 title = new H1("Seitentitel");


    public BasicLayout(){
        this.addToDrawer(createNav());

        DrawerToggle toggle = new DrawerToggle();

        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        HorizontalLayout header = new HorizontalLayout(toggle, title);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setSpacing(false);
        addToNavbar(header);

    }



    public Component createNav(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(new RouterLink());
        return verticalLayout;
    }
    public H1 getTitle(){
        return this.title;
    }




}

