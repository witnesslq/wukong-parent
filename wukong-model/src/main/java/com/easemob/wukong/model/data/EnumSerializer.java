package com.easemob.wukong.model.data;

import com.easemob.wukong.model.annotation.JsonEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by dongwentao on 16/9/30.
 */
public class EnumSerializer<T> extends StdSerializer<T> {

    EnumSerializer() {
        this(null);
    }

    EnumSerializer(Class<T> t) {
        super(t);
    }

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        Method[] methods = value.getClass().getDeclaredMethods();

        for (Method method : methods) {

            if (null == method.getAnnotation(JsonEnum.class)) continue;

            gen.writeFieldName(WordUtils.uncapitalize(method.getName().substring(getFirstUpIndex(method.getName()))));

            try {
                gen.writeString(method.invoke(value) + "");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        gen.writeEndObject();
    }

    private int getFirstUpIndex(String word){

        char[] chars = word.toCharArray();

        for (int i=0;i<chars.length;i++){

            if (chars[i]>='A' && chars[i]<='Z'){
                return i;
            }
        }
        return 0;
    }
}
