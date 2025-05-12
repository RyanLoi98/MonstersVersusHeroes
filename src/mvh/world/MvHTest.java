package mvh.world;

import mvh.enums.Direction;
import mvh.enums.Symbol;
import mvh.enums.WeaponType;
import mvh.util.Reader;

import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Monsters vs Heroes JUNIT 5 testing
 * A class to test the various classes of the Monsters vs Heroes game.
 * @author Ryan Loi
 * @date (dd / mm / yr): 07/03/22
 * @tutorial TO5 - Anika Achari
 * @version 1.0
 */

class MvHTest {
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    /**
     *A test to ensure that the reader loadWorld function is able to read an input file for a 3x3 world and generate
     * a world object where the entities are placed correctly, and the entities have the proper attributes assigned to
     * them (health, symbol, attack, armor, weapon)
     */
    @Test
    void testReader3x3world() {
        // creating the file object
        File world = new File("world.txt");

        //reset id counter of each entity
        Entity.resetIDCounter();

        // EXPECTED WORLD

        // creating a 3x3 world that will contain everything we expect the reader to return after reading the file
        World expectedWorld = new World(3,3);

        //creating the expected monster from the world.txt and adding it to the expected 3x3 world at location (index) 0,0
        // Monster has symbol M, 10 health, and a Sword ('S')
        Monster expectedMonster = new Monster(10,'M', WeaponType.getWeaponType('S'));
        expectedWorld.addEntity(0,0, expectedMonster);

        //creating the expected hero from world.txt and adding the hero to the expected 3x3 world at location (index) 2,2
        // hero has symbol H, 10 health, an attack of 3 and a defense of 1
        Hero expectedHero = new Hero(10,'H', 3,1);
        expectedWorld.addEntity(2,2,expectedHero);

        //reset id counter of each entity
        Entity.resetIDCounter();

        // get the world string of the expected world
        String expectedWorldString = expectedWorld.worldString();

        // get the game string of the expected world
        String expectedGameString = expectedWorld.gameString();



        // ACTUAL WORLD from Reader

        // get the actual world
        World actualWorld = Reader.loadWorld(world);

        // get the world string of the actual world
        String actualWorldString = actualWorld.worldString();

        // get the game string of the actual world
        String actualGameString = actualWorld.gameString();

        // world string is tested first because if the actual vs. expected world string is not the same the assert will
        // print an error and stop the test here. If the worldStrings are the same then the program will continue to
        // test the game string which combines the world string and table together. This way we know the world string
        // is correct and the only thing the second assert really is looking at is the table.

        // test to see if the world was created with all of the entities and floor in the correct spots
        // if the actual is not the same as the expected results print error message
        assertEquals(expectedWorldString, actualWorldString, "Error the reader loadWorld() function was not able " +
                "to read the file and create the world with the floor or entities placed accurately");

        // check if the entities are created with the correct amount of health, symbol, attack, armor, weapon
        // if the actual is not the same as the expected results print error message
        assertEquals(expectedGameString, actualGameString, "Error the reader loadWorld() function was" +
                " unable to correctly read the file and recreate the entities with the proper symbol or health, " +
                "weapon/attack or defense for the world");

    }

