package pl.smyk;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String csvFileName = "C:\\Users\\Kamil\\Desktop\\kody.csv";
        String dbUrl = "jdbc:postgresql://localhost:5432/mydb";
        String username = "postgres";
        String password = "admin";
        Connection connection = null;

        // 1. zapis całej kolekcji -> wczytanie całej zawartości, zapis całej kolekcji, koniec połączenia z bazą
        // 2. zapis pojedyńczego rekordu -> wyczytać jeden rekord, zmierzyć czas zapisu, koniec połączenia z bazą (procedure powtórzyć na wszystkich rekordach)
        // + 2 wybrane metody
        // 3. ORM -> hibernate
        // 4. bulk copy - komenda copy


        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            connection.setAutoCommit(false);

            String createTableSQL = "CREATE TABLE IF NOT EXISTS kody (id SERIAL PRIMARY KEY, post_code VARCHAR (255), adress VARCHAR(255), voivoship VARCHAR(255), county VARCHAR(255))";
            connection.createStatement().execute(createTableSQL);

            String insertSql = "INSERT INTO kody (post_code, adress, voivoship, county) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSql);

            FileReader fileReader = new FileReader(csvFileName);
            BufferedReader reader = new BufferedReader(fileReader);

            String record = null;

            reader.readLine();

            long startTime = System.currentTimeMillis() / 1000; // początek procedury zapisu danych
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

                statement.executeUpdate();
            }

            connection.commit();
            connection.close();
            long endTime = System.currentTimeMillis() / 1000; // koniec procedury zapisu danych oraz połączenie z bazą zostaje zamknięte
            long duration = endTime - startTime;
            System.out.println("Czas zapisu danych do bazy wynosi: " + duration + "s");


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