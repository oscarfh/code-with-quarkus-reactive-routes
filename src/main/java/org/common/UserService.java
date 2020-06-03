package org.common;

import io.smallrye.mutiny.Uni;
import javax.inject.Singleton;

@Singleton
public class UserService {

    // This is here because infrastructure (routing and so on) should be independent
    public User createUser(String name) {
        if (name.equals("fail")) {
            throw new MyCustomException();
        }
        return new User(name);
    }

    public Uni<User> rxCreateUser(String name) {
        return Uni.createFrom().item(() -> createUser(name));
    }
}
