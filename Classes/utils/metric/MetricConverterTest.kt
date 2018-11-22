package project.ar.itransition.myapplication

import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import project.ar.itransition.myapplication.utils.metric.MetricConverter
import project.ar.itransition.myapplication.utils.metric.Metrics

/**
 * @author e.fetskovich on 11/13/18.
 */
class MetricConverterTest {

    private lateinit var converter: MetricConverter

    @Before
    fun init() {
        converter = MetricConverter()
    }

    @Test
    fun convertCMToMetersTest() {
        convertToMetersAssertion(CM_TO_METERS_RESULT, Metrics.CENTIMETERS)
    }

    @Test
    fun convertMMToMetersTest() {
        convertToMetersAssertion(MM_TO_METERS_RESULT, Metrics.MILIMETERS)
    }

    @Test
    fun convert1PercentOfCMTOMetersTest() {
        convertToMetersAssertion(ONE_PERCENT_OF_CM_TO_METERS_RESULT, Metrics.ONE_PERCENT_OF_CM)
    }

    @Test
    fun convertMetersToMetersTest() {
        convertToMetersAssertion(METERS_TO_METERS_RESULT, Metrics.METRES)
    }

    @Test
    fun convertKilometersToMetersTest() {
        convertToMetersAssertion(KILOMETERS_TO_METERS_RESULT, Metrics.KILOMETERS)
    }

    private fun convertToMetersAssertion(expectedResult: Double, fromMetric: Metrics) {
        val meterValue = converter.convertToMeters(VALUE_CONVERT_TO_METRES, fromMetric)
        assertTrue(meterValue == expectedResult)
    }

    @Test
    fun convertCMFromMetersTest() {
        val cmValue = converter.convertFromMeters(CM_TO_METERS_RESULT, Metrics.CENTIMETERS)
        assertTrue(cmValue == VALUE_CONVERT_TO_METRES)
    }

    @Test
    fun convertOnePercentOfCMToCM(){
        val cmValue = converter.convertMetric(ONE_PERCENT_OF_CM_VALUE, Metrics.ONE_PERCENT_OF_CM, Metrics.CENTIMETERS)
        assertTrue(cmValue == ONE_PERCENT_OF_CM_IN_CENTIMETERS)
    }

    companion object {
        const val VALUE_CONVERT_TO_METRES = 2.0

        const val CM_TO_METERS_RESULT = 0.02
        const val MM_TO_METERS_RESULT = 0.002
        const val ONE_PERCENT_OF_CM_TO_METERS_RESULT = 0.0002
        const val METERS_TO_METERS_RESULT = 2.0
        const val KILOMETERS_TO_METERS_RESULT = 2000.0

        const val ONE_PERCENT_OF_CM_VALUE = 123000.0
        const val ONE_PERCENT_OF_CM_IN_CENTIMETERS = 1230.0
    }

}