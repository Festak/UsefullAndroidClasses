package project.ar.itransition.myapplication.utils.metric

/**
 * @author e.fetskovich on 11/12/18.
 */
// TODO: Make shortname adaptive to localisation
enum class Metrics (var valueToMeter: Double, var shortName: String) {
    ONE_PERCENT_OF_CM(0.0001, "one percent of CM"),
    MILIMETERS (0.001, "MM"),
    CENTIMETERS(0.01, "CM"),
    METRES(1.0, "M"),
    KILOMETERS(1000.0, "KM")
}