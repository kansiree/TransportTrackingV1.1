package th.co.gosoft.storemobile.transporttracking.Models;

import java.util.ArrayList;

/**
 * Created by print on 12/22/2017.
 */

public class model_Expanlist {

    ArrayList<model_item> items;

    public ArrayList<model_item> getItems() {
        return items;
    }

    public void setItems(ArrayList<model_item> items) {
        this.items = items;
    }

    public static class model_item{
        String itemIN;
        String itemOut;
        String status;
        String phon;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPhon() {
            return phon;
        }

        public void setPhon(String phon) {
            this.phon = phon;
        }

        public String getItemIN() {
            return itemIN;
        }

        public void setItemIN(String itemIN) {
            this.itemIN = itemIN;
        }

        public String getItemOut() {
            return itemOut;
        }

        public void setItemOut(String itemOut) {
            this.itemOut = itemOut;
        }
    }
}
