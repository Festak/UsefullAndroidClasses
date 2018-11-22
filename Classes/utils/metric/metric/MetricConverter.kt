package project.ar.itransition.myapplication.utils.metric

/**
 * @author e.fetskovich on 11/12/18.
 */
class MetricConverter : Converter {

    override fun convertMetric(value: Double, currentMetric: Metrics, newMetric: Metrics): Double {
        return convertFromMeters(convertToMeters(value, currentMetric), newMetric)
    }

    fun convertFromMeters(value: Double, newMetric: Metrics): Double {
        return value / newMetric.valueToMeter
    }

    fun convertToMeters(value: Double, currentMetric: Metrics): Double {
        return value * currentMetric.valueToMeter
    }

}