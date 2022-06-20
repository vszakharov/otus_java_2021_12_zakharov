package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.Collections;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final static String SELECT = "select *";
    private final static String FROM = " from %s";
    private final static String WHERE = " where %s = ?";
    private final static String INSERT = "insert into %s (%s) values (%s)";
    private final static String UPDATE = "update %s set %s";

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        var table = entityClassMetaData.getName();

        return SELECT + String.format(FROM, table);
    }

    @Override
    public String getSelectByIdSql() {
        var table = entityClassMetaData.getName();
        var id = entityClassMetaData.getIdField().getName();

        return SELECT + String.format(FROM, table) + String.format(WHERE, id);
    }

    @Override
    public String getInsertSql() {
        var table = entityClassMetaData.getName();
        var fields = entityClassMetaData.getFieldsWithoutId();
        var columnCount = fields.size();
        var columns = String.join(",", fields.stream().map(Field::getName).toList());
        var values = String.join(",", Collections.nCopies(columnCount, "?"));

        return String.format(INSERT, table, columns, values);
    }

    @Override
    public String getUpdateSql() {
        var table = entityClassMetaData.getName();
        var id = entityClassMetaData.getIdField().getName();
        var fields = entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).toList();
        var columns = String.join(",", fields.stream().map(field -> field + " = ?").toList());

        return String.format(UPDATE, table, columns) + String.format(WHERE, id);
    }
}
