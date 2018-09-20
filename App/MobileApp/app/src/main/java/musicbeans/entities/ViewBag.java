package musicbeans.entities;

import java.util.HashMap;

public class ViewBag {

    public static HashMap<String,Object>  map = new HashMap<>();

    public static  void put(String key,Object content)
    {
        map.put(key,content);
    }
    public static Object get(String key)
    {
        Object obj = map.get(key);
        map.remove(key);
        return  obj;
    }
}
