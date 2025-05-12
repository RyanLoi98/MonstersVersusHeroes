package mvh.world;

import java.util.ArrayList;
import java.util.HashMap;
import mvh.Main;
import mvh.Menu;
import mvh.enums.Direction;
import mvh.enums.Symbol;

/**
 * A World is a 2D grid of entities, null Spots are floor spots
 * @author Ryan Loi
 * @author Jonathan Hudson
 * @date (dd / mm / yr): 28/02/22
 * @tutorial TO5 - Anika Achari
 * @version 1.0
 */
public class World {

    /**
     * World starts ACTIVE, but will turn INACTIVE after a simulation ends with only one type of Entity still ALIVE
     */
    private enum State {
        ACTIVE, INACTIVE
    }

    /**
     * The World starts ACTIVE
     */
    private State state;
    /**
     * The storage of entities in World, floor is null, Dead entities can be moved on top of (deleting them essentially from the map)
     */
    private final Entity[][] world;
    /**
     * We track the order that entities were added (this is used to determine order of actions each turn)
     * Entities remain in this list (Even if DEAD) ,unlike the world Entity[][] where they can be moved on top of causing deletion.
     */
    private final ArrayList<Entity> entities;
    /**
     * We use a HashMap to track entity location in world {row, column}
     * We will update this every time an Entity is shifted in the world Entity[][]
     */
    private final HashMap<Entity, Integer[]> locations;

    /**
     * The local view of world will be 3x3 grid for attacking
     */
    private static final int ATTACK_WORLD_SIZE = 3;
    /**
     * The local view of world will be 5x5 grid for moving
     */
    private static final int MOVE_WORLD_SIZE = 5;

    /**
     * A new world of ROWSxCOLUMNS in size
     *
     * @param rows    The 1D of the 2D world (rows)
     * @param columns The 2D of the 2D world (columns)
     */
    public World(int rows, int columns) {
        this.world = new Entity[rows][columns];
        this.entities = new ArrayList<>();
        this.locations = new HashMap<>();
        //Starts active
        this.state = State.ACTIVE;
    }

    /**
     * Is this simulation still considered ACTIVE
     *
     * @return True if the simulation still active, otherwise False
     */
    public boolean isActive() {
        return state == State.ACTIVE;
    }

    /**
     * End the simulation, (Set in INACTIVE)
     */
    public void endSimulation() {
        this.state = State.INACTIVE;
    }

    /**
     * Advance the simulation one step
     */
    public void advanceSimulation() {
        //Do not advance if simulation is done
        if (state == State.INACTIVE) {
            return;
        }
        //If not done go through all entities (this will be in order read and added from file)
        for (Entity entity : entities) {
            //If entity is something that is ALIVE, we want to give it a turn to ATTACK or MOVE
            if (entity.isAlive()) {
                //Get location of entity (only the world knows this, the entity does not itself)
                Integer[] location = locations.get(entity);
                //Pull out row,column
                int row = location[0];
                int column = location[1];
                //Determine if/where an entity wants to attack
                World attackWorld3X3 = getLocal(ATTACK_WORLD_SIZE, row, column);
                Direction attackWhere = entity.attackWhere(attackWorld3X3);
                //If I don't attack, then I must be moving
                if (attackWhere == null) {
                    //Figure out where entity wants to move
                    World moveWorld5x5 = getLocal(MOVE_WORLD_SIZE, row, column);
                    Direction moveWhere = entity.chooseMove(moveWorld5x5);
                    //Log moving
                    Menu.println(String.format("%s moving %s", entity.shortString(), moveWhere));
                    //If this move is valid, then move it
                    if (canMoveOnTopOf(row, column, moveWhere)) {
                        moveEntity(row, column, moveWhere);
                    } else {
                        //Otherwise, indicate an invalid attempt to move
                        Menu.println(String.format("%s  tried to move somewhere it could not!", entity.shortString()));
                    }
                } else {
                    //If we are here our earlier attack question was not null, and we are attacking a nearby entity
                    //Get the entity we are attacking
                    Entity attacked = getEntity(row, column, attackWhere);
                    Menu.println(String.format("%s attacking %s in direction %s", entity.shortString(), attackWhere, attacked.shortString()));
                    //Can we attack this entity
                    if (canBeAttacked(row, column, attackWhere)) {
                        //Determine damage using RNG
                        int damage = Main.random.nextInt(1, entity.weaponStrength() + 1);
                        int true_damage = Math.max(0, damage - attacked.armorStrength());
                        Menu.println(String.format("%s attacked %s for %d damage against %d defense for %d", entity.shortString(), attacked.shortString(), damage, attacked.armorStrength(), true_damage));
                        attacked.damage(true_damage);
                        if (!attacked.isAlive()) {
                            locations.remove(attacked);
                            Menu.println(String.format("%s died!", attacked.shortString()));
                        }
                    } else {
                        Menu.println(String.format("%s  tried to attack somewhere it could not!", entity.shortString()));
                    }
                }
            }
        }
        checkActive();
    }

