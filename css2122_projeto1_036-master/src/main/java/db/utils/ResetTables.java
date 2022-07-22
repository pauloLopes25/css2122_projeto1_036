package db.utils;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ResetTables {
		
		public void resetDB() throws IOException {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("saudeges-jpa");
			new RunSQLScript().runScript(emf, "META-INF/resetTables.sql");
			emf.close();
		}
		
		public static void main(String[] args) throws IOException {
			new ResetTables().resetDB();
		}
	
}
