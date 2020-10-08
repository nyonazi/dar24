// Generated code from Butter Knife. Do not modify!
package com.dar24.app.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dar24.app.R;
import com.flipboard.bottomsheet.BottomSheetLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsRelatedDetailActivity_ViewBinding implements Unbinder {
  private NewsRelatedDetailActivity target;

  @UiThread
  public NewsRelatedDetailActivity_ViewBinding(NewsRelatedDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewsRelatedDetailActivity_ViewBinding(NewsRelatedDetailActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.bottomSheetLayout = Utils.findRequiredViewAsType(source, R.id.bottomsheet, "field 'bottomSheetLayout'", BottomSheetLayout.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.tvPublishedAt = Utils.findRequiredViewAsType(source, R.id.tvPublishedAt, "field 'tvPublishedAt'", TextView.class);
    target.webView = Utils.findRequiredViewAsType(source, R.id.webView, "field 'webView'", WebView.class);
    target.ivThumbnail = Utils.findRequiredViewAsType(source, R.id.ivThumbnail, "field 'ivThumbnail'", ImageView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefresh, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsRelatedDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.bottomSheetLayout = null;
    target.tvTitle = null;
    target.tvPublishedAt = null;
    target.webView = null;
    target.ivThumbnail = null;
    target.swipeRefreshLayout = null;
  }
}
