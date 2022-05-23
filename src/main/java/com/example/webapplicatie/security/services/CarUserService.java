//import com.example.webapplicatie.repository.CarRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Service
//public class CarService {
//    @Autowired
//    private CarRepository carRepository;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Override
//    public List<User> findAllUsers() {
//        return (List<User>) userRepository.findAll();
//    }
//
//    @Override
//    public Optional<User> findUserById(String userId){
//        return userRepository.findById(userId);
//    }
//
//    @Override
//    public User addUser(User user){
//        User userExisted = userRepository.findById(user.getUserId()).get();
//        Account accountExisted = accountRepository.findById(user.getAccount().getAccountId()).get();
//
//        if(userExisted != null || accountExisted == null){
//            return null;
//        } else {
//            user.setAccount(accountExisted);
//            return userRepository.save(user);
//        }
//    }
//
//    @Override
//    public User updateUser(User userDetails, User user){
//        Account accountExisted = accountRepository.findById(userDetails.getAccount().getAccountId()).get();
//
//        if( accountExisted == null){
//            return null;
//        } else {
//            user.setEmailAddress(userDetails.getEmailAddress());
//            user.setUserName(userDetails.getUserName());
//            user.setAccount(userDetails.getAccount());
//            user.setPassword(userDetails.getPassword());
//            user.setAccount(accountExisted);
//
//            return userRepository.save(user);
//        }
//    }
//
//    @Override
//    public void deleteUser (User user){
//        userRepository.delete(user);
//    }
//}