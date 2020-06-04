package com.activity.controller.rest;
import java.awt.image.BufferedImage;
import java.nio.file.FileSystems;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RestController;

import com.activity.entity.Registration;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@RestController
@Path("/QRcode")
public class QRcodeController {
	//寫檔
	public BufferedImage generateQRCodeImage(String barcodeText,String filename) throws Exception {
	    QRCodeWriter barcodeWriter = new QRCodeWriter();
	    BitMatrix bitMatrix = 
	      barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 350, 350);
	    System.setProperty("java.specification.version","1.9");
	    java.nio.file.Path path = FileSystems.getDefault().getPath("C:\\Users\\jack1\\Desktop\\face\\Engine\\"+filename+".PNG");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	    return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}
	//直接展示
	public BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
	    QRCodeWriter barcodeWriter = new QRCodeWriter();
	    BitMatrix bitMatrix = 
	      barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 350, 350);
	    System.setProperty("java.specification.version","1.9");
	    //java.nio.file.Path path = FileSystems.getDefault().getPath("C:\\Users\\jack1\\Desktop\\face\\Engine\\"+filename+".PNG");
        //MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	    return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}
	
	@POST
	@Produces("image/png")
	public Response getQRcode(Registration registration) throws Exception
	{
		BufferedImage bufferImage = generateQRCodeImage(String.valueOf(registration.getAInum()));
		generateQRCodeImage(String.valueOf("http://localhost:8080/api/regisWithQRcode/"+registration.getAInum()),registration.getMember_Email());
		return Response.status(200).entity(bufferImage).build();
	}
	
	@GET
	@Produces("image/png")
	public Response getQRcode1() throws Exception
	{
		Registration registration = new Registration();
		registration.setAInum(2);
		BufferedImage bufferImage = generateQRCodeImage(String.valueOf(registration.getAInum()));
		generateQRCodeImage(String.valueOf("http://localhost:8080/api/regisWithQRcode/"+registration.getAInum()),registration.getMember_Email());
		return Response.status(200).entity(bufferImage).build();
	}

}
