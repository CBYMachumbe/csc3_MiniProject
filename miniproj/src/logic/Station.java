package logic;

public class Station extends Outlet implements Comparable<Outlet>
{
	double supply;
	
	public Station(String name, boolean isOn, double supply)
	{
		super(name, isOn);
		this.supply = supply;
		setType("Station");
	}

	public double getSupply() {
		return supply;
	}

	public void setSupply(double supply) {
		this.supply = supply;
	}

	@Override
	public String toString() 
	{
		String display = "Station: " + getName() + ", Supply Cap: " + supply + " kW, is";
	
		if(isOn())
			display += " Operating";
		else
			display += " Not Operating ";
	
		return display;
	}	
}
