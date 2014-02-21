package com.litetech.omt;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/**
 * Hello world!
 *
 */
public class App 
{
   
    
    
    public static void stamp(String src, String dest)
            throws IOException, DocumentException {
            PdfReader reader = new PdfReader(src);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
            PdfContentByte canvas = stamper.getOverContent(1);
            ColumnText.showTextAligned(canvas,
                    Element.ALIGN_LEFT, new Phrase("BEST WISHES!!"), 36, 540, 0);
            stamper.close();
            reader.close();
    }
    
    public static void main(String[] args) throws IOException, DocumentException {
		stamp("C:/Users/Arun/Downloads/Rajikarthik_invitation.pdf", "C:/Users/Arun/Downloads/output/output2.pdf");
	}

}
