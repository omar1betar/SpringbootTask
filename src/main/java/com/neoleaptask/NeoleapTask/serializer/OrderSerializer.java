package com.neoleaptask.NeoleapTask.serializer;


import com.hazelcast.nio.serialization.ByteArraySerializer;
import com.neoleaptask.NeoleapTask.model.Order;
import org.hibernate.type.SerializationException;

import java.io.*;

public class OrderSerializer implements ByteArraySerializer<Order> {

    @Override
    public byte[] write(Order order) throws SerializationException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(order);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Error serializing Order", e);
        }
    }

    @Override
    public Order read(byte[] bytes) throws SerializationException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (Order) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException("Error deserializing Order", e);
        }
    }

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public void destroy() {
    }
}