    /**
     *A test to ensure that the reader loadWorld function is able to read an input file for a 5x4 world and generate
     * a world object where the entities are placed correctly, and the entities have the proper attributes assigned to
     * them (health, symbol, attack, armor, weapon)
     */
    @Test
    void testReader5x4world() {
        // creating the file object for the big 5x4 world
        File world = new File("worldbig.txt");


        // EXPECTED WORLD

        // creating a 3x3 world that will contain everything we expect the reader to return after reading the file
        World expectedWorld = new World(5,4);

        //creating the expected monster A from the worldbig.txt and adding it to the expected 5x4 world at location (index) 0,0
        // Monster has a symbol 'A', 10 health, and an Axe ('A')
        Monster expectedMonsterA = new Monster(10,'A', WeaponType.getWeaponType('A'));
        expectedWorld.addEntity(0,0, expectedMonsterA);

        //creating the expected monster B from the worldbig.txt and adding it to the expected 5x4 world at location (index) 0,2
        // Monster has a symbol 'B', 9 health, and a Sword ('S')
        Monster expectedMonsterB = new Monster(9,'B', WeaponType.getWeaponType('S'));
        expectedWorld.addEntity(0,2, expectedMonsterB);

        //creating the expected monster C from the worldbig.txt and adding it to the expected 5x4 world at location (index) 1,2
        // Monster has a symbol 'C', 8 health, and a Club ('C')
        Monster expectedMonsterC = new Monster(8,'C', WeaponType.getWeaponType('C'));
        expectedWorld.addEntity(1,2, expectedMonsterC);

        //creating the expected hero from worldBig.txt and adding the hero to the expected 5x4 world at location (index) 4,1
        // hero has symbol D, 8 health, an attack of 5 and a defense of 2
        Hero expectedHeroD = new Hero(8,'D', 5,2);
        expectedWorld.addEntity(4,1,expectedHeroD);

        //creating the expected hero from worldBig.txt and adding the hero to the expected 5x4 world at location (index) 4,2
        // hero has symbol E, 7 health, an attack of 4 and a defense of 1
        Hero expectedHeroE = new Hero(7,'E', 4,1);
        expectedWorld.addEntity(4,2,expectedHeroE);

        //creating the expected hero from worldBig.txt and adding the hero to the expected 5x4 world at location (index) 4,3
        // hero has symbol F, 6 health, an attack of 3 and a defense of 1
        Hero expectedHeroF = new Hero(6,'F', 3,1);
        expectedWorld.addEntity(4,3,expectedHeroF);

        //reset id counter of each entity
        Entity.resetIDCounter();

        // get the world string of the expected world
        String expectedWorldString = expectedWorld.worldString();

        // get the game string of the expected world
        String expectedGameString = expectedWorld.gameString();



        // ACTUAL WORLD from Reader

        // get the actual world
        World actualWorld = Reader.loadWorld(world);


        // get the world string of the actual world
        String actualWorldString = actualWorld.worldString();

        // get the game string of the actual world
        String actualGameString = actualWorld.gameString();

        // world string is tested first because if the actual vs. expected world string is not the same the assert will
        // print an error and stop the test here. If the worldStrings are the same then the program will continue to
        // test the game string which combines the world string and table together. This way we know the world string
        // is correct and the only thing the second assert really is looking at is the table.

        // test to see if the world was created with all of the entities and floor in the correct spots
        // if the actual is not the same as the expected results print error message
        assertEquals(expectedWorldString, actualWorldString, "Error the reader loadWorld() function was not able " +
                "to read the file and create the world with the floor or entities placed accurately");

        // check if the entities are created with the correct amount of health, symbol, attack, armor, weapon
        // if the actual is not the same as the expected results print error message
        assertEquals(expectedGameString, actualGameString, "Error the reader loadWorld() function was" +
                " unable to correctly read the file and recreate the entities with the proper symbol or health, " +
                "weapon/attack or defense for the world");

    }


    /**
     * A test that checks if the gameString() function is able to generate a correct gameString() for a 3x3 world
     * that is found in the world.txt file, this is the game map in conjunction with a data table telling the player
     * what entities are in play, and their stats (health, symbol, attack/defense/weapon), and if they are alive or
     * dead.
     */
    @Test
    void gameString() {
        // creating the string (empty for now) to hold the expected gameString of a 3x3 world from the world.txt file
        String expectedGameString = "";

        // creating the map
        // adding the top 5 walls
        expectedGameString = expectedGameString + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol()
                + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol() + "\n";

        // adding row 1
        expectedGameString = expectedGameString + Symbol.WALL.getSymbol() + 'M' + Symbol.FLOOR.getSymbol()
                + Symbol.FLOOR.getSymbol() + Symbol.WALL.getSymbol() + "\n";

        // adding row 2
        expectedGameString = expectedGameString + Symbol.WALL.getSymbol() + Symbol.FLOOR.getSymbol() + Symbol.FLOOR.getSymbol()
                + Symbol.FLOOR.getSymbol() + Symbol.WALL.getSymbol() + "\n";

        // adding row 3
        expectedGameString = expectedGameString + Symbol.WALL.getSymbol() + Symbol.FLOOR.getSymbol() + Symbol.FLOOR.getSymbol()
                + 'H' + Symbol.WALL.getSymbol() + "\n";

        // adding the bot 5 walls
        expectedGameString = expectedGameString + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol()
                + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol() + "\n";

        // creating the table

        // adding the table header, tab separated
        expectedGameString = expectedGameString + "NAME  \tS\tH\tSTATE\tINFO\n";

        // adding the first row of the table (Name, Symbol, health, state (alive or dead), weapon, tab separated
        // the monster is in this first row
        expectedGameString = expectedGameString + "Mons(1)\tM\t10\tALIVE\tSWORD\n";

        // adding the second row of the table (Name, Symbol, health, state (alive or dead), attack and defense, all tab
        // separated. This row of the table contains the hero
        expectedGameString = expectedGameString + "Hero(2)\tH\t10\tALIVE\t3\t1\n";


        // getting the actual gameString

        //reset id counter of each entity
        Entity.resetIDCounter();

        // creating the file object to the 3x3 world
        File world = new File("world.txt");

        // get the actual world
        World actualWorld = Reader.loadWorld(world);

        // get the game string of the actual world
        String actualGameString = actualWorld.gameString();

        // checking if the actual gameString is what we expect it to be, if not print error message
        assertEquals(expectedGameString, actualGameString, "The worldString function was not able to generate " +
                "the correct worldString");

    }



