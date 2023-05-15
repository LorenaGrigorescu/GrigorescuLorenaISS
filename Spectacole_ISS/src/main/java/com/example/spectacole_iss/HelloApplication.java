package com.example.spectacole_iss;

import com.example.spectacole_iss.controller.LogInController;
import com.example.spectacole_iss.controller.SpectacolController;
import com.example.spectacole_iss.repository.SpectacolDBRepository;
import com.example.spectacole_iss.repository.UserDBRepository;
import com.example.spectacole_iss.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
//        FXMLLoader Sloader = new FXMLLoader(HelloApplication.class.getResource("spectacole.fxml"));
        Parent root = loader.load();
        LogInController controller = loader.getController();
//        SpectacolController spectacolController = Sloader.getController();
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("D:\\Anul 2-FMI UBB Cluj\\Semestru 2\\Ingineria Sistemelor Soft\\Spectacole_ISS\\src\\main\\resources\\bd.config"));
        } catch (IOException ioException) {
            System.out.println("Cannot find bd.config");
        }
        UserDBRepository userRepository = new UserDBRepository(properties);
        SpectacolDBRepository spectacolRepository = new SpectacolDBRepository(properties);
        Service service = new Service(spectacolRepository, userRepository);
        controller.setService(service);
//        spectacolController.setService(service);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("LogIn!");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}