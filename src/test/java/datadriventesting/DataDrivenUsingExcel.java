package datadriventesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DataDrivenUsingExcel {
    WebDriver driver;

    @BeforeClass
    public void setup(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Rezia Parvin\\IdeaProjects\\DataDrivenFramework\\drivers\\chromedriver.exe");
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
    @Test(dataProvider = "LoginData")
    public void loginTest(String user,String pwd,String exp){
    driver.get("https://admin-demo.nopcommerce.com/login");

        WebElement txtEmail=driver.findElement(By.id("Email"));
        txtEmail.clear();
        txtEmail.sendKeys(user);

        WebElement txtPassword=driver.findElement(By.id("Password"));
        txtPassword.clear();
        txtPassword.sendKeys(pwd);

        //driver.findElement(By.xpath("//input[@value='Log in']")).click();
        driver.findElement(By.xpath("/html/body/div[6]/div/div/div/div/div[2]/div[1]/div/form/div[3]/button\n")).click();

        String exp_title="Dashboard / nopCommerce administration";
        String act_title=driver.getTitle();
        System.out.println(act_title);

    if(exp.equals("Valid")){
        if(exp_title.equals(act_title)){
            driver.findElement(By.linkText("logout")).click();
            Assert.assertTrue(true);
        }else {
            Assert.assertTrue(false);
        }
    }
    else if(exp.equals("Invalid")){
        if(exp_title.equals(act_title)){
            driver.findElement(By.linkText("logout")).click();
            Assert.assertTrue(false);
        }else {
            Assert.assertTrue(true);
        }
    }
    }
    @DataProvider(name="LoginData")
    public Object[][] getData() throws IOException {
    String path="C:\\Users\\Rezia Parvin\\IdeaProjects\\DataDrivenFramework\\testData\\loginData.xlsx";
    XLUtility xlutil=new XLUtility(path);

    int totalrows=xlutil.getRowCount("Sheet1");
    int totalcols=xlutil.getCellCount("Sheet1",1);

    String loginData[][]=new String[totalrows][totalcols];
       for (int i=1;i<=totalrows;i++){
           for(int j=0;j<totalcols;j++){
    loginData[i-1][j]=xlutil.getCellData("Sheet1",i,j);
           }
       }
    return loginData;
    }

    @AfterClass
    void tearDown(){
        driver.close();
    }
}
