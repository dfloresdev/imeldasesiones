/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import entidad.Userstemp;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gris Gomez
 */

public class UsertempJpaController implements Serializable {

    public UsertempJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Userstemp usertemp, UserTransaction utx) {
        EntityManager em = null;       
        
        try {            
            System.out.println("crear usuario");
            em = getEntityManager();
            System.out.println("creo instancia");
            //em.getTransaction().begin();
            em.getTransaction().begin();
            System.out.println(".begin");
            //System.out.println(usertemp);
            em.persist(usertemp);
            System.out.println("despues de persist");
            em.getTransaction().commit();
            //em.getTransaction().commit();
            System.out.println("finalizo commit");
            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Userstemp usertemp) throws NonexistentEntityException,  Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usertemp = em.merge(usertemp);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usertemp.getId();
                if (findUsertemp(id) == null) {
                    throw new NonexistentEntityException("The usertemp with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Userstemp usertemp;
            try {
                usertemp = em.getReference(Userstemp.class, id);
                usertemp.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usertemp with id " + id + " no longer exists.", enfe);
            }
            em.remove(usertemp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Userstemp> findUsertempEntities() {
        return findUsertempEntities(true, -1, -1);
    }

    public List<Userstemp> findUsertempEntities(int maxResults, int firstResult) {
        return findUsertempEntities(false, maxResults, firstResult);
    }

    private List<Userstemp> findUsertempEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Userstemp.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Userstemp findUsertemp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Userstemp.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsertempCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Userstemp> rt = cq.from(Userstemp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    //metodo agregado
    public Userstemp findByEmail (String correo){
        Userstemp user = null;
        List<Userstemp> users;
        EntityManager em = getEntityManager();
        System.out.println("Buscando usuario por correo en jpacontroller");
        Query consulta = em.createNamedQuery("Userstemp.findByCorreo");
        consulta.setParameter("correo", correo);
        users = consulta.getResultList();

        if (!users.isEmpty()) {
            System.out.println("Lo encontro");
            System.out.println("users.get(0): " + users.get(0).getNombre());

            user  = users.get(0);
        }
        return user;
    }
}
