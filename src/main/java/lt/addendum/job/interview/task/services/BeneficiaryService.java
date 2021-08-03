package lt.addendum.job.interview.task.services;

import lt.addendum.job.interview.task.clients.ExternalQrClient;
import lt.addendum.job.interview.task.domains.dto.RequestDTO;
import lt.addendum.job.interview.task.domains.models.BeneficiaryModel;
import lt.addendum.job.interview.task.exceptions.RecordNotFoundException;
import lt.addendum.job.interview.task.repositories.BeneficiaryRepository;
import lt.addendum.job.interview.task.utilities.BeneficiaryModelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class BeneficiaryService {
    private final Logger logger = LoggerFactory.getLogger(BeneficiaryService.class);

    private final BeneficiaryRepository repository;
    private final ExternalQrClient externalQrClient;

    public BeneficiaryService(BeneficiaryRepository repository, ExternalQrClient externalQrClient) {

        this.repository = repository;
        this.externalQrClient = externalQrClient;
    }

    public BeneficiaryModel addBeneficiary(RequestDTO requestDTO) {

        var beneficiaryModel = BeneficiaryModelHelper.getBeneficiaryModel(requestDTO);

        logger.info("BeneficiaryModel created: {}", beneficiaryModel);

        return repository.save(beneficiaryModel);
    }

    public List<BeneficiaryModel> getAllBeneficiary() {

        List<BeneficiaryModel> beneficiaryModelList = repository.findAll();

        logger.info("BeneficiaryModel list created: {}", beneficiaryModelList);

        return beneficiaryModelList;
    }

    public BeneficiaryModel getBeneficiary(Long id) {

        var beneficiaryModel = repository.findById(id);

        if (beneficiaryModel.isEmpty()) {

            logger.error("Record not found. Id: {} ", id);

            throw new RecordNotFoundException("Record not found. Id: " + id);
        }

        logger.info("BeneficiaryModel has been found: {}", beneficiaryModel);

        return beneficiaryModel.get();
    }

    public BeneficiaryModel updateBeneficiary(Long id, RequestDTO requestDTO) {

        var beneficiaryModel = getBeneficiary(id);

        beneficiaryModel.setName(requestDTO.getName());

        var updatedBeneficiaryModel = repository.save(beneficiaryModel);

        logger.info("BeneficiaryModel has been updated: {}", updatedBeneficiaryModel);

        return updatedBeneficiaryModel;
    }

    public BeneficiaryModel deleteBeneficiary(Long id) {

        var beneficiaryModel = getBeneficiary(id);

        repository.deleteById(id);
        logger.info("BeneficiaryModel has been deleted: {}", beneficiaryModel);

        return beneficiaryModel;
    }

    public InputStream getBeneficiaryQr(Long id) {

        return externalQrClient.getQrCode(getBeneficiary(id));
    }
}
