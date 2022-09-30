import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
 * Jiaxu Kang
 * CSC 210, Fall 2018
 * PA6, Crispr.java
 * 
 * For this assignment, the ecosystem you will be implementing will 
 * consist of a number of columns and rows. The animals will move 
 * around this ecosystem. When they run into one another, depending on 
 * the animal type, they will either eat or reproduce. Animals with a 
 * Crispr gene will stop being able to reproduce in circumstances 
 * escribed later on in this specification. Animals must have enough to eatin 
 * order to stay alive. Animals are divided up into three different categories
 * : mammals, birds, and insects, all of which have unique characteristics.

Getting Started
 *
 * USAGE: 
 * java PA6Main nameOfFileForInput
 * The format here:
 * rows: 10
 * cols: 10

 * CREATE (1,1) warbler female 3
 * CREATE (1,1) thrush male 1
 * EAT
 * PRINT
 */
public class PA6Main {
    public static void main(String[] args) {
        Scanner input = null;
        try {
            input = new Scanner(new File(args[0]));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String[] a = input.nextLine().split(" ");
        int rows = Integer.valueOf(a[1]);
        String[] b = input.nextLine().split(" ");
        int cols = Integer.valueOf(b[1]);
        // create the rows*cols ecisySysten.
        EcosySystem system = new EcosySystem(rows, cols);
        input.nextLine();
        // collection the data.
        HashMap<String, String> types = type();
        // run the command.
        command(input, types, system);
    }

    // run different command and deal with data.
    // @return : void
    public static void command(Scanner input, HashMap<String, String> types,
            EcosySystem system) {
        while (input.hasNextLine()) {
            String[] line = input.nextLine().toLowerCase().split(" ");
            // create one animal.
            system = judge(line, types, system);

        }

    }

    public static EcosySystem judge(String[] line,
            HashMap<String, String> types, EcosySystem system) {
        if (line.length == 5) {
            set(line, types, system);
            return system;
        } else if (line[0].equals("print")) {
            print(system);
            return system;
        } else if (line[0].equals("eat")) {
            system = eat(line, system, types);
            return system;
        } else if (line[0].equals("move")) {
            system = move(line, system, types);
            return system;
        } // create the mosquitos.
        else if (line.length > 5) {
            String type = "insect";
            String[] position = dealPosition(line[1]);
            int row = Integer.valueOf(position[0].substring(1));
            int col = Integer.valueOf(
                    position[1].substring(0, position[1].length() - 1));
            Animal animal = new Mosquitos(row, col, line, system.col);
            system.set(animal);
            return system;
        } else if (line[0].equals("reproduce")) {
            system = reproduce(line, system, types);
            return system;
        } else if (line[0].equals("clear")) {
            system = new EcosySystem(system.rows, system.cols);
            return system;
        }
        return system;

    }

    // run the reproduce command. check if it can reproduce, then born it.
    // @return: Class EcosySystem.
    public static EcosySystem reproduce(String[] line, EcosySystem system,
            HashMap<String, String> types) {
        if (line.length == 1) {
            System.out.println("> REPRODUCE");
            System.out.println();
            system.reproduce();
        } // run REPRODUCE [type].
        else if (types.containsKey(line[1])) {
            System.out.println("> REPRODUCE " + line[1]);
            System.out.println();
            system.reproduce(line[1]);
        } // run REPRODUCE [x,y]
        else {
            System.out.println("> REPRODUCE " + line[1]);
            System.out.println();
            String[] position = dealPosition(line[1]);
            int x = Integer.valueOf(position[0].substring(1));
            int y = Integer.valueOf(
                    position[1].substring(0, position[1].length() - 1));
            system.reproduce(x, y);
        }
        return system;
    }

    // split (x,y).
    // @return: String[].
    public static String[] dealPosition(String string) {
        String[] position = string.split(",");
        return position;
    }

    // set value in EcosySystem.
    // @return: void.
    public static void set(String[] line, HashMap<String, String> types,
            EcosySystem system) {
        String type = chooseType(types, line[2]);
        String[] position = dealPosition(line[1]); // get x,y.
        int row = Integer.valueOf(position[0].substring(1));
        int col = Integer
                .valueOf(position[1].substring(0, position[1].length() - 1));
        // check type.
        Animal animal = createClass(type, row, col, line, system.cols);
        // set value;
        system.set(animal);

    }

    // print value.
    public static void print(EcosySystem system) {

        System.out.println("> PRINT");
        String data = system.print();
        System.out.println();

    }

    // run three different way move. Different types has different ways to move.
    // @return: Class EcosySystem.
    public static EcosySystem move(String[] line, EcosySystem system,
            HashMap<String, String> types) {
        EcosySystem newSystem = new EcosySystem(system.rows, system.cols);
        ArrayList<Animal> allAnimal = system.list;
        // run move();
        if (line.length == 1) {
            System.out.println("> MOVE");
            reset(allAnimal, newSystem, system);
            System.out.println();
        } else {
            // run move[type];
            if (types.containsKey(line[1])) {
                System.out.println("> MOVE " + line[1]);
                System.out.println();
                for (Animal element : allAnimal) {
                    if (element instanceof Mammals
                            && element.name.equals(line[1])) {
                        ((Mammals) element).move();
                        if (element.steps == 100) {
                            newSystem.set(null);
                        }
                        newSystem.set(element);
                    } else if (element instanceof Birds
                            && element.name.equals(line[1])) {
                        ((Birds) element).move();
                        if (element.steps == 50) {
                            newSystem.set(null);
                        }

                        newSystem.set(element);
                    } else if (element instanceof Insects
                            && element.name.equals(line[1])) {
                        ((Insects) element).move();
                        newSystem.set(element);
                    } else {
                        newSystem.set(element);
                    }
                }
            } // run move[animal];
            else if (types.containsValue(line[1])) {
                System.out.println("> MOVE " + line[1]);
                System.out.println();
                String type = line[1];
                for (Animal element : allAnimal) {
                    if (element.type.equals(line[1])
                            && element instanceof Mammals) {
                        ((Mammals) element).move();

                        newSystem.set(element);
                    } else if (element.type.equals(line[1])
                            && element instanceof Birds) {
                        ((Birds) element).move();

                        newSystem.set(element);
                    } else if (element instanceof Insects
                            && element.type.equals(line[1])) {
                        ((Insects) element).move();
                        newSystem.set(element);
                    } else {
                        newSystem.set(element);
                    }
                }
            } // run move[x,y];
            else if (line[1].substring(0, 1).equals("(")) {
                System.out.println("> MOVE " + line[1]);
                System.out.println();
                String[] position = dealPosition(line[1]);
                int x = Integer.valueOf(position[0].substring(1));
                int y = Integer.valueOf(
                        position[1].substring(0, position[1].length() - 1));
                if (x >= system.cols || y >= system.rows) {
                    return system;
                } else {
                    for (Animal element : allAnimal) {
                        if (element.row == x && element.col == y) {
                            if (element instanceof Mammals) {
                                ((Mammals) element).move();
                                newSystem.set(element);
                            } else if (element instanceof Birds) {
                                ((Birds) element).move();
                                newSystem.set(element);
                            } else if (element instanceof Insects) {
                                ((Insects) element).move();
                                newSystem.set(element);
                            }
                        } else {
                            newSystem.set(element);
                        }
                    }
                }
            } else {
                System.out.println("> MOVE " + line[1]);
                System.out.println();
                return system;
            }
        }
        return newSystem;
    }

    // reset the EcosySystem.
    // @return void.
    public static void reset(ArrayList<Animal> allAnimal,
            EcosySystem newSystem, EcosySystem system) {
        // loop all position.
        for (int i = 0; i < system.grid.length; i++) {
            for (int j = 0; j < system.grid[i].length; j++) {
                for (int h = 0; h < system.grid[i][j].size(); h++) {
                    if ((system.grid[i][j].get(h) instanceof Birds)) {
                        // check if it alive.
                        if (((Birds) system.grid[i][j].get(h)).steps
                                - system.eatStep == 10
                                && ((Birds) system.grid[i][j]
                                        .get(h)).ifEat == false) {
                        }else {
                            ((Birds) system.grid[i][j].get(h)).move();
                            newSystem.set((Animal) system.grid[i][j].get(h));
                        }
                    }
                    if ((system.grid[i][j].get(h) instanceof Insects)) {
                        ((Insects) system.grid[i][j].get(h)).move();
                        newSystem.set((Animal) system.grid[i][j].get(h));
                    }
                    if ((system.grid[i][j].get(h) instanceof Mammals)) {
                        if (((Mammals) system.grid[i][j].get(h)).steps == 10) {
                        } else {
                            ((Mammals) system.grid[i][j].get(h)).move();
                            newSystem.set((Animal) system.grid[i][j].get(h));
                        }
                    }
                }
            }
        }
    }

    // run eat command.
    // @return: Class EcosySystem.
    public static EcosySystem eat(String[] line, EcosySystem system,
            HashMap<String, String> types) {
        // run eat();
        if (line.length == 1) {
            system.eat();
            System.out.println("> EAT");
            System.out.println();
            return system;
        } // run eat(type).
        else if (types.containsKey(line[1])) {
            system.eat(line[1]);
            System.out.println("> EAT " + line[1]);
            System.out.println();
            return system;
        } // run eat(x,y);
        else {
            String[] position = dealPosition(line[1]);
            int x = Integer.valueOf(position[0].substring(1));
            int y = Integer.valueOf(
                    position[1].substring(0, position[1].length() - 1));
            system.eat(x, y);
            System.out.println("> EAT " + line[1]);
            System.out.println();
            return system;
        }

    }

    // check types, and set the class.
    // @return Class Animal
    public static Animal createClass(String type, int row, int col,
            String[] line, int cols) {
        if (type.equals("mammal")) {
            Animal animal = new Mammals(row, col, line, cols);
            return animal;
        }
        if (type.equals("bird")) {
            Animal animal = new Birds(row, col, line, cols);
            return animal;
        }
        if (type.equals("insect")) {
            Animal animal = new Insects(row, col, line, cols);
            return animal;

        }
        return null;
    }

    // check type.
    // return String
    public static String chooseType(HashMap<String, String> types,
            String name) {
        String type = "";
        for (String key : types.keySet()) {
            if (key.equals(name)) {
                type = types.get(key);
            }
        }
        return type;
    }

    // collection the data.
    // return HashMap.
    public static HashMap<String, String> type() {
        HashMap<String, String> typea = new HashMap<String, String>();
        String[] mammals = { "elephant", "rhinoceros", "lion", "cheeta",
                "giraffe", "zebra" };
        String[] birds = { "thrush", "owl", "warbler", "dove", "shrike" };
        String[] insects = { "mosquito", "bee", "fly", "horsefly", "ant" };
        for (String mam : mammals) {
            typea.put(mam, "mammal");
        }
        for (String bird : birds) {
            typea.put(bird, "bird");
        }
        for (String insect : insects) {
            typea.put(insect, "insect");
        }
        return typea;
    }
}
