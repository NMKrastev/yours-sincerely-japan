package com.yourssincerelyjapan.events;

import com.yourssincerelyjapan.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@SuppressWarnings("serial")
@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final User user;

    public OnRegistrationCompleteEvent(final User user) {
        super(user);
        this.user = user;
    }
}
