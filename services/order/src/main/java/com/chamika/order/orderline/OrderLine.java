package com.chamika.order.orderline;

import com.chamika.order.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// This entity is the bridge between Order and Product (Many to Many) with additional attribute "quantity" to represent the quantity of a product in an order

// * OrderLine Entity is used to represent individual products in an order

@Entity
@Table(name = "t_order_line")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLine {

    // TODO: write sql queries for this entity

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer productId;  // can not use foreign key since product is a different service (following database per service architecture)

    private Integer quantity;


}
