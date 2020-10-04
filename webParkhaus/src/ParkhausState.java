import java.util.ArrayList;
import java.util.List;

public class ParkhausState implements IObservable {
	
	private List<IObserver> observers;
	private boolean alarm;
	
	public ParkhausState() {
		this.observers = new ArrayList<IObserver>();
	}
	
	@Override
	public void add(IObserver o) {
		this.observers.add(o);
		
	}

	@Override
	public void remove(IObserver o) {
		this.observers.remove(o);
		
	}

	@Override
	public void notify(boolean b) {
		this.alarm = b;
		observers.forEach((IObserver o) -> o.update(alarm));
	}

	public boolean getAlarm() {
		return this.alarm;
	}
	
}
