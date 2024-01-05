package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User(new Car("BMW", 5), "User5", "Lastname5", "user4@mail.ru"));
        userService.add(new User(new Car("Mercedes", 600), "User6", "Lastname6", "user6@mail.ru"));
        userService.add(new User(new Car("Audi", 4), "User7", "Lastname7", "user7@mail.ru"));
        userService.add(new User(new Car("MiniCooper", 3), "User8", "Lastname8", "user8@mail.ru"));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getUserCar());
            System.out.println();
        }

        System.out.println(userService.getUserCar("Mercedes", 600));

        context.close();
    }
}
