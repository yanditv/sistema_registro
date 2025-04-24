/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

/*
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
*/        
import java.util.ArrayList;
import java.util.List;
import util.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import modelo.Persona;

/**
 *
 * @author Jheyson Gaona
 */
public class PersonaDAO {
    // Inicia una sesión de trabajo con la base de datos
    EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
    
    public PersonaDAO(){
    }
    
    
    // Se emplea este metodo para poder agregar la persona a la database
    // [0] Registro exitoso, [1] Fallo de registro
    public List<Persona> AgregarPersona(Persona persona) {
        var lista = new ArrayList<Persona>();
        return lista ;
    }
    
    // [0] ya existe la Persona, [1] No existe la persona, registro exitoso
    // [2] Hubo un error inesperado
    public int VerificarAgregarPersona(Persona persona) {
        System.out.println("Capa de datos");
        int resultado = 0;
        try {
            Persona personaExiste = em.createQuery(
                "SELECT p FROM Persona p WHERE p.numIdentificacion = :numId", Persona.class)
                .setParameter("numId", persona.getNumIdentificacion())
                .getSingleResult();
            
            if(personaExiste != null){
                System.out.println("YA EXISTE LA PERSONA");
                return resultado;
            }

        } catch (NoResultException nre) {
            // Se inicia la transicion
            em.getTransaction().begin();
            // Se inserta la persona
            em.persist(persona);
            // Confirma y guarda los cambios
            em.getTransaction().commit();
            resultado = 1;
            System.out.println("AGREGANDO NUEVA PERSONA");
        } catch (Exception ex){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            System.out.println("Error: " + ex.getMessage());
            resultado = 2;
        } finally {
            em.close();
        }
        return resultado;
    }
    public List<Persona> ListarPersona() {
        List<Persona> lista = new ArrayList<>();
        try {
            lista = em.createQuery("SELECT p FROM Persona p", Persona.class).getResultList();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            em.close();
        }
        return lista;
    }
}
