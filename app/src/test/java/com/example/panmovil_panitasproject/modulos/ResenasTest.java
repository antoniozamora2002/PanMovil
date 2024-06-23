package com.example.panmovil_panitasproject.modulos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ResenasTest {

    private Resenas resena;

    @BeforeEach
    public void setUp() {
        resena = new Resenas("1", "100", "usuario@example.com", "5", "Excelente producto", "200", "Pan Integral");
    }

    @Test
    public void testGetReId() {
        assertEquals("1", resena.getReId());
    }

    @Test
    public void testSetReId() {
        resena.setReId("2");
        assertEquals("2", resena.getReId());
    }

    @Test
    public void testGetUsumoId() {
        assertEquals("100", resena.getUsumoId());
    }

    @Test
    public void testSetUsumoId() {
        resena.setUsumoId("101");
        assertEquals("101", resena.getUsumoId());
    }

    @Test
    public void testGetUsumoCorreo() {
        assertEquals("usuario@example.com", resena.getUsumoCorreo());
    }

    @Test
    public void testSetUsumoCorreo() {
        resena.setUsumoCorreo("nuevo_usuario@example.com");
        assertEquals("nuevo_usuario@example.com", resena.getUsumoCorreo());
    }

    @Test
    public void testGetReEstrellas() {
        assertEquals("5", resena.getReEstrellas());
    }

    @Test
    public void testSetReEstrellas() {
        resena.setReEstrellas("4");
        assertEquals("4", resena.getReEstrellas());
    }

    @Test
    public void testGetReDescripcion() {
        assertEquals("Excelente producto", resena.getReDescripcion());
    }

    @Test
    public void testSetReDescripcion() {
        resena.setReDescripcion("Producto aceptable");
        assertEquals("Producto aceptable", resena.getReDescripcion());
    }

    @Test
    public void testGetProId() {
        assertEquals("200", resena.getProId());
    }

    @Test
    public void testSetProId() {
        resena.setProId("201");
        assertEquals("201", resena.getProId());
    }

    @Test
    public void testGetProNombre() {
        assertEquals("Pan Integral", resena.getProNombre());
    }

    @Test
    public void testSetProNombre() {
        resena.setProNombre("Pan Baguette");
        assertEquals("Pan Baguette", resena.getProNombre());
    }

}
