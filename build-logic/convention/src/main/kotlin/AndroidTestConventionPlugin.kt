import com.android.build.gradle.LibraryExtension
import com.yunhan.sample.kotlin.configureGradleManagedDevices
import com.yunhan.sample.kotlin.configureKotlinAndroid
import com.yunhan.sample.kotlin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
                configureGradleManagedDevices(this)
            }

            dependencies {
                "implementation"(libs.findLibrary("junit4").get())
                "implementation"(libs.findLibrary("androidx.test.junit").get())
                "implementation"(libs.findLibrary("androidx.test.espresso.core").get())
                "implementation"(libs.findLibrary("robolectric").get())
            }
        }
    }
}
