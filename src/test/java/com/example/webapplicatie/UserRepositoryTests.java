//package com.example.webapplicatie;
//
//import com.example.webapplicatie.models.User;
//import com.example.webapplicatie.repository.UserRepository;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
//public class UserRepositoryTests {
//
//
//    @Autowired
//    private UserRepository repo;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//
//    @Test
//    @Order(1)
//    public void testCreateUser() {
//        User user = new User();
//        user.setEmail("marjolijn_adolfs@hotmail.com");
//        user.setPassword("marjo1010");
//        user.setFirstName("Annalies");
//        user.setLastName("Adolfs");
//
//        User savedUser = repo.save(user);
//        User existUser = entityManager.find(User.class, savedUser.getId());
//        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
//    }
//}
////
//////
////        Arrange act serve meteen meegeven,
////        Mock ()
////        Praten direct tegen de database niet verstandig
////
////
////
////
////    @Test
////    @Order(2)
////    public void testFindByGetEmail()
////    {
////        User user = repo.findByEmail(createTestUser().getEmail());
////        assertThat(user).isNotNull();
////    }
//////
////    @Test
////    @Order(3)
////    public void TestDeleteUser()
////    {
////        User user;
////        repo.deleteByGetEmail(user.getEmail());
////        assertThat(repo.findByGetEmail(user.getEmail())).isNull();
////    }
//
