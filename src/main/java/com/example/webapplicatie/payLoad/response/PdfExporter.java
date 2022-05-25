package com.example.webapplicatie.payLoad.response;

import com.example.webapplicatie.models.Car;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

// Exporteert car naar een pdf bestand
public class PdfExporter {
    private List <Car> carList;

    public PdfExporter(List<Car> carList) {
        this.carList = carList;
    }
    private void writeTableHeader(PdfPTable table)
    {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.PINK.darker());
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID:", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Username:", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Car:", font));
        table.addCell(cell);
    }
    // zorgt voor de hoeveel cellen van het PDF bestand.
    // Laat zien welke cellen er zichtbaar zijn met de namen van het PDF bestand
    private void writeTableData(PdfPTable table){
        PdfPCell cell= new PdfPCell();
        for (Car car: carList){
            cell.setPhrase(new Phrase(String.valueOf(car.getId())));
            table.addCell(cell);

            if (car.getUser()==null){
                cell.setPhrase(new Phrase("-"));
                table.addCell(cell);
            }
            else {
                cell.setPhrase(new Phrase(car.getUser().getUsername()));
                table.addCell(cell);
            }


            cell.setPhrase(new Phrase(car.getDescription()));
            table.addCell(cell);
        }
    }
    // instellingen van het PDF bestand kleur etc
    public void export(HttpServletResponse response) throws DocumentException, IOException
    {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("List of cars", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] { 0.8f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }
}
