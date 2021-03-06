package com.bookerapi.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bookerapi.base.TestBase;
import com.bookerapi.util.MyRetry;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
//@Listeners(com.stockaccounting.listeners.Listeners.class)
public class TC002_Get_Booking_By_Id extends TestBase{
	
	String id;
	public TC002_Get_Booking_By_Id() {
		RestAssured.baseURI = prop.getProperty("baseURI");
		RequestSpecification httpRequest1 = RestAssured.given();
		Response response1 = httpRequest1.accept("application/json")
							  			.get("/booking");
		id = response1.getBody().jsonPath().getString("[0].bookingid");
	}
	
	@BeforeClass
	public void getBookById() throws InterruptedException {
		log.info("**********Starting TC002_Get_All_Booking_Ids***********");
		RestAssured.baseURI = prop.getProperty("baseURI");
		httpRequest = RestAssured.given();
		response = httpRequest.accept("application/json")
							  .get("/booking/"+id+"");
		Thread.sleep(5000);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkResponseBody_TC002() {
		ResponseBody body = response.getBody();
		log.info("Response Body "+body.jsonPath().prettify());
		String firstName = body.asString();
		Assert.assertEquals(firstName.contains("firstname"),true);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusCode_TC002() {
		int statusCode = response.getStatusCode();
		log.info("Status Code "+statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	private void checkResponseTime_TC002() {
		long time = response.getTime();
		if(time>2000)
			log.warn("Response Time is greater than 2000 "+time);
		Assert.assertTrue(time<2000);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusLine_TC002() {
		String statusLine = response.getStatusLine();
		log.info("Status Line "+statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContententType_TC002() {
		String contentType = response.contentType();
		log.info("Content Type "+contentType);
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentLength_TC002() {
		String contentLength = response.header("Content-Length");
		log.info("Content Leanth "+contentLength);
		Assert.assertTrue(Integer.parseInt(contentLength)<1500);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void tearDown() {
		log.info("**********Finished TC002***********");
	}
}
