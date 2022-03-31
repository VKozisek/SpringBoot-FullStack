package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuarioController {

    //hace que la clase usuarioDaoImp se cree un objeto y la guarda en la variable - Injeccion de dependencias
    @Autowired
    private UsuarioDao objUsuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuario/{id}",method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id) {
        Usuario usuario;
        usuario = objUsuarioDao.getUsuario(id);
        return usuario;
    }

    @RequestMapping(value = "api/usuarios",method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token) {

        if(!validarToken(token)){
            return null;
        }

        return objUsuarioDao.getUsuarios();
    }

    private boolean validarToken(String token){
        String idUsuario = jwtUtil.getKey(token);

        return idUsuario != null;
    }

    @RequestMapping(value = "api/usuarios",method = RequestMethod.POST)
    public void postUsuarios(@RequestBody Usuario reqUsuario) {

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1, reqUsuario.getPassword());
        reqUsuario.setPassword(hash);
        objUsuarioDao.registrarUsuario(reqUsuario);
    }

    @RequestMapping(value = "api/usuario/{id}",method = RequestMethod.PUT)
    public Usuario editarUsuario(@PathVariable Long id) {
        Usuario usuario = objUsuarioDao.getUsuario(id);
        objUsuarioDao.editUsuario(usuario);
        return usuario;
    }

    @RequestMapping(value = "api/usuario/{id}",method = RequestMethod.DELETE)
    public Usuario eliminarUsuario(@RequestHeader(value = "Authorization") String token,
                                   @PathVariable Long id) {

        if(!validarToken(token)){
            return null;
        }

        Usuario usuario = objUsuarioDao.deleteUsuario(id);
        return usuario;
    }
}
