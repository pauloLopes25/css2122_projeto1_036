package db.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import business.Certificado.Certificado;
import business.Instrutor.Instrutor;
import facade.exceptions.ApplicationException;


public class CreateDatabase {

	private CreateDatabase () {
	}

	public static void main(String[] args) throws ApplicationException {


		HashMap<String, String> properties = new HashMap<>();
		properties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
		properties.put("javax.persistence.schema-generation.create-source", "metadata");
		properties.put("javax.persistence.schema-generation.drop-source", "metadata");
		properties.put("javax.persistence.sql-load-script-source", "META-INF/load-script.sql"); 

   	 	Persistence.generateSchema("saudeges-jpa", properties);
   	 	
		EntityManagerFactory emf;
		try {
			emf = Persistence.createEntityManagerFactory("saudeges-jpa");
		} catch (Exception e) {
			throw new ApplicationException("Erro ao conectar com a data base");
		}
		EntityManager em;
		em= emf.createEntityManager();
   	 em.getTransaction().begin();
		List <Certificado> cert = new ArrayList<>();
		cert.add(Certificado.FIS0);
		cert.add(Certificado.FIS1);
		cert.add(Certificado.MASG);
		Instrutor i = new Instrutor(1,"Uma", cert);
		try {
			em.persist(i);
			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new ApplicationException("ola", e);
		}
		
	   	 em.getTransaction().begin();
			cert = new ArrayList<>();
			cert.add(Certificado.FIS0);
			cert.add(Certificado.MASG);
			i = new Instrutor(2,"Dona", cert);
			try {
				em.persist(i);
				em.getTransaction().commit();

			} catch (Exception e) {
				em.getTransaction().rollback();
				throw new ApplicationException("ola", e);
			}
			
		   	 em.getTransaction().begin();
				cert = new ArrayList<>();
				cert.add(Certificado.MASG);
				i = new Instrutor(3,"Tina", cert);
				try {
					em.persist(i);
					em.getTransaction().commit();

				} catch (Exception e) {
					em.getTransaction().rollback();
					throw new ApplicationException("ola", e);
				}
   	 	

	}
}