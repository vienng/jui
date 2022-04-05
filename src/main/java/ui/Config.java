package ui;

import java.io.InputStream;
import java.util.Properties;

public class Config {
	private String url;
	private String[] browsers;
	private int timeoutSeconds;

	public void loadConfig() {
		try {
			InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");
			Properties prop = new Properties();
			prop.load(input);
			
			this.url = prop.getProperty("url");
			this.timeoutSeconds = Integer.parseInt(prop.getProperty("timeout.seconds"));
			this.browsers = prop.getProperty("browsers").split(":");
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public String getUrl() {
		return url;
	}

	public String[] getBrowsers() {
		return browsers;
	}

	public int getTimeoutSeconds() {
		return timeoutSeconds;
	}
}