package timeseries.models.arima;

import static data.DoubleFunctions.newArray;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import org.junit.Test;

import data.TestData;
import timeseries.TimePeriod;
import timeseries.TimeSeries;
import timeseries.models.Forecast;
import timeseries.models.arima.Arima;
import timeseries.models.arima.Arima.ModelCoefficients;
import timeseries.models.arima.Arima.ModelOrder;

public class ArimaSpec {
  
  @Test
  public void whenArimaModelFitThenParametersSimilarToROutput() throws Exception {
    TimeSeries series = TestData.livestock();
    ModelOrder order = new ModelOrder(1, 1, 1, 0, 0, 0, false);
    Arima model = new Arima(series, order, TimePeriod.oneYear());
    assertThat(model.coefficients().arCoeffs()[0], is(closeTo(0.64, 0.02)));
    assertThat(model.coefficients().maCoeffs()[0], is(closeTo(-0.48, 0.02)));
  }
  
  @Test
  public void whenArimaModelForecastThenForecastValuesCorrect() throws Exception {
    TimeSeries series = TestData.livestock();
    ModelOrder order = new ModelOrder(2, 0, 1, 0, 0, 0, false);
    ModelCoefficients coeffs = ModelCoefficients.newBuilder().setArCoeffs(-0.2, -0.7)
            .setMaCoeffs(-0.6).setDiff(0).build();
    Arima model = new Arima(series, coeffs, TimePeriod.oneYear());
    new Arima(series, order, TimePeriod.oneYear());
    new Arima(series, order, TimePeriod.oneYear());
    ArimaForecast fcst = new ArimaForecast(model, 12, 0.05);
    System.out.println(Arrays.toString(fcst.getPsiCoefficients()));
  }

}