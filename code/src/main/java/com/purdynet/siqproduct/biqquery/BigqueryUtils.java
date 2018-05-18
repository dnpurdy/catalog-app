package com.purdynet.siqproduct.biqquery;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.googleapis.services.json.CommonGoogleJsonClientRequestInitializer;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Preconditions;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryRequest;
import com.google.api.services.bigquery.BigqueryScopes;
import com.google.api.services.bigquery.model.*;
import com.purdynet.siqproduct.util.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility methods for beginning jobs, waiting for jobs, and instantiating Bigqueries.
 *
 * @author lparkinson@google.com (Laura Parkinson)
 */
public class BigqueryUtils {

    private static final Logger logger = LoggerFactory.getLogger(BigqueryUtils.class);

    private static final int TIMEOUT_MS = 60 * 25 * 1000; // TWENTY FIVE MINUTES
    private static final int WAIT_MS = 1000; // ONE SECOND
    private static final int MAX_INTERVAL = 60000;

    private static final String projectId = System.getProperty("com.google.api.client.sample.bigquery.appengine.dashboard.projectId") == null ? "swiftiq-master" :
        System.getProperty("com.google.api.client.sample.bigquery.appengine.dashboard.projectId");

    final Bigquery bigquery;
    private Job job;

    /** Global instance of the HTTP transport. */
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /** Global instance of the JSON factory. */
    static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static GoogleClientSecrets clientSecrets = null;

