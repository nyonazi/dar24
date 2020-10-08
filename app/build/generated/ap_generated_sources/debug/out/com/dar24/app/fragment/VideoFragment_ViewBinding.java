// Generated code from Butter Knife. Do not modify!
package com.dar24.app.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dar24.app.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VideoFragment_ViewBinding implements Unbinder {
  private VideoFragment target;

  @UiThread
  public VideoFragment_ViewBinding(VideoFragment target, View source) {
    this.target = target;

    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tabLayout, "field 'tabLayout'", TabLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    VideoFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tabLayout = null;
  }
}
