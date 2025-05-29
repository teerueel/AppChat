package tds.appchat.repositorio;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import tds.appchat.modelo.*;
import tds.appchat.persistencia.DAOException;
import tds.appchat.persistencia.FactoriaDAO;
import tds.appchat.persistencia.IAdaptadorUsuarioDAO;

public enum CatalogoUsuarios {
    INSTANCIA;

    private Map<String, Usuario> usuarios;
    private FactoriaDAO dao;
    private IAdaptadorUsuarioDAO adaptadorUsuario;
    
    private CatalogoUsuarios(){
        try {

  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorUsuario = dao.getUsuarioDAO();
  			usuarios = new HashMap<String,Usuario>();
           
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
    }

    /*Recupera todos los usuarios para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
        List<Usuario> usuariosBD = adaptadorUsuario.obtenerUsuarios();
        for (Usuario user: usuariosBD) {
            
                if(user.getTelefono() != null){
               System.out.println("Usuario:" + user.getId() + " " + user.getNombre() + " " + user.getTelefono());
               usuarios.put(user.getTelefono(),user);
                }
        }
   }

   public List<Usuario> getUsuarios(){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        for(Usuario user: usuarios.values()) {
            lista.add(user);
        }
        return lista;
   }

   public Usuario obtenerUsuario(String tlf) {
        for (Usuario user: usuarios.values()) {
            if(user.getTelefono() == tlf) {
                return user;
            }
        }
        return null;
   }

   

    public void addUsuario(Usuario user) {
        
          usuarios.put(user.getTelefono(),user);
    }

    public void removeUsuario(Usuario user) {
        usuarios.remove(user.getTelefono());
    }

    public boolean tlfRegistrado(String tlf) {
        return usuarios.values().stream().anyMatch(u -> u.getTelefono().equalsIgnoreCase(tlf));
        
    }

    public boolean emailRegistrado(String email) {
        return usuarios.values().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public Optional <Usuario> autenticarUsuario(String tlf, String password) {
        return usuarios.values().stream().filter(u -> u.getTelefono().equalsIgnoreCase(tlf) 
                && u.getPassword().equals(password)).findAny();
    }

    public Optional<Usuario> getUsuario(int id) {
        return usuarios.values().stream().filter(u -> u.getId() == id).findAny();
    }

    public Optional<Usuario> buscarUsuario(String tlf) {
        return usuarios.values().stream().filter(u -> u.getTelefono().equalsIgnoreCase(tlf)).findAny();
    }







    
}

