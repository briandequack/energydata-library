package nl.energydata.library.datacontainer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DataContainerService {

	@Autowired
	private DataContainerRepository dataContainerRepository;
	
	@Autowired
	private EnergyUtilityDataContainerRepository energyUtilityDataContainerRepository;

	public void saveOrUpdate(DataContainer dataContainer) {	
		if (dataContainer instanceof EnergyUtilityDataContainer) {
	        EnergyUtilityDataContainer energyData = (EnergyUtilityDataContainer) dataContainer;
	        Optional<EnergyUtilityDataContainer> excistingDataContainer = energyUtilityDataContainerRepository.findByDataProviderAndDurationCategory(dataContainer.getDataProvider(), energyData.getDurationCategory());
	    
	        if (excistingDataContainer.isPresent()) {
	            EnergyUtilityDataContainer existing = excistingDataContainer.get();
	            energyData.setId(existing.getId());
	            energyData.setTimeStamp(existing.getTimeStamp()); 
	        }
	        
	        dataContainerRepository.save(energyData);
		}
			
	}
	
	 

}
