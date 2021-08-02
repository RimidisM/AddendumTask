package lt.addendum.job.interview.task.repositories;

import lt.addendum.job.interview.task.domains.models.BeneficiaryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryRepository extends JpaRepository<BeneficiaryModel, Long> {
}
