package anchovy.io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * Class responsible for the command line like in the UI. Handles enter keypress event and then accordingly moves the data around.
 * @author Tadas
 *
 */
public class Console/*<T>*/ implements ActionListener
{
	
	Console(JTextField textField, JTextArea output/*, T parser*/)
	{
		txtField = textField;
		txtArea = output;
		//T = prsr;
	}
	/**
	 * Gets automatically called when user presses enter with the text field in focus. Essentially just moves the text from the input area to output area.
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		String text = txtField.getText();
		
		writeToConsole('>' + text);
		//prsr.parse(text);
		txtField.setText("");
		
	}
	/**
	 * Appends a string at the of the command line. Always adds a newline character to the start of the string.
	 * @param text The string that you want to be appended to the command line output
	 */
	public void writeToConsole(String text)
	{
		txtArea.append('\n' + text);
	}
	private JTextField txtField;
	private JTextArea txtArea;
	/*private T prsr;*/
}
