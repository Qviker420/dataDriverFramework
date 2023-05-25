import com.codeborne.selenide.SelenideElement;
import org.example.MySqlJDCButil;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DbTest {

    @BeforeTest
    public void setup() {
        try (Connection conn = SQLConnectionStep.getConnection()) {
            System.out.println(String.format("Connected to database %s " + "successfully.", conn.getCatalog()));
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Test
    public void dbTest() throws SQLException {
        open("https://demoqa.com/automation-practice-form");
        SelenideElement firstName = $(By.id("firstName"));
        SelenideElement lastName = $(By.id("lastName"));
        SelenideElement phoneNumber = $(By.id("userNumber"));

        String sql = "SELECT * FROM dbo.[students]";


        try (Connection conn = MySqlJDCButil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                firstName.setValue(rs.getString("firstname"));
                lastName.setValue(rs.getString("firstname"));
                phoneNumber.setValue(rs.getString("firstname"));

                firstName.clear();
                lastName.clear();
                phoneNumber.clear();
            }
        }
    }
}

