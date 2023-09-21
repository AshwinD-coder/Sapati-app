//package global.citytech;
//
//import io.micronaut.http.HttpRequest;
//import io.micronaut.http.client.HttpClient;
//import io.micronaut.http.client.annotation.Client;
//import io.micronaut.runtime.EmbeddedApplication;
//import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions.*;
//
//import jakarta.inject.Inject;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@MicronautTest
//class MyappTest {
//
//@Inject
//@Client("/")
//HttpClient client;
//
//@Test
//public void testGetResources(){
//    HttpRequest<String> request = HttpRequest.GET("/ping");
//    String body = client.toBlocking().retrieve(request);
//    assertNotNull(body);
//    assertEquals("System running...",body);
//}
//
//}
