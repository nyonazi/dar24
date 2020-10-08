
package com.dar24.app.model;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated("org.parceler.ParcelAnnotationProcessor")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class NewsRelated$$Parcelable
    implements Parcelable, ParcelWrapper<com.dar24.app.model.NewsRelated>
{

    private com.dar24.app.model.NewsRelated newsRelated$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<NewsRelated$$Parcelable>CREATOR = new Creator<NewsRelated$$Parcelable>() {


        @Override
        public NewsRelated$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new NewsRelated$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public NewsRelated$$Parcelable[] newArray(int size) {
            return new NewsRelated$$Parcelable[size] ;
        }

    }
    ;

    public NewsRelated$$Parcelable(com.dar24.app.model.NewsRelated newsRelated$$2) {
        newsRelated$$0 = newsRelated$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(newsRelated$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.dar24.app.model.NewsRelated newsRelated$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(newsRelated$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(newsRelated$$1));
            parcel$$1 .writeString(newsRelated$$1 .summary);
            parcel$$1 .writeString(newsRelated$$1 .thumbnail);
            parcel$$1 .writeString(newsRelated$$1 .publishedAt);
            parcel$$1 .writeString(newsRelated$$1 .link);
            parcel$$1 .writeInt(newsRelated$$1 .id);
            parcel$$1 .writeString(newsRelated$$1 .title);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.dar24.app.model.NewsRelated getParcel() {
        return newsRelated$$0;
    }

    public static com.dar24.app.model.NewsRelated read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.dar24.app.model.NewsRelated newsRelated$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            newsRelated$$4 = new com.dar24.app.model.NewsRelated();
            identityMap$$1 .put(reservation$$0, newsRelated$$4);
            newsRelated$$4 .summary = parcel$$3 .readString();
            newsRelated$$4 .thumbnail = parcel$$3 .readString();
            newsRelated$$4 .publishedAt = parcel$$3 .readString();
            newsRelated$$4 .link = parcel$$3 .readString();
            newsRelated$$4 .id = parcel$$3 .readInt();
            newsRelated$$4 .title = parcel$$3 .readString();
            com.dar24.app.model.NewsRelated newsRelated$$3 = newsRelated$$4;
            identityMap$$1 .put(identity$$1, newsRelated$$3);
            return newsRelated$$3;
        }
    }

}
