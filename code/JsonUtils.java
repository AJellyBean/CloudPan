package com;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by silver on 16-2-18.
 */
public class JsonUtils {


    public static void main() {
        String json = "{\"56c4539bc24aa800528d16ba\":{\"height\":852,\"id\":\"56c4539d128fe100516cc54f\",\"likesCount\":0,\"taggedAudio\":\"\",\"thumbUrl\":\"http://ac-tSyVzkMT.clouddn.com/f6GVVnIz5NoIkBDfTbb6cSomKwiXDKndArRpraDZ.mp4?vframe/jpg/offset/0/w/72/h/127\",\"videoUrl\":\"http://ac-tSyVzkMT.clouddn.com/f6GVVnIz5NoIkBDfTbb6cSomKwiXDKndArRpraDZ.mp4\",\"width\":480},\"56c453b26be3ff00546d8c7d\":{\"height\":852,\"id\":\"56c453b7816dfa0051caa560\",\"likesCount\":0,\"taggedAudio\":\"\",\"thumbUrl\":\"http://ac-tSyVzkMT.clouddn.com/TtAULEXqkED1VQzAqnFgDeQoQRtaukRpsDyTWxg9.mp4?vframe/jpg/offset/0/w/72/h/127\",\"videoUrl\":\"http://ac-tSyVzkMT.clouddn.com/TtAULEXqkED1VQzAqnFgDeQoQRtaukRpsDyTWxg9.mp4\",\"width\":480}}\n";
        List<Bean> beanList = JsonUtils.parseJSON(json, Bean.class);
        android.util.Log.e("JsonUtils", "bean thumbUrl" + beanList.get(0).thumbUrl);

    }

    public static <T> List<T> parseJSON(String json, Class<T> clazz) {

        try {

            JSONObject jsonObject = new JSONObject(json);
            List<T> beanList = new ArrayList();
            Gson gson = new Gson();
            String key = null;
            String childStr = null;

            Iterator jsonKeys = jsonObject.keys();
            while (jsonKeys.hasNext()) {
                key = jsonKeys.next().toString();
                childStr = jsonObject.getString(key);
                T bean = gson.fromJson(childStr, clazz);
                beanList.add(bean);
                android.util.Log.e("JsonUtils", bean.toString());
            }
            return beanList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public class Bean {
        public int height;

        public String id;

        public int likesCount;

        public String taggedAudio;

        public String thumbUrl;

        public String videoUrl;

        public int width;

        @Override
        public String toString() {
            return "Bean{" +
                    "height=" + height +
                    ", id='" + id + '\'' +
                    ", likesCount=" + likesCount +
                    ", taggedAudio='" + taggedAudio + '\'' +
                    ", thumbUrl='" + thumbUrl + '\'' +
                    ", videoUrl='" + videoUrl + '\'' +
                    ", width=" + width +
                    '}';
        }
    }

}
