package com.ele.domain.delivery;

import com.ele.domain.member.Address;
import com.ele.domain.order.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static com.ele.domain.delivery.DeliveryStatus.READY;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @Embedded
    private Address address;
    @Enumerated(STRING)
    private DeliveryStatus deliveryStatus;
    @OneToOne(fetch = LAZY)
    private Order order;

    @Builder
    public Delivery(Address address) {
        this.address = address;
        this.deliveryStatus = READY;
    }

    protected Delivery() {
    }
}
