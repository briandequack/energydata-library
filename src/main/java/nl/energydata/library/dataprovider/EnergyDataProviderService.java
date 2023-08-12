package nl.energydata.library.dataprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EnergyDataProviderService {

    @Autowired
    private DataProviderRepository dataProviderRepository;

    public EnergyDataProvider createEnergyDataProvider(String name) {
        EnergyDataProvider dataProvider = (EnergyDataProvider) dataProviderRepository.findByName(name);
        
        if (dataProvider == null) {
            dataProvider = new EnergyDataProvider();
            dataProvider.setName(name);
            try {
                dataProvider = dataProviderRepository.save(dataProvider);
            } catch(Exception e) {
                System.err.println("Error occurred while saving EnergyDataProvider: " + e.getMessage());

            }
        }

        return dataProvider;
    }

}
