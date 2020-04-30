package logic;

public class Station implements Comparable<Station>{
	
	private String name;
	private boolean isStation;
	private boolean isOn;

	public Station(String name, boolean isOn, boolean isStation)
	{
		this.name = name;
		this.isStation = isStation;
		this.isOn = isOn;
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
	public String toString() 
	{
		String display = name;
		
		if(isStation)
		{
			if(isOn)
			{
				display += ", station is working";
				return display;
			}
			else
			{
				display += ", station is not working";
				return display;
			}
			
		}
		else
		{
			if(isOn)
			{
				display += ", home is getting electity";
				return display;
			}
			else
			{
				display += ", home is not getting electricty";
				return display;
			}
		}
	}

	@Override
	public int compareTo(Station otherStation) 
	{
		if(this.name.equals(otherStation.name))
			return 0;
		else
			return -1;
	}
		
}
