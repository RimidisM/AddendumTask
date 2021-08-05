package lt.addendum.job.interview.task.controllers;

import lt.addendum.job.interview.task.domains.dto.RequestDTO;
import lt.addendum.job.interview.task.domains.models.BeneficiaryModel;
import lt.addendum.job.interview.task.services.BeneficiaryService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BeneficiaryController {

    private final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);

    private final BeneficiaryService service;

    public BeneficiaryController(BeneficiaryService service) {
        this.service = service;
    }

    @PostMapping("/addBeneficiary")
    public ResponseEntity<BeneficiaryModel> addBeneficiary(@Valid @RequestBody RequestDTO request) {

        logger.info("Received request: {}", request);

        return ResponseEntity.ok().body(service.addBeneficiary(request));
    }

    @GetMapping("/getAllBeneficiary")
    public ResponseEntity<List<BeneficiaryModel>> getAllBeneficiary() {

        logger.info("Received request to get all beneficiary");

        return ResponseEntity.ok().body(service.getAllBeneficiary());
    }

    @GetMapping("/getBeneficiary/{id}")
    public ResponseEntity<BeneficiaryModel> getBeneficiary(@PathVariable Long id) {

        logger.info("Received request to get beneficiary with id: {}", id);

        return ResponseEntity.ok().body(service.getBeneficiary(id));
    }

    @PutMapping("/updateBeneficiary/{id}")
    public ResponseEntity<BeneficiaryModel> updateBeneficiary(@Valid @RequestBody RequestDTO request,
                                                              @PathVariable Long id) {

        logger.info("Received request to update beneficiary: {}, with id: {}", request, id);

        return ResponseEntity.ok().body(service.updateBeneficiary(id, request));
    }

    @DeleteMapping("/deleteBeneficiary/{id}")
    public ResponseEntity<BeneficiaryModel> deleteBeneficiary(@PathVariable Long id) {

        logger.info("Received request to delete beneficiary with id: {}", id);

        return ResponseEntity.ok().body(service.deleteBeneficiary(id));
    }

    @GetMapping(path = "/getBeneficiaryQr/{id}", produces = "image/png")
    public ResponseEntity<byte[]> getBeneficiaryQr(@PathVariable Long id) throws IOException {

        logger.info("Received request to QR for beneficiary with id: {}", id);

        return ResponseEntity.ok().body(IOUtils.toByteArray(service.getBeneficiaryQr(id)));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
