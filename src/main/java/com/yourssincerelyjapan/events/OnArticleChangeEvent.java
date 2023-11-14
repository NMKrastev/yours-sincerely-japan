package com.yourssincerelyjapan.events;

import org.springframework.context.ApplicationEvent;

public class OnArticleChangeEvent extends ApplicationEvent {
    public OnArticleChangeEvent(Object source) {

        super(source);
    }
}
