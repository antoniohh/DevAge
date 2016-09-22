package com.antoniohorrillo.devage.model;

import java.io.Serializable;

/**
 * Created by antoniohh on 2/09/16.
 */
public class Developer implements Serializable {

    /**
     * Variables.
     */
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String especialidad;

    /**
     * Constructor de la clase Developer.
     * @param dni
     * @param nombre
     * @param apellidos
     * @param email
     * @param especialidad
     */
    public Developer(String dni, String nombre, String apellidos, String email, String especialidad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.especialidad = especialidad;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Inicio de los métodos Getter and Setter de los Atributos de la clase Developer.
    ////////////////////////////////////////////////////////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Fin de los métodos Getter and Setter de los Atributos de la clase Developer.
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
