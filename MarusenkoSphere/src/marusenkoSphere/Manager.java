package marusenkoSphere;

import marusenkoSphereControle.ControlPanel;
import marusenkoSphereControle.KugelSteuern;
import marusenkoSphereKugel.Kugel;
import marusenkoSphereRender.KugelRendern;
import marusenkoSphereSolving.Solver;

public class Manager {

	public Kugel k;
	public KugelRendern kr;
	private Solver s;
	public Logger d;
	public Logger l;
	public ControlPanel cp;
	
	public Manager(Kugel k, KugelRendern kr, Solver s, Logger d, Logger l){
		this.k = k;
		this.kr = kr;
		this.s = s;
		this.d = d;
		this.l = l;
		cp = new ControlPanel(this);
		
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
		update(100);
	}
	public void update200(){
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
		//d.log("Start Solving Kugel");
		k = s.solve(k);
	}
	public void fillSphere(){
		//d.log("Fill Kugel");
		k.FillKugelRandom();
	}
	public void fillSphere(String s){
	//	d.log("Fill Kugel from string");
		k.FillKugelFromString(s);
	}
	
}
