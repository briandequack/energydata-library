package nl.energydata.library.datacontainer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.energydata.library.dataprovider.DataProvider;

public interface DataContainerRepository extends JpaRepository<DataContainer, Long> {


}