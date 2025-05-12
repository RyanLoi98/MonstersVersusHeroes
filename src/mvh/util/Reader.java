package mvh.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import mvh.enums.WeaponType;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;

/**
 * Class to assist reading in world file
 * @author Ryan Loi
 * @author Jonathan Hudson
 * @date (dd / mm / yr): 28/02/22
 * @tutorial TO5 - Anika Achari
 * @version 1.0
 */
public final class Reader {

    //CONSTANTS
    static int minRequiredData = 2;
    static int entityTypeIndex = 2;
    static int entitySymbolIndex = 3;
    static int entityHealthIndex = 4;
    static int monsterWeaponIndex = 5;
    static int heroAttackIndex = 5;
    static int heroArmorIndex = 6;

    /**
     * A class method that will open a user provided input file, read its contents, and create a world and return it
     * (unless the file cannot be read, at this point the program will exit with an error)
     * @param fileWorld File object linking to the file.txt entered by the user as an argument
     * @return world object which is a 2D grid (array) containing our entities (monsters or heroes), the floor are the
     * null spots
     */
    public static World loadWorld(File fileWorld) {
        // creating world object
        World world;

        // Creating the fileReader and bufferedReader within the try round brackets to enable file closing via resources
        // also catches any exceptions that may occur
        try (FileReader fileReader = new FileReader(fileWorld); BufferedReader bufferedReader = new BufferedReader(fileReader);){

            // getting the number of rows (first line) and columns (second line)
            int rows = Integer.parseInt(bufferedReader.readLine());
            int columns = Integer.parseInt(bufferedReader.readLine());

            // initializing world
            world = new World(rows, columns);

            // looping through all of the rows (which is 1 less than the number of rows and attainable when r<rows)
            for (int r = 0; r < rows; r++){

                // looping through all of the columns (which is 1 less than the number of columns and attainable when c<columns)
                for (int c = 0; c < columns; c++){
                    String line = bufferedReader.readLine();

                    //splitting the line via the commas
                    String[] lineSplit = line.split(",");

                    // checking if there is an entity present in this cell (rowXcolumn) if there is the length line will
                    // be > 2 (minRequiredData)

                    if (lineSplit.length > minRequiredData){

                        // if index 2 of the split comma separated line is Monster, create a monster entity and add it
                        // to the world in this row X column
                        if (lineSplit[entityTypeIndex].equals("MONSTER")){

                            // getting the symbol of the entity which is in string form, so it is converted into a char
                            // and using index 0 because it is only one symbol at index 3 (entitiySymbolIndex) of the
                            // split comma separated line
                            char entitySymbolChar = lineSplit[entitySymbolIndex].charAt(0);

                            // obtaining the char for the weapon type in the same fashion as the symbol.
                            char weaponChar = lineSplit[monsterWeaponIndex].charAt(0);
                            // getting weapon using the weapon type method
                            WeaponType weapon = WeaponType.getWeaponType(weaponChar);

                            //creating monster, health is converted into an int from the split line
                            Monster monster = new Monster(Integer.parseInt(lineSplit[entityHealthIndex]),entitySymbolChar,weapon);

                            // adding monster to the world at its specified row and column
                            world.addEntity(r , c, monster);
                        }
                        // else if index 2 of the split comma separated line is hero, create a hero entity and add it
                        // to the world in this row (r) X column (c)
                        else if (lineSplit[2].equals("HERO")){

                            // getting the symbol of the entity which is in string form, so it is converted into a char
                            // and using index 0 because it is only one symbol at index 3 (entitiySymbolIndex) of the
                            // split comma separated line
                            char entitySymbolChar = lineSplit[entitySymbolIndex].charAt(0);

                            // creating the hero, health, attack, and defense were obtained using indexing of the
                            // split comma separated line
                            Hero hero = new Hero(Integer.parseInt(lineSplit[entityHealthIndex]),entitySymbolChar,Integer.parseInt(lineSplit[heroAttackIndex]), Integer.parseInt(lineSplit[heroArmorIndex]));

                            // adding hero to the world at its specified row and column
                            world.addEntity(r , c, hero);

                        }

                    }


                }

            }
            return world;
        } //catching any errors from reading the file
        catch(IOException e){
            System.err.println("Error reading from the file named: " + fileWorld.getName() + ", Path: " + fileWorld.getAbsolutePath());
            System.exit(1);
        }
        /* Throw a RuntimeException if this point of the coded is reached, because if the try block is successful
        in reading the input file it should return world there, if there was an issue reading the file the program should
        exit in the catch block. There is no reason why the program should progress to this point.
        */
        throw new RuntimeException("Error creating world");
    }

}
