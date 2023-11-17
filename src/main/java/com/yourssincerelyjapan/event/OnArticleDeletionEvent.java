package com.yourssincerelyjapan.event;

import com.yourssincerelyjapan.model.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@Getter
@Setter
public class OnArticleDeletionEvent extends ApplicationEvent {

    private final List<Comment> comments;

    public OnArticleDeletionEvent(final List<Comment> comments) {
        super(comments);
        this.comments = comments;
    }
}
