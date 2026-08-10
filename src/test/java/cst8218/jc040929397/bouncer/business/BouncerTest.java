package cst8218.jc040929397.bouncer.business;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BouncerTest {
    
    //Test function that tests whether or not the bouncer is moving forwards
    @Test
    public void testTimeStepMovesBouncer() {

        Bouncer bouncer = new Bouncer();

        bouncer.setCurrentTravel(10);
        bouncer.setMvtDirection(1);
        bouncer.setMaxTravel(100);

        bouncer.timeStep();

        assertEquals(20, bouncer.getCurrentTravel());
    }
    //Test function that tests whether or not the bouncer is moving backwards
    @Test
    public void testTimeStepMovesBackward() {

        Bouncer bouncer = new Bouncer();

        bouncer.setCurrentTravel(10);
        bouncer.setMvtDirection(-1);
        bouncer.setMaxTravel(100);

        bouncer.timeStep();

        assertEquals(0, bouncer.getCurrentTravel());
    }

    //Test to see if bouncer reverses when the max travel is reached
    @Test
    public void testDirectionReversesAtMaxTravel() {

        Bouncer bouncer = new Bouncer();

        bouncer.setCurrentTravel(100);
        bouncer.setMvtDirection(1);
        bouncer.setMaxTravel(100);
        bouncer.setDirChangeCount(0);
        
        bouncer.timeStep();

        assertEquals(-1, bouncer.getMvtDirection());
    }
    
    //Test to see whether or not the direction logs that it changes
    @Test
    public void testDirectionChangeCountIncreases() {

        Bouncer bouncer = new Bouncer();

        bouncer.setCurrentTravel(100);
        bouncer.setMvtDirection(1);
        bouncer.setMaxTravel(100);
        bouncer.setDirChangeCount(0);

        bouncer.timeStep();

        assertEquals(1, bouncer.getDirChangeCount());
    }
    
    //Test for if the bouncer stops when max travel is zero
    @Test
    public void testTimeStepStopsWhenMaxTravelIsZero() {

        Bouncer bouncer = new Bouncer();

        bouncer.setCurrentTravel(10);
        bouncer.setMvtDirection(1);
        bouncer.setMaxTravel(0);

        bouncer.timeStep();

        assertEquals(10, bouncer.getCurrentTravel());
    }
}