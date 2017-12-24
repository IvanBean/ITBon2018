package ivankuo.com.itbon2018.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Owner {

    @SerializedName("login")
    @NonNull
    public final String login;

    @SerializedName("avatar_url")
    public final String avatarUrl;

    @SerializedName("url")
    public final String url;

    public Owner(@NonNull String login, String avatarUrl, String url) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(this.login, owner.login) &&
                Objects.equals(this.avatarUrl, owner.avatarUrl) &&
                Objects.equals(this.url, owner.url);
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}