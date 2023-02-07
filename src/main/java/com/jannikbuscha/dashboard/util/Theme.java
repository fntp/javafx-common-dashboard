package com.jannikbuscha.dashboard.util;

import com.jannikbuscha.dashboard.Main;
import lombok.*;

@Getter
@AllArgsConstructor
public enum Theme {

    STANDARD, BACKIFY;

    public String getThemeFile() {
        return Theme.class.getResource(Main.PATH + "/css/theme/" + this.name().toLowerCase() + "/theme.css").toExternalForm();
    }

    public String getDarkFile() {
        return Theme.class.getResource(Main.PATH + "/css/theme/" + this.name().toLowerCase() + "/dark.css").toExternalForm();
    }

    public String getLightFile() {
        return Theme.class.getResource(Main.PATH + "/css/theme/" + this.name().toLowerCase() + "/light.css").toExternalForm();
    }

    public String getName() {
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase().replace("_", " ");
    }

    public static void setCurrentTheme(Theme theme, boolean dark) {
        Main.getInstance().getScene().getStylesheets().removeIf(s -> s.contains("theme.css") || s.contains("dark.css") || s.contains("light.css"));
        Main.getInstance().getScene().getStylesheets().addAll(theme.getThemeFile(), dark ? theme.getDarkFile() : theme.getLightFile());
    }

}
