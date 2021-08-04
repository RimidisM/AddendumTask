package lt.addendum.job.interview.task.controllers

import groovy.json.JsonOutput
import lt.addendum.job.interview.task.domains.dto.RequestDTO
import lt.addendum.job.interview.task.domains.models.BeneficiaryModel
import lt.addendum.job.interview.task.services.BeneficiaryService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@WebMvcTest
class BeneficiaryControllerTests extends Specification {

    @Autowired
    private MockMvc mvc

    @SpringBean
    BeneficiaryService service = Mock()

    def "Should create record, return ok status and Beneficiary object"() {
        setup:
        service.addBeneficiary(*_) >> createBeneficiaryModel()

        when:
        def result = mvc.perform(MockMvcRequestBuilders.post('/api/addBeneficiary')
                .header("Authorization", "Basic dGVzdDp0ZXN0")
                .contentType("application/json")
                .content(JsonOutput.toJson(getAddRequest())))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
    }

    def createBeneficiaryModel() {

        BeneficiaryModel response = new BeneficiaryModel()

        response.setId(123)
        response.setUuid("7777")
        response.setName("Test")
        return response
    }

    def getAddRequest() {

        RequestDTO request = new RequestDTO()

        request.setName("Testauskas")
        return request
    }
}
