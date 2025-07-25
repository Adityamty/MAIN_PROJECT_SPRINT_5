package com.orderservice.sprint4.repository;

import com.orderservice.sprint4.dto.InvoiceResponseDTO;
import com.orderservice.sprint4.model.OrderInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderInvoiceRepository extends JpaRepository<OrderInvoice,Integer> {

    @Query("SELECT new com.orderservice.sprint4.dto.InvoiceResponseDTO(" +
            "oi.invoiceId, o.orderId, oi.invoiceNumber, oi.invoiceDate, oi.paymentMode, oi.invoiceAmount) " +
            "FROM OrderInvoice oi JOIN oi.order o WHERE o.orderId = :orderId")
    InvoiceResponseDTO getInvoiceByOrderId(@Param("orderId") Integer orderId);
}