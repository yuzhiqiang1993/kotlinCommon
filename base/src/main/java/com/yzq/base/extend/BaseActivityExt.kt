import android.view.LayoutInflater
import androidx.core.app.ComponentActivity
import androidx.viewbinding.ViewBinding
import com.yzq.base.ui.delegate.ActivityViewBindingProp


/**
 * 扩展方法的实现
 *
 * @param B
 * @param inflate
 * @receiver
 */
fun <B : ViewBinding> ComponentActivity.viewbind(
    inflate: (layoutInflater: LayoutInflater) -> B
) = ActivityViewBindingProp<ComponentActivity, B>(inflate)