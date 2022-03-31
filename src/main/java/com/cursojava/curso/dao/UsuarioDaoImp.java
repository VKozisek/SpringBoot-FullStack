package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public List<Usuario> getUsuarios() {

        String query = "FROM Usuario";
        List<Usuario> resultado = em.createQuery(query).getResultList();
        return resultado;
    }

    @Override
    public Usuario getUsuario(Long idUsuario) {
        return null;
    }

    @Override
    public Usuario deleteUsuario(Long id) {

        Usuario objUsuario = em.find(Usuario.class,id);
        em.remove(objUsuario);
        return objUsuario;
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        em.merge(usuario);
    }

    @Override
    public Usuario editUsuario(Usuario usuario) {
        em.merge(usuario);

        return null;
    }

    @Override
    public Usuario obtenerUsuarioXCredenciales(Usuario reqUsuario) {
        //String query = "FROM Usuario WHERE email = :email AND password = :password";
        String query = "FROM Usuario WHERE email = :email";
        List<Usuario> lista = em.createQuery(query)
                .setParameter("email", reqUsuario.getEmail())
                .getResultList();

        if(lista.isEmpty()){
            return null;
        }

        String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(passwordHashed, reqUsuario.getPassword())){
            return lista.get(0);
        }
        return null;
    }
}
