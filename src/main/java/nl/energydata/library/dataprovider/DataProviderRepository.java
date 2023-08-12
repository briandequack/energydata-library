package nl.energydata.library.dataprovider;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DataProviderRepository extends JpaRepository<DataProvider, Long> {
    DataProvider findByName(String name);
}