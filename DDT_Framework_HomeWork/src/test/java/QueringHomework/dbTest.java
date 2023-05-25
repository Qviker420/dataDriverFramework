package QueringHomework;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class dbTest {
    QueringHomeWorkSteps qhSteps = new QueringHomeWorkSteps();

    public dbTest() throws SQLException {
    }

    @BeforeTest
    public void setup() throws SQLException {
        qhSteps.setupSQL();
    }
    @Test
    public void test() throws SQLException {
        qhSteps.setAutoCommitFalse()
                .insertNewStudentRow("Bush", "Chaligava", "5551122", 1020)
                .validateNewRowNotAdded()
                .commit()
                .validateNewRowAdded()
                .updateFirstname()
                .commit()
                .validateLastChanges();

    }
}
