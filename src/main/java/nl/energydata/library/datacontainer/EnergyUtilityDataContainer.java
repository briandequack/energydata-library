package nl.energydata.library.datacontainer;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "energy_utility_data_container")
@Entity
public class EnergyUtilityDataContainer extends DataContainer{
	    
    @Enumerated(EnumType.STRING)
    @Column(name = "duration_category")
    private DurationCategory durationCategory;
    
    @Column(name = "electricity_on_peak_rate_per_kwh", columnDefinition="DECIMAL(7,6)") 
    private BigDecimal electricityOnPeakRatePerKwh;
    
    @Column(name = "electricity_off_peak_rate_per_kwh", columnDefinition="DECIMAL(7,6)") 
    private BigDecimal electricityOffPeakRatePerKwh;
    
    @Column(name = "produced_electricity_rate_per_kwh", columnDefinition="DECIMAL(7,6)") 
    private BigDecimal producedElectricityRatePerKwh;
    
    @Column(name = "gas_rate_per_m3", columnDefinition="DECIMAL(7,6)") 
    private BigDecimal gasRatePerM3;
    
    @Column(name = "electricity_fixed_costs", columnDefinition="DECIMAL(7,6)") 
    private BigDecimal electricityFixedCosts;
    
    @Column(name = "gas_fixed_costs", columnDefinition="DECIMAL(7,6)") 
    private BigDecimal gasFixedCosts;


}
