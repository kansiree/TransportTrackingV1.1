package th.co.gosoft.storemobile.transporttracking.Models;

/**
 * Created by print on 1/29/2018.
 */

public class model_send_on_shelf {
    private int num_sent;

    public int getNum_sent() {
        return num_sent;
    }

    public void setNum_sent(int num_sent) {
        this.num_sent = num_sent;
    }

    public String getTote_id() {
        return tote_id;
    }

    public void setTote_id(String tote_id) {
        this.tote_id = tote_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    private String tote_id;
    private String num;


}
