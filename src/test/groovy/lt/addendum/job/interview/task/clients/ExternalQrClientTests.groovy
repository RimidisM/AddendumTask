package lt.addendum.job.interview.task.clients

import lt.addendum.job.interview.task.TestsSpecifications
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.web.reactive.function.client.WebClient

class ExternalQrClientTests extends TestsSpecifications {

    ExternalQrClient client

    MockWebServer server

    def FILE = "1234"

    @BeforeEach
    void setup() throws IOException {
        server = new MockWebServer()
        server.start()
        String rootUrl = server.url("/api/").toString()
        client = new ExternalQrClient(WebClient.create(rootUrl))
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown()
    }

    def "WebClient should work properly"() {
        setup:
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "image/png")
                .setBody(FILE)
        server.enqueue(response)

        and:
        byte[] byteArrray = FILE.getBytes("ASCII")

        InputStream byteStram = new ByteArrayInputStream(byteArrray)

        when:
        InputStream result = client.getQrCode(createBeneficiaryModel())
        RecordedRequest request = server.takeRequest()

        println request.getRequestUrl().queryParameter("data")

        then:
        request.getMethod().equals("GET")
        request.getPath().startsWith("/api/?data=Beneficiary%20information:%20%0D%0A%0DUnique_code:%207777%20%0D%0A%0DName:%20Test&size=small")
        result.read() == byteStram.read()
        request.getRequestUrl().queryParameter("size").equals("small")
        request.getRequestUrl().queryParameter("data").equals('''Beneficiary information: \r\n\rUnique_code: 7777 \r\n\rName: Test''')
        request.getRequestUrl().queryParameter("data").contains('''Beneficiary information:''')
        request.getRequestUrl().queryParameter("data").contains('''Unique_code: 7777''')
        request.getRequestUrl().queryParameter("data").contains('''Name: Test''')
    }
}
