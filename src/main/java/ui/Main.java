package ui;

public class Main {
public static void main(String[] args) {
	
	Config cfg = new Config();
	cfg.loadConfig();
	cfg.getBrowsers();
	cfg.getUrl();
	
	Driver cr = new Driver(AvailableDrivers.CHROME);
	cr.setupPage(cfg);
	
	Driver fx = new Driver(AvailableDrivers.FIREFOX);
	fx.setupPage(cfg);

}
}