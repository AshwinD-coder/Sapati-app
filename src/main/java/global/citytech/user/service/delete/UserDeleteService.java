package global.citytech.user.service.delete;

import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;

import java.util.Optional;

public class UserDeleteService {
    @Inject
    private UserRepository userRepository;

    public UserDeleteService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public HttpResponse<String> deleteUser(UserDeleteRequest userDeleteRequest){
        Optional<User> user = this.userRepository.findById(userDeleteRequest.getId());
        if(user.isPresent()){
        this.userRepository.delete(user.get());
        return HttpResponse.status(0,"SUCCESS").body("Deleted User!");
        }
        else return HttpResponse.notFound("User not found!");

    }
}
