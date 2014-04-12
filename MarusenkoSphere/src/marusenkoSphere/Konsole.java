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
	
	
	public Konsole(Kugel k, Solver s, Logger d, Logger l,String logInput){
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
		
		Logger out = new Logger(logInput,"AnzahlLog");
		
		for(long i = 0;i<9223372036854775807L;i++){
			k.FillKugelRandom();
			String sphere = k.getSphere();
			k = s.solve(k);
			System.out.println(i+". Kugel solved in "+k.steps+" steps");
			if(out.logAnz(k.steps)){
				l.log(sphere, "longest sphere --> "+k.steps);
			}
		}
		
	}
}

