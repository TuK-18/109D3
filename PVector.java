

import java.lang.Math;


public class PVector {
    private double x;
    private double y;
    private double magnitude;
    private double limit;
    private double direction;

    public PVector(double _x, double _y) {
        this.x = _x;
        this.y = _y;
        mag();
    }

    public PVector(double _x, double _y, double _direction) {
        this.x = _x;
        this.y = _y;
        this.direction = _direction;
        mag();
    }

    public PVector() {

    }

    public void add(PVector v) {
        this.x = this.x + v.x;
        this.y = this.y + v.y;
    }

    public void addX(double d) {
        this.x = this.x + d;
    }

    public void addY(double d) {
        this.y = this.y + d;
    }

    public void sub(PVector v) {
        this.x = this.x - v.x;
        this.y = this.y - v.y;
    }

    public void mult(double scalar) {
        this.x = this.x * scalar;
        this.y = this.y * scalar;
    }

    public void multX(double scalar) {
        this.x = this.x * scalar;
    }

    public void multY(double scalar) {
        this.y = this.y * scalar;
    }

    public void mag() {
        this.magnitude = Math.sqrt(x * x + y * y);
    }

    public void limit(double d) {
        this.limit = d;
    }

    public double normalize() {
        double m = this.magnitude;
        if (m != 0) {
            div(m);
        }
        return m;
    }

    public void div(double n) {
        this.x = this.x / n;
        this.y = this.y / n;

    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double _x) {
        this.x = _x;
    }

    public void setY(double _y) {
        this.y = _y;
    }

    public PVector get() {
        return new PVector(this.x, this.y);
    }

}