package com.bezzo.coreandroid.data.network;

import com.bezzo.coreandroid.base.*;

import io.reactivex.functions.*;
import retrofit2.*;

public abstract class RequestRxHandler<M> implements Consumer<BaseResponse<M>> {

    private Integer successCode;

    public RequestRxHandler(Integer successCode){
        this.successCode = successCode;
    }

    public abstract void onSuccess(M model);

    public abstract void onAuthorized();

    public abstract void onError(BaseResponse<M> response);

    @Override
    public void accept(BaseResponse<M> response) {
        if (response.getCode() == successCode){
            onSuccess(response.getData());
        }
        else if (response.getCode() == 401){
            onAuthorized();
        }
        else {
            onError(response);
        }
    }
}
