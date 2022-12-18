package command;

import exceptions.DBException;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    String execute(HttpServletRequest request);

}
