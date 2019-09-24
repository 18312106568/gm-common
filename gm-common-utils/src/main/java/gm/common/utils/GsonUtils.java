package gm.common.utils;

import com.google.gson.Gson;

public class GsonUtils {
    private static ThreadLocal<Gson> threadLocal = new ThreadLocal<>();

    public static Gson getGson(){
        Gson gson = threadLocal.get();
        if(gson == null){
            gson = new Gson();
            threadLocal.set(gson);
        }
        return gson;
    }
}
