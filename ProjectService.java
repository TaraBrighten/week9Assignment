package projects.service;

import projects.dao.ProjectDao;
import projects.entity.Project;

/**
 * @author Promineo
 */
public class ProjectService {
	private ProjectDao projectDao = new ProjectDao();

/**
 * Method that calls DAO class to insert a project row	
 * @param project The {@link Project} object
 * @return The Project object with newly generated primary key value
 */
	
	public Project addProject(Project project) {
		return projectDao.insertProject(project);

	}
}
