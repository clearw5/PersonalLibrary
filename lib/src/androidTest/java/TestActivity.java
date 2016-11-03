import android.app.Activity;

import com.stardust.xml.XmlDataObject;

/**
 * Created by Stardust on 2016/11/3.
 */
public class TestActivity extends Activity {


    public void onStart(){

    }

    private static class Group extends XmlDataObject {

        int sysId;
        int outId;
        String deviceGroupName;

        @Override
        protected String getTagName() {
            return "ROW";
        }

        @Override
        protected void setAttribute(String attribute, String value) throws NoSuchFieldException {
            switch (attribute) {
                case "sys_id":
                    sysId = Integer.parseInt(value);
                    break;
                case "out_id":
                    outId = Integer.parseInt(value);
                    break;
                case "device_group_name":
                    deviceGroupName = value;
                    break;
            }
        }

        @Override
        protected String[] getAttributeNames() {
            return new String[]{"sys_id", "out_id", "device_group_name"};
        }
    }
}