    /**
     * A test that checks if the worldString() function is able to generate a correct worldString() for a 3x3 world
     * that is found in the world.txt file. This is essentially a map of the game and where the entities are.
     */
    @Test
    void worldString() {
        // creating the string (empty for now) to hold the expected worldString of a 3x3 world from the world.txt file
        String expectedWorldString = "";

        // adding the top 5 walls
        expectedWorldString = expectedWorldString + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol()
        + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol() + "\n";

        // adding row 1
        expectedWorldString = expectedWorldString + Symbol.WALL.getSymbol() + 'M' + Symbol.FLOOR.getSymbol()
                + Symbol.FLOOR.getSymbol() + Symbol.WALL.getSymbol() + "\n";

        // adding row 2
        expectedWorldString = expectedWorldString + Symbol.WALL.getSymbol() + Symbol.FLOOR.getSymbol() + Symbol.FLOOR.getSymbol()
                + Symbol.FLOOR.getSymbol() + Symbol.WALL.getSymbol() + "\n";

        // adding row 3
        expectedWorldString = expectedWorldString + Symbol.WALL.getSymbol() + Symbol.FLOOR.getSymbol() + Symbol.FLOOR.getSymbol()
                + 'H' + Symbol.WALL.getSymbol() + "\n";

        // adding the bot 5 walls
        expectedWorldString = expectedWorldString + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol()
                + Symbol.WALL.getSymbol() + Symbol.WALL.getSymbol();

        // creating the file object to the 3x3 world
        File world = new File("world.txt");

        // get the actual world
        World actualWorld = Reader.loadWorld(world);

        // get the world string of the actual world
        String actualWorldString = actualWorld.worldString();

        // checking if the actual world string is what we expect it to be, if not print error message
        assertEquals(expectedWorldString, actualWorldString, "The worldString function was not able to generate " +
                "the correct worldString");
    }


    /**
     * A test to ensure the getLocal function is able to get a view centered on index 2,2 on a 3x3 grid with a view of 3
     * which means only the immediate surroundings of where the view is centered on. This test will be the view the
     * entity has when attacking. This test will be utilizing the world.txt file and thus centering on index 2,2 will be
     * the local view of the hero
     *
     * So in summary in the 3x3 world we are looking at index 2,2. This translates to index 1,1 when centered in our
     * local view
     */
    @Test
    void getLocal3x3view3() {
        // creating the expected 3x3 local world, which will be empty for now
        World expectedLocalWorld = new World(3,3);

        // adding in the entity to index 1,1 which is centered in our local world. The entity will be a hero with
        // 10 health, the symbol H, 3 weapon strength and 1 armor strength
        expectedLocalWorld.addEntity(1,1,new Hero(10,'H', 3,1));

        //adding walls to the bottom of the world going left to right
        expectedLocalWorld.addEntity(2,0, Wall.getWall()); expectedLocalWorld.addEntity(2,1, Wall.getWall());
        expectedLocalWorld.addEntity(2,2, Wall.getWall());

        //adding walls to the right side of the world going top to bottom
        expectedLocalWorld.addEntity(0,2, Wall.getWall()); expectedLocalWorld.addEntity(1,2,Wall.getWall());

        // turning the expected world into a World string for comparison
        String expectedLocalWorldString = expectedLocalWorld.worldString();


        // getting the local view for the actual getLocal function

        // creating the file object to the 3x3 world
        File world = new File("world.txt");

        // get the actual world
        World actualWorld = Reader.loadWorld(world);

        // getting the local world of size 3 centered on index 2,2
        World actualLocalWorld = actualWorld.getLocal(3,2,2);

        // turning the actual world into a World string for comparison
        String actualLocalWorldString = actualLocalWorld.worldString();


        // checking if the string of the expected local world matches the string of the actual local world, if not
        // print an error message
        assertEquals(expectedLocalWorldString, actualLocalWorldString, "The getLocal function was not " +
                "able to create a local view of size 3 centered around index 2,2");

    }


