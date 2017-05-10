package nl.fontys.micro;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteDataSource;

@Component
public class AccountRepository {

    private SQLiteDataSource dataSource;

    @Autowired
    public AccountRepository(@Value("${database}") String databaseName) {
        dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + databaseName);
        setUp();
    }

    private void setUp() {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS account (id INTEGER PRIMARY KEY, name TEXT, encryptedPassword TEXT)");
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Account create(String username, String encryptedPassword) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account (name, encryptedPassword) VALUES (?, ?)")) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, encryptedPassword);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            return new Account(resultSet.getInt(1), username);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Account get(int id) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, encryptedPassword FROM account WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return new Account(id, resultSet.getString("name"), resultSet.getString("encryptedPassword"));

            return null;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Account update(int id, String username, String encryptedPassword) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE account SET name = ?, encryptedPassword = ? WHERE id = ?")) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, encryptedPassword);
            preparedStatement.setInt(3, id);
            preparedStatement.execute();

            return new Account(id, username);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
