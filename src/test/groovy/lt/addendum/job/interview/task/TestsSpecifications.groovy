package lt.addendum.job.interview.task

import lt.addendum.job.interview.task.domains.dto.RequestDTO
import lt.addendum.job.interview.task.domains.models.BeneficiaryModel
import spock.lang.Specification

class TestsSpecifications extends Specification{

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