    /**
     * A test to ensure the getLocal function is able to get a view centered on index 2,2 on a 3x3 grid with a view of 5
     * which means the area of 2 steps around the centered entity will be viewable. This test will be the view the
     * entity has when moving. This test will be utilizing the world.txt file and thus centering on index 2,2 will be
     * the local view of the hero when deciding where to move
     *
     *  So in summary in the 3x3 world we are looking at index 2,2. This translates to index 2,2 when centered in our
     *  local view
     */
    @Test
    void getLocal3x3view5() {
        // creating the expected 5x5 local world, which will be empty for now
        World expectedLocalWorld = new World(5,5);

        // adding in the entity to index 2,2 which is centered in our local world. The entity will be a hero with
        // 10 health, the symbol H, 3 weapon strength and 1 armor strength
        expectedLocalWorld.addEntity(2,2,new Hero(10,'H', 3,1));

        // adding in the monster entity that will be in the localView of size 5, it will be situated in index 0,0
        // Monster has symbol M, 10 health, and a Sword ('S')
        expectedLocalWorld.addEntity(0,0,new Monster(10,'M', WeaponType.getWeaponType('S')));


        //adding walls to the bottom most row (row 5 index = 4) of the world going left to right
        expectedLocalWorld.addEntity(4,0, Wall.getWall()); expectedLocalWorld.addEntity(4,1, Wall.getWall());
        expectedLocalWorld.addEntity(4,2, Wall.getWall()); expectedLocalWorld.addEntity(4,3, Wall.getWall());
        expectedLocalWorld.addEntity(4,4, Wall.getWall());

        //adding walls to the 2nd last row (row 4 index = 3) of the world going left to right
        expectedLocalWorld.addEntity(3,0, Wall.getWall()); expectedLocalWorld.addEntity(3,1, Wall.getWall());
        expectedLocalWorld.addEntity(3,2, Wall.getWall()); expectedLocalWorld.addEntity(3,3, Wall.getWall());
        expectedLocalWorld.addEntity(3,4, Wall.getWall());

        //adding walls to the right most side (column index 4) of the world going top to bottom
        expectedLocalWorld.addEntity(0,4, Wall.getWall()); expectedLocalWorld.addEntity(1,4,Wall.getWall());
        expectedLocalWorld.addEntity(2,4,Wall.getWall());

        //adding walls to the 2nd right most side (column index 3) of the world going top to bottom
        expectedLocalWorld.addEntity(0,3, Wall.getWall()); expectedLocalWorld.addEntity(1,3,Wall.getWall());
        expectedLocalWorld.addEntity(2,3,Wall.getWall());


        // turning the expected world into a World string for comparison
        String expectedLocalWorldString = expectedLocalWorld.worldString();


        // getting the local view for the actual getLocal function

        // creating the file object to the 3x3 world
        File world = new File("world.txt");

        // get the actual world
        World actualWorld = Reader.loadWorld(world);

        // getting the local world of size 5 centered on index 2,2 of the original world
        World actualLocalWorld = actualWorld.getLocal(5,2,2);

        // turning the actual world into a World string for comparison
        String actualLocalWorldString = actualLocalWorld.worldString();


        // checking if the string of the expected local world matches the string of the actual local world, if not
        // print an error message
        assertEquals(expectedLocalWorldString, actualLocalWorldString, "The getLocal function was not " +
                "able to create a local view of size 5 centered around index 2,2");

    }


