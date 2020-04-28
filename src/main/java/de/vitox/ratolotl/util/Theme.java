package de.vitox.ratolotl.util;

import de.vitox.ratolotl.Main;
import lombok.*;

@Getter
@AllArgsConstructor
public enum Theme {

    STANDARD, AAL, AESTELL, FUTURISTIC;

    public String getMainFile() {
        return Theme.class.getResource("/de/vitox/ratolotl/css/theme/" + this.name().toLowerCase() + "/main.css").toExternalForm();
    }

    public String getDarkFile() {
        return Theme.class.getResource("/de/vitox/ratolotl/css/theme/" + this.name().toLowerCase() + "/dark.css").toExternalForm();
    }

    public String getLightFile() {
        return Theme.class.getResource("/de/vitox/ratolotl/css/theme/" + this.name().toLowerCase() + "/light.css").toExternalForm();
    }

    public static void setCurrentTheme(Theme theme, boolean dark) {
        Main.getInstance().getScene().getStylesheets().removeIf(s -> s.contains("main.css") || s.contains("dark.css") || s.contains("light.css"));
        Main.getInstance().getScene().getStylesheets().addAll(theme.getMainFile(), dark ? theme.getDarkFile() : theme.getLightFile());
    }

}
