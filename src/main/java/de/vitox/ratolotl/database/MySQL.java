package de.vitox.ratolotl.database;

import com.google.common.util.concurrent.*;
import de.vitox.ratolotl.database.row.*;
import lombok.Getter;

import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executors;

public class MySQL {

    private Connection connection;

    @Getter
    private ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public MySQL() {
        System.out.println("MySQL: Connecting...");
        if (this.connect()) {
            System.out.println("MySQL: Connected!");
            return;
        }
        System.out.println("MySQL: Can't connect to database!");
        System.exit(0);
    }

    private boolean connect() {
        InputStream inputStream = getClass().getResourceAsStream("/de/vitox/ratolotl/data/mysql.properties");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Config config = new Config(inputStream);
            this.connection = DriverManager.getConnection("jdbc:mysql://" + config.getMySQLHost() + ":" + config.getMySQLPort() + "/" +
                    config.getMySQLDatabase() + "?user=" + config.getMySQLUser() + "&password=" + config.getMySQLPassword() + "&autoReconnect=true");
        } catch (Exception e) {
            System.out.println("MySQL: Connection error!");
            e.printStackTrace();
        }
        return this.isConnected();
    }

    public boolean disconnect() {
        if (!this.isConnected()) {
            return true;
        }

        try {
            this.connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println("MySQL: Can't close connection!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() {
        try {
            return this.connection != null && !this.connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ListenableFuture<Integer> insertAndGetId(String statement, Object... objects) {
        if (!this.isConnected()) {
            return Futures.immediateFailedFuture(new SQLException("No MySQL connection!"));
        }

        return this.pool.submit(() -> {
            try {
                PreparedStatement preparedStatement = this.getPreparedStatement(statement, objects);

                preparedStatement.execute();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                Integer result = null;

                if (resultSet.next()) {
                    result = resultSet.getInt(1);
                }

                resultSet.close();
                preparedStatement.close();

                return result;
            } catch (SQLException e) {
                System.out.println("[MySQL] Error while inserting statement!");
                e.printStackTrace();
                return null;
            }
        });
    }

    public ListenableFuture<Integer> execute(String statement, Object... objects) {
        if (!this.isConnected()) {
            return Futures.immediateFailedFuture(new SQLException("No MySQL connection!"));
        }

        return this.pool.submit(() -> {
            try {
                PreparedStatement preparedStatement = this.getPreparedStatement(statement, objects);

                Integer result = preparedStatement.executeUpdate();

                preparedStatement.close();

                return result;
            } catch (SQLException e) {
                System.out.println("[MySQL] Error while executing statement!");
                e.printStackTrace();
                return 0;
            }
        });
    }

    public ListenableFuture<Rows> query(String statement, Object... objects) {
        if (!this.isConnected()) {
            return Futures.immediateFailedFuture(new SQLException("No MySQL connection!"));
        }

        return this.pool.submit(() -> {
            try {
                PreparedStatement preparedStatement = this.getPreparedStatement(statement, objects);

                ResultSet resultSet = preparedStatement.executeQuery();

                Rows rows = this.getRows(resultSet);

                preparedStatement.close();
                resultSet.close();

                return rows;
            } catch (SQLException e) {
                System.out.println("[MySQL] Error while query statement!");
                e.printStackTrace();
                return new Rows();
            }
        });
    }

    private PreparedStatement getPreparedStatement(String statement, Object... objects) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject((i + 1), objects[i]);
        }

        return preparedStatement;
    }

    private Rows getRows(ResultSet resultSet) throws SQLException {
        List<Row> rowList = new ArrayList<>();

        ResultSetMetaData metaData = resultSet.getMetaData();

        int columnCount = metaData.getColumnCount();

        Map<String, Object> row;

        while (resultSet.next()) {
            row = new HashMap<>(columnCount);

            for (int i = 1; i <= columnCount; i++) {
                if (metaData.getColumnType(i) == Types.DECIMAL) {
                    row.put(metaData.getColumnName(i), resultSet.getBigDecimal(i).doubleValue());
                    continue;
                }
                row.put(metaData.getColumnName(i), resultSet.getObject(i));
            }

            rowList.add(new Row(row));
        }
        return new Rows(rowList);
    }

}
