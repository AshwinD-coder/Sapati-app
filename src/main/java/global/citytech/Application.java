package global.citytech;

import io.micronaut.runtime.Micronaut;

import java.io.IOException;

public class Application {

    public static void main(String[] args) {

        Micronaut.run(Application.class, args);
    }
}