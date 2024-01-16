package com.example.teamapplication.web;

import com.example.teamapplication.dto.TeamDTO;
import com.example.teamapplication.dto.TeamsDTO;
import com.example.teamapplication.exceptions.TeamNotFoundException;
import com.example.teamapplication.services.TeamService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class TeamRestController {
    private TeamService teamPlayerService;

    @PostMapping("/teams")
    public ResponseEntity<String> saveTeam(@Valid @RequestBody TeamDTO teamDTO){
        try {
            teamPlayerService.addTeam(teamDTO);
            return new ResponseEntity<>("Team created successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Log other exceptions
            System.err.println("An error occurred: " + e.getMessage());
            return new ResponseEntity<>("An error occurred while processing your request.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/teams/pageAllTeams")
    public TeamsDTO getAllTeams(
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder){
        return teamPlayerService.getTeamList(page,size, sortField, sortOrder);
    }

    @GetMapping("/teams/all")
    public List<TeamDTO> getAllTeams(){
        return teamPlayerService.getAllteams();
    }

    @GetMapping("/teams/search")
    public List<TeamDTO> searchTeams(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return teamPlayerService.searchTeams("%"+keyword+"%");
    }

    @GetMapping("/teams/{id}")
    public TeamDTO getTeam(@PathVariable(name = "id") Long teamId) throws TeamNotFoundException {
        return teamPlayerService.getTeam(teamId);
    }

    @PutMapping("/teams/{teamId}")
    public TeamDTO updateTeam(@PathVariable Long teamId, @RequestBody TeamDTO teamDTO){
        teamDTO.setId(teamId);
        return teamPlayerService.updateTeam(teamDTO);
    }

    @DeleteMapping("/teams/{id}")
    public void deleteTeam(@PathVariable Long id){
        teamPlayerService.deleteTeam(id);
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable Long id){
        teamPlayerService.deletePlayer(id);
    }


}
