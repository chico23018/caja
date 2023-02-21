package caja.conezione;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.Query;

import caja.dao.CajaDao;
import caja.dao.impl.CajaDaoImple;
import caja.model.Caja;

public class prueva {
	public static void main(String[] args) {
		EntityManager em = Jpa.getEntityManagerFactory().createEntityManager();
		CajaDao m = new  CajaDaoImple();
		Caja n = new Caja();
//		n.setTipo("m");
//		n.setTamano("123x120x50");
//		n.setCantidad("50");
//		m.inseri(n);
	m.allFind();


	}

}
