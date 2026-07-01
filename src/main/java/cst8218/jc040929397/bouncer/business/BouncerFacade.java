/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package cst8218.jc040929397.bouncer.business;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author joey
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
