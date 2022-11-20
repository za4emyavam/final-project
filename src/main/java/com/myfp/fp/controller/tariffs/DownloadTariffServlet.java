package com.myfp.fp.controller.tariffs;



import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.myfp.fp.entities.Tariff;
import com.myfp.fp.service.ServiceException;
import com.myfp.fp.service.TariffService;
import com.myfp.fp.util.FactoryException;
import com.myfp.fp.util.MainServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@WebServlet(name = "DownloadTariffServlet", value = "/tariffs/download")
public class DownloadTariffServlet extends HttpServlet {
    public static final String FONT = "/assets/fonts/arial.ttf";

    public Font fontF;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tariff> tariffList;
        try {
            TariffService tariffService = MainServiceFactoryImpl.getInstance().getTariffService();
            tariffList = tariffService.findAll();
        } catch (FactoryException | ServiceException e) {
            throw new ServletException(e);
        }


        Document document = new Document();
        File file = new File("C:\\PRJ\\FinalProject\\final-project\\tariffs.pdf");
        file.createNewFile();
        resp.setContentType("application/pdf");
        resp.setHeader("Content-disposition", "attachment; filename=tariffs.pdf");
        try {
            PdfWriter.getInstance(document, resp.getOutputStream());
            BaseFont bf=BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            fontF = new Font(bf,16, Font.NORMAL, BaseColor.BLACK);
            document.open();
            Chunk chunk = new Chunk("List of tariffs\n\n", fontF);

            Paragraph paragraph = new Paragraph(chunk);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            PdfPTable tariffTable = new PdfPTable(6);
            addTableHeader(tariffTable);
            addRows(tariffTable, tariffList);
            document.add(tariffTable);
            document.close();

        } catch (DocumentException e) {
            throw new ServletException(e);
        }
        resp.sendRedirect("/tariffs");
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Name", "Description", "Cost", "Frequency of payment (days)", "Service")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<Tariff> tariffList) {
        for (Tariff t :
                tariffList) {
            table.addCell(new PdfPCell(new Phrase(t.getId().toString(), fontF)));
            table.addCell(new PdfPCell(new Phrase(t.getName(), fontF)));
            table.addCell(new PdfPCell(new Phrase(t.getDescription(), fontF)));
            table.addCell(new PdfPCell(new Phrase("" + t.getCost(), fontF)));
            table.addCell(new PdfPCell(new Phrase("" + t.getFrequencyOfPayment(), fontF)));
            table.addCell(new PdfPCell(new Phrase(t.getService().getServiceType(), fontF)));
        }
    }
}