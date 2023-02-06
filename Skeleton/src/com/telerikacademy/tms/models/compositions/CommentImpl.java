package com.telerikacademy.tms.models.compositions;

import com.telerikacademy.tms.models.compositions.contracts.Comment;

import static com.telerikacademy.tms.utils.ValidationHelpers.validateInRange;
import static java.lang.String.format;

public class CommentImpl implements Comment {
    private static final int COMMENT_MIN_LEN = 3;
    private static final int COMMENT_MAX_LEN = 200;
    private static final String COMMENT_LEN_ERR = format(
            "Comment must be between %d and %d characters long!",
            COMMENT_MIN_LEN,
            COMMENT_MAX_LEN);

    private String content;
    private String author;

    public CommentImpl(String content, String author) {
        setContent(content);
        setAuthor(author);
    }

    @Override
    public String getContent() {
        return content;
    }

    private void setContent(String content) {
        validateInRange(content.length(), COMMENT_MIN_LEN, COMMENT_MAX_LEN, COMMENT_LEN_ERR);
        this.content = content;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    private void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return format("\"%s\" - %s ", this.getContent(), this.getAuthor());
    }
}
