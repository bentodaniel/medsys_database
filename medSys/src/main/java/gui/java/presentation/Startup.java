package gui.java.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import business.ConsultaHandler;
import facade.services.ConsultaOperationsService;
import gui.java.presentation.inputController.MainWindowController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Startup extends Application {

    private EntityManagerFactory emf;
    private ConsultaOperationsService consultaOperationsService;

    @Override
    public void start(Stage stage) throws IOException, SQLException {

        // This line to resolve keys against Bundle.properties
        ResourceBundle i18nBundle = ResourceBundle.getBundle(
                "i18n.Bundle", new Locale("pt", "PT"));

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/med_sys.fxml"), i18nBundle);
        Parent root = fxmlLoader.load();

        MainWindowController mainWindowController = fxmlLoader.getController();

        emf = Persistence.createEntityManagerFactory("medSysDB");
        consultaOperationsService = new ConsultaOperationsService(new ConsultaHandler(emf));
        mainWindowController.setService(consultaOperationsService);

        mainWindowController.startGetAll();

        stage.setScene(new Scene(root, 1280, 720));
        stage.setTitle(i18nBundle.getString("application.title"));
        //stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop(){
        System.out.println("Exiting MedSys");
        emf.close();
    }

    public static void startGUI() {
        launch();
    }
}
