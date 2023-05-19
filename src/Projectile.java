/**
 * Name: Tolga Selcuk
 * Date: 03/04/2023
 * Description: Creates projectiles launched with any given gravitational conditions.
 */
import java.lang.Math;
public class Projectile {
    // Time variable, measured in seconds.
    private double time;
    // Horizontal displacement measured in meters.
    private double horizontalDisplacement;
    // Vertical displacement measured in meters.
    private double verticalDisplacement;
    // Velocity measured in meters per second.
    private double velocity;
    // Acceleration (gravity) measured in meters per second squared.
    private double acceleration;
    // Angle measured in degrees.
    private double angle;

    //Basic constructor that assumes projectile is still on earth.
    public Projectile() {
        time = 0;
        horizontalDisplacement = 0;
        verticalDisplacement = 0;
        velocity = 0;
        acceleration = 9.81;
        angle  = 0;
    }

    /**
     * Constructor that allows for gravity to be set easily.
     * @param planet
     */
    public Projectile(String planet) {
        time = 0;
        horizontalDisplacement = 0;
        verticalDisplacement = 0;
        velocity = 0;
        acceleration = setPlanet(planet);
        angle  = 0;
    }

    /**
     * This constructor allows for user to set gravity between any two given masses.
     * @param radius
     * @param mass
     */
    public Projectile(double radius, double mass ) {
        time = 0;
        horizontalDisplacement = 0;
        verticalDisplacement = 0;
        velocity = 0;
        acceleration = (Math.pow(6.67, -11))*mass/(Math.pow(radius,2));
        angle  = 0;
    }

    /**
     * This method allows for user to set gravity to a commonly used and preset planet.
     * @param planet
     * @return
     */
    public double setPlanet(String planet) {
        if(planet.equals("moon")) {
            return 1.62;
        }else if(planet.equals("saturn")) {
            return 10.44;
        }else if(planet.equals("jupiter")) {
            return 24.79;
        }else if(planet.equals("mercury")) {
            return 3.7 ;
        }else if(planet.equals("mars")) {
            return 3.72;
        }else if(planet.equals("venus")) {
            return 8.87;
        }else if(planet.equals("uranus")) {
            return 8.87;
        }else if(planet.equals("neptune")) {
            return 11.15;
        }else{
            return 9.81;
        }
    }

    /*Method Legend:
     * case1: Projectile launched from height to ground without an angle.
     * case2: Projectile launched from ground with angle.
     * case3: Projectile launched with angle from elevation.
     * T: Time (seconds)
     * V: Velocity (meters per second)
     * Hd: Horizontal displacement (meters)
     * Vd: Vertical displacement (meters)
     * A: Angle of launch (degrees)
     * */

    /**
     * Given time and velocity, calculate all other variables and assign them to object.
     * Launched from height with no angle.
     * @param timeInput
     * @param velocityInput
     */
    public void case1TV (double timeInput, double velocityInput) {
        time = timeInput;
        velocity = velocityInput;
        horizontalDisplacement = velocity*time;
        verticalDisplacement = (0.5)*acceleration*(Math.pow(time,2));
    }

    /**
     * Given time and horizontal displacement, calculate all other variables and assign them to object.
     *  Launched from height with no angle.

     * @param timeInput
     * @param horizontalDisplacementInput
     */
    public void case1THd (double timeInput, double horizontalDisplacementInput) {
        time = timeInput;
        velocity = horizontalDisplacementInput/time;
        horizontalDisplacement = horizontalDisplacementInput;
        verticalDisplacement = (0.5)*acceleration*(Math.pow(time,2));
    }

    /**
     * Given velocity and horizontal displacement, calculate all other variables and assign them to object.
     * Launched from height with no angle.
     * @param velocityInput
     * @param horizontalDisplacementInput
     */
    public void case1VHd ( double velocityInput , double horizontalDisplacementInput) {
        time =  horizontalDisplacementInput/velocityInput;
        velocity = velocityInput;
        horizontalDisplacement = horizontalDisplacementInput;
        verticalDisplacement = (0.5)*acceleration*(Math.pow(time,2));
    }

    /**
     * Given velocity and vertical displacement, calculate all other variables and assign them to object.
     *  Launched from height with no angle.
     * @param velocityInput
     * @param verticalDisplacementInput
     */

