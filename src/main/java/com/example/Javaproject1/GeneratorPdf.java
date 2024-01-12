package com.example.Javaproject1;

import com.lowagie.text.*;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class GeneratorPdf {
    public void zapiszDoPliku(RaportPdf raport, File file){
        String path = file.getAbsoluteFile() + "/" +raport.getNazwa() +  ".pdf";
        try (FileOutputStream stream = new FileOutputStream(path);
             Document document = new Document(PageSize.A4)) {
            PdfWriter.getInstance(document, stream);
            document.open();
            Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontTiltle.setSize(20);
            Paragraph paragraph1 = new Paragraph(raport.getNazwa(), fontTiltle);
            paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph1);
            PdfPTable table = new PdfPTable(raport.ilosc());
            table.setWidthPercentage(100f);
            int[] withs = new int[raport.ilosc()];
            Arrays.fill(withs,3);
            table.setWidths(withs);
            table.setSpacingBefore(5);
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(CMYKColor.BLUE);
            cell.setPadding(5);
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            font.setColor(CMYKColor.WHITE);
            for (int i = 0; i < raport.ilosc(); i++) {
                cell.setPhrase(new Phrase(raport.getNaglowek(i), font));
                table.addCell(cell);
            }
            for (int i = 0; i < raport.ilosc(); i++) {
                List<String> rzad = raport.getRzad(i);
                for (String komorka : rzad) {
                    table.addCell(komorka);
                }
            }

            Paragraph opis = new Paragraph(raport.getOpis(),fontTiltle);
            document.add(table);
            document.add(opis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
