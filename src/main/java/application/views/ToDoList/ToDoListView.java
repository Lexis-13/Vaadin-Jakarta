package application.views.ToDoList;

import application.views.BasicLayout;
import application.views.components.CustomButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/ToDo-List")
public class ToDoListView extends BasicLayout {

    CustomButton test = new CustomButton("LogIn");

    public ToDoListView(){
        this.getTitle().setText("ToDo-List");
        createContent();
    }
    public void createContent(){
        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();

        test.setWidth("200px");
        test.setHeight("50px");
        test.setVisible(true);


        layout.add(test);
        this.setContent(layout);
    }
}
