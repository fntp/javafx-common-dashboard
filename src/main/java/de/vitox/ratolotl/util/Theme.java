package de.vitox.ratolotl.util;

import de.vitox.ratolotl.Main;
import lombok.*;

@Getter
@AllArgsConstructor
public enum Theme {

    STANDARD, AAL;

    public String getDarkFile() {
        return Theme.class.getResource("/de/vitox/ratolotl/css/theme/" + this.name().toLowerCase() + "/dark.css").toExternalForm();
    }

    public String getLightFile() {
        return Theme.class.getResource("/de/vitox/ratolotl/css/theme/" + this.name().toLowerCase() + "/light.css").toExternalForm();
    }

    public static void setCurrentTheme(Theme theme, boolean dark) {
        Main.getInstance().getScene().getStylesheets().removeIf(s -> s.contains("dark.css") || s.contains("light.css"));
        Main.getInstance().getScene().getStylesheets().add(dark ? theme.getDarkFile() : theme.getLightFile());
    }

}
