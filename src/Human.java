
public class Human extends Mammals {

    public Boolean jud;

    public Human(int row, int col, String[] line, int cols) {
        super(row, col, line, cols);
        jud = Boolean.valueOf(line[5]);
    }

    public Animal reproduce() {
        return null;
    }
}
