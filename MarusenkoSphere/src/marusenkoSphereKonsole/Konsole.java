package marusenkoSphereKonsole;

import marusenkoSphereKugel.Kugel;
import marusenkoSphereKugel.Solver;

public class Konsole {

	public Kugel k;
	protected Solver s = new Solver(); 
	
	public Konsole(Kugel k){
		this.k = k;
	
		for(long i = 0;i<9223372036854775807L;i++){
			k.FillKugelRandom();
			System.out.println(i+". Kugel solved in "+k.steps+" steps");
		}
		
	}
}

