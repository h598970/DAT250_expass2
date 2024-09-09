package no.hvl.dat250.project.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.hvl.dat250.project.model.Poll;
import no.hvl.dat250.project.model.User;
import no.hvl.dat250.project.model.Vote;
import no.hvl.dat250.project.service.PollManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@Tag(name = "Poll API", description = "API for managing polls, votes, and users")
public class RestController {

    public final PollManager pollManager;

    public RestController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @PostMapping(value = "/user")
    User newUser(@RequestBody User user) {
        return pollManager.addUser(user);
    }

    @Operation(summary = "Get a specific user", description = "Get a specific user from the system")
    @GetMapping(value = "/user/{id}" )
    public User getUser(@PathVariable("id") String id) {
        return pollManager.getUser(id);
    }


    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users")
    @GetMapping(value = "/users")
    List<User> getUsers() {

        return pollManager.getUsers().values().stream().toList();
    }


    @Operation(summary = "Create a new poll", description = "Create a new poll for a user")
    @PostMapping(value = "/poll")
    Poll newPoll(@RequestBody Poll poll) {
        pollManager.getUser(poll.getCreator().getId()).getPollsCreated().add(poll);
        return pollManager.addPoll(poll);
    }

    @Operation(summary = "Get a specific poll", description = "Get a specific poll from the system")
    @GetMapping(value = "/poll/{id}" )
    public Poll getPoll(@PathVariable("id") String id) {
        return pollManager.getPoll(id);
    }


    @Operation(summary = "Get all polls", description = "Retrieve all polls in the system")
    @GetMapping(value = "/polls")
    List<Poll> getPolls() {
        return pollManager.getPolls().values().stream().toList();
    }


    @Operation(summary = "Submit a vote", description = "Submit a vote for a specific poll")
    @PostMapping(value = "/vote")
    Vote newVote(@RequestBody Vote vote) {
        User user  = pollManager.getUser(vote.getVoter().getId());
        Poll poll = pollManager.getPoll(vote.getPoll().getId());

        if (user.getVotes().stream().anyMatch(x -> x.getPoll() == poll)) {
           user.getVotes().remove(vote);
        }
        user.getVotes().add(vote);
        return vote;
    }


    @Operation(summary = "Get all votes for a user", description = "Retrieve all votes cast by a specific user")
    @PostMapping(value = "/votes")
    List<Vote> getVotes(@RequestBody User user) {
        return pollManager.getUser(user.getId()).getVotes();
    }


    @Operation(summary = "Delete a poll", description = "Delete a poll created by a user")
    @PostMapping(value = "/DelPoll")
    void delPoll(@RequestBody Poll poll) {
        pollManager.getPolls().remove(poll.getQuestions());
        pollManager.getUser(poll.getCreator().getId()).getPollsCreated().remove(poll);
    }
}
