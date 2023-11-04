package ma.enset;

import ma.enset.entities.Role;
import ma.enset.entities.User;
import ma.enset.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class UseCaseJpaHibernateSpringDataManyToManyCaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseCaseJpaHibernateSpringDataManyToManyCaseApplication.class, args);
    }
    @Bean
    CommandLineRunner start(UserService userService){
        return  args -> {
            User user1=new User();
            user1.setUserName("meriem");
            user1.setPassword("123456");
            userService.addNewUser(user1);

            User user2=new User();
            user2.setUserName("admin");
            user2.setPassword("123456");
            userService.addNewUser(user2);

            Stream.of("STUDENT","USER","ADMIN").forEach(role->{
                Role role1=new Role();
                role1.setRoleName(role);

                userService.addNewRole(role1);
            });

            userService.addRoleToUser("meriem","STUDENT");
            userService.addRoleToUser("meriem","USER");
            userService.addRoleToUser("admin","USER");
            userService.addRoleToUser("admin","ADMIN");
           try{
               User user=userService.autehticate("meriem","123456");
               System.out.println(user.getUserId());
               System.out.println(user.getUserName());
               System.out.println("Roles==>");
               user.getRoles().forEach(r->{
                   System.out.println("Roles=>"+r);
               });

           }catch (Exception e){
               e.printStackTrace();
           }


        };
    }

}
