package com.ecovision.ecovision.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "total_view")
public class TotalView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_distance")
    private int totalDistance;

    @Column(name = "total_time")
    private int totalTime;

    @Column(name = "total_count")
    private int totalCount;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
