package ru.otus.dao;

import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.otus.model.DocumentInfo;

public class DocumentDaoImpl implements DocumentDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DocumentDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Сохранить в базу информацию о документе.
     */
    public void create(DocumentInfo documentInfo) {
        String insertQuery = "" +
                "insert into document " +
                "(id, name, size, owner) " +
                "values " +
                "(:id, :name, :size, :owner)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", documentInfo.documentId())
                .addValue("name", documentInfo.documentName())
                .addValue("size", documentInfo.size())
                .addValue("owner", documentInfo.owner());

        namedParameterJdbcTemplate.update(insertQuery, params);
    }

    /**
     * Расшарить документ (сделать публичным).
     */
    public void share(String documentId) {
        String updateQuery = "" +
                "update document " +
                "   set public = true " +
                " where id = :documentId";

        SqlParameterSource params = new MapSqlParameterSource("documentId", documentId);

        namedParameterJdbcTemplate.update(updateQuery, params);
    }

    /**
     * Получить информацию о документе.
     */
    public Optional<DocumentInfo> get(String documentId) {
        String query = "" +
                "select id, name, size, owner, public " +
                "  from document " +
                " where id = :documentId";

        return namedParameterJdbcTemplate.query(
                query,
                new MapSqlParameterSource("documentId", documentId),
                rs -> !rs.next() ? Optional.empty() :
                        Optional.of(
                                new DocumentInfo(
                                        rs.getString("id"),
                                        rs.getString("name"),
                                        rs.getLong("size"),
                                        rs.getString("owner"),
                                        rs.getBoolean("public")
                                )
                        )
        );
    }

}
