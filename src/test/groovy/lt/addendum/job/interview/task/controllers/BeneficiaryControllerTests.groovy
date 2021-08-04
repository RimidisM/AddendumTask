package lt.addendum.job.interview.task.controllers

import lt.addendum.job.interview.task.domains.dto.RequestDTO
import lt.addendum.job.interview.task.domains.models.BeneficiaryModel
import lt.addendum.job.interview.task.services.BeneficiaryService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification


//@WebMvcTest(controllers = [BeneficiaryController, BeneficiaryService])
////@WebMvcTest
//@AutoConfigureMockMvc
@WebMvcTest
class BeneficiaryControllerTests extends Specification {

    @Autowired
    private MockMvc mvc

@SpringBean
    BeneficiaryService service = Mock()

//    BeneficiaryController controller = new BeneficiaryController(service)

//    def "ttttt"() {
//        given:
//
//        service.addBeneficiary(*_) >> createBeneficianaryModel()
//
//        when:
//        def result = controller.addBeneficiary(getAddRequest())
//
//        println  result.getBody().getName()
//        println  result.getBody().getUuid()
//        then:
//        result.statusCode.'2xxSuccessful'
//
//    }
//
    def "Should create record, return ok status and Beneficiary object"() {
        setup:
        service.addBeneficiary(*_) >> createBeneficianaryModel()

//        expect: "controller is available"
//        mvc.perform(MockMvcRequestBuilders.post(('/api/addBeneficiary'))
//                .header("Authorization", "Basic dGVzdDp0ZXN0"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("hello world"))

//        when:
//        def result = mvc.perform(MockMvcRequestBuilders.post(('/api/addBeneficiary'))
//                .header("Authorization", "Basic dGVzdDp0ZXN0")
//                .content(toJson(getAddRequest())))

        when:
        def result = mvc.perform(post(('/api/addBeneficiary'))
                .header("Authorization", "Basic dGVzdDp0ZXN0")
                .content(toJson(getAddRequest())))

        then:
        result.andExpect(status().isOk())
    }

    static def createBeneficianaryModel() {
        BeneficiaryModel response = new BeneficiaryModel()

        response.setId(123)
        response.setUuid("7777")
        response.setName("Test")
    }

    static def getAddRequest() {
        RequestDTO request = new RequestDTO()

        request.setName("Testauskas")
    }

//    @Shared
//    def client = new RESTClient( "http://localhost:7777")
//
//    def 'should return 200 code when used valid credentials' () {
//        when: 'login with invalid credentials'
//        client.headers['Authorization'] = "Basic dGVzdDp0ZXN0"
//        def response = client.post( path : '/api/addBeneficiary' )
//
//        then: 'server returns 200 code (ok)'
//        assert response.status == 200 : 'response code should be 200 when tried to authenticate with valid credentials'
//    }

}
