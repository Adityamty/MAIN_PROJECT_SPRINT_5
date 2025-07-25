package com.catalogservice.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {
    @Test
    void testMessage() {
        String msg = "Resource not found!";
        ResourceNotFoundException ex = new ResourceNotFoundException(msg);
        assertEquals(msg, ex.getMessage());
    }
}
