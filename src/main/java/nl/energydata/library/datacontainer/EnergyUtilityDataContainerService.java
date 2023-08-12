package nl.energydata.library.datacontainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import nl.energydata.library.dataprovider.DataProvider;
import nl.energydata.library.log.ILogService;
import nl.energydata.library.log.LogService;

@Service
public class EnergyUtilityDataContainerService{
	
    @Autowired
    private EntityManager entityManager;
	
    @Autowired
    private EnergyUtilityDataContainerRepository energyUtilityDataContainerRepository;
    
    @Autowired
    private ILogService logService;

	public EnergyUtilityDataContainer create() {
		return logService.executeSafeWithLog("Create new UtilityDataContainer", () -> {
			EnergyUtilityDataContainer energyUtilityDataContainer = new EnergyUtilityDataContainer();
			return energyUtilityDataContainer;
		});
	}
	
    public List<EnergyUtilityDataContainer> getAll() {
        return logService.executeSafeWithLog("Get all UtilityDataContainers", () -> {
            return energyUtilityDataContainerRepository.findAll();
        });
    }

    
    public Map<DurationCategory, List<EnergyUtilityDataContainer>> getByUtilityRates(
            Optional<Integer> ELECTRICTY_USAGE_ON_PEAK_IN_KWH, Optional<Integer> ELECTRICTY_USAGE_OFF_PEAK_IN_KWH,
            Optional<Integer> ELECTRICTY_PRODUCTION_IN_KWH, Optional<Integer> GAS_USAGE_IN_M3,
            int pageNumber, int pageSize) {

        Map<DurationCategory, List<EnergyUtilityDataContainer>> result = new HashMap<>();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        for (DurationCategory durationCategory : DurationCategory.values()) {
            CriteriaQuery<EnergyUtilityDataContainer> query = cb.createQuery(EnergyUtilityDataContainer.class);

            Root<EnergyUtilityDataContainer> energyUtility = query.from(EnergyUtilityDataContainer.class);

            List<Predicate> predicates = new ArrayList<>();

            if (ELECTRICTY_USAGE_ON_PEAK_IN_KWH.isPresent()) {
                predicates.add(cb.isNotNull(energyUtility.get("electricityOnPeakRatePerKwh")));
            }
            if (ELECTRICTY_USAGE_OFF_PEAK_IN_KWH.isPresent()) {
                predicates.add(cb.isNotNull(energyUtility.get("electricityOffPeakRatePerKwh")));
            }
            if (ELECTRICTY_PRODUCTION_IN_KWH.isPresent()) {
                predicates.add(cb.isNotNull(energyUtility.get("producedElectricityRatePerKwh")));
            }
            if (GAS_USAGE_IN_M3.isPresent()) {
                predicates.add(cb.isNotNull(energyUtility.get("gasRatePerM3")));
            }

            predicates.add(cb.equal(energyUtility.get("durationCategory"), durationCategory));

            query.orderBy(cb.asc(energyUtility.get("electricityOnPeakRatePerKwh")));

            query.select(energyUtility).where(predicates.toArray(new Predicate[0]));

            TypedQuery<EnergyUtilityDataContainer> typedQuery = entityManager.createQuery(query);
            typedQuery.setFirstResult(pageNumber * pageSize);
            typedQuery.setMaxResults(pageSize);

            List<EnergyUtilityDataContainer> plans = typedQuery.getResultList();
            result.put(durationCategory, plans);
        }

        return result;
    }




    
}

