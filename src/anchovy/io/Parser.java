package anchovy.io;
import anchovy.GameEngine;
import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Components.*;
class Parser
{
	private GameEngine engine;
	public Parser(GameEngine Engine)
	{
		engine = Engine;
	}
	
	public String parseCommand(String componentName, String command)
	{
		String result = new String();
		Component component= engine.getPowerPlantComponent(componentName);
		InfoPacket i = new InfoPacket();
		
		if(component != null)
		{
		if(component.getName().contains("Valve"))
		{
			//i.namedValues.add(new Pair<String>(Pair.Label.cNme, component.getName()));
			if(command.equals("open"))
			{
				i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
			}
			else if(command.equals("close"))
					{
						i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, false));
					}
		}
					else if(component.getName().contains("Pump"))
					{
						
						i.namedValues.add(new Pair<String>(Pair.Label.cNme, component.getName()));
						if(command.equals("on"))
						{
							i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
						}
						else if(command.equals("off"))
						{
							i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, false));
						}
					}
					else if(component.getName().contains("Control rods"))
					{
							
						i.namedValues.add(new Pair<String>(Pair.Label.cNme, component.getName()));
						if(command.equals("lower"))
						{
							i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
						}
						else if(command.equals("raise"))
						{
							i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, false));
						}
					}
			else
			{
				return "Wrong command entered";
			}
			try{
				component.takeInfo(i);
			}
			catch(Exception e) { e.printStackTrace(); }
		} else result = "Component " + componentName + " does not exist";
		engine.updateInterfaceComponents(engine.getAllComponentInfo());
		return result;
	}
	public String parse(String text)
	{	
		if(text.length() != 0)
		{
			//String lowerCase = text.toLowerCase(); //Convert string to lower case to avoid case mismatches
			String lowerCase= text;
			
				String s= lowerCase.substring(0, lowerCase.lastIndexOf(' '));
				String i= lowerCase.substring(lowerCase.lastIndexOf(' ') + 1, lowerCase.length());
				
				return parseCommand(s,i);
				
			}
			
		 else return "";
	}
}

