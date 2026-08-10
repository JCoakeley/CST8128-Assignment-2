package cst8218.jc040929397.bouncer.rest;

import cst8218.jc040929397.bouncer.business.Bouncer;
import cst8218.jc040929397.bouncer.business.BouncerFacade;

import jakarta.ws.rs.core.Response;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BouncerFacadeRESTTest {

    private BouncerFacadeREST instance;
    private BouncerFacade bouncerFacade;

    @BeforeEach
    public void setUp() throws Exception {

        instance = new BouncerFacadeREST();

        // Create a fake BouncerFacade
        bouncerFacade = mock(BouncerFacade.class);

        // Inject the fake facade into BouncerFacadeREST
        Field field = BouncerFacadeREST.class
                .getDeclaredField("bouncerFacade");

        field.setAccessible(true);
        field.set(instance, bouncerFacade);
    }

    @Test
    public void testCreate() {

        Bouncer entity = mock(Bouncer.class);

        when(entity.getId()).thenReturn(null);

        Response result = instance.create(entity);

        assertEquals(Response.Status.CREATED.getStatusCode(),
                result.getStatus());

        assertEquals(entity, result.getEntity());

        verify(bouncerFacade).create(entity);
    }

    @Test
    public void testReplaceBouncer() {

        Long id = 1L;

        Bouncer existing = mock(Bouncer.class);
        Bouncer entity = mock(Bouncer.class);

        when(bouncerFacade.find(id)).thenReturn(existing);
        when(entity.getId()).thenReturn(null);

        Response result = instance.replaceBouncer(id, entity);

        assertEquals(Response.Status.OK.getStatusCode(),
                result.getStatus());

        assertEquals(entity, result.getEntity());

        verify(entity).setId(id);
        verify(entity).applyDefaults();
        verify(bouncerFacade).edit(entity);
    }

    @Test
    public void testUpdateBouncer() {

        Long id = 1L;

        Bouncer existing = mock(Bouncer.class);
        Bouncer entity = mock(Bouncer.class);

        when(bouncerFacade.find(id)).thenReturn(existing);
        when(entity.getId()).thenReturn(null);

        Response result = instance.updateBouncer(id, entity);

        assertEquals(Response.Status.OK.getStatusCode(),
                result.getStatus());

        assertEquals(existing, result.getEntity());

        verify(entity).updateNonNull(existing);
        verify(bouncerFacade).edit(existing);
    }

    @Test
    public void testPutRoot() {

        Bouncer incoming = new Bouncer();

        Response result = instance.putRoot(incoming);

        assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(),
                result.getStatus());

        assertEquals(
                "PUT on the root Bouncer resource is not allowed.",
                result.getEntity()
        );
    }

    @Test
    public void testRemove() {

        Long id = 1L;

        Bouncer existing = mock(Bouncer.class);

        when(bouncerFacade.find(id)).thenReturn(existing);

        Response result = instance.remove(id);

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(),
                result.getStatus());

        verify(bouncerFacade).find(id);
        verify(bouncerFacade).remove(existing);
    }

    @Test
    public void testFind() {

        Long id = 1L;

        Bouncer bouncer = mock(Bouncer.class);

        when(bouncerFacade.find(id)).thenReturn(bouncer);

        Response result = instance.find(id);

        assertEquals(Response.Status.OK.getStatusCode(),
                result.getStatus());

        assertEquals(bouncer, result.getEntity());

        verify(bouncerFacade).find(id);
    }

    @Test
    public void testFindAll() {

        Bouncer bouncer1 = mock(Bouncer.class);
        Bouncer bouncer2 = mock(Bouncer.class);

        List<Bouncer> expected = Arrays.asList(
                bouncer1,
                bouncer2
        );

        when(bouncerFacade.findAll()).thenReturn(expected);

        List result = instance.findAll();

        assertEquals(expected, result);

        verify(bouncerFacade).findAll();
    }

    @Test
    public void testFindRange() {

        Integer from = 0;
        Integer to = 2;

        Bouncer bouncer1 = mock(Bouncer.class);
        Bouncer bouncer2 = mock(Bouncer.class);

        List<Bouncer> expected = Arrays.asList(
                bouncer1,
                bouncer2
        );

        when(bouncerFacade.findRange(new int[]{from, to}))
                .thenReturn(expected);

        List result = instance.findRange(from, to);

        assertEquals(expected, result);

        verify(bouncerFacade).findRange(any(int[].class));
    }

    @Test
    public void testCountREST() {

        when(bouncerFacade.count()).thenReturn(5);

        String result = instance.countREST();

        assertEquals("5", result);

        verify(bouncerFacade).count();
    }
}