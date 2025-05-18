package com.rodri.demo.controlador;

import java.util.ArrayList;
import java.util.List;

import com.rodri.demo.entidad.Usuario;

public class UsuarioGestor {
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static int ultimoId = 0;

    // CRUD
    public static void crearUsuario(Usuario usuario) {
        usuario.setId(++ultimoId);
        usuarios.add(usuario);
    }

    public static List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }

    public static Usuario buscarPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public static boolean actualizarUsuario(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuario.getId()) {
                usuarios.set(i, usuario);
                return true;
            }
        }
        return false;
    }

    public static boolean eliminarUsuario(int id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }
}