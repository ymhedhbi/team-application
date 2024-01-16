package com.example.teamapplication;

import com.example.teamapplication.dto.TeamDTO;
import com.example.teamapplication.entities.Team;
import com.example.teamapplication.mapper.DtoMapper;
import com.example.teamapplication.repositories.PlayerRepository;
import com.example.teamapplication.repositories.TeamRepository;
import com.example.teamapplication.services.TeamService;
import com.example.teamapplication.services.TeamServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private DtoMapper dtoMapper ;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Test
    public void testGetAllTeams() {
        // Mocking data
        List<Team> teamList = Arrays.asList(
                new Team(1L, "Team1", "T1", 1000000.0, Collections.emptyList()),
                new Team(2L, "Team2", "T2", 1200000.0, Collections.emptyList())
        );

        Mockito.when(teamRepository.findAll()).thenReturn(teamList);

        // Set the mocked mapper in the service

        // Mock the mapping behavior in your teamMapper mock
        Mockito.when(dtoMapper.convertToDTO(Mockito.any(Team.class)))
                .thenAnswer(invocation -> {
                    Team team = invocation.getArgument(0);
                    return new TeamDTO(team.getId(), team.getName(), team.getAcronym(), team.getBudget(),new ArrayList<>());
                });

        // Test
        List<TeamDTO> result = teamService.getAllteams();

        Assertions.assertEquals(2, result.size());
        // Add more assertions based on your mapping behavior
    }

    @Test
    public void testAddTeam() {
        // Simuler des données d'entrée
        TeamDTO teamDTO = new TeamDTO(null, "New Team", "NT",120000.0, new ArrayList<>());

        // Simuler le comportement du mapper
        Team teamToAdd = new Team(null, "New Team", "NT",120000.0, new ArrayList<>());
        Mockito.when(dtoMapper.convertToEntity(teamDTO)).thenReturn(teamToAdd);

        // Simuler le comportement du repository
        Team teamAdded = new Team(1L, "New Team", "NT", 120000.0,new ArrayList<>() );
        Mockito.when(teamRepository.save(teamToAdd)).thenReturn(teamAdded);

        // Simuler le comportement du mapper lors de la conversion Team -> TeamDTO
        Mockito.when(dtoMapper.convertToDTO(teamAdded)).thenReturn(new TeamDTO(1L, "New Team", "NT", 120000.0, new ArrayList<>()));

        // Appeler la méthode du service
        TeamDTO result = teamService.addTeam(teamDTO);

        // Vérifier les résultats
        Assert.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("New Team", result.getName());
        Assertions.assertEquals("NT", result.getAcronym());
        Assertions.assertEquals(120000.0, result.getBudget());
    }
}