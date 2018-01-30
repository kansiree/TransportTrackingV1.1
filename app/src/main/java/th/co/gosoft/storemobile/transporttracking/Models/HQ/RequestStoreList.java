package th.co.gosoft.storemobile.transporttracking.Models.HQ;

import java.util.List;

/**
 * Created by Jubjang on 9/25/2017.
 */

public class RequestStoreList {
    String returnCode;
    String returnDescription;
    private List<StoreModel> storemodel;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDescription() {
        return returnDescription;
    }

    public void setReturnDescription(String returnDescription) {
        this.returnDescription = returnDescription;
    }

    public List<StoreModel> getStoremodel() {
        return storemodel;
    }

    public void setStoremodel(List<StoreModel> storemodel) {
        this.storemodel = storemodel;
    }

    public static class StoreModel {
        String storeID;
        String storeName;
        String storePhone;
        String storeLat;
        String storeLng;
        String storeType;

        public String getStoreType() {
            return storeType;
        }

        public void setStoreType(String storeType) {
            this.storeType = storeType;
        }

        public String getStoreID() {
            return storeID;
        }

        public void setStoreID(String storeID) {
            this.storeID = storeID;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStorePhone() {
            return storePhone;
        }

        public void setStorePhone(String storePhone) {
            this.storePhone = storePhone;
        }

        public String getStoreLat() {
            return storeLat;
        }

        public void setStoreLat(String storeLat) {
            this.storeLat = storeLat;
        }

        public String getStoreLng() {
            return storeLng;
        }

        public void setStoreLng(String stotrLng) {
            this.storeLng = stotrLng;
        }
    }

    private List<Item> item;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public static class Item {
        String productStoreID;
        List<ItemInOut> itemInOut;

        public String getProductStoreID() {
            return productStoreID;
        }

        public void setProductStoreID(String productStoreID) {
            this.productStoreID = productStoreID;
        }

        public List<ItemInOut> getItemInOut() {
            return itemInOut;
        }

        public void setItemInOut(List<ItemInOut> itemInOut) {
            this.itemInOut = itemInOut;
        }

        public static class ItemInOut {
            String productCode;
            String productName;
            String productType;
            String productStatus;
            String productAdd;

            public String getProductAdd() {
                return productAdd;
            }

            public void setProductAdd(String productAdd) {
                this.productAdd = productAdd;
            }

            public String getProductStatus() {
                return productStatus;
            }

            public void setProductStatus(String productStatus) {
                this.productStatus = productStatus;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductType() {
                return productType;
            }

            public void setProductType(String productType) {
                this.productType = productType;
            }

        }
    }

}

