class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrawerComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    checkPermissions()
                    NavigationView()
                }
            }
        }
    }
}

@Composable
fun checkPermissions(): Boolean {

    var permissionGranted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    DisposableEffect(Unit) {
        val permissionStatus = ContextCompat.checkSelfPermission(context, permission)
        permissionGranted = permissionStatus == PackageManager.PERMISSION_GRANTED
        if (!permissionGranted) {
            ActivityCompat.requestPermissions(
                (context as Activity),
                arrayOf(permission),
                1
            )
        }
        onDispose { }
    }
    return permissionGranted
}