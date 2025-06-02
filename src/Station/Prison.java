package Station;

import Persons.Suspect;
import java.util.ArrayList;

public class Prison {
    private ArrayList<Suspect> prisoners;

    public Prison() {
        this.prisoners = new ArrayList<>();
    }

    public void addPrisoner(Suspect suspect) {
        if (!prisoners.contains(suspect)) {
            prisoners.add(suspect);
        }
    }

    public ArrayList<Suspect> getPrisoners() {
        return prisoners;
    }

    public boolean removePrisoner(Suspect suspect) {
        return prisoners.remove(suspect);
    }

    public void displayAllPrisoners() {
        if (prisoners.isEmpty()) {
            System.out.println("No prisoners in custody.");
        } else {
            System.out.println("Prisoners in custody:");
            for (Suspect prisoner : prisoners) {
                prisoner.displayDetails();
            }
        }
    }
}