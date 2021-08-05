package lt.addendum.job.interview.task.controllers

import groovy.json.JsonOutput
import lt.addendum.job.interview.task.TestsSpecifications
import lt.addendum.job.interview.task.services.BeneficiaryService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest
class BeneficiaryControllerTests extends TestsSpecifications {

    @Autowired
    private MockMvc mvc

    @SpringBean
    BeneficiaryService service = Mock()

    def "Should return ok status for beneficiary add"() {
        setup:
        service.addBeneficiary(*_) >> createBeneficiaryModel()

        when:
        def result = mvc.perform(MockMvcRequestBuilders.post('/api/addBeneficiary')
                .header("Authorization", "Basic dGVzdDp0ZXN0")
                .contentType("application/json")
                .content(JsonOutput.toJson(getAddRequest())))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
        result.andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"uuid\":\"7777\",\"name\":\"Test\"}"))
    }

    def "Should return bad request status and error message"() {
        when:
        def result = mvc.perform(MockMvcRequestBuilders.post('/api/addBeneficiary')
                .header("Authorization", "Basic dGVzdDp0ZXN0")
                .contentType("application/json")
                .content(JsonOutput.toJson(getEmptyAddRequest())))

        then:
        result.andExpect(MockMvcResultMatchers.status().isBadRequest())
        result.andExpect(MockMvcResultMatchers.content().string("{\"name\":\"Name is mandatory\"}"))
    }

    def "Should return ok status for beneficiary get all"() {
        setup:
        service.getAllBeneficiary() >> createListBeneficiaryModel()

        when:
        def result = mvc.perform(MockMvcRequestBuilders.get('/api/getAllBeneficiary')
                .header("Authorization", "Basic dGVzdDp0ZXN0")
                .contentType("application/json"))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
        result.andExpect(MockMvcResultMatchers.content().string("[{\"id\":123,\"uuid\":\"7777\",\"name\":\"Test\"},{\"id\":456,\"uuid\":\"8888\",\"name\":\"Test2\"}]"))
    }

    def "Should return ok status for beneficiary get"() {
        setup:
        service.getBeneficiary(*_) >> createBeneficiaryModel()

        when:
        def result = mvc.perform(MockMvcRequestBuilders.get('/api/getBeneficiary/1')
                .header("Authorization", "Basic dGVzdDp0ZXN0")
                .contentType("application/json"))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
        result.andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"uuid\":\"7777\",\"name\":\"Test\"}"))
    }

    def "Should return ok status for beneficiary update"() {
        setup:
        service.updateBeneficiary(*_) >> createBeneficiaryModel()

        when:
        def result = mvc.perform(MockMvcRequestBuilders.put('/api/updateBeneficiary/1')
                .header("Authorization", "Basic dGVzdDp0ZXN0")
                .contentType("application/json")
                .content(JsonOutput.toJson(getAddRequest())))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
        result.andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"uuid\":\"7777\",\"name\":\"Test\"}"))
    }

    def "Should return ok status for beneficiary delete"() {
        setup:
        service.deleteBeneficiary(*_) >> createBeneficiaryModel()

        when:
        def result = mvc.perform(MockMvcRequestBuilders.delete('/api/deleteBeneficiary/1')
                .header("Authorization", "Basic dGVzdDp0ZXN0")
                .contentType("application/json")
                .content(JsonOutput.toJson(getAddRequest())))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
        result.andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"uuid\":\"7777\",\"name\":\"Test\"}"))
    }

    def "Should return ok status for beneficiary get QR"() {
        setup:
        service.getBeneficiaryQr(*_) >> new FileInputStream(new ClassPathResource("response.png").getFile())

        when:
        def result = mvc.perform(MockMvcRequestBuilders.get('/api/getBeneficiaryQr/1')
                .header("Authorization", "Basic dGVzdDp0ZXN0")
                .contentType("application/json"))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
        !result.andReturn().response.getContentAsString().isEmpty()
        result.andExpect(MockMvcResultMatchers.header().stringValues("\"Content-Type\":\"image/png\""))
    }
}
