package caja.dao;

import java.util.List;

import caja.model.Caja;

public interface CajaDao {
	public void inseri(Caja n);
	public void update(Caja n);
	public void eliminare (int id);
	public List<Caja> allFind();
	public Caja Usar(int id,int usar);
	public Caja agregar(int id,int agregar);
	public Caja find(int id);

}
