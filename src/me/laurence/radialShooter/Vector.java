package me.laurence.radialShooter;

// Basically an implementation of Point but with doubles instead of ints with minor added functionality.
public class Vector {
	private double x, y;
	
	public Vector() {
		x = 0;
		y = 0;
	}
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void translate(Vector v) {
		x += v.x;
		y += v.y;
	}
	
	public void translate(double vx, double vy) {
		x += vx;
		y += vy;
	}
	
	public void setLocation(Vector v) {
		x = v.x;
		y = v.y;
	}
	
	public void setLocation(double vx, double vy) {
		x = vx;
		y = vy;
	}
	
	public double distance(Vector v) {
		return Math.sqrt(Math.pow(v.x- x, 2) + Math.pow(v.y - y, 2));
	}
	
	public double distance(double vx, double vy) {
		return Math.sqrt(Math.pow(vx- x, 2) + Math.pow(vy - y, 2));
	}
	
	public Vector getUnitVector() {
		double dist = distance(new Vector(0,0));
		return new Vector(x/dist, y/dist);
	}
	
	public void rotateRad(double radians) {
		// TODO
	}
	
	public void rotateDeg(double degrees) {
		rotateRad(Math.PI/180);
	}
	
	public Vector getLocation() {
		return new Vector(x, y);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
