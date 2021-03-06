package com.teamawesome.zurbs;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.kotcrab.vis.runtime.component.Invisible;
import com.teamawesome.zurbs.component.Laser;
import com.teamawesome.zurbs.manager.GameSceneManager;
import com.teamawesome.zurbs.system.PlayerSystem;

/**
 * Created by Amy on 11/26/2016.
 */
public class WorldContactListener implements ContactListener {
    ComponentMapper<Invisible> invisComp;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        PlayerSystem playerSys = null;

        if(fixA != null && fixA.getUserData() instanceof PlayerSystem)
            playerSys = (PlayerSystem)fixA.getUserData();
        else if(fixB != null && fixB.getUserData() instanceof PlayerSystem)
            playerSys = (PlayerSystem)fixB.getUserData();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case GameSceneManager.PLAYER01_HEAD_BIT | GameSceneManager.PLAYER02_BIT:
                (playerSys).hitOnHead("Player02", "Player01"); // hitOnHead(killer, killed)
                break;
            case GameSceneManager.PLAYER01_HEAD_BIT | GameSceneManager.PLAYER03_BIT:
                (playerSys).hitOnHead("Player03", "Player01");
                break;
            case GameSceneManager.PLAYER01_HEAD_BIT | GameSceneManager.PLAYER04_BIT:
                (playerSys).hitOnHead("Player04", "Player01");
                break;

            case GameSceneManager.PLAYER02_HEAD_BIT | GameSceneManager.PLAYER01_BIT:
                (playerSys).hitOnHead("Player01", "Player02");
                break;
            case GameSceneManager.PLAYER02_HEAD_BIT | GameSceneManager.PLAYER03_BIT:
                (playerSys).hitOnHead("Player03", "Player02");
                break;
            case GameSceneManager.PLAYER02_HEAD_BIT | GameSceneManager.PLAYER04_BIT:
                (playerSys).hitOnHead("Player04", "Player02");
                break;

            case GameSceneManager.PLAYER03_HEAD_BIT | GameSceneManager.PLAYER01_BIT:
                (playerSys).hitOnHead("Player01", "Player03");
                break;
            case GameSceneManager.PLAYER03_HEAD_BIT | GameSceneManager.PLAYER02_BIT:
                (playerSys).hitOnHead("Player02", "Player03");
                break;
            case GameSceneManager.PLAYER03_HEAD_BIT | GameSceneManager.PLAYER04_BIT:
                (playerSys).hitOnHead("Player04", "Player03");
                break;

            case GameSceneManager.PLAYER04_HEAD_BIT | GameSceneManager.PLAYER01_BIT:
                (playerSys).hitOnHead("Player01", "Player04");
                break;
            case GameSceneManager.PLAYER04_HEAD_BIT | GameSceneManager.PLAYER02_BIT:
                (playerSys).hitOnHead("Player02", "Player04");
                break;
            case GameSceneManager.PLAYER04_HEAD_BIT | GameSceneManager.PLAYER03_BIT:
                (playerSys).hitOnHead("Player03", "Player04");
                break;


            case GameSceneManager.PLAYER01_LASER_BIT|GameSceneManager.PLAYER02_BIT :
                (playerSys).hitByLaser("Player01", "Player02");// (killer, killed)
                break;
            case GameSceneManager.PLAYER01_LASER_BIT|GameSceneManager.PLAYER03_BIT :
                (playerSys).hitByLaser("Player01", "Player03");// (killer, killed)
                break;
            case GameSceneManager.PLAYER01_LASER_BIT|GameSceneManager.PLAYER04_BIT :
                (playerSys).hitByLaser("Player01", "Player04");// (killer, killed)
                break;

            case GameSceneManager.PLAYER02_LASER_BIT|GameSceneManager.PLAYER01_BIT:
                (playerSys).hitByLaser("Player02", "Player01");
                break;
            case GameSceneManager.PLAYER02_LASER_BIT|GameSceneManager.PLAYER03_BIT:
                (playerSys).hitByLaser("Player02", "Player03");
                break;
            case GameSceneManager.PLAYER02_LASER_BIT|GameSceneManager.PLAYER04_BIT:
                (playerSys).hitByLaser("Player02", "Player04");
                break;

            case GameSceneManager.PLAYER03_LASER_BIT|GameSceneManager.PLAYER01_BIT:
                (playerSys).hitByLaser("Player03", "Player01");
                break;
            case GameSceneManager.PLAYER03_LASER_BIT|GameSceneManager.PLAYER02_BIT:
                (playerSys).hitByLaser("Player03", "Player02");
                break;
            case GameSceneManager.PLAYER03_LASER_BIT|GameSceneManager.PLAYER04_BIT:
                (playerSys).hitByLaser("Player03", "Player04");
                break;

            case GameSceneManager.PLAYER04_LASER_BIT|GameSceneManager.PLAYER01_BIT:
                (playerSys).hitByLaser("Player03", "Player01");
                break;
            case GameSceneManager.PLAYER04_LASER_BIT|GameSceneManager.PLAYER02_BIT:
                (playerSys).hitByLaser("Player03", "Player02");
                break;
            case GameSceneManager.PLAYER04_LASER_BIT|GameSceneManager.PLAYER03_BIT:
                (playerSys).hitByLaser("Player03", "Player03");
                break;



            case GameSceneManager.WALL_BIT|GameSceneManager.PLAYER01_LASER_BIT:
                DestroyLaser(fixA, fixB);
                break;
            case GameSceneManager.WALL_BIT|GameSceneManager.PLAYER02_LASER_BIT:
                DestroyLaser(fixA, fixB);
                break;
            case GameSceneManager.WALL_BIT|GameSceneManager.PLAYER03_LASER_BIT:
                DestroyLaser(fixA, fixB);
                break;
            case GameSceneManager.WALL_BIT|GameSceneManager.PLAYER04_LASER_BIT:
                DestroyLaser(fixA, fixB);
                break;

                /*
            case GameSceneManager.WALL_BIT|GameSceneManager.PLAYER02_LASER_BIT:
                System.out.println("wall hit");
                if(fixA.getFilterData().categoryBits == GameSceneManager.PLAYER02_LASER_BIT)
                    ((GameSceneManager)fixA.getUserData()).destroyBullet(contact.getFixtureA());
                else
                    ((GameSceneManager)fixB.getUserData()).destroyBullet(contact.getFixtureB());
                break;
                */
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

    private void DestroyLaser(Fixture fixA, Fixture fixB) {
        Entity laser;
        System.out.println("wall hit");
        switch(fixA.getFilterData().categoryBits) {
            case GameSceneManager.PLAYER01_LASER_BIT:
            case GameSceneManager.PLAYER02_LASER_BIT:
            case GameSceneManager.PLAYER03_LASER_BIT:
            case GameSceneManager.PLAYER04_LASER_BIT:
                laser = (Entity) fixA.getUserData();
                laser.getComponent(Laser.class).destroy = true;
                break;

            default:
                laser = (Entity) fixB.getUserData();
        }

        laser.getComponent(Laser.class).destroy = true;
        invisComp = laser.getWorld().getMapper(Invisible.class);
        invisComp.create(laser);

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