    /**
     * Check if simulation has now ended (only one of two versus Entity types is alive
     */
    private void checkActive() {
        boolean hero_alive = false;
        boolean monster_alive = false;
        for (Entity entity : entities) {
            if (entity.isAlive()) {
                if (entity instanceof Monster) {
                    monster_alive = true;
                } else if (entity instanceof Hero) {
                    hero_alive = true;
                }
            }
        }
        if (!(hero_alive && monster_alive)) {
            state = State.INACTIVE;
        }
    }

    /**
     * Move an existing entity
     *
     * @param row    The  row location of existing entity
     * @param column The  column location of existing entity
     * @param d      The direction to move the entity in
     */
    public void moveEntity(int row, int column, Direction d) {
        Entity entity = getEntity(row, column);
        int moveRow = row + d.getRowChange();
        int moveColumn = column + d.getColumnChange();
        this.world[moveRow][moveColumn] = entity;
        this.world[row][column] = null;
        this.locations.put(entity, new Integer[]{moveRow, moveColumn});
    }

    /**
     * Add a new entity
     *
     * @param row    The  row location of new entity
     * @param column The  column location of new entity
     * @param entity The entity to add
     */
    public void addEntity(int row, int column, Entity entity) {
        this.world[row][column] = entity;
        this.entities.add(entity);
        locations.put(entity, new Integer[]{row, column});
    }

    /**
     * Get entity at a location
     *
     * @param row    The row of the entity
     * @param column The column of the entity
     * @return The Entity at the given row, column
     */
    public Entity getEntity(int row, int column) {
        return this.world[row][column];
    }

    /**
     * Get entity at a location
     *
     * @param row    The row of the entity
     * @param column The column of the entity
     * @param d      The direction adjust look up towards
     * @return The Entity at the given row, column
     */
    public Entity getEntity(int row, int column, Direction d) {
        return getEntity(row + d.getRowChange(), column + d.getColumnChange());
    }

