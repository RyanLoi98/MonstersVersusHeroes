# ⚔️ Monsters vs Heroes – Java OOP Simulation Game

**CPSC 233 – Introduction to Computer Science II**
**Assignment 2 – Winter 2022**
Author: Ryan Loi

## 🧙‍♂️ Project Overview

This project is an object-oriented, grid-based **Monsters vs Heroes** simulation written in Java. The game reads a world configuration from a file, places Heroes and Monsters on a 2D grid, and runs a turn-based simulation of movement and combat. The game ends when all of one faction has been eliminated or when the user ends it manually.

This assignment highlights key **object-oriented programming (OOP)** concepts including:

* Encapsulation, Inheritance, and Polymorphism
* Abstract classes and Interfaces
* Enumerations for direction, symbols, and weapon types
* File I/O using `java.io`
* Static methods and constants
* Unit testing with JUnit 5

---

## 🕹️ How to Run the Game


```bash
cd src
javac @sources.txt
```

```bash
# from the src directory run
java mvh.Main world.txt log.txt 12345

```

* `world.txt`: Input file defining the game grid and initial entities (I have 2 world files for you to choose from (they are in the src directory), `world.txt` and `worldbig.txt` for the larger game world)
* `log.txt`: Output file to record game logs and simulation steps (this is just the name you want to call your output file that logs the game activity)
* `12345`: Random seed for consistent game behavior (change it to vary outcomes) 


When the game starts to can either enter: C into the terminal to complete the entire simulation, E to exit, or any other key press to go through the game step by step.


## 📸 Demo Screenshot

Here’s a screenshot of the game in action:

![Screenshot](https://imgur.com/vi9wTaV.png)

---

## 📜 Game Features

* 🗺️ **2D Grid-Based Simulation** with surrounding walls and customizable size
* 🧟‍♂️ **Entity Types**: Heroes and Monsters with unique attack/armor logic
* ⚔️ **Combat System**: Entities attack based on nearby opponents
* 🧠 **AI Movement & Attack** logic via local views (3x3 or 5x5)
* 💾 **File Input**: `Reader.loadWorld()` parses a CSV-style world definition
* 🔄 **Turn-based Simulation** runs until one faction is wiped out
* 📄 **Logging Support** to monitor progression via `log.txt`
* 🔍 **Bonus Support** for advanced types (Mage, Fighter, Rogue, Kobold, etc.)

---

## 🧪 Unit Testing

The project includes **8+ JUnit 5 test cases** for key functions:

* `Reader.loadWorld()` – 2 tests
* `World.gameString()` – 1 test
* `World.worldString()` – 1 test
* `World.getLocal()` – 3 tests
* `Hero.chooseMove()` / `attackWhere()` – 2 each
* `Monster.chooseMove()` / `attackWhere()` – 2 each

Tests are located in the `mvh/world/` folder (the file is named `MvHTest.java`) and validate game correctness with controlled grid inputs. To use this test file ensure you
have all the JUnit 5 dependencies downloaded to your computer and accessible to the test file, you can do this via command line or use IntelliJ to set it up for you - which is what I personally did. Afterwards compile the test file and run it.


To run the tests:

```bash
#cd into the src/mhv/world directory
javac -cp .:junit-platform-console-standalone-1.9.0.jar MvHTest.java
java -jar junit-platform-console-standalone-1.9.0.jar --class-path . --scan-classpath
```


## 📸 Demo Screenshot

Here’s a screenshot of the tests in action:

![Screenshot](https://imgur.com/1rZzwIQ.png)

---

## 🧱 Object-Oriented Design Highlights

| Concept        | Example Files                             |
| -------------- | ----------------------------------------- |
| Abstraction    | `Entity.java` (abstract class)            |
| Inheritance    | `Hero.java`, `Monster.java`               |
| Polymorphism   | `attackWhere()` / `chooseMove()`          |
| Enums          | `Direction.java`, `WeaponType.java`       |
| Static Classes | `Logger.java`, `Reader.java`              |
| Encapsulation  | Internal state management in `World.java` |

---

## 📁 Example Input (`world.txt`)

```
3
3
0,0,MONSTER,M,10,S
0,1
0,2
1,0
1,1
1,2
2,0
2,1
2,2,HERO,H,10,3,1
```

---

## 🧠 Learning Outcomes

* Gained hands-on experience with Java OOP principles
* Practiced designing and maintaining class hierarchies
* Learned to work with enumerations and abstract classes
* Implemented AI-like movement and combat decision-making
* Gained experience reading/writing structured file input/output
* Developed robust unit tests using **JUnit 5**