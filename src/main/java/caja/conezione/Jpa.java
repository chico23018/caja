package caja.conezione;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Jpa {
private static final EntityManagerFactory entitymManagerFactory;
	
	static {
		entitymManagerFactory = Persistence.createEntityManagerFactory("caja");
	}
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return entitymManagerFactory;
		
	}

}
