package tds.appchat.persistencia;

import java.util.Hashtable;

public enum PoolDAO {
    INSTANCIA;

    private Hashtable<Integer,Object> pool;

    private PoolDAO() {
        pool = new Hashtable<Integer,Object>();
    }
    
    public Object getObjeto(int id) {
		return pool.get(id);
	} // devuelve null si no encuentra el objeto

	public void addObjeto(int id, Object objeto) {
		pool.put(id, objeto);
	}

	public boolean contiene(int id) {
		return pool.containsKey(id);
	}

}
