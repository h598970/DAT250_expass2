package no.hvl.dat250.project.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Poll {
    private String id;
    private String questions;
    private Instant publishedAt;
    private Instant validUntil;
    private List<VoteOption> options;


//    @JsonBackReference(value = "user-poll")
    @JsonIdentityReference(alwaysAsId = true)
    private User creator;



//    @JsonManagedReference(value = "poll-vote")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Vote> votes = new ArrayList<>();

    public Poll() {
        this.id = UUID.randomUUID().toString();
        this.options = new ArrayList<>();
        this.publishedAt = Instant.now();
        this.creator = new User();
    }

    public Poll(String questions, Instant publishedAt, Instant validUntil, List<VoteOption> options, User creator) {
        this.id = UUID.randomUUID().toString();
        this.questions = questions;
        this.publishedAt = publishedAt;
        this.validUntil = validUntil;
        this.options = options;
        this.creator = creator;
    }

    public Poll(String id) {
        this.id = id;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public String getId() {
        return id;
    }


    public User getCreator() {
        return creator;
    }

//    @JsonProperty(value = "creator")
//    public void setCreator(String creatorId) {
//        this.creator = new User(creatorId);
//    }


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