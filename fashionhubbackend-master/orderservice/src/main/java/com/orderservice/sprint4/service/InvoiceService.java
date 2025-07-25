package com.orderservice.sprint4.service;

import jakarta.servlet.http.HttpServletResponse;

public interface InvoiceService {
    void generatepdf(Integer orderId, HttpServletResponse response);
}
