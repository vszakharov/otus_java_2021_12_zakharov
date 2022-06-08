package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor,
            EntitySQLMetaData entitySQLMetaData,
            EntityClassMetaData<T> entityClassMetaData
    ) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        var sql = entitySQLMetaData.getSelectByIdSql();
        return dbExecutor.executeSelect(connection, sql, List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createItem(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        var sql = entitySQLMetaData.getSelectAllSql();
        return dbExecutor.executeSelect(connection, sql, Collections.emptyList(), rs -> {
            var itemList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    itemList.add(createItem(rs));
                }
                return itemList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T item) {
        var sql = entitySQLMetaData.getInsertSql();
        var fields = entityClassMetaData.getFieldsWithoutId();
        var values = fields.stream()
                .map(field -> getFieldValue(field, item))
                .toList();
        return dbExecutor.executeStatement(connection, sql, values);
    }

    @Override
    public void update(Connection connection, T item) {
        var sql = entitySQLMetaData.getUpdateSql();
        var fields = entityClassMetaData.getFieldsWithoutId();
        var idField = entityClassMetaData.getIdField();
        var valuesStream = fields.stream()
                .map(field -> getFieldValue(field, item));
        var id = getFieldValue(idField, item);
        var params = Stream.concat(valuesStream, Stream.of(id)).toList();
        dbExecutor.executeStatement(connection, sql, params);
    }

    private T createItem(ResultSet rs) {
        try {
            var fields = entityClassMetaData.getAllFields();
            var constructor = entityClassMetaData.getConstructor();

            var args = new ArrayList<>();
            for (var field : fields) {
                args.add(rs.getObject(field.getName()));
            }

            return constructor.newInstance(args.toArray());
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private Object getFieldValue(Field field, T item) {
        try {
            field.setAccessible(true);
            return field.get(item);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
