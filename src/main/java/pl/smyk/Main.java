package pl.smyk;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jdk.jfr.Timespan;
import org.apache.commons.lang3.time.StopWatch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.hibernate.cfg.Configuration;

import javax.imageio.spi.ServiceRegistry;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;




public class Main {
    public static void main(String[] args) throws SQLException {

        String csvFileName = "C:\\Users\\kamil.smyk\\Pulpit\\kody.csv";
        String dbUrl = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "admin";

        // 1. zapis całej kolekcji -> wczytanie całej zawartości, zapis całej kolekcji, koniec połączenia z bazą
        // 2. zapis pojedyńczego rekordu -> wyczytać jeden rekord, zmierzyć czas zapisu, koniec połączenia z bazą (procedure powtórzyć na wszystkich rekordach)
        // 3. ORM -> hibernate



        final MySQL mySQL = new MySQL("localhost", 3306, "mydb", "root", "admin",4, 4);
        mySQL.CREATETABLE("code", "post_code VARCHAR(6), adress VARCHAR(103), voivoship VARCHAR(27), county VARCHAR(33)");

        String record = null;
        long rowCount = 0;

        Connection connection = null;
        PreparedStatement statement = null;

//         1. zapis całej kolekcji -> wczytanie całej zawartości, zapis całej kolekcji, koniec połączenia z bazą
//        try {
//            connection = mySQL.getHikari().getConnection();
//            statement = connection.prepareStatement("INSERT INTO code (post_code, adress, voivoship, county) VALUES (?, ?, ?, ?)");
//            BufferedReader reader = new BufferedReader(new FileReader(csvFileName));
//            reader.readLine();
//            StopWatch stopWatch = new StopWatch();
//            stopWatch.start();
//            while ((record = reader.readLine()) != null) {
//                String[] data = record.split(";");
////
////                String postCode = data[0];
////                String adress = data[1];
////                String voivoship = data[2];
////                String county = data[3];
//
//                statement.setString(1, data[0]);
//                statement.setString(2, data[1]);
//                statement.setString(3, data[2]);
//                statement.setString(4, data[3]);
//                statement.addBatch();
//            }
//            statement.executeBatch();
//            stopWatch.stop();
//            System.out.println("CZAS ZAPISU: " + (stopWatch.getStopTime() - stopWatch.getStartTime()) + "miliseconds");
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
//                statement = connection.prepareStatement("INSERT INTO code (post_code, adress, voivoship, county) VALUES (?, ?, ?, ?)");
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
//
//            stopWatchFull.stop();
//
//            long duration = stopWatchFull.getStopTime() - stopWatchFull.getStartTime();
//            double avgPerRecord = (double) duration / rowCount;
//
//            System.out.println("Wszystkie rekordy zostały zapisane w: " + duration + " ms");
//            System.out.println("Średni czas zapisu jednego rekordu to: " + avgPerRecord + " ms");
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            mySQL.close(statement);
//            mySQL.close(connection);
//        }

        // 3. ORM -> hibernate
//        Session session = null;
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(csvFileName));
//            reader.readLine();
//            StopWatch stopWatch = new StopWatch();
//            stopWatch.start();
//            session = HibernateUtil.getSessionFactory().openSession();
//            Transaction transaction = session.beginTransaction();
//            while ((record = reader.readLine()) != null) {
//                String[] data = record.split(";");
//                Code code = new Code();
//                code.setPostCode(data[0]);
//                code.setAdress(data[1]);
//                code.setVoivoship(data[2]);
//                code.setCounty(data[3]);
//                session.save(code);
//            }
//            transaction.commit();
//            stopWatch.stop();
//            System.out.println("Czas zapisu danych z użyciem HIBERNATE wynosi: " + (stopWatch.getStopTime() - stopWatch.getStartTime()) + " ms");
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }finally {
//            session.close();
//        }

    }
}