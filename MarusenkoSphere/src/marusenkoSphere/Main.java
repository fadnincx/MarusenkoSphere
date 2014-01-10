package marusenkoSphere;

public class Main {

	public static void main(String[] args){
		Kugel k = new Kugel();
		GUI GUI = new GUI(k);
		//k.ChangeColor(3,3);
		GUI.drawKugleToFrame(k);
		k.FillKugleRandom(8);
		GUI.drawKugleToFrame(k);
		
		/*while(true){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Next Time");
		k.DrehenVertikal(1);
		GUI.drawKugleToFrame(k);
		}*/
		
	}
}
