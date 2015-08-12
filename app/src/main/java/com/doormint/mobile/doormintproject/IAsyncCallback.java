package com.doormint.mobile.doormintproject;

/**
 * Created by adhiraj on 8/8/15.
 */
public interface IAsyncCallback {

    public void onSuccessResponse(String successResponse);

    public void onErrorResponse(int errorCode , String errorResponse );
}
