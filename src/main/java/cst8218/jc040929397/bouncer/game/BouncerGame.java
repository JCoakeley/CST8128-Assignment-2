/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SingletonEjbClass.java to edit this template
 */
package cst8218.jc040929397.bouncer.game;

import cst8218.jc040929397.bouncer.business.Bouncer;
import cst8218.jc040929397.bouncer.business.BouncerFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.util.List;

/**
 *
 * @author joey
 */
@Startup
@Singleton
public class BouncerGame {
    
    public static final int CHANGE_RATE = 50;
    
    @EJB
    private BouncerFacade bouncerFacade;
    private List<Bouncer> bouncers;

    @PostConstruct
    public void go() {
        new Thread(new Runnable() {
            public void run() {
                // the game runs indefinitely
                while (true) {
                    //update all the bouncers and save changes to the database
                    bouncers = bouncerFacade.findAll();
                    for (Bouncer bouncer : bouncers) {
                        bouncer.timeStep();
                        bouncerFacade.edit(bouncer);
                    }
                    //sleep while waiting to process the next frame of the animation
                    try {
                        // wake up roughly CHANGE_RATE times per second
                        Thread.sleep((long)(1.0/CHANGE_RATE*1000));
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
