package com.example.teamapplication.services;

import com.example.teamapplication.dto.PlayerDTO;
import com.example.teamapplication.dto.TeamDTO;
import com.example.teamapplication.dto.TeamsDTO;
import com.example.teamapplication.exceptions.TeamNotFoundException;

import java.util.List;

public interface TeamService {

    TeamDTO addTeam(TeamDTO teamDTO);
    TeamsDTO getTeamList(int page, int size, String sortField, String sortOrder);

    List<TeamDTO> getAllteams();
    List<TeamDTO> searchTeams(String keyword);
    TeamDTO updateTeam(TeamDTO teamDTO);

    TeamDTO getTeam(Long teamId) throws TeamNotFoundException;
    void deleteTeam(Long teamId);
    void deletePlayer(Long playerId);


}
