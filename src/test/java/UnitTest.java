import com.feidao.dingzk.h2server.util.PortStatusCheck;
import com.feidao.dingzk.h2server.util.PropertyUtil;
import org.junit.Test;

public class UnitTest {
    @Test
    public void TestPort() {
        boolean status = PortStatusCheck.checkPort("22");
        System.out.println(status);
    }

    @Test
    public void TestProperties() {
        PropertyUtil props = new PropertyUtil("a.h2server.properties");
        System.out.println(props.filePath);
        System.out.println("server.root-----" + props.get("server.root"));
        String value1 = props.get("phone");
        if (props.containsKey("key1")) {
            System.out.println("key: phone" + "不存在");
            props.set("phone", "10086");
        } else {
            System.out.println(value1);
        }
    }
}
