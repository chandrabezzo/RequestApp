package com.bezzo.coreandroid.data.network;

import io.reactivex.functions.*;
import retrofit2.*;

public abstract class RequestOkHttpHandler<M> implements Consumer<Response<M>> {

    private Integer successCode;

    public RequestOkHttpHandler(Integer successCode){
        this.successCode = successCode;
    }

    public abstract void onSuccess(M model);

    public abstract void onAuthorized();

    public abstract void onError(Response<M> response);

    @Override
    public void accept(Response<M> response) {
        if (response.code() == successCode){
            onSuccess(response.body());
        }
        else if (response.code() == 401){
            onAuthorized();
        }
        else {
            onError(response);
        }
    }
}
