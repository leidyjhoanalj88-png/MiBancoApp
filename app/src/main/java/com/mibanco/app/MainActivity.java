<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="32dp">

    <EditText
        android:id="@+id/txtUser"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Usuario"
        android:layout_marginBottom="16dp"/>

    <EditText
        android:id="@+id/txtPass"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Contraseña"
        android:inputType="textPassword"
        android:layout_marginBottom="24dp"/>

    <Button
        android:id="@+id/btnEntrar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="ENTRAR"/>

</LinearLayout>