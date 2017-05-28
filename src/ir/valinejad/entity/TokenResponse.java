package ir.valinejad.entity;

public class TokenResponse {
    public long wedding_id;
    public String token;
    public long getWedding_id() {
        return wedding_id;
    }

    public void setWedding_id(long wedding_id) {
        this.wedding_id = wedding_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
