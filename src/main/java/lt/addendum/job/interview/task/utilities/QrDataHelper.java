package lt.addendum.job.interview.task.utilities;

import lt.addendum.job.interview.task.domains.models.BeneficiaryModel;
import org.springframework.stereotype.Component;

@Component
public class QrDataHelper {

    private QrDataHelper() {
    }

    public static String constructQrData(BeneficiaryModel model) {

        return "Beneficiary information: \r\n\rUnique_code: " + model.getUuid() + " \r\n\rName: " + model.getName();
    }
}

