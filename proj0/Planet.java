public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = p.xxPos - this.xxPos, dy = p.yyPos - this.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p) {
        if (this.equals(p))
            return 0;
        double r = this.calcDistance(p);
        return G * mass * p.mass / r / r;
    }

    public double calcForceExertedByX(Planet p) {
        if (this.equals(p))
            return 0;
        return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / calcDistance(p);
    }

    public double calcForceExertedByY(Planet p) {
        if (this.equals(p))
            return 0;
        return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] allplanets) {
        double force = 0;
        for (Planet p : allplanets) {
            if (!this.equals(p))
                force += calcForceExertedByX(p);
        }
        return force;
    }

    public double calcNetForceExertedByY(Planet[] allplanets) {
        double force = 0;
        for (Planet p : allplanets) {
            if (!this.equals(p))
                force += calcForceExertedByY(p);
        }
        return force;
    }

    public void update(double dt, double fX, double fY) {
        double ax = fX / mass, ay = fY / mass;
        xxVel += ax * dt;
        yyVel += ay * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
