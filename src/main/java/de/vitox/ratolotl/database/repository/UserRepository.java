package de.vitox.ratolotl.database.repository;

import com.google.common.util.concurrent.*;
import de.vitox.ratolotl.database.MySQL;
import de.vitox.ratolotl.database.row.Row;
import de.vitox.ratolotl.user.User;
import de.vitox.ratolotl.util.*;

import java.util.concurrent.ExecutionException;

public class UserRepository {

    private MySQL mySQL;

    public UserRepository(MySQL mySQL) {
        this.mySQL = mySQL;

        try {
            this.mySQL.execute("CREATE TABLE IF NOT EXISTS user (username VARCHAR(25), password CHAR(128), theme INT DEFAULT 0, dark_mode TINYINT(1) DEFAULT 0, PRIMARY KEY(username))").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public ListenableFuture<Integer> insertUser(String username, String password) {
        return this.mySQL.execute("INSERT INTO user (username, password) VALUES (?,?)", username, LoginUtil.getHashedPassword(password));
    }

    public ListenableFuture<Boolean> existsUsername(String username) {
        return Futures.transform(this.mySQL.query("SELECT username FROM user WHERE username = ?", username), rows ->
                rows != null && rows.first() != null, MoreExecutors.directExecutor());
    }

    public ListenableFuture<User> getUser(String username, String password) {
        return Futures.transform(this.mySQL.query("SELECT * FROM user WHERE username = ? AND password = ?", username, LoginUtil.getHashedPassword(password)), rows -> {
            if (rows != null && rows.first() != null) {
                Row row = rows.first();
                return new User(row.getString("username"),
                        Theme.values()[row.getInt("theme")],
                        row.getBoolean("dark_mode"));
            }
            return null;
        }, MoreExecutors.directExecutor());
    }

    public ListenableFuture<Integer> updateUser(User user) {
        return this.mySQL.execute("UPDATE user SET theme = ?, dark_mode = ? WHERE username = ?",
                user.getTheme().ordinal(), user.isDarkMode(), user.getUsername());
    }
}
