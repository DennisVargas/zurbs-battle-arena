package com.teamawesome.zurbs;

import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;
import com.teamawesome.zurbs.manager.GameSceneManager;
import com.teamawesome.zurbs.system.PlayerSystem;
import com.teamawesome.zurbs.system.LaserSystem;

/**
 * Created by Amy on 11/26/2016.
 */
public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case GameSceneManager.PLAYER02_HEAD_BIT | GameSceneManager.PLAYER01_BIT:
                ((PlayerSystem)fixA.getUserData()).hitOnHead("Player01", "Player02"); // hitOnHead(killer, killed)
                break;
            case GameSceneManager.PLAYER01_HEAD_BIT | GameSceneManager.PLAYER02_BIT:
                ((PlayerSystem)fixA.getUserData()).hitOnHead("Player02", "Player01");
                break;
            case GameSceneManager.PLAYER01_LASER_BIT|GameSceneManager.PLAYER02_BIT :
                ((PlayerSystem)fixA.getUserData()).hitByLaser("Player01", "Player02");
                break;
            case GameSceneManager.PLAYER02_LASER_BIT|GameSceneManager.PLAYER01_BIT:
                ((PlayerSystem)fixA.getUserData()).hitByLaser("Player02", "Player01");
                break;
            case GameSceneManager.WALL_BIT|GameSceneManager.PLAYER01_LASER_BIT:
                ((PlayerSystem)fixA.getUserData()).hitByLaser("Player01", "Player02");
                break;
            case GameSceneManager.WALL_BIT|GameSceneManager.PLAYER02_LASER_BIT:
                System.out.println("wall hit");
                break;
/*
            case MarioBros.ENEMY_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioBros.MARIO_BIT | MarioBros.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.MARIO_BIT)
                    ((Mario) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else
                    ((Mario) fixB.getUserData()).hit((Enemy)fixA.getUserData());
                break;
            case MarioBros.ENEMY_BIT | MarioBros.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
                ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
                break;
            case MarioBros.ITEM_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioBros.ITEM_BIT | MarioBros.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Mario) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Mario) fixA.getUserData());
                break;
            case MarioBros.FIREBALL_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.FIREBALL_BIT)
                    ((FireBall)fixA.getUserData()).setToDestroy();
                else
                    ((FireBall)fixB.getUserData()).setToDestroy();
                break;
*/
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
