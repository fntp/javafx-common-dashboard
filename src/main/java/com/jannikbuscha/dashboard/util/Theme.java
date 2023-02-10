package com.jannikbuscha.dashboard.util;

import com.jannikbuscha.dashboard.Main;
import javafx.application.Platform;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Theme {

    STANDARD, BACKIFY;

    public String getThemeFile() {
        return Main.class.getResource("css/theme/" + this.name().toLowerCase() + "/theme.css").toExternalForm();
    }

    public String getDarkFile() {
        return Main.class.getResource("css/theme/" + this.name().toLowerCase() + "/dark.css").toExternalForm();
    }

    public String getLightFile() {
        return Main.class.getResource("css/theme/" + this.name().toLowerCase() + "/light.css").toExternalForm();
    }

    public String getName() {
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase().replace("_", " ");
    }

    public static void setCurrentTheme(Theme theme, boolean dark) {
        Main.getInstance().getScene().getStylesheets().removeIf(s -> s.contains("theme.css") || s.contains("dark.css") || s.contains("light.css"));
        Main.getInstance().getScene().getStylesheets().addAll(theme.getThemeFile(), dark ? theme.getDarkFile() : theme.getLightFile());
    }

}
