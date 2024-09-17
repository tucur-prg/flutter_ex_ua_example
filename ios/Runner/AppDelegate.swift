import Flutter
import UIKit
import SafariServices

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
  var myvc: MyViewController!

  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    GeneratedPluginRegistrant.register(with: self)

    let controller: FlutterViewController = window?.rootViewController as! FlutterViewController

    let methodChannel = FlutterMethodChannel(name: "Channel", binaryMessenger: controller as! FlutterBinaryMessenger)
    methodChannel.setMethodCallHandler({
        (call:FlutterMethodCall, result: @escaping FlutterResult) -> Void in
        switch call.method {
        case "hello" :
            result("Hello from Swift!")
        case "launch":
            NSLog("LOGGING:call launch")
            guard let url = URL(string: "https://tucur-prg.github.io/flutter_ex_ua_example/") else {
                result(FlutterError(code: "FAILER", message: "", details: nil))
                return
            }

            self.myvc = MyViewController(url: url, completion: result)
            if let vc = UIApplication.shared.windows.first?.rootViewController {
              vc.present(self.myvc.safariViewController, animated: true)
            }
        default :
            result(FlutterMethodNotImplemented)
        }
    })

    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }

  // Custom url Scheme
  override func application(
    _ app: UIApplication,
    open url: URL,
    options: [UIApplication.OpenURLOptionsKey : Any] = [:]
  ) -> Bool {
    NSLog("LOGGING:custom scheme open")
    NSLog("LOGGING:query : \(url.query!)")

    myvc.close()

    return true
  }
}

class MyViewController: NSObject, SFSafariViewControllerDelegate {
  private let completion: FlutterResult

  let safariViewController: SFSafariViewController

  init(url: URL, completion: @escaping FlutterResult) {
    NSLog("LOGGING:init")
    self.completion = completion
    self.safariViewController = SFSafariViewController(url: url)
    super.init()
    self.safariViewController.delegate = self
  }

  deinit {
    NSLog("LOGGIING:deinit")
  }

  func safariViewController(_ controller: SFSafariViewController, didCompleteInitialLoad didLoadSuccessfully: Bool) {
    NSLog("LOGGING:didCompleteInitialLoad")
  }

  func safariViewControllerDidFinish(_ controller: SFSafariViewController) {
    NSLog("LOGGING:safariViewControllerDidFinish")
    controller.dismiss(animated: true, completion: nil)
    completion("finish")
  }

  func close() {
    safariViewControllerDidFinish(safariViewController)
  }
}