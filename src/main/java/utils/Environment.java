package utils;

public class Environment {


	public static environment currentEnvironment = environment.PROD;
	public static boolean printALL = true;
	public static String userToken;
	public static String cpCookie;

	public String baseAPIsURL, baseWebsiteURL;

	public void variables(environment environment) {
		if(environment.equals(Environment.environment.PROD)) {
			baseAPIsURL = "";
			baseWebsiteURL = "https://www.airbnb.com/";
		}
		else if(environment.equals(Environment.environment.STAG)) {
			baseAPIsURL = "";
			baseWebsiteURL = "";
		}
		else if(environment.equals(Environment.environment.DEV)) {
			baseAPIsURL = "";
			baseWebsiteURL = "";
		}
	}

	public enum environment {
		DEV,
		STAG,
		PROD
	}

}
