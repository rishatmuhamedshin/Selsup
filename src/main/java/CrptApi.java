import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrptApi {

    private final String apiUrl;

    public CrptApi(String apiUrl) {
        this.apiUrl = apiUrl;
    }


    public void createDocument(Object document, String signature) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(apiUrl);

            httpPost.setHeader("Content-Type", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            String documentJson = objectMapper.writeValueAsString(document);

            String requestBody = String.format("{ \"product_document\": \"%s\", \"document_format\": \"MANUAL\", \"type\": \"LP_INTRODUCE_GOODS\", \"signature\": \"%s\" }", documentJson, signature);
            StringEntity entity = new StringEntity(requestBody);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    System.out.println("Document successfully created!");
                } else {
                    System.out.println("Failed to create the document. Status code: " + statusCode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String apiUrl = "https://ismp.crpt.ru/api/v3/lk/documents/create";
        CrptApi crptApi = new CrptApi(apiUrl);

        Description description = new Description("1234567890");

        Product product1 = new Product("cert1", "2023-07-27", "123", "owner1", "producer1", "2023-07-27", "tnved1", "uit1", "uitu1");
        Product product2 = new Product("cert2", "2023-07-28", "456", "owner2", "producer2", "2023-07-28", "tnved2", "uit2", "uitu2");
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        Document document = new Document(description, "doc123", "approved", "type1", true, "owner_inn", "participant_inn", "producer_inn", "2023-07-27", "type2", products, "2023-07-27", "reg123");
        String signature = "<Открепленная подпись в base64>";
        crptApi.createDocument(document, signature);
    }
}

class Document {

    private Description description;
    private String docId;
    private String docStatus;
    private String docType;
    private boolean importRequest;
    private String ownerInn;
    private String participantInn;
    private String producerInn;
    private String productionDate;
    private String productionType;
    private List<Product> products;
    private String regDate;
    private String regNumber;

    public Document(Description description, String docId, String docStatus, String docType, boolean importRequest,
                    String ownerInn, String participantInn, String producerInn, String productionDate, String productionType,
                    List<Product> products, String regDate, String regNumber) {
        this.description = description;
        this.docId = docId;
        this.docStatus = docStatus;
        this.docType = docType;
        this.importRequest = importRequest;
        this.ownerInn = ownerInn;
        this.participantInn = participantInn;
        this.producerInn = producerInn;
        this.productionDate = productionDate;
        this.productionType = productionType;
        this.products = products;
        this.regDate = regDate;
        this.regNumber = regNumber;
    }

    public Description getDescription() { return description; }
    public void setDescription(Description description) { this.description = description; }

    public String getDocId() { return docId; }
    public void setDocId(String docId) { this.docId = docId; }

    public String getDocStatus() { return docStatus; }
    public void setDocStatus(String docStatus) { this.docStatus = docStatus; }

    public String getDocType() { return docType; }
    public void setDocType(String docType) { this.docType = docType; }

    public boolean isImportRequest() { return importRequest; }
    public void setImportRequest(boolean importRequest) { this.importRequest = importRequest; }

    public String getOwnerInn() { return ownerInn; }
    public void setOwnerInn(String ownerInn) { this.ownerInn = ownerInn; }

    public String getParticipantInn() { return participantInn; }
    public void setParticipantInn(String participantInn) { this.participantInn = participantInn; }

    public String getProducerInn() { return producerInn; }
    public void setProducerInn(String producerInn) { this.producerInn = producerInn; }

    public String getProductionDate() { return productionDate; }
    public void setProductionDate(String productionDate) { this.productionDate = productionDate; }

    public String getProductionType() { return productionType; }
    public void setProductionType(String productionType) { this.productionType = productionType; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public String getRegDate() { return regDate; }
    public void setRegDate(String regDate) { this.regDate = regDate; }

    public String getRegNumber() { return regNumber; }
    public void setRegNumber(String regNumber) { this.regNumber = regNumber; }
}

class Description {
    private String participantInn;

    public Description(String participantInn) {
        this.participantInn = participantInn;
    }

    public String getParticipantInn() {
        return participantInn;
    }

    public void setParticipantInn(String participantInn) {
        this.participantInn = participantInn;
    }
}

class Product {
    private String certificateDocument;
    private String certificateDocumentDate;
    private String certificateDocumentNumber;
    private String ownerInn;
    private String producerInn;
    private String productionDate;
    private String tnvedCode;
    private String uitCode;
    private String uituCode;

    public Product(String certificateDocument, String certificateDocumentDate, String certificateDocumentNumber,
                   String ownerInn, String producerInn, String productionDate, String tnvedCode, String uitCode, String uituCode) {
        this.certificateDocument = certificateDocument;
        this.certificateDocumentDate = certificateDocumentDate;
        this.certificateDocumentNumber = certificateDocumentNumber;
        this.ownerInn = ownerInn;
        this.producerInn = producerInn;
        this.productionDate = productionDate;
        this.tnvedCode = tnvedCode;
        this.uitCode = uitCode;
        this.uituCode = uituCode;
    }

    public String getCertificateDocument() { return certificateDocument; }
    public void setCertificateDocument(String certificateDocument) { this.certificateDocument = certificateDocument; }

    public String getCertificateDocumentDate() { return certificateDocumentDate; }
    public void setCertificateDocumentDate(String certificateDocumentDate) { this.certificateDocumentDate = certificateDocumentDate; }

    public String getCertificateDocumentNumber() { return certificateDocumentNumber; }
    public void setCertificateDocumentNumber(String certificateDocumentNumber) { this.certificateDocumentNumber = certificateDocumentNumber; }

    public String getOwnerInn() { return ownerInn; }
    public void setOwnerInn(String ownerInn) { this.ownerInn = ownerInn; }

    public String getProducerInn() { return producerInn; }
    public void setProducerInn(String producerInn) { this.producerInn = producerInn; }

    public String getProductionDate() { return productionDate; }
    public void setProductionDate(String productionDate) { this.productionDate = productionDate; }

    public String getTnvedCode() { return tnvedCode; }
    public void setTnvedCode(String tnvedCode) { this.tnvedCode = tnvedCode; }

    public String getUitCode() { return uitCode; }
    public void setUitCode(String uitCode) { this.uitCode = uitCode; }

    public String getUituCode() { return uituCode; }
    public void setUituCode(String uituCode) { this.uituCode = uituCode; }
}

