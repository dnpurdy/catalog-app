package com.purdynet.siqproduct.biqquery;

import com.google.api.client.googleapis.json.GoogleJsonError;

import java.util.List;


public class GoogleJsonErrorDTO {
    /** List of detailed errors or {@code null} for none. */
    private List<GoogleJsonError.ErrorInfo> errors;

    /** HTTP status code of this response or {@code null} for none. */
    private int code;

    /** Human-readable explanation of the error or {@code null} for none. */
    private String message;

    public List<GoogleJsonError.ErrorInfo> getErrors() {
        return errors;
    }

    public void setErrors(List<GoogleJsonError.ErrorInfo> errors) {
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}