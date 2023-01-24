package dao;

public class Constants {
    public static final String FIND_ALL_USERS = "SELECT * FROM mydb.users";

    public static final String FIND_USERS_BY_ID = "SELECT * FROM mydb.users WHERE id =?";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM mydb.users WHERE login =?";
    public static final String FIND_ALL_USERS_BY_ROLE = "SELECT * FROM mydb.users WHERE role =?";
    public static final String DELETE_USERS_BY_LOGIN = "DELETE FROM mydb.users WHERE login =?";
    public static final String INSERT_INTO_USERS = "INSERT INTO `mydb`.`users`(`login`, `password`, `role`) VALUES (?, ?, ?)";
    public static final String UPDATE_USER = "UPDATE `mydb`.`users` SET `login` =?, `password` = ?, `role` = ? WHERE `id` = ?";

    public static final String FIND_ALL_MOVIES = "SELECT * FROM mydb.movies";
    public static final String FIND_ALL_HALLS = "SELECT * FROM mydb.halls";

    public static final String FIND_MOVIE_BY_NAME = "SELECT * FROM mydb.movies WHERE name=?";
    public static final String DELETE_MOVIE_BY_NAME = "DELETE FROM `mydb`.`movies`WHERE name =?;";
    public static final String INSERT_INTO_MOVIES = "INSERT INTO `mydb`.`movies` (`id`,`name`) VALUES (default,?)";
    public static final String UPDATE_MOVIE = "UPDATE movies set name= ? where id=?";
    public static final String FIND_ALL_SESSIONS_SORTED_BY_NUMBER_OF_SEATS = "SELECT * FROM mydb.sessions left join movies on movie_id=movies.id left join halls on hall_id= halls.id order by number_available_seats;";
    public static final String FIND_ALL_SESSIONS_SORTED_BY_MOVIE_TITLE = "SELECT * FROM mydb.sessions left join movies on movie_id=movies.id left join halls on hall_id= halls.id order by name;";
    public static final String FIND_ALL_SESSIONS_SORTED_BY_DATE = "SELECT * FROM mydb.sessions left join movies on movie_id=movies.id left join halls on hall_id= halls.id order by date";
    public static final String FIND_ALL_SESSIONS_FILTER_BY_SORTED_BY = "SELECT * FROM mydb.sessions left join movies on movie_id=movies.id left join halls on hall_id= halls.id";
    public static final String COUNT_ALL_SESSIONS_FILTER_BY_SORTED_BY = "SELECT COUNT(*) FROM mydb.sessions left join movies on movie_id=movies.id left join halls on hall_id= halls.id";
    public static final String FIND_ALL_SESSIONS = "SELECT *  FROM mydb.sessions left join movies on movie_id=movies.id left join halls on hall_id= halls.id";
    public static final String DELETE_SESSION_BY_ID = "DELETE FROM sessions WHERE id =?";
    public static final String INSERT_INTO_HALLS = "INSERT INTO `mydb`.`halls`(`id`,`number_seats`,`number_available_seats`, `number_sold_seats`, `attendance`) VALUES(default,?,?,?,?);";
    public static final String FIND_MOVIE_BY_ID = "SELECT * FROM mydb.movies where id =?";
    public static final String FIND_HALL_BY_ID = "SELECT * FROM `mydb`.`halls` WHERE id=?";
    public static final String DELETE_HALL_BY_ID = "DELETE FROM halls where id=?";
    public static final String INSERT_INTO_SESSIONS = "INSERT INTO sessions (id, `date`, movie_id, hall_id, `time`) VALUES (default,?,?,?,?)";
    public static final String UPDATE_SESSIONS = "UPDATE `mydb`.`sessions` SET `date` = ? , `movie_id` = ? , `hall_id` = ?, `time` = ?, `status` = ? WHERE `id` =?";
    public static final String FIND_ALL_ORDERS = "SELECT * FROM mydb.orders;";
    public static final String FIND_ORDER_BY_ID = "SELECT * FROM orders join users on user_id=users.id join sessions on sessions_id=sessions.id join movies on movie_id=movies.id join halls on hall_id=halls.id WHERE orders.id =?";

    public static final String FIND_SESSION_BY_ID = "SELECT * FROM mydb.sessions left join movies on movie_id=movies.id left join halls on hall_id= halls.id WHERE sessions.id=?";
    public static final String DELETE_ORDER_BY_ID = "DELETE FROM orders where id=?";
    public static final String INSERT_INTO_ORDER = "INSERT INTO `mydb`.`orders` (`id`,`state`,`number_of_seats`,`price`,`user_id`,`sessions_id`)VALUES(default,?,?,?,?,?)";
    public static final String UPDATE_ORDERS = "UPDATE `mydb`.`orders` SET `state` = ?,`number_of_seats` = ?,`price` = ?,`user_id` = ?,`sessions_id` = ? WHERE `id` = ?";
    public static final String UPDATE_HALL = "UPDATE mydb.halls SET number_seats = ?, number_available_seats = ?, number_sold_seats=? , attendance=?  WHERE id = ?;";
    public static final String FIND_ALL_SESSIONS_SORTED_ORDER_BY = "SELECT * FROM mydb.sessions left join movies on movie_id=movies.id left join halls on hall_id= halls.id order by ";
    public static final String FIND_ALL_MOVIES_SORTED_BY_NAME = "SELECT * FROM mydb.movies ";
    public static final String FIND_ORDER_BY_SESSION_ID = "SELECT * FROM orders left join users on user_id=users.id join sessions on sessions_id= sessions.id left join movies on movie_id=movies.id join halls on hall_id= halls.id where sessions_id=?;";
    public static final String COUNT_ALL_MOVIES = "SELECT COUNT(*) FROM mydb.movies ";
    public static final String FIND_ALL_SESSIONS_BY_MOVIE_NAME = "SELECT * FROM mydb.sessions left join movies on movie_id=movies.id left join halls on hall_id= halls.id WHERE name=?;";
}
