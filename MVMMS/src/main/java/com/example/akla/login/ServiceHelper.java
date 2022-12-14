package com.example.akla.login;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public  class ServiceHelper {
    //Namespace of the Webservice - It is http://tempuri.org for .NET webservice
    //https://mobileservices.ceb.lk/cebwebservice/
    //119.235.6.179
    private static String NAMESPACE = "https://mobileservices.ceb.lk/mobileservice/";
    //Webservice URL - It is asmx file location hosted in the server in case of .Net
    //Change the IP address to your machine IP address
    private static String URL = "https://mobileservices.ceb.lk/mobileservice/?wsdl";
    //SOAP Action URI again http://tempuri.org
    private static String SOAP_ACTION = "https://mobileservices.ceb.lk/mobileservice/";

    public static String invokeWS(Map<String, String> properties, String webMethodName) {

        //System.setProperty("http.proxyUser", "sa5");
        //System.setProperty("http.proxyPassword", "sa5");

        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethodName);
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // Property which holds input parameters
            PropertyInfo p1 = new PropertyInfo();
            // Set Name
            p1.setName(key);
            // Set Value
            p1.setValue(value);
            // Set dataType
            p1.setType(String.class);
            // Add the property to request object
            request.addProperty(p1);
        }
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // allow ssl
            trustEveryone();

            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + webMethodName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response.toString();

        }
        catch (Exception e) {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            resTxt = e.toString();
        }
        //Return resTxt to calling object
        return resTxt;
    }


    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }

    public static class ProxyAuthenticator extends Authenticator {

        private String userName, password;

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password.toCharArray());
        }

        public ProxyAuthenticator(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }
    }
}