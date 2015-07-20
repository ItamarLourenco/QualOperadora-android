package com.isl.operadora.Request;

/**
 * Created by itamarlourenco on 19/07/15.
 */
public class URLRequest {
    protected static final String security = "#@qu4l0pe4d0r4@#";

    protected String[] server = new String[] {
            "http://192.168.0.16:8888/operadora/?tel=%s",
            //"http://consultanumero1.telein.com.br/sistema/consulta_numero.php?chave=senhasite&numero=%s",
            "http://consultanumero2.telein.com.br/sistema/consulta_numero.php?chave=senhasite&numero=%s",
            "http://consultanumero3.telein.com.br/sistema/consulta_numero.php?chave=senhasite&numero=%s"
    };

    protected final static String ERRO_LIMITE = "Telein.com.br Codigo 995";

    protected String formatUrl(String server, String number){
        return String.format(server, number);
    }
}
