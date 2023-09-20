package global.citytech.user.service.delete;

import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adapter.dto.UserDeleteDto;
import jakarta.inject.Inject;

import java.util.Optional;

public class UserDeleteService {
    @Inject
    private UserRepository userRepository;

    @Inject
    private CashRepository cashRepository;

    public UserDeleteService(UserRepository userRepository, CashRepository cashRepository) {
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
    }

    public void deleteUser(UserDeleteDto userDeleteDto) {
        Optional<User> user = this.userRepository.findById(userDeleteDto.getId());
        if (user.isPresent()) {
            Optional<Cash> cash = this.cashRepository.findByUsername(user.get().getUsername());
            this.userRepository.delete(user.get());
            this.cashRepository.delete(cash.get());
        } else throw new IllegalArgumentException("No user found!");

    }
}
