package marusenkoSphere;
/*
import java.util.Scanner;
*/
import marusenkoSphereKugel.Kugel;
import marusenkoSphereSolving.Solver;

public class Konsole {

	public Kugel k;
	protected Solver s;
	public Logger l;
	public Logger d;
	
	
	public Konsole(Kugel k, Solver s, Logger l, Logger d){
		this.k = k;
		this.s = s;
		this.l = l;
		this.d = d;
/*
		 Scanner in = new Scanner(System.in);
		 
		 while(true){
			 int i = in.nextInt();
			 if(i==1){
				k.FillKugelRandom();
				k = s.solve(k);
				System.out.println("S");
			 }else{
				 System.out.println(i);
			 }
		 }
*/
		k.FillKugelRandom();
		k = s.solve(k);
	}
}

