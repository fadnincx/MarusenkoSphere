package marusenkoSphere;

import marusenkoSphereKugel.Kugel;
import marusenkoSphereRender.KugelRendern;
import marusenkoSphereRender.KugelSteuern;
import marusenkoSphereSolving.Solver;

public class Manager {

	public Kugel k;
	public KugelRendern kr;
	private Solver s;
	public Logger d;
	public Logger l;
	
	public Manager(Kugel k, KugelRendern kr, Solver s, Logger d, Logger l){
		this.k = k;
		this.kr = kr;
		this.s = s;
		this.d = d;
		this.l = l;
		
		while(true){
			KugelSteuern.Input(this);
		}
	}
	public void update(long time){
		kr.updateKugel(k);
		long startTime = System.nanoTime();    
		long estimatedTime = System.nanoTime() - startTime;
		time*=1000000;
		while(estimatedTime<time){
			KugelSteuern.Input(this);
			estimatedTime = System.nanoTime() - startTime;
		}
	}
	public void update(){
		update(200);
	}
	public void updateKugel(Kugel k){
		this.k = k;
		renderKugel();
	}
	public void renderKugel(){
		kr.updateKugel(k);
	}
	public void startSolve(){
		k = s.solve(k);
	}
	
}
