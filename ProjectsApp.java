package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

/**
 * This is a class that takes input from user in the console
 * and performs CRUD operations to build project tables
 * 
 * @author Promineo
 */
public class ProjectsApp {
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	
	//@formatter:off
	private List<String> operations = List.of(
		"1) Add a project"
		);
	//@formatter:on		
/**
 * Entry point for app			
 * @param args
 */
	public static void main(String[] args ) {			
		new ProjectsApp().processUserSelections(); 
	}
/**
 * Method that prints requests for user input then performs operations
 * and repeats until user presses 'enter' to terminate.	
 */
	
	private void processUserSelections() {
		boolean done = false;
		
		while(!done) {
			try {
				int selection = getUserSelection();
			
			switch (selection) {
           case -1:
               done = exitMenu();
                break;
 
           case 1:
         	createProject();
             break;

           default:
               System.out.println("\n" + selection + " is not a valid selection. Try again.");
                 break;
			}
		}
			catch(Exception e) {
			System.out.println("\nError:  " + e + " Try again.");
			}
		}	
	}	

/**
 * Receive user input for each row 
 * of each column prompted in the project table	
 * then call the project service to create the row
 */
	
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		int difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		Object Project;
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project " + dbProject);
		
	}

/**
 * Takes user input from console and converts it to BigDecimal	
 * @param prompt
 * @return
 */
	
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number");
		}
	}

/**
 * Called when user prompts to exit the application and
 * prints a message that app is exiting. 
 * @return to terminate app	
 */
	
	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}

/**
 * Method that prints available selection to console
 * Receives user selection from console
 * converts to an integer
 * @return Menu selection as an int or -1 if nothing is selected
 */
	private int getUserSelection() {
		printOperations();
		
			Integer input = getIntInput("Enter a menu selection");
		
				return Objects.isNull(input) ? -1 : input;
		}
	
/**
 * Method that prints available selection to console,
 * receives user selection from console,
 * and converts input to an integer
 * @param prompt to print
 * @return If user enters nothing, [@code null} is returned,
 *  otherwise input is converted to an integer.
 *  @throws DbException Thrown if input is not a valid integer.
 */
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
	
		if(Objects.isNull(input)) {
			return null;
	}
	
	try {
		return Integer.valueOf(input);
	}
	catch(NumberFormatException e) {
		throw new DbException(input + " is not a valid number");
	}
}

/**
 * Method that prints available selections to console,
 * receives user selection from console.
 * If user enters nothing, {@code null} is returned
 * Otherwise, trimmed input is returned	
 * @param prompt Prompt is to print
 * @return User's input or {@code null}
 */
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input.trim();
	}

	private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit.");
		operations.forEach(line -> System.out.println("   " + line));
	}
	
}

