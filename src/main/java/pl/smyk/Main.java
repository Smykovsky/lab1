package pl.smyk;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jdk.jfr.Timespan;
import org.apache.commons.lang3.time.StopWatch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
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
        long rowCount = 0;

        Connection connection = null;
        PreparedStatement statement = null;




        try {
            connection = mySQL.getHikari().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO kody (post_code, adress, voivoship, county) VALUES (?, ?, ?, ?)");
            BufferedReader reader = new BufferedReader(new FileReader(csvFileName));
            reader.readLine();
            while ((record = reader.readLine()) != null) {
                long startTime = System.currentTimeMillis();
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
                statement.executeBatch();
                System.out.println("CZAS ZAPISU 1 REKORDU: " + (System.currentTimeMillis() - startTime) + "miliseconds");
            }
            connection.commit();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            mySQL.close(statement);
            mySQL.close(connection);
        }




//         1. zapis całej kolekcji -> wczytanie całej zawartości, zapis całej kolekcji, koniec połączenia z bazą
//        try {
//            connection = mySQL.getHikari().getConnection();
//            connection.setAutoCommit(false);
//            statement = connection.prepareStatement("INSERT INTO kody (post_code, adress, voivoship, county) VALUES (?, ?, ?, ?)");
//            BufferedReader reader = new BufferedReader(new FileReader(csvFileName));
//            reader.readLine();
//            long startTime = System.currentTimeMillis();
//            StopWatch stopWatch = new StopWatch();
//            stopWatch.start();
//            while ((record = reader.readLine()) != null) {
//                String[] data = record.split(";");
//
//                String postCode = data[0];
//                String adress = data[1];
//                String voivoship = data[2];
//                String county = data[3];
//
//                statement.setString(1, postCode);
//                statement.setString(2, adress);
//                statement.setString(3, voivoship);
//                statement.setString(4, county);
//                statement.addBatch();
//            }
//            statement.executeBatch();
//            stopWatch.stop();
//            System.out.println("CZAS ZAPISU: " + (stopWatch.getStopTime() - stopWatch.getStartTime()) + "miliseconds");
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        finally {
//            mySQL.close(statement);
//            mySQL.close(connection);
//        }

        // 2. zapis pojedyńczego rekordu -> wyczytać jeden rekord, zmierzyć czas zapisu, koniec połączenia z bazą (procedure powtórzyć na wszystkich rekordach)
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(csvFileName));
//            reader.readLine();
//            StopWatch stopWatchFull = new StopWatch();
//            stopWatchFull.start();
//            while ((record = reader.readLine()) != null) {
//                connection = mySQL.getHikari().getConnection();
//                statement = connection.prepareStatement("INSERT INTO kody (post_code, adress, voivoship, county) VALUES (?, ?, ?, ?)");
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
//                statement.executeUpdate();
//                connection.close();
//
//                rowCount++;
//            }
//            stopWatchFull.stop();
//
//            long duration = stopWatchFull.getStopTime() - stopWatchFull.getStartTime();
//
//            double avgPerRecord = (double) duration / rowCount;
//
//            System.out.println("Wszystkie rekordy zostały zapisane w: " + duration + " ms");
//            System.out.println("Średni czas zapisu jednego rekordu to: " + avgPerRecord + " ms");
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            mySQL.close(statement);
//            mySQL.close(connection);
//        }

    }
}