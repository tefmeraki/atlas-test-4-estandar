package com.biblioteca.model;

import java.time.LocalDate;

public class Prestamo {
    private int id;
    private int socioId;
    private int libroId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDevolucion; // null si activo

    public Prestamo() {}

    public Prestamo(int id, int socioId, int libroId, LocalDate fechaPrestamo, LocalDate fechaVencimiento, LocalDate fechaDevolucion) {
        this.id = id;
        this.socioId = socioId;
        this.libroId = libroId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Prestamo(int socioId, int libroId, LocalDate fechaPrestamo, LocalDate fechaVencimiento) {
        this.socioId = socioId;
        this.libroId = libroId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSocioId() { return socioId; }
    public void setSocioId(int socioId) { this.socioId = socioId; }

    public int getLibroId() { return libroId; }
    public void setLibroId(int libroId) { this.libroId = libroId; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public boolean isActivo() {
        return fechaDevolucion == null;
    }

    public boolean isRetrasado(LocalDate hoy) {
        return isActivo() && hoy.isAfter(fechaVencimiento);
    }
}