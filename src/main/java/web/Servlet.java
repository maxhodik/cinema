package web;

import command.*;
import dao.*;
import dao.impl.SqlDaoFactory;
import service.HallService;
import service.impl.*;
import web.form.validation.*;

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
        OrderServiceImpl orderService = new OrderServiceImpl(orderDao);
        UserDao userDao = SqlDaoFactory.createUserDao();
        HallService hallService = new HallServiceImpl(hallDao);
        UserServiceImpl userService = new UserServiceImpl(userDao, new PasswordEncoderService());
        MovieServiceImpl movieService = new MovieServiceImpl(movieDao, sessionDao);
        ScheduleServiceImpl scheduleService = new ScheduleServiceImpl(sessionDao, hallService, movieService, orderService);

        Pagination paginate = new Pagination();
        ScheduleCommand scheduleCommand = new ScheduleCommand(scheduleService, paginate);
        MovieFormValidator movieValidator = new MovieFormValidator();
        SessionFormValidator sessionValidator = new SessionFormValidator();
        IdValidator idValidator= new IdValidator();

        commands.put("register", new RegisterCommand(userService, new UserFormValidator()));
        commands.put("login", new LoginCommand(userService));
        commands.put("schedule", scheduleCommand);
        commands.put("order", new OrderCommand(new OrderServiceImpl(orderDao,
                hallService, scheduleService, userService), scheduleService, userService, new OrderValidator(),idValidator));
        commands.put("admin/movie", new MovieCommand(movieService, paginate));
        commands.put("admin/movie/delete", new MovieDeleteCommand(movieService, idValidator));
        commands.put("admin/movie/update-movie", new MovieUpdateCommand(movieService, movieValidator,idValidator));
        commands.put("admin/movie/add-movie", new MovieAddCommand(movieService, movieValidator));
        commands.put("schedule/delete", new ScheduleDeleteCommand(scheduleService, idValidator));
        commands.put("admin/add-session", new ScheduleAddCommand(scheduleService, movieService, hallService, sessionValidator));
        commands.put("admin/update-session", new ScheduleUpdateCommand(scheduleService, movieService, hallService,
                sessionValidator, idValidator));
        commands.put("admin/analise", new AnaliseCommand(scheduleService, movieService, hallService, new AnaliseFormValidator(), paginate));
        commands.put("logout", new LogOutCommand());
        commands.put("ticket", new TicketCommand(orderService, scheduleService, idValidator));

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



