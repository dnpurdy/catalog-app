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
import com.google.api.services.bigquery.model.Job;
import com.google.api.services.bigquery.model.JobConfiguration;
import com.google.api.services.bigquery.model.JobConfigurationQuery;
import com.google.api.services.bigquery.model.JobReference;
import com.google.api.services.bigquery.model.Table;
import com.google.api.services.bigquery.model.TableDataList;
import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableReference;
import com.google.api.services.bigquery.model.TableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Utility methods for beginning jobs, waiting for jobs, and instantiating Bigqueries.
 *
 * @author lparkinson@google.com (Laura Parkinson)
 */
public class BigqueryUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int TIMEOUT_MS = 60 * 25 * 1000; // TWENTY FIVE MINUTES
    private static final int WAIT_MS = 1000; // ONE SECOND
    private static final int MAX_INTERVAL = 60000;

    static final String projectId =
            System.getProperty("com.google.api.client.sample.bigquery.appengine.dashboard.projectId");

    private final String userId;
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
                    new InputStreamReader(BigqueryUtils.class.getResourceAsStream("/client_secrets.json")));
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

    public BigqueryUtils() throws IOException {
        this.userId = "david@swiftiq.com";
        //this.bigquery = initializeAndBuild(GoogleCredential.getApplicationDefault());
        this.bigquery = initializeAndBuild(getCredi());
    }

    private GoogleCredential getCredi() {
        try {
            InputStream credentialsStream = BigqueryUtils.class.getResourceAsStream("/client_secrets.json");
            return GoogleCredential.fromStream(credentialsStream, HTTP_TRANSPORT, JSON_FACTORY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
          //      .setJsonFactory(JSON_FACTORY).setClientSecrets(GoogleClientSecrets.load(JSON_FACTORY, ))
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

    public BigqueryUtils(String userId) throws IOException {
        this(userId, null);
    }

    public BigqueryUtils(String userId, final String jobId) throws IOException {
        this.userId = userId;

        bigquery = loadBigqueryClient(userId);

        if (jobId != null) {
            job = tryToDo(new Callable<Job>() {
                @Override
                public Job call() throws Exception {
                    return bigquery.jobs().get(projectId, jobId).execute();
                }
            });

            if (job == null) {
                throw new RuntimeException("Wasn't able to get a job for jobId " + jobId);
            }
        }
    }

    public void beginQuery(String query) throws RuntimeException {
        final Job queryJob = makeJob(query);

        job = tryToDo(new Callable<Job>() {
            @Override
            public Job call() throws Exception {
                return bigquery.jobs().insert(projectId, queryJob).execute();
            }
        });

        Preconditions.checkNotNull(job);
        //enqueueWaitingTask();
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

            Table table = tryToDo(new Callable<Table>() {
                @Override
                public Table call() throws IOException {
                    return bigquery.tables().get(tableReference.getProjectId(), tableReference.getDatasetId(),
                            tableReference.getTableId()).execute();
                }
            });

            Preconditions.checkNotNull(table);
            Preconditions.checkNotNull(table.getSchema());
            Preconditions.checkNotNull(table.getSchema().getFields());
            return table.getSchema().getFields();
        }
        return null;
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

    public Job pollJob(String projectId, String jobId) {
        return tryToDo(new Callable<Job>() {
            @Override
            public Job call() throws Exception {
                return bigquery.jobs().get(projectId, jobId).execute();
            }
        });
    }
}