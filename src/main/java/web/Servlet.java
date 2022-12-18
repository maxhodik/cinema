package web;

import command.*;
import dao.*;
import dao.impl.SqlDaoFactory;
import service.HallService;
import service.impl.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SessionDao sessionDao = SqlDaoFactory.createSessionDao();
        HallDao hallDao = SqlDaoFactory.createHallDao();
        MovieDao movieDao = SqlDaoFactory.createMovieDao();
        OrderDao orderDao = SqlDaoFactory.createOrderDao();
        ScheduleServiceImpl scheduleService = new ScheduleServiceImpl(sessionDao, hallDao, movieDao,orderDao);
        UserDao userDao = SqlDaoFactory.createUserDao();
        HallService hallService= new HallServiceImpl(hallDao);
        UserServiceImpl userService = new UserServiceImpl(userDao);
        MovieServiceImpl movieService = new MovieServiceImpl(movieDao);
        ScheduleCommand scheduleCommand = new ScheduleCommand(scheduleService);
        commands.put("register", new RegisterCommand(userService));
        commands.put("login", new LoginCommand(userService));
        commands.put("schedule", scheduleCommand);
        commands.put("order", new OrderCommand(new OrderServiceImpl(orderDao,
                hallDao, sessionDao, userDao), scheduleService, userService));
        commands.put("admin/movie", new MovieCommand(movieService));

        commands.put("admin/movie/delete", new MovieDeleteCommand(movieService));
        commands.put("admin/movie/update-movie", new MovieUpdateCommand(movieService));
        commands.put("admin/movie/add-movie", new MovieAddCommand(movieService));
        commands.put("schedule/delete",new ScheduleDeleteCommand(scheduleService));
        commands.put("admin/add-session",new ScheduleAddCommand (scheduleService, movieService, hallService ));
        commands.put("admin/update-session", new ScheduleUpdateCommand(scheduleService, movieService, hallService ));
        commands.put("admin/analise", new AnaliseCommand(scheduleService, movieService, hallService ));



    }

    private Command findCommandByPath(String path) {
        String commandKey = path.replaceAll(".*/cinema/", "");
        return commands.getOrDefault(commandKey, new NotFoundCommand());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processResponseRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processResponseRequest(req, resp);
    }

    private void processResponseRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        Command command = findCommandByPath(requestURI);
        String page = command.execute(req);

        // "redirect:/users" --> /cinema/users
        if (page.contains("redirect:")) {
            resp.sendRedirect(page.replace("redirect:", "/cinema/"));
        } else {
            // "WEB-INF/register.jsp"
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }


}



