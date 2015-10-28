package Excercise;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.db4o.*;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

public class Ex2 {

	public static void main(String[] args){

// ########## �BUNG2 ##########
		
		// open DB
		
		new File ("test.db").delete();
		ObjectContainer db = Db4o.openFile("test.db");
		int j = 0;
		final int[] points = {1, 90, 100};

		try

		{
			// Speichere zwei Personen
			db.store(new Pilot("Michael Schuhmacher", 45));
			db.store(new Pilot("Chuck Norris", 100));
			db.store(new Pilot("Rubens Barrichello", 42));

			
			ObjectSet resultAfter = db.queryByExample(Pilot.class); // Mit Klassenobjekt aufgerufen
			while (resultAfter.hasNext()) {
				if (j<1) {
					System.err.println("Daten:");
				}
				j++;	
				System.out.println(resultAfter.next());
			}
			// a.) Fahrer mit 100 Punkten
			List < Pilot > pilots = db.query ( new Predicate <Pilot>() {
				public boolean match ( Pilot o) {
				return o.getPoints() == 100;
				}
				});
			
			System.out.println("\nFahrer mit 100 Punkten" + pilots.toString());
			
			// b.) Fahrer, deren Punktzahl zwischen 99 und 199 Punkten liegt oder Rubens Barrichello.
			
			List < Pilot > pilots2 = db.query ( new Predicate <Pilot>() {
				public boolean match ( Pilot o) {
				return o.getPoints() >98 && o.getPoints() <199 || o.getName().equals("Rubens Barrichello");
				}
				});
			System.out.println("\nFahrer zwischen 99 und 199 Punkten oder Barrichello" + pilots2.toString());
			
			// c.) Fahrer, deren Punktzahl zu denen einer gegebene Liste final int[] points = f1, 90, 100g;
			
			List < Pilot > pilots3 = db.query ( new Predicate <Pilot>() {
				List booltmp;
				public boolean match ( Pilot o) {
					
						return (Arrays.binarySearch(points, o.getPoints()) >=0);
					
				}
				});
			System.out.println("\nFahrer einer gegebenen Liste final int[] points = 1, 90, 100" + pilots3.toString());
			
			// Aufgabe 3 SODA Anfrage 100 Punkte
			Object myValue = 100;
			
			Query query = db.query ();
			query.constrain ( Pilot.class ); // Einschraenken auf Klasse
			query.descend ("m_points").constrain (myValue ); // Bedingung an Attribut
			ObjectSet <Pilot> result = query.execute (); // Anfrage stellen
			System.out.println("\nSODA Anfrage a 100 Punkte" + result.toString());
			
			// Aufgabe 3 SODA, deren Punktzahl zwischen 99 und 199 Punkten liegt oder Rubens Barrichello.
			
			
			Query query2=db.query();
			query.constrain(Pilot.class);
			Query pointQuery=query.descend("m_points");
			query.descend("m_name").constrain("Rubens Barrichello")
			.or(pointQuery.constrain(99).greater()
			.and(pointQuery.constrain(199).smaller()));
			ObjectSet result2=pointQuery.execute();
			
			System.out.println("\nSODA Anfrage Zwischen 99 und 199 Punkte oder Barrichello" + result2.toString());
			
			
		} finally

		{
		// close DB
		db.close();
		
	}
}
}