    static GoogleClientSecrets getClientCredential() throws IOException {
        if (clientSecrets == null) {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                    new InputStreamReader(getClientSecretsStream()));
            Preconditions.checkArgument(!clientSecrets.getDetails().getClientId().startsWith("Enter ")
                            && !clientSecrets.getDetails().getClientSecret().startsWith("Enter "),
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=bigquery "
                            + "into bigquery-appengine-sample/src/main/resources/client_secrets.json");
        }
        return clientSecrets;
    }

    static GoogleAuthorizationCodeFlow newFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                getClientCredential(), Collections.singleton(BigqueryScopes.BIGQUERY)).setAccessType("offline").build();
    }

    static Bigquery loadBigqueryClient(String userId) throws IOException {
        Credential credential = newFlow().loadCredential(userId);
        return new Bigquery.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();
    }

    public BigqueryUtils() {
        //this.bigquery = initializeAndBuild(GoogleCredential.getApplicationDefault());
        this.bigquery = initializeAndBuild(getCredential());
    }

    private GoogleCredential getCredential() {
        try {
            return GoogleCredential.fromStream(getClientSecretsStream(), HTTP_TRANSPORT, JSON_FACTORY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream getClientSecretsStream() {
        return BigqueryUtils.class.getResourceAsStream("/client_secrets.json");
    }

    private Bigquery initializeAndBuild(HttpRequestInitializer credential) {
        GoogleClientRequestInitializer initializer = new CommonGoogleJsonClientRequestInitializer() {
            public void initialize(AbstractGoogleJsonClientRequest request) {
                BigqueryRequest bigqueryRequest = (BigqueryRequest) request;
                bigqueryRequest.setPrettyPrint(true);
            }
        };

        return new Bigquery.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("SwiftIQ").setHttpRequestInitializer(credential)
                .setGoogleClientRequestInitializer(initializer).build();
    }

    public void beginQuery(String query) throws RuntimeException {
        final Job queryJob = makeJob(query);
        job = tryToDo(() -> bigquery.jobs().insert(projectId, queryJob).execute());
        Preconditions.checkNotNull(job);
    }

    public boolean jobSucceeded() {
        return (job != null && job.getStatus().getErrorResult() == null);
    }

    public String getJobErrorMessage() {
        if (job != null && job.getStatus().getErrorResult() != null) {
            return job.getStatus().getErrorResult().getMessage();
        }
        return "";
    }

    public boolean jobIsDone() {
        String status = getJobStatus();
        return (status != null && ("DONE").equalsIgnoreCase(status));
    }

    public String getJobStatus() {
        return (job != null) ? job.getStatus().getState() : null;
    }

    public List<TableFieldSchema> getSchemaFieldNames() throws RuntimeException {
        if (job != null) {
            final TableReference tableReference = job.getConfiguration().getQuery().getDestinationTable();

            Table table = getTable(tableReference);

            Preconditions.checkNotNull(table);
            Preconditions.checkNotNull(table.getSchema());
            Preconditions.checkNotNull(table.getSchema().getFields());
            return table.getSchema().getFields();
        }
        return null;
    }

    public Table getTable(TableReference tableReference) throws RuntimeException {
        return tryToDo(() -> bigquery.tables().get(tableReference.getProjectId(), tableReference.getDatasetId(), tableReference.getTableId()).execute());
    }

    public BqTableData getBqTableData() throws RuntimeException {
        if (job != null) {
            BqTableData bqTableData = new BqTableData(job.getConfiguration().getQuery().getDestinationTable());
            bqTableData.setSchemaFieldNames(getSchemaFieldNames());
            bqTableData.setTableRowList(getTableData());
            return bqTableData;
        } else {
            return null;
        }
    }

    public List<TableRow> getTableData() throws RuntimeException {
        if (job != null) {
            final TableReference tableReference = job.getConfiguration().getQuery().getDestinationTable();

            TableDataList tableDataList = tryToDo(new Callable<TableDataList>() {
                @Override
                public TableDataList call() throws IOException {
                    return bigquery.tabledata().list(tableReference.getProjectId(),
                            tableReference.getDatasetId(), tableReference.getTableId()).execute();
                }
            });

            Preconditions.checkNotNull(tableDataList);
            Preconditions.checkNotNull(tableDataList.getRows());
            return tableDataList.getRows();
        }
        return null;
    }

    /**
     * Instantiates an example job and sets required fields.
     */
    private Job makeJob(String query) {
        JobConfigurationQuery jobconfigurationquery = new JobConfigurationQuery();

        jobconfigurationquery.setQuery(query);
        jobconfigurationquery.setCreateDisposition("CREATE_IF_NEEDED");

        JobConfiguration jobconfiguration = new JobConfiguration();
        jobconfiguration.setQuery(jobconfigurationquery);

        JobReference jobreference = new JobReference();
        jobreference.setProjectId(projectId);

        Job newJob = new Job();
        newJob.setConfiguration(jobconfiguration);
        newJob.setJobReference(jobreference);

        return newJob;
    }

    private <T> T tryToDo(Callable<T> callback) throws RuntimeException {
        int retries = 3;
        int currentTry = 0;
        RuntimeException sdex = null;
        while (currentTry < retries) {
            currentTry++;
            try {
                return callback.call();
            } catch (Exception ex) {
                sdex = new RuntimeException(ex);
                logger.warn("Caught exception (): " + ex);
            }
        }
        throw Preconditions.checkNotNull(sdex);
    }

    ///

    public String getJobId() {
        return this.job.getJobReference().getJobId();
    }

    public Job pollForCompletion() {
        return pollForCompletion(getJobId(), this.projectId, false);
    }

    public Job pollForCompletion(final String jobId, final String projectId, final Boolean exponentialDelay) {
        try {
            long startTime = System.currentTimeMillis();
            int interval = WAIT_MS;

            while ((System.currentTimeMillis() - startTime) < TIMEOUT_MS) {
                Job pollJob = pollJob(projectId, jobId);
                logger.info("Job status: " + pollJob.getStatus().getState());
                if (pollJob.getStatus().getState().equals("DONE")) {
                    logger.info(String.format("Job completed. [%d ms]", System.currentTimeMillis() - startTime));
                    return pollJob;
                }
                if (exponentialDelay) {
                    Thread.sleep(interval);
                    interval = Math.min(MAX_INTERVAL, interval * 2);
                } else {
                    Thread.sleep(WAIT_MS);
                }
            }
            throw new BigqueryServiceException("Timeout. Bigquery job was not able to be completed.");
        } catch (InterruptedException e) {
            throw new BigqueryServiceException("Poll for Completion failed!", e);
        }
    }

    private Job pollJob(String projectId, String jobId) {
        return tryToDo(() ->  bigquery.jobs().get(projectId, jobId).execute());
    }

    public static BigqueryUtils runQuerySync(final String sql) {
        logger.info(sql);

        BigqueryUtils bigqueryUtils = new BigqueryUtils();
        bigqueryUtils.beginQuery(sql);
        bigqueryUtils.pollForCompletion();

        return bigqueryUtils;
    }

    public static <T> List<T> convertTableRowToModel(BqTableData bqTableData, Function<TableRow,T> ofFunc) {
        if (bqTableData != null) {
            try {
                return bqTableData.getTableRowList().stream().map(ofFunc).collect(Collectors.toList());
            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    public void extractTable(TableReference tableReference, String destinationUri) {
        JobReference jobReference = new JobReference();
        jobReference.setProjectId(projectId);
        jobReference.setJobId(UUID.randomUUID().toString());

        JobConfiguration configuration = createJobConfigurationExtract(tableReference,
                ListUtils.asSingleton(destinationUri));

        final Job extractJob = new Job();
        extractJob.setConfiguration(configuration);
        extractJob.setJobReference(jobReference);

        job = tryToDo(() -> bigquery.jobs().insert(tableReference.getProjectId(), extractJob).execute());
    }


    public JobConfiguration createJobConfigurationExtract(TableReference tableReference, List<String> destinationUris) {
        JobConfigurationExtract extractConfig = new JobConfigurationExtract();
        extractConfig.setSourceTable(tableReference);
        extractConfig.setDestinationUris(destinationUris);
        extractConfig.setDestinationFormat("CSV");
        extractConfig.setCompression("NONE");

        JobConfiguration jobConfig = new JobConfiguration();
        jobConfig.setExtract(extractConfig);
        return jobConfig;
    }
}
