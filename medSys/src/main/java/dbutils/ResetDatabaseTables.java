package dbutils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.SQLException;


public class ResetDatabaseTables {

    public void resetCSSDerbyDB() throws IOException, SQLException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("medSysDB");
        new RunSQLScript().runScript(emf, "META-INF/resetTables.sql");
        emf.close();
    }

    public static void main(String[] args) throws IOException, SQLException {
        new ResetDatabaseTables().resetCSSDerbyDB();
    }
}
