package com.internship.stock_alert_service.errors;

import result.ApiError;

public final class AlertErrors {

    private AlertErrors() {}

    public static ApiError notFound(Long id){
        return ApiError.notFound(
                "Alert.AlertNotFound",
                "Alert with ID " + id + " not found"
        );
    }

    public static ApiError unauthorized(){
        return ApiError.notFound(
                "Alert.UnauthorizedAccess",
                "You do not have permission to access this alert"
        );
    }

    public static ApiError creationProblem(String message){
        return ApiError.problem(
                "Alert.CreationProblem",
                message
        );
    }

    public static ApiError alreadyExists(String symbol){
        return ApiError.conflict(
                "Alert.AlreadyExists",
                "You already have a pending alert with these exact parameters for " + symbol
        );
    }
}
