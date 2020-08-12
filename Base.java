package ProjectName;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;



public class Base {
	
	public static WebDriver driver=null;
	String filePath="C:\\ProjectPath\\src\\main\\java\\global.properties";
	Properties p=new Properties();
	
	public void setup() throws IOException, InterruptedException
	{
		FileInputStream fi = new FileInputStream(filePath);
		p.load(fi);
		
		if(p.getProperty("browser").contains("google.chrome"))
		{
			Process process =Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			process.waitFor();
			process.destroy();
			
			//SSL Certificates
			
			DesiredCapabilities ch=DesiredCapabilities.chrome();
			ch.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			ch.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			ChromeOptions c= new ChromeOptions();
			c.merge(ch);
			
			System.setProperty("webdriver.chrome.driver",
			           "C:\\ProjectPath\\Browsers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		
		else if(p.getProperty("browser").contains("edge.driver"))
		{
			System.setProperty("webdriver.edge.driver",
					"C:\\ProjectPath\\Browsers\\MicrosoftWebDriver.exe");
			           
			driver = new EdgeDriver();
		}

		
		else
		{
			// Open internet explorer
		}
		
		driver.get(p.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}
	

	public Connection DBCon() throws ClassNotFoundException, SQLException
	{
		//step1 load the driver class  
				Class.forName("oracle.jdbc.driver.OracleDriver");  
				//step2 create  the connection object  
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@DBHost:1521:DBName", "DBUser", "DBuserPassword");
				return con;
	}
}
