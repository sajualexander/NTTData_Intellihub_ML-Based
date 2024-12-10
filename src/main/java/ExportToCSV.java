import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExportToCSV {

    public static void main(String[] args) {
        // JDBC connection information
        String jdbcURL = "jdbc:mysql://localhost:3306/html_elements_db";
        String username = "root";
        String password = "mysqlserver";

        // The query to fetch data from the table
        String csvFilePath = "/Users/saju.alexander/Intellihub/Selenium/testdataset.csv"; // Path to save the CSV file

        // The query to select data from your MySQL table
        String query = "SELECT * FROM html_elements"; // Modify this query for your table

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            // Create a statement to execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Get the metadata of the table (column names)
            int columnCount = resultSet.getMetaData().getColumnCount();

            // FileWriter to write CSV data
            FileWriter fileWriter = new FileWriter(csvFilePath);

            // Write column headers to the CSV file
            for (int i = 1; i <= columnCount; i++) {
                fileWriter.append(resultSet.getMetaData().getColumnName(i));
                if (i < columnCount) {
                    fileWriter.append(",");
                }
            }
            fileWriter.append("\n");

            // Write table data to the CSV file
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    fileWriter.append(resultSet.getString(i)); // Write the column value
                    if (i < columnCount) {
                        fileWriter.append(","); // Separate values with a comma
                    }
                }
                fileWriter.append("\n"); // Move to the next line for the next row
            }

            // Close the FileWriter and ResultSet
            fileWriter.flush();
            fileWriter.close();
            resultSet.close();

            System.out.println("Data has been successfully exported to " + csvFilePath);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
