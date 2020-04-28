package de.vitox.ratolotl;

import de.vitox.ratolotl.database.MySQL;
import de.vitox.ratolotl.database.repository.UserRepository;
import de.vitox.ratolotl.user.LocalUserData;
import de.vitox.ratolotl.util.FXUtil;
import de.vitox.ratolotl.util.Theme;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.*;

@Getter
public class Main extends Application {

    private final String name = "Ratolotl", version = "1.0";

    @Getter
    private static Main instance;

    private MySQL mySQL;

    private UserRepository userRepository;

    private Stage stage;

    @Setter
    private Scene scene;

    public void start(Stage stage) throws Exception {
        instance = this;

        this.stage = stage;

        this.mySQL = new MySQL();

        this.userRepository = new UserRepository(this.mySQL);

        Parent root = FXMLLoader.load(getClass().getResource("/de/vitox/ratolotl/fxml/dashboard.fxml"));

        scene = new Scene(root);
        scene.setRoot(root);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);

        stage.initStyle(StageStyle.TRANSPARENT);
        FXUtil.resizable(stage);

        stage.show();

        LocalUserData.getProperty("theme").ifPresent(theme ->
                Theme.setCurrentTheme(Theme.values()[Integer.parseInt(theme)], LocalUserData.getProperty("dark_mode").map(Boolean::valueOf).orElse(false)));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

}
