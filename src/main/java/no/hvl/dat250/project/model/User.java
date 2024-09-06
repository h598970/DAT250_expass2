package no.hvl.dat250.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jdk.jfr.Enabled;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;


public class User  {

    private String username;
    private String email;
    private String password_hash;
    @JsonBackReference
    private List<Poll> polls;
    private List<Vote> votes ;


    public User(String username, String email, String password_hash, List<Poll> polls, List<Vote> votes) {
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
        this.polls = polls != null ? new ArrayList<>(polls) : new ArrayList<>();
        this.votes = votes != null ? new ArrayList<>(votes) : new ArrayList<>();
    }

    public User(String username, String email, String password_hash) {
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
        this.polls = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    public User() {
        this.polls = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
    public void addPoll(Poll poll) {
        this.polls.add(poll);
    }
}