    public void case1VVd ( double velocityInput , double verticalDisplacementInput) {
        velocity = velocityInput;
        time = Math.sqrt(verticalDisplacementInput*2/acceleration);
        horizontalDisplacement = time*velocity;
        verticalDisplacement = verticalDisplacementInput;

    }

    /**
     * Given horizontal displacement and vertical displacement, calculate all other variables and assign them to object.
     *  Launched from height with no angle.
     * @param horizontalDisplacementInput
     * @param verticalDisplacementInput
     */
    public void case1HdVd (double horizontalDisplacementInput , double verticalDisplacementInput) {

        time = Math.sqrt(verticalDisplacementInput*2/acceleration);
        horizontalDisplacement = horizontalDisplacementInput;
        verticalDisplacement = verticalDisplacementInput;
        velocity = horizontalDisplacement/time;

    }

    /**
     * Given an angle and velocity, calculate all other variables and assign them to object.
     * Launched from ground with an angle.
     * @param angleInput
     * @param velocityInput
     */
    public void case2AV(double angleInput, double velocityInput) {
        verticalDisplacement = 0;
        angle = angleInput;
        velocity = velocityInput;
        time = 2*velocity*Math.sin(Math.toRadians(angle))/acceleration;
        horizontalDisplacement = velocity*Math.cos(Math.toRadians(angle))*time;
    }

    /**
     * Given an angle and time, calculate all other variables and assign them to object.
     * Launched from ground with an angle.
     * @param angleInput
     * @param timeInput
     */
    public void case2AT(double angleInput, double timeInput) {
        verticalDisplacement = 0;
        angle = angleInput;
        time = timeInput;
        velocity = acceleration*time/(2*Math.sin(Math.toRadians(angleInput)));
        horizontalDisplacement = velocity*Math.cos(Math.toRadians((angleInput)))*time;
    }

    /**
     * Given velocity and time, calculate all other variables and assign them to object.
     * Launched from ground with an angle.
     * @param velocityInput
     * @param timeInput
     */
    public void case2VT(double velocityInput, double timeInput) {
        verticalDisplacement = 0;
        time = timeInput;
        velocity = velocityInput;
        angle = Math.toDegrees(Math.asin(acceleration*time/(2*velocity)));
        horizontalDisplacement = velocity*Math.cos(Math.toRadians(angle))*time;
    }

    /**
     * Given velocity, time and angle, calculate all other variables and assign them to object.
     * Launched from height with an angle.
     * @param velocityInput
     * @param timeInput
     * @param angleInput
     */
    public void case3VTA(double velocityInput, double timeInput, double angleInput) {
        velocity = velocityInput;
        time = timeInput;
        angle = angleInput;
        horizontalDisplacement = velocity*time*Math.cos(Math.toRadians(angle));
        verticalDisplacement = velocity*time + (0.5)*acceleration*Math.pow(time, 2);
    }
    /**
     * Given velocity, time and vertical displacement, calculate all other variables and assign them to object.
     * Launched from height with an angle.
     * @param velocityInput
     * @param timeInput
     * @param verticalDisplacementInput
     */
    public void case3VTVd(double velocityInput, double timeInput, double verticalDisplacementInput) {
        velocity = velocityInput;
        time = timeInput;
        verticalDisplacement = verticalDisplacementInput;
        angle = Math.toDegrees(Math.asin((verticalDisplacement-((0.5)*Math.pow(time, 2)*acceleration))/(velocity*time)));
        horizontalDisplacement = velocity*time*Math.cos(Math.toRadians(angle));
    }

    /**
     * Given velocity, angle and vertical displacement, calculate all other variables and assign them to object.
     * Launched from height with an angle.
     * @param velocityInput
     * @param angleInput
     * @param verticalDisplacementInput
     */
    public void case3VAVd(double velocityInput, double angleInput, double verticalDisplacementInput) {
        velocity = velocityInput;
        angle = angleInput;
        verticalDisplacement = verticalDisplacementInput;
// In this portion the formula will be rearranged into a quadratic since we have both a t^2 and t term.
// Quadratic formula is required for this solution.
        time = quadFormula(verticalDisplacementInput ,velocityInput, angleInput);
        horizontalDisplacement = velocity*time*Math.cos(Math.toRadians(angle));

    }

