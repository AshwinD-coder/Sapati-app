package global.citytech.user.service.delete;

import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adapter.dto.UserDeleteDto;
import jakarta.inject.Inject;

import java.util.Optional;

public class UserDeleteService {
    @Inject
    private UserRepository userRepository;


    public UserDeleteService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser(UserDeleteDto userDeleteDto) {
        Optional<User> user = this.userRepository.findById(userDeleteDto.getId());
        if (user.isPresent()) {
            user.get().setActiveStatus(false);
            this.userRepository.update(user.get());
        } else throw new IllegalArgumentException("No user found!");

    }
}
