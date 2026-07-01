/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package cst8218.jc040929397.bouncer.business;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Stateless Enterprise JavaBean that provides business logic and persistence
 * operations for Bouncer entities.
 * 
 * This facade extends AbstractFacade to inherit the standard CRUD
 * operations while supplying the EntityManager used to interact with 
 * the application's database.
 * 
 * @author Joseph Coakeley
 */
@Stateless
public class BouncerFacade extends AbstractFacade<Bouncer> {
    @PersistenceContext(unitName = "bouncerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BouncerFacade() {
        super(Bouncer.class);
    }

}
