package application.influx;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;

public class InfluxDBConnector {
    private String token;
    private String bucket;
    private String Org;
    private String Url;

    public InfluxDBClient buildConnection(String url, String token, String bucket, String org) {

        setToken(token);

        setBucket(bucket);

        setOrg(org);

        setUrl(url);

        return InfluxDBClientFactory.create(getUrl(), getToken().toCharArray(), getOrg(), getBucket());

    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getOrg() {
        return Org;
    }

    public void setOrg(String org) {
        Org = org;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
