package tds.appchat.persistencia;

public abstract class FactoriaDAO {

    private static FactoriaDAO unicaInstancia;
	
	public static final String DAO_TDS = "tds.appchat.persistencia.TDSFactoriaDAO";
		
	/** 
	 * Crea un tipo de factoria DAO.
	 * Solo existe el tipo TDSFactoriaDAO
	 */
	public static FactoriaDAO getInstancia(String tipo) throws DAOException{
		if (unicaInstancia == null)
			try { 
				Class<?> clase = Class.forName(tipo);
				unicaInstancia = (FactoriaDAO) clase.getDeclaredConstructor().newInstance();
			} catch (Exception e) {	
				throw new DAOException(e.getMessage());
			} 
		return unicaInstancia;
	}


	public static FactoriaDAO getInstancia() throws DAOException{
			if (unicaInstancia == null) return getInstancia(FactoriaDAO.DAO_TDS);
					else return unicaInstancia;
		}

	/* Constructor */
	protected FactoriaDAO (){}
		
		
	// Metodos factoria que devuelven adaptadores que implementen estos interfaces
	public abstract IAdaptadorUsuarioDAO getUsuarioDAO();
	public abstract IAdaptadorContactoDAO getContactoDAO();
	public abstract IAdaptadorMensajeDAO getMensajeDAO();

}
