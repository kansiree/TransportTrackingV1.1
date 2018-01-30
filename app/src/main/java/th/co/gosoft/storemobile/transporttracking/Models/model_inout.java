package th.co.gosoft.storemobile.transporttracking.Models;

/**
 * Created by Jubjang on 9/13/2017.
 */

public class model_inout {
    private String Head_name;       //Header Name
    private String Item_name;       //Item list Name
    private String Type;            //Head or Item
    private String Status;          //inprogress or success check with barcode
    private String inORout;         //inbox or outbox
    private String Barcode;
    private String Add;             //0 or 1 < 1 == add item >
    private String Type_order;

    public String getAdd() {
        return Add;
    }

    public void setAdd(String add) {
        Add = add;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getInORout() {
        return inORout;
    }

    public void setInORout(String inORout) {
        this.inORout = inORout;
    }

    public String getHead_name() {
        return Head_name;
    }

    public void setHead_name(String head_name) {
        Head_name = head_name;
    }

    public String getItem_name() {
        return Item_name;
    }

    public void setItem_name(String item_name) {
        Item_name = item_name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStatus() {return Status;}

    public void setStatus(String status) {
        Status = status;
    }

    public String getType_order() {
        return Type_order;
    }

    public void setType_order(String type_order) {
        Type_order = type_order;
    }
}
