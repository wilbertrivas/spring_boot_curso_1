package com.cursoJava.cursoJava.controllers;

import com.cursoJava.cursoJava.dao.UsuarioDao;
import com.cursoJava.cursoJava.models.Usuario;
import com.cursoJava.cursoJava.utils.JWTUtils;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtils jwtUtils;

    //Listar usuarios
    @RequestMapping(value = "api/listUsuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value ="Authorization") String token){
        if(!(validarToken(token))){
            return null;
        }else{
            return usuarioDao.getUsuarios();
        }
    }
    //Eliminar Usuarios
    @RequestMapping(value = "api/usuario/{id}",method = RequestMethod.DELETE)
    public void eliminarUsuario(@RequestHeader(value ="Authorization") String token,@PathVariable Long id){
        if(!(validarToken(token))){
            return ;
        }else{
            usuarioDao.eliminar(id);
        }
    }
    //Registrar Usuarios
    @RequestMapping(value = "api/registrarUsuario", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        usuario.setPassword(argon2.hash(1, 1024, 1,usuario.getPassword()));
        usuarioDao.registrar(usuario);
    }

    //Metodo para validar el Token que viene desde el cliente.
    private boolean validarToken(String token){
        String usuarioId=jwtUtils.getKey(token);
        return usuarioId != null;
    }
}
