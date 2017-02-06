/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personfxeditor;

import com.asgteach.familytreefx.model.Person;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;


/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.asgteach.familytree.personfxeditor//PersonFXEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PersonFXEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(roles="Admin",mode = "editor", openAtStartup = true)

@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PersonFXEditorAction",
        preferredID = "PersonFXEditorTopComponent"
)
@Messages({
    "CTL_PersonFXEditorAction=PersonFXEditor",
    "CTL_PersonFXEditorTopComponent=PersonFXEditor Window",
    "HINT_PersonFXEditorTopComponent=This is a PersonFXEditor window"
})
public final class PersonFXEditorTopComponent extends TopComponent {
    private static JFXPanel fxPanel;
    private Lookup.Result<Person> lookupResult = null;
    private PersonFXEditorController controller;
    private static final Logger logger = Logger.getLogger(PersonFXEditorTopComponent.class.getName());

    public PersonFXEditorTopComponent() {
        initComponents();
        setName(Bundle.CTL_PersonFXEditorTopComponent());
        setToolTipText(Bundle.HINT_PersonFXEditorTopComponent());
         setLayout(new BorderLayout());
        
        init();
        

    }
     private void init() {
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);
        Platform.setImplicitExit(false);
        Platform.runLater(() -> createScene());
    }

    private void createScene() {
        logger.log(Level.INFO, "PersonFXEditorTopComponent createScene() called");
        try {
            URL location = getClass().getResource("PersonFXEditor.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            logger.log(Level.INFO, "About to invoke load for {0} ", location);
            Parent root = (Parent) fxmlLoader.load(location.openStream());
            logger.log(Level.INFO, "Finished fxmlLoader.load");
            Scene scene = new Scene(root,400,300);
            fxPanel.setScene(scene);
            controller = (PersonFXEditorController) fxmlLoader.getController();
            logger.log(Level.INFO, "Finished getting controller");
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    // LookupListener to detect changes in Global Selection Lookup
    LookupListener lookupListener = (LookupEvent le) -> checkLookup();
    
    private void checkLookup() {
        TopComponent tc = TopComponent.getRegistry().getActivated();
        if (tc != null && tc.equals(this)) {
            logger.log(Level.INFO, "PersonFXEditorTopComponent has focus.");
            return;
        }
        Collection<? extends Person> allPeople = lookupResult.allInstances();
        if (Platform.isFxApplicationThread()) {
            System.out.println("Already in FX application thread");
            controller.doUpdate(allPeople);
       } else {
            System.out.println("NOT in FX application thread");
            Platform.runLater(() -> controller.doUpdate(allPeople));
        }       
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        lookupResult = Utilities.actionsGlobalContext().lookupResult (Person.class);
        lookupResult.addLookupListener(lookupListener);
        checkLookup();

    }

    @Override
    public void componentClosed() {
       lookupResult.removeLookupListener(lookupListener);

    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
