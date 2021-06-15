package com.plociennik.poogphasefront.gui.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("temp")
@PageTitle("Temp")
@UIScope
public class TempPage extends VerticalLayout {

    public TempPage() {
        add(new Text("example text"));
    }
}
