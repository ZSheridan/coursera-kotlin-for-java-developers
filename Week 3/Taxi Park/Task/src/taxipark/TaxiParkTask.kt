package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers - trips.map { it.driver }
        // could be allDrivers.minus(trips.map { it.driver })
        // Another SOLUTION: allDrivers.filter { driver -> trips.none { it.driver == driver } }.toSet()
        // MY SOLUTION: allDrivers.filter { driver -> driver !in trips.map { it.driver } }.toSet()
/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    allPassengers
        .filter { pass ->
            trips.count { it.passengers.contains(pass) } >= minTrips
        }
        .toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    allPassengers
        .filter { pass ->
            trips.count { it.driver == driver && pass in it.passengers } > 1
        }
        .toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers.filter { pass ->
        val tripsWithDisccount = trips.count { pass in it.passengers && it.discount != null }
        val tripsWithoutDisccount = trips.count { pass in it.passengers && it.discount == null }
        tripsWithDisccount > tripsWithoutDisccount
    }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? =
    trips
        .groupBy {
            val start = it.duration/10 * 10
            val end = start + 9
            start..end
        }
        .maxBy { (_, tripGroup) -> tripGroup.size }
        ?.key

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false
    val totalIncome = trips.sumByDouble(Trip::cost)
    val listDescendingDriverIncome = allDrivers
        .map { driver -> trips.filter { it.driver == driver }.sumByDouble(Trip::cost) }
        .sortedDescending()
    val numTopDrivers = (0.2*allDrivers.size).toInt()
    val twentyIncome = listDescendingDriverIncome
        .take(numTopDrivers)
        .sum()
    return twentyIncome >= 0.8*totalIncome
}