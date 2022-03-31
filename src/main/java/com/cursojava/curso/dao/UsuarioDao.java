package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> getUsuarios();
    Usuario getUsuario(Long idUsuario);
    Usuario deleteUsuario(Long id);
    void registrarUsuario(Usuario usuario);

    Usuario editUsuario(Usuario reqUsuario);

    Usuario obtenerUsuarioXCredenciales(Usuario reqUsuario);
}
