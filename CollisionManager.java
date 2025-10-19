public class CollisionManager {
    public CollisionManager() {

    }

    public boolean hitSides(GameObject pl, GameObject b) {

        /*if (pl.getHitBox().intersects(b.getHitBox().getLayoutBounds()) ) {

            if( b.getCentreY() > pl.getY()
                    && (b.getCentreX() + b.getRadius() >= pl.getX()
                    || b.getCentreX() - b.getRadius() <= pl.getX()+pl.getW()) ) {
                    System.out.println("side");
                return true;
            }
        }*/



        return false;
    }

    public boolean hitBottoms(GameObject pl, GameObject b) {
        /*System.out.println("bottom");
        if (pl.getHitBox().intersects(b.getHitBox().getLayoutBounds()) ) {

            if( b.getCentreY() + b.getRadius() >= pl.getY()
                    || b.getCentreY() - b.getRadius() <= pl.getY()+pl.getH() ) {
                return true;
            }
        }*/

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
            System.out.println("bottom");
            return true;
        }

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
            System.out.println("top");
            return true;
        }

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
            System.out.println("left");
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
            System.out.println("right");
            return true;
        }

        return false;
    }



}