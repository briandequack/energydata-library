package nl.energydata.library.energytax;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.energydata.library.log.ILogService;

@Service
public class EnergyTaxService {
	
	@Autowired
	ILogService logService;

	public BigDecimal getElecExcise() {
		return logService.executeSafeWithLog("Get elec excise", () -> {
			BigDecimal elecExcise = new BigDecimal(0.12599);
			return elecExcise;
		});
	}
	
	public BigDecimal getElecTaxDiscount() {
		return logService.executeSafeWithLog("Get elec excise", () -> {
			BigDecimal elecTaxDiscount = new BigDecimal(597.86);
			return elecTaxDiscount;
		});
	}
	
	public BigDecimal getGasExcise() {
		return logService.executeSafeWithLog("Get gas excise", () -> {
			BigDecimal gasExcise = new BigDecimal(0.48980);
			return gasExcise;
		});
	}
	
	public BigDecimal getElecTax() {
		return logService.executeSafeWithLog("Get elec tax", () -> {
			BigDecimal elecTax = new BigDecimal(21);
			return elecTax;
		});
	}
	
	public BigDecimal getGasTax() {
		return logService.executeSafeWithLog("Get gas tax", () -> {
			BigDecimal gasTax = new BigDecimal(21);
			return gasTax;
		});
	}
	
	
	public BigDecimal elecRateRemoveAllTax(BigDecimal elecRateInclAllTax) {
		return logService.executeSafeWithLog("Elec rate remove all tax", () -> {
			BigDecimal elecRateWithoutTax = elecRateRemoveTax(elecRateInclAllTax);
			BigDecimal elecRateWithoutExciseAndTax = elecRateRemoveExcise(elecRateWithoutTax);
			return elecRateWithoutExciseAndTax;
		});
	}
	
	public BigDecimal elecRateRemoveTax(BigDecimal elecRateInclTax) {
		return logService.executeSafeWithLog("Elec rate remove tax", () -> {
			BigDecimal elecRateWithoutTax = subtractTax(elecRateInclTax, getElecTax());
			return elecRateWithoutTax;
		});
	}
	
	public BigDecimal elecRateRemoveExcise(BigDecimal elecRateInclExcise) {
		return logService.executeSafeWithLog("Gas rate remove excise", () -> {
			BigDecimal elecRateWithoutExcise = elecRateInclExcise.subtract(getElecExcise());
			return elecRateWithoutExcise;		
		});
	}
	
	public BigDecimal elecRateAddAllTax(BigDecimal elecRate) {
		return logService.executeSafeWithLog("Elec rate add all tax", () -> {
			BigDecimal elecRateWithExcise = elecRateAddExcise(elecRate);
			BigDecimal elecRateWithInclAll = elecRateAddTax(elecRateWithExcise);
			return elecRateWithInclAll;
		});
	}
	
	public BigDecimal elecRateAddTax(BigDecimal elecRate) {
		return logService.executeSafeWithLog("Elec rate add tax", () -> {
			BigDecimal elecRateWithTax = addTax(elecRate, getElecTax());
			return elecRateWithTax;
		});
	}
	
	public BigDecimal elecRateAddExcise(BigDecimal elecRate) {
		return logService.executeSafeWithLog("Gas rate add excise", () -> {
			BigDecimal elecRateWithExcise = elecRate.add(getElecExcise());
			return elecRateWithExcise;		
		});
	}
	
	public BigDecimal gasRateRemoveExcise(BigDecimal gasRateInclExcise) {
		return logService.executeSafeWithLog("Elec rate remove excise", () -> {
			BigDecimal gasRateWithoutExcise = gasRateInclExcise.subtract(getGasExcise());
			return gasRateWithoutExcise;
		});
	}
	
	public BigDecimal gasRateRemoveAllTax(BigDecimal gasRateInclAllTax) {
		return logService.executeSafeWithLog("Gas rate remove all tax", () -> {
			BigDecimal gasRateWithoutTax = gasRateRemoveTax(gasRateInclAllTax);
			BigDecimal gasRateWithoutExciseAndTax = gasRateRemoveExcise(gasRateWithoutTax);
			return gasRateWithoutExciseAndTax;
		});
	}
	
	public BigDecimal gasRateRemoveTax(BigDecimal gasRateInclTax) {
		return logService.executeSafeWithLog("Gas rate remove tax", () -> {
			BigDecimal gasRateWithoutTax = subtractTax(gasRateInclTax, getGasTax());
			return gasRateWithoutTax;
		});
	}
	
	public BigDecimal gasRateAddAllTax(BigDecimal gasRate) {
		return logService.executeSafeWithLog("Gas rate add all tax", () -> {
			BigDecimal gasRateWithExcise = gasRateAddExcise(gasRate);
			BigDecimal gasRateWithInclAll = gasRateAddTax(gasRateWithExcise);
			return gasRateWithInclAll;
		});
	}
	
