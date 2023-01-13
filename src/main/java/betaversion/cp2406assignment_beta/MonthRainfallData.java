package betaversion.cp2406assignment_beta;

/**
 * CP2406 Assignment - Tan Beng Siang
 * Basic class for storing data about one month's worth of rainfall.
 * Contains getter method for Date and constructor method
 * for each variable.
 */
public class MonthRainfallData {

        private final double total;
        private final double min;
        private final double max;
        private final int month;
        private final int year;

        public MonthRainfallData(double newTotal, double newMin, double newMax, int newMonth, int newYear) {
                total = newTotal;
                min = newMin;
                max = newMax;
                month = newMonth;
                year = newYear;
        }

        @Override
        public String toString() {
                return (month + "/" + year + " had " + total + " millimeters of rain");
        }

        public String getDate() {
                return month + "/" + year;
        }

        public double getTotal() {
                return total;
        }

        public double getMin() {
                return min;
        }

        public double getMax() {
                return max;
        }

        public int getMonth() {
                return month;
        }

        public int getYear() {
                return year;
        }

}
