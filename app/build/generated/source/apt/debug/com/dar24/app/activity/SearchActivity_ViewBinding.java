// Generated code from Butter Knife. Do not modify!
package com.dar24.app.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dar24.app.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchActivity_ViewBinding implements Unbinder {
  private SearchActivity target;

  @UiThread
  public SearchActivity_ViewBinding(SearchActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchActivity_ViewBinding(SearchActivity target, View source) {
    this.target = target;

    target.llMessage = Utils.findRequiredViewAsType(source, R.id.llMessage, "field 'llMessage'", LinearLayout.class);
    target.ivMessage = Utils.findRequiredViewAsType(source, R.id.ivIcon, "field 'ivMessage'", ImageView.class);
    target.tvMessage = Utils.findRequiredViewAsType(source, R.id.tvMessage, "field 'tvMessage'", TextView.class);
    target.etSearch = Utils.findRequiredViewAsType(source, R.id.etSearch, "field 'etSearch'", EditText.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefresh, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.llMessage = null;
    target.ivMessage = null;
    target.tvMessage = null;
    target.etSearch = null;
    target.swipeRefreshLayout = null;
    target.recyclerView = null;
    target.toolbar = null;
  }
}
