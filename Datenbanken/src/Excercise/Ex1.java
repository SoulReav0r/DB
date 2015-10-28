package Excercise;
import java.io.File;
import java.util.List;

import com.db4o.*;
import com.db4o.query.Predicate;

public class Ex1 {

	public static void main(String[] args){

// ########## �BUNG 1 ##########
		
		int i = 0;
		int j = 0;
		
	
		// open DB
		
		new File ("test.db").delete();
		ObjectContainer db = Db4o.openFile("test.db");
		

		try

		{
			// Speichere zwei Personen
			db.store(new Pilot("Michael Schuhmacher", 45));
			db.store(new Pilot("Chuck Norris", 100));

			// Iteriere ueber alle Personen
			ObjectSet result; 
			result = db.queryByExample(new Pilot(null,0));
			
			while (result.hasNext()) {
				if (i<1) {
					System.err.println("Daten vorher:");
				}
				i++;
				System.out.println(result.next());
			}

			// Veraendere eine Person
			result = db.queryByExample(new Pilot("Chuck Norris", 100));
			Pilot found = (Pilot) result.next();
			found.addPoints(2);
			db.store(found);
			
			ObjectSet resultAfter = db.queryByExample(Pilot.class); // Mit Klassenobjekt aufgerufen
			while (resultAfter.hasNext()) {
				if (j<1) {
					System.err.println("Daten nachher:");
				}
				j++;
				
				System.out.println(resultAfter.next());
			}
			

// ########## �BUNG 2 ##########
			
			List < Pilot > pilots = db.query ( new Predicate <Pilot>() {
				public boolean match ( Pilot o) {
				return o.getPoints() == 102;
				}
				});
			
		} finally

		{
		// close DB
		db.close();
		
	}
}
}
