// Generated code from Butter Knife. Do not modify!
package com.dar24.app.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dar24.app.R;
import com.flipboard.bottomsheet.BottomSheetLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsDetailActivity_ViewBinding implements Unbinder {
  private NewsDetailActivity target;

  @UiThread
  public NewsDetailActivity_ViewBinding(NewsDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewsDetailActivity_ViewBinding(NewsDetailActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.bottomSheetLayout = Utils.findRequiredViewAsType(source, R.id.bottomsheet, "field 'bottomSheetLayout'", BottomSheetLayout.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewPager, "field 'viewPager'", ViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.bottomSheetLayout = null;
    target.viewPager = null;
  }
}
