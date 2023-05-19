/*
Name: Hasnain
Date: April 3rd, 2023
Course: ICS4U
Description: A projectile calculator that calculates the attributes of a projectile given 2 or 3 variables.
 */
import java.io.*;
import java.util.Scanner;
public class Main {
    public static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        int ans;
        do {

            //The user decides if they want to use this program to determine the perfect kick to play one touch or if they want to use it for their own curiosity
            ans = KickBallOrCuriosity();
            //If they did not type 0 to exit, the program continues
            if (ans != 0) {
                int numOfProjectiles;
                //Determines between 1 or 2 projectiles for the calculations
                if (ans == 1) {
                    //by default if they select to play the perfect game of one touch, they will be using 1 projectile
                    numOfProjectiles = 1;
                } else {
                    numOfProjectiles = NumberOfProjectiles();
                }
                //Creates an array for the mass of the planet based on the number of projectiles.
                //is in corespondent with the planet choice array
                double[] massOfPlanet = new double[numOfProjectiles];
                //Creates an array for the radius of the planet based on the number of projectiles
                double[] radiusOfPlanet = new double[numOfProjectiles];
                Projectile[] projectiles = new Projectile[numOfProjectiles];
                //Creates a string array of the planet choice based on the number of projectiles
                String[] planetChoice = new String[numOfProjectiles];
                //Checks if you want to read from a file
                if (ans != 1 && isReadingFromFile() == 'y') {
                    //is there any error with the file
                    boolean fileError;
                    //Where the values separate from the gravity is stored
                    String[] otherValuesFiles;
                    double[] finalOtherValuesFiles;
                    fileError = false;
                    otherValuesFiles = readingFromFile(projectiles);
                    //checks if the file exists
                    if (otherValuesFiles[0].equals("File does not exists")) {
                        fileError = true;
                    }
                    else {
                        //converts the string values previously determined into usable doubles
                        finalOtherValuesFiles = parseDoubleArray(otherValuesFiles);
                        //applies all the values to the specific cass
                        ApplyCases(otherValuesFiles.length/numOfProjectiles, finalOtherValuesFiles, otherValuesFiles, projectiles, numOfProjectiles);
                        //writes the final details to the specific file
                        writeToFile(projectiles);
                    }
                }
                else {
                    if (ans == 1) {
                        //By default, if they select to play the perfect game of one touch they will be using the gravity of Earth
                        planetChoice[0] = "e";
                    } else {
                        //Determines the planet choice of the user
                        UserPlanetChoice(planetChoice, 0, planetChoice.length - 1);
                    }
                    //To check if the choice the user made is Earth or not
                    boolean[] isItEarth = new boolean[planetChoice.length];
                    IsItEarth(isItEarth, planetChoice);
                    //To check if the projectile was initialized by constructor one or two
                    boolean[] projectileInitialized = new boolean[numOfProjectiles];
                    for (int i = 0; i < planetChoice.length; i++) {
                        if (planetChoice[i].length() > 1) {
                            projectiles[i] = new Projectile(planetChoice[i]);
                            projectileInitialized[i] = true;
                        } else if (planetChoice[i].equals("e")) {
                            projectiles[i] = new Projectile();
                            projectileInitialized[i] = true;
                        }
                    }
                    //This takes the input for the mass and radius based on the earlier inputs by the user and then
                    //initializes the projectiles with constructor three
                    for (int i = 0; i < projectileInitialized.length; i++) {
                        if (!projectileInitialized[i]) {
                            Gravity(massOfPlanet, radiusOfPlanet, isItEarth, i, projectiles);
                        }
                    }
                    int caseFromUser;
                    if (ans == 1) {
                        caseFromUser = 2;
                    } else {
                        //Determines the case that the user wants to use
                        caseFromUser = DetermineCaseFromUser();
                    }
                    //Array to see which options the user selects when entering the other required values for the calculations
                    int[] usedOption = new int[]{10, 10, 10};
                    //The projectile number if there is more than one projectile
                    int projectileNumber = 1;
                    //Array with the user's selection of the remaining values needed
                    String[] selectionOfOtherValues;
                    //Since the third case requires 3 parameters and the other two cases require two parameters, this adjusts the length of
                    //the above string array to be the right length based on the case.
                    if (caseFromUser == 3) {
                        selectionOfOtherValues = new String[numOfProjectiles * 3];
                    } else {
                        selectionOfOtherValues = new String[numOfProjectiles * 2];
                    }
                    //The following block asks the user for the remaining required values and determines if the values for
                    //the first projectile (if there are 2 projectiles) have been taken and resets the usedOptions array so
                    //that all the appropriate options can reappear
                    int counter = 0;
                    for (int i = 0; i < selectionOfOtherValues.length; i++) {
                        if (i == 2 && selectionOfOtherValues.length == 4) {
                            for (int k = 0; k < usedOption.length; k++) {
                                usedOption[k] = 10;
                            }
                            counter = 0;
                            projectileNumber = 2;
                        } else if (i == 3 && selectionOfOtherValues.length == 6) {
                            for (int k = 0; k < usedOption.length; k++) {
                                usedOption[k] = 10;
                            }
                            counter = 0;
                            projectileNumber = 2;
                        }
                        selectionOfOtherValues[i] = OtherValuesOfPlanet(usedOption, projectileNumber, caseFromUser, ans);
                        DetermineUsedOption(selectionOfOtherValues[i].substring(0, 1), usedOption, counter);
                        counter++;
                    }
                    //converts the string array of inputs into doubles so that they can be sent into the class to then calculate all the
                    //attributes of the projectile
                    double[] correctTypeOfOtherValues = new double[selectionOfOtherValues.length];
                    for (int i = 0; i < selectionOfOtherValues.length; i++) {
                        correctTypeOfOtherValues[i] = ParseDouble(selectionOfOtherValues[i].substring(2));
                    }
                    //Applies all the numbers and the case that the user selected to calculate the required things
                    ApplyCases(caseFromUser, correctTypeOfOtherValues, selectionOfOtherValues, projectiles, numOfProjectiles);
                    //If there are two projectiles then it runs the comparing projectiles method
                    if (numOfProjectiles == 2) {
                        System.out.println(ComparingProjectiles(projectiles));
                        ProjectilePrintArray(projectiles);
                    }
                    //just displays the values of the projectile
                    else {
                        ProjectilePrintArray(projectiles);
                    }
                }
            }
            //While loop that loops the entire program, allowing for the user to calculate as many times as they want
        } while (ans != 0);
    }

    /**
     * Reads from the file, ProjectileInfo.txt in the src. It initializes the projectiles based on the provided gravity in the files. With the remaining values,
     * it stores them in a String array which is then returned
     * @param projectiles
     * The projectiles being used in the program
     * @return
     * A string array with the remaining values needed to calculate the attributes of the projectile
     */
    public static String[] readingFromFile(Projectile[] projectiles) {
        double mass;
        double radius;
        String[] otherValues = new String[0];
        //Tries to read from the file
        try {
            File file = new File("src/ProjectileInfo.txt");
            //Checks if it is the first projectile or the second projectile
            boolean isReachedProjectile2 = false;
            String line;
            int index = 0;
            //Checks if the file exists
            if (file.exists()) {
                //Reads from the file
                BufferedReader reader = new BufferedReader(new FileReader(file));
                //Loops through the file
                while ((line = reader.readLine()) != null) {
                    //If the line is a hashtag then it means that the second projectile is being read
                    if (line.equals("#")) {
                        isReachedProjectile2 = true;
                        line = reader.readLine();
                    }
                    //If the line is projectile 1 or if the second projectile has not been reached then it reads the first projectile
                    if (line.equals("Projectile 1") || !isReachedProjectile2) {
                        //Reads the next line
                        if (line.equals("Projectile 1")) {
                            line = reader.readLine();
                        }
                        //If the line is gravity: exoplanet then it reads the next two lines for the mass and radius and then initializes the projectile
                        if (line.equals("Gravity: Exoplanet")) {
                            line = reader.readLine();
                            mass = Double.parseDouble(line.substring(5).trim());
                            line = reader.readLine();
                            radius = Double.parseDouble(line.substring(7).trim());
                            projectiles[0] = new Projectile(radius, mass);
                            //If the line is gravity: (with a planet in our library) it initializes the projectile based on the planet
                        } else if (line.contains("Gravity")){
                            line = reader.readLine();
                            projectiles[0] = new Projectile(line.substring(8).trim().toLowerCase());
                        }
                        //If the line is case 1, 2 or 3 then it initializes the string array with the appropriate length
                        if (line.contains("Case")) {
                            if (line.contains("3")) {
                                otherValues = new String[3 * projectiles.length];
                            }
                            else {
                                otherValues = new String[2 * projectiles.length];
                            }
                        }
                        //If the line contains any of the following letters then it adds it to the string array to identify what type each value is
                        if (line.contains("T") || line.contains("A") || line.contains("S") || line.contains("H") || line.contains("V")) {
                            otherValues[index] = line;
                            index++;
                        }
                        //If the line is projectile 2 then it reads the next line
                    } else if (projectiles.length > 1){
                        if (line.equals("Projectile 2")) {
                            line = reader.readLine();
                        }
                        //If the line is gravity: exoplanet then it reads the next two lines for the mass and radius and then initializes the projectile
                        if (line.equals("Gravity: Exoplanet")) {
                            line = reader.readLine();
                            mass = Double.parseDouble(line.substring(5).trim());
                            line = reader.readLine();
                            radius = Double.parseDouble(line.substring(7).trim());
                            projectiles[1] = new Projectile(radius, mass);
                            //If the line is gravity: (with a planet in our library) it initializes the projectile based on the planet
                        } else if (line.contains("Gravity")){
                            projectiles[1] = new Projectile(line.substring(8).trim().toLowerCase());
                        }
                        //If the line is case 1, 2 or 3 then it initializes the string array with the appropriate length
                        if (line.contains("T") || line.contains("A") || line.contains("S") || line.contains("H") || line.contains("V")) {
                            otherValues[index] = line;
                            index++;
                        }
                    }
                }
                reader.close();
            }
            //If the file does not exist then it tells the user to create the file
            else {
                System.out.println("File does not exist. Please create the file with the appropriate name and info and restart the program");
                return new String[]{"File does not exists"};
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return otherValues;
    }
    /**
     * Writes all the details of the projectiles to the files
     * @param projectiles
     * the projectiles being written
     * returns nothing
     */
    private static void writeToFile(Projectile[] projectiles) {
        //Tries to write to the file
        try {
            //Creates the file if it does not exist
            File file = new File("src/ProjectileDetails.txt");
            file.createNewFile();
            //Writes to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            //Loops through the projectiles and writes the details of each projectile
            for (int i = 0; i < projectiles.length; i++) {
                String projectileInfo = projectiles[i].toString();
                writer.write("Projectile " + (i+1) + "\n");
                writer.write(projectileInfo + "\n");
                writer.write("\n");
            }
            //If there are two projectiles then it compares the two projectiles and writes the comparison to the file
            if (projectiles.length == 2) {
                writer.write("\n" + ComparingProjectiles(projectiles));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the remaining numbers from the string array into a double array
     * @param array
     * the string array
     * @return
     * A double array with all the values
     */
    public static double[] parseDoubleArray(String[] array) {
        double[] parsedArray = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length(); j++) {
                if (array[i].charAt(j) <= 57 && array[i].charAt(j) >= 48) {
                    parsedArray[i] = Double.parseDouble(array[i].substring(j));
                    j = array[i].length();
                }
            }
        }
        return parsedArray;
    }

    /**
     * Asks the user if they want to provide the info about the projectile from a file
     * @return
     * A char with their answer y or n
     */
    public static char isReadingFromFile() {
        System.out.println("Are you providing the projectile info from a file? y/n");
        char choice = input.next().charAt(0);
        input.nextLine();
        return choice;
    }
    /**
     * Asks the user if they want to play the perfect game of one touch, use it for their own curiosity or exit the program
     * No parameters
     * @return an int with their answer
     */
    public static int KickBallOrCuriosity() {
        System.out.println("""
 Welcome to our Projectile Calculator! What is your purpose for using this calculator? If you would like to use it for \s
 playing the perfect game of one touch with your friend then type 1. If you are using it for your own curiosity then type 2. \s
 Enter 0 to exit. By: Hasnain and Tolga""");
        String ans = input.next();
        input.nextLine();
        //Validation of their input
        int ansx;
        if (!IsPurposeValid(ans)) {
            ansx = KickBallOrCuriosity();
            return ansx;
        }
        return (int)ParseDouble(ans);
    }

    /**
     * Validating the input that the user provides for if their answer is valid. Between 0 and 2
     * @param ans
     * The answer that the user provides
     * @return True or false depending on if it is valid or not
     */
    public static boolean IsPurposeValid(String ans) {
        //if the answer is longer than 1 character then it is invalid
        if (ans.length() > 1) {
            return false;
        }
        //If the answer is outside the range then it is invalid
        else return ans.charAt(0) <= '2' && ans.charAt(0) >= '0';
    }
    /**
     * Compares the projectiles if there are two of them
     * @param projectiles The array of projectiles
     * Returns nothing. Prints a statement instead
     */
    public static String ComparingProjectiles(Projectile[] projectiles) {
        String comparison = "";
        //Boolean for if the first projectile goes farther
        boolean projectile1IsFarther = false;
        //Boolean for if the first projectile starts at a lower height
        boolean projectile1IsLower = false;
        //Boolean for if the first projectile is slower
        boolean projectile1IsSlower = false;
        //Boolean for if the first projectile is in a stronger gravitational field
        boolean projectile1IsHeavier = false;
        if (projectiles[0].equals(projectiles[1])) {
            comparison = "Both projectiles are identical";
        }
        if (projectiles[0].getHorizontalDisplacement() > projectiles[1].getHorizontalDisplacement()) {
            projectile1IsFarther = true;
        }
        if (projectiles[0].getVerticalDisplacement() < projectiles[1].getVerticalDisplacement()) {
            projectile1IsLower = true;
        }
        if (projectiles[0].getVelocity() < projectiles[1].getVelocity()) {
            projectile1IsSlower = true;
        }
        if (projectiles[0].getAcceleration() > projectiles[1].getVelocity()) {
            projectile1IsHeavier = true;
        }
        if (projectile1IsFarther && projectile1IsLower && projectile1IsHeavier && projectile1IsSlower) {
            comparison = comparison + "\nProjectile 1 is the more efficiently thrown projectile";
        }
        else if (!projectile1IsFarther && !projectile1IsLower && !projectile1IsHeavier && !projectile1IsSlower){
            comparison = comparison + "\nProjectile 2 is the more efficiently thrown projectile";
        }
        else {
            if (projectile1IsFarther) {
                comparison = comparison + "\nProjectile 1 travelled farther";
            } else {
                comparison = comparison + "\nProjectile 2 travelled farther";
            }
            if (projectile1IsSlower) {
                comparison = comparison + "\nProjectile 2 travelled faster";
            } else {
                comparison = comparison + "\nProjectile 1 travelled faster";
            }
        }
        return comparison;
    }

    /**
     * Prints the projectile to the user. Since the projectiles are kept in an array since there can be two, prints via for loop
     * @param projectiles The projectiles array
     * return nothing
     */
    public static void ProjectilePrintArray(Projectile[] projectiles) {
        for (int i = 0; i < projectiles.length; i++) {
            System.out.println(projectiles[i]);
            System.out.println();
        }
    }

    /**
     * Takes the double array of the required values separate from the gravity and orders them based on the legend provided
     * T, S, H, V, A
     * @param otherValues
     * The required string values that are not the gravity
     * @param doubleOtherValues
     * double version of the required string values
     * @param numOfProjectiles
     * The number of projectiles that the user selected
     * @return A 2D array with the values ordered. If it is one projectile then the 2D array will just have one row.
     */
    public static double[][] DoubleOrderedOtherValues(String[] otherValues, double[] doubleOtherValues, int numOfProjectiles, int caseFromUser) {
        //The 2D array with the double values ordered according to the legend
        double[][] orderedDoubleValues;
        //Creates one row if there is only one projectile
        if (numOfProjectiles == 1) {
            orderedDoubleValues = new double[5][1];
        }
        //creates two rows if there are two projectiles
        else {
            orderedDoubleValues = new double[5][2];
        }
        int k = 0;
        //Determines where the specific value is and then orders is accordingly
        for (int i = 0; i < otherValues.length; i++) {
            if (caseFromUser == 3 && orderedDoubleValues[0].length>1 && i ==3) {
                k++;
            }
            else if (caseFromUser < 3 && orderedDoubleValues[0].length>1 && i == 2) {
                k++;
            }
            if (otherValues[i].charAt(0) == 'T') {
                orderedDoubleValues[0][k] = doubleOtherValues[i];
            }
            else if (otherValues[i].charAt(0) == 'S') {
                orderedDoubleValues[1][k] = doubleOtherValues[i];
            }
            else if (otherValues[i].charAt(0) == 'H') {
                orderedDoubleValues[2][k] = doubleOtherValues[i];
            }
            else if (otherValues[i].charAt(0) == 'V') {
                orderedDoubleValues[3][k] = doubleOtherValues[i];
            }
            else if (otherValues[i].charAt(0) == 'A') {
                orderedDoubleValues[4][k] = doubleOtherValues[i];
            }
        }
        return orderedDoubleValues;
    }

    /**
     * orders the otherValues array according to the legend
     * T, S, H, V, A
     * @param otherValues
     * The String array of the required values that are not the gravity for the calculations
     * @param numOfProjectiles
     * The number of projectiles that the user selected
     * @return Returns a 2D array with the values ordered
     */
    public static String[][] StringOrderedOtherValues(String[] otherValues, int numOfProjectiles, int caseFromUser) {
        //The ordered string array
        String[][] orderedStringValues;
        //Creates one row if there is only one projectile
        if (numOfProjectiles == 1) {
            orderedStringValues = new String[5][1];
        }
        //Creates two rows if there is two projectiles
        else {
            orderedStringValues = new String[5][2];
        }
        int k = 0;
        //Determines where the specific values are and orders it accordingly
        for (int i = 0; i < otherValues.length; i++) {
            if (caseFromUser == 3 && orderedStringValues[0].length>1 && i ==3) {
                k++;
            }
            else if (caseFromUser < 3 && orderedStringValues[0].length>1 && i == 2) {
                k++;
            }
            if (otherValues[i].charAt(0) == 'T') {
                orderedStringValues[0][k] = otherValues[i];
            }
            else if (otherValues[i].charAt(0) == 'S') {
                orderedStringValues[1][k] = otherValues[i];
            }
            else if (otherValues[i].charAt(0) == 'H') {
                orderedStringValues[2][k] = otherValues[i];
            }
            else if (otherValues[i].charAt(0) == 'V') {
                orderedStringValues[3][k] = otherValues[i];
            }
            else if (otherValues[i].charAt(0) == 'A') {
                orderedStringValues[4][k] = otherValues[i];
            }
        }
        //if there are any empty spaces or indexes in the array that are null, they are filled to prevent errors
        for (int i = 0; i < orderedStringValues.length; i++) {
            for (int j = 0; j < orderedStringValues[i].length; j++) {
                if (orderedStringValues[i][j] == null) {
                    orderedStringValues[i][j] = "E";
                }
            }
        }
        return orderedStringValues;
    }

    /**
     * Applies the case that the user selected and uses the methods provided from the projectile class to do the calculations for the projectile with the provided numbers
     * @param caseFromUser
     * the case that the user selected
     * @param doubleOtherValues
     * The double version of the other values that the user selected
     * @param otherValues
     * The string version of the other values that the user selected
     * @param projectiles
     * the array of projectiles that have been initialized based on the user inputs to the constructors
     * @param numOfProjectiles
     * The number of projectiles that the user selected
     */
    public static void ApplyCases(int caseFromUser, double[] doubleOtherValues, String[] otherValues, Projectile[] projectiles, int numOfProjectiles) {
        //The ordered version of the values
        double[][] orderedDoubleValues = DoubleOrderedOtherValues(otherValues, doubleOtherValues, numOfProjectiles, caseFromUser);
        String[][] orderedStringValues = StringOrderedOtherValues(otherValues, numOfProjectiles, caseFromUser);
        int k = 0;
        //Has the three cases with all their individual cases that are called based on what the user inputted
        for (int i = 0; i < projectiles.length; i++) {
            //To prevent an index out of bounds for when there is only one projectile
            if (orderedStringValues[0].length == 1) {
                k = 0;
            }
            //Case one
            if (caseFromUser == 1) {
                if (orderedStringValues[0][k].charAt(0) == 'T' && orderedStringValues[1][k].charAt(0) == 'S') {
                    projectiles[i].case1TV(orderedDoubleValues[0][k], orderedDoubleValues[1][k]);
                }
                else if (orderedStringValues[0][k].charAt(0) == 'T' && orderedStringValues[2][k].charAt(0) == 'H') {
                    projectiles[i].case1THd(orderedDoubleValues[0][k], orderedDoubleValues[2][k]);
                }
                else if (orderedStringValues[1][k].charAt(0) == 'S' && orderedStringValues[2][k].charAt(0) == 'H') {
                    projectiles[i].case1VHd(orderedDoubleValues[1][k], orderedDoubleValues[2][k]);
                }
                else if (orderedStringValues[1][k].charAt(0) == 'S' && orderedStringValues[3][k].charAt(0) == 'V') {
                    projectiles[i].case1VVd(orderedDoubleValues[1][k], orderedDoubleValues[3][k]);
                }
                else if (orderedStringValues[2][k].charAt(0) == 'H' && orderedStringValues[3][k].charAt(0) == 'V') {
                    projectiles[i].case1HdVd(orderedDoubleValues[2][k], orderedDoubleValues[3][k]);
                }
                //Case two
            } else if (caseFromUser == 2) {
                if (orderedStringValues[4][k].charAt(0) == 'A' && orderedStringValues[1][k].charAt(0) == 'S') {
                    projectiles[i].case2AV(orderedDoubleValues[4][k], orderedDoubleValues[1][k]);
                }
                else if (orderedStringValues[4][k].charAt(0) == 'A' && orderedStringValues[0][k].charAt(0) == 'T') {
                    projectiles[i].case2AT(orderedDoubleValues[4][k], orderedDoubleValues[0][k]);
                }
                else if (orderedStringValues[1][k].charAt(0) == 'S' && orderedStringValues[0][k].charAt(0) == 'T') {
                    projectiles[i].case2VT(orderedDoubleValues[1][k], orderedDoubleValues[0][k]);
                }
                //Case three
            } else if (caseFromUser == 3) {
                if (orderedStringValues[1][k].charAt(0) == 'S' && orderedStringValues[0][k].charAt(0) == 'T' && orderedStringValues[4][k].charAt(0) == 'A') {
                    projectiles[i].case3VTA(orderedDoubleValues[1][k], orderedDoubleValues[0][k], orderedDoubleValues[4][k]);
                }
                else if (orderedStringValues[1][k].charAt(0) == 'S' && orderedStringValues[0][k].charAt(0) == 'T' && orderedStringValues[3][k].charAt(0) == 'V') {
                    projectiles[i].case3VTVd(orderedDoubleValues[1][k], orderedDoubleValues[0][k], orderedDoubleValues[3][k]);
                }
                else if (orderedStringValues[1][k].charAt(0) == 'S' && orderedStringValues[4][k].charAt(0) == 'A' && orderedStringValues[3][k].charAt(0) == 'V') {
                    projectiles[i].case3VAVd(orderedDoubleValues[1][k], orderedDoubleValues[4][k], orderedDoubleValues[3][k]);
                }
                else if (orderedStringValues[4][k].charAt(0) == 'A' && orderedStringValues[0][k].charAt(0) == 'T' && orderedStringValues[3][k].charAt(0) == 'V') {
                    projectiles[i].case3ATVd(orderedDoubleValues[4][k], orderedDoubleValues[0][k], orderedDoubleValues[3][k]);
                }
                else if (orderedStringValues[4][k].charAt(0) == 'A' && orderedStringValues[0][k].charAt(0) == 'T' && orderedStringValues[2][k].charAt(0) == 'H') {
                    projectiles[i].case3ATHd(orderedDoubleValues[4][k], orderedDoubleValues[0][k], orderedDoubleValues[2][k]);
                }
                else if (orderedStringValues[4][k].charAt(0) == 'A' && orderedStringValues[3][k].charAt(0) == 'V' && orderedStringValues[2][k].charAt(0) == 'H') {
                    projectiles[i].case3AVHd(orderedDoubleValues[4][k], orderedDoubleValues[3][k], orderedDoubleValues[2][k]);
                }
                else if (orderedStringValues[3][k].charAt(0) == 'V' && orderedStringValues[0][k].charAt(0) == 'T' && orderedStringValues[2][k].charAt(0) == 'H') {
                    projectiles[i].case3VTHd(orderedDoubleValues[3][k], orderedDoubleValues[0][k], orderedDoubleValues[2][k]);
                }
            }
            k++;
        }
    }

    /**
     * Determines the case from the user
     * no parameters
     * @return the case that the user selected
     */
    public static int DetermineCaseFromUser() {
        System.out.println("""
 Which of the following cases applies to you?
 1. Launched from initial height with angle equal to 0
 2. Launched from ground with angle greater than 0
 3. Launched from initial height with angle greater than 0
 """);
        String caseSelected = input.next();
        input.nextLine();
        int casex;
        //Validating the input that the user gave for the case
        if (!ValidateSelectedCase(caseSelected)) {
            casex = DetermineCaseFromUser();
            return casex;
        }
        return (int)ParseDouble(caseSelected);
    }

    /**
     * Validates the case that the user provided
     * @param caseSelected
     * The case that the user selected
     * @return true or false depending on if the input was valid or not
     */
    public static boolean ValidateSelectedCase(String caseSelected) {
        //If it is in the range
        if (caseSelected.length() < 2 && (caseSelected.charAt(0) > 48 && caseSelected.charAt(0) < 52)) {
            return true;
        }
        return false;
    }

    /**
     * Prints a string array given an array
     * @param array
     * give string array to print
     * returns nothing
     */
    public static void StringPrintArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    /**
     * Determines the option that the user has already used
     * @param s
     * @param usedOption
     * returns nothing
     */
    public static void DetermineUsedOption(String s, int[] usedOption, int counter) {
        String[] validStrings = new String[]{"T", "S", "H", "V", "A"};
        for (int i = 0; i < 5; i++) {
            if (s.equals(validStrings[i])) {
                usedOption[counter] = i;
            }
        }
    }
    /**
     * Prints a double array
     * @param array The array being printed
     * Returns nothing
     */
    public static void DoublePrintArray(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    /**
     * Takes input from the user for the other values needed to calculate the final path of the projectile
     * @param usedOption The option that the user selects and needs to be removed on the next run
     * @param projectileNumber The projectile that the user is selecting the values for
     * @param caseFromUser The case that the user selects
     * @return A string for the values that the user selected
     */
    public static String OtherValuesOfPlanet(int[] usedOption, int projectileNumber, int caseFromUser, int ans) {
        //The legend based on what the inputs have to be
        String[] validStrings = new String[]{"T", "S", "H", "V", "A"};
        //The options that will show up for the other values. Invalid values based on the case or already inputted values for a case will be removed
        String[] options = new String[]{"T: Time (seconds)", "S: Velocity (meters per second)", "H: Horizontal displacement (meters)",
                "V: Vertical displacement (meters)", "A: Angle of launch (degrees)"};
        System.out.println("Please enter the values of one of the following. Add the correspondent letter based on the legend before the number separated by a space." +
                "\nProjectile Number " + projectileNumber);
        //if the user is using the program for the perfect kick then this is extra instructions and info to be provided to the user
        if (ans == 1) {
            System.out.println("""
 The case being used for this is that the ball is on the ground and is kicked with an angle of greater than 0.\s
 The time represents how long the ball will travel for. Angle represents the angle between the ground and the path of the ball.\s
 Velocity represents the speed of the ball when you kick it.""");
        }
        //Removes the options that are invalid or have been already used
        for (int k = 0; k < options.length; k++) {
            if (k == usedOption[0] || k == usedOption[1]) {
                System.out.print("");
            }
            else if (usedOption[0] == 0 && k == 3 && (caseFromUser == 2||caseFromUser == 1) || usedOption[1] == 0 && k == 3 && (caseFromUser == 2||caseFromUser == 1)) {
                System.out.print("");
            }
            else if (caseFromUser == 3 && usedOption[0] == 2 && k == 3|| caseFromUser == 3 && usedOption[1] == 2 && k == 3) {
                System.out.print("");
            }
            else if (usedOption[0] == 3 && k == 0 && (caseFromUser == 2||caseFromUser == 3)|| usedOption[1] == 3 && k == 0 && (caseFromUser == 2||caseFromUser == 3)) {
                System.out.print("");
            }
            else if (caseFromUser == 1 && k == 4) {
                System.out.print("");
            }
            else if (caseFromUser == 2 && k == 3 || caseFromUser == 2 && k == 2) {
                System.out.print("");
            }

            else {
                System.out.println(options[k]);
            }
        }
        String selectionOfOtherValues = input.nextLine();
        //Capitalizes the letter that the user inputs for consistency and comparison sake
        selectionOfOtherValues = selectionOfOtherValues.substring(0, 1).toUpperCase() + selectionOfOtherValues.substring(1);
        //Validation of the input
        String otherValue;
        if (!OtherValuesOfPlanetsValidation(selectionOfOtherValues, validStrings, caseFromUser)) {
            System.out.println("Invalid Input");
            otherValue = OtherValuesOfPlanet(usedOption, projectileNumber, caseFromUser, ans);
            return otherValue;
        }
        //returns the value after validation
        return selectionOfOtherValues;
    }
    /**
     * Validates the other values the user needs to input apart from the gravity
     * @param s The string being validated
     * @param validStrings The list of valid strings
     * @param caseFromUser The case the user selected
     * @return True or false whether the string is valid or not
     */
    public static boolean OtherValuesOfPlanetsValidation(String s, String[] validStrings, int caseFromUser) {
        boolean isLetterValid = false;
        //Loops through all the valid strings and checks if the beginning of the input starts with one of them
        for (int i = 0; i < validStrings.length; i++) {
            isLetterValid = s.substring(0, 1).equals(validStrings[i]);
            if (isLetterValid) {
                i = 10;
            }
        }
        //The count used to check if there is more than one decimal point in the input
        int decimalCount = 0;
        boolean isDigit = false;
        for (int i = 1; i < s.length(); i++) {
            if (IsDigit(s, i) || s.charAt(1) == ' ') {
                isDigit = true;
            }
            else if (s.charAt(i) == '.') {
                decimalCount++;
            }
            else {
                isDigit = false;
                i = s.length();
            }
        }
        //Checks if the numbers are valid based on the chosen option
        boolean isNumValid = true;
        if (isDigit && isLetterValid && decimalCount < 2 && s.charAt(0) == 'A') {
            if (ParseDouble(s.substring(2)) < 1 || ParseDouble(s.substring(2)) > 179){
                isNumValid = false;
            }
        }
        //Checks if the numbers are valid based on the selected case
        boolean isCaseValid = true;
        boolean optionsVAndA = s.charAt(0) == 'V' || s.charAt(0) == 'A';
        //Case 1
        if (isDigit && isLetterValid && decimalCount < 2 && caseFromUser == 1 && s.charAt(0) == 'V') {
            if (s.charAt(0) == 'V') {
                if (ParseDouble(s.substring(2)) < 1.0) {
                    isCaseValid = false;
                }
            }
        }
        //Case 2
        else if (isDigit && isLetterValid && decimalCount < 2 && caseFromUser == 2 && s.charAt(0) == 'A') {
            if (s.charAt(0) == 'A') {
                if (ParseDouble(s.substring(2)) < 1.0) {
                    isCaseValid = false;
                }
            }
        }
        //Case 3
        else if (isDigit && isLetterValid && decimalCount < 2 && caseFromUser == 3 && optionsVAndA) {
            if (s.charAt(0) == 'V') {
                if (ParseDouble(s.substring(2)) < 1.0) {
                    isCaseValid = false;
                }
            }
            if (s.charAt(0) == 'A') {
                if (ParseDouble(s.substring(2)) < 1.0) {
                    isCaseValid = false;
                }
            }
        }
        return isDigit && isLetterValid && decimalCount < 2 && isNumValid && isCaseValid;
    }
    /**
     * takes the provided inputs from the user
     * @param massOfPlanet The mass of their chosen planet. If they chose Earth then the correspondent index will be 0
     * @param radiusOfPlanet The radius of their chosen planet. If they chose Earth then the correspondent index will be 0
     * @param isItEarth True if the choice were Earth and false if the choice were a different planet.
     * Returns nothing
     */
    public static void Gravity(double[] massOfPlanet, double[] radiusOfPlanet, boolean[] isItEarth, int i, Projectile[] projectiles) {
        if (!isItEarth[i]) {
            MassOfExoPlanet(i, isItEarth, massOfPlanet);
            RadiusOfExoPlanet(i, isItEarth, radiusOfPlanet);
        }
        projectiles[i] = new Projectile(radiusOfPlanet[i], massOfPlanet[i]);
    }
    /**
     * Determines from the user the amount of projectiles they are throwing
     * no parameters
     * @return int numOfProjectiles
     */
    public static int NumberOfProjectiles() {
        System.out.println("Are you comparing the trajectories of 2 projectiles or determining the path of one variable? " +
                "\nPlease enter 2 for comparing trajectories and 1 for determining the path of one variable.");
        String numOfProjectiles = input.next();
        input.nextLine();
        int num;
        if (!NumberOfProjectilesInputValidation(numOfProjectiles)) {
            num = NumberOfProjectiles();
            return num;
        }
        return (int)ParseDouble(numOfProjectiles);
    }
    /**
     * Validates the number of projectiles that the user inputted
     * @param numOfProjectiles The number of projectiles the user selected
     * @return int numOfProjectiles
     */
    public static boolean NumberOfProjectilesInputValidation(String numOfProjectiles) {
        if (numOfProjectiles.length() > 1) {
            return false;
        }
        else if (numOfProjectiles.charAt(0) > '3' || numOfProjectiles.charAt(0) < '1') {
            return false;
        }
        return true;
    }
    /**
     * The planet that the user chooses. Earth or an outside planet for which they enter the details
     * No parameters
     * No return
     */
    public static void UserPlanetChoice(String[] planetChoice, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            //The list of options for gravity
            System.out.println("Are you throwing your projectile(s) from Earth or a different celestial object? " +
                    "\nYou may also select one of the following given planets." +
                    "\nEnter the name of the planet if you would like to use a predetermined gravity of. Projectile number " + (i+1) +
                    "\n" +
                    "\n1. Earth (enter 'e')" +
                    "\n2. Exoplanet(enter 'g')" +
                    "\n OR" +
                    "\n1. Moon" +
                    "\n2. Saturn" +
                    "\n3. Jupiter" +
                    "\n4. Mercury" +
                    "\n5. Mars" +
                    "\n6. Venus" +
                    "\n7. Uranus" +
                    "\n8. Neptune");
            //Makes the input lowercase
            planetChoice[i] = input.next().toLowerCase();
            input.nextLine();
        }
        //Validation of planet choice input
        PlanetInputValidation(planetChoice);
    }
    /**
     * @Description The validation of the planet inputted
     * @param planetChoice The choice that the user made for the planet
     * Returns nothing
     */
    public static void PlanetInputValidation(String[] planetChoice) {

        for (int i = 0; i<planetChoice.length; i++) {
            //Checks to make sure that the input is one of the main celestial bodies or is 'e' or 'g'
            if (planetChoice[i].length() > 1) {
                if(!planetChoice[i].equals("moon") && (!planetChoice[i].equals("saturn")) && (!planetChoice[i].equals("jupiter")) && (!planetChoice[i].equals("mercury")) &&
                        (!planetChoice[i].equals("mars")) && (!planetChoice[i].equals("venus")) && (!planetChoice[i].equals("uranus")) && (!planetChoice[i].equals("neptune"))) {
                    UserPlanetChoice(planetChoice, i, i);
                }
            }
            else if (!planetChoice[i].equals("e") && !planetChoice[i].equals("g")) {
                UserPlanetChoice(planetChoice, i, i);
            }
        }
    }
    /**
     * @Description checks if the planet they chose is Earth
     * @param planetChoice The choice that the user made for the planet
     * Returns nothing
     */
    public static void IsItEarth(boolean[] isItEarth, String[] planetChoice) {
        for (int i = 0; i < planetChoice.length; i++) {
            isItEarth[i] = planetChoice[i].equals("E");
        }
    }
    /**
     *
     * @param whichProjectile
     * @param isItEarth
     * @param massOfPlanet
     */
    public static void MassOfExoPlanet(int whichProjectile, boolean[] isItEarth, double[] massOfPlanet) {
        if (!isItEarth[whichProjectile]) {
            //Asks from the user for the mass of the celestial object
            System.out.println("Enter the mass of the celestial object you are throwing your projectile from" +
                    "(Projectile Number " + (whichProjectile+1) + "): ");
            String mass = input.nextLine();
            //Validates that mass
            boolean isValid = MassInputValidation(mass);
            //If it is invalid then it provides an error message
            if (!isValid) {
                System.out.println("Invalid input. Please input an integer or the number in proper scientific notation");
                MassOfExoPlanet(whichProjectile, isItEarth, massOfPlanet);
            }
            //updates the values from string to double
            massOfPlanet[whichProjectile] = ParseDouble(mass);
        }
    }

    /**
     * Parses any string into a double if the string is a valid double
     * @param s
     * The string being parsed
     * @return the parsed double
     */
    public static double ParseDouble(String s) {
        int i = 0;
        double num = 0.0;
        double decimal = 0.0;
        double sign = 1.0;
        // determines the sign of the number
        if (s.charAt(i) == '-') {
            sign = -1.0;
            i++;
        } else if (s.charAt(i) == '+') {
            i++;
        }
        // Parse integer part
        while (i < s.length() && IsDigit(s, i)) {
            num = num * 10.0 + (s.charAt(i) - '0');
            i++;
        }
        // Parse decimal part
        if (i < s.length() && s.charAt(i) == '.') {
            i++;
            double divisor = 10.0;
            while (i < s.length() && IsDigit(s, i)) {
                decimal += (s.charAt(i) - '0') / divisor;
                divisor *= 10.0;
                i++;
            }
        }
        // Parse exponent part
        if (i < s.length() && (s.charAt(i) == 'e' || s.charAt(i) == 'E')) {
            i++;
            double expSign = 1.0;
            if (s.charAt(i) == '-') {
                expSign = -1.0;
                i++;
            } else if (s.charAt(i) == '+') {
                i++;
            }
            int exponent = 0;
            while (i < s.length() && IsDigit(s, i)) {
                exponent = exponent * 10 + (s.charAt(i) - '0');
                i++;
            }
            decimal *= Math.pow(10.0, expSign * exponent);
        }
        return sign * (num + decimal);
    }
    /**
     * Checks if the String inputted is in scientific notation or is an integer
     * @param mass mass of the planet
     * @return true if scientific notation or integer otherwise returns false
     */
    public static boolean MassInputValidation(String mass) {
        boolean isScientificNotationORInteger= true;
        int i = 0;
        // check for digits before decimal point
        while (i < mass.length() && IsDigit(mass, i)) {
            i++;
        }
        // check for decimal point
        if (i < mass.length() && mass.charAt(i) == '.') {
            i++;
            // check for digits after decimal point
            while (i < mass.length() && IsDigit(mass, i)) {
                i++;
            }
        }
        // check for exponent
        if (i < mass.length() && (mass.charAt(i) == 'E' || mass.charAt(i) == 'e')) {
            i++;
            // check for plus sign
            if (i < mass.length() && (mass.charAt(i) == '+')) {
                i++;
            }
            // check for digits after exponent
            while (i < mass.length() && IsDigit(mass, i)) {
                i++;
            }
        }
        // check if we have reached the end of the input
        if (i < mass.length()) {
            isScientificNotationORInteger = false;
        }
        // checks if the number is greater the 1 billion kg
        boolean isNumBigEnough = true;
        if (ParseDouble(mass) < 1000000000) {
            isNumBigEnough = false;
        }
        return isScientificNotationORInteger && isNumBigEnough;
    }

    /**
     * Checks if the string is a number(can be a double too but does not check if it is scientific notation)
     * @param s
     * The string being checked
     * @return ture or false depending on if the string is a number or not
     */
    public static boolean IsStringNumber(String s) {
        //Counts to see if the number of digits is the same as the length of the String. If the length of the string is greater than the number of digits then
        //that means this sting is not a number since there are extra characters that are not digits
        int digitsCounter = 0;
        //Counts to see if there are only 0 or 1 decimals. If there are more, then it suggests that there is an extra decimal point
        int decimalCounter = 0;
        for (int k = 0; k<s.length(); k++) {
            if (IsDigit(s, k) || s.charAt(k) == '.') {
                digitsCounter++;
                if (s.charAt(k) == '.') {
                    decimalCounter++;
                }
            }
            if (digitsCounter == s.length() && decimalCounter < 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a character is a digit
     * @param s
     * The string being checked
     * @param i
     * the index at which it is being checked at
     * @return true or false depending on if it is a digit or not.
     */
    public static boolean IsDigit(String s, int i) {
        return s.charAt(i) >= 48 && s.charAt(i) <= 57;
    }

    /**
     * Asks the user for the radius of the planet if they selected exoplanet
     * @param whichProjectile
     * The projectile that the loop is on so that the user can know which projectile they are providing the radius for
     * @param isItEarth
     * The value that tells the code whether to proceed by providing a true or false regarding whether the user selected earth or not
     * @param radiusOfPlanet
     * Where the radius of the planet is then saved
     * Returns nothing
     */
    private static void RadiusOfExoPlanet(int whichProjectile, boolean[] isItEarth, double[] radiusOfPlanet) {
        if (!isItEarth[whichProjectile]) {
            //The message provided to the user regarding the radius
            System.out.println("Enter the radius of the celestial object you are throwing your projectile from" +
                    "(Projectile Number " + (whichProjectile+1) + "): ");
            String radius = input.next().trim();
            input.nextLine();
            //validating the input
            boolean isRadiusValidation = IsStringNumber(radius);
            if (!isRadiusValidation) {
                System.out.println("Invalid Input. Please enter an integer number for the radius of the celestial object");
                RadiusOfExoPlanet(whichProjectile, isItEarth, radiusOfPlanet);
            }
            //updating the string into a double
            radiusOfPlanet[whichProjectile] = ParseDouble(radius);
        }
    }
}