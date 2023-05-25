package QueringHomework;

import com.beust.ah.A;
import io.qameta.allure.Step;
import org.example.MySqlJDCButil;
import org.openqa.selenium.devtools.v110.css.model.Value;
import org.testng.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueringHomeWorkSteps {

    Connection conn = MySqlJDCButil.getConnection();
    public List namesBeforeInsertingWithoutCommit = new ArrayList<String>();

    public QueringHomeWorkSteps() throws SQLException {
    }

    @Step
    public QueringHomeWorkSteps setupSQL() throws SQLException {
            conn.setAutoCommit(false);
            System.out.println(String.format("Connected to database %s " + "successfully.", conn.getCatalog()));

        return this;
    }
    @Step
    public QueringHomeWorkSteps getCurrentRowsNumber() throws SQLException {
        String sql = "SELECT firstname FROM students";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                namesBeforeInsertingWithoutCommit.add(rs.getString("firstname"));
            }
        return this;
    }

    @Step
    public QueringHomeWorkSteps setAutoCommitFalse() throws SQLException {
        conn.setAutoCommit(false);
        return this;
    }
    @Step
    public QueringHomeWorkSteps insertNewStudentRow(String firstName,String lastName, String phone, Integer id) throws SQLException {

        String SQL = "INSERT INTO students(id,firstname,lastname, phone) "
                + "VALUES(?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, id);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, phone);
            pstmt.executeUpdate();
            return this;
        }
    @Step
    public QueringHomeWorkSteps validateNewRowNotAdded() throws SQLException {

        String sql = "SELECT firstname FROM dbo.[students]";

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Assert.assertNotEquals("Bush", rs.getString("firstname"));
            }
        return this;
    }
    @Step
    public QueringHomeWorkSteps commit() throws SQLException {
        conn.commit();
        return this;
    }
    @Step
    public QueringHomeWorkSteps validateNewRowAdded() throws SQLException {

        String sql = "SELECT firstname FROM dbo.[students]";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.last())
                {
                    Assert.assertEquals("Bush", rs.getString("firstName"));
                }
            }
        return this;
    }
    @Step
    public QueringHomeWorkSteps updateFirstname() throws SQLException {

        String sql = "UPDATE students SET firstname ='changedName' WHERE ID= (SELECT MAX(ID) FROM students)";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.insertRow();
        return this;
    }
    @Step
    public QueringHomeWorkSteps validateLastChanges() throws SQLException {
        String sql = "SELECT firstname FROM dbo.[students]";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            if(rs.last())
            {
                Assert.assertEquals("changedName", rs.getString("firstName"));
            }
        }
        return this;
    }

}
