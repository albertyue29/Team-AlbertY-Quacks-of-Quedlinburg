import java.util.ArrayList;
import java.util.Random;

public class Bag {
    private ArrayList<IngredientChip> chips;
    private Random rand;

    public Bag() {
        this.chips = new ArrayList<IngredientChip>();
        this.rand = new Random();
    }

    public IngredientChip getRandomChip() {
        if (chips.isEmpty()) {
            return null;
        }
        int index = rand.nextInt(chips.size());
        return chips.remove(index);
    }

    public void addChip(IngredientChip chip) {
        if (chip != null) {
            chips.add(chip);
        }
    }

    public void removeChip(IngredientChip chip) {
        chips.remove(chip);
    }

    public ArrayList<IngredientChip> getChips() {
        return chips;
    }

    public boolean isEmpty() {
        return chips.isEmpty();
    }
}
