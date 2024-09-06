package no.hvl.dat250.project.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.hvl.dat250.project.model.Poll;
import no.hvl.dat250.project.model.User;
import no.hvl.dat250.project.model.Vote;
import no.hvl.dat250.project.service.PollManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@Tag(name = "Poll API", description = "API for managing polls, votes, and users")
public class RestController {

    private final PollManager pollManager;

    public RestController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/user")
    User newUser(@RequestBody User user) {

        return pollManager.addUser(user);
    }


    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})
    })
    @GetMapping("/users")
    List<User> getUsers() {
        return pollManager.getUsers().values().stream().toList();
    }


    @Operation(summary = "Create a new poll", description = "Create a new poll for a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Poll created successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Poll.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/poll")
    Poll newPoll(@RequestBody Poll poll) {
        pollManager.getUser(poll.getCreator().getUsername()).getPolls().add(poll);
        return pollManager.addPoll(poll);
    }


    @Operation(summary = "Get all polls", description = "Retrieve all polls in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Polls retrieved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Poll.class))})
    })
    @GetMapping("/polls")
    List<Poll> getPolls() {
        return pollManager.getPolls().values().stream().toList();
    }


    @Operation(summary = "Submit a vote", description = "Submit a vote for a specific poll")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vote submitted successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Vote.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid vote")
    })
    @PostMapping("/vote")
    Vote newVote(@RequestBody Vote vote) {
        if(pollManager.getUser(vote.getVoter().getUsername()).getVotes().stream().anyMatch( x -> x.getPoll() == vote.getPoll())){
            pollManager.getUser(vote.getVoter().getUsername()).getVotes().remove(vote);
        }
        pollManager.getUser(vote.getVoter().getUsername()).getVotes().add(vote);
        return vote;
    }


    @Operation(summary = "Get all votes for a user", description = "Retrieve all votes cast by a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Votes retrieved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Vote.class))}),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/votes")
    List<Vote> getVotes(@RequestBody User user) {
        return pollManager.getUser(user.getUsername()).getVotes();
    }


    @Operation(summary = "Delete a poll", description = "Delete a poll created by a user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Poll deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Poll not found")
    })
    @PostMapping("/DelPoll")
    void delPoll(@RequestBody Poll poll) {
        pollManager.getPolls().remove(poll.getQuestions());
        pollManager.getUser(poll.getCreator().getUsername()).getPolls().remove(poll);
    }
}
