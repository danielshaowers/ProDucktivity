package TestUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtil {
    public static < T > T getValue(LiveData< T > liveData) {
        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        Observer< T > observer = new Observer < T > () {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        try{
            latch.await(2, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (T) data[0];
    }
}