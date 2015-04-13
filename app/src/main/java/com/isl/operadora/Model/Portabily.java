package com.isl.operadora.Model;

import com.google.gson.annotations.SerializedName;
import com.isl.operadora.Application.AppController;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by webx on 09/04/15.
 */
public class Portabily {

    private PullPortabily getPortabily = new PullPortabily();
    public PullPortabily getPortabily(){
        return getPortabily;
    }

    public class PullPortabily {
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

    public class PushPortabily{
        private String status;
        private String message;
        private DataPortabily[] data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ArrayList<DataPortabily> getData() {
            ArrayList<DataPortabily> dataPortabiliesList = new ArrayList<DataPortabily>(Arrays.asList(data));
            return dataPortabiliesList;
        }

        public void setData(DataPortabily[] data) {
            this.data = data;
        }

        public class DataPortabily{
            private String phone;
            private boolean portabilidade;
            private boolean encontrado;
            private String uf;
            private String ddd;
            private String rn1;
            private String prefixo;
            private String operadora;
            private String date;
            @SerializedName("operadora_anterior") private String operadoraAnterior;

            public boolean isPortabilidade() {
                return portabilidade;
            }

            public void setPortabilidade(boolean portabilidade) {
                this.portabilidade = portabilidade;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public boolean isEncontrado() {
                return encontrado;
            }

            public void setEncontrado(boolean encontrado) {
                this.encontrado = encontrado;
            }

            public String getUf() {
                return uf;
            }

            public void setUf(String uf) {
                this.uf = uf;
            }

            public String getDdd() {
                return ddd;
            }

            public void setDdd(String ddd) {
                this.ddd = ddd;
            }

            public String getRn1() {
                return rn1;
            }

            public void setRn1(String rn1) {
                this.rn1 = rn1;
            }

            public String getPrefixo() {
                return prefixo;
            }

            public void setPrefixo(String prefixo) {
                this.prefixo = prefixo;
            }

            public String getOperadora() {
                return operadora;
            }

            public void setOperadora(String operadora) {
                this.operadora = operadora;
            }

            public String getDate() {
                return date;
            }

            public void setOperadoraAnterior(String rn1Anterior){
                this.operadoraAnterior = rn1Anterior;
            }

            public String getOperadoraAnterior(){
                return operadoraAnterior;
            }
        }
    }
}
