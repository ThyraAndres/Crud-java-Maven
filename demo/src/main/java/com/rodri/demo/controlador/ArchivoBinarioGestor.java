package com.rodri.demo.controlador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.rodri.demo.entidad.ArchivoBinario;

public class ArchivoBinarioGestor {
    private static final ArrayList<ArchivoBinario> archivos = new ArrayList<>();
    private static int ultimoId = 0;

    // CRUD

    public static void crearArchivo(ArchivoBinario archivo) {
        archivo.setId(++ultimoId);
        archivos.add(archivo);
    }

    public static List<ArchivoBinario> obtenerTodos() {
        return new ArrayList<>(archivos);
    }

    public static ArchivoBinario buscarPorId(int id) {
        for (ArchivoBinario archivo : archivos) {
            if (archivo.getId() == id) {
                return archivo;
            }
        }
        return null;
    }

    public static boolean actualizarArchivo(ArchivoBinario archivoActualizado) {
        for (int i = 0; i < archivos.size(); i++) {
            if (archivos.get(i).getId() == archivoActualizado.getId()) {
                archivos.set(i, archivoActualizado);
                return true;
            }
        }
        return false;
    }

    public static boolean eliminarArchivo(int id) {
        return archivos.removeIf(a -> a.getId() == id);
    }

    // MÉTODOS ADICIONALES

    public static void cargarArchivo(String nombre, String tipoArchivo, byte[] contenido) {
        crearArchivo(new ArchivoBinario(nombre, tipoArchivo, contenido));
    }

    public static List<ArchivoBinario> buscarPorNombre(String nombreBusqueda) {
        List<ArchivoBinario> resultados = new ArrayList<>();
        for (ArchivoBinario archivo : archivos) {
            if (archivo.getNombre().toLowerCase().contains(nombreBusqueda.toLowerCase())) {
                resultados.add(archivo);
            }
        }
        return resultados;
    }

    public static List<ArchivoBinario> filtrarPorTipo(String tipoArchivo) {
        List<ArchivoBinario> resultados = new ArrayList<>();
        for (ArchivoBinario archivo : archivos) {
            if (archivo.getTipoArchivo().equalsIgnoreCase(tipoArchivo)) {
                resultados.add(archivo);
            }
        }
        return resultados;
    }

    public static int contarArchivos() {
        return archivos.size();
    }

    public static long calcularCapacidadTotal() {
        long total = 0;
        for (ArchivoBinario archivo : archivos) {
            total += archivo.getCapacidad();
        }
        return total;
    }

    // MÉTODOS PARA FLUJOS BINARIOS

    public static void guardarEnDisco(String rutaArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(rutaArchivo)))) {

            oos.writeInt(archivos.size());
            for (ArchivoBinario archivo : archivos) {
                oos.writeObject(archivo);
                oos.writeInt(archivo.getContenido() != null ? archivo.getContenido().length : 0);
                if (archivo.getContenido() != null) {
                    oos.write(archivo.getContenido());
                }
            }
        }
    }

    public static void cargarDesdeDisco(String rutaArchivo) throws IOException, ClassNotFoundException {
        ArrayList<ArchivoBinario> listaTemp = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(rutaArchivo)))) {

            int cantidad = ois.readInt();
            for (int i = 0; i < cantidad; i++) {
                ArchivoBinario archivo = (ArchivoBinario) ois.readObject();
                int longitud = ois.readInt();
                if (longitud > 0) {
                    byte[] contenido = new byte[longitud];
                    ois.readFully(contenido);
                    archivo.setContenido(contenido);
                }
                listaTemp.add(archivo);
            }
        }

        archivos.clear();
        archivos.addAll(listaTemp);
        actualizarUltimoId();
    }

    private static void actualizarUltimoId() {
        ultimoId = archivos.stream()
                .mapToInt(ArchivoBinario::getId)
                .max()
                .orElse(0);
    }
}