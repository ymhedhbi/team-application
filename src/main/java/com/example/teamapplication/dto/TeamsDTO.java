package com.example.teamapplication.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamsDTO {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<TeamDTO> teamDto;
}
