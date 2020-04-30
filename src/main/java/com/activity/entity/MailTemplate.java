package com.activity.entity;

public class MailTemplate {

	private String mail = 
			"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
			"\r\n" + 
			"<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\r\n" + 
			"<head>\r\n" + 
			"<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->\r\n" + 
			"<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\r\n" + 
			"<meta content=\"width=device-width\" name=\"viewport\"/>\r\n" + 
			"<!--[if !mso]><!-->\r\n" + 
			"<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"/>\r\n" + 
			"<!--<![endif]-->\r\n" + 
			"<title></title>\r\n" + 
			"<!--[if !mso]><!-->\r\n" + 
			"<!--<![endif]-->\r\n" + 
			"<style type=\"text/css\">\r\n" + 
			"		body {\r\n" + 
			"			margin: 0;\r\n" + 
			"			padding: 0;\r\n" + 
			"		}\r\n" + 
			"\r\n" + 
			"		table,\r\n" + 
			"		td,\r\n" + 
			"		tr {\r\n" + 
			"			vertical-align: top;\r\n" + 
			"			border-collapse: collapse;\r\n" + 
			"		}\r\n" + 
			"\r\n" + 
			"		* {\r\n" + 
			"			line-height: inherit;\r\n" + 
			"		}\r\n" + 
			"\r\n" + 
			"		a[x-apple-data-detectors=true] {\r\n" + 
			"			color: inherit !important;\r\n" + 
			"			text-decoration: none !important;\r\n" + 
			"		}\r\n" + 
			"	</style>\r\n" + 
			"<style id=\"media-query\" type=\"text/css\">\r\n" + 
			"		@media (max-width: 620px) {\r\n" + 
			"\r\n" + 
			"			.block-grid,\r\n" + 
			"			.col {\r\n" + 
			"				min-width: 320px !important;\r\n" + 
			"				max-width: 100% !important;\r\n" + 
			"				display: block !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.block-grid {\r\n" + 
			"				width: 100% !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.col {\r\n" + 
			"				width: 100% !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.col>div {\r\n" + 
			"				margin: 0 auto;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			img.fullwidth,\r\n" + 
			"			img.fullwidthOnMobile {\r\n" + 
			"				max-width: 100% !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.no-stack .col {\r\n" + 
			"				min-width: 0 !important;\r\n" + 
			"				display: table-cell !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.no-stack.two-up .col {\r\n" + 
			"				width: 50% !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.no-stack .col.num4 {\r\n" + 
			"				width: 33% !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.no-stack .col.num8 {\r\n" + 
			"				width: 66% !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.no-stack .col.num4 {\r\n" + 
			"				width: 33% !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.no-stack .col.num3 {\r\n" + 
			"				width: 25% !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.no-stack .col.num6 {\r\n" + 
			"				width: 50% !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.no-stack .col.num9 {\r\n" + 
			"				width: 75% !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.video-block {\r\n" + 
			"				max-width: none !important;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.mobile_hide {\r\n" + 
			"				min-height: 0px;\r\n" + 
			"				max-height: 0px;\r\n" + 
			"				max-width: 0px;\r\n" + 
			"				display: none;\r\n" + 
			"				overflow: hidden;\r\n" + 
			"				font-size: 0px;\r\n" + 
			"			}\r\n" + 
			"\r\n" + 
			"			.desktop_hide {\r\n" + 
			"				display: block !important;\r\n" + 
			"				max-height: none !important;\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"	</style>\r\n" + 
			"</head>\r\n" + 
			"<body class=\"clean-body\" style=\"margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #283C4B;\">\r\n" + 
			"<!--[if IE]><div class=\"ie-browser\"><![endif]-->\r\n" + 
			"<table bgcolor=\"#283C4B\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; min-width: 320px; Margin: 0 auto; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #283C4B; width: 100%;\" valign=\"top\" width=\"100%\">\r\n" + 
			"<tbody>\r\n" + 
			"<tr style=\"vertical-align: top;\" valign=\"top\">\r\n" + 
			"<td style=\"word-break: break-word; vertical-align: top;\" valign=\"top\">\r\n" + 
			"<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#283C4B\"><![endif]-->\r\n" + 
			"<div style=\"background-color:#283C4B;\">\r\n" + 
			"<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #283C4B;\">\r\n" + 
			"<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#283C4B;\">\r\n" + 
			"<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#283C4B;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#283C4B\"><![endif]-->\r\n" + 
			"<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:#283C4B;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\"><![endif]-->\r\n" + 
			"<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\r\n" + 
			"<div style=\"width:100% !important;\">\r\n" + 
			"<!--[if (!mso)&(!IE)]><!-->\r\n" + 
			"<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\r\n" + 
			"<!--<![endif]-->\r\n" + 
			"<div align=\"center\" class=\"img-container center autowidth\" style=\"padding-right: 25px;padding-left: 25px;\">\r\n" + 
			"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 25px;padding-left: 25px;\" align=\"center\"><![endif]-->\r\n" + 
			"<div style=\"font-size:1px;line-height:25px\"> </div><img align=\"center\" alt=\"Image\" border=\"0\" class=\"center autowidth\" src=\"https://i.imgur.com/GNnFcaz.png\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 100px; display: block;\" title=\"Image\" width=\"100\"/>\r\n" + 
			"<div style=\"font-size:1px;line-height:25px\"> </div>\r\n" + 
			"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
			"</div>\r\n" + 
			"<!--[if (!mso)&(!IE)]><!-->\r\n" + 
			"</div>\r\n" + 
			"<!--<![endif]-->\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + 
			"<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"<div style=\"background-color:#283C4B;\">\r\n" + 
			"<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #3AAEE0;\">\r\n" + 
			"<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#3AAEE0;\">\r\n" + 
			"<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#283C4B;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#3AAEE0\"><![endif]-->\r\n" + 
			"<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:#3AAEE0;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:0px;\"><![endif]-->\r\n" + 
			"<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\r\n" + 
			"<div style=\"width:100% !important;\">\r\n" + 
			"<!--[if (!mso)&(!IE)]><!-->\r\n" + 
			"<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\r\n" + 
			"<!--<![endif]-->\r\n" + 
			"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 20px; padding-left: 20px; padding-top: 30px; padding-bottom: 20px; font-family: Arial, sans-serif\"><![endif]-->\r\n" + 
			"<div style=\"color:#FFFFFF;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:30px;padding-right:20px;padding-bottom:20px;padding-left:20px;\">\r\n" + 
			"<div style=\"line-height: 1.2; font-size: 12px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; color: #FFFFFF; mso-line-height-alt: 14px;\">\r\n" + 
			"<p style=\"font-size: 24px; line-height: 1.2; text-align: center; font-family: lucida sans unicode, lucida grande, sans-serif; word-break: break-word; mso-line-height-alt: 29px; margin: 0;\"><span style=\"font-size: 24px;\">Welcome!</span></p>\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
			"<div align=\"center\" class=\"img-container center autowidth fullwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\r\n" + 
			"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]--><img align=\"center\" alt=\"Image\" border=\"0\" class=\"center autowidth fullwidth\" src=\"https://i.imgur.com/50sZzIb.jpg\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 600px; display: block;\" title=\"Image\" width=\"600\"/>\r\n" + 
			"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
			"</div>\r\n" + 
			"<!--[if (!mso)&(!IE)]><!-->\r\n" + 
			"</div>\r\n" + 
			"<!--<![endif]-->\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + 
			"<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"<div style=\"background-color:#283C4B;\">\r\n" + 
			"<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\r\n" + 
			"<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\r\n" + 
			"<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#283C4B;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\r\n" + 
			"<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:#FFFFFF;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:15px;\"><![endif]-->\r\n" + 
			"<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\r\n" + 
			"<div style=\"width:100% !important;\">\r\n" + 
			"<!--[if (!mso)&(!IE)]><!-->\r\n" + 
			"<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:0px; padding-bottom:15px; padding-right: 0px; padding-left: 0px;\">\r\n" + 
			"<!--<![endif]-->\r\n" + 
			"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\r\n" + 
			"<div style=\"color:#283C4B;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.5;padding-top:10px;padding-right:30px;padding-bottom:10px;padding-left:30px;\">\r\n" + 
			"<div style=\"line-height: 1.5; font-size: 12px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; color: #283C4B; mso-line-height-alt: 18px;\">\r\n" + 
			"<p style=\"font-size: 16px; line-height: 1.5; text-align: center; word-break: break-word; font-family: lucida sans unicode, lucida grande, sans-serif; mso-line-height-alt: 24px; margin: 0;\"><span style=\"font-size: 16px;\"><strong><span style=\"font-size: 16px;\">親愛的 [guest_name] 您好</span></strong></span></p>\r\n" + 
			"<p style=\"font-size: 12px; line-height: 1.5; text-align: center; word-break: break-word; font-family: lucida sans unicode, lucida grande, sans-serif; mso-line-height-alt: 18px; margin: 0;\"> </p>\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
			"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 30px; padding-left: 30px; padding-top: 10px; padding-bottom: 0px; font-family: Arial, sans-serif\"><![endif]-->\r\n" + 
			"<div style=\"color:#283C4B;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.5;padding-top:10px;padding-right:30px;padding-bottom:0px;padding-left:30px;\">\r\n" + 
			"<div style=\"line-height: 1.5; font-size: 12px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; color: #283C4B; mso-line-height-alt: 18px;\">\r\n" + 
			"<p style=\"font-size: 14px; line-height: 1.5; text-align: center; word-break: break-word; font-family: lucida sans unicode, lucida grande, sans-serif; mso-line-height-alt: 21px; margin: 0;\"><span style=\"font-size: 14px;\">您的新密碼為  :  [新密碼]</span></p>\r\n" + 
			"<p style=\"font-size: 14px; line-height: 1.5; text-align: center; word-break: break-word; font-family: lucida sans unicode, lucida grande, sans-serif; mso-line-height-alt: 21px; margin: 0;\"><span style=\"font-size: 14px;\">請盡速登入網站後更改密碼</span></p>\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"<!--[if mso]></td></tr></table><![endif]-->\r\n" + 
			"<div align=\"center\" class=\"button-container\" style=\"padding-top:25px;padding-right:0px;padding-bottom:0px;padding-left:0px;\">\r\n" + 
			"<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\"><tr><td style=\"padding-top: 25px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px\" align=\"center\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"http://localhost:3000/signin\" style=\"height:39pt; width:170.25pt; v-text-anchor:middle;\" arcsize=\"8%\" stroke=\"false\" fillcolor=\"#3AAEE0\"><w:anchorlock/><v:textbox inset=\"0,0,0,0\"><center style=\"color:#ffffff; font-family:Arial, sans-serif; font-size:16px\"><![endif]--><a href=\"http://localhost:3000/signin\" style=\"-webkit-text-size-adjust: none; text-decoration: none; display: inline-block; color: #ffffff; background-color: #3AAEE0; border-radius: 4px; -webkit-border-radius: 4px; -moz-border-radius: 4px; width: auto; width: auto; border-top: 1px solid #3AAEE0; border-right: 1px solid #3AAEE0; border-bottom: 1px solid #3AAEE0; border-left: 1px solid #3AAEE0; padding-top: 10px; padding-bottom: 10px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; text-align: center; mso-border-alt: none; word-break: keep-all;\" target=\"_blank\"><span style=\"padding-left:20px;padding-right:20px;font-size:16px;display:inline-block;\"><span style=\"font-size: 16px; line-height: 2; word-break: break-word; font-family: lucida sans unicode, lucida grande, sans-serif; mso-line-height-alt: 32px;\"> Go to our website</span></span></a>\r\n" + 
			"<!--[if mso]></center></v:textbox></v:roundrect></td></tr></table><![endif]-->\r\n" + 
			"</div>\r\n" + 
			"<!--[if (!mso)&(!IE)]><!-->\r\n" + 
			"</div>\r\n" + 
			"<!--<![endif]-->\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + 
			"<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"<div style=\"background-color:transparent;\">\r\n" + 
			"<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;\">\r\n" + 
			"<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\r\n" + 
			"<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\r\n" + 
			"<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:transparent;width:600px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\r\n" + 
			"<div class=\"col num12\" style=\"min-width: 320px; max-width: 600px; display: table-cell; vertical-align: top; width: 600px;\">\r\n" + 
			"<div style=\"width:100% !important;\">\r\n" + 
			"<!--[if (!mso)&(!IE)]><!-->\r\n" + 
			"<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\r\n" + 
			"<!--<![endif]-->\r\n" + 
			"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\r\n" + 
			"<tbody>\r\n" + 
			"<tr style=\"vertical-align: top;\" valign=\"top\">\r\n" + 
			"<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;\" valign=\"top\">\r\n" + 
			"<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 0px solid transparent; width: 100%;\" valign=\"top\" width=\"100%\">\r\n" + 
			"<tbody>\r\n" + 
			"<tr style=\"vertical-align: top;\" valign=\"top\">\r\n" + 
			"<td style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\r\n" + 
			"</tr>\r\n" + 
			"</tbody>\r\n" + 
			"</table>\r\n" + 
			"</td>\r\n" + 
			"</tr>\r\n" + 
			"</tbody>\r\n" + 
			"</table>\r\n" + 
			"<!--[if (!mso)&(!IE)]><!-->\r\n" + 
			"</div>\r\n" + 
			"<!--<![endif]-->\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + 
			"<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"</div>\r\n" + 
			"<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\r\n" + 
			"</td>\r\n" + 
			"</tr>\r\n" + 
			"</tbody>\r\n" + 
			"</table>\r\n" + 
			"<!--[if (IE)]></div><![endif]-->\r\n" + 
			"</body>\r\n" + 
			"</html>";

	public void setAddressee(Member member)
	{
		this.mail = this.mail.replace("[guest_name]", member.getMemberName());
	}
	
	public void setNewPassword(String password)
	{
		this.mail = this.mail.replace("[新密碼]", password);
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
}
