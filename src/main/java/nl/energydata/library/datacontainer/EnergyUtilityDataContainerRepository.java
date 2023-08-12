package nl.energydata.library.datacontainer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.energydata.library.dataprovider.DataProvider;

public interface EnergyUtilityDataContainerRepository extends JpaRepository<EnergyUtilityDataContainer, Long> {

	@Query("SELECT e FROM EnergyUtilityDataContainer e WHERE e.dataProvider = :dataProvider AND e.durationCategory = :durationCategory")
    Optional<EnergyUtilityDataContainer> findByDataProviderAndDurationCategory(DataProvider dataProvider, DurationCategory durationCategory);

}