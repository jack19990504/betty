package com.activity.util.line;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

public class MessageUtil {

	private String accessToken = "lYGAFdtFadLWbdWs/UBYpwljVega08Zk5FHDwQ3pBB6FQdvx4ROdDJn8MaITMWC808NcdI2G6vTdgL2SKGBuiZdajo3jJj6JuOJCNQ0sViX6fhLWriWH8UMxyMRf2sYBejlf9uy3utOG1zXAD+S13wdB04t89/1O/w1cDnyilFU=";
	
	public void sendResponseMessages(String replyToken, String message) {
		try {
			message = "{\"replyToken\":\"" + replyToken + "\",\"messages\":[{\"type\":\"text\",\"text\":\""
					+ message + "\"}]}"; // 回傳的json格式訊息
			URL myurl = new URL("https://api.line.me/v2/bot/message/reply"); // 回傳的網址
			HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection(); // 使用HttpsURLConnection建立https連線
			con.setRequestMethod("POST");// 設定post方法
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // 設定Content-Type為json
			con.setRequestProperty("Authorization", "Bearer " + accessToken); // 設定Authorization
			con.setDoOutput(true);
			con.setDoInput(true);
			System.out.println(message);
			DataOutputStream output = new DataOutputStream(con.getOutputStream()); // 開啟HttpsURLConnection的連線
			output.write(message.getBytes(Charset.forName("utf-8"))); // 回傳訊息編碼為utf-8
			output.close();
			System.out.println("Resp Code:" + con.getResponseCode() + "; Resp Message:" + con.getResponseMessage()); // 顯示回傳的結果，若code為200代表回傳成功
		} catch (MalformedURLException e) {
			System.out.println("1Message: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void typeTemplate(String replyToken)
	{
		try {
			String message = "{\"replyToken\":\"" + replyToken + "\","+"\"messages\" : [" +
					"{\"type\":\"imagemap\","+
					"\"baseUrl\":\"https://i.imgur.com/U3Vg6OJ.png/1040\","+
					"\"altText\":\"請至Line查看圖片選單\","+
					"\"baseSize\":{"+
					"\"height\":1040,"+
					"\"width\":1040"+
					"},"+
					"\"actions\":["
					+"{"+
					"\"type\":\"message\","+
					"\"text\":\"學習活動\","+
					"\"area\":{"+
					"\"x\":0,"+
					"\"y\":0,"+
					"\"width\":346,"+
					"\"height\":346"+
					"}},"
					+"{"+
					"\"type\":\"message\","+
					"\"text\":\"藝文活動\","+
					"\"area\":{"+
					"\"x\":346,"+
					"\"y\":0,"+
					"\"width\":346,"+
					"\"height\":346"+
					"}},"
					+"{"+
					"\"type\":\"message\","+
					"\"text\":\"親子活動\","+
					"\"area\":{"+
					"\"x\":693,"+
					"\"y\":0,"+
					"\"width\":346,"+
					"\"height\":346"+
					"}},"
					+"{"+
					"\"type\":\"message\","+
					"\"text\":\"體驗活動\","+
					"\"area\":{"+
					"\"x\":0,"+
					"\"y\":346,"+
					"\"width\":346,"+
					"\"height\":346"+
					"}},"
					+"{"+
					"\"type\":\"message\","+
					"\"text\":\"休閒活動\","+
					"\"area\":{"+
					"\"x\":346,"+
					"\"y\":346,"+
					"\"width\":346,"+
					"\"height\":346"+
					"}},"
					+"{"+
					"\"type\":\"message\","+
					"\"text\":\"運動活動\","+
					"\"area\":{"+
					"\"x\":693,"+
					"\"y\":346,"+
					"\"width\":346,"+
					"\"height\":346"+
					"}},"
					
					+"{"+
					"\"type\":\"message\","+
					"\"text\":\"戶外活動\","+
					"\"area\":{"+
					"\"x\":0,"+
					"\"y\":693,"+
					"\"width\":346,"+
					"\"height\":346"+
					"}},"
					
					+"{"+
					"\"type\":\"message\","+
					"\"text\":\"講座活動\","+
					"\"area\":{"+
					"\"x\":346,"+
					"\"y\":693,"+
					"\"width\":346,"+
					"\"height\":346"+
					"}},"
					
					+"{"+
					"\"type\":\"message\","+
					"\"text\":\"資訊活動\","+
					"\"area\":{"+
					"\"x\":693,"+
					"\"y\":693,"+
					"\"width\":346,"+
					"\"height\":346"+
					"}}"
					
					+"]}]}";

			URL myurl = new URL("https://api.line.me/v2/bot/message/reply"); // 回傳的網址
			HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection(); // 使用HttpsURLConnection建立https連線
			con.setRequestMethod("POST");// 設定post方法
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // 設定Content-Type為json
			con.setRequestProperty("Authorization", "Bearer " + accessToken); // 設定Authorization
			con.setDoOutput(true);
			con.setDoInput(true);
			System.out.println(message);
			DataOutputStream output = new DataOutputStream(con.getOutputStream()); // 開啟HttpsURLConnection的連線
			output.write(message.getBytes(Charset.forName("utf-8"))); // 回傳訊息編碼為utf-8
			output.close();
			System.out.println("Resp Code:" + con.getResponseCode() + "; Resp Message:" + con.getResponseMessage()); // 顯示回傳的結果，若code為200代表回傳成功
		} catch (MalformedURLException e) {
			System.out.println("1Message: " + e.getMessage());
			e.printStackTrace();
	} catch (IOException e) {
		System.out.println("Message: " + e.getMessage());
		e.printStackTrace();
}
		
	}
		
	public void sendResponseMessages(String replyToken, String[] messages) {
		try {
			String message_head = "{\"replyToken\":\"" + replyToken + "\",\"messages\":[";
			for(String a :messages)
			{
				message_head = message_head+"{\"type\":\"text\",\"text\":\""+ a + "\"},";
			}
			String message = message_head.substring(0, message_head.length()-1)+"]}";
			System.out.println(message);
			 // 回傳的json格式訊息
			URL myurl = new URL("https://api.line.me/v2/bot/message/reply"); // 回傳的網址
			HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection(); // 使用HttpsURLConnection建立https連線
			con.setRequestMethod("POST");// 設定post方法
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // 設定Content-Type為json
			con.setRequestProperty("Authorization", "Bearer " + accessToken); // 設定Authorization
			con.setDoOutput(true);
			con.setDoInput(true);
			DataOutputStream output = new DataOutputStream(con.getOutputStream()); // 開啟HttpsURLConnection的連線
			output.write(message.getBytes(Charset.forName("utf-8"))); // 回傳訊息編碼為utf-8
			output.close();
			System.out.println("Resp Code:" + con.getResponseCode() + "; Resp Message:" + con.getResponseMessage()); // 顯示回傳的結果，若code為200代表回傳成功
		} catch (MalformedURLException e) {
			System.out.println("1Message: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	//to one user
	public void sendPostMessages( String[] messages,String UserId) {
		try {
			String message_head = "{\"to\":\"" + UserId + "\",\"messages\":[";
			for(String a :messages)
			{
				message_head = message_head+"{\"type\":\"text\",\"text\":\""+ a + "\"},";
			}
			String message = message_head.substring(0, message_head.length()-1)+"]}";
			System.out.println(message);
			 // 回傳的json格式訊息
			URL myurl = new URL("https://api.line.me/v2/bot/message/push"); // 回傳的網址
			HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection(); // 使用HttpsURLConnection建立https連線
			con.setRequestMethod("POST");// 設定post方法
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // 設定Content-Type為json
			con.setRequestProperty("Authorization", "Bearer " + accessToken); // 設定Authorization
			con.setDoOutput(true);
			con.setDoInput(true);
			DataOutputStream output = new DataOutputStream(con.getOutputStream()); // 開啟HttpsURLConnection的連線
			output.write(message.getBytes(Charset.forName("utf-8"))); // 回傳訊息編碼為utf-8
			output.close();
			System.out.println("Resp Code:" + con.getResponseCode() + "; Resp Message:" + con.getResponseMessage()); // 顯示回傳的結果，若code為200代表回傳成功
		} catch (MalformedURLException e) {
			System.out.println("1Message: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void sendPostMessage( String messages,String UserId) {
		try {
			String message_head = "{\"to\":\"" + UserId + "\",\"messages\":[";
			message_head = message_head+"{\"type\":\"text\",\"text\":\""+ messages + "\"},";
			
			String message = message_head.substring(0, message_head.length()-1)+"]}";
			System.out.println(message);
			 // 回傳的json格式訊息
			URL myurl = new URL("https://api.line.me/v2/bot/message/push"); // 回傳的網址
			HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection(); // 使用HttpsURLConnection建立https連線
			con.setRequestMethod("POST");// 設定post方法
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // 設定Content-Type為json
			con.setRequestProperty("Authorization", "Bearer " + accessToken); // 設定Authorization
			con.setDoOutput(true);
			con.setDoInput(true);
			DataOutputStream output = new DataOutputStream(con.getOutputStream()); // 開啟HttpsURLConnection的連線
			output.write(message.getBytes(Charset.forName("utf-8"))); // 回傳訊息編碼為utf-8
			output.close();
			System.out.println("Resp Code:" + con.getResponseCode() + "; Resp Message:" + con.getResponseMessage()); // 顯示回傳的結果，若code為200代表回傳成功
		} catch (MalformedURLException e) {
			System.out.println("1Message: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void sendPostMessagesToMutiPerson( String[] messages,String[] UserId) {
		try {
			String message_head = "{\"to\":[";
			for (String userId : UserId) {
				message_head = message_head + "\"" + userId + "\",";
			}
			
			message_head = message_head.substring(0, message_head.length()-1)+"]";
			message_head = message_head+",\"messages\":[";
			for(String a :messages)
			{
				message_head = message_head+"{\"type\":\"text\",\"text\":\""+ a + "\"},";
			}
			
			
			String message = message_head.substring(0, message_head.length()-1)+"]}";
			System.out.println(message);
			 // 回傳的json格式訊息
			URL myurl = new URL("https://api.line.me/v2/bot/message/multicast"); // 回傳的網址
			HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection(); // 使用HttpsURLConnection建立https連線
			con.setRequestMethod("POST");// 設定post方法
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // 設定Content-Type為json
			con.setRequestProperty("Authorization", "Bearer " + accessToken); // 設定Authorization
			con.setDoOutput(true);
			con.setDoInput(true);
			DataOutputStream output = new DataOutputStream(con.getOutputStream()); // 開啟HttpsURLConnection的連線
			output.write(message.getBytes(Charset.forName("utf-8"))); // 回傳訊息編碼為utf-8
			output.close();
			System.out.println("Resp Code:" + con.getResponseCode() + "; Resp Message:" + con.getResponseMessage()); // 顯示回傳的結果，若code為200代表回傳成功
		} catch (MalformedURLException e) {
			System.out.println("1Message: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
