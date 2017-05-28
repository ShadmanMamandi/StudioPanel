package ir.valinejad.entity;

import ir.valinejad.commons.Constants;

public class PhotoResponse {
    private long photo_id;
    private String photo_url;

    public long getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(long photo_id) {
        this.photo_id = photo_id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = Constants.SERVER_IP + "/ib/" + photo_url;
    }
}
