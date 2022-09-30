import java.util.HashMap;
import java.util.Random;

/*This is insects class. 
 * It is the subclass of animal.
 * It run the special behavior which insects have. 
 * 
 */

public class Insects extends Animal {

    public int n;
    public int x;
    public int circle;
    public HashMap<Integer, Boolean> mapa;

    public Insects(int row, int col, String[] line,
            int cols) {
        this.max = cols;
        this.row = row;
        this.col = col;
        this.name = line[2];
        this.gender = line[3];
        this.value = Boolean.valueOf(line[4]);
        this.n = 1;
        this.x = 0;
        this.type = "insect";
        this.steps = 0;
        this.circle = 1;
        mapa = new HashMap<>();
        mapa.put(1, true);
        mapa.put(0, false);
    }

    public String type() {
        return type;
    }

    // Insects move behavior.
    public void move() {

        if (value == true) {
            left();
        }
        if (value == false) {
            right();
        }

        steps += 1;

    }

    public void right() {
        if (0 <= steps && steps < 4) {

            x = 0;
            n = 1;
            changes();
        }
        if (4 <= steps && steps < 12) {

            x = 4;
            n = 2;
            changes();
        }
        if (12 <= steps && steps <= 20) {

            x = 12;
            n = 3;
            changes();
        }
        
    }

    public void changes() {
        if ((steps - x) / n == 0) {
            col -= 1;
            if (col == -1) {
                col = max - 1;
            }

        }
        else if ((steps - x) / n == 1) {
            row += 1;
            if (row == max) {
                row = 0;
            }

        }
        else if ((steps - x) / n == 2) {
            col += 1;
            if (col == max) {
                col = 0;

            }
        }
        else if ((steps - x) / n == 3) {
            row -= 1;
            if (row == -1) {
                row = max - 1;
            }

        }
        
    }

    public void left() {
        if (0 <= steps && steps < 4) {
            x = 0;
            n = 1;
            change();
        }
        if (4 <= steps && steps < 12) {
            x = 4;
            n = 2;
            change();
        }
        if (12 <= steps && steps <= 20) {
            x = 12;
            n = 3;
            change();
        }

    }

    public void change() {
        if ((steps - x) / n == 0) {
            col -= 1;
            if (col == -1) {
                col = max - 1;
            }
        }
        if ((steps - x) / n == 1) {
            row -= 1;
            if (row == -1) {
                row = max - 1;
            }
        }
        if ((steps - x) / n == 2) {
            col += 1;
            if (col == max) {
                col = 0;
            }
        }
        if ((steps - x) / n == 3) {
            row += 1;
            if (row == max) {
                row = 0;
            }
        }

    }

    // Insect reproduce behavior.
    public Animal reproduce() {
        String[] line = { "", "", "baby", "gender", "true" };
        Animal baby = new Mammals(row, col, line, max);
        Random rand = new Random();
        int j = rand.nextInt(2);
        int i = rand.nextInt(2);
        baby.value = mapa.get(i);
        baby.gender = map.get(j);
        return baby;
    }
}
