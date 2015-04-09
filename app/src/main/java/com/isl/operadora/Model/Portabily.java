package com.isl.operadora.Model;

import com.google.gson.annotations.SerializedName;
import com.isl.operadora.Application.AppController;

/**
 * Created by webx on 09/04/15.
 */
public class Portabily {

    private GetPortabily getPortabily = new GetPortabily();
    public GetPortabily getPortabily(){
        return getPortabily;
    }

    public class GetPortabily{
        @SerializedName("token_id") public String tokenId;
        @SerializedName("token_cryp") public String tokenCryp;
        public String[] phones;

        public String getTokenId() {
            return tokenId;
        }

        public void setTokenId(String tokenId) {
            this.tokenId = tokenId;
        }

        public String getTokenCryp() {
            return tokenCryp;
        }

        public void setTokenCryp(String tokenCryp) {
            this.tokenCryp = tokenCryp;
        }

        public String[] getPhones() {
            return phones;
        }

        public void setPhones(String[] phone) {
            this.phones = phone;
        }

        public String getJson(){
            return AppController.GSON.toJson(this);
        }
    }
}
