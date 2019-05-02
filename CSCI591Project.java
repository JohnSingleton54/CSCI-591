package notSurePackage;

import java.util.Random;

public class CSCI591Project {

	public static void main(String[] args) {
		/* first bits
		Coordinate[] four = new Coordinate[4];
		four[0] = new Coordinate(1,0);
		four[1] = new Coordinate(0,1);
		four[2] = new Coordinate(-1,0);
		four[3] = new Coordinate(0,-2);

		int result = ShewchuksDeterminant.orientationIndex(four[0], four[1], four[3]);
		System.out.println(result);
		
		boolean result2 = TriPredicate.isInCircleRobust(four[0], four[1], four[2], four[3]);
		if(result2) {
			System.out.println("It's in the circle.");
		}
		else {
			System.out.println("It's not in the circle.");
		}
		
		QuadEdge e0 = QuadEdge.makeEdge( new Vertex(four[0]), new Vertex (four[1]) );
		QuadEdge e1 = QuadEdge.makeEdge( new Vertex(four[1]), new Vertex (four[2]) );
		QuadEdge.splice(e0.sym(), e1);
		QuadEdge e2 = QuadEdge.makeEdge( new Vertex(four[2]), new Vertex (four[0]) );
		QuadEdge.splice(e1.sym(), e2);
		QuadEdge.splice(e2.sym(), e0);
		//QuadEdge e2 = QuadEdge.connect(e1, e0);
		System.out.println(e0.lNext().orig().getX());
		//QuadEdge e3 = QuadEdge.makeEdge( new Vertex(four[2]), new Vertex (four[3]) );
		//QuadEdge e4 = QuadEdge.makeEdge( new Vertex(four[3]), new Vertex (four[0]) );
		*/

		Coordinate[] frame = new Coordinate[3];
		frame[0] = new Coordinate(0  , 0  );
		frame[1] = new Coordinate(100, 0  );
		frame[2] = new Coordinate(0  , 100);

		QuadEdge e0 = QuadEdge.makeEdge( new Vertex(frame[0]), new Vertex (frame[1]) );
		QuadEdge e1 = QuadEdge.makeEdge( new Vertex(frame[1]), new Vertex (frame[2]) );
		QuadEdge.splice(e0.sym(), e1);
		QuadEdge e2 = QuadEdge.makeEdge( new Vertex(frame[2]), new Vertex (frame[0]) );
		QuadEdge.splice(e1.sym(), e2);
		QuadEdge.splice(e2.sym(), e0);
		//System.out.println(e0.lNext().orig().getX());
		
		//Is e0.oNext() equal to e2.sym() or e0? Let's find out:
		//QuadEdge e54 = e0.oNext();
		//System.out.printf("x-coord of origin of e54: %f\ny-coord of origin of e54: %f\n", e54.orig().getX(), e54.orig().getY());
		//System.out.printf("x-coord of dest. of e54: %f\ny-coord of dest. of e54: %f\n", e54.dest().getX(), e54.dest().getY());
		//It was equal to e2.sym().

		
		Vertex[] sites = { new Vertex(new Coordinate(5, 50 )),
		                   new Vertex(new Coordinate(10, 75)) };
		
		// If a pointer to one edge is passed to a method, then the method gets the whole quad-edge data structure.
		for(int i=0; i < sites.length; i++) {
			insertSite(sites[i], e0);
		}
		
		

	}
	
	static void insertSite(Vertex v, QuadEdge e0) {
		QuadEdge e = locate(v, e0);
		System.out.printf("x-coord of origin of e: %f\ny-coord of origin of e: %f\n", e.orig().getX(), e.orig().getY());
		
		// Connect the new site v to the sites around it.
		Vertex first = e.orig();
		QuadEdge base = QuadEdge.makeEdge(first, v);
		QuadEdge.splice(base, e);
		// ***The repeat-until loop of the paper's pseudocode is like a do-while loop in java (and not a while loop).***
		do {
			base = QuadEdge.connect( e,  base.sym() );
			e = base.oPrev();
		} 
		while( !(e.dest().equals(first)) );
		
		
		// Deal with the suspect edges.
		QuadEdge t;
		while(true) {
			t = e.oPrev();
			if (t.dest().rightOf(e) && v.isInCircle(e.orig(), t.dest(), e.dest())) {
				System.out.println("Marcellin Boule did not think much of the Neanderthals.");
				QuadEdge.swap(e);
				e = t;
			}
			else if (e.orig().equals(first)) {
				return;
			}
			else {
				e = e.oNext().lPrev();
			}
		}		
	}
	
	static QuadEdge locate(Vertex v, QuadEdge e0) {
		QuadEdge e = e0;
		//System.out.println(e.lNext().orig().getY());
		while(true) {
			if(v.rightOf(e)) {
				System.out.println("New Zealand's megafauna was exclusively avian.");
				e = e.sym();
				return null;
			}
			else if (  !( v.rightOf(e.oNext()) )  ) {
				System.out.println("The Descent of Man appeared in 1871.");
				e = e.oNext();
			}
			else if (  !( v.rightOf(e.dPrev()) )  ) {
				System.out.println("Geomyces destructans is bad for bats!");
				e = e.dPrev();
			}
			else {
				System.out.println("Chytrid fungus is bad for amphibians!");
				return e;
			}
		}
	}
}
