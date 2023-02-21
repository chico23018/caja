package caja.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import caja.conezione.Jpa;
import caja.dao.CajaDao;
import caja.model.Caja;

public class CajaDaoImple implements CajaDao {
	
	private EntityManager em = Jpa.getEntityManagerFactory().createEntityManager();

	@Override
	public void inseri(Caja n) {
		String res = "";
		
		try{
			Query q = em.createQuery(
				    "SELECT c.tipo FROM Caja c WHERE c.tipo = :user")
				    .setParameter("user", n.getTipo());
			 res = (String) q.getSingleResult();
			
		}catch (NoResultException nre){
			System.out.println(res);
			}
		if (res.equals(n.getTipo())){
			JOptionPane.showMessageDialog(null, "tipo de caja ya excistente en el base datos");
		}else {
		EntityTransaction t = em.getTransaction();
		t.begin();

		em.persist(n);

		t.commit();
	em.close();
	}
	}

	@Override
	public void update(Caja n) {
		
		EntityTransaction t = em.getTransaction();
		t.begin();

		em.merge(n);

		t.commit();

	}

	@Override
	public void eliminare(int id) {
		EntityTransaction t = em.getTransaction();
		t.begin();

		Caja res = em.find(Caja.class, id);
		em.remove(res);

		t.commit();

	}

	@Override
	public List<Caja> allFind() {
		Query q = em.createNamedQuery("Caja.findAll");
	
		List<Caja> res = q.getResultList();
		System.out.println(res);
		return res;
		
	}

	@Override
	public Caja Usar(int id,int usar) {

		int agregar2,suma;
		String agregar1;
		Caja res = em.find(Caja.class, id);
		agregar2= Integer.parseInt(res.getCantidad());
		
		if(usar <= agregar2) {
			suma = agregar2-usar;
			agregar1 = String.valueOf(suma);
		Caja cargar = new Caja();
		cargar.setId(res.getId());
		cargar.setTipo(res.getTipo());
		cargar.setTamano(res.getTamano());
		cargar.setCantidad(agregar1);
		update(cargar);}
		else {

			JOptionPane.showMessageDialog(null, "No tienes suficientes cajas");

		}
		return res;
	}

	@Override
	public Caja agregar(int id, int agregar) {
		
		int agregar2,suma;
		String agregar1;
		Caja res = em.find(Caja.class, id);
		agregar2= Integer.parseInt(res.getCantidad());
		suma = agregar2+agregar;
		agregar1 = String.valueOf(suma);
		
		Caja cargar = new Caja();
		cargar.setId(res.getId());
		cargar.setTipo(res.getTipo());
		cargar.setTamano(res.getTamano());
		cargar.setCantidad(agregar1);
		update(cargar);
		return res;
	}

	@Override
	public Caja find(int id) {
		Caja res = em.find(Caja.class, id);
		
		return res;
	}

	}
