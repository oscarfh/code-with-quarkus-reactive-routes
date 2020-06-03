package org.reactive.acme;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.reactivex.WriteStreamSubscriber;
import io.vertx.reactivex.core.buffer.Buffer;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import org.common.MyCustomException;
import org.common.UserService;

@ApplicationScoped
public class ExampleReactiveResource {

    private UserService service;

    @Inject
    public ExampleReactiveResource(UserService service) {
        this.service = service;
    }

    @Route(path = "/reactive-hello", methods = HttpMethod.GET)
    void greetings(RoutingExchange ex) {
        String name = ex.getParam("name").get();

        ObjectMapper objectMapper = new ObjectMapper();

        service.rxCreateUser(name)
                .onItem().apply(user -> {
            try {
                return objectMapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        })
                .onItem().invoke(userAsString -> {
            ex.response().putHeader("Content-type", MediaType.APPLICATION_JSON);
            ex.ok(userAsString);
        })
                .subscribe().with(s -> ex.response().close(),
                throwable -> {
                    if (throwable instanceof MyCustomException) {
                        ex.response().setStatusCode(400).setStatusMessage("BAD REQUEST").end();
                    } else {
                        ex.response().setStatusCode(500).setStatusMessage("INTERNAL SERVER ERROR").end();
                    }
                });
    }
}