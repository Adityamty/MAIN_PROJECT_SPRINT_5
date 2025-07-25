package com.nisum.cartAndCheckout.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_addresses")
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "address_lane_1", nullable = false, length = 255)
    private String addressLane1;

    @Column(name = "address_lane_2", length = 255)
    private String addressLane2;

    @Column(length = 10, nullable = false)
    private String zipcode;

    @Column(length = 100)
    private String state;

    @Column(length = 100)
    private String country;

    @Column(name = "address_type", length = 50)
    private String addressType;

    @Column(name = "contact_name", length = 200)
    private String contactName;

    @Column(name = "contact_phone_number", length = 15)
    private String contactPhoneNumber;

}
