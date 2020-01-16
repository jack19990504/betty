package com.activity.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.activity.dao.ActivityDAO;
import com.activity.dao.MemberDAO;
import com.activity.dao.RegistrationDAO;
import com.activity.entity.Activity;
import com.activity.entity.Member;
import com.activity.entity.Registration;
import com.activity.util.line.Event;
import com.activity.util.line.EventWrapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/line")
@RestController
public class LineController {
	private String accessToken = "lYGAFdtFadLWbdWs/UBYpwljVega08Zk5FHDwQ3pBB6FQdvx4ROdDJn8MaITMWC808NcdI2G6vTdgL2SKGBuiZdajo3jJj6JuOJCNQ0sViX6fhLWriWH8UMxyMRf2sYBejlf9uy3utOG1zXAD+S13wdB04t89/1O/w1cDnyilFU=";
	private String[] UserIDs = {
			"U1b77037d358bc652c9b0c0fdf2868cdb",
			"U1c64ec423c085bed19adc5d176e0bdaf",
			"U848d0fb8269d111a96875ae3cb365ba6",
			"Ubcdf942fb35591a3c29c5f1c763cc0a8",
			"Uedb45f1ab77ed45363238641d987b33b",
			"Ufdfd00bdb0fcd6694ff944f19ae876a7"};
	@Autowired
	ActivityDAO activityDAO;
	
	private boolean saveLineUserId = false;
	private boolean resetLineUserId = false;
	
	@Autowired
	RegistrationDAO registerDAO;
	
	@Autowired
	MemberDAO memberDAO;