	public BigDecimal gasRateAddTax(BigDecimal gasRate) {
		return logService.executeSafeWithLog("Gas rate add tax", () -> {
			BigDecimal gasRateWithTax = addTax(gasRate, getGasTax());
			return gasRateWithTax;
		});
	}
	
	public BigDecimal gasRateAddExcise(BigDecimal gasRate) {
		return logService.executeSafeWithLog("Gas rate add excise", () -> {
			BigDecimal gasRateWithExcise = gasRate.add(getGasExcise());
			return gasRateWithExcise;		
		});
	}

	
	public BigDecimal fixedCostsRemoveTax(BigDecimal fixedCostsInclTax) {
		return logService.executeSafeWithLog("Fixed cost remove tax", () -> {
			BigDecimal fixedCostsWithoutTax = subtractTax(fixedCostsInclTax, getElecTax());
			return fixedCostsWithoutTax;
		});
	}
	
	public BigDecimal fixedCostsAddTax(BigDecimal fixedCosts) {
		return logService.executeSafeWithLog("Fixed cost add tax", () -> {
			BigDecimal fixedCostsWithTax = addTax(fixedCosts, getElecTax());
			return fixedCostsWithTax;
		});
	}
	
	public BigDecimal addTax(BigDecimal value, BigDecimal taxRatePercentage) {
		return logService.executeSafeWithLog("Add tax", () -> {
			BigDecimal taxRate = taxRatePercentage.divide(new BigDecimal(100),5, RoundingMode.HALF_UP);
			BigDecimal valueInclTax = value.add(value.multiply(taxRate));	 	
			return valueInclTax;
		});
	}
	
	public BigDecimal subtractTax(BigDecimal valueInclTax, BigDecimal taxRatePercentage) {
		return logService.executeSafeWithLog("Subtract tax", () -> {
			BigDecimal taxRate = taxRatePercentage.divide(new BigDecimal(100),5, RoundingMode.HALF_UP);
			BigDecimal value = valueInclTax.divide(taxRate.add(new BigDecimal(1)) ,5, RoundingMode.HALF_UP);
			return value;
		});
	}
	
	public BigDecimal monthlyRateToDailyRate(BigDecimal monthlyRate) {
		return logService.executeSafeWithLog("Monthly rate to daily rate", () -> {
			BigDecimal yearlyRate = monthlyRate.multiply(new BigDecimal(12));
			BigDecimal dailyRate = yearlyRate.divide(new BigDecimal(365) ,5, RoundingMode.HALF_UP);
			return dailyRate;
		});
	}
	
	public BigDecimal yearlyRateToDailyRate(BigDecimal yearlyRate) {
		return logService.executeSafeWithLog("Yearly rate to daily rate", () -> {
			BigDecimal dailyRate = yearlyRate.divide(new BigDecimal(365) ,5, RoundingMode.HALF_UP);
			return dailyRate;
		});
	}

	public BigDecimal numYearlyToDaily(BigDecimal yearlyNum) {
		return logService.executeSafeWithLog("Yearly rate to daily rate", () -> {
			BigDecimal dailyNum = yearlyNum.divide(new BigDecimal(365) ,5, RoundingMode.HALF_UP);
			return dailyNum;
		});
	}
	
	public BigDecimal numDailyToMonthly(BigDecimal dailyNum) {
		return logService.executeSafeWithLog("Yearly rate to daily rate", () -> {
			BigDecimal yearlyNum = dailyNum.multiply(new BigDecimal(365));
			BigDecimal monthlyNum = yearlyNum.divide(new BigDecimal(12) ,5, RoundingMode.HALF_UP);
			return monthlyNum;
		});
	}
	
	public BigDecimal numDailyToYearly(BigDecimal dailyNum) {
		return logService.executeSafeWithLog("Yearly rate to daily rate", () -> {
			BigDecimal yearlyNum = dailyNum.multiply(new BigDecimal(365));
			return yearlyNum;
		});
	}
	
	public BigDecimal numYearlyToMonthly(BigDecimal yearlyNum) {
		return logService.executeSafeWithLog("Yearly rate to daily rate", () -> {
			BigDecimal monthlyNum = yearlyNum.divide(new BigDecimal(12) ,5, RoundingMode.HALF_UP);
			return monthlyNum;
		});
	}

	public BigDecimal calculateCappedUnits(BigDecimal usage, BigDecimal totalUsage, BigDecimal cap) {  
	    BigDecimal proportion = usage.divide(totalUsage, 5, RoundingMode.HALF_UP);
	    BigDecimal cappedUnits = cap.multiply(proportion);
	    return usage.compareTo(cappedUnits) <= 0 ? usage : cappedUnits;
	}

	public BigDecimal calculateNonCappedUnits(BigDecimal usage, BigDecimal totalUsage, BigDecimal cap) {
	    BigDecimal proportion = usage.divide(totalUsage, 5, RoundingMode.HALF_UP);
	    BigDecimal cappedUnits = cap.multiply(proportion);
	    return usage.compareTo(cappedUnits) <= 0 ? BigDecimal.ZERO : usage.subtract(cappedUnits);
	}
	
	
	
}