    /**
     * See if we can move to location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if we can move to that location
     */
    public boolean canMoveOnTopOf(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return true;
        }
        return entity.canMoveOnTopOf();
    }

    /**
     * See if we can move to location
     *
     * @param row    The row to check
     * @param column The column to check
     * @param d      The direction adjust look up towards
     * @return True if we can move to that location
     */
    public boolean canMoveOnTopOf(int row, int column, Direction d) {
        return canMoveOnTopOf(row + d.getRowChange(), column + d.getColumnChange());
    }

    /**
     * See if we can attack entity at a location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if we can attack entity at that location
     */
    public boolean canBeAttacked(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity.canBeAttacked();

    }

    /**
     * See if we can attack entity at a location
     *
     * @param row    The row to check
     * @param column The column to check
     * @param d      The direction adjust look up towards
     * @return True if we can attack entity at that location
     */
    public boolean canBeAttacked(int row, int column, Direction d) {
        return canBeAttacked(row + d.getRowChange(), column + d.getColumnChange());

    }

    /**
     * See if entity is hero at this location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if entity is a hero at that location
     */
    public boolean isHero(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity instanceof Hero;
    }


    /**
     * See if entity is monster at this location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if entity is a monster at that location
     */
    public boolean isMonster(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity instanceof Monster;
    }

    /**
     * World method that outputs the world as a string. It will output a map generated by the worldString Method and
     * a tab '\t' separated table of entities (Monsters/Heroes) from the user input file. The 5 columns in the table
     * will be type of entity, symbol of entity, health, state (ALIVE/DEAD), INFO unique to the entity.
     * @return map generated by worldString method and table of entities.
     */
    public String gameString(){

        //Creating a string to hold table (with the header premade) for the table of entities
        String table = "NAME  \tS\tH\tSTATE\tINFO\n";

        // looping through each entity contained in the array list entities and adding it to the table
        for (Entity entity : entities) {
            table = table + entity + "\n";
        }

        // concatenate the world map (made by the worldString() function to the table
        return worldString() + "\n" + table;
    }


    /**
     * World method to create the world map of the game. Walls are represented as '#', floor as '.', dead entities as '$',
     * and alive entities by their user designated symbols from the input file
     * @return returns the world map of the game as a string to the caller
     */
    public String worldString(){
        // String holding the game map
        String map = "";

        // String to hold the walls for the top and bottom of the map
        String topBotWall = "";

        /*Creating the walls for the top and bottom of the map, this will get the number of columns of the array world
          using index 0 to access the first row. Then it will add 2 to it so the walls will be able to line up with the
          side walls. Then the loop will add the appropriate number of wall pieces to the string topBotWall for
          the top and bottom walls of the map
        */

        int rowCounter = 0;
        int columnCounter = 0;

        for (int i = 0; i< (world[0].length + 2); i++){
            topBotWall += Symbol.WALL.getSymbol();
        }

        // adding the top wall to the map with a newline escape
        map = map + topBotWall + "\n";

        // loop through the rows of the world array
        for (Entity[] rows : world){
            // add left wall to the row of the map before we loop through said row and get the columns
            map = map + Symbol.WALL.getSymbol();

            // looping through each column of the array and adding whatever is contained there to the map, this can
            // be an Entity (dead = $, alive = symbol) or floor (if column is null)
            for (Entity column : rows){

                if (column == null){
                    map = map + Symbol.FLOOR.getSymbol();
                }
                else if (column.isAlive()){
                    map = map + column.getSymbol();
                }
                // checks if dead hero if so then put symbol of dead entity
                else if (column.isDead() && isHero(rowCounter, columnCounter)){
                    map = map + Symbol.DEAD.getSymbol();
                }
                // checks if dead monster if so then put symbol of dead entity
                else if (column.isDead() && isMonster(rowCounter, columnCounter)) {
                    map = map + Symbol.DEAD.getSymbol();
                }
                // if anything else this is a wall so put wall here, this is mainly used for the getLocal where it puts
                // walls within the function, so if we need to print that function this is here to ensure it is done
                // properly
                else{
                    map = map + Symbol.WALL.getSymbol();
                }

                // increment columnCounter
                columnCounter++;
            }
            // adding a right wall piece for this row of the map and new line so each row of the map is separate
            map = map + Symbol.WALL.getSymbol() + "\n";
            //increment rowCounter
            rowCounter ++;
            // reset columnCounter at end of columns
            columnCounter = 0;
        }

        // adding the bottom wall to the map
        map = map + topBotWall;

        // return the map
        return map;
    }

    /**
     *Function that creates a world that is the local sub-view of the Entity[][] 2D array called world, centered around an entity specified
     * by the row/column parameters. Areas outside the original world will be filled with walls, and all entities
     * present in the original world will be transferred over in their appropriate locations. This allows entities to
     * see what direction to move and where to attack
     * @param size size of the local sub-view world we will create (must be an odd integer > 3)
     * @param row row index of where this new local sub-view should be centered around the old world
     * @param column column index of where this new local sub-view should be centered around the old world
     * @return a world object that is a local subview of the 2D world array
     */
    World getLocal(int size, int row, int column){
        // checking to make sure the size parameter is a valid input (odd int > 3)
        if (((size % 2) == 0) || size < 3){
            throw new IllegalArgumentException("Invalid size input, size must be an odd integer > 3");
        }

        // creating world for the local sub-view
        World localWorld = new World(size, size);

        // getting the max row and column index of the existing world
        int maxRowIndex = world.length - 1;
        int maxColumnIndex = (world[0].length)-1;

        // correction factor which is how many spaces all around the hero to reach the edge of the board, this is the
        // integer division of the size of the board/world
        int correctionFactor = size/2;

        // the starting and ending row/column indexes of the new local world, but using the index system of the original
        // world. To do this we subtract the (to find the starting row and column index) or add (to find the ending row and
        // column index) the correction factor from the row and column of where the entity is centered. This now allows
        // us to access locations in the original world using the original indexes, while looping through an index range
        // that reflect the size of the new local world.
        //
        // (Note when we subtract and add the correction factor to the row
        // and column variables, we are in effect getting the indexes that are the same distance apart as the size of
        // the new local world but in the index system of the original world.)
        int rowCorrectedStart = row - correctionFactor;
        int rowCorrectedEnd = row + correctionFactor;
        int columnCorrectedStart = column - correctionFactor;
        int columnCorrectedEnd = column +  correctionFactor;

        // create row and column counters so we can use them as indexes to put things into the new local world, since
        // we can't use our looping indexes as they are using the index system of the original world.
        int rowCounter = 0;
        int columnCounter = 0;

        // loop from starting corrected row index to ending corrected row index
        while (rowCorrectedStart <= rowCorrectedEnd){

            // loop from starting corrected column index to ending corrected column index
            while(columnCorrectedStart<=columnCorrectedEnd){

                // check to see if our corrected row index is larger than the max row index or if it is less than 0, or if
                // the corrected column index is larger than the max column index or if it is less than 0. If any of these
                // conditions are true we are looking outside of the original world and we fill these spots with wall pieces
                // in our new world
                if(rowCorrectedStart>maxRowIndex || rowCorrectedStart < 0 || columnCorrectedStart > maxColumnIndex || columnCorrectedStart < 0){
                    localWorld.addEntity(rowCounter, columnCounter, Wall.getWall());
                }
                // otherwise we are looking within the original world and we check if this spot contain an entity (monster or hero), and
                // if it does contain an entity we add that entity (monster or hero) to the new local world in the spot
                // designated by our counters
                else {
                    if(isHero(rowCorrectedStart, columnCorrectedStart) || isMonster(rowCorrectedStart, columnCorrectedStart)){
                        // add entity to our new local world using the add entity function, while getting the entity from the original world
                        // using the getEntity method.
                            localWorld.addEntity(rowCounter,columnCounter, getEntity(rowCorrectedStart,columnCorrectedStart));
                    }
                }
                // increment the corrected column counter
                columnCorrectedStart++;
                // increment the column counter to increase as we loop through, this will keep track of the indexes corresponding to the
                // actual new local world
                columnCounter++;
            }
            //reset the column counter once it has reached the end of the columns
            columnCounter = 0;
            //reset the column counter corrected once it has reached the end of the columns
            columnCorrectedStart = column - correctionFactor;
            // increment the corrected row counter
            rowCorrectedStart++;
            // increment the row counter so it again keeps track of the indexes corresponding to the actual new local world
            rowCounter++;
        }
        return localWorld;
    }


    @Override
    public String toString() {
        return gameString();
    }

}
