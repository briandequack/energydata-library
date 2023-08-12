package nl.energydata.library.dataprovider;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table(name = "energy_data_provider")
@Entity
public class EnergyDataProvider extends DataProvider {


    //public void addUtilityConnection(UtilityConnection utilityConnection) {
     //   this.utilities.add(utilityConnection);
        //utilityConnection.setDataProvider(this);
    //}

    //public void removeUtilityConnection(UtilityConnection utilityConnection) {
    //    this.utilities.remove(utilityConnection);
        //utilityConnection.setDataProvider(null);
    //}

}

