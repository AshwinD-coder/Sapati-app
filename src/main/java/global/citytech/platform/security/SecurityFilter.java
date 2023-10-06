package global.citytech.platform.security;


import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;


@Filter("/**")
public class SecurityFilter implements HttpServerFilter {
    private final String TOKEN = "X-XSRF-TOKEN";
    @Inject
    private final SecurityUtils securityUtils;

    @Inject
    private UserRepository userRepository;

    private final Logger logger = Logger.getLogger(SecurityFilter.class.getName());

    public SecurityFilter(SecurityUtils securityUtils, UserRepository userRepository) {
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        try {
            if (request.getMethod() == HttpMethod.OPTIONS)
                return Flowable.just(HttpResponse.ok());

            var token = request.getHeaders().get(TOKEN);

            if (request.getPath().contains("/user/create")) {
                return chain.proceed(request);
            }
            if (request.getPath().contains("/user/login")) {
                return chain.proceed(request);
            }
            if (request.getPath().contains("/user/verify")) {
                return chain.proceed(request);
            }
            if (Objects.isNull(token) || token.isEmpty()) {
                throw new IllegalArgumentException("Security Token is missing");
            }
            RequestContext requestContext = securityUtils.parseTokenAndGetContext(token);
            validateUser(requestContext);
            System.out.println("REQUESTED BY :: " + requestContext.getUsername());
            return Flowable.just(true)
                    .doOnRequest(t -> {
                        ContextHolder.set(requestContext);
                    })
                    .switchMap(aBoolean -> chain.proceed(request))
                    .onErrorReturn(throwable -> {
                        throwable.printStackTrace();
                        logger.info("::: ERROR IN CHAIN PROCESS :::");
                        throw new IllegalArgumentException("Security interceptor Exception");
                    });
        } catch (Exception e) {

            return Flowable.just(HttpResponse.badRequest(new CustomResponseHandler("0",e.getMessage(),null)));
        }

    }

    private void validateUser(RequestContext requestContext) {
        Optional<User> user = this.userRepository.findByUsername(requestContext.getUsername());
        if(user.isEmpty()){
            throw new IllegalArgumentException("User not found");
        }
        if(user.get().getActiveStatus().equals(false)){
            throw new IllegalArgumentException("Your account is deactivated!");
        }
        if(user.get().getBlacklistStatus().equals(true)){
            throw new IllegalArgumentException("Your account is blacklisted!");
        }
    }
}