/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.actions;

import com.asgteach.familytree.capabilities.CalendarCapability;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Nodes",
        id = "com.asgteach.familytree.actions.NodeCalendarAction"
)
@ActionRegistration(
        iconBase = "com/asgteach/familytree/actions/calendar.png",
        displayName = "#CTL_NodeCalendarAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1200),
    @ActionReference(path = "Toolbars/File", position = 500),
    @ActionReference(path = "Shortcuts", name = "C-E")
})
@Messages("CTL_NodeCalendarAction=Calendar")
public final class NodeCalendarAction implements ActionListener {

    private final CalendarCapability context;

    public NodeCalendarAction(CalendarCapability context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.doCalendar();
    }
}
