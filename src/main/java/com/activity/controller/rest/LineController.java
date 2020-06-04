package com.activity.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.activity.dao.ActivityDAO;
import com.activity.dao.MemberDAO;
import com.activity.dao.RegistrationDAO;
import com.activity.engine.util.AttributeCheck;
import com.activity.entity.Activity;
import com.activity.entity.Member;
import com.activity.entity.Registration;
import com.activity.util.line.Event;
import com.activity.util.line.EventWrapper;
import com.activity.util.line.MessageUtil;

@CrossOrigin("*") 
@Path("/line")
@RestController
public class LineController {
	private String accessToken = "lYGAFdtFadLWbdWs/UBYpwljVega08Zk5FHDwQ3pBB6FQdvx4ROdDJn8MaITMWC808NcdI2G6vTdgL2SKGBuiZdajo3jJj6JuOJCNQ0sViX6fhLWriWH8UMxyMRf2sYBejlf9uy3utOG1zXAD+S13wdB04t89/1O/w1cDnyilFU=";
	private String[] UserIDs = {
			"U848d0fb8269d111a96875ae3cb365ba6",
			};
//	private String[] UserIDs = {
//			"U1b77037d358bc652c9b0c0fdf2868cdb",
//			"U1c64ec423c085bed19adc5d176e0bdaf",
//			"U848d0fb8269d111a96875ae3cb365ba6",
//			"Ubcdf942fb35591a3c29c5f1c763cc0a8",
//			"Uedb45f1ab77ed45363238641d987b33b",
//			"Ufdfd00bdb0fcd6694ff944f19ae876a7"};
	
	/*
	1	學習📚
	2	藝文🎼
	3	親子👨‍👩‍👧‍👦
	4	體驗💆🏻
	5	休閒🏖
	6	運動🚴🏻
	7	戶外🏔
	8	講座💼🎤？
	9	資訊🖥
	地點 📍
	*/
	Map<String, String> map = Map.of("學術","📚","藝文","🎼","親子","👶🏻","體驗"
			,"💆","休閒","🏖","運動","🚴","戶外","🏔","講座","💼","資訊","🖥");
	
	private ArrayList<String> bindUserId = new ArrayList<>();
	private ArrayList<String> resetUserId = new ArrayList<>();
	final AttributeCheck attributeCheck = new AttributeCheck();	
	final MessageUtil messageUtil = new MessageUtil();
	
	@Autowired
	ActivityDAO activityDAO;
	
	@Autowired
	RegistrationDAO registerDAO;
	
