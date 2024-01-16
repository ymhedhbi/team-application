package com.example.teamapplication.services;

import com.example.teamapplication.dto.TeamDTO;
import com.example.teamapplication.dto.TeamsDTO;
import com.example.teamapplication.entities.Player;
import com.example.teamapplication.entities.Team;
import com.example.teamapplication.exceptions.TeamNotFoundException;
import com.example.teamapplication.mapper.DtoMapper;
import com.example.teamapplication.repositories.PlayerRepository;
import com.example.teamapplication.repositories.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService{
    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;
    private DtoMapper dtoMapper;

    /**
     *
     * @param teamDTO
     * @return
     */
    @Override
    public TeamDTO addTeam(TeamDTO teamDTO) {
        Team team = dtoMapper.convertToEntity(teamDTO);
        for (Player player : team.getListPlayers()) {
            player.setTeam(team);
        }
        Team savedTeam = teamRepository.save(team);
        return dtoMapper.convertToDTO(savedTeam);
    }

    /**
     *
     * @param page
     * @param size
     * @param sortField
     * @param sortOrder
     * @return
     */
    @Override
    public TeamsDTO getTeamList(int page, int size, String sortField, String sortOrder) {
        Sort sort = Sort.by(sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        Page<Team> teams = teamRepository.findAll(PageRequest.of(page, size,sort));
        TeamsDTO teamsDto = new TeamsDTO();
        List<TeamDTO> teamDtoList = teams.getContent().stream().map(team -> dtoMapper.convertToDTO(team)).collect(Collectors.toList());
        teamsDto.setTeamDto(teamDtoList);
        teamsDto.setPageSize(size);
        teamsDto.setCurrentPage(page);
        teamsDto.setTotalPages(teams.getTotalPages());
        return teamsDto;
    }

    @Override
    public List<TeamDTO> getAllteams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(team -> dtoMapper.convertToDTO(team)).collect(Collectors.toList());
    }

    /**
     *
     * @param keyword
     * @return
     */
    @Override
    public List<TeamDTO> searchTeams(String keyword) {
        List<Team> teams=teamRepository.searchTeam(keyword);
        List<TeamDTO> teamDTOS = teams.stream().map(team -> dtoMapper.convertToDTO(team)).collect(Collectors.toList());
        return teamDTOS;
    }

    /**
     *
     * @param teamDTO
     * @return
     */
    @Override
    public TeamDTO updateTeam(TeamDTO teamDTO) {
        Team team = dtoMapper.convertToEntity(teamDTO);
        Team savedTeam = teamRepository.save(team);
        log.info("Team updates successfully");
        return dtoMapper.convertToDTO(savedTeam);
    }

    /**
     *
     * @param teamId
     * @return
     * @throws TeamNotFoundException
     */
    @Override
    public TeamDTO getTeam(Long teamId) throws TeamNotFoundException {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team Not found"));
        return dtoMapper.convertToDTO(team);
    }

    /**
     *
     * @param teamId
     */
    @Override
    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    /**
     *
     * @param playerId
     */
    @Override
    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);

    }


}
