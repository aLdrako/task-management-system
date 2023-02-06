package com.telerikacademy.tms.models.tasks.contracts;

import com.telerikacademy.tms.models.compositions.contracts.Comment;

import java.util.List;

public interface Commentable {

    List<Comment> getComments();

    void addComment(Comment comment);
}
