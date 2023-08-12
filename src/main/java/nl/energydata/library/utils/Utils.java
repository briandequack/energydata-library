package nl.energydata.library.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;


public class Utils {
	
	public static BigDecimal getElecExcise() {
		BigDecimal elecExcise = new BigDecimal(0.12599);
		return elecExcise;
	}
	
	public static BigDecimal getGasExcise() {
		BigDecimal gasExcise = new BigDecimal(0.48980);
		return gasExcise;
	}
	
	public static BigDecimal getElecTax() {
		BigDecimal elecTax = new BigDecimal(21);
		return elecTax;
	}
	
	public static BigDecimal getGasTax() {
		BigDecimal gasTax = new BigDecimal(21);
		return gasTax;
	}
	
	
	public static BigDecimal elecRateRemoveAllTax(BigDecimal elecRateInclAllTax) {
		BigDecimal elecRateWithoutTax = elecRateRemoveTax(elecRateInclAllTax);
		BigDecimal elecRateWithoutExciseAndTax = elecRateRemoveTax(elecRateWithoutTax);
		return elecRateWithoutExciseAndTax;
	}
	
	public static BigDecimal elecRateRemoveTax(BigDecimal elecRateInclTax) {
		BigDecimal elecRateWithoutTax = subtractTax(elecRateInclTax, getElecTax());
		return elecRateWithoutTax;
	}
	
	public static BigDecimal elecRateRemoveExcise(BigDecimal elecRateInclExcise) {
		BigDecimal elecRateWithoutExcise = elecRateInclExcise.subtract(getElecExcise());
		return elecRateWithoutExcise;			
	}
	
	public static BigDecimal addTax(BigDecimal value, BigDecimal taxRatePercentage) {
		BigDecimal taxRate = taxRatePercentage.divide(new BigDecimal(100),5, RoundingMode.HALF_UP);
		BigDecimal valueInclTax = value.add(value.multiply(taxRate));	 	
		return valueInclTax;
	}
	
	public static BigDecimal subtractTax(BigDecimal valueInclTax, BigDecimal taxRatePercentage) {
		BigDecimal taxRate = taxRatePercentage.divide(new BigDecimal(100),5, RoundingMode.HALF_UP);
		BigDecimal value = valueInclTax.divide(taxRate.add(new BigDecimal(1)) ,5, RoundingMode.HALF_UP);
		return value;
	}
	
	public static BigDecimal monthlyRateToDailyRate(BigDecimal monthlyRate) {
		BigDecimal yearlyRate = monthlyRate.multiply(new BigDecimal(12));
		BigDecimal dailyRate = yearlyRate.divide(new BigDecimal(365) ,5, RoundingMode.HALF_UP);
		return dailyRate;
	}

}

