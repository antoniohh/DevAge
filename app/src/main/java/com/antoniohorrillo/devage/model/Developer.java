package com.antoniohorrillo.devage.model;

import java.io.Serializable;

/**
 * Created by antoniohh on 2/09/16.
 */
public class Developer implements Serializable {

    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String especialidad;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Developer(String dni, String nombre, String apellidos, String email, String especialidad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.especialidad = especialidad;
    }
}
