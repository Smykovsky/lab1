package pl.smyk;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        String csvFileName = "C:\\Users\\kamil.smyk\\Pulpit\\kody.csv";
//        String dbUrl = "jdbc:mysql://localhost:3306/mydb";
//        String username = "root";
//        String password = "admin";
//        Connection connection = null;

        // 1. zapis całej kolekcji -> wczytanie całej zawartości, zapis całej kolekcji, koniec połączenia z bazą
        // 2. zapis pojedyńczego rekordu -> wyczytać jeden rekord, zmierzyć czas zapisu, koniec połączenia z bazą (procedure powtórzyć na wszystkich rekordach)
        // + 2 wybrane metody
        // 3. ORM -> hibernate
        // 4. bulk copy - komenda copy


        final MySQL mySQL = new MySQL("localhost", 3306, "mydb", "root", "admin",4, 4);
        mySQL.CREATETABLE("kody", "post_code VARCHAR(10), adress VARCHAR(100), voivoship VARCHAR(100), county VARCHAR(50)");

        String record = null;

        Connection connection = null;
        PreparedStatement statement = null;


        try {
            connection = mySQL.getHikari().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO kody (post_code, adress, voivoship, county) VALUES (?, ?, ?, ?)");
            BufferedReader reader = new BufferedReader(new FileReader(csvFileName));
            reader.readLine();
            long startTime = System.currentTimeMillis();
            while ((record = reader.readLine()) != null) {
                String[] data = record.split(";");
                statement.setString(1, data[0]);
                statement.setString(2, data[1]);
                statement.setString(3, data[2]);
                statement.setString(4, data[3]);
                statement.executeUpdate();
            }
            System.out.println("CZAS ZAPISU: " + (System.currentTimeMillis() - startTime) + "miliseconds");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            mySQL.close(statement);
            mySQL.close(connection);
        }


//        try {
//            connection = DriverManager.getConnection(dbUrl, username, password);
//            connection.setAutoCommit(false);
//
//            String createTableSQL = "CREATE TABLE IF NOT EXISTS kody (id AUTO_INCREMENT PRIMARY KEY, post_code VARCHAR (10), adress VARCHAR(100), voivoship VARCHAR(100), county VARCHAR(50))";
//            connection.createStatement().execute(createTableSQL);
//
//            String insertSql = "INSERT INTO kody (post_code, adress, voivoship, county) VALUES (?, ?, ?, ?)";
//            statement = connection.prepareStatement(insertSql);
//
//            FileReader fileReader = new FileReader(csvFileName);
//            BufferedReader reader = new BufferedReader(fileReader);
//
////            String record = null;
//
//            reader.readLine();
//
//            long startTime = System.currentTimeMillis() / 1000; // początek procedury zapisu danych
//            while ((record = reader.readLine()) != null) {
//                String[] data = record.split(";");
//                String postCode = data[0];
//                String adress = data[1];
//                String voivoship = data[2];
//                String county = data[3];
//
//                statement.setString(1, postCode);
//                statement.setString(2, adress);
//                statement.setString(3, voivoship);
//                statement.setString(4, county);
//
//                statement.executeUpdate();
//            }
//
//            connection.commit();
//            connection.close();
//            long endTime = System.currentTimeMillis() / 1000; // koniec procedury zapisu danych oraz połączenie z bazą zostaje zamknięte
//            long duration = endTime - startTime;
//            System.out.println("Czas zapisu danych do bazy wynosi: " + duration + "s");
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (connection != null){
//                connection.close();
//            }
//        }

    }
}