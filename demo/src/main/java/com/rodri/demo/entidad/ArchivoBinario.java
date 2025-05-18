package com.rodri.demo.entidad;

import java.io.Serializable;

public class ArchivoBinario implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;
    private String tipoArchivo;
    private byte[] contenido;
    private long capacidad;

    // Constructores
    public ArchivoBinario() {
    }

    public ArchivoBinario(String nombre, String tipoArchivo, byte[] contenido) {
        this.nombre = nombre;
        this.tipoArchivo = tipoArchivo;
        this.contenido = contenido;
        this.capacidad = contenido != null ? contenido.length : 0;
    }

    public ArchivoBinario(String nombre, String tipoArchivo, byte[] contenido, long capacidad) {
        this.nombre = nombre;
        this.tipoArchivo = tipoArchivo;
        this.contenido = contenido;
        this.capacidad = capacidad;
    }

    public String getTipoArchivo() {
        return this.tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public long getCapacidad() {
        return this.capacidad;
    }

    public void setCapacidad(long capacidad) {
        this.capacidad = capacidad;
    }

    public ArchivoBinario id(int id) {
        setId(id);
        return this;
    }

    public ArchivoBinario nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    public ArchivoBinario tipoArchivo(String tipoArchivo) {
        setTipoArchivo(tipoArchivo);
        return this;
    }

    public ArchivoBinario contenido(byte[] contenido) {
        this.contenido = contenido;
        this.capacidad = contenido != null ? contenido.length : 0;
        return this;
    }

    public ArchivoBinario capacidad(long capacidad) {
        setCapacidad(capacidad);
        return this;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    // Overrides
    @Override
    public String toString() {
        return "ArchivoBinario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipoArchivo='" + tipoArchivo + '\'' +
                ", capacidad=" + capacidad + " bytes" +
                '}';
    }
}