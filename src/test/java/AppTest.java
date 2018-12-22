import com.feidao.dingzk.h2server.util.PropertyUtil;

import java.util.UUID;

public class AppTest {
    public static void main(String[] args) {
        getSetProperties();
    }

    private static void getSetProperties() {
        PropertyUtil props = new PropertyUtil("a.h2server.properties");
        System.out.println(props.filePath);
        System.out.println(props.getFullPath());
        System.out.println("server.root-----" + props.get("server.root"));
        String value1 = props.get("phone");
        if (props.containsKey("phone")) {
            System.out.println("key: phone 原始值: " + value1);
            props.set("phone", UUID.randomUUID().toString());
            System.out.println("key: phone 新值: " + props.get("phone"));
        } else {
            System.out.println("key: phone" + "不存在");
            props.set("phone", UUID.randomUUID().toString());
            System.out.println("key: phone 新值: " + props.get("phone"));
        }
    }
}