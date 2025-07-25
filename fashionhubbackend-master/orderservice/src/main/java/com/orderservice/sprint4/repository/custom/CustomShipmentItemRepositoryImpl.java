package com.orderservice.sprint4.repository.custom;

import com.orderservice.sprint4.dao.ShipmentItemDAO;
import com.orderservice.sprint4.model.enmus.ShipmentItemStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomShipmentItemRepositoryImpl implements CustomShipmentItemRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ShipmentItemDAO> getShipmentItemsByOrderId(Integer orderId) {
        String sql = """
            SELECT 
                oi.product_id, 
                si.item_tracking_id, 
                oi.sku, 
                oi.quantity, 
                oi.seller_id, 
                si.item_status, 
                si.shipment_date, 
                si.delivered_date
            FROM shipment_items si
            JOIN order_items oi ON si.order_item_id = oi.id
            WHERE oi.order_id = :orderId
        """;

        List<Object[]> rows = entityManager.createNativeQuery(sql)
                .setParameter("orderId", orderId)
                .getResultList();

        List<ShipmentItemDAO> result = new ArrayList<>();
        for (Object[] row : rows) {
            ShipmentItemDAO dao = new ShipmentItemDAO();
            dao.setProduct((Integer) row[0]);
            dao.setTrackingId((String) row[1]);
            dao.setSku((String) row[2]);
            dao.setQuantity((Integer) row[3]);
            dao.setSeller((Integer) row[4]);
            dao.setItemStatus(ShipmentItemStatus.valueOf(row[5].toString()));
            dao.setShipmentDate(row[6] != null ? ((java.sql.Timestamp) row[6]).toLocalDateTime() : null);
            dao.setDeliveredDate(row[7] != null ? ((java.sql.Timestamp) row[7]).toLocalDateTime() : null);
            result.add(dao);
        }

        return result;
    }
}
