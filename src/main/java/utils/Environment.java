package utils;

public class Environment {


    public static environment currentEnvironment = environment.PROD;

    public String baseWebsiteURL;

    public void variables(environment environment) {
        if (environment.equals(Environment.environment.PROD)) {
            baseWebsiteURL = "https://www.airbnb.com/";
        } else if (environment.equals(Environment.environment.STAG)) {
            baseWebsiteURL = "";
        } else if (environment.equals(Environment.environment.DEV)) {
            baseWebsiteURL = "";
        }
    }

    public enum environment {
        DEV,
        STAG,
        PROD
    }

}
