package tds.appchat.modelo;




public class Usuario {

   
    private Integer id;  
    private String nombre;
    private String telefono;
    private String saludo;
    private String email;
    private String password;
    private EstadisticasUsuario stats;
    private boolean Premium;
    String imagen;
    

    
    public Usuario() {
        this.stats = new EstadisticasUsuario();
        this.Premium    = false;
    }


    
    

    public Usuario(int id,  String nombre, String email, String password, EstadisticasUsuario stats) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.stats = stats;
        
    }

    public Usuario(int id,  String nombre, String email, String password) {
        this();
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public Usuario(int id, String nombre, String email, String password, String telefono, String saludo) {
        this(id, nombre, email, password);
        this.telefono = telefono;
        this.saludo = saludo;
    }

    public Usuario(int id, String nombre, String email, String password, String telefono, String saludo, String imagen) {
        this(id, nombre, email, password, telefono, saludo);
        this.imagen = imagen;
    }

   

    public int getId() {
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }


    

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EstadisticasUsuario getStats() {
        return this.stats;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSaludo() {
        return this.saludo;
    }  

    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }

    public boolean isPremium() {
        return this.Premium;
    }

    public void setPremium(boolean premium) {
        this.Premium = premium;
    }

    public String getImagen() {
        return this.imagen;
    }  

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }



    
    public void aumentarTiempoTotal(long tiempo) {
        this.stats.aumentarTiempoUso(tiempo);
    }

    public void actualizarRacha(boolean acierto) {
        this.stats.actualizarRacha(acierto);
    }
}
