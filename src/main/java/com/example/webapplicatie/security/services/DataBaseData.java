package com.example.webapplicatie.security.services;

//Database connectie
//Zorgt dat er users in staan om te testen zodat er makkelijker getest van worden
import com.example.webapplicatie.models.Car;
import com.example.webapplicatie.models.ERole;
import com.example.webapplicatie.models.Role;
import com.example.webapplicatie.models.User;
import com.example.webapplicatie.repository.CarRepository;
import com.example.webapplicatie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataBaseData implements CommandLineRunner {

    @Autowired
    UserRepository userRepo;

    @Autowired
    CarRepository carRepo;


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {

        admin();
        moderator();
        user();
    }

    //Zorgt dat er users in staan om te testen zodat er makkelijker getest van worden
    private void admin()
    {
        Set<Role> roles = new HashSet<>();
        Role tempRole = new Role();
        tempRole.setId(3);
        tempRole.setName(ERole.ROLE_ADMIN);
        roles.add(tempRole);
        User user = new User();
        user.setUsername("Annalies");
        user.setEmail("Annalies_adolfs@hotmail.com");
        user.setPassword(passwordEncoder().encode("Wachtwoord!"));
        user.setRoles(roles);

        userRepo.save(user);
    }

    private void user()
    {
        Set<Role> roles = new HashSet<>();
        Role tempRole = new Role();
        tempRole.setId(1);
        tempRole.setName(ERole.ROLE_USER);
        roles.add(tempRole);
        User user = new User();
        user.setUsername("Marjolijn");
        user.setEmail("marjolijn_adolfs@hotmail.com");
        user.setPassword(passwordEncoder().encode("Wachtwoord!"));
        user.setRoles(roles);

        userRepo.save(user);
        //Atuo's worden hier gesaved
        Car car = new Car();
        car.setUser(user);
        car.setDescription("Description");
        car.setTitle("Title");
        car.setId(1);


        Car car2 = new Car();
        car2.setUser(user);
        car2.setDescription("Description2");
        car2.setTitle("Title2");
        car2.setId(2);

        carRepo.save(car);
        carRepo.save(car2);
    }

    //Rollen toekennen aan users
    private void moderator()
    {
        Set<Role> roles = new HashSet<>();
        Role tempRole = new Role();
        tempRole.setId(2);
        tempRole.setName(ERole.ROLE_ADMIN);
        roles.add(tempRole);
        User user = new User();
        user.setUsername("Noortje");
        user.setEmail("Noortje_adolfs@hotmail.com");
        user.setPassword(passwordEncoder().encode("Wachtwoord!"));
        user.setRoles(roles);

        userRepo.save(user);
    }

}
