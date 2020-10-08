// Generated code from Butter Knife. Do not modify!
package com.dar24.app.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dar24.app.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsDetailFragment_ViewBinding implements Unbinder {
  private NewsDetailFragment target;

  private View view2131230851;

  private View view2131230852;

  @UiThread
  public NewsDetailFragment_ViewBinding(final NewsDetailFragment target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.tvCategory = Utils.findRequiredViewAsType(source, R.id.tvCategory, "field 'tvCategory'", TextView.class);
    target.tvPublishedAt = Utils.findRequiredViewAsType(source, R.id.tvPublishedAt, "field 'tvPublishedAt'", TextView.class);
    target.webView = Utils.findRequiredViewAsType(source, R.id.webView, "field 'webView'", WebView.class);
    target.ivThumbnail = Utils.findRequiredViewAsType(source, R.id.ivThumbnail, "field 'ivThumbnail'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.llNextNews, "field 'llNextNews' and method 'onNextNews'");
    target.llNextNews = Utils.castView(view, R.id.llNextNews, "field 'llNextNews'", LinearLayout.class);
    view2131230851 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNextNews();
      }
    });
    view = Utils.findRequiredView(source, R.id.llPreviousNews, "field 'llPreviousNews' and method 'onPreviousNews'");
    target.llPreviousNews = Utils.castView(view, R.id.llPreviousNews, "field 'llPreviousNews'", LinearLayout.class);
    view2131230852 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPreviousNews();
      }
    });
    target.tvNextTitle = Utils.findRequiredViewAsType(source, R.id.tvNextNewsTitle, "field 'tvNextTitle'", TextView.class);
    target.tvPreviousTitle = Utils.findRequiredViewAsType(source, R.id.tvPreviousNewsTitle, "field 'tvPreviousTitle'", TextView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsDetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvCategory = null;
    target.tvPublishedAt = null;
    target.webView = null;
    target.ivThumbnail = null;
    target.llNextNews = null;
    target.llPreviousNews = null;
    target.tvNextTitle = null;
    target.tvPreviousTitle = null;
    target.recyclerView = null;

    view2131230851.setOnClickListener(null);
    view2131230851 = null;
    view2131230852.setOnClickListener(null);
    view2131230852 = null;
  }
}
