package lt.addendum.job.interview.task.clients;

import lt.addendum.job.interview.task.domains.models.BeneficiaryModel;
import lt.addendum.job.interview.task.exceptions.RecordNotFoundException;
import lt.addendum.job.interview.task.utilities.QrDataHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

@Service
public class ExternalQrClient {

    private final Logger logger = LoggerFactory.getLogger(ExternalQrClient.class);

    private final WebClient webClient;

    public ExternalQrClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public InputStream getQrCode(BeneficiaryModel beneficiaryModel) {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        // If necessary we could make all settings accessible for our API users
        queryParams.add("data", QrDataHelper.constructQrData(beneficiaryModel));
        queryParams.add("size", "small");

        byte[] image = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParams(queryParams)
                        .build())
                .accept(MediaType.IMAGE_PNG)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(byte[].class)
                        .doOnSuccess(body -> {
                            if (clientResponse.statusCode().isError()) {
                                logger.error("HttpStatusCode = {}", clientResponse.statusCode());
                                logger.error("HttpHeaders = {}", clientResponse.headers().asHttpHeaders());
                                logger.error("ResponseBody = {}", body);
                            }
                        })).block();

        if (Objects.isNull(image)) {
            logger.error("Received empty response for getQrCode");

            throw new RecordNotFoundException("Error while getting QR code for Id: " + beneficiaryModel.getId());
        }

        return new ByteArrayInputStream(image);
    }
}
