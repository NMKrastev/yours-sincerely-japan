package com.yourssincerelyjapan.event;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class OnArticleChangeEvent extends ApplicationEvent {

    public OnArticleChangeEvent(Object source) {

        super(source);
    }
}
