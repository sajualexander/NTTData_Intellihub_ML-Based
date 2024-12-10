import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CsvToDatabase {

    public static void main(String[] args) {
        // Path to the CSV file
        String csvFilePath = "/Users/saju.alexander/Intellihub/Selenium/elements.csv"; // Replace with your actual file path

        // Database connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/html_elements_db"; // Your DB URL
        String username = "root";  // Your DB username
        String password = "mysqlserver";  // Your DB password

        // SQL Query to insert data into the database
        String insertSQL = "INSERT INTO html_elements "
                + "(tag, element, element_id, type, class_name, name, aria_autocomplete, title, href, text, value, aria_label) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Initialize DB connection
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {

            // Prepare the SQL statement
            PreparedStatement statement = connection.prepareStatement(insertSQL);

            String[] rowData;
            boolean firstRow = true;  // Skip header

            // Read CSV file line by line
            while ((rowData = csvReader.readNext()) != null) {
                if (firstRow) {
                    firstRow = false;  // Skip the header row
                    continue;
                }

                // Map CSV columns to table columns
                String tag = rowData[0];  // "tag"
                String element = rowData[1];  // "element"
                String element_id = rowData[2].equals("None") ? null : rowData[2];  // "id"
                String type = rowData[3].equals("None") ? null : rowData[3];  // "type"
                String class_name = rowData[4].equals("None") ? null : rowData[4];  // "class"
                String name = rowData[5].equals("None") ? null : rowData[5];  // "name"
                String ariaAutocomplete = rowData[6].equals("None") ? null : rowData[6];  // "aria-autocomplete"
                String title = rowData[7].equals("None") ? null : rowData[7];  // "title"
                String href = rowData[8].equals("None") ? null : rowData[8];  // "href"
                String text = rowData[9].equals("None") ? null : rowData[9];  // "text"
                String value = rowData[10].equals("None") ? null : rowData[10];  // "value"
                String ariaLabel = rowData[11].equals("None") ? null : rowData[11];  // "aria-label"

                // Set parameters for the prepared statement
                statement.setString(1, tag);
                statement.setString(2, element);
                statement.setString(3, element_id);
                statement.setString(4, type);
                statement.setString(5, class_name);
                statement.setString(6, name);
                statement.setString(7, ariaAutocomplete);
                statement.setString(8, title);
                statement.setString(9, href);
                statement.setString(10, text);
                statement.setString(11, value);
                statement.setString(12, ariaLabel);

                // Execute the insert query
                statement.executeUpdate();
            }

            System.out.println("Data has been inserted successfully into the database!");

        } catch (SQLException | IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