    /**
     * A test to ensure the getLocal function is able to get a view centered on index 1,2 on a 5x4 grid with a view of 5
     * which means the area of 2 steps around the centered entity will be viewable. This test will be the view the
     * entity has when moving. This test will be utilizing the worldbig.txt file and thus centering on index 1,2 will be
     * the local view of the monster C when deciding where to move. This test checks to make sure getLocal can loop
     * through an asymmetric world and place the entities in a size 5 view accordingly.
     *
     * So in summary in the 5x4 world we are looking at index 1,2. This translates to index 2,2 when centered in our
     * local view
     */
    @Test
    void getLocal5x4view5() {
        // creating the expected 5x5 local world, which will be empty for now
        World expectedLocalWorld = new World(5,5);

        // adding in the entity to index 2,2 which is centered in our local world. The entity will be a monster with
        // 8 health, the symbol C, and a club (C) for a weapon
        expectedLocalWorld.addEntity(2,2,new Monster(8,'C', WeaponType.getWeaponType('C')));

        // adding in the entity to index 1,0 which is part of the view size 5. The entity will be a monster with
        // 10 health, the symbol A, and an Axe (A) for a weapon
        expectedLocalWorld.addEntity(1,0,new Monster(10,'A', WeaponType.getWeaponType('A')));

        // adding in the entity to index 1,2 which is part of the view size 5. The entity will be a monster with
        // 9 health, the symbol B, and a Sword (S) for a weapon
        expectedLocalWorld.addEntity(1,2,new Monster(9,'B', WeaponType.getWeaponType('S')));


        //adding walls to the top most row (row 1 index = 0) of the world going left to right
        expectedLocalWorld.addEntity(0,0, Wall.getWall()); expectedLocalWorld.addEntity(0,1, Wall.getWall());
        expectedLocalWorld.addEntity(0,2, Wall.getWall()); expectedLocalWorld.addEntity(0,3, Wall.getWall());
        expectedLocalWorld.addEntity(0,4, Wall.getWall());

        //adding walls to the right most column (column 5 index = 4) of the world going top to bottom
        expectedLocalWorld.addEntity(0,4, Wall.getWall()); expectedLocalWorld.addEntity(1,4, Wall.getWall());
        expectedLocalWorld.addEntity(2,4, Wall.getWall()); expectedLocalWorld.addEntity(3,4, Wall.getWall());
        expectedLocalWorld.addEntity(4,4, Wall.getWall());



        // turning the expected world into a World string for comparison
        String expectedLocalWorldString = expectedLocalWorld.worldString();


        // getting the local view for the actual getLocal function

        // creating the file object to the 5x4 world
        File world = new File("worldbig.txt");

        // get the actual world
        World actualWorld = Reader.loadWorld(world);

        // getting the local world of size 5 centered on index 1,2 of the original world
        World actualLocalWorld = actualWorld.getLocal(5,1,2);

        // turning the actual world into a World string for comparison
        String actualLocalWorldString = actualLocalWorld.worldString();


        // checking if the string of the expected local world matches the string of the actual local world, if not
        // print an error message
        assertEquals(expectedLocalWorldString, actualLocalWorldString, "The getLocal function was not " +
                "able to create a local view of size 5 centered around index 1,2");

    }


    /**
     * A test to ensure that the heroAttackWhere function is able to return North when provided a 3x3 local world
     * with a monster (index 0,1) north of the hero centered at index 1,1
     */
    @Test
    void heroAttackNorth() {
        // creating the expected 3x3 local world, which will be empty for now
        World expectedLocalWorld = new World(3,3);

        //creating the hero
        Entity hero = new Hero(10,'H', 3,1);

        // adding in the hero to index 1,1 which is centered in our local world. The entity will be a hero with
        // 10 health, the symbol H, 3 weapon strength and 1 armor strength
        expectedLocalWorld.addEntity(1,1,hero);

        // adding in the monster entity that will be to the north of the hero at index 0,1, this is the entity the hero
        // should try to attack, Monster has symbol M, 10 health, and a Sword ('S')
        expectedLocalWorld.addEntity(0,1,new Monster(10,'M', WeaponType.getWeaponType('S')));

        // expected attack location is North
        Direction expectedDirection = Direction.NORTH;
        // getting the actual direction of attack returned by the attackWhere function
        Direction directionActual = hero.attackWhere(expectedLocalWorld);

        // checking if the actual direction of attack matches the expected, if not print an error message
        assertEquals(expectedDirection, directionActual, "Error the attackWhere function was unable to " +
                "return the correct direction of attack for the hero, which should be North");

    }



