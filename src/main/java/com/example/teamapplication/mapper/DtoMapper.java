package com.example.teamapplication.mapper;

import com.example.teamapplication.dto.PlayerDTO;
import com.example.teamapplication.dto.TeamDTO;
import com.example.teamapplication.entities.Player;
import com.example.teamapplication.entities.Team;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DtoMapper {

    private static final ModelMapper modelMapper = new ModelMapper();
    public TeamDTO convertToDTO(Team team) {return modelMapper.map(team, TeamDTO.class);
    }
    public Team  convertToEntity(TeamDTO teamDto) {return modelMapper.map(teamDto, Team.class);
    }
    public PlayerDTO convertToDTO(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }

    public Player convertToEntity(PlayerDTO playerDTO) {
        return modelMapper.map(playerDTO, Player.class);
    }


}
