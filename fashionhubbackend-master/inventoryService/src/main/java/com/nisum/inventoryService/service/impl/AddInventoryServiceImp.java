package com.nisum.inventoryService.service;


import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.dto.InventoryDTO;
import com.nisum.inventoryService.repository.AddInventoryRepo;
import com.nisum.inventoryService.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddInventoryServiceImp implements AddInventoryService {

    @Autowired
    private AddInventoryRepo addInventoryRepo;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private MapperUtil mapperUtil;

    @Override
    public InventoryDTO addInventory(InventoryDTO dto) {
        if (addInventoryRepo.existsBySku(dto.getSku())) {
            throw new IllegalArgumentException("SKU already exists");
        }

        Inventory inventory = mapperUtil.mapToInventory(dto);
        Inventory saved = addInventoryRepo.save(inventory);
        return mapperUtil.mapToInventoryDto(saved);
    }

    @Override
    public List<InventoryDTO> getAllInventory() {
        return addInventoryRepo.findAll().stream()
                .map(mapperUtil::mapToInventoryDto)
                .collect(Collectors.toList());
    }






    @Override
    public List<Map<String, Object>> getAvailabilityForSkus(List<String> skuList) {
//        try{
//            List<InventoryDTO> inventoryDTOList = restTemplate.getForObject("http://localhost:8001/get/data", List.class);
//        }
        return addInventoryRepo.findAll().stream()
                .filter(inv -> skuList.contains(inv.getSku()))
                .map(inv -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("sku", inv.getSku());
                    map.put("categoryId", inv.getCategoryId());
                    map.put("availableQty", inv.getAvailableQty());
                    return map;
                })
                .collect(Collectors.toList());
    }

}
