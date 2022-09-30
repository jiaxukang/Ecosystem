import java.util.ArrayList;

/*This is EcosySystem class. 
 *
 * It is a system of animal. 
 * 
 */
public class EcosySystem {
    int rows;
    int cols;
    int row;
    int col;
    ArrayList[][] grid;
    char[][] grids;
    ArrayList<Animal> list;
    int eatStep;
    String one;
    String two;

    // set the grid of system.
    public EcosySystem(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new ArrayList[rows][cols];
        this.eatStep = 0;
        this.list = new ArrayList<Animal>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new ArrayList<Animal>();

            }
        }
    }

    // print the system.
    public String print() {
        String content = "";

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                if (grid[i][j].size() == 0) {
                    content += ".";

                } 
                else {
                    content += grid[i][j].get(0);
                }
            }
            content += "\n";
        }
        System.out.print(content);
        System.out.println();
        return content;
    }

    // give value of grid position.
    public void set(Animal animal) {
        this.col = animal.col;
        this.row = animal.row;
        grid[animal.row][animal.col].add(animal);
        list.add(animal);
    }

    // run the all types eat behavior.
    public ArrayList[][] eat() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].size() > 1) {
                    if ((grid[i][j].get(0) instanceof Birds)
                            && (grid[i][j].get(1) instanceof Insects)) {
                        ((Birds) grid[i][j].get(0)).ifEat = true;
                        eatStep = ((Birds) grid[i][j].get(0)).steps;
                        grid[i][j].remove(grid[i][j].get(1));
                        list.remove(1);
                    }
                    if ((grid[i][j].get(0) instanceof Insects)
                            && (grid[i][j].get(1) instanceof Birds)) {
                        ((Birds) grid[i][j].get(1)).ifEat = true;
                        eatStep = ((Birds) grid[i][j].get(1)).steps;
                        grid[i][j].remove(grid[i][j].get(0));
                        list.remove(0);
                    }
                    if ((grid[i][j].get(0) instanceof Mammals)
                            && (grid[i][j].get(1) instanceof Mammals)) {
                        grid[i][j].remove(grid[i][j].get(1));
                        list.remove(1);
                    }
                }
            }
        }
        return grid;
    }

    // run reproduce(type).
    public ArrayList[][] reproduce(String line) {
        for (Animal elment : list) {
            if (elment.name == line) {
                return reproduce();
            }
        }
        return grid;
    }

    // run reproduce().
    public ArrayList[][] reproduce() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].size() > 1) {
                    String gen1 = ((Animal) grid[i][j].get(0)).gender;
                    String gen2 = ((Animal) grid[i][j].get(1)).gender;
                    // check gender.
                    if (!(gen1 == gen2)) {
                        // check types
                        if ((grid[i][j].get(0) instanceof Birds)
                                && (grid[i][j].get(1) instanceof Birds)) {
                            Animal baby = (((Birds) grid[i][j].get(0))
                                    .reproduce());
                            set(baby);
                        }
                        if (grid[i][j].get(0) instanceof Mammals
                                && (grid[i][j].get(1) instanceof Mammals)) {
                            Animal baby = (((Mammals) grid[i][j].get(0))
                                    .reproduce());
                            set(baby);
                        }
                        if ((grid[i][j].get(0) instanceof Insects)
                                && (grid[i][j].get(1) instanceof Insects)
                                && !(grid[i][j].get(0) instanceof Mosquitos)) {
                            Animal baby = (((Insects) grid[i][j].get(0))
                                    .reproduce());
                            set(baby);
                        }
                        if ((grid[i][j].get(0) instanceof Mosquitos)
                                && (grid[i][j].get(1) instanceof Mosquitos)) {
                            if (!((((Mosquitos) grid[i][j].get(0)).jud1 == true)
                                    && (((Mosquitos) grid[i][j]
                                            .get(0)).jud2 == true))) {
                                if (!((((Mosquitos) grid[i][j]
                                        .get(1)).jud1 == true)
                                        && (((Mosquitos) grid[i][j]
                                                .get(1)).jud2 == true))) {
                                    Animal baby = (((Mosquitos) grid[i][j]
                                            .get(0)).reproduce(
                                                    ((Mosquitos) grid[i][j]
                                                            .get(1))));
                                    set(baby);
                                }
                            }
                        }
                        return grid;
                    }
                    return grid;

                } else {
                    return grid;
                }

            }
        }
        return grid;

    }

    // run the reproduce(x,y)
    public ArrayList[][] reproduce(int x, int y) {
        if (x == row && y == col && grid[x][y].size() > 1) {
            String gen1 = ((Animal) grid[x][y].get(0)).gender;
            String gen2 = ((Animal) grid[x][y].get(1)).gender;
            if (!(gen1 == gen2)) {
                if ((grid[x][y].get(0) instanceof Birds)
                        && (grid[x][y].get(1) instanceof Birds)) {
                    Animal baby = (((Birds) grid[x][y].get(0)).reproduce());
                    set(baby);
                }
                if (grid[x][y].get(0) instanceof Mammals
                        && (grid[x][y].get(1) instanceof Mammals)) {
                    Animal baby = (((Mammals) grid[x][y].get(0)).reproduce());
                    set(baby);
                }
                if ((grid[x][y].get(0) instanceof Insects)
                        && (grid[x][y].get(1) instanceof Insects)) {
                    Animal baby = (((Insects) grid[x][y].get(0)).reproduce());
                    set(baby);
                }
                return grid;
            }
        }
        return grid;
    }

    // run the eat behavior.
    public ArrayList[][] eat(String type) {
        for (Animal animal : list) {
            if (animal.name.equals(type)) {
                return eat();
            }
        }
        return grid;
    }

    // run eat(x,y).
    public ArrayList[][] eat(int x, int y) {
        if (x == row && y == col && grid[x][y].size() > 1) {
            if ((grid[x][y].get(0) instanceof Birds)
                    && (grid[x][y].get(1) instanceof Insects)) {
                grid[x][y].remove(grid[x][y].get(1));
                list.remove(1);
            }
            if ((grid[x][y].get(0) instanceof Insects)
                    && (grid[x][y].get(1) instanceof Birds)) {
                grid[x][y].remove(0);
                list.remove(0);
            }
            if ((grid[x][y].get(0) instanceof Mammals)
                    && (grid[x][y].get(1) instanceof Mammals)) {
                grid[x][y].remove(1);
                list.remove(1);
            }
        }
        return grid;
    }
}
