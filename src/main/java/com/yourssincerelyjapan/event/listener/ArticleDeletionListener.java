package com.yourssincerelyjapan.event.listener;

import com.yourssincerelyjapan.event.OnArticleDeletionEvent;
import com.yourssincerelyjapan.service.CommentService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ArticleDeletionListener {

    private final CommentService commentService;

    public ArticleDeletionListener(CommentService commentService) {

        this.commentService = commentService;
    }

    @EventListener
    public void onApplicationEvent(OnArticleDeletionEvent event) {

        this.commentService.deleteCommentsFromDeletedArticle(event.getComments());
    }
}
