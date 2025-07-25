package com.orderservice.sprint4.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.orderservice.sprint4.dto.InvoiceResponseDTO;
import com.orderservice.sprint4.dto.OrderDetailsResponseDTO;
import com.orderservice.sprint4.dto.OrderItemResponseDTO;
import com.orderservice.sprint4.exception.PdfGenerationException;
import com.orderservice.sprint4.model.Order;
import com.orderservice.sprint4.model.OrderItem;
import com.orderservice.sprint4.repository.OrderInvoiceRepository;
import com.orderservice.sprint4.repository.OrderItemRepository;
import com.orderservice.sprint4.repository.OrderRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Service
public class InvoiceServiceImpl implements InvoiceService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderInvoiceRepository orderInvoiceRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void generatepdf(Integer orderId, HttpServletResponse response) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        List<OrderItem> orderItems = order.getOrderItems();

        try(OutputStream out = response.getOutputStream()){
            Document document = new Document();
            PdfWriter.getInstance(document,out);
            document.open();
            System.out.println("pdf Writer");
            Font titleFont = new Font(Font.FontFamily.HELVETICA,18,Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.HELVETICA,14,Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA,12,Font.NORMAL);

            document.add(new Paragraph("Invoice Report",titleFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Invoice Number: "+order.getOrderInvoice().getInvoiceNumber(),normalFont));
            document.add(new Paragraph("Invoice Date: "+order.getOrderInvoice().getInvoiceDate(),normalFont));
            document.add(new Paragraph("Payment Mode: "+order.getOrderInvoice().getPaymentMode(),normalFont));
            document.add(new Paragraph("Invoice Amount: "+order.getOrderInvoice().getInvoiceAmount()));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Order Details",headerFont));
            document.add(new Paragraph("Order ID: "+order.getOrderId(),normalFont));
            document.add(new Paragraph("Order Date: "+order.getOrderDate(),normalFont));
            document.add(new Paragraph("Order Status: "+order.getOrderStatus(),normalFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Invoice Items",headerFont));
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Stream.of("Product","Quantity","Unit Price","Discount","Final Price")
                    .forEach(columnTitle ->{
                        PdfPCell header = new PdfPCell();
                        header.setPhrase(new Phrase(columnTitle,headerFont));
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(header);
                    });
            BigDecimal price = BigDecimal.valueOf(0.00);
            for(OrderItem item : orderItems){
                table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getProductId()),normalFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getQuantity()),normalFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getUnitPrice()),normalFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getDiscount()),normalFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getFinalPrice()),normalFont)));
                price = price.add(item.getFinalPrice());
            }

            document.add(table);

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Price: "+price));
            document.add(new Paragraph("Promo Discount: -"+order.getPromoDiscount()));
            document.add(new Paragraph("Total: "+price.subtract(order.getPromoDiscount())));

            document.close();


        } catch (DocumentException | IOException e) {
            throw new PdfGenerationException("Failed to generate invoice PDF for order ID: " + orderId, e);
        }

    }
}
