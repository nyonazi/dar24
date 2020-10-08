
package com.dar24.app.model;

import java.util.ArrayList;
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
public class News$$Parcelable
    implements Parcelable, ParcelWrapper<com.dar24.app.model.News>
{

    private com.dar24.app.model.News news$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<News$$Parcelable>CREATOR = new Creator<News$$Parcelable>() {


        @Override
        public News$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new News$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public News$$Parcelable[] newArray(int size) {
            return new News$$Parcelable[size] ;
        }

    }
    ;

    public News$$Parcelable(com.dar24.app.model.News news$$2) {
        news$$0 = news$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(news$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.dar24.app.model.News news$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(news$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(news$$1));
            parcel$$1 .writeString(news$$1 .summary);
            parcel$$1 .writeString(news$$1 .thumbnail);
            parcel$$1 .writeString(news$$1 .publishedAt);
            parcel$$1 .writeString(news$$1 .author);
            parcel$$1 .writeString(news$$1 .link);
            parcel$$1 .writeString(news$$1 .description);
            parcel$$1 .writeString(news$$1 .title);
            parcel$$1 .writeString(news$$1 .category);
            if (news$$1 .newsRelatedList == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(news$$1 .newsRelatedList.size());
                for (com.dar24.app.model.NewsRelated newsRelated$$0 : news$$1 .newsRelatedList) {
                    com.dar24.app.model.NewsRelated$$Parcelable.write(newsRelated$$0, parcel$$1, flags$$0, identityMap$$0);
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.dar24.app.model.News getParcel() {
        return news$$0;
    }

    public static com.dar24.app.model.News read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.dar24.app.model.News news$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            news$$4 = new com.dar24.app.model.News();
            identityMap$$1 .put(reservation$$0, news$$4);
            news$$4 .summary = parcel$$3 .readString();
            news$$4 .thumbnail = parcel$$3 .readString();
            news$$4 .publishedAt = parcel$$3 .readString();
            news$$4 .author = parcel$$3 .readString();
            news$$4 .link = parcel$$3 .readString();
            news$$4 .description = parcel$$3 .readString();
            news$$4 .title = parcel$$3 .readString();
            news$$4 .category = parcel$$3 .readString();
            int int$$0 = parcel$$3 .readInt();
            ArrayList<com.dar24.app.model.NewsRelated> list$$0;
            if (int$$0 < 0) {
                list$$0 = null;
            } else {
                list$$0 = new ArrayList<com.dar24.app.model.NewsRelated>(int$$0);
                for (int int$$1 = 0; (int$$1 <int$$0); int$$1 ++) {
                    com.dar24.app.model.NewsRelated newsRelated$$1 = com.dar24.app.model.NewsRelated$$Parcelable.read(parcel$$3, identityMap$$1);
                    list$$0 .add(newsRelated$$1);
                }
            }
            news$$4 .newsRelatedList = list$$0;
            com.dar24.app.model.News news$$3 = news$$4;
            identityMap$$1 .put(identity$$1, news$$3);
            return news$$3;
        }
    }

}
