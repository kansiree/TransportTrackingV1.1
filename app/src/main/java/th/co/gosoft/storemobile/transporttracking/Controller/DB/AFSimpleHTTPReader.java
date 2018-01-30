package th.co.gosoft.storemobile.transporttracking.Controller.DB;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Jubjang on 9/25/2017.
 */

public class AFSimpleHTTPReader {
    private int connectionTimeOut = 10000;
    private int readTimeOut = 20000;
    final private int retry = 10;
    final private long sleepForRetry = 1000;

    public static final String METHOD_GET ="GET";
    public static final String METHOD_POST ="POST";
    private String method = METHOD_GET;

    private HashMap<String, String> header;

    public ByteArrayOutputStream readHTML(URL url, String postData) throws IOException, Exception {
        return isHttps(url) ? readHttps(url) : readHttp(url, postData);

    }

    public boolean isHttps(URL url) {
        return "https".equalsIgnoreCase(url.getProtocol()) ? true : false;
    }

    public boolean isHttp(URL url) {
        return "http".equalsIgnoreCase(url.getProtocol()) ? true : false;
    }

    public boolean isFile(URL url) {
        return "file".equalsIgnoreCase(url.getProtocol()) ? true : false;
    }

    public HttpURLConnection addHeader(HttpURLConnection http)
    {
        if(getHeader()==null)return http;

        for(String key:getHeader().keySet())
        {
            http.setRequestProperty(key,getHeader().get(key));
            //	Logs.i("add "+key+":"+getHeader().get(key));
        }
        return http;
    }
    public HttpURLConnection addHeader(HttpsURLConnection https)
    {
        if(getHeader()==null)return https;

        for(String key:getHeader().keySet())
        {
            https.setRequestProperty(key,getHeader().get(key));
        }
        return https;
    }
    private ByteArrayOutputStream readHttp(URL url, String postData) throws IOException, Exception {
        HttpURLConnection http = null;
        ByteArrayOutputStream bout = null;
        System.setProperty("http.keepAlive", "false");
        int retryCount = -1;
        while (retryCount < retry) {
            try {
                retryCount++;
                http = (HttpURLConnection) url.openConnection();
                http.setDoOutput("GET".equalsIgnoreCase(getMethod())?false:true);
                http.setDoInput(true);
                http.setRequestMethod(getMethod());
                http.setConnectTimeout(getConnectionTimeOut());
                http.setReadTimeout(getReadTimeOut());

                http.setRequestProperty("Connection", "close");
                addHeader(http);
                http.connect();

                String out = "";
                if (postData != null) {
//					JSONObject object = new JSONObject();
//					for (String key : postData.keySet()) {
//						object.put(key, postData.get(key));
//						AFLogUtils.i("key = " + postData.get(key));
//					}
                    //String message = object.toString();

                    // Send request
                    OutputStream os = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(postData);
                    writer.flush();
                    writer.close();
                    os.close();
                }


                if (http.getResponseCode() >= 200
                        && http.getResponseCode() < 300) {
                    //	Log.i(Logs.TAG, "Accepted code:" + http.getResponseCode());
                    InputStream ins = http.getInputStream();
                    byte[] buf = new byte[256];
                    int read;
                    bout = new ByteArrayOutputStream();
                    while ((read = ins.read(buf)) > 0) {
                        bout.write(buf, 0, read);
                    }

                } else {
                    throw new Exception("HTTP read error : "
                            + http.getResponseCode() + ":"
                            + http.getResponseMessage()+"["+url+"]");
                }
                break;

            } finally {
                if (http != null) {
                    http.disconnect();
                }
            }
        }
        return bout;

    }

    private ByteArrayOutputStream readHttps(URL url) throws IOException,
            Exception {
        HttpsURLConnection https = null;
        ByteArrayOutputStream bout = null;
        int retryCount = -1;
        while (retryCount < retry) {
            try {
                trustServer();
                https = (HttpsURLConnection) url.openConnection();
                https.setDoOutput("GET".equalsIgnoreCase(getMethod())?false:true);
                https.setDoInput(true);
                https.setRequestMethod(getMethod());
                https.setConnectTimeout(getConnectionTimeOut());
                https.setReadTimeout(getReadTimeOut());
                https.setRequestProperty("Connection", "close");
                addHeader(https);
                https.connect();
                if (https.getResponseCode() >= 200
                        && https.getResponseCode() < 300) {
                    //Log.i(Logs.TAG, "Accepted code:" + https.getResponseCode());
                    InputStream ins = https.getInputStream();
                    byte[] buf = new byte[256];
                    int read;

                    bout = new ByteArrayOutputStream();
                    while ((read = ins.read(buf)) > 0) {
                        bout.write(buf, 0, read);
                    }

                } else {
                    throw new Exception("HTTPS read error : "
                            + https.getResponseCode() + ":"
                            + https.getResponseMessage()+"["+url+"]");
                }
                break;

            } finally {
                if (https != null) {
                    https.disconnect();
                }
            }
        }

        return bout;

    }

    private void trustServer() {
        try {
            // TrustHost//

            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs,
                        String authType) {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs,
                        String authType) {
                }
            } };

            SSLContext sc = SSLContext.getInstance("SSLv3");
            HostnameVerifier hv = new HostnameVerifier() {

                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            };
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the connectionTimeOut
     */
    public int getConnectionTimeOut() {
        return this.connectionTimeOut;
    }

    /**
     * @param connectionTimeOut
     *            the connectionTimeOut to set
     */
    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    /**
     * @return the readTimeOut
     */
    public int getReadTimeOut() {
        return this.readTimeOut;
    }

    /**
     * @param readTimeOut
     *            the readTimeOut to set
     */
    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * @param method
     *            the method to set
     */
    public void setMethod(String method) {

        this.method = method;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }
}


