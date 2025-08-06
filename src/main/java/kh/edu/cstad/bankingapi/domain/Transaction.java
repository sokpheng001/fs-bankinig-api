package kh.edu.cstad.bankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(length = 50)
    private String remark;

    @Column(nullable = false, length = 25)
    private String status;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    private TransactionType transactionType;

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Account receiver;
}
