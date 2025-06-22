package com.weather.weather_consumer.model;

public class WeatherData {
    private double temperature;
    private double apparentTemperature;
    private int relativeHumidity;
    private int probabilityOfPrecipitation;
    private double precipitation;
    private double rain;
    private double snowfall;
    private double seaLevelPressure;
    private int cloudCover;
    private double windSpeed;
    private double windGusts;
    private double visibility;

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getApparentTemperature() { return apparentTemperature; }
    public void setApparentTemperature(double apparentTemperature) { this.apparentTemperature = apparentTemperature; }

    public int getRelativeHumidity() { return relativeHumidity; }
    public void setRelativeHumidity(int relativeHumidity) { this.relativeHumidity = relativeHumidity; }

    public int getProbabilityOfPrecipitation() { return probabilityOfPrecipitation; }
    public void setProbabilityOfPrecipitation(int probabilityOfPrecipitation) { this.probabilityOfPrecipitation = probabilityOfPrecipitation; }

    public double getPrecipitation() { return precipitation; }
    public void setPrecipitation(double precipitation) { this.precipitation = precipitation; }

    public double getRain() { return rain; }
    public void setRain(double rain) { this.rain = rain; }

    public double getSnowfall() { return snowfall; }
    public void setSnowfall(double snowfall) { this.snowfall = snowfall; }

    public double getSeaLevelPressure() { return seaLevelPressure; }
    public void setSeaLevelPressure(double seaLevelPressure) { this.seaLevelPressure = seaLevelPressure; }

    public int getCloudCover() { return cloudCover; }
    public void setCloudCover(int cloudCover) { this.cloudCover = cloudCover; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public double getWindGusts() { return windGusts; }
    public void setWindGusts(double windGusts) { this.windGusts = windGusts; }

    public double getVisibility() { return visibility; }
    public void setVisibility(double visibility) { this.visibility = visibility; }
}
