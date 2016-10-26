import com.easemob.wukong.model.data.usertask.Encryptype;
import com.easemob.wukong.utils.wukong.EncryptUtiles;

/**
 * Created by dongwentao on 16/10/26.
 */
public class GeneralTest {
    public static void main(String[] args) throws Exception {
        String password = "81dc9bdb52d04dc20036dbd8313ed055";
        String encryptedType = Encryptype.MD5.toString();
        String s = EncryptUtiles.encryptEncode(password, encryptedType);
        System.out.println(s);
    }
}
