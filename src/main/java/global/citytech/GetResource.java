package global.citytech;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

@Controller("/ping")
public class GetResource {
    @Get("/")
    @Options
    @Produces(MediaType.TEXT_PLAIN)
    public String  ping(){
       return "System running...";
    }

}

