package ulb.repository;

import ulb.model.no.FloorDefinition;

import java.util.List;
import java.util.Optional;

/** Read-only contract for NO tower floor definitions. */
public interface IFloorRepository {

    List<FloorDefinition> getAllFloors();
    int getNumbersOfFloors();
    Optional<FloorDefinition> getFloorByNumber(int floorNumber);
}
