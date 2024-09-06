package no.hvl.dat250.project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class Poll {
    private String questions;
    private Instant publishedAt;
    private Instant validUntil;
    private List<VoteOption> options;
    @JsonManagedReference
    private User creator;

    public Poll() {
        this.options = new ArrayList<>();
        this.publishedAt = Instant.now();
        this.creator = new User();
    }

    public Poll(String questions, Instant publishedAt, Instant validUntil, List<VoteOption> options, User creator) {
        this.questions = questions;
        this.publishedAt = publishedAt;
        this.validUntil = validUntil;
        this.options = options != null ? options : new ArrayList<>();
        this.creator = creator != null ? creator : new User();
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public List<VoteOption> getOptions() {
        return options;
    }

    public void setOptions(List<VoteOption> options) {
        this.options = options;
    }
}
