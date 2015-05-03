package com.isl.operadora.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonSyntaxException;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.Util.Util;

/**
 * Created by webx on 09/04/15.
 */
public abstract class ContactRequest {

    private final String url = "http://54.200.133.35/qualOperadora/web/index.php?r=api/portabilidade";
    private static final String security = "#@qu4l0pe4d0r4@#";

    public ContactRequest(final String[] phones){

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
                            Portabily.PushPortabily pushPortabily = AppController.GSON.fromJson(response, Portabily.PushPortabily.class);
                            onFinish(pushPortabily);
                        }catch (JsonSyntaxException e){
                            onFinish(null);
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

    public abstract void onFinish(Portabily.PushPortabily portabily);
}
