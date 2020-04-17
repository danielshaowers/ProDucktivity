package com.example.producktivity;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.producktivity.R;
//to be used for unit testing of fragments https://medium.com/@aitorvs/isolate-your-fragments-just-for-testing-ea7d4fddcba2
//when other aspects aren't necessary only!
@RestrictTo(RestrictTo.Scope.TESTS)
public class SingleFragmentActivity extends AppCompatActivity {
    /**
     * Used as container to test fragments in isolation with Espresso
     */
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FrameLayout content = new FrameLayout(this);
            content.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            content.setId(R.id.nav_host_fragment);
            setContentView(content);
        }

        public void setFragment(Fragment fragment) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nav_host_fragment, fragment, "TEST")
                    .commit();
        }

        public void replaceFragment(Fragment fragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment).commit();
        }
}