	@Autowired
	MemberDAO memberDAO;
	


	
	//提醒所有參加者
	@Path("/postMessage/{activityId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void sendRemindMessages(@PathParam("activityId") Integer id)
	{
		List<Registration> registrationList = registerDAO.getListWithMemberInformation(id);
		final AttributeCheck attributeCheck = new AttributeCheck();
		Activity activity = new Activity();
		activity.setActivityId(id);
		activity = activityDAO.get(activity);
		
		String startDate = activity.getActivityStartDateString(); 
		
		String message = "提醒您，您所報名的活動 : \\n🔍" + activity.getActivityName() + " 即將在\\n " + startDate + " 開始";
		String message2 = "活動地點為:\\n📍" + activity.getActivitySpace();
		
		StringBuilder sb = new StringBuilder();
		
		for(Registration r : registrationList)
		{
			String memLineId = r.getMember().getMemberLineId();
			if(attributeCheck.stringsNotNull(memLineId))	
			{
				sb.append(memLineId);
				sb.append(",");
			}
		}
		String idString = sb.substring(0,sb.length()-1);
		if(sb.length() == 0)
		{
			System.out.println("no paticipate!");
		}
		else
		{
			String[] lineIds = idString.split(",");
			
			System.out.println(message);
			System.out.println(idString);		
			messageUtil.sendPostMessagesToMutiPerson(new String[]{message,message2},lineIds);
		}
		
	}
	//提醒單一參加者
	
	@Path("/postMessage/one")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void sendRemindMessagesToOnePerson(Registration registration)
	{
		
		final AttributeCheck attributeCheck = new AttributeCheck();
		Activity activity = new Activity();
		activity.setActivityId(registration.getActivity_Id());
		activity = activityDAO.get(activity);
		
		String startDate = activity.getActivityStartDateString(); 
		
		String message = "提醒您，您所報名的活動 : \\n🔍" + activity.getActivityName() + " 即將在\\n " + startDate + " 開始";
		String message2 = "活動地點為:\\n📍" + activity.getActivitySpace();
		
		
				
		messageUtil.sendPostMessages(new String[]{message,message2},registration.getMember().getMemberLineId());
		
		
	}
	
	@POST
	@Path("/postMessage/announcement/{activityId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendAnyMessage(@PathParam("activityId") Integer id,String[] messages) {
		List<Registration> registrationList = registerDAO.getListWithMemberInformation(id);
		final AttributeCheck attributeCheck = new AttributeCheck();
		//System.out.println(message);
		String message1 = messages[0];
		String message2 = messages[1];
		message1 = message1.replaceAll("\"", "");
		message2 = message2.replaceAll("\"", "");
		StringBuilder sb = new StringBuilder();
		
		for(Registration r : registrationList)
		{
			//String memLineId = r.getMember().getMemberLineId();
			if(attributeCheck.stringsNotNull(r.getMember().getMemberLineId()))
			{
				String memLineId = r.getMember().getMemberLineId();
				sb.append(memLineId);
				sb.append(",");
			}
		}
		if(sb.length() == 0)
		{
			System.out.println("no particate!");
		}
		else
		{
			String idString = sb.substring(0,sb.length()-1);
			String[] lineIds = idString.split(",");
			messageUtil.sendPostMessagesToMutiPerson(new String[] {message1+"\\n"+message2,"詳細資訊請上官方網站查詢"},lineIds);
		}
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response callback( EventWrapper events) {
		
		for (Event event : events.getEvents()) {
			switch (event.getType()) {
			case "message": // 當event為message時進入此case執行，其他event(如follow、unfollow、leave等)的case在此省略，您可自己增加
				System.out.print("This is a message event!\n");
				switch (event.getMessage().getType()) {
				case "text": // 當message type為text時，進入此case執行，目前子是將使用者傳來的文字訊息在其前加上echo字串後，回傳給使用者
					
					if(event.getMessage().getText().equals("綁定帳號"))
					{
						if(resetUserId.contains(event.getSource().getUserId()))
						{
							messageUtil.sendResponseMessages(event.getReplyToken(),"重置失敗，請按照格式輸入!\\n請再次點選「重置綁定」並重新輸入!");
							resetUserId.remove(event.getSource().getUserId());
						}
						else
						{
							messageUtil.sendResponseMessages(event.getReplyToken(),"請輸入帳號密碼!\\n如範例⬇️\\n帳號:\\n密碼:");
							if(!bindUserId.contains(event.getSource().getUserId())){
								bindUserId.add(event.getSource().getUserId());
							}
						}
					}
					else if(event.getMessage().getText().equals("重置綁定"))
					{
						if(bindUserId.contains(event.getSource().getUserId()))
						{
							messageUtil.sendResponseMessages(event.getReplyToken(),"綁定失敗，請按照格式輸入!\\n請再次點選「綁定帳號」並重新輸入!");
							bindUserId.remove(event.getSource().getUserId());
						}
						else
						{
							messageUtil.sendResponseMessages(event.getReplyToken(),"請輸入欲重置的帳號密碼!\\n如範例⬇️\\n帳號:\\n密碼:");
							if(!resetUserId.contains(event.getSource().getUserId())){
								resetUserId.add(event.getSource().getUserId());
							}
						
						}
					}
					
					else if(resetUserId.contains(event.getSource().getUserId()))
					{
						bindUserId.remove(event.getSource().getUserId());
						resetUserId.remove(event.getSource().getUserId());
						String init  = event.getMessage().getText();
						if(!init.startsWith("帳號"))
						{
							
							messageUtil.sendResponseMessages(event.getReplyToken(), "重置失敗，請按照格式輸入!\\n請再次點選「重置綁定」並重新輸入!");
						}
						else
						{
							String datas[]  = init.split("\n");
							if(datas[0].length() < 4 || datas[1].length() < 4)
							{
								messageUtil.sendResponseMessages(event.getReplyToken(), "重置失敗，請按照格式輸入!\\n請再次點選「重置綁定」並重新輸入!");
							}
							else
							{
								String account = datas[0].substring(3).trim();
								String password = datas[1].substring(3).trim();
						
								Member member = new Member();
								member.setMemberEmail(account);
								member = memberDAO.get(member);
								//無此筆帳號
								if(member.getMemberPassword() == null || !member.getMemberPassword().equals(password))
								{
							
									messageUtil.sendResponseMessages(event.getReplyToken(), "重置失敗，帳號或密碼輸入錯誤!\\n請再次點選「重置綁定」並重新輸入!");
								}
								else 
								{
									if(member.getMemberLineId() == null)
									{
										messageUtil.sendResponseMessages(event.getReplyToken(), "重置失敗，此帳號尚無綁定過!");
								
									}
							
									else if(member.getMemberLineId().equals(""))
									{
										messageUtil.sendResponseMessages(event.getReplyToken(), "重置失敗，此帳號已重置過!\\n請再次點選「重置綁定」並重新輸入!");
								
									}
							
									else if(!member.getMemberLineId().equals(event.getSource().getUserId()))
									{
										messageUtil.sendResponseMessages(event.getReplyToken(), "重置失敗，您綁定的不是此帳號!\\n請再次點選「重置綁定」並重新輸入!");
									}
							
									else 
									{
										memberDAO.resetLineUserId(account);
										messageUtil.sendResponseMessages(event.getReplyToken(), "重置成功!");
								
									}
								}
							}
						
						}
						
						
						
					}
					
					else if(bindUserId.contains(event.getSource().getUserId()))
					{
						resetUserId.remove(event.getSource().getUserId());
						bindUserId.remove(event.getSource().getUserId());
						String init  = event.getMessage().getText();
						if(!init.startsWith("帳號"))
						{
							messageUtil.sendResponseMessages(event.getReplyToken(), "綁定失敗，請按照格式輸入!\\n請再次點選「重置綁定」並重新輸入!");
						}
						else
						{
							String datas[]  = init.split("\n");
							if(datas[0].length() < 4 || datas[1].length() < 4)
							{
								messageUtil.sendResponseMessages(event.getReplyToken(), "綁定失敗，請按照格式輸入!\\n請再次點選「重置綁定」並重新輸入!");
							}
							else
							{
								String account = datas[0].substring(3).trim();
								String password = datas[1].substring(3).trim();
								Member member = new Member();
								member.setMemberEmail(account);
								member = memberDAO.get(member);
								
								//無此筆帳號
								if(member.getMemberPassword() == null || !member.getMemberPassword().equals(password))
								{
									messageUtil.sendResponseMessages(event.getReplyToken(), "綁定失敗，帳號或密碼輸入錯誤!\\n請再次點選「重置綁定」並重新輸入!");
								}
								else
								{
									//如未登錄過LineId
									//
									if(member.getMemberLineId() == null  || member.getMemberLineId().equals(""))
									{
										//測試此LINE用戶是否已綁定過其他帳號
										Member test = new Member();
										test.setMemberLineId(event.getSource().getUserId());
										test = memberDAO.check(test);
										//如已綁定過
										if(test.getMemberEmail()!= null)
										{
											messageUtil.sendResponseMessages(event.getReplyToken(), "綁定失敗，此Line帳號已綁定其他用戶帳號!");
										}
										//如未綁定
										else
										{
											memberDAO.UpdateLineUserId(event.getSource().getUserId(), account, password);
											messageUtil.sendResponseMessages(event.getReplyToken(), "綁定成功!");
										}
										
									}
									
									else 
									{
										System.out.println(!member.getMemberLineId().equals(""));
										System.out.println(!member.getMemberLineId().equals(null));
										
										messageUtil.sendResponseMessages(event.getReplyToken(), "此用戶帳號已綁定過!");
									}
									
									
									
								}
							}
						}
									
					}
					
					
					else if (event.getMessage().getText().equals("可報名活動")) {
						resetUserId.remove(event.getSource().getUserId());
						bindUserId.remove(event.getSource().getUserId());
						List<Activity> activityList = activityDAO.getActivityNames();
						StringBuilder sb = new StringBuilder();
						
						
						sb.append("目前可報名的活動有 :"+"\\n");
						for (Activity activity : activityList) {
							
							activityDAO.getActivityTypes(activity);
							
							for(String a : activity.getActivityTypes())
							{
								System.out.println(a);
								if(!attributeCheck.stringsNotNull(a))
									continue;
								else
								{
									sb.append(map.get(a));
									break;
								}
								
							}
							
							sb.append(activity.getActivityName()+"\\n"+"活動開始時間為:\\n"+activity.getActivityStartDateString() + "\\n");
			
						}
						
						System.out.println(event.getSource().getUserId());
						
						//sendResponseMessages(event.getReplyToken(),message);
						String output [] = {sb.substring(0,sb.length()-2),"詳細活動資訊請上官方網站查詢"};
						messageUtil.sendResponseMessages(event.getReplyToken(),output);
						
					} 
//					else if (event.getMessage().getText().equals("選單"))
//					{
//						 ActivityButtonTemplate(event.getReplyToken());
//					}
					else if(event.getMessage().getText().equals("已報名活動"))
					{
						resetUserId.remove(event.getSource().getUserId());
						bindUserId.remove(event.getSource().getUserId());
						List<Registration> Registration = registerDAO.getUserRegistration(event.getSource().getUserId());
						if(Registration.isEmpty())
						{
							System.out.println(event.getSource().getUserId());
							messageUtil.sendResponseMessages(event.getReplyToken(),"您沒有已報名的活動或尚未綁定帳號!");
						}
						else
						{
							StringBuilder sb = new StringBuilder();
							sb.append("您所參加的活動有:\\n");
						for(Registration r  : Registration)
						{
							sb.append("🔍"+ r.getActivity().getActivityName() +  "\\n");
						}
						String [] output = {sb.substring(0,sb.length()-2),"已參加活動之詳細資訊請至官方網站查看"};
						messageUtil.sendResponseMessages(event.getReplyToken(),output);
						}
						
					}
//					else if(event.getMessage().getText().equals("發送訊息"))
//					{
//						sendPostMessagesToMutiPerson("記得做專題ㄛ",UserIDs);
//					}
					else if(event.getMessage().getText().equals("類型選單")) 
					{
						resetUserId.remove(event.getSource().getUserId());
						bindUserId.remove(event.getSource().getUserId());
						messageUtil.typeTemplate(event.getReplyToken());
					}
//					else if (event.getMessage().getText().equals("圖片選單"))
//					{
//						imageTemplate(event.getReplyToken());
//					}
					
					
					else if(event.getMessage().getText().endsWith("活動") && !event.getMessage().getText().equals("可報名活動") && !event.getMessage().getText().equals("已報名活動"))
					{
						resetUserId.remove(event.getSource().getUserId());
						bindUserId.remove(event.getSource().getUserId());
						final String type = event.getMessage().getText().substring(0,event.getMessage().getText().length()-2);
						List<Activity> aList = new ArrayList<Activity>();
						aList = activityDAO.getListByType(type);
						if(aList.size() > 0)
						{
							String message = type + "類型的活動如下 : \\n";
							for(Activity a : aList)
							{
								message = message + a.getActivityName() + ", ";
							}
							message = message.substring(0, message.length()-2);
							messageUtil.sendResponseMessages(event.getReplyToken(), message);
						}
						else
						{
							String error = "查詢不到任何有關" + type + "的活動";
							messageUtil.sendResponseMessages(event.getReplyToken(), error);
						}
					}
					else
						messageUtil.sendResponseMessages(event.getReplyToken(), event.getMessage().getText());
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



}
