package logic;

public class Outlet implements Comparable<Outlet>
{

	private String name;
	private boolean isOn;
	private String Type;

	public Outlet(String name, boolean isOn)
	{
		this.name = name;
		this.isOn = isOn;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOn() {
		return isOn;
	}
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	@Override
	public int compareTo(Outlet o) 
	{
		if(this.name.equals(o.name))
			return 0;
		else
			return -1;
	}
}
