package global.citytech;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller("/ping")
public class PingResource {
    @Get("/")
    public String  ping(){
       return "System running......";
    }

}

