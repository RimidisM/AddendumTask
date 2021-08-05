package lt.addendum.job.interview.task.services

import lt.addendum.job.interview.task.TestsSpecifications
import lt.addendum.job.interview.task.clients.ExternalQrClient
import lt.addendum.job.interview.task.domains.dto.RequestDTO
import lt.addendum.job.interview.task.domains.models.BeneficiaryModel
import lt.addendum.job.interview.task.exceptions.RecordNotFoundException
import lt.addendum.job.interview.task.repositories.BeneficiaryRepository
import org.spockframework.spring.SpringBean

class BeneficiaryServiceTests extends TestsSpecifications {

    @SpringBean
    BeneficiaryRepository repository = Mock()

    @SpringBean
    ExternalQrClient externalQrClient = Mock()

    BeneficiaryService service = new BeneficiaryService(repository, externalQrClient)

    def "Should invoke save"() {
        when:
        service.addBeneficiary(getAddRequest())

        then:
        1 * repository.save(*_)
    }

    def "Should invoke findAll"() {
        when:
        service.getAllBeneficiary()

        then:
        1 * repository.findAll(*_)
    }

    def "Should should return beneficiary object"() {
        setup:
        repository.findById(*_) >> Optional.of(createBeneficiaryModel())

        when:
        def result = service.getBeneficiary(123)

        then:
        "Test".equals(result.getName())
        "7777".equals(result.getUuid())
        123L == result.getId()
    }

    def "Should throw exception"() {
        setup:
        repository.findById(*_) >> Optional.empty()

        when:
        def result = service.getBeneficiary(123)

        then:
        thrown(RecordNotFoundException)
    }

    def "Should execute update logic"() {
        setup:
        repository.findById(*_) >> Optional.of(createBeneficiaryModel())

        when:
        service.updateBeneficiary(123, getAddRequest())

        then:
        1 * repository.save(*_)
    }

    def "Should execute delete logic"() {
        setup:
        repository.findById(*_) >> Optional.of(createBeneficiaryModel())

        when:
        service.deleteBeneficiary(123)

        then:
        1 * repository.deleteById(*_)
    }


    def createBeneficiaryModel() {

        BeneficiaryModel response = new BeneficiaryModel()

        response.setId(123)
        response.setUuid("7777")
        response.setName("Test")
        return response
    }

    def createListBeneficiaryModel() {

        BeneficiaryModel responseOne = new BeneficiaryModel()
        BeneficiaryModel responseTwo = new BeneficiaryModel()

        responseOne.setId(123)
        responseOne.setUuid("7777")
        responseOne.setName("Test")

        responseTwo.setId(456)
        responseTwo.setUuid("8888")
        responseTwo.setName("Test2")

        List<BeneficiaryModel> list = new ArrayList<>()

        list.add(responseOne)
        list.add(responseTwo)

        return list
    }

    def getAddRequest() {

        RequestDTO request = new RequestDTO()

        request.setName("Test")
        return request
    }

    def getEmptyAddRequest() {

        RequestDTO request = new RequestDTO()

        request.setName("")
        return request
    }
}
