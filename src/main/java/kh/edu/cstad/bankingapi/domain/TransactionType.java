package kh.edu.cstad.bankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transaction_types")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,  unique = true, length = 100)
    private String type;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "transactionType")
    private List<Transaction> transactions;

}