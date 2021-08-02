package lt.addendum.job.interview.task.domains.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "beneficiary")
public class BeneficiaryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "uniq_code")
    private String uuid;
    @Column(name = "name")
    private String name;
}
