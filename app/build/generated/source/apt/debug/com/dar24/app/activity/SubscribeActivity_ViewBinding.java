// Generated code from Butter Knife. Do not modify!
package com.dar24.app.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dar24.app.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SubscribeActivity_ViewBinding implements Unbinder {
  private SubscribeActivity target;

  private View view2131230762;

  @UiThread
  public SubscribeActivity_ViewBinding(SubscribeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SubscribeActivity_ViewBinding(final SubscribeActivity target, View source) {
    this.target = target;

    View view;
    target.etEmail = Utils.findRequiredViewAsType(source, R.id.etEmail, "field 'etEmail'", EditText.class);
    target.etFirstName = Utils.findRequiredViewAsType(source, R.id.etFirstname, "field 'etFirstName'", EditText.class);
    target.etLastName = Utils.findRequiredViewAsType(source, R.id.etLastname, "field 'etLastName'", EditText.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.etPhone, "field 'etPhone'", EditText.class);
    view = Utils.findRequiredView(source, R.id.bnSubscribe, "field 'bnSubscribe' and method 'onSubscribe'");
    target.bnSubscribe = Utils.castView(view, R.id.bnSubscribe, "field 'bnSubscribe'", Button.class);
    view2131230762 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSubscribe();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SubscribeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etEmail = null;
    target.etFirstName = null;
    target.etLastName = null;
    target.etPhone = null;
    target.bnSubscribe = null;

    view2131230762.setOnClickListener(null);
    view2131230762 = null;
  }
}
