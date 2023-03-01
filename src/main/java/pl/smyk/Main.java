package pl.smyk;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String csvFileName = "C:\\java\\lab1\\src\\main\\resources\\kody.csv";
        String dbUrl = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "admin";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            connection.setAutoCommit(false);

            String createTableSQL = "CREATE TABLE IF NOT EXISTS kody (id INT PRIMARY KEY AUTO_INCREMENT, post_code VARCHAR (255), adress VARCHAR(255), voivoship VARCHAR(255), county VARCHAR(255))";
            connection.createStatement().execute(createTableSQL);

            String insertSql = "INSERT INTO kody (post_code, adress, voivoship, county) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSql);

            FileReader fileReader = new FileReader(csvFileName);
            BufferedReader reader = new BufferedReader(fileReader);

            String record = null;

            reader.readLine();

            while ((record = reader.readLine()) != null) {
                String[] data = record.split(";");
                String postCode = data[0];
                String adress = data[1];
                String voivoship = data[2];
                String county = data[3];

                statement.setString(1, postCode);
                statement.setString(2, adress);
                statement.setString(3, voivoship);
                statement.setString(4, county);
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            connection.close();

            System.out.println("Records inserted successfully!");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null){
                connection.close();
            }
        }

    }
}