package com.air.advantage.data;

import android.content.Context;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.jsondata.MasterStore;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;

@PurelyImplements({"SMAP\nLightFavourites.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LightFavourites.kt\ncom/air/advantage/data/LightFavourites\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,122:1\n1603#2,9:123\n1855#2:132\n1856#2:134\n1612#2:135\n1603#2,9:136\n1855#2:145\n1856#2:147\n1612#2:148\n1#3:133\n1#3:146\n37#4,2:149\n*S KotlinDebug\n*F\n+ 1 LightFavourites.kt\ncom/air/advantage/data/LightFavourites\n*L\n53#1:123,9\n53#1:132\n53#1:134\n53#1:135\n66#1:136,9\n66#1:145\n66#1:147\n66#1:148\n53#1:133\n66#1:146\n66#1:149,2\n*E\n"})
/* renamed from: com.air.advantage.data.e1 */
/* loaded from: classes.dex */
public final class LightFavourites {
    private boolean favouritesPaused;

    @NotNull
    private final List<String> lightFavourites = new ArrayList();

    @Nullable
    private a onFavouriteChangeListener;

    /* renamed from: com.air.advantage.data.e1$a */
    public interface a {
        void onFavouriteAdded(@Nullable String str, int i10, int i11);

        void onFavouriteMoved(int i10, int i11);

        void onFavouriteRemoved(@Nullable String str, int i10, int i11);

        void onFavouriteUpdated(@Nullable String str, int i10);
    }

    /* renamed from: com.air.advantage.data.e1$b */
    private static final class b implements Comparator<String> {

        @NotNull
        private final WeakReference<Context> contextWeakReference;

        public b(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.contextWeakReference = new WeakReference<>(context);
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        /* JADX DEBUG: Method merged with bridge method: compare(Ljava/lang/Object;Ljava/lang/Object;)I */
        @Override // java.util.Comparator
        public int compare(@NotNull String light1, @NotNull String light2) {
            Intrinsics.checkNotNullParameter(light1, "light1");
            Intrinsics.checkNotNullParameter(light2, "light2");
            Context context = this.contextWeakReference.get();
            if (context == null) {
                return 1;
            }
            return Intrinsics.compare(((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).J0(context, light2), ((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).J0(context, light1));
        }
    }

    private final void workOutFavourites(Context context, DataFavourites dataFavourites) {
        this.lightFavourites.clear();
        ArrayList<String> arrayList = new ArrayList();
        List<String> lightList = dataFavourites.lightStore.getLightList();
        ArrayList<String> arrayList2 = new ArrayList();
        for (String str : lightList) {
            if (str != null) {
                arrayList2.add(str);
            }
        }
        for (String str2 : arrayList2) {
            DataLight light = dataFavourites.lightStore.getLight(str2);
            Intrinsics.checkNotNull(light);
            if (light.typeFavourite() == 4) {
                if (((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).E0(context, str2)) {
                    List<String> list = this.lightFavourites;
                    list.add(list.size(), str2);
                }
            } else if (((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).J0(context, light.id) > 0) {
                arrayList.add(arrayList.size(), light.id);
            }
        }
        b bVar = new b(context);
        ArrayList arrayList3 = new ArrayList();
        for (String str3 : arrayList) {
            if (str3 != null) {
                arrayList3.add(str3);
            }
        }
        ArraysKt___ArraysKt1.sortWith(arrayList3.toArray(new String[0]), bVar);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str4 = (String) it.next();
            List<String> list2 = this.lightFavourites;
            list2.add(list2.size(), str4);
            if (this.lightFavourites.size() >= 10) {
                return;
            }
        }
    }

    public final int getFavouriteType(int i10) {
        DataLight lightFavourite = getLightFavourite(i10);
        if (lightFavourite != null) {
            return lightFavourite.typeFavourite();
        }
        return 0;
    }

    @Nullable
    public final DataLight getLightFavourite(int i10) {
        String str;
        DataLight light;
        if (i10 < 0 || i10 >= this.lightFavourites.size() || (str = this.lightFavourites.get(i10)) == null) {
            return null;
        }
        synchronized (MasterStore.class) {
            light = MasterStore.helper.getInstance().dataFavourites.lightStore.getLight(str);
        }
        return light;
    }

    public final int numberOfFavourites() {
        if (this.favouritesPaused) {
            return 0;
        }
        int size = this.lightFavourites.size();
        if (size <= 10) {
            return size;
        }
        return 10;
    }

    public final void setFavouritesPaused(@NotNull Context context, boolean z7) {
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (MasterStore.class) {
            DataFavourites dataFavourites = MasterStore.helper.getInstance().dataFavourites;
            if (z7) {
                int numberOfFavourites = numberOfFavourites();
                workOutFavourites(context, dataFavourites);
                this.favouritesPaused = true;
                a aVar = this.onFavouriteChangeListener;
                if (aVar != null) {
                    Intrinsics.checkNotNull(aVar);
                    aVar.onFavouriteRemoved("Paused", 0, numberOfFavourites);
                }
            } else {
                this.favouritesPaused = false;
                workOutFavourites(context, dataFavourites);
                if (this.onFavouriteChangeListener != null) {
                    int numberOfFavourites2 = numberOfFavourites();
                    a aVar2 = this.onFavouriteChangeListener;
                    Intrinsics.checkNotNull(aVar2);
                    aVar2.onFavouriteAdded("Paused", 0, numberOfFavourites2);
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setOnFavouriteChangeListener(@Nullable a aVar) {
        this.onFavouriteChangeListener = aVar;
    }
}