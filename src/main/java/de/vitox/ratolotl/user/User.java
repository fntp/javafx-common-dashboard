package de.vitox.ratolotl.user;

import de.vitox.ratolotl.Main;
import de.vitox.ratolotl.util.Theme;
import lombok.*;

@Getter
@AllArgsConstructor
public class User {

    private String username;

    private Theme theme;

    private boolean darkMode;

    public void updateTheme(Theme theme, boolean dark) {
        this.theme = theme;
        this.darkMode = dark;

        Main.getInstance().getUserRepository().updateUser(this);

        this.updateLocalTheme();
    }

    public void updateLocalTheme() {
        LocalUserData.setProperty("theme", this.theme.ordinal() + "");
        LocalUserData.setProperty("dark_mode", String.valueOf(this.darkMode));
    }

}