    /**
     * This method computes quadratic formula for certain cases.
     * @param accelerationIP
     * @param angleIP
     * @param velocityIP
     * @return
     */
    public double quadFormula(double verticalDisplacementIP, double angleIP, double velocityIP) {
        double a = (1/2)*acceleration;
        double b = velocityIP*Math.sin(Math.toRadians(angleIP));
        double c = verticalDisplacementIP;
        double x1 = (-b+Math.sqrt(Math.pow(b, 2)-4*a*c))/ 2*a;
        double x2 = (-b-Math.sqrt(Math.pow(b, 2)-4*a*c))/ 2*a;
        if(x2 >x1) {
            return x2;
        }else {
            return x1;
        }
    }
    /**
     * Given time, angle and vertical displacement, calculate all other variables and assign them to object.
     * Launched from height with an angle.
     * @param angleInput
     * @param timeInput
     * @param verticalDisplacementInput
     */
    public void case3ATVd(double angleInput, double timeInput, double verticalDisplacementInput) {
        time = timeInput;
        verticalDisplacement = verticalDisplacementInput;
        angle = angleInput;
        velocity =  (verticalDisplacement-((0.5)*Math.pow(time, 2)*acceleration))/(Math.sin(Math.toRadians(angle))*time);
        horizontalDisplacement = velocity*time*Math.cos(Math.toRadians(angle));
    }

    /**
     * Given time, angle and horizontal displacement, calculate all other variables and assign them to object.
     * Launched from height with an angle.
     * @param angleInput
     * @param timeInput
     * @param horizontalDisplacementInput
     */
    public void case3ATHd(double angleInput, double timeInput, double horizontalDisplacementInput) {
        time = timeInput;
        horizontalDisplacement = horizontalDisplacementInput;
        angle = angleInput;
        verticalDisplacement = velocity*time + (0.5)*acceleration*Math.pow(time, 2);
        velocity = horizontalDisplacement/(time*Math.cos(Math.toRadians(angle)));
    }

    /**
     * Given velocity, angle and horizontal displacement, calculate all other variables and assign them to object.
     * Launched from height with an angle.
     * @param angleInput
     * @param velocityInput
     * @param horizontalDisplacementInput
     */
    public void case3AVHd(double angleInput, double velocityInput, double horizontalDisplacementInput) {

        horizontalDisplacement = horizontalDisplacementInput;
        angle = angleInput;
        velocity = velocityInput;
        verticalDisplacement = velocity*time + (0.5)*acceleration*Math.pow(time, 2);
        time = horizontalDisplacement/(velocity*Math.cos(Math.toRadians(angle)));
    }

    /**
     * Given velocity, time and horizontal displacement, calculate all other variables and assign them to object.
     * Launched from height with an angle.
     * @param velocityInput
     * @param timeInput
     * @param horizontalDisplacementInput
     */
    public void case3VTHd(double velocityInput, double timeInput, double horizontalDisplacementInput) {
        time = timeInput;
        horizontalDisplacement = horizontalDisplacementInput;
        velocity = velocityInput;
        verticalDisplacement = velocity*time + (0.5)*acceleration*Math.pow(time, 2);
        angle = Math.toDegrees(Math.acos( horizontalDisplacement/(time*velocity)));
    }

    // Get methods to view specific attributes of object.
    public double getTime() {
        return time;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getAngle() {
        return angle;
    }

    public double getHorizontalDisplacement() {
        return horizontalDisplacement;
    }

    public double getVerticalDisplacement() {
        return verticalDisplacement;

    }

    /**
     * Equals method to overwrite previous method and check if two projectiles are identical.
     * @param p1
     * @return
     */
    public boolean equals(Projectile p1) {
        if(p1.getAngle() == angle && p1.getTime() == time &&
                p1.getHorizontalDisplacement() == horizontalDisplacement
                && p1.getAcceleration() == acceleration && p1.getVelocity() == velocity
                && p1.getVerticalDisplacement() == verticalDisplacement) {
            return true;
        }else {
            return false;
        }

    }

    /**
     * Method to print out all attributes of the object.
     * toString method makes it easy to print.
     */
    public String toString() {
        String print = "Time Travelled: " + time + " s" +
                "\nHorizontal Displacement: " + horizontalDisplacement + " m" +
                "\nVertical Displacement: " + verticalDisplacement + " m" +
                "\nInitial Speed: " + velocity + " m/s" +
                "\nAccelleration Due To Gravity: " + acceleration  + " m/s^2" +
                "\nAngle of launch: " + angle  + " degrees";
        return print;
    }
}