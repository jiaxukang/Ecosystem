import java.util.HashMap;
import java.util.Random;

/*This is mammals class. 
 * It is the subclass of animal.
 * It run the special behavior which mammal have. 
 * 
 */
public class Mammals extends Animal {
    public int time;
    public HashMap<Integer, String> maps;

    public Mammals(int row, int col, String[] line, int cols) {
        this.max = cols;
        this.row = row;
        this.col = col;
        this.name = line[2];
        this.gender = line[3];
        this.direction = line[4];
        this.steps = 0;
        this.type = "mammal";
        this.time = 0;
        maps = new HashMap<>();
        maps.put(0, "right");
        maps.put(1, "left");

    }

    public String type() {
        return type;
    }

    // run mammal move behavior.
    public void move() {
        if (direction.equals("right")) {
            if (steps % 2 == 0) {
                row += 1;
                if (row == max) {
                    row = 0;
                }
            }
            else if (steps % 2 == 1) {
                col += 1;
                if (col == max) {
                    col = 0;
                }
            }
        }
        else if (direction.equals("left")) {
            if (steps % 2 == 0) {

                row -= 1;

                if (row == -1) {
                    row = max - 1;
                }
            }
            else if (steps % 2 == 1) {
                col -= 1;
                if (col == -1) {
                    col = max - 1;
                }
            }

        }
        steps += 1;
    }

    // run mammals reproduce behavior.
    public Animal reproduce() {
        if (time == 5) {
            return null;
        } else {
            Random rand = new Random();
            int j = rand.nextInt(2);
            int i = rand.nextInt(2);
            String[] line = { "", "", "baby", "gender", "right" };
            Animal baby = new Mammals(row, col, line, max);
            time += 1;
            baby.gender = map.get(j);
            baby.direction = maps.get(i);
            return baby;
        }
    }

    public void move(String type) {
        move();

    }

}
