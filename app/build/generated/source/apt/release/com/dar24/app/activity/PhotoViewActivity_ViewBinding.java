// Generated code from Butter Knife. Do not modify!
package com.dar24.app.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dar24.app.R;
import com.github.chrisbanes.photoview.PhotoView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PhotoViewActivity_ViewBinding implements Unbinder {
  private PhotoViewActivity target;

  @UiThread
  public PhotoViewActivity_ViewBinding(PhotoViewActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PhotoViewActivity_ViewBinding(PhotoViewActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.pvThumbnail = Utils.findRequiredViewAsType(source, R.id.pvThumbnail, "field 'pvThumbnail'", PhotoView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PhotoViewActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.pvThumbnail = null;
  }
}
