package com.purdynet.siqproduct.biqquery;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class BigqueryServiceException extends RuntimeException {

    private int statusCode;
    private GoogleJsonErrorDTO googleJsonErrorDto = new GoogleJsonErrorDTO();
    private GoogleJsonResponseException originalGoogleJsonResponseException;

    public BigqueryServiceException(String message, Throwable t) {
        super(message, t);

        if (t instanceof GoogleJsonResponseException) {
            GoogleJsonResponseException ge = (GoogleJsonResponseException) t;
            populateGoogleJsonError(ge);
        }
    }

    public BigqueryServiceException(String message) {
        super(message);
    }

    public GoogleJsonErrorDTO getGoogleJsonError() {
        return googleJsonErrorDto;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public GoogleJsonResponseException getOriginalGoogleJsonResponseException() {
        return originalGoogleJsonResponseException;
    }

    public void setOriginalGoogleJsonResponseException(
            GoogleJsonResponseException originalGoogleJsonResponseException) {
        this.originalGoogleJsonResponseException = originalGoogleJsonResponseException;
    }

    private void populateGoogleJsonError(GoogleJsonResponseException e) {
        setOriginalGoogleJsonResponseException(e);
        setStatusCode(e.getStatusCode());
        googleJsonErrorDto.setCode(e.getDetails().getCode());
        googleJsonErrorDto.setMessage(e.getDetails().getMessage());

        List<GoogleJsonError.ErrorInfo> errors = new ArrayList<>();
        for (GoogleJsonError.ErrorInfo ei : e.getDetails().getErrors()) {
            GoogleJsonError.ErrorInfo errorInfo = new GoogleJsonError.ErrorInfo();

            errorInfo.setDomain(ei.getDomain());
            errorInfo.setLocation(ei.getLocation());
            errorInfo.setLocationType(ei.getLocationType());
            errorInfo.setMessage(ei.getMessage());
            errorInfo.setReason(ei.getReason());
            errors.add(errorInfo);
        }
        googleJsonErrorDto.setErrors(errors);
    }
}