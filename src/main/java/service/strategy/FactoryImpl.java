package service.strategy;

import dto.SessionAdminDto;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FactoryImpl implements Factory {
    private static final Map<String,SortStrategy> STRATEGIES = Map.of(
            "attendance", x-> x.stream().sorted(Comparator.comparing(sessionAdminDto -> sessionAdminDto.getAttendance())).collect(Collectors.toList()),
            "number_sold_seats", x-> x.stream().sorted(Comparator.comparing(SessionAdminDto::getNumberOfSoldSeats)).collect(Collectors.toList()),
            "capacity", x-> x.stream().sorted(Comparator.comparing(sessionAdminDto -> sessionAdminDto.getCapacity())).collect(Collectors.toList())

    );


    @Override
    public SortStrategy defineStrategy(String orderBy) {
        return STRATEGIES.get(orderBy);
    }
}
