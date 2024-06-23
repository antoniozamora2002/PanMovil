package com.example.panmovil_panitasproject.modulos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class PromocionesTest {

    private Promociones promocion;

    @BeforeEach
    public void setUp() {
        promocion = new Promociones("1", "Pan Integral", "Descuento Verano", "Descuento del 20%", "20%", "10.0", "2024-06-01", "2024-06-30", "http://example.com/image.jpg");
    }

    @Test
    public void testGetPromoId() {
        assertEquals("1", promocion.getPromoId());
    }

    @Test
    public void testSetPromoId() {
        promocion.setPromoId("2");
        assertEquals("2", promocion.getPromoId());
    }

    @Test
    public void testGetProNombre() {
        assertEquals("Pan Integral", promocion.getProNombre());
    }

    @Test
    public void testSetProNombre() {
        promocion.setProNombre("Baguette");
        assertEquals("Baguette", promocion.getProNombre());
    }

    @Test
    public void testGetTpromoNombre() {
        assertEquals("Descuento Verano", promocion.getTpromoNombre());
    }

    @Test
    public void testSetTpromoNombre() {
        promocion.setTpromoNombre("Descuento Invierno");
        assertEquals("Descuento Invierno", promocion.getTpromoNombre());
    }

    @Test
    public void testGetTpromoDescripcion() {
        assertEquals("Descuento del 20%", promocion.getTpromoDescripcion());
    }

    @Test
    public void testSetTpromoDescripcion() {
        promocion.setTpromoDescripcion("Descuento del 30%");
        assertEquals("Descuento del 30%", promocion.getTpromoDescripcion());
    }

    @Test
    public void testGetPromoDescuento() {
        assertEquals("20%", promocion.getPromoDescuento());
    }

    @Test
    public void testSetPromoDescuento() {
        promocion.setPromoDescuento("25%");
        assertEquals("25%", promocion.getPromoDescuento());
    }

    @Test
    public void testGetProPrecioUnitario() {
        assertEquals("10.0", promocion.getProPrecioUnitario());
    }

    @Test
    public void testSetProPrecioUnitario() {
        promocion.setProPrecioUnitario("15.0");
        assertEquals("15.0", promocion.getProPrecioUnitario());
    }

    @Test
    public void testGetPromoFechaini() {
        assertEquals("2024-06-01", promocion.getPromoFechaini());
    }

    @Test
    public void testSetPromoFechaini() {
        promocion.setPromoFechaini("2024-07-01");
        assertEquals("2024-07-01", promocion.getPromoFechaini());
    }

    @Test
    public void testGetPromoFechafin() {
        assertEquals("2024-06-30", promocion.getPromoFechafin());
    }

    @Test
    public void testSetPromoFechafin() {
        promocion.setPromoFechafin("2024-07-31");
        assertEquals("2024-07-31", promocion.getPromoFechafin());
    }

    @Test
    public void testGetProImagen() {
        assertEquals("http://example.com/image.jpg", promocion.getProImagen());
    }

    @Test
    public void testSetProImagen() {
        promocion.setProImagen("http://example.com/newimage.jpg");
        assertEquals("http://example.com/newimage.jpg", promocion.getProImagen());
    }

}
