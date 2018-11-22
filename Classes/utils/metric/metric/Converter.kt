package project.ar.itransition.myapplication.utils.metric

/**
 * @author e.fetskovich on 11/12/18.
 */
interface Converter {
    fun convertMetric(value: Double, currentMetric: Metrics, newMetric: Metrics) : Double
}