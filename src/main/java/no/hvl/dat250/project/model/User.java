package no.hvl.dat250.project.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User  {
    private String id;
    private String username;
    private String email;
    private String password_hash;
//    @JsonManagedReference(value = "user-poll")
    private List<Poll> pollsCreated = new ArrayList<>();

//    @JsonManagedReference(value = "user-vote")
    private List<Vote> votes = new ArrayList<>() ;


    public User(String username, String email, String password_hash, List<Poll> pollsCreated, List<Vote> votes) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
        this.pollsCreated = pollsCreated;
        this.votes = votes;
    }

    public User(String id) {
        this.id = id;
    }

    public User(String username, String email, String password_hash) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
    }

    public User() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
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

    public List<Poll> getPollsCreated() {
        return pollsCreated;
    }

    public void setPollsCreated(List<Poll> pollsCreated) {
        this.pollsCreated = pollsCreated;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
    public void addPoll(Poll poll) {
        this.pollsCreated.add(poll);
    }
}
