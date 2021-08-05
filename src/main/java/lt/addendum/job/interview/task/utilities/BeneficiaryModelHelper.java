package lt.addendum.job.interview.task.utilities;

import lt.addendum.job.interview.task.domains.dto.RequestDTO;
import lt.addendum.job.interview.task.domains.models.BeneficiaryModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BeneficiaryModelHelper {

    private BeneficiaryModelHelper() {
    }

    public static BeneficiaryModel getBeneficiaryModel(RequestDTO requestDTO) {

        var beneficiaryModel = new BeneficiaryModel();

        beneficiaryModel.setName(requestDTO.getName());
        beneficiaryModel.setUuid(UUID.randomUUID().toString());

        return beneficiaryModel;
    }
}
