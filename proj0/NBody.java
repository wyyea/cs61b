public class NBody {
    private static String background = "./images/starfield.jpg";

    public static double readRadius(String str) {
        In in = new In(str);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String str) {
        In in = new In(str);
        int N = in.readInt();
        in.readDouble();
        double xP, yP, xV, yV, m;
        String img;
        Planet[] planets = new Planet[N];
        for (int i = 0; i < N; i++) {
            xP = in.readDouble();
            yP = in.readDouble();
            xV = in.readDouble();
            yV = in.readDouble();
            m = in.readDouble();
            img = in.readString();
            planets[i] = new Planet(xP, yP, xV, yV, m, img);
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]), dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        double curtime = 0;
        while (curtime < T) {
            double[] xForces = new double[planets.length], yForces = new double[planets.length];
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i++)
                planets[i].update(dt, xForces[i], yForces[i]);
            StdDraw.clear();
            StdDraw.picture(0, 0, background);
            for (Planet p : planets)
                p.draw();
            StdDraw.show();
            StdDraw.pause(10);
            curtime += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
