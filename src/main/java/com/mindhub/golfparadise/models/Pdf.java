package com.mindhub.golfparadise.models;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mindhub.golfparadise.dtos.OrderProductDTO;
import com.mindhub.golfparadise.dtos.OrderPurchaseDTO;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class Pdf {
    Document document;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    Font titleSource = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
    Font subtitleSource = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    Font paragraphSource = FontFactory.getFont(FontFactory.HELVETICA, 12);
    Font paragraphSourceBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

    public void createDocument(HttpServletResponse response) throws IOException, DocumentException {
        document = new Document(PageSize.A4, 45, 45, 50, 50);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
    }

    public void addTitle(String text) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Phrase(text, titleSource));
        cell.setColspan(3);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        document.add(table);
    }

    public void addSubTitle(String text) throws DocumentException, IOException {
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(text, subtitleSource));
        document.add(paragraph);
    }

    public void addParagraph(String text) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(text, paragraphSource));
        document.add(paragraph);
    }

    public void addLineJumps() throws DocumentException {
        Paragraph lineJumps = new Paragraph();
        lineJumps.add(new Phrase(Chunk.NEWLINE));
        document.add(lineJumps);
    }

    public void addOrderProductsTable(Set<OrderProductDTO> orderProducts) throws DocumentException {
        String[] tableHeader = {"Product", "Quantity", "Amount"};
        PdfPTable table = new PdfPTable(4);

        for (String header : tableHeader) {
            PdfPCell cell = new PdfPCell(new Phrase(header, paragraphSourceBold));
            if (header.equals("Product")) {
                cell.setColspan(2);
            }
            cell.setBackgroundColor(new BaseColor(184, 202, 162));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            table.addCell(cell);
        }

        orderProducts.forEach(orderProductDTO -> {
            int counter = 0;
            String[] tableContent = {
                    orderProductDTO.getProductName(),
                    orderProductDTO.getQuantity() + "",
                    "$" + orderProductDTO.getTotalAmount()
            };

            for (String content : tableContent) {
                PdfPCell cell = new PdfPCell(new Phrase(content));
                if (counter == 0) {
                    cell.setColspan(2);
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
                counter++;
            }
        });
        table.setWidthPercentage(100);
        document.add(table);
    }

    public void addTotalAmountTable(OrderPurchaseDTO orderPurchaseDTO) throws DocumentException {
        String[] tableContent = {"", "Total Amount", "$" + (orderPurchaseDTO.getAmount() + orderPurchaseDTO.getDeliveryCost())};
        PdfPTable table = new PdfPTable(4);
        int counter = 0;
        for (String content : tableContent) {
            PdfPCell cell = new PdfPCell(new Phrase(content, paragraphSourceBold));
            if (counter == 0) {
                cell.setColspan(2);
            }
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
            counter++;
        }
        table.setWidthPercentage(100);
        document.add(table);
    }

    public void closeDocument() {
        document.close();
    }
}