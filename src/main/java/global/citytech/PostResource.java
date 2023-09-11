package global.citytech;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/addUser")
public class PostResource {
    @Post("/")
    public  String addUser(String name,Boolean isAdmin){
        if(isAdmin) return "Hello Mr."+ name+ ", System is running on port 8080 . This is a post request method.";
        else
            return "Hello "+name+", Welcome to frontend";
    }
}
