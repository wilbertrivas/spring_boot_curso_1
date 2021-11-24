package com.cursoJava.cursoJava.dao;

import com.cursoJava.cursoJava.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional

public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> getUsuarios() {
       String query= "FROM  Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario= entityManager.find(Usuario.class,id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario); //para registrar un usuario
    }

    @Override
    public boolean verificarEmailPassword(Usuario usuario) {
        //String query= "FROM  Usuario WHERE email= :email AND password= :password ";
        String query= "FROM  Usuario WHERE email= :email";
        List<Usuario>  lista =entityManager.createQuery(query)
                    .setParameter("email", usuario.getEmail())
                    .getResultList();
        if(lista.isEmpty()){
            return false;
        }else{
            String passwordHashed = lista.get(0).getPassword();
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            return argon2.verify(passwordHashed, usuario.getPassword());
        }
         //return !lista.isEmpty();
    }

    @Override
    public Usuario obtenerUsuarioPorCrendenciales(Usuario usuario) {
        String query= "FROM  Usuario WHERE email= :email";
        List<Usuario>  lista =entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();
        if(lista.isEmpty()){
            return null;
        }else{
            String passwordHashed = lista.get(0).getPassword();
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            if(argon2.verify(passwordHashed, usuario.getPassword())){
                return lista.get(0);
            }else{
                return null;
            }
        }
    }
}
