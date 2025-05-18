package com.rodri.demo.controlador;

import java.util.ArrayList;
import java.util.List;

import com.rodri.demo.entidad.Equipo;

public class EquipoGestor {
    private static ArrayList<Equipo> equipos = new ArrayList<>();
    private static int ultimoId = 0;

    // CRUD
    public static void crearEquipo(Equipo equipo) {
        equipo.setId(++ultimoId);
        equipos.add(equipo);
    }

    public static List<Equipo> obtenerTodos() {
        return new ArrayList<>(equipos);
    }

    public static Equipo buscarPorId(int id) {
        for (Equipo e : equipos) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public static boolean actualizarEquipo(Equipo equipo) {
        for (int i = 0; i < equipos.size(); i++) {
            if (equipos.get(i).getId() == equipo.getId()) {
                equipos.set(i, equipo);
                return true;
            }
        }
        return false;
    }

    public static boolean eliminarEquipo(int id) {
        return equipos.removeIf(e -> e.getId() == id);
    }
}