package com.im.message;

public interface Serializer {
    byte JSON_SERIALIZER = 1;
    Serializer DEFAULT = new JSONSerialzer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object o);

    <T> T deserialize(Class<T> clazz,byte[] bytes);
}
