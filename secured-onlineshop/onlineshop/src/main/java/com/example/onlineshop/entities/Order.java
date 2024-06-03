package com.example.onlineshop.entities;

import com.example.onlineshop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //@OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    //OrderItem entity has a reference to the Order entity
    //which is an "order" field
    //any operations performed on the Order entity should
    //be cascaded to its associated OrderItem entities
    //private List<OrderItem> items = new ArrayList<>();
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    public double getTotalPrice(){
        double sum = 0.0;
        for(OrderItem item: items){
            sum += item.getTotalItemPrice();
        }
        return sum;
    }
    public List<OrderItem> getItems(){
        return items;
    }
}
