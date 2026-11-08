
import java.util.ArrayList;
import java.util.Random;

 public class Bag {
  
	private ArrayList<IngredientChip> chips;
	
	public Bag() {
		chips=new ArrayList<IngredientChip>();
		
	}
	
	public IngredientChip getRandomChip() {
		
		if (chips.size() == 0) {
	        return null;
	    }
			
		  Random rand = new Random();
		  int index = rand.nextInt(chips.size());
			return chips.get(index);

	}
	
	public void addChip(IngredientChip chip) {
	    chips.add(chip);

	}
	
	public void removeChip(IngredientChip chip){
	    chips.remove(chip);
	}

 }

