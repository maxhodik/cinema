package service.strategy;

import service.strategy.SortStrategy;

public interface Factory {
   SortStrategy defineStrategy (String orderBy);

}
