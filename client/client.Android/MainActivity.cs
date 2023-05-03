using Android;
using Android.App;
using Android.Content;
using Android.Content.PM;
using Android.OS;
using Android.Widget;
using AndroidX.Core.App;
using Avalonia.Android;

namespace client.Android;

[Activity(Label = "client.Android", Theme = "@style/MyTheme.NoActionBar", Icon = "@drawable/icon", LaunchMode = LaunchMode.SingleTop, ConfigurationChanges = ConfigChanges.Orientation | ConfigChanges.ScreenSize)]
public class MainActivity : AvaloniaMainActivity
{
    private static string[] STORAGE_PERMISSIONS = new string[] { Manifest.Permission.WriteExternalStorage };

    protected override void OnCreate(Bundle savedInstanceState)
    {
        base.OnCreate(savedInstanceState);
        Permission permission = ActivityCompat.CheckSelfPermission(this, Manifest.Permission.WriteExternalStorage);
        if (permission == Permission.Denied)
            ActivityCompat.RequestPermissions(this, STORAGE_PERMISSIONS, 1);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.SetTitle("About SHIFT");
        builder.SetMessage("SHIFT CONNECTS US ALL\nSHIFT will be decentral and will therefore not run on a single server.\n" +
                "Your SHIFT account is anonymous, only your real friends know who is behind your account.\n" +
                "No registration needed.\n" +
                "No server means, also no censorship and no ads.\n" +
                "SHIFT also creates a universal basic income and can be used to show gratitude to other members.");
        builder.SetPositiveButton("OK", OkClicked);
        AlertDialog? dialog = builder.Create();
        if (dialog != null)
            dialog.Show();

    }

    private void OkClicked(object? sender, DialogClickEventArgs e)
    {
        Toast? t = Toast.MakeText(Application.Context, "Ok clicked", ToastLength.Short);
        if (t != null)
            t.Show();
    }
}
