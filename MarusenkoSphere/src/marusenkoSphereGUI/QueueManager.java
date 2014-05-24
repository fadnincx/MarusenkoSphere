package marusenkoSphereGUI;

public class QueueManager {

	public static void Queue(Manager m){
		Manager.doQueue = false;
		if(Manager.doQuetoEnd){
			m.oneStep();
		}else
		if(m.toDoQueue.size()>0){
			String s = m.toDoQueue.poll();
			switch(s){
			case "x":
				m.oneStep();
				break;
			case "y":
				m.backStep();
				break;
			}
		}
		
	}
}
