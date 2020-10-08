// Generated code from Butter Knife. Do not modify!
package com.dar24.app.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dar24.app.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MenuFragment_ViewBinding implements Unbinder {
  private MenuFragment target;

  @UiThread
  public MenuFragment_ViewBinding(MenuFragment target, View source) {
    this.target = target;

    target.recyclerViewMenuTop = Utils.findRequiredViewAsType(source, R.id.recyclerViewMenuTop, "field 'recyclerViewMenuTop'", RecyclerView.class);
    target.recyclerViewMenuBottom = Utils.findRequiredViewAsType(source, R.id.recyclerViewMenuBottom, "field 'recyclerViewMenuBottom'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MenuFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerViewMenuTop = null;
    target.recyclerViewMenuBottom = null;
  }
}
