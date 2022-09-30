import java.util.HashMap;
import java.util.Random;

/*This is insects class. 
 * It is the subclass of insects.
 * It run the special behavior which mosquitos have. 
 * 
 */
public class Mosquitos extends Insects {
    public Boolean jud1;
    public Boolean jud2;
    public String judge1;
    public String judge2;
    public HashMap<Integer, String> map;
    public Mosquitos(int row, int col, String[] line, int cols) {
        super(row, col, line, cols);
        jud1 = Boolean.valueOf(line[5]);
        jud2 = Boolean.valueOf(line[6]);
        judge1 = line[5];
        judge2 = line[6];
        map = new HashMap<>();
        map.put(0, "male");
        map.put(1, "female");
    }

    // run mosquitos behavior.
    public Animal reproduce(Mosquitos other) {
        String[] line = { "", "", "baby", "gender", "false", judge1,
                other.judge1 };
        Animal baby = new Mosquitos(row, col, line, max);
        Random rand = new Random();
        int j = rand.nextInt(2);
        baby.gender = map.get(j);

        return baby;
    }
}
