package pl.smyk;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class PostgreSQL {
    private final String host;
    private final int port;
    private final String dbName;
    private final String user;
    private final String password;
    private final Executor executor;
    private final int pool;
    @Getter
    private HikariDataSource hikari;

    public PostgreSQL(String host, int port, String dbName, String user, String password, int pool, int threadpool) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.password =password;
        this.pool = pool;
        this.executor = Executors.newScheduledThreadPool(threadpool);
    }

    private void connect() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.dbName);
        hikariConfig.setUsername(this.user);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.setMaximumPoolSize(this.pool);
        this.hikari = new HikariDataSource();
        try {
            if (this.getHikari().getConnection() != null) {
                System.out.println("Nawiązano połączenie z POSTGRESQL!");
            }
        } catch (SQLException e) {
            System.out.println("BŁĄD!");
        }
    }

    private void close(Connection connection) {
        if (connection == null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Błąd podczas zamykania połączenia z bazą danych!");
            }
        }
    }

    private void close(Statement statement) {
        if (statement == null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Błąd podczas zamykania statementa!");
            }
        }
    }
}
