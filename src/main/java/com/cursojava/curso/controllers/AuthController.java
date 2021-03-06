package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao objUsuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login",method = RequestMethod.POST)
    public String login(@RequestBody Usuario reqUsuario) {

        Usuario objUsuario = objUsuarioDao.obtenerUsuarioXCredenciales(reqUsuario);
        if (objUsuario != null){

            //jwtUtil.create(objUsuario.getId().toString(),objUsuario.getEmail());

            String tokenJwt = jwtUtil.create(String.valueOf(objUsuario.getId()),objUsuario.getEmail());

            return tokenJwt;
        }
        return "FAIL";
    }
}
