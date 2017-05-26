package control;

import entidad.Userstemp;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gris Gomez
 */
@Named(value = "usertFacade")
@RequestScoped
public class UserstempFacade {

//    @Resource
//    private UserTransaction utx;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoFinalPU");

    UsertempJpaController userJpa = new UsertempJpaController(emf);
    Userstemp usuario;

    public void crearUserstemp(UserstempPojo userPojo) {       
        usuario = new Userstemp();
        llenaDatosUserstemp (userPojo, usuario);
        try {
            System.out.println("Me voy a Jpa a crear usuariotemp");
            userJpa.create(usuario, null);
            System.out.println("creo usuario en jpa");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void actualizaUserstempActivo(UserstempPojo userPojo) {
        String correo = userPojo.getCorreo();
        usuario = userJpa.findByEmail(correo);
        usuario.setActivo(userPojo.getActivo());

        try {
            System.out.println("Me voy a Jpa a actualizar activo en usuariotemp");
            userJpa.edit(usuario);
        }catch (Exception ex){

        }
    }

    public void actualizarUserstempEstado (UserstempPojo userPojo) {
        String correo = userPojo.getCorreo();
        usuario = userJpa.findByEmail(correo);
        usuario.setEstado(userPojo.getEstado());
        System.out.println();

        try {
            System.out.println("Me voy a Jpa a actualizar estado en usuaiotemp");
            userJpa.edit(usuario);
        } catch (Exception ex) {

        }
    }

    public void llenaDatosUserstemp (UserstempPojo userPojo, Userstemp usuario) {
        usuario.setAp(userPojo.getAp());
        usuario.setAm(userPojo.getAm());
        usuario.setNombre(userPojo.getNombre());
        usuario.setCorreo(userPojo.getCorreo());
        usuario.setActivo(userPojo.getActivo());
        usuario.setEstado(userPojo.getEstado());
        usuario.setHash(userPojo.getHash());
        usuario.setPasswordTemp(userPojo.getPasswordTemp());
    }

    public UserstempPojo buscaUserstemp (String correo) {
        UserstempPojo userPojo;
        boolean valido = false;
        System.out.println("id en busca usuariotemp en usuario facade: ");
        System.out.println("correo: " + correo);

        usuario = new Userstemp();
        usuario = userJpa.findByEmail(correo);

        if (usuario != null) {
            userPojo = consigueDatosUserstemp(usuario);
        } else
            userPojo = null;
        return userPojo;
    }

    private UserstempPojo consigueDatosUserstemp (Userstemp usuario) {
        UserstempPojo userPojo = new UserstempPojo();
        userPojo.setId(usuario.getId());
        userPojo.setAp(usuario.getAp());
        userPojo.setAm(usuario.getAm());
        userPojo.setNombre(usuario.getNombre());
        userPojo.setCorreo(usuario.getCorreo());
        userPojo.setActivo(usuario.getActivo());
        userPojo.setEstado(usuario.getEstado());
        userPojo.setHash(usuario.getHash());
        userPojo.setPasswordTemp(usuario.getPasswordTemp());
        return userPojo;
    }
}
