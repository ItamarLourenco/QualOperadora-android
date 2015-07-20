package com.isl.operadora.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonSyntaxException;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.Util.Logger;
import com.isl.operadora.Util.Util;

/**
 * Created by webx on 09/04/15.
 */
public abstract class ContactRequest extends URLRequest {
    public static int countCalLServer = 0;
    private String[] phones;

    public ContactRequest(final String[] phones){
        this.phones = phones;
        startCall();
    }

    private void startCall() {
        if(ContactRequest.countCalLServer > server.length){
            ContactRequest.countCalLServer = 0;
        }

        if(this.phones.length >= 0 && server.length >= 0){
            String url = formatUrl(server[countCalLServer], this.phones[0]);
            callServer(url);

            return;
        }
        onFinish(null);
    }

    private void callServer(final String url){
        ContactRequest.countCalLServer++;

        Portabily portabily = new Portabily();
        final Portabily.PullPortabily getPortabily = portabily.getPortabily();
        String timeStamp = Util.getTimeStamp();
        getPortabily.setTokenId(timeStamp);
        getPortabily.setTokenCryp(Util.md5(security + timeStamp));
        getPortabily.setPhones(phones);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            handleResult(response);
                        }catch (JsonSyntaxException e){
                            handleResult(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        onFinish(null);
                    }
                })
        {
            @Override
            public byte[] getBody() throws AuthFailureError
            {
                return getPortabily.getJson().getBytes();
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void handleResult(String response){
        //Portabily.PushPortabily pushPortabily = AppController.GSON.fromJson(response, Portabily.PushPortabily.class);
        if(ERRO_LIMITE.equals(response)){
            startCall();
        }

        Logger.t("OK");
    }

    public abstract void onFinish(Portabily.PushPortabily portabily);
}
