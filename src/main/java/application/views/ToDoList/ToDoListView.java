package application.views.ToDoList;

import application.views.BasicLayout;
import com.vaadin.flow.router.Route;

@Route("/ToDo-List")
public class ToDoListView extends BasicLayout {

    public ToDoListView(){
        this.getTitle().setText("ToDo-List");
    }
}
