package com.orderservice.sprint4.service;

import com.orderservice.sprint4.dto.OrderItemStatusRequestDTO;
import com.orderservice.sprint4.dto.OrderStageDTO;
import com.orderservice.sprint4.dto.OrderStatusRequestDTO;
import com.orderservice.sprint4.dto.ShipmentItemListDTO;
import com.orderservice.sprint4.exception.OrderNotFoundException;
import com.orderservice.sprint4.model.Order;
import com.orderservice.sprint4.model.ShipmentItem;
import com.orderservice.sprint4.model.enmus.OrderStatus;
import com.orderservice.sprint4.model.enmus.ShipmentItemStatus;
import com.orderservice.sprint4.repository.OrderRepository;
import com.orderservice.sprint4.repository.ShipmentItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private RestTemplate restTemplate;

    @Value("${inventory.service.inventory.status.url}")
    String  INVENTORY_SERVICE_INVENTORY_STATUS_URL;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShipmentItemRepository shipmentItemRepository;



    @Override
    public void orderShipped(OrderItemStatusRequestDTO dto){
        Optional.ofNullable(dto.getItemStatus())
                .filter(stat -> stat == ShipmentItemStatus.InTransit)
                .orElseThrow(()-> new RuntimeException("Wrong Input"));

        ShipmentItem item = shipmentItemRepository.findByOrderItemOrderItemId(dto.getOrderItemId());

        if(item==null){
            throw new OrderNotFoundException(dto.getOrderItemId());
        }if(!item.getItemStatus().equals(ShipmentItemStatus.Pending)){
            throw new RuntimeException("Item already uptodate");
        }


        Optional.ofNullable(item)
                .ifPresentOrElse(
                        i -> {
                            i.setItemStatus(dto.getItemStatus());
                            shipmentItemRepository.save(i);
                        },
                        () -> {
                            throw new RuntimeException("OrderItem not Found Exception");
                        }
                );
        try {
            String url = INVENTORY_SERVICE_INVENTORY_STATUS_URL + item.getOrderItem().getOrderItemId();

            OrderStageDTO request = new OrderStageDTO();
            request.setOrderId(item.getOrderItem().getOrderItemId());
            request.setIsCancelled(0);

            restTemplate.postForObject(url, request, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Inventory Service down");
        }
    }

    @Override
    public void orderDelivered(OrderItemStatusRequestDTO dto){
        Optional.ofNullable(dto.getItemStatus())
                .filter(stat -> stat == ShipmentItemStatus.Delivered)
                .orElseThrow(()-> new RuntimeException("Wrong Input"));


        ShipmentItem item = shipmentItemRepository.findByOrderItemOrderItemId(dto.getOrderItemId());

        if(item==null){
            throw new OrderNotFoundException(dto.getOrderItemId());
        }if(!item.getItemStatus().equals(ShipmentItemStatus.InTransit)){
            throw new RuntimeException("Order is uptodate");
        }


        Optional.ofNullable(item)
                .ifPresentOrElse(
                        i -> {
                            i.setItemStatus(dto.getItemStatus());
                            shipmentItemRepository.save(i);
                        },
                        () -> {
                            throw new RuntimeException("OrderItem not Found Exception");
                        }
                );

    }

    @Override
    public List<ShipmentItemListDTO> getShipmentByStatus(ShipmentItemStatus itemStatus) {
        if (itemStatus == null ||
                !(itemStatus == ShipmentItemStatus.Pending ||
                        itemStatus == ShipmentItemStatus.InTransit ||
                        itemStatus == ShipmentItemStatus.Delivered)) {
            throw new RuntimeException("Wrong Input");
        }

        List<ShipmentItem> shipmentItems = shipmentItemRepository.findByItemStatus(itemStatus);
        if (shipmentItems == null || shipmentItems.isEmpty()) {
            throw new RuntimeException("Items not found");
        }
        List<ShipmentItemListDTO> listDTOS = new ArrayList<>();

        shipmentItems.forEach(item->{
            ShipmentItemListDTO dto = new ShipmentItemListDTO();
            dto.setOrderId(item.getOrderItem().getOrder().getOrderId());
            dto.setOrderItemId(item.getOrderItem().getOrderItemId());
            dto.setItemStatus(item.getItemStatus());
            dto.setShipmentDate(item.getShipmentDate());
            listDTOS.add(dto);
        });


        return listDTOS;
    }

    @Override
    public void orderCanelled(OrderItemStatusRequestDTO dto) {
        Optional.ofNullable(dto.getItemStatus())
                .filter(stat -> stat == ShipmentItemStatus.Delivered)
                .orElseThrow(()-> new RuntimeException("Wrong Input"));


        ShipmentItem item = shipmentItemRepository.findByOrderItemOrderItemId(dto.getOrderItemId());

        if(item==null){
            throw new OrderNotFoundException(dto.getOrderItemId());
        }if(!item.getItemStatus().equals(ShipmentItemStatus.InTransit) || !item.getItemStatus().equals(ShipmentItemStatus.Pending)){
            throw new RuntimeException("Order is uptodate");
        }



        Optional.ofNullable(item)
                .ifPresentOrElse(
                        i -> {
                            i.setItemStatus(dto.getItemStatus());
                            shipmentItemRepository.save(i);
                        },
                        () -> {
                            throw new RuntimeException("OrderItem not Found Exception");
                        }
                );
        try {
            String url = INVENTORY_SERVICE_INVENTORY_STATUS_URL + item.getOrderItem().getOrderItemId();

            OrderStageDTO request = new OrderStageDTO();
            request.setOrderId(item.getOrderItem().getOrderItemId());
            request.setIsCancelled(1);

            restTemplate.postForObject(url, request, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Inventory Service down");
        }
    }

}
