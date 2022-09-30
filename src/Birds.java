import java.util.Random;

/*This is birds class. 
 * It is the subclass of animal.
 * It run the special behavior which birds have. 
 * 
 */
public class Birds extends Animal {
    public int num;
    public Boolean ifEat;

    public Birds(int row, int col, String[] line,
            int cols) {
        ifEat = false;
        this.max = cols;
        this.row = row;
        this.col = col;
        this.name = line[2];
        this.gender = line[3];
        this.num = Integer.valueOf(line[4]);
        this.type = "bird";
    }

    // birds move behavior.
    public void move() {
        if (num > (steps % (num * 3))) {
            row += 1;
            if (row == max) {
                row = 0;
            }

        }
        else if ((num <= steps % (num * 3))
                && ((steps % (num * 3)) < (num * 2))) {
            col += 1;
            if (col == max) {
                col = 0;
            }

        } else {
            row -= 1;
            if (row == -1) {
                row = max - 1;
            }

        }
        steps += 1;
    }

    // birds reproduce behavior.
    public Animal reproduce() {
        if ((num > (steps % (num * 3))) && ((num <= steps % (num * 3))
                && ((steps % (num * 3)) < num * 2))) {
            String[] line = { "", "", "baby", "gender", "5" };
            Animal baby = new Mammals(row, col, line, max);
            Random rand = new Random();
            int j = rand.nextInt(2);
            baby.gender = map.get(j);
            baby.steps = (int) (Math.random() * 10);
            return baby;
        }
        return null;
    }
    public String type() {
        return type;
    }

}
