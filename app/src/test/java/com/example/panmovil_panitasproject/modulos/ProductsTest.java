package com.example.panmovil_panitasproject.modulos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductsTest {

    private Products product;

    @BeforeEach
    public void setUp() {
        product = new Products("1", "Pan", "Pan integral", "1", "10.0", "http://example.com/image.jpg");
    }

    @Test
    public void testGetProId() {
        assertEquals("1", product.getProId());
    }

    @Test
    public void testSetProId() {
        product.setProId("2");
        assertEquals("2", product.getProId());
    }

    @Test
    public void testGetProNombre() {
        assertEquals("Pan", product.getProNombre());
    }

    @Test
    public void testSetProNombre() {
        product.setProNombre("Baguette");
        assertEquals("Baguette", product.getProNombre());
    }

    @Test
    public void testGetProDescripcion() {
        assertEquals("Pan integral", product.getProDescripcion());
    }

    @Test
    public void testSetProDescripcion() {
        product.setProDescripcion("Pan francés");
        assertEquals("Pan francés", product.getProDescripcion());
    }

    @Test
    public void testGetTproId() {
        assertEquals("1", product.getTproId());
    }

    @Test
    public void testSetTproId() {
        product.setTproId("2");
        assertEquals("2", product.getTproId());
    }

    @Test
    public void testGetProPrecioUnitario() {
        assertEquals("10.0", product.getProPrecioUnitario());
    }

    @Test
    public void testSetProPrecioUnitario() {
        product.setProPrecioUnitario("15.0");
        assertEquals("15.0", product.getProPrecioUnitario());
    }

    @Test
    public void testGetUrlImagen() {
        assertEquals("http://example.com/image.jpg", product.getUrlImagen());
    }

    @Test
    public void testSetUrlImagen() {
        product.setUrlImagen("http://example.com/newimage.jpg");
        assertEquals("http://example.com/newimage.jpg", product.getUrlImagen());
    }
}
