package com.cursoJava.cursoJava.dao;

import com.cursoJava.cursoJava.models.Usuario;
import java.util.List;

public interface UsuarioDao {
    List<Usuario>getUsuarios();

    void eliminar(Long id);

    void registrar(Usuario usuario);

    boolean verificarEmailPassword(Usuario usuario);

    Usuario obtenerUsuarioPorCrendenciales(Usuario usuario);
}
