package service.strategy;

import dto.SessionAdminDto;

import java.util.List;

public interface SortStrategy {
    List<SessionAdminDto> sort (List<SessionAdminDto> list);
}
