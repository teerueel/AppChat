package tds.appchat.persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
    
    @Override
    public IAdaptadorUsuarioDAO getUsuarioDAO() {
        return AdaptadorUsuarioTDS.getInstance();
    }

    @Override
    public IAdaptadorContactoDAO getContactoDAO() {
        return AdaptadorContactoIndividualTDS.getInstance();
    }

    @Override
    public IAdaptadorMensajeDAO getMensajeDAO() {
        return AdaptadorMensajeTDS.getInstance();
    }

}
