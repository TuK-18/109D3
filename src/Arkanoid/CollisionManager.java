package Arkanoid;

import Arkanoid.GameObjects.GameObject;

public class CollisionManager {
    public CollisionManager() {

    }

    public boolean detectBoxBoxCollision(GameObject a, GameObject b) {
        // lay thuoc tinh a
        double ax1 = a.getX();
        double ay1 = a.getY();
        double ax2 = ax1 + a.getW();
        double ay2 = ay1 + a.getH();

        // lay thuoc tinh b
        double bx1 = b.getX();
        double by1 = b.getY();
        double bx2 = bx1 + b.getW();
        double by2 = by1 + b.getH();

        // kiem tra va cham
        if (ax2 > bx1 && ax1 < bx2 && ay2 > by1 && ay1 < by2) {
            return true;
        }
        return false;
    }

    public boolean detectBallBoxCollision(GameObject a, GameObject b) {
        double ballx = a.getCentreX();
        double bally = a.getCentreY();
        double ballradius = a.getRadius();

        double brickx = b.getX();
        double bricky = b.getY();
        double brickw = b.getW();
        double brickh = b.getH();

        // vi tri tren gach gan nhat voi bong
        double closestX = Math.max(brickx, Math.min(ballx, brickx + brickw));
        double closestY = Math.max(bricky, Math.min(bally, bricky + brickh));

        // vecto tu tam bong den vi tri gan nhat cua gach
        double dx = ballx - closestX;
        double dy = bally - closestY;


        if (dx * dx + dy * dy > ballradius * ballradius) {
            return false;
        }
        return true;
    }

}