package anchovy.io;
import anchovy.GameEngine;
import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Components.*;
class parser
{
	private GameEngine engine;
	public parser(GameEngine Engine)
	{
		engine = Engine;
	}
public String parsercommand(String componentType,String componentName, String command)
{
	Component component= engine.getPowerPlantComponent(componentName);
	String result = "";
	if(component.getName() =="valve")
	{
		InfoPacket i = new InfoPacket();
		i.namedValues.add(new Pair<String>(Pair.Label.cNme,  component.getName()));
		if(command=="open")
		{
			i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
		}
		else
		{
							i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, false));
		}
	}
	else if(component.getName() =="pump")
			{
				InfoPacket i = new InfoPacket();
				i.namedValues.add(new Pair<String>(Pair.Label.cNme, component.getName()));
				if(command=="on")
				{
					i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
				}
				else
				{
					i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, false));
				}
			}
			else if(component.getName() =="rods")
			{
				InfoPacket i = new InfoPacket();
				i.namedValues.add(new Pair<String>(Pair.Label.cNme, component.getName()));
				if(command=="lower")
				{
					i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
				}
				else
				{
					i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, false));
				}
		}
		else
		{
			result = "Invalid input";
		}
	return "Cows";	
}
}