	@Path("/test")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void Send() {
		sendPostMessagesToMutiPerson("測試",UserIDs);
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response callback(@RequestBody EventWrapper events) {
		List<Activity> activityList = activityDAO.getList();
		for (Event event : events.getEvents()) {
			switch (event.getType()) {
			case "message": // 當event為message時進入此case執行，其他event(如follow、unfollow、leave等)的case在此省略，您可自己增加
				System.out.print("This is a message event!\n");
				switch (event.getMessage().getType()) {
				case "text": // 當message type為text時，進入此case執行，目前子是將使用者傳來的文字訊息在其前加上echo字串後，回傳給使用者
					if (event.getMessage().getText().equals("可報名活動")) {
						String n = "目前可報名的活動有 :"+"\\n";
						String[] list = new String[activityList.size()];
						int i = 0;
						for (Activity activity : activityList) {
							n  = n + activity.getActivityName()+"、";
							list[i] = activity.getActivityName();
							i++;
							System.out.println(activity.getActivityName()+"\n"+event.getReplyToken());
							System.out.println(event.getSource().getUserId());
							//sendPostMessages(activity.getActivityName(),event.getSource().getUserId());
						}
						n = n.substring(0,n.length()-1);
						System.out.println(n);
						sendResponseMessages(event.getReplyToken(),n);
						//sendResponseMessages(event.getReplyToken(),list);
					} 
					else if (event.getMessage().getText().equals("選單"))
					{
						 ActivityButtonTemplate(event.getReplyToken());
					}
					else if(event.getMessage().getText().equals("查詢已報名活動"))
					{
						List<Registration> Registration = registerDAO.getUserRegistration(event.getSource().getUserId());
						String message = "您所參加的活動有:\\n";
						for(Registration r : Registration)
						{
							message = message + r.getActivityName() +  "、";
						}
						message = message.substring(0,message.length()-1);
						sendResponseMessages(event.getReplyToken(),message);
						if(Registration.isEmpty())
						{
							sendResponseMessages(event.getReplyToken(),"您沒有已報名的活動或尚未綁定帳號!");
						}
						
					}
//					else if(event.getMessage().getText().equals("發送訊息"))
//					{
//						sendPostMessagesToMutiPerson("記得做專題ㄛ",UserIDs);
//					}
					else if (event.getMessage().getText().equals("圖片選單"))
					{
						imageTemplate(event.getReplyToken());
					}
					else if(event.getMessage().getText().equals("綁定帳號"))
					{
						sendResponseMessages(event.getReplyToken(),"請輸入帳號密碼!\\n如範例:\\n帳號:\\n密碼:");
						saveLineUserId = true ;
						resetLineUserId = false;
					}
					else if(event.getMessage().getText().equals("重置綁定帳號"))
					{
						sendResponseMessages(event.getReplyToken(),"請輸入欲重置的帳號密碼!\\n如範例:\\n帳號:\\n密碼:");
						resetLineUserId = true;
						saveLineUserId = false;
					}
					else if(resetLineUserId)
					{
						String init  = event.getMessage().getText();
						
						String datas[]  = init.split("\n");
						String account = datas[0].substring(3).trim();
						String password = datas[1].substring(3).trim();
						System.out.println(account);
						System.out.println(password);
						Member member = new Member();
						member.setMemberEmail(account);
						member = memberDAO.get(member);
						
						//無此筆帳號
						if(member.getMemberPassword() == null || !member.getMemberPassword().equals(password))
						{
							sendResponseMessages(event.getReplyToken(), "重置失敗，帳號或密碼輸入錯誤!\\n請重新輸入!");
						}
						else 
						{
							if(member.getMemberLineId() == null)
							{
								sendResponseMessages(event.getReplyToken(), "重置失敗，此帳號尚無綁定過!");
								resetLineUserId = false;
							}
							
							else if(member.getMemberLineId().equals(""))
							{
								sendResponseMessages(event.getReplyToken(), "重置失敗，此帳號已重置過!\\n請重新輸入!");
								resetLineUserId = false ;
							}
							
							else if(!member.getMemberLineId().equals(event.getSource().getUserId()))
							{
								sendResponseMessages(event.getReplyToken(), "重置失敗，您綁定的不是此帳號!\\n請重新輸入!");
							}
							
							else 
							{
								memberDAO.resetLineUserId(account);
								sendResponseMessages(event.getReplyToken(), "重置成功!");
								resetLineUserId = false;
							}
						}
					}
					
					else if(saveLineUserId)
					{
						
						String init  = event.getMessage().getText();
						
						String datas[]  = init.split("\n");
						String account = datas[0].substring(3).trim();
						String password = datas[1].substring(3).trim();
						System.out.println(account);
						System.out.println(password);
						Member member = new Member();
						member.setMemberEmail(account);
						member = memberDAO.get(member);
						
						System.out.println(member.getMemberEmail());
						System.out.println(member.getMemberPassword());
						
						
						//無此筆帳號
						if(member.getMemberPassword() == null || !member.getMemberPassword().equals(password))
						{
							sendResponseMessages(event.getReplyToken(), "綁定失敗，帳號或密碼輸入錯誤!\\n請重新輸入!");
						}
						else
						{
							//如未登錄過LineId
							
							if(member.getMemberLineId() == null)
							{
								//測試此LINE用戶是否已綁定過其他帳號
								Member test = new Member();
								test.setMemberLineId(event.getSource().getUserId());
								test = memberDAO.check(test);
								//如已綁定過
								if(test.getMemberEmail()!= null)
								{
									saveLineUserId =false;
									sendResponseMessages(event.getReplyToken(), "綁定失敗，此Line帳號已綁定其他用戶帳號!");
								}
								//如未綁定
								else
								{
									saveLineUserId =false;
									memberDAO.UpdateLineUserId(event.getSource().getUserId(), account, password);
									sendResponseMessages(event.getReplyToken(), "綁定成功!");
								}
								
							}
							else if(member.getMemberLineId().equals(""))
							{
								//測試此LINE用戶是否已綁定過其他帳號
								Member test = new Member();
								test.setMemberLineId(event.getSource().getUserId());
								test = memberDAO.check(test);
								System.out.println(test.getMemberEmail());
								System.out.println(test.getMemberLineId());
								//如已綁定過
								if(test.getMemberEmail()!= null)
								{
									saveLineUserId =false;
									sendResponseMessages(event.getReplyToken(), "綁定失敗，此Line帳號已綁定其他用戶帳號!");
								}
								//如未綁定
								else
								{
									saveLineUserId =false;
									memberDAO.UpdateLineUserId(event.getSource().getUserId(), account, password);
									sendResponseMessages(event.getReplyToken(), "綁定成功!");
								}
							}
							else 
							{
								System.out.println(!member.getMemberLineId().equals(""));
								System.out.println(!member.getMemberLineId().equals(null));
								saveLineUserId =false;
								sendResponseMessages(event.getReplyToken(), "此用戶帳號已綁定過!");
							}
							
							
							
						}
						
					}
					else
						sendResponseMessages(event.getReplyToken(), event.getMessage().getText());
					System.out.println("This is a text message. It's replytoken is " + event.getMessage().getText().toString());
					break;
				case "image":// 當message type為image時，進入此case執行，
					System.out.println("This is a image message. It's replytoken is " + event.getReplyToken());
					break;
				case "audio":// 當message type為audio時，進入此case執行，
					System.out.println("This is a audio message. It's replytoken is " + event.getReplyToken());
					break;
				case "video":// 當message type為video時，進入此case執行，
					System.out.println("This is a video message. It's replytoken is " + event.getReplyToken());
					break;
				case "file":// 當message type為file時，進入此case執行，
					System.out.println("This is a file message. It's replytoken is " + event.getReplyToken());
					break;
				case "sticker":// 當message type為sticker時，進入此case執行，
					System.out.println("This is a sticker message. It's replytoken is " + event.getReplyToken());
					break;
				case "location":// 當message type為location時，進入此case執行，
					System.out.println("This is a location message. It's replytoken is " + event.getReplyToken());
					break;
				}

				break;
			}
		}
		return Response.status(200).build();
	}

	private void sendResponseMessages(String replyToken, String message) {
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
	
	private void imageTemplate(String replyToken)
	{
		try {
				String message = "{\"replyToken\":\"" + replyToken + "\","+"\"messages\" : [" +
						"{\"type\":\"imagemap\","+
						"\"baseUrl\":\"https://i.imgur.com/vuzigAT.jpg/1040\","+
						"\"altText\":\"請至Line查看圖片選單\","+
						"\"baseSize\":{"+
						"\"height\":1040,"+
						"\"width\":1040"+
						"},"+
						"\"actions\":["
						+"{"+
						"\"type\":\"message\","+
						"\"text\":\"綁定帳號\","+
						"\"area\":{"+
						"\"x\":0,"+
						"\"y\":0,"+
						"\"width\":341,"+
						"\"height\":512"+
						"}},"
						+"{"+
						"\"type\":\"message\","+
						"\"text\":\"重置綁定帳號\","+
						"\"area\":{"+
						"\"x\":342,"+
						"\"y\":0,"+
						"\"width\":341,"+
						"\"height\":512"+
						"}},"
						+"{"+
						"\"type\":\"message\","+
						"\"text\":\"圖片選單\","+
						"\"area\":{"+
						"\"x\":683,"+
						"\"y\":0,"+
						"\"width\":341,"+
						"\"height\":512"+
						"}},"
						+"{"+
						"\"type\":\"message\","+
						"\"text\":\"選單\","+
						"\"area\":{"+
						"\"x\":0,"+
						"\"y\":512,"+
						"\"width\":341,"+
						"\"height\":512"+
						"}},"
						+"{"+
						"\"type\":\"message\","+
						"\"text\":\"可報名活動\","+
						"\"area\":{"+
						"\"x\":342,"+
						"\"y\":512,"+
						"\"width\":341,"+
						"\"height\":512"+
						"}},"
						+"{"+
						"\"type\":\"message\","+
						"\"text\":\"查詢已報名活動\","+
						"\"area\":{"+
						"\"x\":683,"+
						"\"y\":512,"+
						"\"width\":341,"+
						"\"height\":512"+
						"}}]}]}";

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
		
	
	
	private void ActivityButtonTemplate(String replyToken) {
		try {
			String message = "{\"replyToken\":\"" + replyToken + "\","+"\"messages\" : [" + 
					"{\"type\": \"template\",\"altText\": \"請至Line查看選單\"," + 
					"\"template\":{\"type\": \"buttons\",\"title\": \"選單\",\"text\": \"請選擇欲查詢之項目\",\"actions\": ["; // 回傳的json格式訊息
			
			String action1 = "{\"type\": \"message\",\"label\": \"可報名活動\",\"text\": \"可報名活動\"},";
			String action2 = "{\"type\": \"message\",\"label\": \"查詢已報名活動\",\"text\": \"查詢已報名活動\"},";
			
			message = message + action1 + action2;
			message = message.substring(0, message.length()-1)+"]}}]}";
			
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
	
	private void sendResponseMessages(String replyToken, String[] messages) {
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
	private void sendPostMessages( String[] messages,String UserId) {
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
	
	private void sendPostMessages( String messages,String UserId) {
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
	
	private void sendPostMessagesToMutiPerson( String messages,String[] UserId) {
		try {
			String message_head = "{\"to\":[";
			for (String userId : UserId) {
				message_head = message_head + "\"" + userId + "\",";
			}
			message_head = message_head.substring(0, message_head.length()-1)+"]";
			message_head = message_head+",\"messages\":[{\"type\":\"text\",\"text\":\""+ messages + "\"},";
			
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


