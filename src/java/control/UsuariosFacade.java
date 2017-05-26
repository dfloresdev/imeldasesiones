package control;

import entidad.Usuarios;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gris Gomez
 */

public class UsuariosFacade extends AbstractFacade<Usuarios> {
    
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }   
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoFinalPU");
//    @Resource
////    private UserTransaction utx;
    UsuariosJpaController userJpa = new UsuariosJpaController(emf);
    Usuarios usuario;
    
//    public void crearUsuario(UsuarioPojo userPojo){
//        usuario = new Usuarios();
//        llenaDatosUsuario(userPojo, usuario);
//        try{
//            System.out.println("Me voy a Jpa a crear usuario");
//            userJpa.create(usuario);
//        } catch (RollbackFailureException ex) {
//        } catch (Exception ex){            
//        }
//    }

    public UsuarioPojo buscarUsuario(String unombre, String ucontrasenia){
        UsuarioPojo userPojo;
        boolean valido = false;
        
        System.out.println("id en busca usuario facade:");
        System.out.println("unombre: " + unombre);
        System.out.println("ucontrasenia: " + ucontrasenia);
        
        usuario = new Usuarios();
        usuario = userJpa.findByUser(unombre);
        System.out.println("Usuario hallado:" + usuario);
        
        if (usuario != null){
            valido = validaUsuario(usuario, ucontrasenia);
            if (valido)
                userPojo = consigueDatosUsuario(usuario);
            else 
                userPojo = null;
        } else
            userPojo = null;
        return userPojo;
            
    }
    
    private boolean validaUsuario(Usuarios user, String pwd){
        String actPwd;
        actPwd = user.getUcontrasenia();
        if (actPwd.equals(pwd))
            return true;
        else
            return false;
    }
    
    private UsuarioPojo consigueDatosUsuario(Usuarios usuario){
        UsuarioPojo userPojo = new UsuarioPojo();
        userPojo.setUid(usuario.getUid());
        userPojo.setUnombre(usuario.getUnombre());
        userPojo.setUcontrasenia(usuario.getUcontrasenia());
        
        return userPojo;
    }    
}