    /**
     * A test to ensure that the heroAttackWhere function is able to return null when provided a 3x3 local world
     * without a monster while the hero is centered at index 1,1
     */
    @Test
    void heroAttackNull() {
        // creating the expected 3x3 local world, which will be empty for now
        World expectedLocalWorld = new World(3,3);

        //creating the hero
        Entity hero = new Hero(10,'H', 3,1);

        // adding in the hero to index 1,1 which is centered in our local world. The entity will be a hero with
        // 10 health, the symbol H, 3 weapon strength and 1 armor strength
        expectedLocalWorld.addEntity(1,1,hero);

        // expected attack location is null because there are no monsters in this local world
        Direction expectedDirection = null;
        // getting the actual direction of attack returned by the attackWhere function
        Direction directionActual = hero.attackWhere(expectedLocalWorld);

        // checking if the actual direction of attack matches the expected, if not print an error message
        assertEquals(expectedDirection, directionActual, "Error the attackWhere function was unable to " +
                "return the correct direction of attack for the hero, which should be null");

    }


    /**
     * A test to ensure that the monsterAttackWhere function is able to return south east when provided a 3x3 local world
     * with a hero (index 2,2) south east of the monster centered at index 1,1
     */
    @Test
    void MonsterAttackSouthEast() {
        // creating the expected 3x3 local world, which will be empty for now
        World expectedLocalWorld = new World(3,3);

        //creating the hero
        Entity hero = new Hero(10,'H', 3,1);

        // adding in the hero to index 2,2 which is south east of the monster in our local world. The entity will be a
        // hero with 10 health, the symbol H, 3 weapon strength and 1 armor strength. This is the entity the monster
        // will attack
        expectedLocalWorld.addEntity(2,2,hero);

        // Creating the monster which has symbol M, 10 health, and a Sword ('S')
        Entity monster = new Monster(10,'M', WeaponType.getWeaponType('S'));

        // adding in the monster entity that will be centered in the local world at index 1,1,
        expectedLocalWorld.addEntity(1,1, monster);

        // expected attack location is South East
        Direction expectedDirection = Direction.SOUTHEAST;
        // getting the actual direction of attack returned by the attackWhere function
        Direction directionActual = monster.attackWhere(expectedLocalWorld);

        // checking if the actual direction of attack matches the expected, if not print an error message
        assertEquals(expectedDirection, directionActual, "Error the attackWhere function was unable to " +
                "return the correct direction of attack for the monster, which should be South East");

    }


    /**
     * A test to ensure that the monsterAttackWhere function is able to return south when provided a 3x3 local world
     * with a hero (index 2,1) south of the monster centered at index 1,1
     */
    @Test
    void MonsterAttackSouth() {
        // creating the expected 3x3 local world, which will be empty for now
        World expectedLocalWorld = new World(3,3);

        //creating the hero
        Entity hero = new Hero(10,'H', 3,1);

        // adding in the hero to index 2,1 which is south of the monster in our local world. The entity will be a
        // hero with 10 health, the symbol H, 3 weapon strength and 1 armor strength. This is the entity the monster
        // will attack
        expectedLocalWorld.addEntity(2,1,hero);

        // Creating the monster which has symbol M, 10 health, and a Sword ('S')
        Entity monster = new Monster(10,'M', WeaponType.getWeaponType('S'));

        // adding in the monster entity that will be centered in the local world at index 1,1,
        expectedLocalWorld.addEntity(1,1, monster);

        // expected attack location is South
        Direction expectedDirection = Direction.SOUTH;
        // getting the actual direction of attack returned by the attackWhere function
        Direction directionActual = monster.attackWhere(expectedLocalWorld);

        // checking if the actual direction of attack matches the expected, if not print an error message
        assertEquals(expectedDirection, directionActual, "Error the attackWhere function was unable to " +
                "return the correct direction of attack for the monster, which should be South");

    }


    /**
     * A test to ensure that the HeroChooseMove function is able to return north when provided a 5x5 local world
     * with a monster (index 0,2) north of the hero centered at index 2,2
     */
    @Test
    void heroMoveNorth() {
        // creating the expected 5x5 local world, which will be empty for now
        World expectedLocalWorld = new World(5,5);

        //creating the hero
        Entity hero = new Hero(10,'H', 3,1);

        // adding in the hero to index 2,2 which is centralized in the 5x5 local world
        // hero with 10 health, the symbol H, 3 weapon strength and 1 armor strength.
        expectedLocalWorld.addEntity(2,2,hero);

        // Creating the monster which has symbol M, 10 health, and a Sword ('S')
        Entity monster = new Monster(10,'M', WeaponType.getWeaponType('S'));

        // adding in the monster entity that will be north of the hero, monster will be at index 0,2. This will cause
        // the hero to move north
        expectedLocalWorld.addEntity(0,2, monster);

        // expected move direction is North
        Direction expectedDirection = Direction.NORTH;
        // getting the actual direction of movement returned by the chooseMove function
        Direction directionActual = hero.chooseMove(expectedLocalWorld);

        // checking if the actual direction of movement matches the expected, if not print an error message
        assertEquals(expectedDirection, directionActual, "Error the chooseMove function was unable to " +
                "return the correct direction of movement for the hero, which should be North");
    }


