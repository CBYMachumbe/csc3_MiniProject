package logic;

public class House extends Outlet implements Comparable<Outlet>
{
	double consuption;
	
	public House(String name, boolean isOn, double con) 
	{
		super(name, isOn);
		consuption = con;
		setType("House");
	}

	public double getConsuption() {
		return consuption;
	}

	public void setConsuption(double consuption) {
		this.consuption = consuption;
	}

	@Override
	public String toString()
	{
		String display = "House: " + getName() + ", Consuption:" + consuption + " kWhr &";
		
		if(isOn())
			display += " Has Electricity";
		else
			display += " Doesn't Have Electrity";
		
		return display;
	}

	
	
}
