// Generated by data binding compiler. Do not edit!
package com.example.practicalwisdomleaf.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.example.practicalwisdomleaf.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogdetailsBinding extends ViewDataBinding {
  @NonNull
  public final Button btnok;

  @NonNull
  public final TextView tvTitle;

  @NonNull
  public final TextView tvTitleMsg;

  protected DialogdetailsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnok, TextView tvTitle, TextView tvTitleMsg) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnok = btnok;
    this.tvTitle = tvTitle;
    this.tvTitleMsg = tvTitleMsg;
  }

  @NonNull
  public static DialogdetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialogdetails, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogdetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogdetailsBinding>inflateInternal(inflater, R.layout.dialogdetails, root, attachToRoot, component);
  }

  @NonNull
  public static DialogdetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialogdetails, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogdetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogdetailsBinding>inflateInternal(inflater, R.layout.dialogdetails, null, false, component);
  }

  public static DialogdetailsBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static DialogdetailsBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogdetailsBinding)bind(component, view, R.layout.dialogdetails);
  }
}