    /**
     * A test to ensure that the HeroChooseMove function is able to return NorthWest when provided a 5x5 local world
     * with no monster nearby while the hero is centered at index 2,2. This means the hero should return the default
     * movement direction of NorthWest
     */
    @Test
    void heroMoveNorthWest() {
        // creating the expected 5x5 local world, which will be empty for now
        World expectedLocalWorld = new World(5,5);

        //creating the hero
        Entity hero = new Hero(10,'H', 3,1);

        // adding in the hero to index 2,2 which is centralized in the 5x5 local world
        // hero with 10 health, the symbol H, 3 weapon strength and 1 armor strength.
        expectedLocalWorld.addEntity(2,2,hero);

        // expected move direction is NorthWest
        Direction expectedDirection = Direction.NORTHWEST;
        // getting the actual direction of movement returned by the chooseMove function
        Direction directionActual = hero.chooseMove(expectedLocalWorld);

        // checking if the actual direction of movement matches the expected, if not print an error message
        assertEquals(expectedDirection, directionActual, "Error the chooseMove function was unable to " +
                "return the correct default direction of movement for the hero, which should be North West");
    }


    /**
     * A test to ensure that the MonsterChooseMove function is able to return SouthEast when provided an empty 5x5 local
     * world and the monster is centered at index 2,2. Since there are no heroes for the monster to move to it should
     * move via its default direction which is south east
     */
    @Test
    void MonsterMoveSouthEast() {
        // creating the expected 5x5 local world, which will be empty for now
        World expectedLocalWorld = new World(5,5);

        // Creating the monster which has symbol M, 10 health, and a Sword ('S')
        Entity monster = new Monster(10,'M', WeaponType.getWeaponType('S'));

        // adding in the monster to index 2,2 which is centralized in the 5x5 local world
        expectedLocalWorld.addEntity(2,2,monster);


        // expected move direction is south east
        Direction expectedDirection = Direction.SOUTHEAST;
        // getting the actual direction of movement returned by the chooseMove function
        Direction directionActual = monster.chooseMove(expectedLocalWorld);


        // checking if the actual direction of movement matches the expected, if not print an error message
        assertEquals(expectedDirection, directionActual, "Error the chooseMove function was unable to " +
                "return the correct direction of movement for the monster, which should be South East");
    }


    /**
     * A test to ensure that the MonsterChooseMove function is able to return South when provided a 5x5 local
     * world and the monster is centered at index 2,2, with a hero south of it at index 4,2. The hero being in this
     * location should prompt the monster to move towards it to attack
     */
    @Test
    void MonsterMoveSouth() {
        // creating the expected 5x5 local world, which will be empty for now
        World expectedLocalWorld = new World(5,5);

        // Creating the monster which has symbol M, 10 health, and a Sword ('S')
        Entity monster = new Monster(10,'M', WeaponType.getWeaponType('S'));

        // adding in the monster to index 2,2 which is centralized in the 5x5 local world
        expectedLocalWorld.addEntity(2,2,monster);

        //creating the hero
        Entity hero = new Hero(10,'H', 3,1);

        // adding in the hero to index 4,2 which is south of the monster in the 5x5 local world
        // hero with 10 health, the symbol H, 3 weapon strength and 1 armor strength.
        expectedLocalWorld.addEntity(4,2,hero);


        // expected move direction is south
        Direction expectedDirection = Direction.SOUTH;
        // getting the actual direction of movement returned by the chooseMove function
        Direction directionActual = monster.chooseMove(expectedLocalWorld);


        // checking if the actual direction of movement matches the expected, if not print an error message
        assertEquals(expectedDirection, directionActual, "Error the chooseMove function was unable to " +
                "return the correct direction of movement for the monster, which should be South");
    }


}