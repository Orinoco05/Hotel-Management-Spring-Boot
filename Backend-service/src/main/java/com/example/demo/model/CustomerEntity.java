package com.example.demo.model;

import com.example.demo.common.Gender;
import com.example.demo.common.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.UserType;

@Entity
@Setter
@Getter
@Table(name = "tbl_customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", length = 255)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String name;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "gender")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Gender gender;

    @Column(name = "status")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Status status;

    @Column(name = "nationality", length = 255)
    private String nationality;

    @Column(name = "citizen_identification_card", unique = true, nullable = false, length = 16)
    private String citizen_identification_card;

}
