

public class CollisionManager {
    public CollisionManager() {

    }

    public boolean hitSides(GameObject pl, GameObject b) {
        return false;
    }

    public boolean hitBottoms(GameObject pl, GameObject b) {


        double cX = b.getCentreX();
        double cY = b.getCentreY();

        double tLx = pl.getX();
        double tLy = pl.getY();

        double tRx = tLx + pl.getW();
        double tRy = tLy;

        double bLx = tLx;
        double bLy = tLy + pl.getH();

        double bRx = tRx;
        double bRy = bLy;

        if (cY <= tLy && cY <= tRy
                && cY <= bLy && cY <= bRy
                && cX < tRx && cX > tLx) {
            System.out.println("Bottom");
            return true;
        }

        return false;
    }

    public boolean hitBottom(GameObject pl, GameObject b) {
        double cX = b.getCentreX();
        double cY = b.getCentreY();

        double tLx = pl.getX();
        double tLy = pl.getY();

        double tRx = tLx + pl.getW();
        //double tRy = tLy;

        //double bLx = tLx;
        double bLy = tLy + pl.getH();

        //double bRx = tRx;
        //double bRy = bLy;

        if (cY >= tLy
                && cY >= bLy
                && cX <= tRx && cX >= tLx) {
            /*System.out.println("bottom");
            System.out.println(cX + " " + cY);*/
            return true;
        }

        //return hitBottomLeft(pl, b) || hitBottomRight(pl, b);

        return false;
    }

    public boolean hitTop(GameObject pl, GameObject b) {
        double cX = b.getCentreX();
        double cY = b.getCentreY();

        double tLx = pl.getX();
        double tLy = pl.getY();

        double tRx = tLx + pl.getW();
        //double tRy = tLy;

        //double bLx = tLx;
        double bLy = tLy + pl.getH();

        //double bRx = tRx;
        //double bRy = bLy;

        if (cY <= tLy
                && cY <= bLy
                && cX <= tRx && cX >= tLx) {
            /*System.out.println("top");
            System.out.println(cX + " " + cY);*/
            return true;
        }

        //return hitTopLeft(pl, b) || hitTopRight(pl ,b);

        return false;
    }

    public boolean hitLeft(GameObject pl, GameObject b) {
        double cX = b.getCentreX();
        double cY = b.getCentreY();

        double tLx = pl.getX();
        double tLy = pl.getY();

        double tRx = tLx + pl.getW();
        //double tRy = tLy;

        //double bLx = tLx;
        double bLy = tLy + pl.getH();

        //double bRx = tRx;
        //double bRy = bLy;

        if (cX <= tLx && cX <= tRx
                && cY <= bLy && cY >= tLy) {
            /*System.out.println("left");
            System.out.println(cX + " " + cY);*/
            return true;
        }

        return false;
    }

    public boolean hitRight(GameObject pl, GameObject b) {
        double cX = b.getCentreX();
        double cY = b.getCentreY();

        double tLx = pl.getX();
        double tLy = pl.getY();

        double tRx = tLx + pl.getW();
        //double tRy = tLy;

        //double bLx = tLx;
        double bLy = tLy + pl.getH();

        //double bRx = tRx;
        //double bRy = bLy;

        if (cX >= tLx && cX >= tRx
                && cY <= bLy && cY >= tLy) {
            /*System.out.println("right");
            System.out.println(cX + " " + cY);*/
            return true;
        }

        return false;
    }

    public boolean hitTopLeft(GameObject pl, GameObject b) {
        double cX = b.getCentreX();
        double cY = b.getCentreY();

        double tLx = pl.getX();
        double tLy = pl.getY();

        double tRx = tLx + pl.getW();
        //double tRy = tLy;

        //double bLx = tLx;
        double bLy = tLy + pl.getH();

        if (cX < tLx && cY < tLy) {
            return true;
        }
        return false;
    }

    public boolean hitTopRight(GameObject pl, GameObject b) {
        double cX = b.getCentreX();
        double cY = b.getCentreY();

        double tLx = pl.getX();
        double tLy = pl.getY();

        double tRx = tLx + pl.getW();
        //double tRy = tLy;

        //double bLx = tLx;
        double bLy = tLy + pl.getH();

        if (cX > tRx && cY > tLy) {
            return true;
        }

        return false;
    }

    public boolean hitBottomLeft(GameObject pl, GameObject b) {
        double cX = b.getCentreX();
        double cY = b.getCentreY();

        double tLx = pl.getX();
        double tLy = pl.getY();

        double tRx = tLx + pl.getW();
        //double tRy = tLy;

        //double bLx = tLx;
        double bLy = tLy + pl.getH();

        if (cX < tLx && cY > bLy) {
            return true;
        }
        return false;
    }

    public boolean hitBottomRight(GameObject pl, GameObject b) {
        double cX = b.getCentreX();
        double cY = b.getCentreY();

        double tLx = pl.getX();
        double tLy = pl.getY();

        double tRx = tLx + pl.getW();
        //double tRy = tLy;

        //double bLx = tLx;
        double bLy = tLy + pl.getH();

        if (cX > tRx && cY > bLy) {
            return true;
        }
        return false;
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